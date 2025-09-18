package com.photowatermark;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * ConfigurationManager类的单元测试
 */
public class ConfigurationManagerTest {

    /**
     * 测试默认构造函数
     */
    @Test
    public void testDefaultConstructor() {
        ConfigurationManager config = new ConfigurationManager();

        assertEquals("默认字体大小应为20", 20, config.getFontSize());
        assertEquals("默认颜色应为white", "white", config.getColor());
        assertEquals("默认位置应为bottom-right", "bottom-right", config.getPosition());
    }

    /**
     * 测试带参数的构造函数
     */
    @Test
    public void testParameterizedConstructor() {
        ConfigurationManager config = new ConfigurationManager(30, "red", "top-left");

        assertEquals("字体大小应为30", 30, config.getFontSize());
        assertEquals("颜色应为red", "red", config.getColor());
        assertEquals("位置应为top-left", "top-left", config.getPosition());
    }

    /**
     * 测试Getter和Setter方法
     */
    @Test
    public void testGettersAndSetters() {
        ConfigurationManager config = new ConfigurationManager();

        // 测试Setter方法
        config.setFontSize(25);
        config.setColor("blue");
        config.setPosition("center");

        // 测试Getter方法
        assertEquals("字体大小应为25", 25, config.getFontSize());
        assertEquals("颜色应为blue", "blue", config.getColor());
        assertEquals("位置应为center", "center", config.getPosition());
    }

    /**
     * 测试toString方法
     */
    @Test
    public void testToString() {
        ConfigurationManager config = new ConfigurationManager(25, "blue", "center");
        String toStringResult = config.toString();

        assertNotNull("toString结果不应为null", toStringResult);
        assertTrue("toString结果应包含类名", toStringResult.contains("ConfigurationManager"));
        assertTrue("toString结果应包含字体大小", toStringResult.contains("fontSize=25"));
        assertTrue("toString结果应包含颜色", toStringResult.contains("color='blue'"));
        assertTrue("toString结果应包含位置", toStringResult.contains("position='center'"));
    }
}