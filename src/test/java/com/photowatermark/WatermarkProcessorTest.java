package com.photowatermark;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

/**
 * WatermarkProcessor类的单元测试
 */
public class WatermarkProcessorTest {

    private WatermarkProcessor watermarkProcessor;

    @Before
    public void setUp() {
        watermarkProcessor = new WatermarkProcessor();
    }

    /**
     * 测试构造函数
     */
    @Test
    public void testConstructor() {
        assertNotNull("WatermarkProcessor实例不应为null", watermarkProcessor);
    }

    /**
     * 测试使用null原始图片的情况
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAddTextWatermarkWithNullOriginalImage() throws IOException {
        File outputFile = new File("output.jpg");
        watermarkProcessor.addTextWatermark(null, "Test Watermark", 20, "white", "bottom-right", outputFile);
    }

    /**
     * 测试使用null水印文本的情况
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAddTextWatermarkWithNullWatermarkText() throws IOException {
        File originalImage = new File("input.jpg");
        File outputFile = new File("output.jpg");
        watermarkProcessor.addTextWatermark(originalImage, null, 20, "white", "bottom-right", outputFile);
    }

    /**
     * 测试使用空水印文本的情况
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAddTextWatermarkWithEmptyWatermarkText() throws IOException {
        File originalImage = new File("input.jpg");
        File outputFile = new File("output.jpg");
        watermarkProcessor.addTextWatermark(originalImage, "", 20, "white", "bottom-right", outputFile);
    }

    /**
     * 测试使用null输出文件的情况
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAddTextWatermarkWithNullOutputFile() throws IOException {
        File originalImage = new File("input.jpg");
        watermarkProcessor.addTextWatermark(originalImage, "Test Watermark", 20, "white", "bottom-right", null);
    }

    // TODO: 添加更多测试用例，包括使用真实图片文件的测试
}