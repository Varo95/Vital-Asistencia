package com.vitalasistencia.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
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
    private final int SEARCH = 0;
    private RecyclerView recyclerView;
    //Controla si estamos buscando o no para no volver a cargar el OnCreate
    private boolean isFiltered = false;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "Starting onCreate");
        myContext = this;
        presenter = new PList(this);
        int a_version = presenter.getAndroidVersion();
        //Este número coincide con el código de la version Android 6.0
        final int limit_version = 23;
        //Cargamos un tema u otro dependiendo de la version de android, si es inferior a 6
        //mostramos uno sin imagen vectorial.
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
            System.out.println("Hubo un error en la carga de OnCreate");
        }
        super.onCreate(savedInstanceState);
        SharedPreferences is_first_time = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        if (!is_first_time.getBoolean("firstTime", false)) {
            presenter.tenUsersForFirstTime();
            SharedPreferences.Editor editor = is_first_time.edit();
            editor.putBoolean("firstTime", true);
            editor.commit();
        }
        Log.d(TAG, "Starting Layout");
        setContentView(R.layout.activity_main);
        items = new ArrayList<>();

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

        // Localizamos el reciclerview para cargar el adaptador dentro de él
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_List);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

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
        //Reemplazamos el textview y actualizamos la X en el String
        nUsers = (TextView) findViewById(R.id.textView_User_List);
        String text = nUsers.getText().toString();
        String text1 = text.replace("x", "" + items.size());
        nUsers.setText(text1);
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
        //Este numero es para controlar en el onCreate y en el onResume, pues declarándolo solo en
        //el onResume no me sustituía bien el número cuando creaba un nuevo usuario.
        int sizeOnCreate = items.size();
        if (!isFiltered) {
            items.clear();
            items.addAll(presenter.getAllUsers());
            adapter.notifyDataSetChanged();
        }
        //Actualizamos el textview de los elementos
        String text = nUsers.getText().toString();
        String text1 = text.replace("" + sizeOnCreate, "" + items.size());
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
        startActivityForResult(intent, SEARCH);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "Starting Activity from Search");
        super.onActivityResult(requestCode, resultCode, data);
        // Comprobamos si el resultado de la segunda actividad es "RESULT_OK".
        String temp = "";
        int sizeOnResume = items.size();
        items.clear();
        ArrayList<BUser> searched = new ArrayList<BUser>();

        if (resultCode == RESULT_OK) {
            String address = null, dayWeek = null, date = null;
            if (data.getStringExtra("ADDRESS") != null) address = data.getStringExtra("ADDRESS");
            if (data.getStringExtra("DAYWEEK") != null) dayWeek = data.getStringExtra("DAYWEEK");
            if (data.getStringExtra("DATE") != null) date = data.getStringExtra("DATE");
            if ((address != null && !(address.isEmpty())) || (dayWeek != null && !(dayWeek.isEmpty())) || (date != null && !(date.isEmpty()))) {
                searched.addAll(presenter.getQuery(address, dayWeek, date));
            }
            items.addAll(searched);
            adapter.notifyDataSetChanged();
            //Actualizamos el textview de los elementos
            String text = nUsers.getText().toString();
            String text1 = text.replace("" + sizeOnResume, "" + items.size());
            nUsers.setText(text1);
            isFiltered = true;
        } else {
            Toast.makeText(this, R.string.search_caceled, Toast.LENGTH_SHORT).show();
        }
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
        String text = nUsers.getText().toString();
        String text1 = text.replace("" + items.size(), "" + (items.size() - 1));
        nUsers.setText(text1);
        items.remove(pos);
        adapter.notifyItemRemoved(pos);
    }
}