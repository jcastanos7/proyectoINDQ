package com.example.proyectoindq.vistas;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.proyectoindq.R;
import com.example.proyectoindq.datos.User;
import com.example.proyectoindq.remote.APIService;
import com.example.proyectoindq.remote.ApiUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Registrar extends AppCompatActivity{

    private EditText apellido,nombre,correo,contrasena,contrasena2;
    private RadioButton mujer,hombre;
    private Button btn_registrar, btn_cancelar;
    private APIService mAPIService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);
        this.apellido = findViewById(R.id.edt_LastName);
        this.nombre = findViewById(R.id.edt_FirstName);
        this.correo = findViewById(R.id.edtSign_Email);
        this.contrasena = findViewById(R.id.edtSign_Password);
        this.contrasena2 = findViewById(R.id.edtSign_PasswordConf);
        this.mujer = findViewById(R.id.rb_mujer);
        this.hombre = findViewById(R.id.rb_hombre);
        this.btn_registrar = findViewById(R.id.btnSign_Registrar);
        //this.btn_registrar.setOnClickListener(this);
        this.btn_cancelar = findViewById(R.id.btn_Cancelar);

        btn_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        this.mAPIService = ApiUtils.getAPIApiService();

        btn_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               //se validan los campos vacios y si hay alguno vacio envia un mensaje de error
                if (apellido.getText().toString().trim().length() <= 0 || nombre.getText().toString().trim().length() <= 0 || correo.getText().toString().trim().length() <= 0 ||
                        contrasena.getText().toString().trim().length() <= 0 || contrasena2.getText().toString().trim().length() <= 0) {
                    Toast.makeText(getApplicationContext(), "No debe haber campos vacios", Toast.LENGTH_LONG).show();
                } else if (!validateEmail(correo.getText().toString().trim())) {
                    Toast.makeText(getApplicationContext(), "Formato incorrecto de email", Toast.LENGTH_LONG).show();
                }else if(validatePassword(contrasena.getText().toString().trim(),contrasena2.getText().toString())==false){
                    Toast.makeText(getApplicationContext(), "Las contraseñas no coinciden", Toast.LENGTH_LONG).show();
                } else if(contrasena.getText().toString().trim().length()<8){//valida si el usuario ingreso menos de 8 caracteres
                    Toast.makeText(getApplicationContext(), "La contraseña debe tener al menos 8 caracteres", Toast.LENGTH_LONG).show();
                }else {
                    String apellido_String = apellido.getText().toString().trim();
                    String nombre_String = nombre.getText().toString().trim();
                    String correo_String = correo.getText().toString().trim();
                    String contrasena_String = contrasena.getText().toString().trim();

                    //Obtenemos los datos que el usuario selecciono en el boton y guardamos el resultado en una variable
                    String seleccionado = "";
                    if (mujer.isChecked() == true) {
                        seleccionado = "female";
                    } else if (hombre.isChecked() == true) {
                        seleccionado = "male";
                    }
                        sendPost(nombre_String, apellido_String, correo_String, contrasena_String, seleccionado);
                }
            }
        });
    }
    /*
    Este metodo envia los datos de registro al servidor
     */
    public void sendPost(String nombreP, String apellidoP, String correoP, String contrasenaP, String selecP){
        mAPIService.savePost(nombreP,apellidoP,correoP,contrasenaP,selecP).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                    if(response.isSuccessful()){
                        Toast.makeText(getApplicationContext(),"Registrado" , Toast.LENGTH_LONG).show();
                       Intent login = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(login);
                    }else{
                        Toast error = Toast.makeText(getApplicationContext(), "error",Toast.LENGTH_LONG);
                        error.show();
                    }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast muestra = Toast.makeText(getApplicationContext(),t.getLocalizedMessage(),Toast.LENGTH_LONG);
                muestra.show();
            }
        });
    }
        /*
        Este metodo valida el correo ingresado por el usuario para que tenga el siguiente formato: correo@hotmail.com
         */
    public final static boolean validateEmail(CharSequence email){
        String expresiones = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expresiones, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
        }
        /*
        Este metodo es para comparar las dos contraseñas que el usuario puso y saber si son iguales o no
         */
    public boolean validatePassword(String pass1, String pass2){
        if(pass1.equals(pass2)){
            return true;
        }else{
            return false;
        }
    }


}


