package com.photowatermark.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期格式化工具类，用于将Date对象格式化为水印文本
 */
public class DateFormatter {
    private static final Logger logger = LoggerFactory.getLogger(DateFormatter.class);

    // 默认日期格式
    private static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * 将日期格式化为默认格式的字符串
     *
     * @param date 日期对象
     * @return 格式化后的日期字符串，如果日期为null则返回空字符串
     */
    public static String format(Date date) {
        return format(date, DEFAULT_DATE_PATTERN);
    }

    /**
     * 将日期格式化为指定格式的字符串
     *
     * @param date 日期对象
     * @param pattern 日期格式模式
     * @return 格式化后的日期字符串，如果日期为null则返回空字符串
     */
    public static String format(Date date, String pattern) {
        if (date == null) {
            logger.warn("日期对象为null，返回空字符串");
            return "";
        }

        if (pattern == null || pattern.isEmpty()) {
            pattern = DEFAULT_DATE_PATTERN;
        }

        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            String formattedDate = sdf.format(date);
            logger.debug("日期格式化成功: {} -> {}", date, formattedDate);
            return formattedDate;
        } catch (Exception e) {
            logger.error("日期格式化失败: {}", e.getMessage(), e);
            return date.toString(); // 返回默认的toString结果作为备选
        }
    }
}