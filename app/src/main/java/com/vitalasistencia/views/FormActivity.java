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
import java.util.Calendar;

import java.util.ArrayList;

public class FormActivity extends AppCompatActivity implements IForm.View {

    String TAG = "Vital_Asistencia/FormActivity";
    private IForm.Presenter presenter;
    private Context myContext;
    TextInputLayout nameTIL;
    TextInputEditText nameET;
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

        nameET = findViewById(R.id.TEI_date);
        nameTIL = findViewById(R.id.date_form);

        nameET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    Log.d("FormActivity", "Exit EditText");
                    if (user.setDate(nameET.getText().toString()) == false ) {
                        nameTIL.setError(presenter.getError("ContactName"));
                    } else {
                        nameTIL.setError("");
                    }
                }else{
                    Log.d(TAG, "Input EditText");
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