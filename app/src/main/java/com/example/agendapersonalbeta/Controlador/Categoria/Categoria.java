package com.example.agendapersonalbeta.Controlador.Categoria;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.agendapersonalbeta.Modelo.Categoria.ClaseCategoria;
import com.example.agendapersonalbeta.Modelo.Categoria.ModeloCategoria;
import com.example.agendapersonalbeta.R;

public class Categoria extends AppCompatActivity {

    //CONEXION VARIABLES DE LA VISTA
    EditText etIdCategoria;
    EditText etTituloCategoria;
    EditText etDescripcionCategoria;

    Button btnAgregarCategoria;
    Button btnEditarCategoria;
    Button btnEliminarCategoria;
    Button btnMostarCategoria;
    Button btnBuscarCategoria;
    Button btnLimpiarCategoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria);

        etIdCategoria = findViewById(R.id.editTextIdCategoria);
        etTituloCategoria = findViewById(R.id.editTextTituloCategoria);
        etDescripcionCategoria = findViewById(R.id.editTextDescripcionCategoria);

        btnAgregarCategoria = findViewById(R.id.btnAddCategoria);
        btnMostarCategoria = findViewById(R.id.btnShowCategoria);
        btnBuscarCategoria = findViewById(R.id.btnBuscarCategoria);
        btnEditarCategoria = findViewById(R.id.btnEditCategoria);
        btnEliminarCategoria = findViewById(R.id.btnDeleteCategoria);
        btnLimpiarCategoria = findViewById(R.id.btnLimpiarCategoria);

        //CREAMOS LA INSTANCIA CON EL MODELO
        ModeloCategoria modeloCategoria = new ModeloCategoria(Categoria.this);

        //GENERAR EL EVENTO DE AGREGAR CATEGORIA
        btnAgregarCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modeloCategoria.agregarCategoria(Integer.valueOf(etIdCategoria.getText().toString()), etTituloCategoria.getText().toString(), etDescripcionCategoria.getText().toString());
                limpiar();
                Toast.makeText(getApplicationContext(), "LA CATEGORIA SE AGREGO CORRECTAMENTE", Toast.LENGTH_SHORT).show();
            }
        });

        //GENERAR EL EVENTO DE MOSTRAR CATEGORIAS
        btnMostarCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mostrarCategorias = new Intent(getApplicationContext(), CategoriaMostrar.class);
                startActivity(mostrarCategorias);
            }
        });

        //GENERAR EVENTO DE BUSCAR CATEGORIA
        btnBuscarCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClaseCategoria categoria = new ClaseCategoria();
                String textid = etIdCategoria.getText().toString();
                int id = Integer.valueOf(textid);
                modeloCategoria.buscarCategoria(categoria, id);
                etTituloCategoria.setText(categoria.getTitulo());
                etDescripcionCategoria.setText(categoria.getDescripcion());

            }
        });

        //GENERAR EVENTO DE EDITAR CATEGORIA
        btnEditarCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modeloCategoria.editarCategoria(Integer.valueOf(etIdCategoria.getText().toString()), etTituloCategoria.getText().toString(), etDescripcionCategoria.getText().toString());
                limpiar();
                Toast.makeText(getApplicationContext(), "Los Datos se Actualizaron Correctamente", Toast.LENGTH_SHORT).show();
            }
        });

        //GENERAR EVENTO DE ELIMINAR CATEGORIA
        btnEliminarCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modeloCategoria.eliminarCategoria(Integer.valueOf(etIdCategoria.getText().toString()));
                limpiar();
                Toast.makeText(getApplicationContext(), "Los Datos se Eliminaron Correctamente", Toast.LENGTH_SHORT).show();
            }
        });

        //GENERAR EVENTO DE LIMPIAR LOS CAMPOS
        btnLimpiarCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                limpiar();
            }
        });

    }

    private void limpiar(){
        etIdCategoria.setText("");
        etTituloCategoria.setText("");
        etDescripcionCategoria.setText("");
    }

}