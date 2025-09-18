package com.photowatermark.exception;

/**
 * EXIF信息读取异常类
 */
public class ExifReadException extends Exception {

    public ExifReadException(String message) {
        super(message);
    }

    public ExifReadException(String message, Throwable cause) {
        super(message, cause);
    }
}