package com.photowatermark;

import com.photowatermark.exception.ExifReadException;
import com.photowatermark.exception.FileOperationException;
import com.photowatermark.util.MemoryUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 图片处理器类，用于处理图片水印添加的完整流程
 */
public class ImageProcessor {
    private static final Logger logger = LoggerFactory.getLogger(ImageProcessor.class);

    private ExifReader exifReader;
    private WatermarkProcessor watermarkProcessor;
    private FileOutputManager fileOutputManager;

    public ImageProcessor() {
        this.exifReader = new ExifReader();
        this.watermarkProcessor = new WatermarkProcessor();
        this.fileOutputManager = new FileOutputManager();

        // 记录初始化时的内存使用情况
        MemoryUtils.logMemoryUsage();
    }

    /**
     * 处理单个图片文件
     *
     * @param imagePath 图片路径
     * @param fontSize 字体大小
     * @param color 字体颜色
     * @param position 水印位置
     * @param config 配置管理器
     * @throws ExifReadException EXIF读取异常
     * @throws FileOperationException 文件操作异常
     * @throws IOException IO异常
     */
    public void processImage(String imagePath, int fontSize, String color, String position, ConfigurationManager config)
            throws ExifReadException, FileOperationException, IOException {
        if (imagePath == null || imagePath.isEmpty()) {
            throw new IllegalArgumentException("图片路径不能为空");
        }

        File originalImageFile = new File(imagePath);
        if (!originalImageFile.exists()) {
            throw new FileOperationException("图片文件不存在: " + imagePath);
        }

        logger.info("开始处理图片: {}", imagePath);

        // 检查内存是否充足
        if (!MemoryUtils.isMemorySufficient(50 * 1024 * 1024)) { // 假设需要50MB
            logger.warn("内存可能不足，当前处理: {}", imagePath);
        }

        // 1. 读取EXIF信息获取拍摄时间
        String captureDate = exifReader.readCaptureDateAsString(originalImageFile);
        if (captureDate == null || captureDate.isEmpty()) {
            logger.warn("无法从图片中读取拍摄时间，使用默认水印文本: {}", imagePath);
            captureDate = "No EXIF Date";
        }

        // 2. 创建水印目录
        File watermarkDir = fileOutputManager.createWatermarkDirectory(imagePath);

        // 3. 生成输出文件路径
        File outputFile = fileOutputManager.generateWatermarkFilePath(watermarkDir, originalImageFile);

        // 4. 添加水印
        watermarkProcessor.addTextWatermark(originalImageFile, captureDate, fontSize, color, position, outputFile);

        logger.info("图片处理完成: {} -> {}", imagePath, outputFile.getPath());

        // 处理完成后记录内存使用情况
        MemoryUtils.logMemoryUsage();
    }

    /**
     * 批量处理图片文件
     *
     * @param imagePaths 图片路径列表
     * @param fontSize 字体大小
     * @param color 字体颜色
     * @param position 水印位置
     * @param config 配置管理器
     * @return 处理结果列表
     */
    public List<ProcessResult> processImages(List<String> imagePaths, int fontSize, String color, String position, ConfigurationManager config) {
        List<ProcessResult> results = new ArrayList<>();

        if (imagePaths == null || imagePaths.isEmpty()) {
            logger.warn("图片路径列表为空");
            return results;
        }

        logger.info("开始批量处理 {} 个图片文件", imagePaths.size());

        // 记录处理前的内存使用情况
        MemoryUtils.logMemoryUsage();

        for (String imagePath : imagePaths) {
            try {
                processImage(imagePath, fontSize, color, position, config);
                results.add(new ProcessResult(imagePath, true, "处理成功"));
            } catch (Exception e) {
                logger.error("处理图片失败: {}", imagePath, e);
                results.add(new ProcessResult(imagePath, false, e.getMessage()));
            }

            // 每处理10个文件建议一次垃圾回收
            if (results.size() % 10 == 0) {
                MemoryUtils.suggestGarbageCollection();
            }
        }

        logger.info("批量处理完成，成功: {}，失败: {}",
            results.stream().filter(r -> r.isSuccess()).count(),
            results.stream().filter(r -> !r.isSuccess()).count());

        // 记录处理后的内存使用情况
        MemoryUtils.logMemoryUsage();

        // 最后建议一次垃圾回收
        MemoryUtils.suggestGarbageCollection();

        return results;
    }

    /**
     * 处理结果类
     */
    public static class ProcessResult {
        private String imagePath;
        private boolean success;
        private String message;

        public ProcessResult(String imagePath, boolean success, String message) {
            this.imagePath = imagePath;
            this.success = success;
            this.message = message;
        }

        // Getter方法
        public String getImagePath() {
            return imagePath;
        }

        public boolean isSuccess() {
            return success;
        }

        public String getMessage() {
            return message;
        }
    }
}