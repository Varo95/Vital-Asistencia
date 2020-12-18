package com.vitalasistencia.views;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.vitalasistencia.R;
import com.vitalasistencia.interfaces.IForm;
import com.vitalasistencia.models.BUser;
import com.vitalasistencia.presenters.PForm;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;

public class FormActivity extends AppCompatActivity implements IForm.View {

    private String TAG = "Vital_Asistencia/FormActivity";
    private IForm.Presenter presenter;
    private Context myContext;
    private TextInputLayout dateLayout;
    private TextInputEditText dateEditText;
    private TextInputLayout phoneLayout;
    private TextInputEditText phoneEditText;
    private TextInputLayout emailLayout;
    private TextInputEditText emailEditText;
    private TextInputLayout addressLayout;
    private TextInputEditText addressEditText;
    private TextInputLayout affiliateLayout;
    private TextInputEditText affiliateEditText;
    private ArrayList<String> letra = null;
    private Spinner s = null;
    private ArrayAdapter<String> adapter;
    private Button buttonDate;
    private Calendar calendar;
    private DatePickerDialog datePickerDialog;
    private int Year, Month, Day;
    private String id;
    private ImageView imageView_Form;
    private boolean creatinguser = false;
    final private int CODE_WRITE_EXTERNAL_STORAGE_PERMISSION = 123;
    private static final int REQUEST_SELECT_IMAGE = 201;
    final private int CODE_CAMERA = 123;
    private ConstraintLayout constraintLayoutFormActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "Starting onCreate");
        setTheme(R.style.Theme_VitalAsistencia_Form);
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

        //Declaramos el botón de resetear la imagen
        Button resetImage = findViewById(R.id.button_reset_photo_Form);
        resetImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Pressing resetImage button");
                presenter.resetImage();
            }
        });
        //TODO Botón de la cámara

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
        Button addToSpinner = findViewById(R.id.add_spinner);
        addToSpinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Pressing ADD spinner button");
                presenter.onClickAddSpinner();
            }
        });

        //Creamos un nuevo usuario
        BUser user = new BUser();

        //Creamos el puntero hacia el imageview del formulario
        imageView_Form = findViewById(R.id.imageView_Form);
        constraintLayoutFormActivity = findViewById(R.id.CL_FormActivity);
        imageView_Form.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Calling presenter.onClickImage");
                //Llama al presentador para comprobar que tiene permisos de lectura
                //Vuelve a llamar al presentador para añadir una imagen
                presenter.onClickImage(FormActivity.this);
            }
        });

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
        Year = calendar.get(Calendar.YEAR);
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);

        //Creamos el DatePicker de fecha
        buttonDate = (Button) findViewById(R.id.date_picker);
        buttonDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Definir el calendario con la fecha del dia actual
                datePickerDialog = new DatePickerDialog(myContext, new DatePickerDialog.OnDateSetListener() {
                    // Definir accion cuando selecionamos la fecha
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        //No sé por qué exactamente, pero en el mes me introduce el mes actual -1, por eso he añadido un +1
                        String day = "" + dayOfMonth;
                        if (dayOfMonth < 10) {
                            day = "0" + dayOfMonth;
                        }
                        dateEditText.setText(day + "/" + (month + 1) + "/" + year);
                        dateLayout.setError(presenter.getError("Valid"));
                    }
                }, Year, Month, Day);
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

        id = getIntent().getStringExtra("id");
        Log.d(TAG, "Get String");

        if (id != null) {
            //set text noseque id
            affiliateEditText.setText(id);
        } else {
            //Cambia el booleano para usarlo en otras funciones
            creatinguser = true;
        }
        //Botón eliminar o cancelar
        Button CancelButton = findViewById(R.id.Cancel_Form);
        if (creatinguser) {
            //Si se está creando un usuario se deshabilita el botón de Eliminar/Cancelar
            CancelButton.setEnabled(false);
        } else {
            CancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "Calling presenter.ClickSaveButton");
                    presenter.onClickCancelButton();
                }
            });
        }
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
        //Si se está creando un usuario se quita la opción de borrar del menu contextual
        if (creatinguser) {
            menu.getItem(0).setEnabled(false);
        }
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
        String p = "";
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
                                if ((dialogInput.getText().toString().equals(p.toString()))) {
                                    dialogBox.cancel();
                                } else {
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

    @Override
    public void DeleteUser() {
        //Borrar el usuario todavía sin implementar
        Log.d(TAG, "Starting DeleteButton");
        AlertDialog.Builder builder = new AlertDialog.Builder(myContext);
        builder.setTitle(R.string.delete_user_dialog_tittle);
        builder.setMessage(R.string.delete_user_dialog_message);

        //Accept Button
        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                presenter.onClickAcceptDeleteButton();
            }
        });

        //Cancel Button
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case CODE_WRITE_EXTERNAL_STORAGE_PERMISSION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permiso aceptado
                    presenter.AcceptedPermission();
                } else {
                    // Permiso rechazado
                    presenter.DeniedPermission();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    /**
     * This method is called by Pform(Presentor) and show to user if asking or denied permissions
     * @param n code of number
     */
    public void showRequestPermission(int n) {
        switch (n) {
            case 0:
                //Si la versión de android es superior a la 6, se podrá otra vez pedir permisos, por lo tanto aquí mostraría
                //Un mensaje en el snackbar cuando salte el pop-up de permisos
                Snackbar.make(constraintLayoutFormActivity, getResources().getString(R.string.P_write_asking), Snackbar.LENGTH_LONG).show();
                break;
            case 1:
                // Permiso denegado
                Snackbar.make(constraintLayoutFormActivity, getResources().getString(R.string.P_write_denied), Snackbar.LENGTH_LONG).show();
                break;
            default:
        }
    }

    @Override
    public void getImageFromStorage() {
        // Se le pide al sistema una imagen del dispositivo
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(intent, getResources().getString(R.string.choose_picture)),
                REQUEST_SELECT_IMAGE);
    }

    @Override
    public void resetImage() {
        imageView_Form = findViewById(R.id.imageView_Form);
        imageView_Form.setImageBitmap(null);
        imageView_Form.setBackgroundResource(R.mipmap.user_background);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            //TODO implementar el boton de la camara
            /*case (REQUEST_CAPTURE_IMAGE):
                if(resultCode == Activity.RESULT_OK){
                    // Se carga la imagen desde un objeto URI al imageView
                    ImageView imageView = findViewById(R.id.imageView);
                    imageView.setImageURI(uri);

                    // Se le envía un broadcast a la Galería para que se actualice
                    Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    mediaScanIntent.setData(uri);
                    sendBroadcast(mediaScanIntent);
                } else if (resultCode == Activity.RESULT_CANCELED) {
                    // Se borra el archivo temporal
                    File file = new File(uri.getPath());
                    file.delete();
                }
                break;*/
            case (REQUEST_SELECT_IMAGE):
                if (resultCode == Activity.RESULT_OK) {
                    // Se carga la imagen desde un objeto Bitmap
                    Uri selectedImage = data.getData();
                    String selectedPath = selectedImage.getPath();

                    if (selectedPath != null) {
                        // Se leen los bytes de la imagen
                        InputStream imageStream = null;
                        try {
                            imageStream = getContentResolver().openInputStream(selectedImage);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }

                        // Se transformam los bytes de la imagen a un Bitmap
                        Bitmap bmp = BitmapFactory.decodeStream(imageStream);
                        Bitmap imageScaled = Bitmap.createScaledBitmap(bmp, 256, 256, false);

                        // Se carga el Bitmap en el ImageView
                        ImageView imageView = findViewById(R.id.imageView_Form);
                        imageView.setImageBitmap(imageScaled);
                        imageView.setBackground(null);
                    }
                }
                break;
        }
    }
}
