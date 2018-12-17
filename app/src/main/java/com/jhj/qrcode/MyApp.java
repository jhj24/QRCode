package com.jhj.qrcode;

import android.app.Application;
import android.graphics.Color;
import com.jhj.zxing.QRCode;

public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        QRCode.INSTANCE.init().setHintWhiteMargin(0);
    }
}
