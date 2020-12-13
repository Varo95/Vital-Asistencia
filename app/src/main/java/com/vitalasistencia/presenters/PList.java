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

    @Override
    public void onClickAboutButton(){
        Log.d(TAG,"About clicked");
        view.startAboutActivity();
    }

    @Override
    public void onClickReciclerViewItem(String affiliate_number) {
        Log.d(TAG,"Item"+affiliate_number+"selected");
        view.startFormActivity(affiliate_number);
    }

    @Override
    public void onClickSwipeEdit(String affiliate_number) {
        Log.d(TAG,"Item"+affiliate_number+"editing");
        view.startFormActivity(affiliate_number);
    }

    @Override
    public void onClickSwipeDelete(int pos) {
        Log.d(TAG,"Item"+pos+"deleting");
        view.deleteUser(pos);
    }
}
