package com.vitalasistencia.presenters;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.vitalasistencia.interfaces.IHelp;
import com.vitalasistencia.views.MyApp;

public class PHelp implements IHelp.Presenter {
    String TAG = "Vital_Asistencia/PAbout";

    private final IHelp.View view;

    public PHelp(IHelp.View view) {
        this.view = view;
    }

    @Override
    public void connectionError(){
        view.showConnectionError();
    }
}
