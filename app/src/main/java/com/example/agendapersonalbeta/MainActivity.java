package com.example.agendapersonalbeta;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.Toast;

import com.example.agendapersonalbeta.Controlador.Contacto.Contacto;

public class MainActivity extends AppCompatActivity {

    //Variables de la vista
    GridLayout menuAgenda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        java.text.DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getApplicationContext());
        setContentView(R.layout.activity_main);

        menuAgenda = (GridLayout)findViewById(R.id.menuGrid);

        //Metodo para poder interactuar con las opciones de los cardView
        setSigleEvent(menuAgenda);

    }

    private void setSigleEvent(GridLayout menuAgenda) {
        for (int i = 0; i < menuAgenda.getChildCount(); i++){
            CardView cardView = (CardView) menuAgenda.getChildAt(i);
            final int finalI = i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (finalI == 0){
                        Intent intent = new Intent(MainActivity.this, Contacto.class);
                        startActivity(intent);
                    }else if(finalI == 1){
                        Intent intent = new Intent(MainActivity.this, Evento.class);
                        startActivity(intent);
                    } else if (finalI == 2) {
                        Intent intent = new Intent(MainActivity.this,Ingreso.class);
                        startActivity(intent);
                    } else if (finalI == 3) {
                        Intent intent = new Intent(MainActivity.this,Egreso.class);
                        startActivity(intent);
                    } else if (finalI == 4) {
                        Intent intent = new Intent(MainActivity.this, Prestamo.class);
                        startActivity(intent);
                    } else if (finalI == 5) {
                        Intent intent = new Intent(MainActivity.this, Categoria.class);
                        startActivity(intent);
                    }else {
                        Toast.makeText(MainActivity.this, "No existe", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}