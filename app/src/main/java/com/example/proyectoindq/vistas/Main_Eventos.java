package com.example.proyectoindq.vistas;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.proyectoindq.R;
import com.example.proyectoindq.datos.Events;
import com.example.proyectoindq.remote.APIService;
import com.example.proyectoindq.remote.ApiUtils;

import java.util.ArrayList;

public class Main_Eventos extends AppCompatActivity {

    private TextView mJsontxt;
    private APIService mAPIService;
    private Button crearEvento;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__eventos);
        this.mJsontxt = findViewById(R.id.jsonText);
        this.mAPIService = ApiUtils.getAPIApiService();
       //Obtenemos los eventos en el servidor y los imprimimos es pantalla
        getEvents();
        this.crearEvento = findViewById(R.id.btn_createEvent);
        //Este boton nos envia a crear un evento
        crearEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent evento = new Intent(getApplicationContext(),Create_Events.class);
                startActivity(evento);
            }
        });
    }
    //Obtenemos el token para poder obtener la lista de eventos
    MainActivity token = new MainActivity();
    String i = token.sendToken();

    private void getEvents(){
        mAPIService.getEvents(i).enqueue(new Callback<ArrayList<Events>>() {
            @Override
            public void onResponse(Call<ArrayList<Events>> call, Response<ArrayList<Events>> response) {
                if(!response.isSuccessful()){
                    mJsontxt.setText("Codigo: " + response.toString());
                    return;
                }
                //recorremos una lista con un foreach para asi poder imprimir en un textField
                ArrayList<Events> eventosList = response.body();
                for (Events eventos:eventosList) {
                    String content = "";
                    content+="id"+eventos.getId()+"\n";
                    content+="title"+eventos.getTitle()+"\n";
                    content+="description"+eventos.getDescription()+"\n";
                    content+="date"+eventos.getDate()+"\n";
                    content+="image"+eventos.getImage()+"\n";
                    content+="attendances"+eventos.getAttendances()+"\n";
                    content+="willYouAttend"+eventos.getWillYouAttend()+"\n";
                    content+="location"+eventos.getLocation()+"\n\n";
                    mJsontxt.setText(content);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Events>> call, Throwable t) {
                mJsontxt.setText(t.getMessage());
            }
        });
    }

}