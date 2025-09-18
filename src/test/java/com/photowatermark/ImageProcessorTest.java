package com.photowatermark;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * ImageProcessor类的单元测试
 */
public class ImageProcessorTest {

    private ImageProcessor imageProcessor;

    @Before
    public void setUp() {
        imageProcessor = new ImageProcessor();
    }

    /**
     * 测试构造函数
     */
    @Test
    public void testConstructor() {
        assertNotNull("ImageProcessor实例不应为null", imageProcessor);
    }

    /**
     * 测试使用null路径处理图片的情况
     */
    @Test(expected = IllegalArgumentException.class)
    public void testProcessImageWithNullPath() throws Exception {
        ConfigurationManager config = new ConfigurationManager();
        imageProcessor.processImage(null, 20, "white", "bottom-right", config);
    }

    /**
     * 测试使用空路径处理图片的情况
     */
    @Test(expected = IllegalArgumentException.class)
    public void testProcessImageWithEmptyPath() throws Exception {
        ConfigurationManager config = new ConfigurationManager();
        imageProcessor.processImage("", 20, "white", "bottom-right", config);
    }

    /**
     * 测试批量处理空列表
     */
    @Test
    public void testProcessImagesWithEmptyList() {
        ConfigurationManager config = new ConfigurationManager();
        List<String> imagePaths = new ArrayList<>();
        List<ImageProcessor.ProcessResult> results = imageProcessor.processImages(imagePaths, 20, "white", "bottom-right", config);

        assertNotNull("处理结果不应为null", results);
        assertTrue("处理结果应为空", results.isEmpty());
    }

    /**
     * 测试处理结果类
     */
    @Test
    public void testProcessResult() {
        ImageProcessor.ProcessResult result = new ImageProcessor.ProcessResult("/path/to/image.jpg", true, "处理成功");

        assertEquals("图片路径应正确", "/path/to/image.jpg", result.getImagePath());
        assertTrue("应处理成功", result.isSuccess());
        assertEquals("消息应正确", "处理成功", result.getMessage());
    }
}