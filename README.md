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
# 克隆项目
git clone https://github.com/dongshao/PhotoWatermark1.git
cd PhotoWatermark1

# 编译和打包项目
mvn clean package
```

构建完成后，可执行的JAR文件将位于 `target/photo-watermark-1.0.0.jar`

## 使用方法

### 基本语法
```bash
java -jar target/photo-watermark-1.0.0.jar [options] <image_path> [image_path2] [image_path3] ...
```

### 命令行参数

```
Usage: PhotoWatermark [options] <image_path>
  -color <color>         字体颜色 (默认: white)
                     可选值: black, blue, cyan, darkgray, gray, green,
                           lightgray, magenta, orange, pink, red, white, yellow
                     或使用十六进制颜色值: #FF0000
  -fontSize <size>       字体大小 (默认: 20)
  -help                  显示帮助信息
  -position <pos>        水印位置 (默认: bottom-right)
                     可选值: top-left, top-center, top-right, center-left,
                           center, center-right, bottom-left, bottom-center, bottom-right
```

### 使用示例

#### 1. 基本使用
```bash
# 处理单个图片
java -jar target/photo-watermark-1.0.0.jar /path/to/image.jpg

# 处理多个图片
java -jar target/photo-watermark-1.0.0.jar /path/to/image1.jpg /path/to/image2.jpg /path/to/image3.jpg

# 处理整个目录的图片（使用通配符）
java -jar target/photo-watermark-1.0.0.jar /path/to/images/*.jpg
```

#### 2. 自定义水印样式
```bash
# 设置字体大小为30
java -jar target/photo-watermark-1.0.0.jar -fontSize 30 /path/to/image.jpg

# 设置字体颜色为红色
java -jar target/photo-watermark-1.0.0.jar -color red /path/to/image.jpg

# 使用十六进制颜色值
java -jar target/photo-watermark-1.0.0.jar -color #FF0000 /path/to/image.jpg

# 设置水印位置为左上角
java -jar target/photo-watermark-1.0.0.jar -position top-left /path/to/image.jpg
```

#### 3. 组合参数使用
```bash
# 同时设置字体大小、颜色和位置
java -jar target/photo-watermark-1.0.0.jar -fontSize 25 -color blue -position top-left /path/to/image.jpg
```

#### 4. 批量处理
```bash
# 处理同一目录下的所有JPEG图片
java -jar target/photo-watermark-1.0.0.jar /home/user/photos/*.jpg

# 处理多个目录的图片
java -jar target/photo-watermark-1.0.0.jar /path/to/summer/*.jpg /path/to/winter/*.jpg
```

### 输出结果

程序会在原目录下创建一个名为 `[原目录名]_watermark` 的子目录，所有添加了水印的图片都会保存在这个目录中。

例如：
- 原图片路径：`/home/user/photos/vacation.jpg`
- 输出图片路径：`/home/user/photos_watermark/vacation_watermark.jpg`

### 支持的图片格式

程序支持常见的图片格式，包括：
- JPEG (.jpg, .jpeg)
- PNG (.png)
- BMP (.bmp)
- GIF (.gif)

### 错误处理

程序会显示清晰的错误信息，例如：
- 文件不存在：`错误: 图片文件不存在: /path/to/nonexistent.jpg`
- 权限不足：`错误: 没有权限读取文件: /path/to/protected.jpg`

处理完成后，程序会显示处理结果摘要：
```
处理结果:
✓ /path/to/image1.jpg - 处理成功
✗ /path/to/image2.jpg - 处理失败: 文件不存在

总结:
成功处理: 1 个文件
处理失败: 1 个文件
```

## 查看更多示例

请查看 [USAGE_EXAMPLES.md](USAGE_EXAMPLES.md) 获取更多使用示例和详细说明。

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
# 运行所有测试
mvn test

# 运行特定测试类
mvn test -Dtest=PhotoWatermarkTest
```

### 代码质量

- 遵循Java编码规范
- 包含完整的单元测试覆盖
- 提供详细的JavaDoc注释
- 实现全面的异常处理