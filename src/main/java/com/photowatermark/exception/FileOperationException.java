package com.photowatermark.exception;

/**
 * 文件操作异常类
 */
public class FileOperationException extends Exception {

    public FileOperationException(String message) {
        super(message);
    }

    public FileOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}