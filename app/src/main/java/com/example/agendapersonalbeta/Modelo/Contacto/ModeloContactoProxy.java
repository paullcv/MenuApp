package com.example.agendapersonalbeta.Modelo.Contacto;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.agendapersonalbeta.Modelo.AgendaBD;

import java.util.List;

public class ModeloContactoProxy extends AgendaBD implements IModeloContacto{

    Context context;
    ModeloContacto modeloContacto;

    boolean agregadoCorrectamente;
    public ModeloContactoProxy(@Nullable Context context) {
        super(context);
        this.context = context;
        modeloContacto = new ModeloContacto(context.getApplicationContext());
    }


    @Override
    public void agregarContacto(Integer id, String nombre, String correo, Integer telefono, String direccion) {
        // Validaciones adicionales antes de llamar al método original

        if (id <= 0) {
            agregadoCorrectamente = false;
            Toast.makeText(context.getApplicationContext(), "Error: El ID del contacto debe ser mayor a cero.", Toast.LENGTH_SHORT).show();
            return; // Salir del método sin llamar al método original
        }

        if (nombre == null || nombre.isEmpty()) {
            agregadoCorrectamente = false;
            Toast.makeText(context.getApplicationContext(), "Error: El nombre del contacto no puede estar vacío.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (correo != null && !correo.matches("[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
            agregadoCorrectamente = false;
            Toast.makeText(context.getApplicationContext(), "Error: El correo electrónico no es válido.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (telefono != null && (telefono.toString().length() > 8 || telefono <= 0)) {
            agregadoCorrectamente = false;
            Toast.makeText(context.getApplicationContext(), "Error: El número de teléfono no es válido.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Llamar al método original
        modeloContacto.agregarContacto(id, nombre, correo, telefono, direccion);
        agregadoCorrectamente = true;
    }

    @Override
    public List<ClaseContacto> mostrarContactos() {
        return modeloContacto.mostrarContactos();
    }

    @Override
    public void buscarContacto(ClaseContacto contacto, Integer id) {
        if (id <= 0) {
            Toast.makeText(context.getApplicationContext(), "Error: El ID del contacto debe ser mayor a cero.", Toast.LENGTH_SHORT).show();
            return; // Salir del método sin llamar al método original
        }
        modeloContacto.buscarContacto(contacto, id);
    }

    @Override
    public void editarContacto(Integer id, String nombre, String correo, Integer telefono, String direccion) {
        if (id <= 0) {
            agregadoCorrectamente = false;
            Toast.makeText(context.getApplicationContext(), "Error: El ID del contacto debe ser mayor a cero.", Toast.LENGTH_SHORT).show();
            return; // Salir del método sin llamar al método original
        }

        if (nombre == null || nombre.isEmpty()) {
            agregadoCorrectamente = false;
            Toast.makeText(context.getApplicationContext(), "Error: El nombre del contacto no puede estar vacío.", Toast.LENGTH_SHORT).show();;
            return; // Salir del método sin llamar al método original
        }

        if (correo != null && !correo.matches("[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
            agregadoCorrectamente = false;
            Toast.makeText(context.getApplicationContext(), "Error: El correo electrónico no es válido.", Toast.LENGTH_SHORT).show();
            return; // Salir del método sin llamar al método original
        }

        if (telefono != null && (telefono.toString().length() >8 || telefono <= 0)) {
            agregadoCorrectamente = false;
            Toast.makeText(context.getApplicationContext(), "Error: El número de teléfono no es válido.", Toast.LENGTH_SHORT).show();
            return; // Salir del método sin llamar al método original
        }

        // Llamar al método original
        modeloContacto.editarContacto(id, nombre, correo, telefono, direccion);
        agregadoCorrectamente = true;
    }

    @Override
    public void eliminarContacto(Integer id) {
        // Validaciones adicionales antes de llamar al método original
        if (id <= 0) {
            Toast.makeText(context.getApplicationContext(), "Error: El ID del contacto debe ser mayor a cero.", Toast.LENGTH_SHORT).show();
            return; // Salir del método sin llamar al método original
        }

        // Llamar al método original
        modeloContacto.eliminarContacto(id);
    }

    public boolean isAgregadoCorrectamente() {
        return agregadoCorrectamente;
    }
}
