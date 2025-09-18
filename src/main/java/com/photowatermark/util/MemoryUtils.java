package com.photowatermark.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 内存工具类，用于监控和优化内存使用
 */
public class MemoryUtils {
    private static final Logger logger = LoggerFactory.getLogger(MemoryUtils.class);

    /**
     * 获取当前内存使用情况
     *
     * @return 内存使用情况的字符串描述
     */
    public static String getMemoryUsage() {
        Runtime runtime = Runtime.getRuntime();
        long totalMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();
        long usedMemory = totalMemory - freeMemory;
        long maxMemory = runtime.maxMemory();

        return String.format("内存使用情况 - 已使用: %d MB, 总计: %d MB, 最大: %d MB",
                usedMemory / (1024 * 1024),
                totalMemory / (1024 * 1024),
                maxMemory / (1024 * 1024));
    }

    /**
     * 打印当前内存使用情况到日志
     */
    public static void logMemoryUsage() {
        logger.debug(getMemoryUsage());
    }

    /**
     * 建议垃圾回收
     */
    public static void suggestGarbageCollection() {
        System.gc();
        logger.debug("已建议垃圾回收");
    }

    /**
     * 检查内存是否充足
     *
     * @param requiredMemory 所需内存量（字节）
     * @return 是否内存充足
     */
    public static boolean isMemorySufficient(long requiredMemory) {
        Runtime runtime = Runtime.getRuntime();
        long freeMemory = runtime.freeMemory();
        long maxMemory = runtime.maxMemory();
        long totalMemory = runtime.totalMemory();
        long availableMemory = freeMemory + (maxMemory - totalMemory);

        return availableMemory > requiredMemory;
    }
}