package com.vitalasistencia.presenters;


import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.vitalasistencia.interfaces.IList;
import com.vitalasistencia.views.FormActivity;
import com.vitalasistencia.views.MainActivity;

public class PList implements IList.Presenter {

    String TAG = "Vital_Asistencia/PList";
    private IList.View view;

    public PList(IList.View view) {
        this.view = view;
    }


    @Override
    public void onClickFloatingButton() {
        Log.d(TAG, "Button Clicked");
        view.startFormActivity();
    }

    @Override
    public void onClickSearchButton() {
        Log.d(TAG,"Search Clicked");
        view.startSearchActivity();
    }
}
