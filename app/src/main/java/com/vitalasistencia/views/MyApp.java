package com.vitalasistencia.views;

import android.app.Application;
import android.content.Context;

import io.realm.Realm;

public class MyApp extends Application {
    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
        Realm.init(sContext);

    }

    public static Context getContext() {
        return sContext;
    }
}
