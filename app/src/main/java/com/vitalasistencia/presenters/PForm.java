package com.vitalasistencia.presenters;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.vitalasistencia.R;
import com.vitalasistencia.interfaces.IForm;
import com.vitalasistencia.models.BUser;
import com.vitalasistencia.models.MUser;
import com.vitalasistencia.views.MyApp;

import java.util.ArrayList;

public class PForm implements IForm.Presenter {

    String TAG = "Vital_Asistencia/PForm";
    private IForm.View view;
    final private int CODE_WRITE_EXTERNAL_STORAGE_PERMISSION = 123;
    private MUser MUser;
    public PForm(IForm.View view) {
        this.view = view;
        MUser = new MUser();
    }

    @Override
    public void onClickSaveButton(BUser user) {
        Log.d(TAG, "Save Button Clicked");
        try{
            if(MUser.insertUser(user)) {
                view.showMessageForm(0);
                view.endActivity();
            }else if(MUser.existUser(user.getAffiliate_number()) && MUser.updateUser(user)){
                view.showMessageForm(7);
                view.endActivity();
            }else{
                //Codigo 6 es mostrar un error en numero de afiliado
                view.showMessageForm(6);
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    public String getError(String error_code) {
        String error_msg = "";
        switch (error_code) {
            case "Not valid date":
                error_msg = MyApp.getContext().getResources().getString(R.string.date_not_valid);
                break;
            case "Not valid phone":
                error_msg = MyApp.getContext().getResources().getString(R.string.phone_not_valid);
                break;
            case "Not valid email":
                error_msg = MyApp.getContext().getResources().getString(R.string.email_not_valid);
                break;
            case "Not valid address":
                error_msg = MyApp.getContext().getResources().getString(R.string.address_not_valid);
                break;
            case "Not valid affiliate":
                error_msg = MyApp.getContext().getResources().getString(R.string.affiliate_not_valid);
                break;
            case "User cannot be Inserted":
                error_msg = MyApp.getContext().getResources().getString(R.string.user_Inserted_Rejected);
                break;
            case "Unfilled Field":
                error_msg = MyApp.getContext().getResources().getString(R.string.unfilled_field);
                break;
            case "Valid":
                error_msg = "";
                break;
            case "Error":
                error_msg = MyApp.getContext().getResources().getString(R.string.unknow_Error);
                break;
            default:
                error_msg = "";
        }
        return error_msg;
    }

    @Override
    public void onClickAddSpinner() {
        Log.d(TAG, "AddToSpinner Clicked");
        view.onClickAddSpinner();
    }

    @Override
    public void onClickCancelButton() {
        view.deleteUser();
    }

    //He tenido que hacer la funcion pasandole la actividad porque el ActivityCompat no podía ejecutarse
    //con otra actividad
    @Override
    public void onClickImage(Activity activity) {
        int WriteExternalStoragePermission = ContextCompat.checkSelfPermission(MyApp.getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        Log.d(TAG, "clickImage" + WriteExternalStoragePermission);
        if (WriteExternalStoragePermission != PackageManager.PERMISSION_GRANTED) {
            // Permiso denegado
            // A partir de Marshmallow (6.0) se puede volver a pedir el permiso
            // En las versiones anteriores no es posible hacerlo
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                //Aquí se le vuelve a pedir el permiso
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, CODE_WRITE_EXTERNAL_STORAGE_PERMISSION);
                view.showRequestPermission(0);
            } else {
                //Permiso denegado
                view.showRequestPermission(1);
            }
        } else {
            // Permiso aceptado
            view.getImageFromStorage();
        }
    }


    @Override
    public void onClickAcceptDeleteButton(String id) {
        MUser.removeUser(id);
        view.endActivity();
    }

    @Override
    public void resetImage() {
        view.resetImage();
    }

    @Override
    public void acceptedPermission() {
        view.getImageFromStorage();
    }

    @Override
    public void deniedPermission() {
        view.showRequestPermission(1);
    }

    @Override
    public void onClickResetForm() {
        view.resetForm();
    }

    @Override
    public ArrayList getSpinner() {
        return MUser.getSpinner();
    }

    @Override
    public BUser getUser(String id) {
        return MUser.searchUser(id);
    }

}