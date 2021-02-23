package com.vitalasistencia.presenters;

import android.util.Log;

import com.vitalasistencia.interfaces.ISearch;
import com.vitalasistencia.models.MUser;

import java.util.ArrayList;

public class PSearch implements ISearch.Presenter {

    String TAG = "Vital_Asistencia/PSearch";
    private final ISearch.View view;
    private final MUser MUser;

    public PSearch(ISearch.View view) {
        this.view = view;
        MUser = new MUser();
    }

    @Override
    public void onClickSearchButton() {
        view.SearchButton();
    }

    @Override
    public ArrayList getSpinner() {
        return MUser.getSpinner();
    }

    @Override
    public void onClickHelpButton() {
        Log.d(TAG,"Help clicked");
        view.startHelpActivity();
    }
}
