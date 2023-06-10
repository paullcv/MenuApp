package com.example.agendapersonalbeta.Controlador.Prestamo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.agendapersonalbeta.Modelo.Prestamo.ModeloPrestamo;
import com.example.agendapersonalbeta.R;


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

        prestamoAdaptador = new PrestamoAdaptador(modeloPrestamo.mostrarPrestamos());
        recyclerView.setAdapter(prestamoAdaptador);

    }
}