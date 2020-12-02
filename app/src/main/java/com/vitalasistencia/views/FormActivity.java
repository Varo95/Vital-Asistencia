package com.vitalasistencia.views;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
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
import java.util.Calendar;

public class FormActivity extends AppCompatActivity implements IForm.View {

    String TAG = "Vital_Asistencia/FormActivity";
    private IForm.Presenter presenter;
    private Context myContext;
    TextInputLayout dateLayout;
    TextInputEditText dateEditText;
    TextInputLayout phoneLayout;
    TextInputEditText phoneEditText;
    TextInputLayout emailLayout;
    TextInputEditText emailEditText;
    TextInputLayout addressLayout;
    TextInputEditText addressEditText;
    TextInputLayout affiliateLayout;
    TextInputEditText affiliateEditText;
    private ArrayList<String> letra = null;
    private Spinner s = null;
    private ArrayAdapter<String> adapter;
    private Button buttonDate;
    Calendar calendar ;
    private DatePickerDialog datePickerDialog ;
    int Year, Month, Day ;

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
                Log.d(TAG, "Pressing ADD spinner button");
                presenter.onClickAddSpinner();
            }
        });

        //Creamos un nuevo usuario
        BUser user = new BUser();

        //Creamos punteros para el textedit de la fecha
        dateEditText = findViewById(R.id.TEI_date_search);
        dateLayout = findViewById(R.id.date_form);

        dateEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    Log.d(TAG, "date_text has focused");
                    //Comprobamos que el formato de la fecha es el correcto
                    if (dateEditText.getText().toString().matches("")) {
                        //Si la cadena está vacía no mostrará ningún error
                        dateLayout.setError(presenter.getError("Valid"));
                    } else if (!(user.setDate("dd/MM/yyyy", (dateEditText.getText().toString())))) {
                        //Si la cadena contiene una fecha invalida, mostrará este error(mirar BUser.setdate())
                        dateLayout.setError(presenter.getError("Not valid date"));
                    } else {
                        dateLayout.setError(presenter.getError("Valid"));
                    }
                } else {
                    Log.d(TAG, "date_text is unfocused");
                }
            }
        });

        //Inicializamos el calendario
        calendar = Calendar.getInstance();
        Year = calendar.get(Calendar.YEAR) ;
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);

        //Creamos el DatePicker de fecha
        buttonDate=(Button)findViewById(R.id.date_picker);
        buttonDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Definir el calendario con la fecha del dia actual
                datePickerDialog = new DatePickerDialog(myContext, new DatePickerDialog.OnDateSetListener() {
                    // Definir accion cuando selecionamos la fecha
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        //No sé por qué exactamente, pero en el mes me introduce el mes actual -1, por eso he añadido un +1
                        dateEditText.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                },Year, Month, Day);
                datePickerDialog.show();
            }
        });

        //Creamos punteros para el textedit del telefono
        phoneEditText = findViewById(R.id.TEI_phone);
        phoneLayout = findViewById(R.id.phone_form);
        //Hacemos una operación equivalente a la anterior pero aplicandolo al número
        phoneEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    Log.d(TAG, "phone_text has focused");
                    if (phoneEditText.getText().toString().matches("")) {
                        phoneLayout.setError(presenter.getError("Valid"));
                    } else if (!(user.setPhone(phoneEditText.getText().toString()))) {
                        //No será valido si no coincide con la expresión regular de BUser.setPhone
                        phoneLayout.setError(presenter.getError("Not valid phone"));
                    } else {
                        phoneLayout.setError(presenter.getError("Valid"));
                    }
                } else {
                    Log.d(TAG, "phone_text is unfocused");
                }
            }
        });
        //Creamos punteros para el textedit de email
        emailEditText = findViewById(R.id.TEI_email);
        emailLayout = findViewById(R.id.email_form);
        //Hacemos una operación equivalente a la anterior pero aplicandolo al email
        emailEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    Log.d(TAG, "email_text has focused");
                    if (emailEditText.getText().toString().matches("")) {
                        emailLayout.setError(presenter.getError("Valid"));
                    } else if (!(user.setEmail(emailEditText.getText().toString()))) {
                        //No será valido si no coincide con la API de BUser.setEmail
                        emailLayout.setError(presenter.getError("Not valid email"));
                    } else {
                        emailLayout.setError(presenter.getError("Valid"));
                    }
                } else {
                    Log.d(TAG, "email_text is unfocused");
                }
            }
        });
        //Creamos punteros para el textedit de address
        addressEditText = findViewById(R.id.TEI_address);
        addressLayout = findViewById(R.id.address_form);
        //Hacemos una operación equivalente a la anterior pero aplicandolo al address
        addressEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    Log.d(TAG, "adress_text has focused");
                    if (addressEditText.getText().toString().matches("")) {
                        addressLayout.setError(presenter.getError("Valid"));
                    } else if (!(user.setAddress(addressEditText.getText().toString()))) {
                        //No será valido si no coincide con la expresión regular de BUser.setAddress
                        addressLayout.setError(presenter.getError("Not valid address"));
                    } else {
                        addressLayout.setError(presenter.getError("Valid"));
                    }
                } else {
                    Log.d(TAG, "address_text is unfocused");
                }
            }
        });
        //Creamos punteros para el textedit de affiliate
        affiliateEditText = findViewById(R.id.TEI_affiliate_number);
        affiliateLayout = findViewById(R.id.affiliate_number_form);
        //Hacemos una operación equivalente a la anterior pero aplicandolo al affiliate
        affiliateEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    Log.d(TAG, "affiliate_text has focused");
                    if (affiliateEditText.getText().toString().matches("")) {
                        affiliateLayout.setError(presenter.getError("Valid"));
                    } else if (!(user.setAffiliate_number(affiliateEditText.getText().toString()))) {
                        //No será valido si no coincide con la expresión regular de BUser.setAffiliate
                        affiliateLayout.setError(presenter.getError("Not valid affiliate"));
                    } else {
                        affiliateLayout.setError(presenter.getError("Valid"));
                    }
                } else {
                    Log.d(TAG, "affiliate_text is unfocused");
                }
            }
        });
        //Botón de guardar
        Button SaveButton = findViewById(R.id.Save_Form);
        SaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Calling presenter.ClickSaveButton");
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
    public void SaveUser() {
        Log.d(TAG, "Starting SaveButton");
        finish();
    }

    @Override
    public void onClickAddSpinner() {
        String p="";
        Log.d(TAG, "Adding to spinner");
        LayoutInflater layoutActivity = LayoutInflater.from(myContext);
        View viewAlertDialog = layoutActivity.inflate(R.layout.alert_dialog, null);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(myContext);
        alertDialog.setView(viewAlertDialog);
        final EditText dialogInput = (EditText) viewAlertDialog.findViewById(R.id.dialogInput);
        alertDialog.setCancelable(false)
                // Botón Añadir
                .setPositiveButton(getResources().getString(R.string.add),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                if((dialogInput.getText().toString().equals(p.toString()))){
                                    dialogBox.cancel();
                                    s.setSelection(adapter.getPosition(dialogInput.getText().toString()));
                                }else{
                                    adapter.add(dialogInput.getText().toString());
                                    s.setSelection(adapter.getPosition(dialogInput.getText().toString()));
                                }
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