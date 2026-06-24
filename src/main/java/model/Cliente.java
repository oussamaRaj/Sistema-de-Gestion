package model;

import java.util.Objects;

/**
 * @author Oussama Rajil
 * @version 1.0.0
 */
public class Cliente {
    private int codigo;
    private String dni;
    private String nombre;
    private String apellidos;
    private String email;
    private String telefono;
    private String direccion;

    public Cliente(int codigo, String dni, String nombre, String apellidos, String email, String telefono, String direccion) {
        this.codigo = codigo;
        this.dni = dni;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    //Getters:

    public int getCodigo() {
        return codigo;
    }
    public String getDni() {
        return dni;
    }
    public String getNombre() {
        return nombre;
    }
    public String getApellidos() {
        return apellidos;
    }
    public String getEmail() {
        return email;
    }
    public String getTelefono() {
        return telefono;
    }
    public String getDireccion() {
        return direccion;
    }

    @Override
    public String toString() {
        return "Dni : " + dni + " | Nombre : " + nombre + " | Apellidos : " + apellidos +  " | Email : " + email
                + " | Telefono : " + telefono + " | Direccion : " + direccion;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return codigo == cliente.codigo &&
                Objects.equals(dni, cliente.dni) &&
                Objects.equals(nombre, cliente.nombre) &&
                Objects.equals(apellidos, cliente.apellidos) &&
                Objects.equals(email, cliente.email) &&
                Objects.equals(telefono, cliente.telefono) &&
                Objects.equals(direccion, cliente.direccion);
    }
    @Override
    public int hashCode() {
        return Objects.hash(codigo, dni,nombre, apellidos, email, telefono,direccion);
    }

}
