package com.example.agendapersonalbeta.Controlador.Evento;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.agendapersonalbeta.Modelo.Evento.ModeloEvento;
import com.example.agendapersonalbeta.R;

public class EventoMostrar extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EventoAdaptador eventoAdaptador;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evento_mostrar);

        recyclerView = findViewById(R.id.reciclerViewEvento);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //INSTACIA DE NUESTRO MODELO PARA QUE UTILICE EL METODO MOSTRAR EVENTO
        ModeloEvento modeloEvento = new ModeloEvento(getApplicationContext());

        eventoAdaptador = new EventoAdaptador(modeloEvento.mostrarEventos());
        recyclerView.setAdapter(eventoAdaptador);

    }
}