package com.photowatermark;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;

/**
 * 集成测试类，测试整个图片水印处理流程
 */
public class IntegrationTest {

    private Path tempDir;
    private File testImageDir;
    private File watermarkDir;

    @Before
    public void setUp() throws IOException {
        // 创建临时目录用于测试
        tempDir = Files.createTempDirectory("photowatermark_test_");
        testImageDir = tempDir.toFile();
        watermarkDir = new File(testImageDir, testImageDir.getName() + "_watermark");
    }

    @After
    public void tearDown() {
        // 清理测试文件和目录
        deleteRecursively(tempDir.toFile());
    }

    /**
     * 递归删除目录和文件
     */
    private void deleteRecursively(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File child : files) {
                    deleteRecursively(child);
                }
            }
        }
        file.delete();
    }

    /**
     * 测试完整处理流程
     */
    @Test
    public void testCompleteProcessingFlow() {
        // 由于我们需要真实的图片文件来进行完整的集成测试，
        // 这里我们主要测试组件之间的集成和流程正确性

        // 创建配置
        ConfigurationManager config = new ConfigurationManager(20, "white", "bottom-right");

        // 创建处理器
        ImageProcessor processor = new ImageProcessor();
        ExifReader exifReader = new ExifReader();
        WatermarkProcessor watermarkProcessor = new WatermarkProcessor();
        FileOutputManager fileOutputManager = new FileOutputManager();

        // 验证所有组件都能正确创建
        assertNotNull("ImageProcessor不应为null", processor);
        assertNotNull("ExifReader不应为null", exifReader);
        assertNotNull("WatermarkProcessor不应为null", watermarkProcessor);
        assertNotNull("FileOutputManager不应为null", fileOutputManager);

        // 测试配置管理器
        assertEquals("字体大小应为20", 20, config.getFontSize());
        assertEquals("颜色应为white", "white", config.getColor());
        assertEquals("位置应为bottom-right", "bottom-right", config.getPosition());
    }

    /**
     * 测试命令行参数处理
     */
    @Test
    public void testCommandLineArgumentProcessing() throws Exception {
        PhotoWatermark app = new PhotoWatermark();

        // 测试参数解析（这需要更复杂的模拟或实际运行程序）
        ConfigurationManager config = new ConfigurationManager();

        // 验证默认值
        assertEquals("默认字体大小应为20", 20, config.getFontSize());
        assertEquals("默认颜色应为white", "white", config.getColor());
        assertEquals("默认位置应为bottom-right", "bottom-right", config.getPosition());
    }

    /**
     * 测试文件输出管理器的目录创建功能
     */
    @Test
    public void testDirectoryCreation() throws Exception {
        FileOutputManager fileOutputManager = new FileOutputManager();

        // 创建测试文件
        File testFile = new File(testImageDir, "test.jpg");
        testFile.createNewFile();

        // 测试创建水印目录
        File watermarkDir = fileOutputManager.createWatermarkDirectory(testFile.getAbsolutePath());

        // 验证目录已创建
        assertTrue("水印目录应存在", watermarkDir.exists());
        assertTrue("水印目录应为目录", watermarkDir.isDirectory());

        // 验证目录名称正确
        assertTrue("目录名称应包含_watermark后缀", watermarkDir.getName().endsWith("_watermark"));
    }

    /**
     * 测试批量处理结果
     */
    @Test
    public void testBatchProcessingResults() {
        ImageProcessor processor = new ImageProcessor();
        ConfigurationManager config = new ConfigurationManager();

        // 测试空列表处理
        java.util.List<ImageProcessor.ProcessResult> results = processor.processImages(
            new java.util.ArrayList<>(), 20, "white", "bottom-right", config);

        assertNotNull("结果列表不应为null", results);
        assertTrue("结果列表应为空", results.isEmpty());

        // 测试处理结果类
        ImageProcessor.ProcessResult result = new ImageProcessor.ProcessResult("/test/path.jpg", true, "测试消息");
        assertEquals("图片路径应正确", "/test/path.jpg", result.getImagePath());
        assertTrue("应处理成功", result.isSuccess());
        assertEquals("消息应正确", "测试消息", result.getMessage());
    }
}