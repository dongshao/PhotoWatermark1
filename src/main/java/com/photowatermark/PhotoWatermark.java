package com.photowatermark;

import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 图片水印命令行程序主类
 * 能够自动读取图片的EXIF信息中的拍摄时间，并将其作为文本水印添加到图片上
 */
public class PhotoWatermark {
    private static final Logger logger = LoggerFactory.getLogger(PhotoWatermark.class);

    // 默认参数值
    private static final int DEFAULT_FONT_SIZE = 20;
    private static final String DEFAULT_COLOR = "white";
    private static final String DEFAULT_POSITION = "bottom-right";

    // 命令行选项
    private static final String OPTION_FONT_SIZE = "fontSize";
    private static final String OPTION_COLOR = "color";
    private static final String OPTION_POSITION = "position";
    private static final String OPTION_HELP = "help";

    public static void main(String[] args) {
        PhotoWatermark app = new PhotoWatermark();
        try {
            app.run(args);
        } catch (Exception e) {
            logger.error("程序执行出错: {}", e.getMessage(), e);
            System.err.println("错误: " + e.getMessage());
            System.exit(1);
        }
    }

    /**
     * 运行程序主逻辑
     * @param args 命令行参数
     * @throws ParseException 参数解析异常
     */
    public void run(String[] args) throws ParseException {
        // 解析命令行参数
        CommandLine cmd = parseCommandLine(args);

        // 如果请求帮助信息，则显示帮助并退出
        if (cmd.hasOption(OPTION_HELP)) {
            printHelp();
            return;
        }

        // 获取图片路径参数
        String[] imagePaths = cmd.getArgs();
        if (imagePaths.length == 0) {
            System.err.println("错误: 请提供图片文件路径");
            printHelp();
            return;
        }

        // 获取选项参数
        int fontSize = getFontSize(cmd);
        String color = getColor(cmd);
        String position = getPosition(cmd);

        // 创建配置管理器
        ConfigurationManager config = new ConfigurationManager(fontSize, color, position);

        // 输出参数信息（用于调试）
        logger.info("程序启动参数:");
        logger.info("  图片路径: {}", String.join(", ", imagePaths));
        logger.info("  字体大小: {}", fontSize);
        logger.info("  字体颜色: {}", color);
        logger.info("  水印位置: {}", position);

        System.out.println("图片水印程序启动...");
        System.out.println("图片路径: " + String.join(", ", imagePaths));
        System.out.println("字体大小: " + fontSize);
        System.out.println("字体颜色: " + color);
        System.out.println("水印位置: " + position);

        // 处理图片
        processImages(imagePaths, config);
    }

    /**
     * 处理图片
     * @param imagePaths 图片路径数组
     * @param config 配置管理器
     */
    private void processImages(String[] imagePaths, ConfigurationManager config) {
        ImageProcessor processor = new ImageProcessor();

        // 将数组转换为列表
        List<String> imagePathList = Arrays.asList(imagePaths);

        // 过滤出存在的图片文件
        List<String> existingImages = imagePathList.stream()
                .filter(path -> new File(path).exists())
                .collect(Collectors.toList());

        if (existingImages.isEmpty()) {
            System.err.println("错误: 没有找到任何存在的图片文件");
            return;
        }

        System.out.println("找到 " + existingImages.size() + " 个图片文件");

        // 批量处理图片
        List<ImageProcessor.ProcessResult> results = processor.processImages(
                existingImages,
                config.getFontSize(),
                config.getColor(),
                config.getPosition(),
                config);

        // 输出处理结果
        System.out.println("\n处理结果:");
        for (ImageProcessor.ProcessResult result : results) {
            if (result.isSuccess()) {
                System.out.println("✓ " + result.getImagePath() + " - 处理成功");
            } else {
                System.err.println("✗ " + result.getImagePath() + " - 处理失败: " + result.getMessage());
            }
        }

        long successCount = results.stream().filter(r -> r.isSuccess()).count();
        long failureCount = results.size() - successCount;

        System.out.println("\n总结:");
        System.out.println("成功处理: " + successCount + " 个文件");
        System.out.println("处理失败: " + failureCount + " 个文件");
    }

    /**
     * 解析命令行参数
     * @param args 命令行参数
     * @return 解析后的命令行对象
     * @throws ParseException 参数解析异常
     */
    private CommandLine parseCommandLine(String[] args) throws ParseException {
        Options options = createOptions();
        CommandLineParser parser = new DefaultParser();
        return parser.parse(options, args);
    }

    /**
     * 创建命令行选项
     * @return 命令行选项对象
     */
    private Options createOptions() {
        Options options = new Options();

        options.addOption(Option.builder()
                .longOpt(OPTION_FONT_SIZE)
                .hasArg()
                .argName("size")
                .desc("字体大小 (默认: " + DEFAULT_FONT_SIZE + ")")
                .build());

        options.addOption(Option.builder()
                .longOpt(OPTION_COLOR)
                .hasArg()
                .argName("color")
                .desc("字体颜色 (默认: " + DEFAULT_COLOR + ")")
                .build());

        options.addOption(Option.builder()
                .longOpt(OPTION_POSITION)
                .hasArg()
                .argName("pos")
                .desc("水印位置 (默认: " + DEFAULT_POSITION + ") 可选值: top-left, center, bottom-right")
                .build());

        options.addOption(Option.builder()
                .longOpt(OPTION_HELP)
                .desc("显示帮助信息")
                .build());

        return options;
    }

    /**
     * 获取字体大小参数
     * @param cmd 命令行对象
     * @return 字体大小
     */
    private int getFontSize(CommandLine cmd) {
        String fontSizeStr = cmd.getOptionValue(OPTION_FONT_SIZE);
        if (fontSizeStr != null) {
            try {
                return Integer.parseInt(fontSizeStr);
            } catch (NumberFormatException e) {
                logger.warn("无效的字体大小值: {}, 使用默认值: {}", fontSizeStr, DEFAULT_FONT_SIZE);
                System.err.println("警告: 无效的字体大小值: " + fontSizeStr + ", 使用默认值: " + DEFAULT_FONT_SIZE);
            }
        }
        return DEFAULT_FONT_SIZE;
    }

    /**
     * 获取字体颜色参数
     * @param cmd 命令行对象
     * @return 字体颜色
     */
    private String getColor(CommandLine cmd) {
        String color = cmd.getOptionValue(OPTION_COLOR);
        return color != null ? color : DEFAULT_COLOR;
    }

    /**
     * 获取水印位置参数
     * @param cmd 命令行对象
     * @return 水印位置
     */
    private String getPosition(CommandLine cmd) {
        String position = cmd.getOptionValue(OPTION_POSITION);
        return position != null ? position : DEFAULT_POSITION;
    }

    /**
     * 显示帮助信息
     */
    private void printHelp() {
        Options options = createOptions();
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("PhotoWatermark [options] <image_path>", options);
    }
}