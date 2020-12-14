package com.vitalasistencia.presenters;

import com.vitalasistencia.interfaces.ISearch;

public class PSearch implements ISearch.Presenter {

    String TAG = "Vital_Asistencia/PSearch";
    private ISearch.View view;

    public PSearch(ISearch.View view) {
        this.view = view;
    }

    @Override
    public void onClickSearchButton() {
        view.SearchButton();
    }
}
