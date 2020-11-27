package com.vitalasistencia.views;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.vitalasistencia.R;
import com.vitalasistencia.interfaces.IForm;
import com.vitalasistencia.models.BUser;
import com.vitalasistencia.presenters.PForm;

import java.util.ArrayList;

public class FormActivity extends AppCompatActivity implements IForm.View {

    String TAG = "Vital_Asistencia/FormActivity";
    private IForm.Presenter presenter;
    private Context myContext;
    TextInputLayout phoneTIL;
    TextInputEditText phoneET;
    private ArrayList<String> letra=null;
    private Spinner s=null;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "Starting onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        myContext = this;
        presenter = new PForm(this);

        //Creamos el toolbar
        Log.d(TAG, "Starting Toolbar");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(R.string.New_User);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Pressing Back Button");
                    onBackPressed();
                }
            });
        } else {
            Log.d(TAG, "Error loading toolbar");
        }
        //Declaramos el arrayList del Spinner
        letra = new ArrayList<String>();
        letra.add("Lunes");
        letra.add("Martes");
        letra.add("Miercoles");
        letra.add("Jueves");
        letra.add("Viernes");

        //Creamos el adaptador
        adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, letra);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        //Creamos el spinner y le inyectamos los valores del adaptador
        s = (Spinner) findViewById(R.id.spinner_Form);
        s.setAdapter(adapter);

        //Boton añadir del spinner
        Button AddToSpinner = findViewById(R.id.add_spinner);
        AddToSpinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"Pressing ADD spinner button");
                presenter.onClickAddSpinner();
            }
        });

        //Creamos un nuevo usuario
        BUser user = new BUser();

        //Creamos punteros para el textedit de la fecha
        phoneET = findViewById(R.id.TEI_date);
        phoneTIL = findViewById(R.id.date_form);

        phoneET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    Log.d(TAG, "date_text has focused");
                    //Comprobamos que el formato de la fecha es el correcto
                    if(phoneET.getText().toString().matches("")){
                        //Si la cadena está vacía no mostrará ningún error
                        phoneTIL.setError(presenter.getError("Valid"));
                    }else if (!(user.setDate("dd/MM/yyyy",(phoneET.getText().toString())))) {
                        //Si la cadena contiene una fecha invalida, mostrará este error(mirar BUser.setdate())
                        phoneTIL.setError(presenter.getError("Not valid date"));
                    }else {
                        phoneTIL.setError(presenter.getError("Valid"));
                    }
                }else{
                    Log.d(TAG, "date_text is unfocused");
                }
            }
        });

        //Creamos punteros para el textedit del telefono
        phoneET = findViewById(R.id.TEI_phone);
        phoneTIL = findViewById(R.id.phone_form);
        //Hacemos una operación equivalente a la anterior pero aplicandolo al número
        phoneET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    Log.d(TAG, "phone_text has focused");
                    if(phoneET.getText().toString().matches("")){
                        phoneTIL.setError(presenter.getError("Valid"));
                    }else if (!(user.setPhone(phoneET.getText().toString()))) {
                        //No será valido si no coincide con la expresión regular de Buser.setPhone
                        phoneTIL.setError(presenter.getError("Not valid phone"));
                    }else {
                        phoneTIL.setError(presenter.getError("Valid"));
                    }
                }else{
                    Log.d(TAG, "phone_text is unfocused");
                }
            }
        });

        //Botón de guardar
        Button SaveButton = findViewById(R.id.Save_Form);
        SaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onClickSaveButton();
            }
        });

    }

    @Override
    public boolean onNavigateUp() {
        // Asignar la acción necesaria. En este caso terminar la actividad
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "Loading Menu Options");
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_form, menu);
        return true;
    }

    @Override
    protected void onStart() {
        Log.d(TAG, "Starting onStart");
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "Starting onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "Starting onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "Starting onStop");
        super.onStop();
    }

    @Override
    protected void onRestart() {
        Log.d(TAG, "Starting onRestart");
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "Starting onDestroy");
        super.onDestroy();
    }

    @Override
    public void SaveUser(){
        Log.d(TAG,"Starting SaveButton");
        finish();
    }

    @Override
    public void onClickAddSpinner() {
        Log.d(TAG,"Adding to spinner");
        LayoutInflater layoutActivity = LayoutInflater.from(myContext);
        View viewAlertDialog = layoutActivity.inflate(R.layout.alert_dialog, null);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(myContext);
        alertDialog.setView(viewAlertDialog);
        final EditText dialogInput = (EditText) viewAlertDialog.findViewById(R.id.dialogInput);
        alertDialog
                .setCancelable(false)
                // Botón Añadir
                .setPositiveButton(getResources().getString(R.string.add),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                adapter.add(dialogInput.getText().toString());
                                s.setSelection(adapter.getPosition(dialogInput.getText().toString()));
                            }
                        })
                // Botón Cancelar
                .setNegativeButton(getResources().getString(R.string.cancel),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                dialogBox.cancel();
                            }
                        })
                .create()
                .show();

    }
}