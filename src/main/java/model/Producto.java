package model;

import java.util.Objects;

/**
 * @author Oussama rajil
 * @version 1.0.0
 */
public class Producto{

    private int codigo;
    private String nombre;
    private double precio;
    private String categoria;


    Producto(int codigo, String nombre, double precio, String categoria){
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.categoria = categoria;
    }

    //Getters:

    public int getCodigo() {
        return codigo;
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

    @Override
    public String toString() {
        return "Nombre : " + nombre + " | Precio : " + precio + " | Categoria : " + categoria;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Producto producto = (Producto) o;
        return codigo == producto.codigo &&
                Double.compare(precio, producto.precio) == 0 &&
                Objects.equals(nombre, producto.nombre) &&
                Objects.equals(categoria, producto.categoria);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo, nombre, precio, categoria);
    }
}
