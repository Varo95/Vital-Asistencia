package com.vitalasistencia.interfaces;

import android.app.Activity;

import com.vitalasistencia.models.BUser;

import java.util.ArrayList;

public interface IForm {
    public interface Presenter {
        void onClickSaveButton(BUser user);
        String getError(String error_code);
        void onClickAddSpinner();
        void onClickCancelButton();
        void onClickImage(Activity activity);
        void onClickAcceptDeleteButton(String id);
        void resetImage();
        void acceptedPermission();
        void deniedPermission();
        void onClickResetForm();
        ArrayList getSpinner();
        BUser getUser(String id);
    }

    public interface View {
        void endActivity();
        void onClickAddSpinner();
        void deleteUser();
        void showRequestPermission(int n);
        void getImageFromStorage();
        void resetImage();
        void resetForm();
        void showMessageForm(int code);
    }
}
