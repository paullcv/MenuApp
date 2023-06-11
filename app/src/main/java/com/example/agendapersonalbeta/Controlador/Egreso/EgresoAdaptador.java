package com.example.agendapersonalbeta.Controlador.Egreso;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agendapersonalbeta.Modelo.Categoria.ClaseCategoria;
import com.example.agendapersonalbeta.Modelo.Egreso.ClaseEgreso;
import com.example.agendapersonalbeta.R;

import java.util.List;


public class EgresoAdaptador extends RecyclerView.Adapter<EgresoAdaptador.ViewHolder>{

    //Lista de los objetos contactos
    public List<ClaseEgreso> egresoLista;
    private List<ClaseCategoria> categoriaLista; // Agrega esta lista de categorías

    public EgresoAdaptador(List<ClaseEgreso> egresoLista, List<ClaseCategoria> categoriaLista) {
        this.egresoLista = egresoLista;
        this.categoriaLista = categoriaLista;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        //varaibles de la vista que van a ser recicladas
        private TextView id, monto, nombre, fecha, categoria_id;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            id = (TextView) itemView.findViewById(R.id.textViewIdEgreso);
            monto = (TextView) itemView.findViewById(R.id.textViewMontoEgreso);
            nombre = (TextView) itemView.findViewById(R.id.textViewNombreEgreso);
            fecha = (TextView) itemView.findViewById(R.id.textViewfechaCreacionEgreso);
            categoria_id = (TextView) itemView.findViewById(R.id.textViewEgresoCategoria_id);
        }
    }


    @NonNull
    @Override
    public EgresoAdaptador.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_egreso, parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull EgresoAdaptador.ViewHolder holder, int position) {
        holder.id.setText(String.valueOf(egresoLista.get(position).getId()));
        holder.monto.setText(String.valueOf(egresoLista.get(position).getMonto()));
        holder.nombre.setText(egresoLista.get(position).getNombre());
        holder.fecha.setText(egresoLista.get(position).getFecha());
        //holder.categoria_id.setText(String.valueOf(ingresoLista.get(position).getCategoria_id()));
        holder.categoria_id.setText(obtenerNombreCategoria(egresoLista.get(position).getCategoria_id())); // Obtener el titulo de la categoría
    }

    @Override
    public int getItemCount() {
        return egresoLista.size();
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
