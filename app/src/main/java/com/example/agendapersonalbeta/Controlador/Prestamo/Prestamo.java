package com.example.agendapersonalbeta.Controlador.Prestamo;

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

import com.example.agendapersonalbeta.Modelo.Prestamo.ClasePrestamo;
import com.example.agendapersonalbeta.Modelo.Categoria.ClaseCategoria;
import com.example.agendapersonalbeta.Modelo.Categoria.ModeloCategoria;
import com.example.agendapersonalbeta.Modelo.Contacto.ClaseContacto;
import com.example.agendapersonalbeta.Modelo.Contacto.ModeloContacto;
import com.example.agendapersonalbeta.Modelo.Prestamo.ModeloPrestamo;
import com.example.agendapersonalbeta.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Prestamo extends AppCompatActivity {

    EditText etIdPrestamo;
    EditText etMontoPrestamo;
    EditText etDPFechaPrestamo;
    EditText etEtDPFechaVencimientoPrestamo;

    Spinner spinnerContacto;
    Spinner spinnerCategoria;


    Button btnAgregarPrestamo;
    Button btnBuscarPrestamo;
    Button btnEliminarPrestamo;
    Button btnEditarPrestamo;
    Button btnMostrarPrestamos;
    Button btnlimpiarPrestamo;
    Button btnDPFechaPrestamo;
    Button btnDPFechaVencimientoPrestamo;

    List<ClaseContacto> listaContactos;
    List<ClaseCategoria> listaCategorias;


    private int anio, mes, dia;
    String fechaPrestamoSeleccionada;
    String fechaVencimientoPrestamoSeleccionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prestamo);

        etIdPrestamo = findViewById(R.id.editTextIdPrestamo);
        etMontoPrestamo = findViewById(R.id.editTextMontoPrestamo);
        etDPFechaPrestamo = findViewById(R.id.editTextDPFechaPrestamo);
        etEtDPFechaVencimientoPrestamo = findViewById(R.id.editTextDPFechaPrestamoVencimiento);

        btnDPFechaPrestamo = findViewById(R.id.btnDPFechaPrestamo);
        btnDPFechaVencimientoPrestamo = findViewById(R.id.btnDPFechaPrestamoVencimiento);

        btnAgregarPrestamo = findViewById(R.id.btnAddPrestamo);
        btnBuscarPrestamo = findViewById(R.id.btnBuscarPrestamo);
        btnEditarPrestamo = findViewById(R.id.btnEditPrestamo);
        btnEliminarPrestamo = findViewById(R.id.btnDeletePrestamo);
        btnMostrarPrestamos = findViewById(R.id.btnShowPrestamo);
        btnlimpiarPrestamo = findViewById(R.id.btnSLimpiarPrestamo);

        spinnerContacto = findViewById(R.id.spinnerContactosPrestamo);
        spinnerCategoria = findViewById(R.id.spinnerCategoriasPrestamo);




        // CREAR INSTANCIA DEL MODELO
        ModeloPrestamo modeloPrestamo = new ModeloPrestamo(Prestamo.this);
        ModeloContacto modeloContacto = new ModeloContacto(Prestamo.this);
        ModeloCategoria modeloCategoria = new ModeloCategoria(Prestamo.this);

        //BOTON FECHA PRESTAMO CREACION
        btnDPFechaPrestamo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar calendar = Calendar.getInstance();
                dia = calendar.get(Calendar.DAY_OF_MONTH);
                mes = calendar.get(Calendar.MONTH);
                anio = calendar.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(Prestamo.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // Muestra la fecha seleccionada en un TextView o realiza cualquier otra operación necesaria
                        fechaPrestamoSeleccionada = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        etDPFechaPrestamo.setText(fechaPrestamoSeleccionada);
                    }
                }, anio, mes, dia);
                datePickerDialog.show();
            }
        });
        //fin del boton de FECHA

        //BOTON FECHA PRESTAMO VENCIMIENTO
        btnDPFechaVencimientoPrestamo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar calendar = Calendar.getInstance();
                dia = calendar.get(Calendar.DAY_OF_MONTH);
                mes = calendar.get(Calendar.MONTH);
                anio = calendar.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(Prestamo.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // Muestra la fecha seleccionada en un TextView o realiza cualquier otra operación necesaria
                        fechaVencimientoPrestamoSeleccionada = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        etEtDPFechaVencimientoPrestamo.setText(fechaVencimientoPrestamoSeleccionada);
                    }
                }, anio, mes, dia);
                datePickerDialog.show();
            }
        });
        //fin del boton de FECHA


        // OBTENER LISTA DE CONTACTOS
        listaContactos = modeloContacto.mostrarContactos();
        List<String> nombreContactos = new ArrayList<>();
        for (ClaseContacto contacto : listaContactos) {
            nombreContactos.add(contacto.getNombre());
        }
        ArrayAdapter<String> adapterContacto = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, nombreContactos);
        spinnerContacto.setAdapter(adapterContacto);

        // OBTENER LISTA DE CATEGORIAS
        listaCategorias = modeloCategoria.mostrarCategorias();
        List<String> titulosCategorias = new ArrayList<>();
        for (ClaseCategoria categoria : listaCategorias) {
            titulosCategorias.add(categoria.getTitulo());
        }
        ArrayAdapter<String> adapterCategoria = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, titulosCategorias);
        spinnerCategoria.setAdapter(adapterCategoria);




        // GENERAR EVENTO DE AGREGAR PRESTAMO
        btnAgregarPrestamo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClaseContacto contactoSeleccionado = listaContactos.get(spinnerContacto.getSelectedItemPosition());
                ClaseCategoria categoriaSeleccionada = listaCategorias.get(spinnerCategoria.getSelectedItemPosition());
                modeloPrestamo.agregarPrestamo(
                        Integer.valueOf(etIdPrestamo.getText().toString()),
                        Double.valueOf(etMontoPrestamo.getText().toString()),
                        etDPFechaPrestamo.getText().toString(),
                        etEtDPFechaVencimientoPrestamo.getText().toString(),
                        contactoSeleccionado.getId(),
                        categoriaSeleccionada.getId()
                );
                limpiar();
                Toast.makeText(getApplicationContext(), "EL PRESTAMO SE AGREGO CORRECTAMENTE", Toast.LENGTH_SHORT).show();
            }
        });

        // Evento de mostrar prestamos

        btnMostrarPrestamos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mostrarEventos = new Intent(getApplicationContext(), PrestamoMostrar.class);
                startActivity(mostrarEventos);
            }
        });

        // GENERAR EVENTO DE BUSCAR PRESTAMO
        btnBuscarPrestamo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClasePrestamo prestamo = new ClasePrestamo();
                int id = Integer.valueOf(etIdPrestamo.getText().toString());
                modeloPrestamo.buscarPrestamo(prestamo, id);
                etMontoPrestamo.setText(String.valueOf(prestamo.getMonto()));
                etDPFechaPrestamo.setText(prestamo.getFechaPrestamo());
                etEtDPFechaVencimientoPrestamo.setText(prestamo.getFechaVencimientoPrestamo());

                // Seleccionar el contacto correspondiente en el Spinner
                int posicionContacto = obtenerPosicionContacto(prestamo.getContacto_id());
                spinnerContacto.setSelection(posicionContacto);

                // Seleccionar la categoría correspondiente en el Spinner
                int posicionCategoria = obtenerPosicionCategoria(prestamo.getCategoria_id());
                spinnerCategoria.setSelection(posicionCategoria);
            }
        });

        // GENERAR EVENTO DE EDITAR PRESTAMO
        btnEditarPrestamo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClaseContacto contactoSeleccionado = listaContactos.get(spinnerContacto.getSelectedItemPosition());
                ClaseCategoria categoriaSeleccionada = listaCategorias.get(spinnerCategoria.getSelectedItemPosition());
                modeloPrestamo.editarPrestamo(
                        Integer.valueOf(etIdPrestamo.getText().toString()),
                        Double.valueOf(etMontoPrestamo.getText().toString()),
                        etDPFechaPrestamo.getText().toString(),
                        etEtDPFechaVencimientoPrestamo.getText().toString(),
                        contactoSeleccionado.getId(),
                        categoriaSeleccionada.getId()
                );
                limpiar();
                Toast.makeText(getApplicationContext(), "LOS DATOS SE ACTUALIZARON CORRECTAMENTE", Toast.LENGTH_SHORT).show();
            }
        });

        // GENERAR EVENTO DE ELIMINAR PRESTAMO
        btnEliminarPrestamo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modeloPrestamo.eliminarPrestamo(Integer.valueOf(etIdPrestamo.getText().toString()));
                limpiar();
                Toast.makeText(getApplicationContext(), "LOS DATOS SE ELIMINARON CORRECTAMENTE", Toast.LENGTH_SHORT).show();
            }
        });

        // GENERAR EVENTO DE LIMPIAR LOS CAMPOS
        btnlimpiarPrestamo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                limpiar();
            }
        });
    }


    private void limpiar() {
        etIdPrestamo.setText("");
        etMontoPrestamo.setText("");
        etDPFechaPrestamo.setText("");
        etEtDPFechaVencimientoPrestamo.setText("");
    }
    private int obtenerPosicionContacto(int contactoId) {
        for (int i = 0; i < listaContactos.size(); i++) {
            if (listaContactos.get(i).getId() == contactoId) {
                return i;
            }
        }
        return 0; // Si no se encuentra, se selecciona la posición 0 por defecto
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