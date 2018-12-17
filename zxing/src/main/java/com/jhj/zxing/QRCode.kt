package com.jhj.zxing

import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import com.jhj.zxing.encode.EncodeAttribute

object QRCode {

    val attribute = EncodeAttribute()

    fun init(): EncodeAttribute {
        return attribute
    }

    fun getQRCodeWidth(): Int {
        return attribute.qrCodeWidth
    }

    fun getQRCodeHeight(): Int {
        return attribute.qrCodeHeight
    }

    fun getQRCodeColor(): Int {
        return attribute.qrCodeColor
    }

    fun getLogoScaleOfQRCode(): Float {
        return attribute.logoSize
    }

    fun getBarCodeFormat(): BarcodeFormat {
        return attribute.barcodeFormat
    }

    fun getHintWhiteMargin(): Int {
        return attribute.hintWhiteMargin
    }

    fun getHintCharacterSet(): String {
        return attribute.hintCharacterSet
    }

    fun getHintErrorCorrection(): ErrorCorrectionLevel {
        return attribute.hintErrorCorrection
    }

    fun isLogoNeedFrame(): Boolean {
        return attribute.isLogoNeedFrame
    }

}