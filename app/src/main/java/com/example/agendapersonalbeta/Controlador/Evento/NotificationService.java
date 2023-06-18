package com.example.agendapersonalbeta.Controlador.Evento;

import android.Manifest;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.IBinder;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.agendapersonalbeta.Modelo.Evento.ClaseEvento;
import com.example.agendapersonalbeta.Modelo.Evento.ModeloEvento;
import com.example.agendapersonalbeta.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NotificationService extends Service {

    private ModeloEvento modeloEvento;
    private SimpleDateFormat dateFormat;
    private Date fechaActual;
    private Handler handler;

    private static final String CHANNEL_ID = "EventoChannel";
    private static final int NOTIFICATION_ID = 1;

    private final int NOTIFICATION_INTERVAL = 60 * 1000; // Intervalo de notificación (1 minuto)

    @Override
    public void onCreate() {
        super.onCreate();
        modeloEvento = new ModeloEvento(getApplicationContext());
        dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        fechaActual = Calendar.getInstance().getTime();
        handler = new Handler();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        handler.postDelayed(notificationRunnable, NOTIFICATION_INTERVAL);
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private Runnable notificationRunnable = new Runnable() {
        @Override
        public void run() {
            List<ClaseEvento> eventos = modeloEvento.mostrarEventos();

            for (ClaseEvento evento : eventos) {
                if (validarFecha(evento.getFecha())) {
                    createNotification(evento.getId(), evento.getNombre(), evento.getFecha(), evento.getHora());
                }
            }

            handler.postDelayed(this, NOTIFICATION_INTERVAL);
        }
    };

    private boolean validarFecha(String fecha) {
        try {
            Date fechaEvento = dateFormat.parse(fecha);
            return fechaEvento.after(fechaActual) || fechaEvento.equals(fechaActual);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void createNotification(int id, String nombre, String fecha, String hora) {
        // Crear intent para abrir la actividad del evento al hacer clic en la notificación
        Intent intent = new Intent(getApplicationContext(), Evento.class);
        intent.putExtra("id", id);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Construir la notificación
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setSmallIcon(R.drawable.evento)
                .setContentTitle("Recordatorio de evento")
                .setContentText(nombre)
                .setStyle(new NotificationCompat.BigTextStyle().bigText("Fecha: " + fecha + "\nHora: " + hora))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        // Mostrar la notificación
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
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
}
