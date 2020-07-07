package com.example.proyectoindq.vistas;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.proyectoindq.R;
import com.example.proyectoindq.datos.Events;
import com.example.proyectoindq.remote.APIService;
import com.example.proyectoindq.remote.ApiUtils;

import java.util.ArrayList;
import java.util.List;

public class Create_Events extends AppCompatActivity {
    private EditText id,titulo,descripcion,fecha,imagen,asistencia;
    private Button btn_CrearEvento2,btn_CancelarEv;
    private APIService mAPIService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create__events);
        this.id = findViewById(R.id.edt_idEvent);
        this.titulo = findViewById(R.id.edt_tittleEvent);
        this.descripcion = findViewById(R.id.edt_descriptionEvent);
        this.fecha = findViewById(R.id.edt_dateEvent);
        this.imagen = findViewById(R.id.edt_imageEvent);
        this.asistencia = findViewById(R.id.edt_attendancesEvent);
        this.btn_CrearEvento2 = findViewById(R.id.btn_CrearEventoCreate);
        this.btn_CancelarEv = findViewById(R.id.btn_CancelarCrearEvento);
        //Este boton regresa al main principal de eventos
        this.btn_CancelarEv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Main_Eventos.class);
                startActivity(intent);
            }
        });
        this.mAPIService = ApiUtils.getAPIApiService();
        btn_CrearEvento2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id_String = id.getText().toString();
                String titulo_String = titulo.getText().toString();
                String descripcion_String = descripcion.getText().toString();
                String fecha_String = fecha.getText().toString();
                String imagen_String = imagen.getText().toString();
                int asistencia_in = Integer.parseInt(asistencia.getText().toString());
                //Enviamos los datos obtenidos al metodo sendEvent
               sendEvent(id_String,titulo_String,descripcion_String,fecha_String,imagen_String,asistencia_in);
            Toast.makeText(getApplicationContext(),"Entro",Toast.LENGTH_LONG).show();
            }
        });
    }
    //Obtenemos el token para poder crear el evento
    MainActivity token = new MainActivity();
    String i = token.sendToken();

    /*
    Este metodo envia los datos obtenidos al servidor para asi crear el evento
     */
    public void sendEvent(String id_send,String titulo_send,String descripcion_send,String fecha_send, String imagen_send,int asistencia_send){
        List<Integer> lista = new ArrayList<>();
        lista.add(1);
        mAPIService.saveEvent(i,id_send,titulo_send,descripcion_send,fecha_send,imagen_send,asistencia_send,true,lista).enqueue(new Callback<Events>() {
            @Override
            public void onResponse(Call<Events> call, Response<Events> response) {
                //Enviamos mensaje de que se creo correctamente el evento
            if(response.isSuccessful()){
                Toast.makeText(getApplicationContext(),"Creado correctamente",Toast.LENGTH_LONG).show();
            }else{
                //Si no se crea enviamos un mensaje de error
                Toast.makeText(getApplicationContext(),"No se pudo crear: "+response.toString(),Toast.LENGTH_LONG).show();
            }
            }

            @Override
            public void onFailure(Call<Events> call, Throwable t) {
            Toast.makeText(getApplicationContext(),"Error2:"+t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
}