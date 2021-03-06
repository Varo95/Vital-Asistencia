package com.vitalasistencia.views;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.vitalasistencia.R;
import com.vitalasistencia.interfaces.IForm;
import com.vitalasistencia.models.BUser;
import com.vitalasistencia.presenters.PForm;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;

public class FormActivity extends AppCompatActivity implements IForm.View {

    private final String TAG = "V_A/FormActivity";
    private IForm.Presenter presenter;
    private Context myContext;
    private TextInputLayout dateLayout;
    private TextInputEditText dateEditText;
    private SwitchCompat prepareFood;
    private TextInputLayout phoneLayout;
    private TextInputEditText phoneEditText;
    private TextInputLayout emailLayout;
    private TextInputEditText emailEditText;
    private TextInputLayout addressLayout;
    private TextInputEditText addressEditText;
    private TextInputLayout affiliateLayout;
    private TextInputEditText affiliateEditText;
    private ArrayList<String> ArrayDayWeek = null;
    private Spinner dayWeek = null;
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
    private ConstraintLayout constraintLayoutFormActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "Starting onCreate");
        //Mostramos el tema a cargar
        setTheme(R.style.Theme_VitalAsistencia_Form);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        myContext = this;
        presenter = new PForm(this);

        //Creamos el toolbar
        Log.d(TAG, "Starting Toolbar");
        Toolbar toolbar = findViewById(R.id.toolbar);
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
        //Creamos un nuevo usuario
        BUser user = null;
        //Recogemos el ID del extra
        id = getIntent().getStringExtra("id");
        Log.d(TAG, "Get String");

        if (id != null) {
            //Cargamos el usuario a partir de su ID
            user = presenter.getUser(id);
        } else {
            //Cambiamos un boleano para diferenciar en otras partes del código si estamos creando
            //o actualizando un usuario
            creatinguser = true;
            //Y creamos un usuario nuevo que es el que insertaremos posteriormente
            user = new BUser();
        }
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

        //Creamos el puntero hacia el botón de la cámara
        Button buttonCamera = findViewById(R.id.button_camera_Form);
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            buttonCamera.setEnabled(false);
        } else {
            buttonCamera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
                        //presenter.onClickCamera(FormActivity.this);
                    }
                }
            });
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

        //Declaramos el arrayList del Spinner
        ArrayDayWeek = new ArrayList<String>();
        ArrayDayWeek.addAll(presenter.getSpinner());
        ArrayDayWeek.remove("");
        ArrayDayWeek.add(getResources().getString(R.string.weekday_spinner));

        //Creamos el adaptador
        adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, ArrayDayWeek);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        //Creamos el spinner y le inyectamos los valores del adaptador
        dayWeek = findViewById(R.id.spinner_Form);
        dayWeek.setAdapter(adapter);
        int indexOfDW = ArrayDayWeek.indexOf(getResources().getString(R.string.weekday_spinner));
        //Valor por defecto del Spinner
        dayWeek.setSelection(indexOfDW);


        //Boton añadir del spinner
        Button addToSpinner = findViewById(R.id.add_spinner);
        addToSpinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Pressing ADD spinner button");
                presenter.onClickAddSpinner();
            }
        });

        //Creamos punteros para el textedit de la fecha
        dateEditText = findViewById(R.id.date_search_tei);
        dateLayout = findViewById(R.id.date_form);

        BUser finalUser = user;
        dateEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    Log.d(TAG, "date_text has focused");
                    //Comprobamos que el formato de la fecha es el correcto
                    if (dateEditText.getText().toString().matches("")) {
                        //Si la cadena está vacía no mostrará ningún error
                        dateLayout.setError(presenter.getError("Valid"));
                    } else if (!(finalUser.setDate("dd/MM/yyyy", (dateEditText.getText().toString())))) {
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
        buttonDate = findViewById(R.id.date_picker);
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
                        String month0 = "" + (month + 1);
                        if (month < 10) {
                            month0 = "0" + month0;
                        }
                        dateEditText.setText(day + "/" + month0 + "/" + year);
                        dateLayout.setError(presenter.getError("Valid"));
                    }
                }, Year, Month, Day);
                datePickerDialog.show();
            }
        });

        //Creamos punteros para el switch de preparar comida
        prepareFood = findViewById(R.id.switch_food_Form);

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
                    } else if (!(finalUser.setPhone(phoneEditText.getText().toString()))) {
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
                    } else if (!(finalUser.setEmail(emailEditText.getText().toString()))) {
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
                    } else if (!(finalUser.setAddress(addressEditText.getText().toString()))) {
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
        if (!creatinguser) {
            affiliateEditText.setEnabled(false);
        } else {
            affiliateEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean hasFocus) {
                    if (!hasFocus) {
                        Log.d(TAG, "affiliate_text has focused");
                        if (affiliateEditText.getText().toString().matches("")) {
                            affiliateLayout.setError(presenter.getError("Valid"));
                        } else if (!(finalUser.setAffiliate_number(affiliateEditText.getText().toString()))) {
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
        }

        //Botón eliminar o cancelar
        Button CancelButton = findViewById(R.id.Cancel_Form);
        if (creatinguser) {
            //Si se está creando un usuario se deshabilita el botón de Eliminar/Cancelar
            CancelButton.setEnabled(false);
        } else {
            toolbar.setTitle(R.string.updating_user);
            CancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "Calling presenter.ClickSaveButton");
                    presenter.onClickCancelButton();
                }
            });
        }
        //Botón de guardar
        Button SaveButton = findViewById(R.id.Save_Form);
        SaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Calling presenter.ClickSaveButton");
                //Intentamos pasar la imagen a base64
                String result = null;
                try {
                    BitmapDrawable drawable = (BitmapDrawable) imageView_Form.getDrawable();
                    if (drawable == null) {
                        result = "";
                    } else {
                        Bitmap bitmap = Bitmap.createBitmap(drawable.getBitmap());
                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 90, bos);
                        byte[] imageBytes = bos.toByteArray();
                        result = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (result != null) {
                    finalUser.setImage(result);
                } else {
                    finalUser.setImage("");
                }
                if (!(dayWeek.getSelectedItem().equals(MyApp.getContext().getString(R.string.weekday_spinner)))) {
                    finalUser.setDayWeek(dayWeek.getSelectedItem().toString());
                } else {
                    finalUser.setDayWeek("");
                }
                finalUser.setFood(prepareFood.isChecked());
                //Comprobamos que los campos no están vacíos antes de validarlos
                if (dateEditText.getText().toString().equals("")) {
                    showMessageForm(1);
                } else if (phoneEditText.getText().toString().equals("")) {
                    showMessageForm(2);
                } else if (emailEditText.getText().toString().equals("")) {
                    showMessageForm(3);
                } else if (addressEditText.getText().toString().equals("")) {
                    showMessageForm(4);
                } else if (affiliateEditText.getText().toString().equals("")) {
                    showMessageForm(5);
                }
                //Comprobamos que los datos son correctos
                else if (!finalUser.setDate("dd/MM/yyyy", dateEditText.getText().toString())) {
                    dateLayout.setError(presenter.getError("Not valid date"));
                } else if (!(finalUser.setPhone(phoneEditText.getText().toString()))) {
                    phoneLayout.setError(presenter.getError("Not valid phone"));
                } else if (!(finalUser.setEmail(emailEditText.getText().toString()))) {
                    emailLayout.setError(presenter.getError("Not valid email"));
                } else if (!(finalUser.setAddress(addressEditText.getText().toString()))) {
                    addressLayout.setError(presenter.getError("Not valid address"));
                } else if (!(finalUser.setAffiliate_number(affiliateEditText.getText().toString()))) {
                    affiliateEditText.setError(presenter.getError("Not valid affiliate"));
                } else {
                    presenter.onClickSaveButton(finalUser);
                }
            }
        });
        if (!creatinguser) {
            try {
                String temp = "";
                if (!(user.getImage().equals(temp))) {
                    byte[] decodedString = Base64.decode(user.getImage(), Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    imageView_Form.setImageBitmap(decodedByte);
                    imageView_Form.setBackground(null);
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error desconocido");
            }
            int temp2 = ArrayDayWeek.indexOf(user.getDayWeek());
            dayWeek.setSelection(temp2);
            prepareFood.setChecked(user.getFood());
            dateEditText.setText(user.getDate());
            phoneEditText.setText(user.getPhone());
            emailEditText.setText(user.getEmail());
            addressEditText.setText(user.getAddress());
            affiliateEditText.setText(id);
        }

    }

    @Override
    public void showMessageForm(int code) {
        switch (code) {
            //Llamar al presentador para que muestre un mensaje de que NO puede dejar los campos vacíos
            case 0:
                Toast.makeText(FormActivity.this, getString(R.string.user_Inserted_Accepted), Toast.LENGTH_SHORT).show();
                break;
            case 1:
                Snackbar.make(constraintLayoutFormActivity, presenter.getError("Not valid date"), Snackbar.LENGTH_LONG).show();
                dateLayout.setError(presenter.getError("Unfilled Field"));
                break;
            case 2:
                Snackbar.make(constraintLayoutFormActivity, presenter.getError("Not valid phone"), Snackbar.LENGTH_LONG).show();
                phoneLayout.setError(presenter.getError("Unfilled Field"));
                break;
            case 3:
                Snackbar.make(constraintLayoutFormActivity, presenter.getError("Not valid email"), Snackbar.LENGTH_LONG).show();
                emailLayout.setError(presenter.getError("Unfilled Field"));
                break;
            case 4:
                Snackbar.make(constraintLayoutFormActivity, presenter.getError("Not valid address"), Snackbar.LENGTH_LONG).show();
                addressLayout.setError(presenter.getError("Unfilled Field"));
                break;
            case 5:
                Snackbar.make(constraintLayoutFormActivity, presenter.getError("Not valid affiliate"), Snackbar.LENGTH_LONG).show();
                affiliateLayout.setError(presenter.getError("Unfilled Field"));
                break;
            case 6:
                Snackbar.make(constraintLayoutFormActivity, presenter.getError("User cannot be Inserted"), Snackbar.LENGTH_LONG).show();
                affiliateLayout.setError(presenter.getError("User cannot be Inserted"));
                break;
            case 7:
                Toast.makeText(FormActivity.this, getString(R.string.user_Updated), Toast.LENGTH_SHORT).show();
                break;
            default:
                Snackbar.make(constraintLayoutFormActivity, presenter.getError("Error"), Snackbar.LENGTH_LONG).show();
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
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_delete) {
            Log.d(TAG, "Menu Delete click");
            presenter.onClickCancelButton();
            return true;
        }
        if (id == R.id.action_reset) {
            Log.d(TAG, "Menu Reset click");
            presenter.onClickResetForm();
            return true;
        }
        if (id == R.id.action_help) {
            Log.d(TAG, "Menu Help click");
            presenter.onClickHelpButton();
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
    public void endActivity() {
        Log.d(TAG, "Starting SaveButton");
        finish();
    }
    @Override
    public void startHelpActivity() {
        Log.d(TAG, "Starting Help Activity");
        Intent intent = new Intent(FormActivity.this, HelpActivity.class);
        intent.putExtra("activity","form");
        startActivity(intent);
    }

    @Override
    public void onClickAddSpinner() {
        String p = "";
        Log.d(TAG, "Adding to spinner");
        LayoutInflater layoutActivity = LayoutInflater.from(myContext);
        View viewAlertDialog = layoutActivity.inflate(R.layout.alert_dialog, null);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(myContext);
        alertDialog.setView(viewAlertDialog);
        final EditText dialogInput = viewAlertDialog.findViewById(R.id.dialogInput);
        alertDialog.setCancelable(false)
                // Botón Añadir
                .setPositiveButton(getResources().getString(R.string.add),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                if ((dialogInput.getText().toString().equals(p))) {
                                    dialogBox.cancel();
                                } else {
                                    adapter.add(dialogInput.getText().toString());
                                    dayWeek.setSelection(adapter.getPosition(dialogInput.getText().toString()));
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
    public void deleteUser() {
        //Borrar el usuario todavía sin implementar
        Log.d(TAG, "Starting DeleteButton");
        AlertDialog.Builder builder = new AlertDialog.Builder(myContext);
        builder.setTitle(R.string.delete_user_dialog_tittle);
        builder.setMessage(R.string.delete_user_dialog_message);

        //Accept Button
        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                presenter.onClickAcceptDeleteButton(id);
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
                    presenter.acceptedPermission();
                } else {
                    // Permiso rechazado
                    presenter.deniedPermission();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    /**
     * This method is called by Pform(Presentor) and show to user if asking or denied permissions
     *
     * @param n code of number
     */
    @Override
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
    public void resetForm() {
        if (id != null) {
            affiliateEditText.setText(id);
            BUser temp = presenter.getUser(id);
            dateEditText.setText(temp.getDate());
            phoneEditText.setText(temp.getPhone());
            emailEditText.setText(temp.getEmail());
            addressEditText.setText(temp.getAddress());
        } else {
            affiliateEditText.setText("");
            dateEditText.setText("");
            phoneEditText.setText("");
            emailEditText.setText("");
            addressEditText.setText("");
        }
        dateLayout.setError(presenter.getError("Valid"));
        phoneLayout.setError(presenter.getError("Valid"));
        emailLayout.setError(presenter.getError("Valid"));
        addressLayout.setError(presenter.getError("Valid"));
        affiliateLayout.setError(presenter.getError("Valid"));
        imageView_Form.setImageBitmap(null);
        imageView_Form.setBackgroundResource(R.mipmap.user_background);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
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