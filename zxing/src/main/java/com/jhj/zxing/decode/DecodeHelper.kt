package com.jhj.zxing.decode

import android.graphics.Bitmap
import com.google.zxing.*
import com.google.zxing.common.GlobalHistogramBinarizer
import com.google.zxing.common.HybridBinarizer
import com.jhj.zxing.QRCode
import java.util.*


object DecodeHelper {


    /**
     *
     */
    fun decode(bitmap: Bitmap): String? {

        val multiFormatReader = MultiFormatReader()
        var source: RGBLuminanceSource? = null
        try {
            val hints = HashMap<DecodeHintType, Any>()
            //字符编码格式
            hints[DecodeHintType.CHARACTER_SET] = QRCode.getHintCharacterSet()

            // 可以解析的编码类型
            val decodeFormats = Vector<BarcodeFormat>()
            if (decodeFormats.isEmpty()) {
                // 这里设置可扫描的类型，这里选择了都支持
                decodeFormats.addAll(DecodeFormatManager.ONE_D_FORMATS)
                decodeFormats.addAll(DecodeFormatManager.QR_CODE_FORMATS)
                decodeFormats.addAll(DecodeFormatManager.DATA_MATRIX_FORMATS)
            }
            hints[DecodeHintType.POSSIBLE_FORMATS] = decodeFormats
            // 设置解码参数
            multiFormatReader.setHints(hints)


            val width = bitmap.width
            val height = bitmap.height
            val pixel = IntArray(width * height)
            bitmap.getPixels(pixel, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)
            source = RGBLuminanceSource(width, height, pixel)

            //二维码识别算法主要有两种，分别是HybridBinarizer和GlobalHistogramBinarizer
            // HybridBinarizer算法在执行效率上要慢于GlobalHistogramBinarizer算法，但识别相对更有效
            // GlobalHistogramBinarizer算法适合于低端的设备，对手机的CPU和内存要求不高

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