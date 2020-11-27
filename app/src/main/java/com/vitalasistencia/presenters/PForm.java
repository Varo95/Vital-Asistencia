package com.vitalasistencia.presenters;

import com.vitalasistencia.R;
import com.vitalasistencia.interfaces.IForm;
import com.vitalasistencia.views.FormActivity;
import com.vitalasistencia.views.MyApp;

import static android.provider.Settings.System.getString;

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
            case "Not valid date":
                error_msg= MyApp.getContext().getResources().getString(R.string.date_not_valid);
                break;
            case "Not valid phone":
                error_msg=MyApp.getContext().getResources().getString(R.string.phone_not_valid);
                break;
            case "Valid":
                error_msg="";
                break;
            default:
                error_msg="";
        }
        return error_msg;
    }

    @Override
    public void onClickAddSpinner() {
        view.onClickAddSpinner();
    }
}
