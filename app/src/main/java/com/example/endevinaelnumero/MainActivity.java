package com.example.endevinaelnumero;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    public static int intentos = 0;
    public int randomNumber;
    public static String nombre;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_TAKE_PHOTO = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        randomNumber = (int) (Math.random() * 100);
        Button button = findViewById(R.id.button);
        EditText number = findViewById(R.id.editTextNumber);
        TextView textIntentos = findViewById(R.id.textView2);
        textIntentos.setText("Intentos: " + intentos);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String toastText = "";
                if (number.getText().toString().equals("")) {
                    toastText = "Introduce un numero";
                } else {
                    int numberSelected = Integer.parseInt(number.getText().toString());
                    intentos = intentos + 1;
                    textIntentos.setText("Intentos: " + intentos);
                    if (numberSelected > randomNumber) {
                        toastText = "El numero introducido es mas grande que el numero a adivinar";
                    } else if (numberSelected < randomNumber) {
                        toastText = "El numero introducido es mas pequeño que el numero a adivinar";
                    } else {
                        toastText = "Has acertado el numero!";
                        /*
                        try {
                            dispatchTakePictureIntent();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                         */
                        int intentosFinales = intentos;
                        Dialog dialog = new Dialog(MainActivity.this);
                        dialog.setContentView(R.layout.dialog);
                        Button okButton = dialog.findViewById(R.id.button2);
                        Button cancelButton = dialog.findViewById(R.id.button3);
                        okButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                EditText nameEdit = dialog.findViewById(R.id.nombre);
                                nombre = nameEdit.getText().toString();
                                if (nombre.equals("")) {
                                    Toast toast = Toast.makeText(getApplicationContext(), "Introduce un nombre", Toast.LENGTH_SHORT);
                                    toast.show();
                                } else {
                                    Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
                                    intent.putExtra("INTENTOS", intentosFinales);
                                    intent.putExtra("NOMBRE", nombre);

                                    startActivity(intent);
                                    dialog.dismiss();
                                }
                            }
                        });
                        cancelButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        });
                        dialog.show();

                        intentos = 0;
                        textIntentos.setText("Intentos: " + intentos);
                        randomNumber = (int) (Math.random() * 100);
                    }
                }
                number.setText("");
                Toast toast = Toast.makeText(getApplicationContext(), toastText, Toast.LENGTH_SHORT);
                toast.show();
            }
        });
        number.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_DONE) {
                    button.callOnClick();
                    return true;
                }
                return false;
            }
        });
    }
}
