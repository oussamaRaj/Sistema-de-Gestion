package model;

import java.util.Objects;

/**
 * @author Oussama rajil
 * @version 1.0.0
 */
public class Producto{

    private int codigo;
    private String tipo;
    private String nombre;
    private double precio;
    private String descripcion;
    private String esDisponible;


    public Producto(int codigo, String tipo, String nombre, double precio, String descripcion, String esDisponible) {
        this.codigo = codigo;
        this.tipo = tipo;
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.esDisponible = esDisponible;
    }

    //Getters:

    public int getCodigo() {
        return codigo;
    }
    public String getTipo() {
        return tipo;
    }
    public String getNombre() {
        return nombre;
    }
    public double getPrecio() {
        return precio;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public String esDisponible() {
        return esDisponible;
    }

    //Setters

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setPrecio(double precio) {
        this.precio = precio;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public void setEsDisponible(String esDisponible) {
        this.esDisponible = esDisponible;
    }

    @Override
    public String toString() {
        return "Tipo : " + tipo + " | Nombre : " + nombre + " | Precio : " + precio + " | Descrepcion : " + descripcion ;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Producto producto = (Producto) o;
        return codigo == producto.codigo &&
                Double.compare(precio, producto.precio) == 0 &&
                Objects.equals(nombre, producto.nombre) &&
                Objects.equals(tipo, producto.tipo) &&
                Objects.equals(descripcion, producto.descripcion) &&
                Objects.equals(esDisponible, producto.esDisponible);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo, tipo, nombre, precio, descripcion, esDisponible);
    }
}
