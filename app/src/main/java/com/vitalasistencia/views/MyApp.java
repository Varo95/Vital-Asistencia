package com.vitalasistencia.views;

import android.app.Application;
import android.content.Context;

public class MyApp extends Application {
    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();

    }

    public static Context getContext() {
        return sContext;
    }
}
