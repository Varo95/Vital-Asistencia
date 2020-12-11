package com.vitalasistencia.interfaces;

public interface IForm {
    public interface Presenter {
        void onClickSaveButton();
        String getError(String error_code);
        void onClickAddSpinner();
        void onClickCancelButton();
        void onClickImage();
        void checkReadInternalStorage(int WriteExternalStoragePermission);
    }

    public interface View {
        void SaveUser();
        void onClickAddSpinner();
        void DeleteUser();
        void getReadPermission(int n);
    }
}
