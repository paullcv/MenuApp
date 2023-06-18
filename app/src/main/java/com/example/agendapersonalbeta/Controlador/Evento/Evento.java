package com.example.agendapersonalbeta.Controlador.Evento;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.agendapersonalbeta.Modelo.Evento.ClaseEvento;
import com.example.agendapersonalbeta.Modelo.Evento.ModeloEvento;
import com.example.agendapersonalbeta.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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

    private SimpleDateFormat dateFormat;
    private Date fechaActual;

    private static final String CHANNEL_ID = "EventoChannel";
    private static final int NOTIFICATION_ID = 1;


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


        dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        fechaActual = Calendar.getInstance().getTime();
        createNotificationChannel();


        //CREAMOS LA INSTANCIA CON EL MODELO
        ModeloEvento modeloEvento = new ModeloEvento(Evento.this);


        List<ClaseEvento> eventos = modeloEvento.mostrarEventos();
        for (ClaseEvento evento : eventos) {
            if (validarFecha(evento.getFecha())) {
                createNotification(evento.getId(), evento.getNombre(), evento.getFecha(), evento.getHora());
            }
        }
        startService(new Intent(this, NotificationService.class));


        //BOTON FECHA EVENTO
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

        //BOTON HORA EVENTO
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

        //GENERAR EL EVENTO DE AGREGAR EVENTO
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Detener el servicio NotificationService
        stopService(new Intent(this, NotificationService.class));
    }


    private boolean validarFecha(String fecha) {
        try {
            Date fechaEvento = dateFormat.parse(fecha);
            return fechaEvento.after(fechaActual) || fechaEvento.equals(fechaActual);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Evento Channel";
            String description = "Canal de notificación para eventos";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void createNotification(int id, String nombre, String fecha, String hora) {
        // Crear intent para abrir la actividad del evento al hacer clic en la notificación
        Intent intent = new Intent(this, Evento.class);
        intent.putExtra("id", id);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Construir la notificación
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.evento)
                .setContentTitle("Recordatorio de evento")
                .setContentText(nombre)
                .setStyle(new NotificationCompat.BigTextStyle().bigText("Fecha: " + fecha + "\nHora: " + hora))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        // Mostrar la notificación
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    private void limpiar() {
        etIdEvento.setText("");
        etNombreEvento.setText("");
        etDPFechaEvento.setText("");
        etTPHoraEvento.setText("");
    }

    //GENERAR NOTIFICACIONES




}