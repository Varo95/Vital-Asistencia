package com.vitalasistencia.interfaces;

import java.util.ArrayList;

public interface ISearch {
    public interface Presenter {
        void onClickSearchButton();
        ArrayList getSpinner();
    }

    public interface View {
        void SearchButton();
    }
}
