# QRCode
二维码扫描


### 二维码解析

ZXing 解析二维码图片有以下步骤：

- 使用 javax.imageio.ImageIO 读取图片文件，并存为一个 java.awt.image.BufferedImage 对象。
- 将 java.awt.image.BufferedImage 转换为 ZXing 能识别的 com.google.zxing.BinaryBitmap 对象。
- com.google.zxing.MultiFormatReader 根据图像解码参数来解析 com.google.zxing.BinaryBitmap 。

### 二维码生成

- com.google.zxing.MultiFormatWriter 根据内容以及图像编码参数生成图像2D矩阵。
- com.google.zxing.client.j2se.MatrixToImageWriter 根据图像矩阵生成图片文件或图片缓存 BufferedImage 
