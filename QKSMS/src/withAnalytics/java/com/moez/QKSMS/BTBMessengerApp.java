package com.bitblocker.messenger;

import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

public class BTBMessengerApp extends BTBMessengerAppBase {
    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
    }
}
