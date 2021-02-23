package com.vitalasistencia.interfaces;

import java.util.ArrayList;

public interface ISearch {
    interface Presenter {
        void onClickSearchButton();
        ArrayList getSpinner();
        void onClickHelpButton();
    }

    interface View {
        void SearchButton();
        void showMessageSearch();
        void startHelpActivity();
    }
}
