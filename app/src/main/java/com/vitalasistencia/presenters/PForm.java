package com.vitalasistencia.presenters;

import com.vitalasistencia.interfaces.IForm;
import com.vitalasistencia.views.FormActivity;

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
}
