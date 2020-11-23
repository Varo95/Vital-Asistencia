package com.vitalasistencia.presenters;

import com.vitalasistencia.R;
import com.vitalasistencia.interfaces.IForm;
import com.vitalasistencia.views.FormActivity;
import com.vitalasistencia.views.MyApp;

public class PForm implements IForm.Presenter {

    String TAG = "Vital_Asistencia/PForm";
    private IForm.View view;

    public PForm(IForm.View view) {
        this.view = view;
    }

    @Override
    public void onClickSaveButton() {
        view.SaveUser();
    }


    public String getError(String error_code){
        String error_msg="";
        switch(error_code){
            case "ContactName":
                error_msg= MyApp.getContext().getResources().getString(R.string.About);
                break;
        }
        return error_msg;
    }
}
