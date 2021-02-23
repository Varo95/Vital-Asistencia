package com.vitalasistencia.interfaces;

import android.app.Activity;

public interface IHelp {
    interface View{
        void showConnectionError();
    }

    interface Presenter {
        void connectionError();
    }
}
