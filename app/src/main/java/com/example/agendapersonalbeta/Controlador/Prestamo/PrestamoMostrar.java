package com.example.agendapersonalbeta.Controlador.Prestamo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.agendapersonalbeta.Modelo.Categoria.ClaseCategoria;
import com.example.agendapersonalbeta.Modelo.Categoria.ModeloCategoria;
import com.example.agendapersonalbeta.Modelo.Contacto.ClaseContacto;
import com.example.agendapersonalbeta.Modelo.Contacto.ModeloContacto;
import com.example.agendapersonalbeta.Modelo.Prestamo.ModeloPrestamo;
import com.example.agendapersonalbeta.R;

import java.util.List;


public class PrestamoMostrar extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PrestamoAdaptador prestamoAdaptador;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prestamo_mostrar);

        recyclerView = findViewById(R.id.reciclerViewPrestamo);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //INSTACIA DE NUESTRO MODELO PARA QUE UTILICE EL METODO MOSTRAR PRESTAMO
        ModeloPrestamo modeloPrestamo = new ModeloPrestamo(getApplicationContext());

        // USO DEL MODELO PAR MOSTRAR EL NOMBRE APARTIR DEL ID
        ModeloContacto modeloContacto = new ModeloContacto(getApplicationContext());
        List<ClaseContacto> listaContactos = modeloContacto.mostrarContactos();

        // USO DEL MODELO PAR MOSTRAR EL TITULO CATEGORIA APARTIR DEL ID
        ModeloCategoria modeloCategoria = new ModeloCategoria(getApplicationContext());
        List<ClaseCategoria> listaCategorias = modeloCategoria.mostrarCategorias();

        prestamoAdaptador = new PrestamoAdaptador(modeloPrestamo.mostrarPrestamos(),listaContactos, listaCategorias);
        recyclerView.setAdapter(prestamoAdaptador);

    }
}