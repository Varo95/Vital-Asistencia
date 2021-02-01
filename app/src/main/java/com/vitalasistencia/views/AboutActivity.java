package com.vitalasistencia.views;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.vitalasistencia.R;
import com.vitalasistencia.interfaces.IAbout;
import com.vitalasistencia.presenters.PAbout;

public class AboutActivity extends AppCompatActivity implements IAbout.View {

    private String TAG = "Vital_Asistencia/AboutActivity";
    private IAbout.Presenter presenter;
    private TextView version;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "Starting on Create");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        //Mostramos el tema a cargar
        setTheme(R.style.Theme_VitalAsistencia_About);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(R.string.About);
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
        presenter = new PAbout(this);
        version = (TextView) findViewById(R.id.app_version);
        String text=version.getText().toString();
        String text1=text.replace("x",""+presenter.getappversion());
        version.setText(text1);
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
}