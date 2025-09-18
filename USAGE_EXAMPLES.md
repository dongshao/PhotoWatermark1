# 使用示例

## 基本使用

```bash
# 处理单个图片
java -jar target/photo-watermark-1.0.0.jar /path/to/image.jpg

# 处理多个图片
java -jar target/photo-watermark-1.0.0.jar /path/to/image1.jpg /path/to/image2.jpg /path/to/image3.jpg

# 处理整个目录的图片
java -jar target/photo-watermark-1.0.0.jar /path/to/images/*.jpg
```

## 自定义参数

### 字体大小
```bash
# 设置字体大小为30
java -jar target/photo-watermark-1.0.0.jar -fontSize 30 /path/to/image.jpg
```

### 字体颜色
```bash
# 设置字体颜色为红色
java -jar target/photo-watermark-1.0.0.jar -color red /path/to/image.jpg

# 使用十六进制颜色值
java -jar target/photo-watermark-1.0.0.jar -color #FF0000 /path/to/image.jpg
```

### 水印位置
```bash
# 设置水印位置为左上角
java -jar target/photo-watermark-1.0.0.jar -position top-left /path/to/image.jpg

# 设置水印位置为居中
java -jar target/photo-watermark-1.0.0.jar -position center /path/to/image.jpg

# 设置水印位置为右下角（默认）
java -jar target/photo-watermark-1.0.0.jar -position bottom-right /path/to/image.jpg
```

## 组合使用参数

```bash
# 同时设置字体大小、颜色和位置
java -jar target/photo-watermark-1.0.0.jar -fontSize 25 -color blue -position top-left /path/to/image.jpg
```

## 查看帮助信息

```bash
# 显示帮助信息
java -jar target/photo-watermark-1.0.0.jar -help
```

## 输出结果

程序会在原目录下创建一个名为 `[原目录名]_watermark` 的子目录，所有添加了水印的图片都会保存在这个目录中。

例如：
- 原图片路径：`/home/user/photos/vacation.jpg`
- 输出图片路径：`/home/user/photos_watermark/vacation_watermark.jpg`

## 支持的图片格式

程序支持常见的图片格式，包括：
- JPEG (.jpg, .jpeg)
- PNG (.png)
- BMP (.bmp)
- GIF (.gif)

## 错误处理

程序会显示清晰的错误信息，例如：
- 文件不存在：`错误: 图片文件不存在: /path/to/nonexistent.jpg`
- 权限不足：`错误: 没有权限读取文件: /path/to/protected.jpg`
- 不支持的格式：`错误: 不支持的图片格式: /path/to/document.pdf`

处理完成后，程序会显示处理结果摘要：
```
处理结果:
✓ /path/to/image1.jpg - 处理成功
✗ /path/to/image2.jpg - 处理失败: 文件不存在

总结:
成功处理: 1 个文件
处理失败: 1 个文件
```