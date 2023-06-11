package com.example.agendapersonalbeta.Controlador.Ingreso;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.agendapersonalbeta.Modelo.Ingreso.ClaseIngreso;
import com.example.agendapersonalbeta.Modelo.Categoria.ClaseCategoria;
import com.example.agendapersonalbeta.Modelo.Categoria.ModeloCategoria;
import com.example.agendapersonalbeta.Modelo.Ingreso.ModeloIngreso;
import com.example.agendapersonalbeta.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Ingreso extends AppCompatActivity {

    EditText etIdIngreso;
    EditText etMontoIngreso;
    EditText etNombreIngreso;
    EditText etFechaIngreso;
    Button btnDPFechaIngreso;

    Button btnAgregarIngreso;
    Button btnBuscarIngreso;
    Button btnEliminarIngreso;
    Button btnEditarIngreso;
    Button btnMostrarIngresos;
    Button btnLimpiarIngreso;

    private int anio, mes , dia;
    String fechaIngresoSeleccionada;
    List<ClaseCategoria> listaCategorias;
    Spinner spinnerCategoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingreso);

        etIdIngreso = findViewById(R.id.editTextIdIngreso);
        etMontoIngreso = findViewById(R.id.editTextMontoIngreso);
        etNombreIngreso = findViewById(R.id.editTextNombreIngreso);
        etFechaIngreso = findViewById(R.id.editTextFechaIngreso);

        btnAgregarIngreso = findViewById(R.id.btnAddIngreso);
        btnBuscarIngreso = findViewById(R.id.btnBuscarIngreso);
        btnMostrarIngresos = findViewById(R.id.btnShowIngreso);
        btnEliminarIngreso = findViewById(R.id.btnDeleteIngreso);
        btnEditarIngreso = findViewById(R.id.btnEditIngreso);
        btnLimpiarIngreso = findViewById(R.id.btnLimpiarIngreso);
        btnDPFechaIngreso = findViewById(R.id.btnDPFechaIngreso);

        spinnerCategoria = findViewById(R.id.spinnerIngresoCategorias);

        // CREAR INSTANCIA DEL MODELO
        ModeloIngreso modeloIngreso = new ModeloIngreso(Ingreso.this);
        ModeloCategoria modeloCategoria = new ModeloCategoria(Ingreso.this);

        //BOTON PARA TRABAJAR CON EL DATAPICKER FECHA INGRESO
        btnDPFechaIngreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                dia = calendar.get(Calendar.DAY_OF_WEEK);
                mes = calendar.get(Calendar.MONTH);
                anio = calendar.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(Ingreso.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // Muestra la fecha seleccionada en un TextView o realiza cualquier otra operación necesaria
                        fechaIngresoSeleccionada = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        etFechaIngreso.setText(fechaIngresoSeleccionada);
                    }
                }, anio, mes, dia);
                datePickerDialog.show();
            }
        });
        //fin del boton de FECHA

        //OBTENER LISTA DE CATEGORIAS
        listaCategorias = modeloCategoria.mostrarCategorias();
        List<String> tituloCategorias = new ArrayList<>();
        for (ClaseCategoria categoria : listaCategorias){
            tituloCategorias.add(categoria.getTitulo());
        }

        ArrayAdapter<String> adapterCategoria = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, tituloCategorias);
        spinnerCategoria.setAdapter(adapterCategoria);


        //GENERAR EVENTO DE AGREGAR INGRESO
        btnAgregarIngreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClaseCategoria categoriaSeleccionada = listaCategorias.get(spinnerCategoria.getSelectedItemPosition());

                modeloIngreso.AgregarIngreso(
                        Integer.valueOf(etIdIngreso.getText().toString()),
                        Double.valueOf(etMontoIngreso.getText().toString()),
                        etNombreIngreso.getText().toString(),
                        etFechaIngreso.getText().toString(),
                        Integer.valueOf(categoriaSeleccionada.getId())
                );
                limpiar();
                Toast.makeText(getApplicationContext(), "EL INGRESO SE AGREGO CORRECTAMENTE", Toast.LENGTH_SHORT).show();
            }
        });

        //GENERAR EVENTO DE MOSTRAR INGRESOS
        btnMostrarIngresos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mostrarIngresos = new Intent(getApplicationContext(), IngresoMostrar.class);
                startActivity(mostrarIngresos);
            }
        });

        // GENERAR EVENTO DE BUSCAR INGRESO
        btnBuscarIngreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClaseIngreso ingreso = new ClaseIngreso();
                int id = Integer.valueOf(etIdIngreso.getText().toString());
                modeloIngreso.buscarIngreso(ingreso, id);
                etMontoIngreso.setText(String.valueOf(ingreso.getMonto()));
                etNombreIngreso.setText(ingreso.getNombre());
                etFechaIngreso.setText(ingreso.getFecha());


                // Seleccionar la categoría correspondiente en el Spinner
                int posicionCategoria = obtenerPosicionCategoria(ingreso.getCategoria_id());
                spinnerCategoria.setSelection(posicionCategoria);
            }
        });

        // GENERAR EVENTO DE EDITAR INGRESO
        btnEditarIngreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClaseCategoria categoriaSeleccionada = listaCategorias.get(spinnerCategoria.getSelectedItemPosition());
                modeloIngreso.editarIngreso(
                        Integer.valueOf(etIdIngreso.getText().toString()),
                        Double.valueOf(etMontoIngreso.getText().toString()),
                        etNombreIngreso.getText().toString(),
                        etFechaIngreso.getText().toString(),
                        categoriaSeleccionada.getId()
                );
                limpiar();
                Toast.makeText(getApplicationContext(), "LOS DATOS SE ACTUALIZARON CORRECTAMENTE", Toast.LENGTH_SHORT).show();
            }
        });

        // GENERAR EVENTO DE ELIMINAR INGRESO
        btnEliminarIngreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modeloIngreso.eliminarIngreso(Integer.valueOf(etIdIngreso.getText().toString()));
                limpiar();
                Toast.makeText(getApplicationContext(), "LOS DATOS SE ELIMINARON CORRECTAMENTE", Toast.LENGTH_SHORT).show();
            }
        });

        // GENERAR EVENTO DE LIMPIAR LOS CAMPOS
        btnLimpiarIngreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                limpiar();
            }
        });


    }

    private void limpiar() {
        etIdIngreso.setText("");
        etMontoIngreso.setText("");
        etNombreIngreso.setText("");
        etFechaIngreso.setText("");
    }

    private int obtenerPosicionCategoria(int categoriaId) {
        for (int i = 0; i < listaCategorias.size(); i++) {
            if (listaCategorias.get(i).getId() == categoriaId) {
                return i;
            }
        }
        return 0; // Si no se encuentra, se selecciona la posición 0 por defecto
    }
}