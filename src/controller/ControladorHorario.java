package controller;

import model.Planificador;
import model.Usuario;
import model.Role;
import view.ConsolaVista;

/**
 *
 * @author eavsx
 */

public class ControladorHorario {
    private Planificador planificador;
    private ConsolaVista vista;

    public ControladorHorario() {
        planificador = new Planificador();
        vista        = new ConsolaVista();
        planificador.subscribir(planificador.getNotificador());
    }

    public void iniciar() {
        while (true) {
            vista.clear();
            Usuario usuario = planificador.login(vista);

            String opcion;
            do {
                vista.mostrarMenu(usuario.getNombre(), usuario.getRole());
                opcion = vista.leerOpcion();

                switch (usuario.getRole()) {
                    case Administrador:
                        manejarAdmin(opcion, usuario);
                        break;
                    case Profesor:
                        manejarProfesor(opcion, usuario);
                        break;
                    case Estudiante:
                        manejarEstudiante(opcion, usuario);
                        break;
                }

                if (!opcion.equals("0")) {
                    vista.pause();
                    vista.clear();
                }
            } while (!opcion.equals("0"));

            vista.mostrarMensaje("Sesion cerrada. Volviendo al login...");
            try { Thread.sleep(1000); } catch (InterruptedException e) {}
        }
    }

    private void manejarAdmin(String opcion, Usuario u) {
        switch (opcion) {
            case "1":
                planificador.crearCurso(vista);
                break;
            case "2":
                planificador.clonarCurso(vista, u);
                break;
            case "3":
                planificador.listarCursos(vista);
                break;
            case "4":
                planificador.agendarSesion(vista);
                break;
            case "5":
                planificador.listarSesiones(vista);
                break;
            case "6":
                planificador.gestionarUsuarios(vista, u);
                break;
            case "0":
                break;
            default:
                vista.mostrarMensaje("Opcion invalida.");
                break;
        }
    }

    private void manejarProfesor(String opcion, Usuario u) {
        switch (opcion) {
            case "1":
                planificador.listarCursos(vista);
                break;
            case "2":
                planificador.agendarSesion(vista);
                break;
            case "3":
                planificador.listarSesionesProfesor(u, vista);
                break;
            case "0":
                break;
            default:
                vista.mostrarMensaje("Opcion invalida.");
                break;
        }
    }

    private void manejarEstudiante(String opcion, Usuario u) {
        switch (opcion) {
            case "1":
                planificador.listarCursos(vista);
                break;
            case "2":
                planificador.listarSesiones(vista);
                break;
            case "0":   
                break;
            default:
                vista.mostrarMensaje("Opcion invalida.");
                break;
        }
    }
}
