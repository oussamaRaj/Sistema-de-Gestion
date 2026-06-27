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
    private String categoria;
    private String descripcion;
    private String esDisponible;


    Producto(int codigo, String tipo, String nombre, double precio, String categoria, String descripcion, String esDisponible) {
        this.codigo = codigo;
        this.tipo = tipo;
        this.nombre = nombre;
        this.precio = precio;
        this.categoria = categoria;
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
    public String getCategoria() {
        return categoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String esDisponible() {
        return esDisponible;
    }

    @Override
    public String toString() {
        return "Tipo : " + tipo + " | Nombre : " + nombre + " | Precio : " + precio + " | Descrepcion : " + descripcion + " | Categoria : " + categoria;
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
                Objects.equals(categoria, producto.categoria) &&
                Objects.equals(descripcion, producto.descripcion) &&
                Objects.equals(esDisponible, producto.esDisponible);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo, tipo, nombre, precio, descripcion, categoria, esDisponible);
    }
}
