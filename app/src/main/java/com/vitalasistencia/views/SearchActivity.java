package com.vitalasistencia.views;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.vitalasistencia.R;
import com.vitalasistencia.interfaces.ISearch;
import com.vitalasistencia.presenters.PSearch;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

public class SearchActivity extends AppCompatActivity implements ISearch.View {
    private String TAG = "Vital_Asistencia/SearchActivity";

    private ISearch.Presenter presenter;
    private Context myContext;
    private Button buttonDate;
    private Calendar calendar;
    private TextInputEditText dateEditText;
    private TextInputLayout addressLayout;
    private TextInputEditText addressEditText;
    private DatePickerDialog datePickerDialog;
    private int Year, Month, Day;

    //Calle, Spinner y fecha
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "Starting OnCreate");
        //Mostramos el tema a cargar
        setTheme(R.style.Theme_VitalAsistencia_Search);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(R.string.title_activity_search);
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
        myContext = this;
        presenter = new PSearch(this);

        addressLayout = findViewById(R.id.search_text_layout);
        addressEditText = findViewById(R.id.date_search_tei);

        calendar = Calendar.getInstance();
        Year = calendar.get(Calendar.YEAR);
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);

        Spinner spinner = (Spinner) findViewById(R.id.spinner_search);
        ArrayList<String> letra = new ArrayList<String>();
        letra.addAll(presenter.getSpinner());
        Collections.sort(letra);
        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, letra));

        //Creamos punteros para el textedit de la fecha
        dateEditText = findViewById(R.id.date_search_tei);
        //Para que el usuario no pueda cambiar la fecha
        dateEditText.setEnabled(false);

        buttonDate = (Button) findViewById(R.id.datePicker_Search);
        buttonDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog = new DatePickerDialog(myContext, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Log.d(TAG, "Clicked OK on Calendar");
                        String day = "" + dayOfMonth;
                        if (dayOfMonth < 10) {
                            day = "0" + dayOfMonth;
                        }
                        String month0 = "" + (month + 1);
                        if (month < 10) {
                            month0 = "0" + month0;
                        }
                        dateEditText.setText(day + "/" + month0 + "/" + year);
                    }
                }, Year, Month, Day);
                datePickerDialog.show();
            }
        });

        Button Searchbutton = findViewById(R.id.button_search);
        Searchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Calling presenter.ClickSearchButton");
                presenter.onClickSearchButton();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        Log.d(TAG, "Loading Menu Options");
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onNavigateUp() {
        // Asignar la acción necesaria. En este caso terminar la actividad
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
    public void SearchButton() {
        Log.d(TAG, "SearchButton Clicked");
        if (addressEditText.getText().length() != 0) {
            String resultado = addressEditText.getText().toString();
            Intent i = getIntent();
            i.putExtra("RESULTADO", resultado);
            setResult(RESULT_OK, i);
            finish();
        } else {
            // Si no tenía nada escrito el EditText lo avisamos.
            //Toast.;
        }
    }
}