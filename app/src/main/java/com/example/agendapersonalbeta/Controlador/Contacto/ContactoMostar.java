package com.example.agendapersonalbeta.Controlador.Contacto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.agendapersonalbeta.Controlador.Contacto.ContactoAdaptador;
import com.example.agendapersonalbeta.Modelo.Contacto.ModeloContacto;
import com.example.agendapersonalbeta.R;

public class ContactoMostar extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ContactoAdaptador contactoAdaptador;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacto_mostar);


        recyclerView = (RecyclerView) findViewById(R.id.reciclerViewContacto);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        //INSTACIA DE NUESTRO MODELO PARA QUE UTILICE EL METODO MOSTRAR CONTACTO
        ModeloContacto modeloContacto = new ModeloContacto(getApplicationContext());


        contactoAdaptador = new ContactoAdaptador(modeloContacto.mostrarContactos());
        recyclerView.setAdapter(contactoAdaptador);
    }


}