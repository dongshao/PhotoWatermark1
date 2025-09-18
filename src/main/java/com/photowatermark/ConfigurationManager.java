package com.photowatermark;

/**
 * 配置管理类，用于存储和管理程序配置参数
 */
public class ConfigurationManager {
    private int fontSize;
    private String color;
    private String position;

    // 默认构造函数，使用默认值
    public ConfigurationManager() {
        this.fontSize = 20;
        this.color = "white";
        this.position = "bottom-right";
    }

    // 带参数的构造函数
    public ConfigurationManager(int fontSize, String color, String position) {
        this.fontSize = fontSize;
        this.color = color;
        this.position = position;
    }

    // Getter和Setter方法
    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "ConfigurationManager{" +
                "fontSize=" + fontSize +
                ", color='" + color + '\'' +
                ", position='" + position + '\'' +
                '}';
    }
}