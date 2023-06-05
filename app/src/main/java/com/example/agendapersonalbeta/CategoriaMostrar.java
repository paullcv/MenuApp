package com.example.agendapersonalbeta;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.agendapersonalbeta.Controlador.Contacto.ContactoAdaptador;
import com.example.agendapersonalbeta.Modelo.Contacto.ModeloContacto;

public class CategoriaMostrar extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CategoriaAdaptador categoriaAdaptador;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria_mostrar);

        recyclerView = findViewById(R.id.reciclerViewCategoria);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //INSTACIA DE NUESTRO MODELO PARA QUE UTILICE EL METODO MOSTRAR CATEGORIA
        ModeloCategoria modeloCategoria = new ModeloCategoria(getApplicationContext());

        categoriaAdaptador = new CategoriaAdaptador(modeloCategoria.mostrarCategorias());
        recyclerView.setAdapter(categoriaAdaptador);

    }
}