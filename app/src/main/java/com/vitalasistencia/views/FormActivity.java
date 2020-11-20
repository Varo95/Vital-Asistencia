package com.vitalasistencia.views;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.vitalasistencia.R;
import com.vitalasistencia.interfaces.IForm;
import com.vitalasistencia.models.BUser;
import com.vitalasistencia.presenters.PForm;

public class FormActivity extends AppCompatActivity implements IForm.View {

    String TAG = "Vital_Asistencia/FormActivity";
    private IForm.Presenter presenter;
    private Context myContext;
    private Object user;
    TextInputLayout nameTIL;
    TextInputEditText nameET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "Starting onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        myContext = this;
        presenter = new PForm(this);
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
        Spinner spinner = (Spinner) findViewById(R.id.spinner_Form);
        String[] letra = {"Lunes", "Martes", "Miercoles", "Jueves", "Viernes"};
        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, letra));
        Button SaveButton = findViewById(R.id.Save_Form);
        SaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onClickSaveButton();
            }
        });

        user = new BUser();

/*        nameET = findViewById(R.id.nameTE);
        nameTIL = findViewById(R.id.nameTIL);

        nameET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    Log.d("FormActivity", "Exit EditText");
                    if (user.setName(nameET.getText().toString()) == false ) {
                        nameTIL.setError(presenter.getError("ContactName"));
                    } else {
                        nameTIL.setError("");
                    }
                }else{
                    Log.d(TAG, "Input EditText");
                }

            }
        });
*/
    }

    @Override
    public boolean onNavigateUp() {
        // Asignar la acción necesaria. En este caso terminar la actividad
        return true;
    }

    /*    @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            Log.d(TAG, "Cargando las opciones del menu");
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_form, menu);

            return true;
        }
    */
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
}