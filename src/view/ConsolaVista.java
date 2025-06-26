package view;

import java.util.Scanner;
import model.Role;

/**
 *
 * @author eavsx
 */

public class ConsolaVista {
    private Scanner sc = new Scanner(System.in);

    /** Limpia pantalla imprimiendo lineas vacias */
    public void clear() {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }

    public void pause() {
        System.out.print("\nPulse ENTER para continuar...");
        sc.nextLine();
    }

    public void mostrarMenu(String nombre, Role rol) {
        System.out.println("\n=== Planificador de Horarios ===");
        System.out.println("Usuario: " + nombre + " (" + rol + ")");
        switch (rol) {
            case Administrador:
                System.out.println("1) Crear plantilla de curso");
                System.out.println("2) Clonar plantilla de curso");
                System.out.println("3) Listar cursos");
                System.out.println("4) Agendar sesion");
                System.out.println("5) Listar sesiones");
                System.out.println("6) Gestionar usuarios");
                System.out.println("0) Cerrar sesion");
                break;
            case Profesor:
                System.out.println("1) Listar cursos");
                System.out.println("2) Agendar sesion");
                System.out.println("3) Listar sesiones");
                System.out.println("0) Cerrar sesion");
                break;
            case Estudiante:
                System.out.println("1) Listar cursos");
                System.out.println("2) Listar sesiones");
                System.out.println("0) Cerrar sesion");
                break;
        }
        System.out.print(">> ");
    }

    public String leerOpcion() {
        return sc.nextLine();
    }

    public String leerTexto(String prompt) {
        System.out.print(prompt + ": ");
        return sc.nextLine();
    }

    public void mostrarMensaje(String msg) {
        System.out.println(msg);
    }

    public void cerrar() {
        sc.close();
    }
}
