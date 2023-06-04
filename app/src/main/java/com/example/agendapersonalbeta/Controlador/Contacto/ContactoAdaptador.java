package com.example.agendapersonalbeta.Controlador.Contacto;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agendapersonalbeta.Modelo.Contacto.ClaseContacto;
import com.example.agendapersonalbeta.R;

import java.util.List;

public class ContactoAdaptador extends RecyclerView.Adapter<ContactoAdaptador.ViewHolder> {

    //Lista de los objetos contactos
    public List<ClaseContacto> contactoLista;

    public ContactoAdaptador(List<ClaseContacto> contactoLista) {
        this.contactoLista = contactoLista;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        //varaibles de la vista que van a ser recicladas

        private TextView id, nombre, correo, telefono, direccion;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            id = (TextView) itemView.findViewById(R.id.textViewId);
            nombre = (TextView) itemView.findViewById(R.id.textViewNombre);
            correo = (TextView) itemView.findViewById(R.id.textViewCorreo);
            telefono = (TextView) itemView.findViewById(R.id.textViewTelefono);
            direccion = (TextView) itemView.findViewById(R.id.textViewDireccion);

        }
    }


    @NonNull
    @Override
    public ContactoAdaptador.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contacto, parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ContactoAdaptador.ViewHolder holder, int position) {
        holder.id.setText(String.valueOf(contactoLista.get(position).getId()));
        holder.nombre.setText(contactoLista.get(position).getNombre());
        holder.correo.setText(contactoLista.get(position).getCorreo());
        holder.telefono.setText(String.valueOf(contactoLista.get(position).getTelefono()));
        holder.direccion.setText(contactoLista.get(position).getDireccion());

    }

    @Override
    public int getItemCount() {
        return contactoLista.size();
    }
}
