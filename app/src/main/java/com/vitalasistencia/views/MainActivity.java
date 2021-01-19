package com.vitalasistencia.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.vitalasistencia.R;
import com.vitalasistencia.helper.MyButtonClickListener;
import com.vitalasistencia.helper.SwipeHelper;
import com.vitalasistencia.interfaces.IList;
import com.vitalasistencia.models.BUser;
import com.vitalasistencia.presenters.PList;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements IList.View {
    String TAG = "Vital_Asistencia/MainActivity";

    private IList.Presenter presenter;
    private Context myContext;
    private ArrayList<BUser> items;
    private UserAdapter adapter;
    private TextView nUsers;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "Starting onCreate");
        //Este código lo he puesto porque el splash me crasheaba en Android 5.0 con la pantalla en
        //horizontal, se cerraba la app si ya la tenía iniciada y si la iniciaba en horizontal
        //provocaba el reinicio del dispositivo
        //PD: Debería de haberte hecho caso con el splash, pues era el problema >.<"
        myContext = this;
        presenter = new PList(this);
        int a_version = presenter.getAndroidVersion();
        //Este número coincide con el código de la version Android 6.0
        final int limit_version = 23;
        if (a_version >= limit_version) {
            setTheme(R.style.Theme_VitalAsistencia_List);
        } else {
            setTheme(R.style.Theme_VitalAsistencia_List_Loadv21);
        }
        try {
            //Esto es para que tarde 2 segundos el splash. Si se quita seguiría apareciendo, pero
            //dependería de la rapidez del móvil el tiempo del splash (más lento, más duraría)
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            //e.printStackTrace();
            System.out.println("Hubo un error en la carga de OnCreate");
        }
        super.onCreate(savedInstanceState);
        Log.d(TAG, "Starting Layout");
        setContentView(R.layout.activity_main);
        items = new ArrayList<>();
        items.addAll(presenter.getAllUsers());

        Log.d(TAG, "Starting Toolbar");
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
        adapter = new UserAdapter(items);


        // Asocia el elemento de la lista con una acción al ser pulsado
        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Acción al pulsar el elemento
                int position = recyclerView.getChildAdapterPosition(v);
                presenter.onClickReciclerViewItem(items.get(position).getAffiliate_number());
            }
        });


        // Asocia el Adaptador al RecyclerView
        recyclerView.setAdapter(adapter);

        // Muestra el RecyclerView en vertical
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Actualizamos el texto del textview
        nUsers = (TextView) findViewById(R.id.textView_User_List);
        String text=nUsers.getText().toString();
        String text1=text.replace("x",""+items.size());
        nUsers.setText(text1);

        SwipeHelper swipeHelper = new SwipeHelper(this, recyclerView, 200) {

            @Override
            public void instantiateMyButton(RecyclerView.ViewHolder viewHolder, ArrayList<SwipeHelper.MyButton> buffer) {
                buffer.add(new MyButton(MainActivity.this,
                        "Delete", 24,
                        R.drawable.ic_delete_white_24dp,
                        Color.parseColor("#FF3C30"), new MyButtonClickListener() {
                    @Override
                    public void onClick(int pos) {
                        Log.d(TAG, "Onclick delete item " + pos + "from list");
                        Toast.makeText(MainActivity.this, getString(R.string.deleted_user) + " " + items.get(pos).getAffiliate_number(), Toast.LENGTH_SHORT).show();
                        presenter.onClickSwipeDelete(pos, items.get(pos));
                    }
                }));
                buffer.add(new MyButton(MainActivity.this,
                        "Update", 24,
                        R.drawable.ic_edit_white_24dp,
                        Color.parseColor("#FF9502"), new MyButtonClickListener() {
                    @Override
                    public void onClick(int pos) {
                        Log.d(TAG, "Onclick update item " + pos + "from list");
                        //Toast.makeText(MainActivity.this, "Update Click",Toast.LENGTH_SHORT).show();
                        presenter.onClickSwipeEdit(items.get(pos).getAffiliate_number());
                    }
                }));
            }
        };
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
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Log.d(TAG, "Menu Settings click");
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
        int first_size=items.size();
        items = new ArrayList<>();
        items.addAll(presenter.getAllUsers());
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView_List);

        // Crea el Adaptador con los datos de la lista anterior
        adapter = new UserAdapter(items);
        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Acción al pulsar el elemento
                int position = recyclerView.getChildAdapterPosition(v);
                presenter.onClickReciclerViewItem(items.get(position).getAffiliate_number());
            }
        });
        recyclerView.setAdapter(adapter);
        //Actualizamos el textview de los elementos
        nUsers = (TextView) findViewById(R.id.textView_User_List);
        String text=nUsers.getText().toString();
        String text1=text.replace(""+first_size,""+items.size());
        nUsers.setText(text1);
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
        Log.d(TAG, "Starting Form Activity");
        Intent intent = new Intent(MainActivity.this, FormActivity.class);
        startActivity(intent);
    }

    @Override
    public void startSearchActivity() {
        Log.d(TAG, "Starting Search Activity");
        Intent intent = new Intent(MainActivity.this, SearchActivity.class);
        startActivity(intent);
    }

    @Override
    public void startAboutActivity() {
        Log.d(TAG, "Starting About Activity");
        Intent intent = new Intent(MainActivity.this, AboutActivity.class);
        startActivity(intent);
    }

    @Override
    public void startFormActivity(String affiliate_number) {
        Log.d(TAG, "Editing User with id: " + affiliate_number);
        Intent intent = new Intent(MainActivity.this, FormActivity.class);
        intent.putExtra("id", affiliate_number);
        startActivity(intent);
    }

    @Override
    public void deleteUser(int pos) {
        nUsers = (TextView) findViewById(R.id.textView_User_List);
        String text=nUsers.getText().toString();
        String text1=text.replace(""+items.size(),""+(items.size()-1));
        nUsers.setText(text1);
        items.remove(pos);
        adapter.notifyItemRemoved(pos);
    }
}