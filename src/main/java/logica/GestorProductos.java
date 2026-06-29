package logica;

import ConexionDB.GestorConexion;
import model.Producto;
import org.mariadb.jdbc.client.socket.impl.SocketUtility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
                    modificarProducto();
                    break;
                case 3:
                    eliminarProducto();
                    break;
                case 4:
                    buscarProducto();
                    break;
                case 5:
                    listarProductos();
                    break;
                case 9:
                    eliminarTodosProductos();
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
        System.out.println("6. Herramienta");
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

    public static void modificarProducto() {
        System.out.println("===MODIFICAR PRODUCTO===");
        System.out.println(" ");

        System.out.println("Introduce el codigo del producto para modificarlo : ");
        int codigo = SC.nextInt();
        SC.nextLine();

        System.out.println("Que quieres modificar : ");
        System.out.println("1. Tipo");
        System.out.println("2. Nombre");
        System.out.println("3. precio");
        System.out.println("4. descripcion");
        System.out.println("5. esDisponible");


        int opcion = SC.nextInt();
        SC.nextLine();
        String modificacion = "";


        switch (opcion) {
            case 1:
                modificacion = "tipo";
                break;
            case 2:
                modificacion = "nombre";
                break;
            case 3:
                modificacion = "precio";
                break;
            case 4:
                modificacion = "descripcion";
                break;
            case 5:
                modificacion = "esDisponible";
                break;
            default:
                System.out.println("El opción invalido!");
                break;
        }

        System.out.println("Introduce nuevo valor para modifocalo : ");

        String sql = "UPDATE producto set " + modificacion + " = ?  where codigo = ?";

        try (Connection conn = GestorConexion.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            if (modificacion.equals("precio")) {
                double valorP =  SC.nextDouble();
                pstmt.setDouble(1, valorP);
                SC.nextLine();
            }else {
                String valor = SC.nextLine();
                pstmt.setString(1, valor);
            }
            pstmt.setInt(2, codigo);

            int fila = pstmt.executeUpdate();
            if (fila > 0) {
                System.out.println("Producto modifocado con exito!");
            }
        } catch (SQLException e) {
            System.out.println("Error : " + e.getMessage());
        }
    }

    public static void eliminarProducto() {
        System.out.println("===ELIMINAR Producto===");
        System.out.println("Introduce el codigo del prodcuto que quieres eliminar : ");
        int codigo = SC.nextInt();
        SC.nextLine();

        String sql = "DELETE FROM producto where codigo = ?";
        try (Connection conn = GestorConexion.obtenerConexion();
             PreparedStatement ptmt = conn.prepareStatement(sql)) {

            ptmt.setInt(1, codigo);

            int fila = ptmt.executeUpdate();
            if (fila > 0) {
                System.out.println("Producto eliminado con exito!");
            }else {
                System.out.println("Error al eliminar el Producto! el codigo no existe!");
            }
        } catch (SQLException e) {
            System.out.println("Error : " + e.getMessage());
        }
    }

    public static void buscarProducto() {
        System.out.println("===BUSCAR PRODUCTO===");
        System.out.println("Introduce el Nombre del producto : ");
        String nombre = SC.nextLine();

        String sql = "SELECT * FROM producto WHERE nombre = ?";

        try (Connection conn = GestorConexion.obtenerConexion();
             PreparedStatement ptmt = conn.prepareStatement(sql)) {

            ptmt.setString(1, nombre);

            ResultSet rs = ptmt.executeQuery();

            if (rs.next()) {
                System.out.println("==Productos Encontrados==");

                int codigo = rs.getInt("codigo");
                String tipo =  rs.getString("tipo");
                String nombreP = rs.getString("nombre");
                double precio = rs.getDouble("precio");
                String descripcion = rs.getString("descripcion");
                String esDisponible = rs.getString("esDisponible");

                System.out.println("Codigo : " + codigo + " | Tipo : " + tipo + " | Nombre : " + nombreP + " | Precio : " + precio + " | Descrepcion : " + descripcion +
                        " | Disponible ? : " + esDisponible);
            }else {
                System.out.println("El Nombre del producto no existe!");
            }
        }catch (SQLException e){
            System.out.println("Error : " + e.getMessage());
        }
    }

    public static void listarProductos(){
        System.out.println("===LISTAR PRODUCTOS===");

        String sql = "SELECT * FROM producto";

        try (Connection conn = GestorConexion.obtenerConexion();
             PreparedStatement ptmt = conn.prepareStatement(sql)) {

            ResultSet rs = ptmt.executeQuery();
            System.out.println("==Productos Encontrados==");
            if (rs.isBeforeFirst()) {
                while (rs.next()) {

                    int codigo = rs.getInt("codigo");
                    String tipo =  rs.getString("tipo");
                    String nombreP = rs.getString("nombre");
                    double precio = rs.getDouble("precio");
                    String descripcion = rs.getString("descripcion");
                    String esDisponible = rs.getString("esDisponible");

                    System.out.println( "Codigo : " + codigo + " | Tipo : " + tipo + " | Nombre : " + nombreP + " | Precio : " + precio + " | Descrepcion : " + descripcion +
                            " | Disponible ? : " + esDisponible);
                }
            }else {
                System.out.println("No hay ningún producto en este momento!");
            }

        }catch (SQLException e){
            System.out.println("Error : " + e.getMessage());
        }
    }

    public static void eliminarTodosProductos() {

        System.out.println("===ELIMINAR TODOS PRODUCTOS===");

        String sql = "DELETE FROM producto";
        try (Connection conn = GestorConexion.obtenerConexion();
             PreparedStatement ptmt = conn.prepareStatement(sql)) {

            ptmt.executeUpdate();

            System.out.println("Todos los productos han sido eliminados correctamente.");
        } catch (SQLException e) {
            System.out.println("Error : " + e.getMessage());
        }
    }

    public static Producto buscarProductoPorCodigo(int codigo) {

        String sql = "SELECT * FROM producto WHERE codigo = ?";
        Producto producto = null;

        try (Connection conn = GestorConexion.obtenerConexion();
             PreparedStatement ptmt = conn.prepareStatement(sql)) {

            ptmt.setInt(1, codigo);

            ResultSet rs = ptmt.executeQuery();
            if (rs.next()) {
                producto = new Producto(
                        rs.getInt("codigo"),
                        rs.getString("tipo"),
                        rs.getString("nombre"),
                        rs.getDouble("precio"),
                        rs.getString("descripcion"),
                        rs.getString("esDisponible")
                );
            }

        } catch (SQLException e) {
            System.out.println("Error : " + e.getMessage());
        }

        return producto;
    }
}
