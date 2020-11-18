package com.vitalasistencia.interfaces;

public interface IList {
    public interface View {
        void startFormActivity();
        //void startSearchActivity();
    }

    public interface Presenter {
        void onClickFloatingButton();
    }
}
