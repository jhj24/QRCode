package com.jhj.zxing.decode

import android.graphics.Bitmap
import com.google.zxing.*
import com.google.zxing.common.GlobalHistogramBinarizer
import com.jhj.zxing.QRCode
import java.util.*


object DecodeHelper {


    fun decode(bitmap: Bitmap): String {

        val multiFormatReader = MultiFormatReader()
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

        multiFormatReader.setHints(hints)

        val width = bitmap.width
        val height = bitmap.height
        val pixel = IntArray(width * height)
        bitmap.getPixels(pixel, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)
        val source = RGBLuminanceSource(width, height, pixel)
        val binarizer = GlobalHistogramBinarizer(source)
        val image = BinaryBitmap(binarizer)
        val result = multiFormatReader.decodeWithState(image)
        return result.toString()

    }
}