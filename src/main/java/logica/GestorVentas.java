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
            System.out.println("===MENÚ GESTOR PRODUCTOS===");
            System.out.println("1. Resgistrar Venta");
            System.out.println("2. Mostrar Todas Ventas");
            System.out.println("3. ");
            System.out.println("4. ");
            System.out.println("5. ");
            System.out.println("9. ");
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

                    break;
                case 3:

                    break;
                case 4:

                    break;
                case 5:

                    break;
                case 9:

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
}
