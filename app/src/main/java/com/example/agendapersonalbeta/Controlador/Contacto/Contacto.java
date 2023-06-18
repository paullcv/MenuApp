package com.example.agendapersonalbeta.Controlador.Contacto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.agendapersonalbeta.Modelo.Contacto.ClaseContacto;
import com.example.agendapersonalbeta.Modelo.Contacto.ModeloContacto;
import com.example.agendapersonalbeta.Modelo.Contacto.ModeloContactoProxy;
import com.example.agendapersonalbeta.R;

public class Contacto extends AppCompatActivity {

    EditText etIdContacto;
    EditText etNombreContacto;
    EditText etCorreoContacto;
    EditText etTelefonoContacto;
    EditText etDireccionContacto;

    Button btnAgregarContacto, btnMostrarContacto, btnBuscarContacto, btnEditarContacto, btnEliminarContacto, btnLimpiar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacto);


        //Asociamos nuestros elementos con los del formulario (vista)

        etIdContacto = findViewById(R.id.editTextId);
        etNombreContacto = findViewById(R.id.editTextNombreContacto);
        etCorreoContacto = findViewById(R.id.editTextCorreoContacto);
        etTelefonoContacto = findViewById(R.id.editTextTelefonoContacto);
        etDireccionContacto = findViewById(R.id.editTextDireccionContacto);

        btnAgregarContacto = findViewById(R.id.btnAddContacto);
        btnMostrarContacto = findViewById(R.id.btnShowContacto);
        btnBuscarContacto = findViewById(R.id.btnBuscarContacto);
        btnEditarContacto = findViewById(R.id.btnEditContacto);
        btnEliminarContacto = findViewById(R.id.btnDeleteContacto);
        btnLimpiar = findViewById(R.id.btnLimpiar);


        //CREAMOS LA INSTACIA DEL MODELO
        final ModeloContactoProxy modeloContactoProxy = new ModeloContactoProxy(Contacto.this);

        //GENERAR EL EVENTO DE AGREGAR CONTACTO
        btnAgregarContacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modeloContactoProxy.agregarContacto(Integer.valueOf(etIdContacto.getText().toString()),etNombreContacto.getText().toString(), etCorreoContacto.getText().toString(), Integer.valueOf(etTelefonoContacto.getText().toString()), etDireccionContacto.getText().toString());
                if (modeloContactoProxy.isAgregadoCorrectamente()) {
                    Toast.makeText(getApplicationContext(), "SE AGREGÓ CORRECTAMENTE", Toast.LENGTH_SHORT).show();
                    limpiar();
                } else {
                    Toast.makeText(getApplicationContext(), "ERROR AL AGREGAR EL CONTACTO", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //GENERAR EL EVENTO DE MOSTRAR CONTACTO
        btnMostrarContacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mostrarContactos = new Intent(getApplicationContext(), ContactoMostar.class);
                startActivity(mostrarContactos);
            }
        });

        //GENERAR EVENTO DE BUSCAR CONTACTO
        btnBuscarContacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClaseContacto contacto = new ClaseContacto();
                String textid = etIdContacto.getText().toString();
                int id = Integer.valueOf(textid);
                modeloContactoProxy.buscarContacto(contacto, id);
                etNombreContacto.setText(contacto.getNombre());
                etCorreoContacto.setText(contacto.getCorreo());
                etTelefonoContacto.setText(String.valueOf(contacto.getTelefono()));
                etDireccionContacto.setText(contacto.getDireccion());

            }
        });

        //GENERAR EVENTO DE EDITAR CONTACTO
        btnEditarContacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modeloContactoProxy.editarContacto(Integer.valueOf(etIdContacto.getText().toString()), etNombreContacto.getText().toString(), etCorreoContacto.getText().toString(), Integer.valueOf(etTelefonoContacto.getText().toString()),etDireccionContacto.getText().toString());
                if (modeloContactoProxy.isAgregadoCorrectamente()) {
                    Toast.makeText(getApplicationContext(), "LOS DATOS SE ACTUALIZARON CORRECTAMENTE", Toast.LENGTH_SHORT).show();
                    limpiar();
                } else {
                    Toast.makeText(getApplicationContext(), "ERROR AL ACTUALIZAR EL CONTACTO", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //GENERAR EVENTO DE ELIMINAR CONTACTO
        btnEliminarContacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modeloContactoProxy.eliminarContacto(Integer.valueOf(etIdContacto.getText().toString()));
                limpiar();
                Toast.makeText(getApplicationContext(), "Los Datos se Eliminaron Correctamente", Toast.LENGTH_SHORT).show();
            }
        });

        //GENERAR EVENTO DE LIMPIAR LOS CAMPOS
        btnLimpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                limpiar();
            }
        });
    }

    private  void limpiar(){
        etIdContacto.setText("");
        etNombreContacto.setText("");
        etCorreoContacto.setText("");
        etTelefonoContacto.setText("");
        etDireccionContacto.setText("");
    }
}