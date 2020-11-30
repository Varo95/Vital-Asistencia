package com.vitalasistencia.interfaces;

public interface IList {
    public interface View {
        void startFormActivity();
        void startSearchActivity();
        void startAboutActivity();
    }

    public interface Presenter {
        void onClickFloatingButton();
        void onClickSearchButton();
        void onClickAboutButton();
    }
}
