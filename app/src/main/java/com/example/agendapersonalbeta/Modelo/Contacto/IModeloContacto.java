package com.example.agendapersonalbeta.Modelo.Contacto;

import java.util.List;

public interface IModeloContacto {

    void agregarContacto(Integer id, String nombre, String correo, Integer telefono, String direccion);

    List<ClaseContacto> mostrarContactos();

    void buscarContacto(ClaseContacto contacto, Integer id);

    void editarContacto(Integer id, String nombre, String correo, Integer telefono, String direccion);

    void eliminarContacto(Integer id);

}
