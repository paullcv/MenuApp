package com.example.agendapersonalbeta.Controlador.Evento;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agendapersonalbeta.Modelo.Evento.ClaseEvento;
import com.example.agendapersonalbeta.R;

import java.util.List;

public class EventoAdaptador extends RecyclerView.Adapter<EventoAdaptador.ViewHolder> {

    //Lista de los objetos contactos
    public List<ClaseEvento> eventoLista;

    public EventoAdaptador(List<ClaseEvento> eventoLista) {
        this.eventoLista = eventoLista;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        //varaibles de la vista que van a ser recicladas
        private TextView id, nombre, fecha, hora;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            id = (TextView) itemView.findViewById(R.id.textViewIdEvento);
            nombre = (TextView) itemView.findViewById(R.id.textViewNombreEvento);
            fecha = (TextView) itemView.findViewById(R.id.textViewFechaEvento);
            hora = (TextView) itemView.findViewById(R.id.textViewHoraaEvento);
        }
    }


    @NonNull
    @Override
    public EventoAdaptador.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_evento, parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull EventoAdaptador.ViewHolder holder, int position) {
        holder.id.setText(String.valueOf(eventoLista.get(position).getId()));
        holder.nombre.setText(eventoLista.get(position).getNombre());
        holder.fecha.setText(eventoLista.get(position).getFecha());
        holder.hora.setText(eventoLista.get(position).getHora());
    }

    @Override
    public int getItemCount() {
        return eventoLista.size();
    }


}
