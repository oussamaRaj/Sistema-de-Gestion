package app;

import ConexionDB.GestorConexion;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

    }

    public static boolean probarCon(){
        return GestorConexion.probarConexion();
    }
}