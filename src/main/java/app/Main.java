package app;

import ConexionDB.GestorConexion;
import logica.GestorClientes;
import logica.GestorProductos;
import model.Cliente;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        menu();
    }

    public static void menu(){
        Scanner sc = new Scanner(System.in);
        int opcion;
        do {
            System.out.println("===MENÚ PRINCIPAL===");
            System.out.println("1. GESTOR DE LOS CLIENTES");
            System.out.println("2. GESTOR DE LOS PRODUCTOS");
            System.out.println("3. GESTOR DE LAS VENTAS");
            System.out.println("0. Salir");

            System.out.println("");

            System.out.println("Elige Opción : ");
            opcion =  sc.nextInt();
            sc.nextLine();

            switch(opcion){
                case 1:
                    GestorClientes.menu();
                    break;
                case 2:
                    GestorProductos.menu();
                    break;
                case 0:
                    System.out.println("Saliendo del Programa...");
                    break;
                default:
                    System.out.println("El opción no existe!");
                    break;
            }
        }while (opcion != 0);
    }
    public static boolean probarCon(){
        return GestorConexion.probarConexion();
    }
}