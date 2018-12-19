package com.jhj.zxing.decode

import android.graphics.Bitmap
import com.google.zxing.*
import com.google.zxing.common.GlobalHistogramBinarizer
import com.google.zxing.common.HybridBinarizer
import com.jhj.zxing.QRCode
import java.util.*


object DecodeHelper {


    fun decode(bitmap: Bitmap): String? {
        return decode(bitmap, DecodeFormat.ALL_DECODE_FORMAT)
    }

    /**
     * 二维码识别算法主要有两种，分别是HybridBinarizer和GlobalHistogramBinarizer,
     * HybridBinarizer算法在执行效率上要慢于GlobalHistogramBinarizer算法，但识别相对更有效,
     * GlobalHistogramBinarizer算法适合于低端的设备，对手机的CPU和内存要求不高
     */
    fun decode(bitmap: Bitmap, format: List<BarcodeFormat>): String? {

        val multiFormatReader = MultiFormatReader()
        var source: RGBLuminanceSource? = null
        try {
            val hints = HashMap<DecodeHintType, Any>()
            //字符编码格式
            hints[DecodeHintType.CHARACTER_SET] = QRCode.getHintCharacterSet()
            // 可以解析的编码类型
            hints[DecodeHintType.POSSIBLE_FORMATS] = format
            //花更多的时间用于寻找图上的编码，优化准确性，但不优化速度
            hints[DecodeHintType.TRY_HARDER] = true
            // 设置解码参数
            multiFormatReader.setHints(hints)

            val width = bitmap.width
            val height = bitmap.height
            val pixel = IntArray(width * height)
            bitmap.getPixels(pixel, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)
            source = RGBLuminanceSource(width, height, pixel)

            val binary = HybridBinarizer(source)
            val binaryBitmap = BinaryBitmap(binary)
            val result = multiFormatReader.decodeWithState(binaryBitmap)
            return result.text
        } catch (e: Exception) {
            try {
                val binary = GlobalHistogramBinarizer(source)
                val binaryBitmap = BinaryBitmap(binary)
                val result = multiFormatReader.decodeWithState(binaryBitmap)
                return result.text
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return null
        }
    }
}