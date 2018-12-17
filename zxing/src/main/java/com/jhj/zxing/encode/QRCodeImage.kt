package com.jhj.zxing.encode

import android.graphics.*
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter
import com.jhj.zxing.QRCode

object QRCodeImage {


    fun encode(content: String): Bitmap {
        return encode(content, null)
    }

    fun encode(content: String, logo: Bitmap?): Bitmap {

        val width = QRCode.getQRCodeWidth()
        val height = QRCode.getQRCodeHeight()

        val pixels = IntArray(width * height)
        val hints = hashMapOf<EncodeHintType, Any>()
        //编码方式
        hints[EncodeHintType.CHARACTER_SET] = QRCode.getHintCharacterSet()
        // 容错级别 这里选择最高H级别,默认时L级别，加LOGO可能识别不出
        hints[EncodeHintType.ERROR_CORRECTION] = QRCode.getHintErrorCorrection()
        //设置空白边距的宽度
        hints[EncodeHintType.MARGIN] = QRCode.getHintWhiteMargin() //default is 4
        //数据content转换为数字矩阵
        val bitMatrix = QRCodeWriter().encode(content, QRCode.getBarCodeFormat(), width, height, hints)

        //矩阵生成二维码图片
        for (y in 0 until height) {
            for (x in 0 until width) {
                if (bitMatrix.get(x, y)) {
                    pixels[y * width + x] = QRCode.getQRCodeColor()
                } else {
                    pixels[y * width + x] = Color.WHITE
                }
            }
        }


        //创建Bitmap，设置生成二维码图片的大小及位图的质量
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

        /**
         * pixels - 设置像素数组，对应点的像素被放在数组中的对应位置
         * offset - 设置偏移量
         * stride - 设置一行打多少像素，通常一行设置为bitmap的宽度
         * x      - 设置开始绘图的x坐标
         * y      - 设置开始绘图的y坐标
         * qrCodeWidth  - 设置绘制出图片的宽度
         * qrCodeHeight - 设置绘制出图片的高度
         */

        bitmap.setPixels(pixels, 0, width, 0, 0, width, height)
        logo?.let {
            return setPersonalityLogo(bitmap, it)
        }

        return bitmap
    }

    /**
     * 设置个性Logo
     */
    private fun setPersonalityLogo(qrCodeBitmap: Bitmap, logoBitmap: Bitmap): Bitmap {

        val qrCodeWidth = qrCodeBitmap.width
        val qrCodeHeight = qrCodeBitmap.height

        // logo的宽高为二维码的1/5
        val targetLogoWidth = qrCodeWidth * QRCode.getLogoScaleOfQRCode()
        val targetLogoHeight = qrCodeHeight * QRCode.getLogoScaleOfQRCode()

        // 设置Logo宽高缩放比例
        val matrix = Matrix()
        matrix.postScale(targetLogoWidth / logoBitmap.width, targetLogoHeight / logoBitmap.height)
        val targetLogoBitmap = Bitmap.createBitmap(logoBitmap, 0, 0, logoBitmap.width, logoBitmap.height, matrix, false)

        //在二维码Bitmap上画logo
        val canvas = Canvas(qrCodeBitmap)
        canvas.drawBitmap(
            targetLogoBitmap,
            (qrCodeWidth - targetLogoWidth) / 2f,
            (qrCodeHeight - targetLogoHeight) / 2f,
            null
        )

        if (QRCode.isLogoNeedFrame()) {
            //Logo外面包裹的白色周边
            val paint = Paint()
            paint.color = Color.WHITE
            paint.strokeWidth = 6f
            paint.style = Paint.Style.STROKE
            paint.strokeCap = Paint.Cap.ROUND
            val rectWhite = RectF(
                (qrCodeWidth - targetLogoWidth) / 2f,
                (qrCodeHeight - targetLogoHeight) / 2f,
                (qrCodeWidth - targetLogoWidth) / 2f + targetLogoWidth,
                (qrCodeHeight - targetLogoHeight) / 2f + targetLogoHeight
            )
            canvas.drawRoundRect(rectWhite, targetLogoWidth / 6, targetLogoWidth / 6, paint)

            //Logo外面的白色周边与Logo之间的黑色线条
            paint.reset()
            paint.color = Color.BLACK
            paint.strokeWidth = 1f
            paint.style = Paint.Style.STROKE
            paint.strokeCap = Paint.Cap.ROUND
            val rectBlack = RectF(
                (qrCodeWidth - targetLogoWidth) / 2f + 4,
                (qrCodeHeight - targetLogoHeight) / 2f + 4,
                (qrCodeWidth - targetLogoWidth) / 2f + targetLogoWidth - 4,
                (qrCodeHeight - targetLogoHeight) / 2f + targetLogoHeight - 4
            )
            canvas.drawRoundRect(rectBlack, targetLogoWidth / 6, targetLogoWidth / 6, paint)
        }

        canvas.save()
        canvas.restore()
        return qrCodeBitmap
    }


}