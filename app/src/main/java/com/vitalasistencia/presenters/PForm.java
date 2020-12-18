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
import com.vitalasistencia.views.MyApp;

public class PForm implements IForm.Presenter {

    String TAG = "Vital_Asistencia/PForm";
    private IForm.View view;
    final private int CODE_WRITE_EXTERNAL_STORAGE_PERMISSION = 123;
    public PForm(IForm.View view) {
        this.view = view;
    }

    @Override
    public void onClickSaveButton() {
        Log.d(TAG, "Save Button Clicked");
        view.SaveUser();
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
            case "Valid":
                error_msg = "";
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
        view.DeleteUser();
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
    public void onClickAcceptDeleteButton() {
        //En un futuro esto debería cambiarse para que borrase al usuario de la lista
        //de momento llama a este método de la vista porque es un método el cual me devuelve a la actividad anterior
        view.SaveUser();
    }

    @Override
    public void resetImage() {
        view.resetImage();
    }

    @Override
    public void AcceptedPermission() {
        view.getImageFromStorage();
    }

    @Override
    public void DeniedPermission() {
        view.showRequestPermission(1);
    }
}