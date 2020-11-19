package com.vitalasistencia.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.vitalasistencia.R;
import com.vitalasistencia.interfaces.IList;
import com.vitalasistencia.presenters.PList;

public class MainActivity extends AppCompatActivity implements IList.View {
    String TAG = "Vital_Asistencia/MainActivity";

    private IList.Presenter presenter;
    private Context myContext;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "Starting onCreate");
        setTheme(R.style.AppTheme_NoActionBar);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "Starting Toolbar");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        myContext = this;
        presenter = new PList(this);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Button Clicked");
                /*Rafa aquí no te he sabido implementar el modelo vista-presentador
                Cuando tengas tiempo me lo vuelves a explicar(a ser posible en persona)
                con un ejemplo práctico. Lo que me falla es llevarlo a la práctica.
                */
                presenter.onClickFloatingButton();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        Log.d(TAG, "Cargando Opciones Menu");
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_search) {
            Log.d(TAG, "Menu Search click");
            presenter.onClickSearchButton();
            return true;
        }

        return super.onOptionsItemSelected(item);
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
    public void startFormActivity() {
        Intent intent = new Intent(MainActivity.this, FormActivity.class);
        startActivity(intent);
    }

    @Override
    public void startSearchActivity() {
        Intent intent = new Intent(MainActivity.this, SearchActivity.class);
        startActivity(intent);

    }
}