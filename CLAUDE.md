# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## 项目概述
这是一个Java命令行程序，用于给图片添加水印。程序会读取图片的EXIF信息中的拍摄时间，并将其作为文本水印添加到图片上。

## 项目结构
目前项目处于初始阶段，包含以下文件：
- PRD.md: 产品需求文档，详细描述了功能需求和技术要求
- README.md: 项目基本介绍
- LICENSE: 开源许可证

## 技术要求
- 开发语言: Java
- 依赖库:
  - 图片处理库（如Thumbnailator或Java Image I/O）
  - EXIF信息读取库（如metadata-extractor）

## 核心功能
1. 图片处理: 读取图片文件，提取EXIF信息中的拍摄时间
2. 水印设置: 支持设置字体大小、颜色和位置（左上角、居中、右下角）
3. 文件输出: 将添加水印后的图片保存到新目录

## 命令行接口
程序应支持以下命令行参数：
```
Usage: PhotoWatermark [options] <image_path>
Options:
  -fontSize <size>     字体大小 (默认: 20)
  -color <color>       字体颜色 (默认: white)
  -position <pos>      水印位置 (默认: bottom-right)
                       可选值: top-left, center, bottom-right
  -help                显示帮助信息
```

## 开发指导原则
1. 遵循PRD文档中的功能需求和技术要求
2. 实现良好的错误处理机制
3. 编写单元测试和集成测试
4. 保持代码简洁、可读性高
5. 遵循Java编码规范