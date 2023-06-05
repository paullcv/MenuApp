package com.example.agendapersonalbeta.Controlador.Categoria;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agendapersonalbeta.Modelo.Categoria.ClaseCategoria;
import com.example.agendapersonalbeta.R;

import java.util.List;

public class CategoriaAdaptador extends RecyclerView.Adapter<CategoriaAdaptador.ViewHolder>{

    //Lista de los objetos contactos
    public List<ClaseCategoria> categoriaLista;

    public CategoriaAdaptador(List<ClaseCategoria> contactoLista) {
        this.categoriaLista = contactoLista;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        //varaibles de la vista que van a ser recicladas
        private TextView id, titulo, descripcion;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            id = (TextView) itemView.findViewById(R.id.textViewIdCategoria);
            titulo = (TextView) itemView.findViewById(R.id.textViewTituloCategoria);
            descripcion = (TextView) itemView.findViewById(R.id.textViewDescripcionCategoria);
        }
    }

    @NonNull
    @Override
    public CategoriaAdaptador.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_categoria, parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriaAdaptador.ViewHolder holder, int position) {
        holder.id.setText(String.valueOf(categoriaLista.get(position).getId()));
        holder.titulo.setText(categoriaLista.get(position).getTitulo());
        holder.descripcion.setText(categoriaLista.get(position).getDescripcion());
    }

    @Override
    public int getItemCount() {
        return categoriaLista.size();
    }

}
