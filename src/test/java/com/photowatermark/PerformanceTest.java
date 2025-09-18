package com.photowatermark;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.Assert.*;

/**
 * 性能测试类，测试图片处理的性能表现
 */
public class PerformanceTest {

    private Path tempDir;
    private File testImageDir;

    @Before
    public void setUp() throws IOException {
        // 创建临时目录用于测试
        tempDir = Files.createTempDirectory("photowatermark_perf_test_");
        testImageDir = tempDir.toFile();
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
     * 测试ExifReader的性能
     */
    @Test
    public void testExifReaderPerformance() {
        ExifReader exifReader = new ExifReader();

        // 验证组件创建
        assertNotNull("ExifReader不应为null", exifReader);

        // 在实际项目中，我们会使用真实的图片文件进行性能测试
        // 这里主要测试组件初始化性能
        long startTime = System.currentTimeMillis();
        ExifReader reader = new ExifReader();
        long endTime = System.currentTimeMillis();

        long initializationTime = endTime - startTime;
        assertTrue("ExifReader初始化应在合理时间内完成", initializationTime < 100);
    }

    /**
     * 测试WatermarkProcessor的性能
     */
    @Test
    public void testWatermarkProcessorPerformance() {
        WatermarkProcessor watermarkProcessor = new WatermarkProcessor();

        // 验证组件创建
        assertNotNull("WatermarkProcessor不应为null", watermarkProcessor);

        // 测试组件初始化性能
        long startTime = System.currentTimeMillis();
        WatermarkProcessor processor = new WatermarkProcessor();
        long endTime = System.currentTimeMillis();

        long initializationTime = endTime - startTime;
        assertTrue("WatermarkProcessor初始化应在合理时间内完成", initializationTime < 100);
    }

    /**
     * 测试ImageProcessor的性能
     */
    @Test
    public void testImageProcessorPerformance() {
        ImageProcessor imageProcessor = new ImageProcessor();

        // 验证组件创建
        assertNotNull("ImageProcessor不应为null", imageProcessor);

        // 测试组件初始化性能
        long startTime = System.currentTimeMillis();
        ImageProcessor processor = new ImageProcessor();
        long endTime = System.currentTimeMillis();

        long initializationTime = endTime - startTime;
        assertTrue("ImageProcessor初始化应在合理时间内完成", initializationTime < 100);
    }

    /**
     * 测试ConfigurationManager的性能
     */
    @Test
    public void testConfigurationManagerPerformance() {
        // 测试组件初始化性能
        long startTime = System.currentTimeMillis();
        ConfigurationManager config = new ConfigurationManager();
        long endTime = System.currentTimeMillis();

        long initializationTime = endTime - startTime;
        assertTrue("ConfigurationManager初始化应在合理时间内完成", initializationTime < 50);

        // 测试配置设置性能
        startTime = System.currentTimeMillis();
        config.setFontSize(30);
        config.setColor("red");
        config.setPosition("top-left");
        endTime = System.currentTimeMillis();

        long configTime = endTime - startTime;
        assertTrue("配置设置应在合理时间内完成", configTime < 50);
    }

    /**
     * 测试FileOutputManager的性能
     */
    @Test
    public void testFileOutputManagerPerformance() {
        FileOutputManager fileOutputManager = new FileOutputManager();

        // 验证组件创建
        assertNotNull("FileOutputManager不应为null", fileOutputManager);

        // 测试组件初始化性能
        long startTime = System.currentTimeMillis();
        FileOutputManager manager = new FileOutputManager();
        long endTime = System.currentTimeMillis();

        long initializationTime = endTime - startTime;
        assertTrue("FileOutputManager初始化应在合理时间内完成", initializationTime < 100);
    }
}