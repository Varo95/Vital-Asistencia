package com.vitalasistencia.interfaces;

import android.app.Activity;

public interface IForm {
    public interface Presenter {
        void onClickSaveButton();
        String getError(String error_code);
        void onClickAddSpinner();
        void onClickCancelButton();
        void onClickImage(Activity activity);
        void onClickAcceptDeleteButton();
        void resetImage();
        void AcceptedPermission();
        void DeniedPermission();
    }

    public interface View {
        void SaveUser();
        void onClickAddSpinner();
        void DeleteUser();
        void showRequestPermission(int n);
        void getImageFromStorage();
        void resetImage();
    }
}
