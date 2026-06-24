package model;

import java.time.LocalDate;
import java.util.Objects;

public class Venta {
    private int id;
    private Producto codigoProducto;
    private Cliente codigoCliente;
    private int cantidad;
    private LocalDate fecha;
    private int total;

    public Venta(int id, Producto codigoProducto, Cliente codigoCliente, int cantidad, LocalDate fecha,  int total) {
        this.id = id;
        this.codigoProducto = codigoProducto;
        this.codigoCliente = codigoCliente;
        this.cantidad = cantidad;
        this.fecha = fecha;
        this.total = total;
    }

    //Getters:
    public int getId() {
        return id;
    }
    public Producto getCodigoProducto() {
        return codigoProducto;
    }
    public Cliente getCodigoCliente() {
        return codigoCliente;
    }
    public int getCantidad() {
        return cantidad;
    }
    public LocalDate getFecha() {
        return fecha;
    }
    public int getTotal() {
        return total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Venta venta = (Venta) o;
        return id == venta.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
