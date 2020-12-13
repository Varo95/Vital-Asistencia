package com.vitalasistencia.presenters;

import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import com.vitalasistencia.R;
import com.vitalasistencia.interfaces.IForm;
import com.vitalasistencia.views.MyApp;

public class PForm implements IForm.Presenter {

    String TAG = "Vital_Asistencia/PForm";
    private IForm.View view;

    public PForm(IForm.View view) {
        this.view = view;
    }

    @Override
    public void onClickSaveButton() {
        Log.d(TAG,"Save Button Clicked");
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
            case "Not valid email":
                error_msg=MyApp.getContext().getResources().getString(R.string.email_not_valid);
                break;
            case "Not valid address":
                error_msg=MyApp.getContext().getResources().getString(R.string.address_not_valid);
                break;
            case "Not valid affiliate":
                error_msg=MyApp.getContext().getResources().getString(R.string.affiliate_not_valid);
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
        Log.d(TAG,"AddToSpinner Clicked");
        view.onClickAddSpinner();
    }

    @Override
    public void onClickCancelButton() {
        view.DeleteUser();
    }

    @Override
    public void onClickImage() {

    }

    @Override
    public void checkReadInternalStorage(int WriteExternalStoragePermission) {
        if (WriteExternalStoragePermission != PackageManager.PERMISSION_GRANTED) {
            // Permiso denegado
            // A partir de Marshmallow (6.0) se pide aceptar o rechazar el permiso en tiempo de ejecución
            // En las versiones anteriores no es posible hacerlo
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                view.getReadPermission(0);
                // Una vez que se pide aceptar o rechazar el permiso se ejecuta el método "onRequestPermissionsResult" para manejar la respuesta
                // Si el usuario marca "No preguntar más" no se volverá a mostrar este diálogo
            } else {
                view.getReadPermission(1);
            }
        } else {
            // Permiso aceptado
            view.getReadPermission(2);
        }
    }

    @Override
    public void onClickAcceptDeleteButton() {
        //En un futuro esto debería cambiarse para que borrase al usuario de la lista
        //de momento llama a este método de la vista porque es un método el cual me devuelve a la actividad anterior
        view.SaveUser();
    }
}
