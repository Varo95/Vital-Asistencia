package com.vitalasistencia.presenters;


import android.util.Log;

import com.vitalasistencia.interfaces.IList;
import com.vitalasistencia.views.MainActivity;

public class PList implements IList.Presenter {

    String TAG = "Vital_Asistencia/PList";
    private IList.View view;

    public PList(IList.View view) {
        this.view = view;
    }

    public PList(MainActivity mainActivity) {
    }

    @Override
    public void onClickFloatingButton() {
        Log.d(TAG, "Button Clicked");
        view.startFormActivity();
    }
}
