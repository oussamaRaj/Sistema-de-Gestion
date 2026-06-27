package logica;

import ConexionDB.GestorConexion;
import org.mariadb.jdbc.client.socket.impl.SocketUtility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * @author Oussama Rajil
 * @version 1.0.0
 */
public class GestorProductos {

    static final Scanner SC = new Scanner(System.in);

    public static void menu() {

        int opcion;
        do {
            System.out.println("===MENÚ GESTOR PRODUCTOS===");
            System.out.println("1. Añadir Producto");
            System.out.println("2. Modificar Producto");
            System.out.println("3. Eliminar Producto");
            System.out.println("4. Buscar Producto");
            System.out.println("5. Listar Productos");
            System.out.println("9. Eliminar Todos Productos");
            System.out.println("0. Salir");
            System.out.println("");
            System.out.printf("Opcion: ");
            opcion = SC.nextInt();
            SC.nextLine();

            switch (opcion) {
                case 1:
                    añadirProdocto();
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
                    System.out.println("Saliendo del Gestor de Los Productos..");
                    break;
                default:
                    System.out.println("Opción no existe!");
                    break;
            }
        } while (opcion != 0);

    }

    public static void añadirProdocto() {

        System.out.println("===AÑADIR PRODUCTO===");
        System.out.println(" ");



        System.out.println("Introduce el tipo del Producto : ");
        System.out.println("1. Dispositivo");
        System.out.println("2. Accesorio");
        System.out.println("3. Electrodoméstico");
        System.out.println("4. Ropa");
        System.out.println("5. Mueble");
        System.out.println("6. Herramiente");
        System.out.println("7. Otro..");
        int opcion = SC.nextInt();
        SC.nextLine();

        String tipo = "";

        switch (opcion) {
            case 1:
                tipo = "Dispositivo";
                break;
            case 2:
                tipo = "Accesorio";
                break;
            case 3:
                tipo = "Electrodoméstico";
                break;
            case 4:
                tipo = "Ropa";
                break;
            case 5:
                tipo = "Mueble";
                break;
            case 6:
                tipo = "Herramienta";
                break;
            case 7:
                System.out.println("Introduce qué tipo : ");
                tipo = SC.nextLine();
                break;
            default:
                System.out.println("Opcion no existe!");
        }


        System.out.println("Introduce el nombre del Producto : ");
        String nombre = SC.nextLine();

        System.out.println("Introduce el precio del Producto : ");
        double precio = SC.nextDouble();
        SC.nextLine();

        System.out.println("Introduce una descripcion sobre el Producto : ");
        String descripcion = SC.nextLine();

        System.out.println("es disponible ? : ");
        System.out.println("1. Sí");
        System.out.println("2. No");
        opcion = SC.nextInt();
        SC.nextLine();

        String esDisponible = "";

        switch (opcion) {
            case 1:
                esDisponible = "Sí";
                break;
            case 2:
                esDisponible = "No";
                break;
            default:
                System.out.println("Opcion no existe!");
                break;
        }

        String sql = "INSERT INTO producto (tipo, nombre, precio, descripcion, esDisponible) values (?, ?, ?, ?, ?)";

        try (Connection conn = GestorConexion.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, tipo);
            pstmt.setString(2, nombre);
            pstmt.setDouble(3, precio);
            pstmt.setString(4, descripcion);
            pstmt.setString(5, esDisponible);

            int fila = pstmt.executeUpdate();
            if (fila > 0) {
                System.out.println("El Producto insertado con exito!");
            }
        } catch (SQLException e) {
            System.out.println("Error : " + e.getMessage());
        }
    }
}
