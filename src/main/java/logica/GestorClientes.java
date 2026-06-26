package logica;

import ConexionDB.GestorConexion;
import com.sun.jna.platform.win32.Sspi;
import model.Cliente;

import java.sql.*;
import java.util.Scanner;

/**
 * @author Oussama Rajil
 * @version 1.0.0
 */
public class GestorClientes {

    static final Scanner SC = new Scanner(System.in);

    /**
     * El menu del gestor del clinete lleva todos opciones necesarios
     */
    public static void menu() {

        int opcion;
        do {
            System.out.println("===MENÚ GESTOR CLIENTES===");
            System.out.println("1. Añadir Cliente");
            System.out.println("2. Modificar Cliente");
            System.out.println("3. Eliminar Cliente");
            System.out.println("4. Buscar Cliente");
            System.out.println("5. Listar Clientes");
            System.out.println("9. Eliminar Todos Clientes");
            System.out.println("0. Salir");
            System.out.println("");
            System.out.printf("Opcion: ");
            opcion = SC.nextInt();
            SC.nextLine();

            switch (opcion) {
                case 1:
                    añadirCliente();
                    break;
                case 2:
                    modificarCliente();
                    break;
                case 3:
                    eliminarCliente();
                    break;
                case 4:
                    buscarCliente();
                    break;
                case 5:
                    listarClientes();
                    break;
                case 9:
                    eliminarTodosClilentes();
                    break;

                case 0:
                    System.out.println("Saliendo del Gestor Del Los Clientes..");
                    break;
                default:
                    System.out.println("Opción no existe!");
                    break;
            }
        } while (opcion != 0);

    }

    /**
     * insertar nuevos clientes a la base de datos de la tienda.
     */
    public static void añadirCliente() {

        System.out.println("===AÑADIR CLIENTE===");
        System.out.println(" ");

        System.out.println("Introduce el Dni del cliente : ");
        String dni = SC.nextLine();
        System.out.println("Introduce el Nombre del cliente : ");
        String nombre = SC.nextLine();
        System.out.println("Introduce los Apellidos del cliente : ");
        String apellidos = SC.nextLine();
        System.out.println("Introduce el email de cliente : ");
        String email = SC.nextLine();
        System.out.println("Introduce el telefono de cliente : ");
        String telefono = SC.nextLine();
        System.out.println("Introduce la direccion del cliente : ");
        String direccion = SC.nextLine();

        String sql = "INSERT INTO cliente (dni, nombre, apellidos, email, telefono, direccion) values (?, ?, ?, ?, ?, ?)";

        try (Connection conn = GestorConexion.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            if(dni.length() > 9){
                System.err.println("Dni invalido, debes ser de 9 digitos");
            }else {
                pstmt.setString(1, dni);
            }

            pstmt.setString(2, nombre);
            pstmt.setString(3, apellidos);

            if (!email.contains("@gmail.com")){
                System.err.println("Email invalido, no conlleva el formato del gmail.");
            }else {
                pstmt.setString(4, email);
            }

            pstmt.setString(5, telefono);
            pstmt.setString(6, direccion);

            int fila = pstmt.executeUpdate();
            if (fila > 0) {
                System.out.println("Cliente insertado con exito!");
            }
        } catch (SQLException e) {
            System.out.println("Error : " + e.getMessage());
        }
    }

    /**
     * Modificar los datos del cliente a través la base de datos.
     */
    public static void modificarCliente() {

        System.out.println("===MODIFICAR CLIENTE===");
        System.out.println(" ");

        System.out.println("Introduce el codigo del cliente para modificarlo : ");
        int codigo = SC.nextInt();
        SC.nextLine();

        System.out.println("Que quieres modificar : ");
        System.out.println("1. Dni");
        System.out.println("2. Nombre");
        System.out.println("3. Apellidos");
        System.out.println("4. Email");
        System.out.println("5. Telefono");
        System.out.println("6. Direccion");

        int opcion = SC.nextInt();
        SC.nextLine();
        String modificacion = "";

        switch (opcion) {
            case 1:
                modificacion = "dni";
                break;
            case 2:
                modificacion = "nombre";
                break;
            case 3:
                modificacion = "apellidos";
                break;
            case 4:
                modificacion = "email";
                break;
            case 5:
                modificacion = "telefono";
                break;
            case 6:
                modificacion = "direccion";
                break;
            default:
                System.out.println("El opción invalido!");
                break;
        }

        System.out.println("Introduce nuevo valor para modifocalo : ");
        String valor = SC.nextLine();

        String sql = "UPDATE cliente set " + modificacion + " = ?  where codigo = ?";

        try (Connection conn = GestorConexion.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, valor);
            pstmt.setInt(2, codigo);

            int fila = pstmt.executeUpdate();
            if (fila > 0) {
                System.out.println("Cliente modifocado con exito!");
            }
        } catch (SQLException e) {
            System.out.println("Error : " + e.getMessage());
        }
    }

    /**
     * Eliminar el cliente asignado.
     */
    public static void eliminarCliente() {
        System.out.println("===ELIMINAR CLIENTE===");
        System.out.println("Introduce el codigo del cliente que quieres eliminar : ");
        int codigo = SC.nextInt();
        SC.nextLine();

        String sql = "DELETE FROM cliente where codigo = ?";
        try (Connection conn = GestorConexion.obtenerConexion();
             PreparedStatement ptmt = conn.prepareStatement(sql)) {

            ptmt.setInt(1, codigo);

            int fila = ptmt.executeUpdate();
            if (fila > 0) {
                System.out.println("Cliente eliminado con exito!");
            }else {
                System.out.println("Error al eliminar el cliente! el codigo no existe!");
            }
        } catch (SQLException e) {
            System.out.println("Error : " + e.getMessage());
        }
    }

    /**
     * Buscar cliente que queremos a traves el dni, porque si tendremos base de datos con millones de clientes no
     * podemos buscar por el codigo, por eso el dni es una mejor opcion.
     */
    public static void buscarCliente() {
        System.out.println("===BUSCAR CLIENTE===");
        System.out.println("Introduce el Dni del cliente : ");
        String dni = SC.nextLine();

        String sql = "SELECT * FROM cliente WHERE dni = ?";

        try (Connection conn = GestorConexion.obtenerConexion();
             PreparedStatement ptmt = conn.prepareStatement(sql)) {

            ptmt.setString(1, dni);

            ResultSet rs = ptmt.executeQuery();

            if (rs.next()) {
                    System.out.println("==Cliente Encontrado==");

                    int codigo = rs.getInt("codigo");
                    String idn = rs.getString("dni");
                    String nombre = rs.getString("nombre");
                    String apellidos = rs.getString("Apellidos");
                    String email = rs.getString("email");
                    String telefono = rs.getString("telefono");
                    String direccion = rs.getString("direccion");

                    System.out.println("Codigo : " + codigo + " | Dni : " + idn + " | Nombre : " +
                            nombre + " | Apellidos : " + apellidos + " | Email : " + email + " | Telefono : " +
                            telefono + " | Direccion : " + direccion);

            }else {
                System.out.println("El DNI  del cliente no existe!");
            }
        }catch (SQLException e){
            System.out.println("Error : " + e.getMessage());
        }
    }

    /**
     * Listar todos los clientes asignados en nuesta base de datos.
     */
    public static void listarClientes(){
        System.out.println("===LISTAR CLIENTES===");

        String sql = "SELECT * FROM cliente";
        try(Connection conn = GestorConexion.obtenerConexion();
            PreparedStatement ptmt = conn.prepareStatement(sql);
            ResultSet rs = ptmt.executeQuery()) {

            while (rs.next()){

                int codigo = rs.getInt("codigo");
                String idn = rs.getString("dni");
                String nombre = rs.getString("nombre");
                String apellidos = rs.getString("Apellidos");
                String email = rs.getString("email");
                String telefono = rs.getString("telefono");
                String direccion = rs.getString("direccion");

                System.out.println("Codigo : " + codigo + " | Dni : " + idn + " | Nombre : " +
                        nombre + " | Apellidos : " + apellidos + " | Email : " + email + " | Telefono : " +
                        telefono + " | Direccion : " + direccion);

            }
        }catch (SQLException e){
            System.out.println("Error : " + e.getMessage());
        }
    }

    /**
     * Eliminar todos los clientes, si queremos vaciar la base de datos.
     */
    public static void eliminarTodosClilentes(){

        System.out.println("===ELIMINAR TODOS CLIENTES===");

        String sql = "DELETE FROM cliente";
        try (Connection conn = GestorConexion.obtenerConexion();
             PreparedStatement ptmt = conn.prepareStatement(sql)) {

            ptmt.executeUpdate();

            System.out.println("Todos los clientes han sido eliminados correctamente.");
        } catch (SQLException e) {
            System.out.println("Error : " + e.getMessage());
        }
    }
}
