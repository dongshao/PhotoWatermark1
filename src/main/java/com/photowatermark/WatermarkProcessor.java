package com.photowatermark;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * 水印处理器类，用于在图片上添加文本水印
 */
public class WatermarkProcessor {
    private static final Logger logger = LoggerFactory.getLogger(WatermarkProcessor.class);

    // 默认字体名称
    private static final String DEFAULT_FONT_NAME = "Arial";

    // 默认字体样式
    private static final int DEFAULT_FONT_STYLE = Font.BOLD;

    /**
     * 在图片上添加文本水印
     *
     * @param originalImage 原始图片文件
     * @param watermarkText 水印文本
     * @param fontSize 字体大小
     * @param color 字体颜色
     * @param position 水印位置
     * @param outputFile 输出文件
     * @throws IOException IO异常
     */
    public void addTextWatermark(File originalImage, String watermarkText, int fontSize,
                                String color, String position, File outputFile) throws IOException {
        if (originalImage == null || !originalImage.exists()) {
            throw new IllegalArgumentException("原始图片文件不存在: " + (originalImage != null ? originalImage.getPath() : "null"));
        }

        if (watermarkText == null || watermarkText.isEmpty()) {
            throw new IllegalArgumentException("水印文本不能为空");
        }

        if (outputFile == null) {
            throw new IllegalArgumentException("输出文件不能为null");
        }

        logger.debug("开始处理图片水印: {}", originalImage.getPath());

        try {
            // 使用Thumbnailator处理图片
            Thumbnails.Builder<File> builder = Thumbnails.of(originalImage);

            // 设置水印位置
            Positions watermarkPosition = parsePosition(position);

            // 设置字体
            Font font = new Font(DEFAULT_FONT_NAME, DEFAULT_FONT_STYLE, fontSize);

            // 设置颜色
            Color textColor = parseColor(color);

            // 添加水印
            builder.watermark(watermarkPosition, createWatermarkImage(watermarkText, font, textColor), 0.5f);

            // 输出图片
            builder.scale(1.0).toFile(outputFile);

            logger.debug("水印添加成功: {} -> {}", originalImage.getPath(), outputFile.getPath());
        } catch (Exception e) {
            logger.error("添加水印时发生错误: {}", e.getMessage(), e);
            throw new IOException("添加水印时发生错误: " + e.getMessage(), e);
        }
    }

    /**
     * 解析水印位置参数
     *
     * @param position 位置字符串
     * @return Positions枚举值
     */
    private Positions parsePosition(String position) {
        if (position == null || position.isEmpty()) {
            return Positions.BOTTOM_RIGHT;
        }

        switch (position.toLowerCase()) {
            case "top-left":
                return Positions.TOP_LEFT;
            case "top-center":
            case "top":
                return Positions.TOP_CENTER;
            case "top-right":
                return Positions.TOP_RIGHT;
            case "center-left":
            case "left":
                return Positions.CENTER_LEFT;
            case "center":
                return Positions.CENTER;
            case "center-right":
            case "right":
                return Positions.CENTER_RIGHT;
            case "bottom-left":
                return Positions.BOTTOM_LEFT;
            case "bottom-center":
            case "bottom":
                return Positions.BOTTOM_CENTER;
            case "bottom-right":
            default:
                return Positions.BOTTOM_RIGHT;
        }
    }

    /**
     * 解析颜色参数
     *
     * @param color 颜色字符串
     * @return Color对象
     */
    private Color parseColor(String color) {
        if (color == null || color.isEmpty()) {
            return Color.WHITE;
        }

        switch (color.toLowerCase()) {
            case "black":
                return Color.BLACK;
            case "blue":
                return Color.BLUE;
            case "cyan":
                return Color.CYAN;
            case "darkgray":
            case "darkgrey":
                return Color.DARK_GRAY;
            case "gray":
            case "grey":
                return Color.GRAY;
            case "green":
                return Color.GREEN;
            case "lightgray":
            case "lightgrey":
                return Color.LIGHT_GRAY;
            case "magenta":
                return Color.MAGENTA;
            case "orange":
                return Color.ORANGE;
            case "pink":
                return Color.PINK;
            case "red":
                return Color.RED;
            case "white":
                return Color.WHITE;
            case "yellow":
                return Color.YELLOW;
            default:
                // 尝试解析十六进制颜色值
                try {
                    if (color.startsWith("#") && color.length() == 7) {
                        int r = Integer.parseInt(color.substring(1, 3), 16);
                        int g = Integer.parseInt(color.substring(3, 5), 16);
                        int b = Integer.parseInt(color.substring(5, 7), 16);
                        return new Color(r, g, b);
                    }
                } catch (NumberFormatException e) {
                    logger.warn("无法解析颜色值: {}, 使用默认颜色白色", color);
                }
                return Color.WHITE;
        }
    }

    /**
     * 创建水印图片
     *
     * @param text 水印文本
     * @param font 字体
     * @param color 颜色
     * @return 水印图片
     */
    private BufferedImage createWatermarkImage(String text, Font font, Color color) {
        // 创建一个临时的图形上下文来计算文本大小
        BufferedImage tempImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D tempGraphics = tempImage.createGraphics();
        tempGraphics.setFont(font);
        FontMetrics fontMetrics = tempGraphics.getFontMetrics();

        int width = fontMetrics.stringWidth(text) + 20;  // 添加一些边距
        int height = fontMetrics.getHeight() + 20;       // 添加一些边距

        tempGraphics.dispose();

        // 创建实际的水印图片
        BufferedImage watermarkImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = watermarkImage.createGraphics();

        // 设置抗锯齿
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // 设置字体和颜色
        graphics.setFont(font);
        graphics.setColor(color);

        // 绘制文本
        graphics.drawString(text, 10, fontMetrics.getAscent() + 10);

        graphics.dispose();

        return watermarkImage;
    }
}