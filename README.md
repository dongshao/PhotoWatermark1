# PhotoWatermark

图片水印命令行程序，能够自动读取图片的EXIF信息中的拍摄时间，并将其作为文本水印添加到图片上。

## 功能特点

- 自动读取图片EXIF信息中的拍摄时间
- 将拍摄时间作为文本水印添加到图片上
- 支持自定义水印字体大小、颜色和位置
- 支持批量处理图片文件
- 跨平台支持（Windows、macOS、Linux）

## 安装要求

- Java 11 或更高版本
- Maven 3.6 或更高版本

## 构建项目

```bash
mvn clean package
```

## 使用方法

```bash
java -jar target/photo-watermark-1.0.0.jar [options] <image_path>
```

### 命令行参数

```
Usage: PhotoWatermark [options] <image_path>
  -color <color>         字体颜色 (默认: white)
  -fontSize <size>       字体大小 (默认: 20)
  -help                  显示帮助信息
  -position <pos>        水印位置 (默认: bottom-right) 可选值: top-left, center, bottom-right
```

### 详细使用示例

请查看 [USAGE_EXAMPLES.md](USAGE_EXAMPLES.md) 获取更多使用示例和详细说明。

### 示例

```bash
# 基本使用
java -jar target/photo-watermark-1.0.0.jar /path/to/images

# 自定义字体大小和颜色
java -jar target/photo-watermark-1.0.0.jar -fontSize 30 -color red /path/to/images

# 自定义水印位置
java -jar target/photo-watermark-1.0.0.jar -position top-left /path/to/images
```

## 开发

### 项目结构

```
src/
├── main/
│   ├── java/           # Java源代码
│   └── resources/      # 资源文件
└── test/
    ├── java/           # 测试代码
    └── resources/      # 测试资源文件
```

### 运行测试

```bash
mvn test
```