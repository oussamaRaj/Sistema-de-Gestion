package logica;

import ConexionDB.GestorConexion;
import model.Cliente;
import model.Producto;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class GestorVentas {

    static final Scanner SC = new Scanner(System.in);
    static int contador = 1;


    public static void menu() {

        int opcion;
        do {
            System.out.println("===MENÚ GESTOR VENTAS===");
            System.out.println("1. Resgistrar Venta");
            System.out.println("2. Mostrar Todas Ventas");
            System.out.println("3. Buscar Venta");
            System.out.println("4. Eliminar Venta");
            System.out.println("5. Mostrar Ventas de un Cliente");
            System.out.println("0. Salir");
            System.out.println("");
            System.out.printf("Opcion: ");
            opcion = SC.nextInt();
            SC.nextLine();

            switch (opcion) {
                case 1:
                    registrarVenta();
                    break;
                case 2:
                    mostrarVentas();
                    break;
                case 3:
                    buscarVenta();
                    break;
                case 4:
                    eliminarVenta();
                    break;
                case 5:
                    mostrarVentasCliente();
                    break;
                case 0:
                    System.out.println("Saliendo del Gestor de Los Ventas..");
                    break;
                default:
                    System.out.println("Opción no existe!");
                    break;
            }
        } while (opcion != 0);

    }

    public static void registrarVenta(){
        System.out.println("===REGISTRAR VENTA===");
        System.out.println("Ingrese el codigo del producto:");
        int codigoProducto = SC.nextInt();
        SC.nextLine();

        System.out.println("Ingrese el codigo del Cliente:");
        int codigoCliente = SC.nextInt();
        SC.nextLine();

        System.out.println("Ingrese la cantidad del producto:");
        int cantidad = SC.nextInt();

        LocalDate fecha = LocalDate.now();
        Producto producto = GestorProductos.buscarProductoPorCodigo(codigoProducto);
        Cliente cliente = GestorClientes.buscarClientePorCodigo(codigoCliente);

        if (producto == null) {
            System.out.println("Producto no existe!");
        }
        if ( cliente == null){
            System.out.println("Cliente no existe!");
            return;
        }
        double total = producto.getPrecio() * cantidad;
        SC.nextLine();

        String sql = "INSERT INTO venta (codigoProducto, codigoCliente, cantidad,fecha, total) VALUES (?,?,?,?,?)";
        try(Connection conn = GestorConexion.obtenerConexion();
            PreparedStatement ptmt = conn.prepareStatement(sql)){

            ptmt.setInt(1, codigoProducto);
            ptmt.setInt(2, codigoCliente);
            ptmt.setInt(3, cantidad);
            ptmt.setDate(4, Date.valueOf(fecha));
            ptmt.setDouble(5, total);

            escribirTicket(cliente.getNombre() +" " + cliente.getApellidos() , producto.getNombre(), cantidad, producto.getPrecio(), total);
            int fila = ptmt.executeUpdate();
            if (fila > 0) {
                System.out.println("Registrado exitosamente!");
            }else {
                System.out.println("Error al registrar! Intenta mas tarde.");
            }
        }catch(SQLException ex){
            System.out.println("ERROR : " + ex.getMessage());
        }
    }
    public static void escribirTicket(String cliente, String producto, int cantidad, double precio, double total) {

        LocalDateTime now = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd  | HH:mm:ss");
        String fechaHora = now.format(formatter);

        String nomFichero = "ticket_" + contador +".txt";

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(nomFichero))) {

            bw.write("======== TICKET ========\n");
            bw.write("Cliente  : " + cliente + "\n");
            bw.write("Producto : " + producto + "\n");
            bw.write("Cantidad : " + cantidad + "\n");
            bw.write("Precio   : " + precio + " €\n");
            bw.write("Total    : " + String.format("%.2f", total) + " €\n");
            bw.write("Fecha y Hora : " + fechaHora + "\n");
            bw.write("========================\n\n");
            contador++;

        } catch (IOException e) {
            System.out.println("Error al generar ticket: " + e.getMessage());
        }
    }
    public static void mostrarVentas(){
        System.out.println("===MOSTRAR VENTAS===");
        System.out.println("==Ventas Encontrados==");
        String sql = "select v.id, v.cantidad, v.fecha, v.total,c.nombre as cliente, c.apellidos as apellidos, p.nombre as producto from venta v join cliente c on v.codigoCliente = c.codigo join producto p on v.codigoProducto = p.codigo";
        try(Connection conn = GestorConexion.obtenerConexion();
            PreparedStatement ptmt = conn.prepareStatement(sql)){

            ResultSet rs = ptmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String nombreCliente = rs.getString("cliente") + " " + rs.getString("apellidos");
                String apellidos = rs.getString("apellidos");
                String nombreProducto = rs.getString("producto");
                int cantidad = rs.getInt("cantidad");
                LocalDate fecha = rs.getDate("fecha").toLocalDate();
                double total =  rs.getDouble("total");

                System.out.println("ID : " + id + " | Cliente : " + nombreCliente + " | Producto : " + nombreProducto + " | Cantidad : " + cantidad +
                                     " | fecha : " + fecha +" | Total : " + String.format("%.2f", total) + " €");
            }
        }catch(SQLException ex){
            System.out.println("ERROR : " + ex.getMessage());
        }

    }
    public static void mostrarVentasCliente(){
        System.out.println("===BUSCAR VENTAS DEL CLIENTE===");
        System.out.println("Introduce el Dni del cliente : ");
        String dni = SC.nextLine();

        String sql = "SELECT c.dni AS Dni,\n" +
                "       v.id,\n" +
                "       v.codigoProducto,\n" +
                "       v.codigoCliente,\n" +
                "       v.cantidad,\n" +
                "       v.fecha,\n" +
                "       v.total\n" +
                "FROM venta v\n" +
                "JOIN cliente c ON v.codigoCliente = c.codigo\n" +
                "WHERE c.dni = ?";
        try(Connection conn = GestorConexion.obtenerConexion();
            PreparedStatement ptmt = conn.prepareStatement(sql)){
            ptmt.setString(1, dni);
            ResultSet rs = ptmt.executeQuery();
            while (rs.next()){
                String idn = rs.getString("Dni");
                int id = rs.getInt("id");
                int codPro = rs.getInt("codigoProducto");
                int codCliente = rs.getInt("codigoCliente");
                int cantidad = rs.getInt("cantidad");
                LocalDate fecha = rs.getDate("fecha").toLocalDate();
                double total = rs.getDouble("total");

                System.out.println("Dni : " + idn + " | Id Venta : " + id + " | Codigo Producto : " + codPro + " | Codigo Cliente : " + codCliente
                                    + " | Cantidad : " + cantidad + " | Fecha : " + fecha + " | Total : " + String.format("%.2f", total) + " €");
            }
        }catch(SQLException e){
            System.out.println("ERROR : " + e.getMessage());
        }
    }
    public static void buscarVenta(){
        System.out.println("===BUSCAR VENTA===");
        System.out.println("Introduce el Codigo de venta : ");
        String cod = SC.nextLine();

        String sql = "SELECT * FROM  venta WHERE id = ?";
        try(Connection conn = GestorConexion.obtenerConexion();
            PreparedStatement ptmt = conn.prepareStatement(sql)){
            ptmt.setString(1, cod);
            ResultSet rs = ptmt.executeQuery();
            while (rs.next()){
                String id = rs.getString("id");
                int codPro = rs.getInt("codigoProducto");
                int codCliente = rs.getInt("codigoCliente");
                int cantidad = rs.getInt("cantidad");
                LocalDate fecha = rs.getDate("fecha").toLocalDate();
                double total = rs.getDouble("total");

                System.out.println("Id : " + id + " | Codigo Producto : " + codPro + " | Codigo Cliente : " + codCliente + " | Cantidad : " + cantidad +
                                    " | Fecha : " + fecha + " | Total : " + String.format("%.2f", total) + " €");
            }
        }catch(SQLException e){
            System.out.println("ERROR : " + e.getMessage());
        }
    }
    public static void eliminarVenta(){
        System.out.println("===ELIMINAR VENTA===");
        System.out.println("Introduce el Codigo de venta que desea eliminar : ");
        int cod = SC.nextInt();
        String sql = "DELETE FROM venta WHERE id = ?";
        try (Connection conn = GestorConexion.obtenerConexion();
              PreparedStatement ptmt = conn.prepareStatement(sql)  ){

            ptmt.setInt(1, cod);

            int fila = ptmt.executeUpdate();
            if (fila > 0) {
                System.out.println("Venta eliminado con exito!");
            }
        }catch (SQLException e){
            System.out.println("ERROR : " + e.getMessage());
        }
    }
}
