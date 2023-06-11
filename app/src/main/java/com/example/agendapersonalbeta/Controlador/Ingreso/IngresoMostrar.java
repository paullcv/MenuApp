package com.example.agendapersonalbeta.Controlador.Ingreso;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.agendapersonalbeta.Modelo.Categoria.ClaseCategoria;
import com.example.agendapersonalbeta.Modelo.Categoria.ModeloCategoria;
import com.example.agendapersonalbeta.Modelo.Ingreso.ModeloIngreso;
import com.example.agendapersonalbeta.R;

import java.util.List;

public class IngresoMostrar extends AppCompatActivity {

    private RecyclerView recyclerView;
    private IngresoAdaptador ingresoAdaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingreso_mostrar);

        recyclerView = findViewById(R.id.reciclerViewIngreso);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //INSTACIA DE NUESTRO MODELO PARA QUE UTILICE EL METODO MOSTRAR INGRESO
        ModeloIngreso modeloIngreso = new ModeloIngreso(getApplicationContext());

        ModeloCategoria modeloCategoria = new ModeloCategoria(getApplicationContext());
        List<ClaseCategoria> listaCategorias = modeloCategoria.mostrarCategorias();

        ingresoAdaptador = new IngresoAdaptador(modeloIngreso.mostrarIngresos(), listaCategorias);
        recyclerView.setAdapter(ingresoAdaptador);

    }
}