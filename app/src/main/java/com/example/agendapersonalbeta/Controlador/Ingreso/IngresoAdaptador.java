package com.example.agendapersonalbeta.Controlador.Ingreso;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.agendapersonalbeta.Modelo.Ingreso.ClaseIngreso;
import com.example.agendapersonalbeta.Modelo.Categoria.ClaseCategoria;
import com.example.agendapersonalbeta.R;

import java.util.List;

public class IngresoAdaptador extends RecyclerView.Adapter<IngresoAdaptador.ViewHolder>{

    //Lista de los objetos contactos
    public List<ClaseIngreso> ingresoLista;
    private List<ClaseCategoria> categoriaLista; // Agrega esta lista de categorías

    public IngresoAdaptador(List<ClaseIngreso> ingresoLista, List<ClaseCategoria> categoriaLista) {
        this.ingresoLista = ingresoLista;
        this.categoriaLista = categoriaLista;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        //varaibles de la vista que van a ser recicladas
        private TextView id, monto, nombre, fecha, categoria_id;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            id = (TextView) itemView.findViewById(R.id.textViewIdIngreso);
            monto = (TextView) itemView.findViewById(R.id.textViewMontoIngreso);
            nombre = (TextView) itemView.findViewById(R.id.textViewNombreIngreso);
            fecha = (TextView) itemView.findViewById(R.id.textViewfechaCreacionIngreso);
            categoria_id = (TextView) itemView.findViewById(R.id.textViewIngresoCategoria_id);
        }
    }

    @NonNull
    @Override
    public IngresoAdaptador.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ingreso, parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull IngresoAdaptador.ViewHolder holder, int position) {
        holder.id.setText(String.valueOf(ingresoLista.get(position).getId()));
        holder.monto.setText(String.valueOf(ingresoLista.get(position).getMonto()));
        holder.nombre.setText(ingresoLista.get(position).getNombre());
        holder.fecha.setText(ingresoLista.get(position).getFecha());
        //holder.categoria_id.setText(String.valueOf(ingresoLista.get(position).getCategoria_id()));
        holder.categoria_id.setText(obtenerNombreCategoria(ingresoLista.get(position).getCategoria_id())); // Obtener el titulo de la categoría
    }

    @Override
    public int getItemCount() {
        return ingresoLista.size();
    }

    private String obtenerNombreCategoria(int categoriaId) {
        for (ClaseCategoria categoria : categoriaLista) {
            if (categoria.getId() == categoriaId) {
                return categoria.getTitulo();
            }
        }
        return ""; // Si no se encuentra la categoría, retorna una cadena vacía
    }

}
