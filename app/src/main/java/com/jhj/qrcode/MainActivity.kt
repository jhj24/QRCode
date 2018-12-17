package com.jhj.qrcode

import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jhj.zxing.QRCodeScanActivity
import com.jhj.zxing.encode.QRCodeImage
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_camera.setOnClickListener {
            startActivity<QRCodeScanActivity>()
        }

        val logoBitmap = BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher)
        btn_encode.setOnClickListener {
            val bitmap = QRCodeImage.encode("123412是的尴尬三分大赛", logoBitmap)

            iv_qrcode.setImageBitmap(bitmap)
        }
    }
}
