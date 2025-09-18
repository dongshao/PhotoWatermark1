package com.photowatermark;

import com.photowatermark.exception.FileOperationException;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

/**
 * FileOutputManager类的单元测试
 */
public class FileOutputManagerTest {

    private FileOutputManager fileOutputManager;

    @Before
    public void setUp() {
        fileOutputManager = new FileOutputManager();
    }

    /**
     * 测试构造函数
     */
    @Test
    public void testConstructor() {
        assertNotNull("FileOutputManager实例不应为null", fileOutputManager);
    }

    /**
     * 测试使用null路径创建水印目录的情况
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCreateWatermarkDirectoryWithNullPath() throws FileOperationException {
        fileOutputManager.createWatermarkDirectory(null);
    }

    /**
     * 测试使用空路径创建水印目录的情况
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCreateWatermarkDirectoryWithEmptyPath() throws FileOperationException {
        fileOutputManager.createWatermarkDirectory("");
    }

    /**
     * 测试生成水印文件路径
     */
    @Test
    public void testGenerateWatermarkFilePath() throws FileOperationException {
        // 创建临时目录用于测试
        File tempDir = new File(System.getProperty("java.io.tmpdir"));
        File watermarkDir = new File(tempDir, "test_watermark");
        watermarkDir.mkdirs();

        File originalImageFile = new File("/tmp/original.jpg");

        File watermarkFile = fileOutputManager.generateWatermarkFilePath(watermarkDir, originalImageFile);

        assertNotNull("水印文件不应为null", watermarkFile);
        assertTrue("水印文件路径应包含水印目录", watermarkFile.getPath().contains("test_watermark"));
    }

    /**
     * 测试使用null参数生成水印文件路径的情况
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGenerateWatermarkFilePathWithNullParameters() throws FileOperationException {
        fileOutputManager.generateWatermarkFilePath(null, null);
    }

    /**
     * 测试检查写入权限
     */
    @Test
    public void testHasWritePermission() {
        // 测试null目录
        assertFalse("null目录应返回false", fileOutputManager.hasWritePermission(null));

        // 测试当前目录
        File currentDir = new File(".");
        boolean hasPermission = fileOutputManager.hasWritePermission(currentDir);
        // 在大多数情况下，当前目录应该有写入权限
        assertTrue("当前目录应该有写入权限", hasPermission);
    }

    /**
     * 测试验证输出目录
     */
    @Test(expected = FileOperationException.class)
    public void testValidateOutputDirectoryWithNullDirectory() throws FileOperationException {
        fileOutputManager.validateOutputDirectory(null);
    }
}