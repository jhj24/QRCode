package com.jhj.qrcode

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jhj.zxing.QRCodeScanActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_camera.setOnClickListener {
            startActivity<QRCodeScanActivity>()
        }
    }
}
