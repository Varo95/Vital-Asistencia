package com.vitalasistencia.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.vitalasistencia.R;
import com.vitalasistencia.interfaces.IList;
import com.vitalasistencia.models.BUser;
import com.vitalasistencia.presenters.PList;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements IList.View {
    String TAG = "Vital_Asistencia/MainActivity";

    private IList.Presenter presenter;
    private Context myContext;
    private ArrayList<BUser> items;

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
        Log.d(TAG,"Starting Layout");
        setContentView(R.layout.activity_main);
        items = new ArrayList<BUser>();
        BUser a=new BUser();
        a.setAffiliate_number("111AAAAA");
        a.setEmail("test@test.com");
        a.setAddress("C/ Ejemplo");
        a.setImage("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");
        items.add(a);
        BUser b=new BUser();
        b.setAffiliate_number("112AAAAA");
        b.setEmail("test1@test1.com");
        b.setAddress("C/ Ejemplo1");
        items.add(b);
        Log.d(TAG, "Starting Toolbar");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        myContext = this;
        presenter = new PList(this);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Button Add Clicked");
                presenter.onClickFloatingButton();
            }
        });

        // Inicializa el RecyclerView
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView_List);

        // Crea el Adaptador con los datos de la lista anterior
        UserAdapter adapter = new UserAdapter(items);


        // Asocia el elemento de la lista con una acción al ser pulsado
        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Acción al pulsar el elemento
                int position = recyclerView.getChildAdapterPosition(v);
                /*Toast.makeText(MainActivity.this, "Posición: " + String.valueOf(position) + " Id: " + items.get(position).getEmail() + " Nombre: " + items.get(position).getAddress(), Toast.LENGTH_SHORT).show();*/
                presenter.onClickReciclerViewItem(items.get(position).getAffiliate_number());
            }
        });


        // Asocia el Adaptador al RecyclerView
        recyclerView.setAdapter(adapter);

        // Muestra el RecyclerView en vertical
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        Log.d(TAG, "Loading Menu Options");
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
        if (id == R.id.action_about) {
            Log.d(TAG, "Menu About click");
            presenter.onClickAboutButton();
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
        Log.d(TAG,"Starting Form Activity");
        Intent intent = new Intent(MainActivity.this, FormActivity.class);
        startActivity(intent);
    }

    @Override
    public void startSearchActivity() {
        Log.d(TAG,"Starting Search Activity");
        Intent intent = new Intent(MainActivity.this, SearchActivity.class);
        startActivity(intent);
    }

    @Override
    public void startAboutActivity() {
        Log.d(TAG,"Starting About Activity");
        Intent intent = new Intent(MainActivity.this, AboutActivity.class);
        startActivity(intent);
    }

    @Override
    public void startFormActivity(String affiliate_number) {
        Log.d(TAG,"Editing User with id: "+affiliate_number);
        Intent intent = new Intent(MainActivity.this, FormActivity.class);
        intent.putExtra("id",affiliate_number);
        startActivity(intent);
    }
}