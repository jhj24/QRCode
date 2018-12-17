package com.jhj.zxing.camera

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jhj.zxing.ImageUtil
import com.jhj.zxing.R

class QRCodeScanActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.actiivty_qrcode_scan)

        ImageUtil.openCamera(this, "qrcode", 100)

    }
}