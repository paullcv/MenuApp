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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Evento extends AppCompatActivity {

    private static final String CHANNEL_ID = "evento_channel";
    private static final int NOTIFICATION_ID = 1;

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

    private SimpleDateFormat dateFormat;
    private SimpleDateFormat timeFormat;
    private Date fechaActual;

    private ModeloEvento modeloEvento;

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
        timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        fechaActual = Calendar.getInstance().getTime();

        // Crear instancia del modelo
        modeloEvento = new ModeloEvento(Evento.this);

        // Botón Fecha Evento
        btnDPFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int anio = calendar.get(Calendar.YEAR);
                int mes = calendar.get(Calendar.MONTH);
                int dia = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(Evento.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        String fecha = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        etDPFechaEvento.setText(fecha);
                    }
                }, anio, mes, dia);
                datePickerDialog.show();
            }
        });

        // Botón Hora Evento
        btnTPHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int hora = calendar.get(Calendar.HOUR_OF_DAY);
                int minuto = calendar.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(Evento.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        String hora = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute);
                        etTPHoraEvento.setText(hora);
                    }
                }, hora, minuto, true);
                timePickerDialog.show();
            }
        });

        // Evento Agregar Evento
        btnAgregarEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    int id = Integer.parseInt(etIdEvento.getText().toString());
                    String nombre = etNombreEvento.getText().toString();
                    String fecha = etDPFechaEvento.getText().toString();
                    String hora = etTPHoraEvento.getText().toString();

                    modeloEvento.agregarEvento(id, nombre, fecha, hora);
                    limpiarCampos();
                    Toast.makeText(getApplicationContext(), "El evento se agregó correctamente", Toast.LENGTH_SHORT).show();
                    programarNotificacion(id, nombre, fecha, hora); // Programar notificación
                } catch (NumberFormatException e) {
                    Toast.makeText(getApplicationContext(), "El ID del evento debe ser un número válido", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Evento Buscar Evento
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

        // Evento Mostrar Eventos
        btnMostrarEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mostrarEventos = new Intent(getApplicationContext(), EventoMostrar.class);
                startActivity(mostrarEventos);
            }
        });

        // Evento Editar Evento
        btnEditarEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    int id = Integer.parseInt(etIdEvento.getText().toString());
                    String nombre = etNombreEvento.getText().toString();
                    String fecha = etDPFechaEvento.getText().toString();
                    String hora = etTPHoraEvento.getText().toString();

                    modeloEvento.editarEvento(id, nombre, fecha, hora);
                    limpiarCampos();
                    Toast.makeText(getApplicationContext(), "Los datos del evento se actualizaron correctamente", Toast.LENGTH_SHORT).show();
                    programarNotificacion(id, nombre, fecha, hora); // Programar notificación
                } catch (NumberFormatException e) {
                    Toast.makeText(getApplicationContext(), "El ID del evento debe ser un número válido", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Evento Eliminar Evento
        btnEliminarrEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    int id = Integer.parseInt(etIdEvento.getText().toString());
                    modeloEvento.eliminarEvento(id);
                    limpiarCampos();
                    Toast.makeText(getApplicationContext(), "Los datos del evento se eliminaron correctamente", Toast.LENGTH_SHORT).show();
                    cancelarNotificacion(id); // Cancelar notificación si existe
                } catch (NumberFormatException e) {
                    Toast.makeText(getApplicationContext(), "El ID del evento debe ser un número válido", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Evento Limpiar Campos
        btnLimpiarEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                limpiarCampos();
            }
        });
    }

    private void limpiarCampos() {
        etIdEvento.setText("");
        etNombreEvento.setText("");
        etDPFechaEvento.setText("");
        etTPHoraEvento.setText("");
    }

    private void programarNotificacion(int id, String nombre, String fecha, String hora) {
        try {
            Date fechaEvento = dateFormat.parse(fecha);
            Date horaEvento = timeFormat.parse(hora);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(fechaEvento);
            calendar.set(Calendar.HOUR_OF_DAY, horaEvento.getHours());
            calendar.set(Calendar.MINUTE, horaEvento.getMinutes());
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);

            long tiempoNotificacion = calendar.getTimeInMillis();

            if (tiempoNotificacion > System.currentTimeMillis()) {
                // Crear canal de notificación
                crearCanalNotificacion();

                // Configurar intent para la notificación
                Intent intent = new Intent(this, EventoMostrar.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_MUTABLE);

                // Formatear la hora del evento
                SimpleDateFormat horaFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
                String horaFormateada = horaFormat.format(horaEvento);

                // Construir la notificación
                NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                        .setSmallIcon(R.drawable.eventos2)
                        .setContentTitle("Recordatorio de evento")
                        .setContentText("Evento: " + nombre)
                        .setContentText("Evento: " + nombre + "\nHora: " + horaFormateada)
                        .setContentIntent(pendingIntent)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
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
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void cancelarNotificacion(int id) {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.cancel(id);
    }

    private void crearCanalNotificacion() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence nombre = "Eventos";
            String descripcion = "Canal de notificaciones para eventos";
            int importancia = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, nombre, importancia);
            channel.setDescription(descripcion);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

} 