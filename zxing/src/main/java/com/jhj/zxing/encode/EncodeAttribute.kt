package com.jhj.zxing.encode

import android.graphics.Color
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel

class EncodeAttribute {

    var qrCodeHeight: Int = 800
    var qrCodeWidth: Int = 800
    var qrCodeColor = Color.RED
    var barcodeFormat = BarcodeFormat.CODE_128
    var logoSize = 1 / 5f
    var isLogoNeedFrame = true
    var hintWhiteMargin = 1
    var hintCharacterSet = "utf-8"
    var hintErrorCorrection = ErrorCorrectionLevel.H


}