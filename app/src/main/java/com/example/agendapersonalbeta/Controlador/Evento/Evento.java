package com.example.agendapersonalbeta.Controlador.Evento;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.agendapersonalbeta.Modelo.Evento.ClaseEvento;
import com.example.agendapersonalbeta.Modelo.Evento.ModeloEvento;
import com.example.agendapersonalbeta.R;

import java.util.Calendar;
import java.util.Locale;

public class Evento extends AppCompatActivity {

    EditText etIdEvento;
    EditText etNombreEvento;

    EditText etDPFechaEvento;

    EditText etTPHoraEvento;

    Button btnDPFecha;
    Button btnTPHora;

    Button btnAgregarEvento;
    Button btnEditarEvento;
    Button btnBuscarEvento;
    Button btnMostrarEvento;
    Button btnEliminarrEvento;
    Button btnLimpiarEvento;


    private int anio, mes, dia;
    private int hora, minuto;
    String fechaSeleccionada;
    String horaSeleccionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evento);

        etIdEvento = findViewById(R.id.editTextIdEvento);
        etNombreEvento = findViewById(R.id.editTextNombreEvento);
        etDPFechaEvento = findViewById(R.id.editTextDPFechaEvento);
        etTPHoraEvento = findViewById(R.id.editTextTPHoraEvento);


        btnDPFecha = findViewById(R.id.btnDPFechaEvento);
        btnTPHora = findViewById(R.id.btnTPHoraEvento);

        btnAgregarEvento = findViewById(R.id.btnAddEvento);
        btnEditarEvento = findViewById(R.id.btnEditEvento);
        btnBuscarEvento = findViewById(R.id.btnBuscarEvento);
        btnEliminarrEvento = findViewById(R.id.btnDeleteEvento);
        btnMostrarEvento = findViewById(R.id.btnShowEvento);
        btnLimpiarEvento = findViewById(R.id.btnLimpiarEvento);


        //CREAMOS LA INSTANCIA CON EL MODELO
        ModeloEvento modeloEvento = new ModeloEvento(Evento.this);

        //GENERAR EL EVENTO DE AGREGAR EVENTO

        btnDPFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar calendar = Calendar.getInstance();
                 dia = calendar.get(Calendar.DAY_OF_MONTH);
                 mes = calendar.get(Calendar.MONTH);
                 anio = calendar.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(Evento.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // Muestra la fecha seleccionada en un TextView o realiza cualquier otra operación necesaria
                        fechaSeleccionada = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        etDPFechaEvento.setText(fechaSeleccionada);
                    }
                }, anio, mes, dia);
                datePickerDialog.show();
            }
        });
        //fin del boton de FECHA

        btnTPHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                 hora = calendar.get(Calendar.HOUR_OF_DAY);
                 minuto = calendar.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(Evento.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // Muestra la hora seleccionada en un TextView o realiza cualquier otra operación necesaria
                         horaSeleccionada = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute);
                        etTPHoraEvento.setText(horaSeleccionada);
                    }
                }, hora, minuto, true);

                timePickerDialog.show();
            }
        });
        //fin del boton de HORA

        //GENERAR EL EVENTO DE AGREGAR CATEGORIA
        btnAgregarEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modeloEvento.agregarEvento(Integer.valueOf(etIdEvento.getText().toString()), etNombreEvento.getText().toString(), etDPFechaEvento.getText().toString(), etTPHoraEvento.getText().toString());
                limpiar();
                Toast.makeText(getApplicationContext(), "EL EVENTO SE AGREGO CORRECTAMENTE", Toast.LENGTH_SHORT).show();
            }
        });

        // GENERAR EL EVENTO DE BUSCAR EVENTO
        btnBuscarEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClaseEvento evento = new ClaseEvento();
                String textId = etIdEvento.getText().toString();
                int id = Integer.valueOf(textId);
                modeloEvento.buscarEvento(evento, id);
                etNombreEvento.setText(evento.getNombre());
                etDPFechaEvento.setText(evento.getFecha());
                etTPHoraEvento.setText(evento.getHora());
            }
        });

        // Evento de mostrar eventos
        btnMostrarEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mostrarEventos = new Intent(getApplicationContext(), EventoMostrar.class);
                startActivity(mostrarEventos);
            }
        });

        // GENERAR EVENTO DE EDITAR O ACTUALIZAR EVENTO
        btnEditarEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modeloEvento.editarEvento(Integer.valueOf(etIdEvento.getText().toString()), etNombreEvento.getText().toString(), etDPFechaEvento.getText().toString(), etTPHoraEvento.getText().toString());
                limpiar();
                Toast.makeText(getApplicationContext(), "Los datos del evento se actualizaron correctamente", Toast.LENGTH_SHORT).show();
            }
        });

        // Evento de eliminar evento
        btnEliminarrEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modeloEvento.eliminarEvento(Integer.valueOf(etIdEvento.getText().toString()));
                limpiar();
                Toast.makeText(getApplicationContext(), "Los datos del evento se eliminaron correctamente", Toast.LENGTH_SHORT).show();
            }
        });

        //GENERAR EVENTO DE LIMPIAR LOS CAMPOS
        btnLimpiarEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                limpiar();
            }
        });
    }

    private void limpiar(){
        etIdEvento.setText("");
        etNombreEvento.setText("");
        etDPFechaEvento.setText("");
        etTPHoraEvento.setText("");
    }



}