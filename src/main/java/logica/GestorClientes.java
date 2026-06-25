package logica;

import ConexionDB.GestorConexion;
import com.sun.jna.platform.win32.Sspi;
import model.Cliente;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * @author Oussama Rajil
 * @version 1.0.0
 */
public class GestorClientes {
 static final Scanner SC = new Scanner(System.in);

    public static void menu(){

        int opcion;
        do {
            System.out.println("===MENÚ GESTOR CLIENTES===");
            System.out.println("1. Añadir Cliente");
            System.out.println("2. Modificar Cliente");
            System.out.println("3. Eliminar Cliente");
            System.out.println("4. Buscar Cliente");
            System.out.println("5. Listar Clientes");
            System.out.println("0. Salir");
            opcion = SC.nextInt();
            SC.nextLine();

            switch(opcion){
                case 1:
                    añadirCliente();
                    break;
                case 2:
                    modificarCliente();
                    break;

                case 0:
                    System.out.println("Saliendo del Gestor Del Los Clientes..");
                    break;
                default:
                    System.out.println("Opción no existe!");
                    break;
            }
        }while (opcion != 0);

    }
    public static void añadirCliente(){

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

        try(Connection conn = GestorConexion.obtenerConexion();
            PreparedStatement pstmt = conn.prepareStatement(sql)){


            pstmt.setString(1, dni);
            pstmt.setString(2, nombre);
            pstmt.setString(3, apellidos);
            pstmt.setString(4, email);
            pstmt.setString(5, telefono);
            pstmt.setString(6, direccion);

            int fila = pstmt.executeUpdate();
            if (fila > 0){
                System.out.println("Cliente insertado con exito!");
            }
        }catch (SQLException e){
            System.out.println("Error : " + e.getMessage());
        }
    }

    public static void modificarCliente(){

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

        int opcion =  SC.nextInt();
        SC.nextLine();
        String modificacion = "";

        switch (opcion){
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
                modificacion= "email";
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
        String valor =  SC.nextLine();

        String sql = "UPDATE cliente set " + modificacion + " = ?  where codigo = ?" ;

        try(Connection conn = GestorConexion.obtenerConexion();
            PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setString(1, valor);
            pstmt.setInt(2, codigo);

            int fila = pstmt.executeUpdate();
            if (fila > 0){
                System.out.println("Cliente modifocado con exito!");
            }
        }catch (SQLException e){
            System.out.println("Error : " + e.getMessage());
        }
    }
}
