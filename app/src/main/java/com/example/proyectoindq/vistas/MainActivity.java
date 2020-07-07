package com.example.proyectoindq.vistas;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.proyectoindq.R;
import com.example.proyectoindq.datos.Login;
import com.example.proyectoindq.remote.APIService;
import com.example.proyectoindq.remote.ApiUtils;

public class MainActivity extends AppCompatActivity {
    private Button registra, acceder;
    private EditText correo, contrasena;
    private APIService mAPIService;;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.registra = findViewById(R.id.btn_sign);
        this.acceder = findViewById(R.id.btn_acceder);
        //Este boton nos envia a la pantalla de registro para crear una cuenta
        registra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),Registrar.class);
                startActivity(intent);
            }
        });
        this.correo = findViewById(R.id.edt_Email);
        this.contrasena = findViewById(R.id.edt_Password);

        this.mAPIService = ApiUtils.getAPIApiService();
        //Este boton nos envia a la pantalla de eventos
        this.acceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String correo_String = correo.getText().toString();
                String contrasena_String = contrasena.getText().toString();
                //Validamos si hay un campo vacio en el editText, si no enviamos los datos al servidor
                if(!TextUtils.isEmpty(correo_String) && !TextUtils.isEmpty(contrasena_String)){
                    sendLogin(correo_String,contrasena_String);
                }else{
                    Toast.makeText(getApplicationContext(),"Ningun campo debe estar vacio",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private static String token;//Token para acceder a los eventos
    public void sendLogin(String correoP, String contrasenaP){
        mAPIService.saveLogin(correoP,contrasenaP).enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Sesion iniciada",Toast.LENGTH_LONG).show();
                    token = response.body().getToken();//Obtenemos el token y lo guardamos en una variable static
                    Intent event = new Intent(getApplicationContext(),Main_Eventos.class);
                    startActivity(event);
                }else{
                    Toast login = Toast.makeText(getApplicationContext(),"Usuario o contraseña incorrecta",Toast.LENGTH_LONG);
                    login.show();
                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                Toast error = Toast.makeText(getApplicationContext(),t.getLocalizedMessage(),Toast.LENGTH_LONG);
                error.show();
            }
        });
    }
    /*
    Este metodo envia el token al servidor para validar el acceso
     */
    public String sendToken(){
        return token;
    }

}