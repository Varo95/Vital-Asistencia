package com.vitalasistencia.interfaces;

public interface IForm {
    public interface Presenter {
        void onClickSaveButton();

        String getError(String contactName);
        void onClickAddSpinner();
    }

    public interface View {
        void SaveUser();
        void onClickAddSpinner();
    }

}
