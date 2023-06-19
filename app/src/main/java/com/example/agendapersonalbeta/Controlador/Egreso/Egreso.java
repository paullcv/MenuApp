package com.example.agendapersonalbeta.Controlador.Egreso;

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

import com.example.agendapersonalbeta.Modelo.Categoria.ClaseCategoria;
import com.example.agendapersonalbeta.Modelo.Categoria.ModeloCategoria;
import com.example.agendapersonalbeta.Modelo.Egreso.ClaseEgreso;
import com.example.agendapersonalbeta.Modelo.Egreso.ModeloEgreso;
import com.example.agendapersonalbeta.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Egreso extends AppCompatActivity {

    EditText etIdEgreso;
    EditText etMontoEgreso;
    EditText etNombreEgreso;
    EditText etFechaEgreso;
    Button btnDPFechaEgreso;

    Button btnAgregarEgreso;
    Button btnBuscarEgreso;
    Button btnEliminarEgreso;
    Button btnEditarEgreso;
    Button btnMostrarEgresos;
    Button btnLimpiarEgreso;

    private int anio, mes , dia;
    String fechaEgresoSeleccionada;
    List<ClaseCategoria> listaCategorias;
    Spinner spinnerCategoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_egreso);

        etIdEgreso = findViewById(R.id.editTextIdEgreso);
        etMontoEgreso = findViewById(R.id.editTextMontoEgreso);
        etNombreEgreso = findViewById(R.id.editTextNombreEgreso);
        etFechaEgreso = findViewById(R.id.editTextFechaEgreso);

        btnAgregarEgreso = findViewById(R.id.btnAddEgreso);
        btnBuscarEgreso = findViewById(R.id.btnBuscarEgreso);
        btnMostrarEgresos = findViewById(R.id.btnShowEgreso);
        btnEliminarEgreso = findViewById(R.id.btnDeleteEgreso);
        btnEditarEgreso = findViewById(R.id.btnEditEgreso);
        btnLimpiarEgreso = findViewById(R.id.btnLimpiarEgreso);
        btnDPFechaEgreso = findViewById(R.id.btnDPFechaEgreso);

        spinnerCategoria = findViewById(R.id.spinnerEgresoCategorias);

        // CREAR INSTANCIA DEL MODELO
        ModeloEgreso modeloEgreso = new ModeloEgreso(Egreso.this);
        ModeloCategoria modeloCategoria = new ModeloCategoria(Egreso.this);

        //BOTON PARA TRABAJAR CON EL DATAPICKER FECHA EGRESO
        btnDPFechaEgreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                dia = calendar.get(Calendar.DAY_OF_WEEK);
                mes = calendar.get(Calendar.MONTH);
                anio = calendar.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(Egreso.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // Muestra la fecha seleccionada en un TextView o realiza cualquier otra operación necesaria
                        fechaEgresoSeleccionada = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        etFechaEgreso.setText(fechaEgresoSeleccionada);
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
        btnAgregarEgreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClaseCategoria categoriaSeleccionada = listaCategorias.get(spinnerCategoria.getSelectedItemPosition());

                modeloEgreso.realizarTransaccion(
                        Integer.valueOf(etIdEgreso.getText().toString()),
                        Double.valueOf(etMontoEgreso.getText().toString()),
                        etNombreEgreso.getText().toString(),
                        etFechaEgreso.getText().toString(),
                        Integer.valueOf(categoriaSeleccionada.getId())
                );
                limpiar();
                Toast.makeText(getApplicationContext(), "EL EGRESO SE AGREGO CORRECTAMENTE", Toast.LENGTH_SHORT).show();
            }
        });

        // GENERAR EVENTO DE BUSCAR EGRESO
        btnBuscarEgreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClaseEgreso egreso = new ClaseEgreso();
                int id = Integer.valueOf(etIdEgreso.getText().toString());
                modeloEgreso.buscarEgreso(egreso, id);
                etMontoEgreso.setText(String.valueOf(egreso.getMonto()));
                etNombreEgreso.setText(egreso.getNombre());
                etFechaEgreso.setText(egreso.getFecha());

                // Seleccionar la categoría correspondiente en el Spinner
                int posicionCategoria = obtenerPosicionCategoria(egreso.getCategoria_id());
                spinnerCategoria.setSelection(posicionCategoria);
            }
        });

        //GENERAR EVENTO DE MOSTRAR EGRESOS
        btnMostrarEgresos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mostrarEgresos = new Intent(getApplicationContext(), EgresoMostrar.class);
                startActivity(mostrarEgresos);
            }
        });

        // GENERAR EVENTO DE EDITAR EGRESO
        btnEditarEgreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClaseCategoria categoriaSeleccionada = listaCategorias.get(spinnerCategoria.getSelectedItemPosition());
                modeloEgreso.editarTransaccion(
                        Integer.valueOf(etIdEgreso.getText().toString()),
                        Double.valueOf(etMontoEgreso.getText().toString()),
                        etNombreEgreso.getText().toString(),
                        etFechaEgreso.getText().toString(),
                        categoriaSeleccionada.getId()
                );
                limpiar();
                Toast.makeText(getApplicationContext(), "LOS DATOS SE ACTUALIZARON CORRECTAMENTE", Toast.LENGTH_SHORT).show();
            }
        });

        // GENERAR EVENTO DE ELIMINAR EGRESO
        btnEliminarEgreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modeloEgreso.eliminarTransaccion(Integer.valueOf(etIdEgreso.getText().toString()));
                limpiar();
                Toast.makeText(getApplicationContext(), "LOS DATOS SE ELIMINARON CORRECTAMENTE", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void limpiar() {
        etIdEgreso.setText("");
        etMontoEgreso.setText("");
        etNombreEgreso.setText("");
        etFechaEgreso.setText("");
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