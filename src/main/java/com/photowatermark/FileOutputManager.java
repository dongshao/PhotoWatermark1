package com.photowatermark;

import com.photowatermark.exception.FileOperationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * 文件输出管理器类，用于管理水印图片的输出目录和文件保存
 */
public class FileOutputManager {
    private static final Logger logger = LoggerFactory.getLogger(FileOutputManager.class);

    private static final String WATERMARK_DIR_SUFFIX = "_watermark";

    /**
     * 创建水印图片的输出目录
     *
     * @param originalImagePath 原始图片路径
     * @return 水印图片输出目录的File对象
     * @throws FileOperationException 文件操作异常
     */
    public File createWatermarkDirectory(String originalImagePath) throws FileOperationException {
        if (originalImagePath == null || originalImagePath.isEmpty()) {
            throw new IllegalArgumentException("原始图片路径不能为空");
        }

        File originalFile = new File(originalImagePath);
        File parentDir = originalFile.getParentFile();

        // 如果原始图片在根目录，则在当前目录创建水印目录
        if (parentDir == null) {
            parentDir = new File(".");
        }

        String parentDirName = parentDir.getName();
        String watermarkDirName = parentDirName + WATERMARK_DIR_SUFFIX;
        File watermarkDir = new File(parentDir, watermarkDirName);

        logger.debug("创建水印目录: {}", watermarkDir.getAbsolutePath());

        // 创建目录（如果不存在）
        if (!watermarkDir.exists()) {
            if (!watermarkDir.mkdirs()) {
                throw new FileOperationException("无法创建水印目录: " + watermarkDir.getAbsolutePath());
            }
            logger.debug("成功创建水印目录: {}", watermarkDir.getAbsolutePath());
        } else {
            logger.debug("水印目录已存在: {}", watermarkDir.getAbsolutePath());
        }

        return watermarkDir;
    }

    /**
     * 生成水印图片的输出文件路径
     *
     * @param watermarkDir 水印目录
     * @param originalImageFile 原始图片文件
     * @return 水印图片输出文件的File对象
     * @throws FileOperationException 文件操作异常
     */
    public File generateWatermarkFilePath(File watermarkDir, File originalImageFile) throws FileOperationException {
        if (watermarkDir == null || originalImageFile == null) {
            throw new IllegalArgumentException("水印目录和原始图片文件都不能为null");
        }

        // 验证输出目录
        try {
            validateOutputDirectory(watermarkDir);
        } catch (Exception e) {
            throw new FileOperationException("输出目录验证失败: " + e.getMessage(), e);
        }

        String originalFileName = originalImageFile.getName();
        String watermarkFileName = generateWatermarkFileName(originalFileName);
        return new File(watermarkDir, watermarkFileName);
    }

    /**
     * 生成水印图片文件名
     *
     * @param originalFileName 原始文件名
     * @return 水印图片文件名
     */
    private String generateWatermarkFileName(String originalFileName) {
        if (originalFileName == null || originalFileName.isEmpty()) {
            return "watermark_" + System.currentTimeMillis() + ".jpg";
        }

        int lastDotIndex = originalFileName.lastIndexOf('.');
        if (lastDotIndex > 0) {
            String name = originalFileName.substring(0, lastDotIndex);
            String extension = originalFileName.substring(lastDotIndex);
            return name + "_watermark" + extension;
        } else {
            return originalFileName + "_watermark.jpg";
        }
    }

    /**
     * 检查是否有写入权限
     *
     * @param directory 目录
     * @return 是否有写入权限
     */
    public boolean hasWritePermission(File directory) {
        if (directory == null) {
            return false;
        }

        // 检查目录是否存在
        if (!directory.exists()) {
            // 尝试创建父目录
            File parent = directory.getParentFile();
            if (parent != null && parent.exists()) {
                return parent.canWrite();
            }
            return false;
        }

        // 检查是否有写入权限
        return directory.canWrite();
    }

    /**
     * 验证输出目录是否有效
     *
     * @param directory 目录
     * @throws FileOperationException 文件操作异常
     */
    public void validateOutputDirectory(File directory) throws FileOperationException {
        if (directory == null) {
            throw new FileOperationException("输出目录不能为null");
        }

        if (!directory.exists()) {
            throw new FileOperationException("输出目录不存在: " + directory.getAbsolutePath());
        }

        if (!directory.isDirectory()) {
            throw new FileOperationException("输出路径不是目录: " + directory.getAbsolutePath());
        }

        if (!directory.canWrite()) {
            throw new FileOperationException("没有写入权限到目录: " + directory.getAbsolutePath());
        }
    }
}