package com.example.agendapersonalbeta.Controlador.Egreso;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.agendapersonalbeta.Modelo.Categoria.ClaseCategoria;
import com.example.agendapersonalbeta.Modelo.Categoria.ModeloCategoria;
import com.example.agendapersonalbeta.Modelo.Egreso.ModeloEgreso;
import com.example.agendapersonalbeta.R;

import java.util.List;

public class EgresoMostrar extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EgresoAdaptador egresoAdaptador;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_egreso_mostrar);

        recyclerView = findViewById(R.id.reciclerViewEgreso);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //INSTACIA DE NUESTRO MODELO PARA QUE UTILICE EL METODO MOSTRAR EGRESO
        ModeloEgreso modeloEgreso = new ModeloEgreso(getApplicationContext());

        ModeloCategoria modeloCategoria = new ModeloCategoria(getApplicationContext());
        List<ClaseCategoria> listaCategorias = modeloCategoria.mostrarCategorias();

        egresoAdaptador = new EgresoAdaptador(modeloEgreso.mostrarEgresos(), listaCategorias);
        recyclerView.setAdapter(egresoAdaptador);
    }
}