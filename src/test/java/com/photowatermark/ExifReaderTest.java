package com.photowatermark;

import com.photowatermark.exception.ExifReadException;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * ExifReader类的单元测试
 */
public class ExifReaderTest {

    private ExifReader exifReader;

    @Before
    public void setUp() {
        exifReader = new ExifReader();
    }

    /**
     * 测试构造函数
     */
    @Test
    public void testConstructor() {
        assertNotNull("ExifReader实例不应为null", exifReader);
    }

    /**
     * 测试读取null文件的情况
     */
    @Test(expected = ExifReadException.class)
    public void testReadCaptureDateWithNullFile() throws ExifReadException {
        exifReader.readCaptureDate(null);
    }

    /**
     * 测试读取不存在文件的情况
     */
    @Test(expected = ExifReadException.class)
    public void testReadCaptureDateWithNonExistentFile() throws ExifReadException {
        File nonExistentFile = new File("non_existent_file.jpg");
        exifReader.readCaptureDate(nonExistentFile);
    }

    /**
     * 测试读取拍摄时间字符串
     */
    @Test
    public void testReadCaptureDateAsString() throws ExifReadException {
        // 使用null文件测试异常情况
        try {
            exifReader.readCaptureDateAsString(null);
            fail("应该抛出ExifReadException异常");
        } catch (ExifReadException e) {
            // 预期的异常
        }
    }

    // TODO: 添加更多测试用例，包括使用真实图片文件的测试
}