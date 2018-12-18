package com.jhj.qrcode

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jhj.zxing.QRCodeScanActivity
import com.jhj.zxing.decode.DecodeHelper
import com.jhj.zxing.encode.EncodeHelper
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity() {

    private lateinit var bitmap: Bitmap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_camera.setOnClickListener {
            startActivity<QRCodeScanActivity>()
        }

        val logoBitmap = BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher)
        btn_encode.setOnClickListener {
            bitmap = EncodeHelper.encode("123412", logoBitmap)

            iv_qrcode.setImageBitmap(bitmap)
        }

        btn_decode.setOnClickListener {
            val a = DecodeHelper.decode(bitmap)
            toast(a?:"")
        }
    }
}
