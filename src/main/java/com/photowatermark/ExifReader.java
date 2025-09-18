package com.photowatermark;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.photowatermark.exception.ExifReadException;
import com.photowatermark.util.DateFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * EXIF信息读取类，用于从图片文件中提取拍摄时间等EXIF信息
 */
public class ExifReader {
    private static final Logger logger = LoggerFactory.getLogger(ExifReader.class);

    /**
     * 从图片文件中读取拍摄时间
     *
     * @param imageFile 图片文件
     * @return 拍摄时间的格式化字符串，如果无法读取则返回null
     * @throws ExifReadException EXIF读取异常
     */
    public String readCaptureDateAsString(File imageFile) throws ExifReadException {
        Date captureDate = readCaptureDate(imageFile);
        return captureDate != null ? DateFormatter.format(captureDate) : null;
    }

    /**
     * 从图片文件中读取拍摄时间
     *
     * @param imageFile 图片文件
     * @return 拍摄时间，如果无法读取则返回null
     * @throws ExifReadException EXIF读取异常
     */
    public Date readCaptureDate(File imageFile) throws ExifReadException {
        if (imageFile == null) {
            throw new ExifReadException("图片文件不能为null");
        }

        if (!imageFile.exists()) {
            throw new ExifReadException("图片文件不存在: " + imageFile.getPath());
        }

        logger.debug("正在读取图片文件的EXIF信息: {}", imageFile.getPath());

        try {
            // 读取图片元数据
            Metadata metadata = ImageMetadataReader.readMetadata(imageFile);

            // 查找EXIF子IFD目录
            ExifSubIFDDirectory directory = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
            if (directory == null) {
                logger.warn("图片文件中未找到EXIF信息: {}", imageFile.getPath());
                return null;
            }

            // 获取拍摄时间
            Date captureDate = directory.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL);
            if (captureDate == null) {
                captureDate = directory.getDate(ExifSubIFDDirectory.TAG_DATETIME);
            }

            if (captureDate != null) {
                logger.debug("成功读取拍摄时间: {} from {}", captureDate, imageFile.getPath());
            } else {
                logger.warn("图片文件中未找到拍摄时间信息: {}", imageFile.getPath());
            }

            return captureDate;
        } catch (IOException e) {
            throw new ExifReadException("读取图片文件时发生IO异常: " + imageFile.getPath(), e);
        } catch (ImageProcessingException e) {
            throw new ExifReadException("处理图片文件时发生异常: " + imageFile.getPath(), e);
        } catch (Exception e) {
            throw new ExifReadException("读取EXIF信息时发生未知异常: " + imageFile.getPath(), e);
        }
    }

    /**
     * 从图片文件中读取所有EXIF信息并打印到日志（用于调试）
     *
     * @param imageFile 图片文件
     * @throws ExifReadException EXIF读取异常
     */
    public void printAllExifInfo(File imageFile) throws ExifReadException {
        if (imageFile == null) {
            throw new ExifReadException("图片文件不能为null");
        }

        if (!imageFile.exists()) {
            throw new ExifReadException("图片文件不存在: " + imageFile.getPath());
        }

        logger.info("=== 图片EXIF信息 ===");
        logger.info("文件路径: {}", imageFile.getPath());

        try {
            Metadata metadata = ImageMetadataReader.readMetadata(imageFile);

            // 遍历所有目录和标签
            for (Directory directory : metadata.getDirectories()) {
                for (Tag tag : directory.getTags()) {
                    logger.info("{} - {}: {}", directory.getName(), tag.getTagName(), tag.getDescription());
                }

                // 检查是否有错误
                if (directory.hasErrors()) {
                    for (String error : directory.getErrors()) {
                        logger.warn("EXIF目录错误: {}", error);
                    }
                }
            }
        } catch (IOException e) {
            throw new ExifReadException("读取图片文件时发生IO异常: " + imageFile.getPath(), e);
        } catch (ImageProcessingException e) {
            throw new ExifReadException("处理图片文件时发生异常: " + imageFile.getPath(), e);
        } catch (Exception e) {
            throw new ExifReadException("读取EXIF信息时发生未知异常: " + imageFile.getPath(), e);
        }
    }
}