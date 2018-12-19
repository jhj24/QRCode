package com.jhj.zxing

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.SurfaceHolder
import com.jhj.zxing.camera.CameraManager
import com.jhj.zxing.camera.CameraPermissionsUtil
import com.jhj.zxing.weight.QRCodeView
import kotlinx.android.synthetic.main.fragment_qrcode_scanning.*
import org.jetbrains.anko.toast


class QRCodeScanActivity : AppCompatActivity() {


    companion object {
        const val PERMISSION_CAMERA = 1000


    }

    private var isHasSurface = false
    /* private var handler: CaptureActivityHandler? = null

     private lateinit var beepManager: BeepManager
     private lateinit var inactivityTimer: InactivityTimer*/
    private lateinit var surfaceHolder: SurfaceHolder
    private lateinit var cameraManager: CameraManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_qrcode_scanning)
        surfaceHolder = surfaceView.holder
        /* inactivityTimer = InactivityTimer(this);
         beepManager = BeepManager(this)*/
        cameraManager = CameraManager(this)
        surfaceHolder.addCallback(surfaceHolderCallback)
    }
/*
    fun getHandler(): Handler? {
        return handler
    }*/

    fun getCameraManager(): CameraManager {
        return cameraManager
    }


    private val surfaceHolderCallback = object : SurfaceHolder.Callback {
        override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {

        }

        override fun surfaceDestroyed(holder: SurfaceHolder?) {
            isHasSurface = false;
        }

        override fun surfaceCreated(holder: SurfaceHolder?) {
            if (holder == null) {

            }
            if (!isHasSurface) {
                isHasSurface = true;
                requestPermissions(holder)
                cameraManager.openDriver(holder)
            }
        }

    }



    fun requestPermissions(holder: SurfaceHolder?) {
        val permissions = arrayOf(Manifest.permission.CAMERA)
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            if (!CameraPermissionsUtil.isCameraDenied()) {
                initCamera(holder)
            } else {
                toast("请开启相机权限")
                finish()
            }
        } else {
            ActivityCompat.requestPermissions(
                this, permissions,
                PERMISSION_CAMERA
            )
        }
    }


    private fun initCamera(surfaceHolder: SurfaceHolder?) {
        if (surfaceHolder == null) {
            throw IllegalStateException("No SurfaceHolder provided")
        }
        if (cameraManager.isOpen) {
            return
        }
        /* try {
             cameraManager.openDriver(surfaceHolder)
             if (handler == null) {
                 // handler = CaptureActivityHandler(this, decodeFormats, decodeHints, characterSet, cameraManager)
                 handler = CaptureActivityHandler(
                     this, DecodeFormatManager.QR_CODE_FORMATS, null, "utf-8", cameraManager
                 )
             }

             //initCrop()
         } catch (ioe: IOException) {
             // Log.w(FragmentActivity.TAG, ioe)
             displayFrameworkBugMessageAndExit()
         } catch (e: RuntimeException) {
             // Barcode Scanner has seen crashes in the wild of this variety:
             // java.?lang.?RuntimeException: Fail to connect to camera service
             //Log.w(FragmentActivity.TAG, "Unexpected error initializing camera", e)
             displayFrameworkBugMessageAndExit()
         }*/


    }


    fun getQRCodeView(): QRCodeView? {
        return qrCodeView
    }


    /*override fun onResume() {
        super.onResume()
        cameraManager = CameraManager(getApplication());
        handler = null
        if (isHasSurface) {
            requestPermissions(surfaceView.holder);
        } else {
            surfaceHolder.addCallback(surfaceHolderCallback)
        }

        inactivityTimer.onResume();

    }*/

/*
    override fun onPause() {
        if (handler != null) {
            handler?.quitSynchronously()
            handler = null
        }
        inactivityTimer.onPause()
        //ambientLightManager.stop()
        beepManager.close()
        cameraManager.closeDriver()
        //historyManager = null; // Keep for onActivityResult
        if (!isHasSurface) {
            surfaceHolder.removeCallback(surfaceHolderCallback)
        }
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        inactivityTimer.shutdown();
    }
*/

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_CAMERA) {

        }
    }


}