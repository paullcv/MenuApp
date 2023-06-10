package com.example.agendapersonalbeta.Controlador.Prestamo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.agendapersonalbeta.Modelo.Categoria.ClaseCategoria;
import com.example.agendapersonalbeta.Modelo.Contacto.ClaseContacto;
import com.example.agendapersonalbeta.Modelo.Prestamo.ClasePrestamo;
import com.example.agendapersonalbeta.R;

import java.util.List;

public class PrestamoAdaptador extends RecyclerView.Adapter<PrestamoAdaptador.ViewHolder>  {

    //Lista de los objetos contactos
    public List<ClasePrestamo> prestamoLista;
    //LSITA PAR MOSTRAR EL NOMBRE APARTIR DEL ID
    private List<ClaseContacto> contactoLista; // Agrega esta lista de contactos
    private List<ClaseCategoria> categoriaLista; // Agrega esta lista de contactos


    public PrestamoAdaptador(List<ClasePrestamo> prestamoLista, List<ClaseContacto> contactoLista, List<ClaseCategoria> categoriaLista) {
        this.prestamoLista = prestamoLista;
        this.contactoLista = contactoLista; // Asigna la lista de contactos recibida
        this.categoriaLista = categoriaLista; // Asigna la lista de categorías recibida
    }



    public static class ViewHolder extends RecyclerView.ViewHolder{
        //varaibles de la vista que van a ser recicladas
        private TextView id, monto, fechaPrestamo, fechaVencimientoPrestamo, contacto_id, categoria_id;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            id = (TextView) itemView.findViewById(R.id.textViewIdPrestamo);
            monto = (TextView) itemView.findViewById(R.id.textViewMontoPrestamo);
            fechaPrestamo = (TextView) itemView.findViewById(R.id.textViewfechaCreacionPrestamo);
            fechaVencimientoPrestamo = (TextView) itemView.findViewById(R.id.textViewfechaVencimientoPrestamo);
            contacto_id = (TextView) itemView.findViewById(R.id.textViewContacto_id);
            categoria_id = (TextView) itemView.findViewById(R.id.textViewCategoria_id);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_prestamo, parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.id.setText(String.valueOf(prestamoLista.get(position).getId()));
        holder.monto.setText(String.valueOf(prestamoLista.get(position).getMonto()));
        holder.fechaPrestamo.setText(prestamoLista.get(position).getFechaPrestamo());
        holder.fechaVencimientoPrestamo.setText(prestamoLista.get(position).getFechaVencimientoPrestamo());
        //holder.contacto_id.setText(String.valueOf(prestamoLista.get(position).getContacto_id()));
        holder.contacto_id.setText(obtenerNombreContacto(prestamoLista.get(position).getContacto_id())); // Obtener el nombre del contacto
        //holder.categoria_id.setText(String.valueOf(prestamoLista.get(position).getCategoria_id()));
        holder.categoria_id.setText(obtenerNombreCategoria(prestamoLista.get(position).getCategoria_id())); // Obtener el nombre de la categoría
    }

    @Override
    public int getItemCount() {
        return prestamoLista.size();
    }

    //FUNCION PAR MOSTRAR EL NOMBRE APARTIR DEL ID
    private String obtenerNombreContacto(int contactoId) {
        for (ClaseContacto contacto : contactoLista) {
            if (contacto.getId() == contactoId) {
                return contacto.getNombre();
            }
        }
        return ""; // Si no se encuentra el contacto, retorna una cadena vacía
    }

    //FUNCION PAR MOSTRAR LA CATEGORIA APARTIR DEL ID
    private String obtenerNombreCategoria(int categoriaId) {
        for (ClaseCategoria categoria : categoriaLista) {
            if (categoria.getId() == categoriaId) {
                return categoria.getTitulo();
            }
        }
        return ""; // Si no se encuentra la categoría, retorna una cadena vacía
    }


}
