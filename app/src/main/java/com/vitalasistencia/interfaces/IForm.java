package com.vitalasistencia.interfaces;

import android.app.Activity;

import com.vitalasistencia.models.BUser;

public interface IForm {
    public interface Presenter {
        void onClickSaveButton(BUser user);
        String getError(String error_code);
        void onClickAddSpinner();
        void onClickCancelButton();
        void onClickImage(Activity activity);
        void onClickAcceptDeleteButton();
        void resetImage();
        void acceptedPermission();
        void deniedPermission();
        void onClickResetForm();
    }

    public interface View {
        void saveUser();
        void onClickAddSpinner();
        void deleteUser();
        void showRequestPermission(int n);
        void getImageFromStorage();
        void resetImage();
        void resetForm();
        void showMessageForm(int code);
    }
}
