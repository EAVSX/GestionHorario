package model;

import java.util.List;
import java.util.ArrayList;
import view.ConsolaVista;

/**
 *
 * @author eavsx
 */

public class Planificador {
    private Storage storage;
    private List<Observador> observadores;

    public Planificador() {
        storage      = new JdbcStorage();
        observadores = new ArrayList<Observador>();
    }

    public void subscribir(Observador o) {
        observadores.add(o);
    }

    public Observador getNotificador() {
        return new NotificadorConsola();
    }

    public Usuario login(ConsolaVista vista) {
        while (true) {
            String nombre = vista.leerTexto("Nombre de usuario");
            String pass   = vista.leerTexto("Password");
            Usuario u = storage.obtenerUsuario(nombre);
            if (u != null && u.getPassword().equals(pass)) {
                vista.mostrarMensaje("Bienvenido, " + u.getNombre() + "!");
                return u;
            } else {
                vista.mostrarMensaje("Credenciales invalidas. Intente de nuevo.");
            }
        }
    }

    public void crearCurso(ConsolaVista vista) {
        int id = storage.nextCursoId();
        String nombre = vista.leerTexto("Nombre del curso");
        String durTxt = vista.leerTexto("Duracion (horas)");
        int dur;
        try {
            dur = Integer.parseInt(durTxt);
        } catch (NumberFormatException e) {
            vista.mostrarMensaje("Duracion invalida.");
            return;
        }
        Curso c = new Curso(id, nombre, dur);
        storage.guardarCurso(c);
        vista.mostrarMensaje("Curso creado: " + c);
    }

    public void clonarCurso(ConsolaVista vista, Usuario usuario) {
        if (usuario.getRole() != Role.Administrador) {
            vista.mostrarMensaje("Solo ADMINISTRADOR puede clonar cursos.");
            return;
        }
        String idTxt = vista.leerTexto("ID del curso a clonar");
        int id;
        try {
            id = Integer.parseInt(idTxt);
        } catch (NumberFormatException e) {
            vista.mostrarMensaje("ID invalido.");
            return;
        }
        Curso orig = storage.obtenerCurso(id);
        if (orig == null) {
            vista.mostrarMensaje("Curso no encontrado.");
            return;
        }
        String seccion = vista.leerTexto("Ingrese seccion (ej: Seccion 2)");
        Curso copia = (Curso) orig.clone();
        copia.setId(storage.nextCursoId());
        copia.setNombre(orig.getNombre() + " - " + seccion);
        storage.guardarCurso(copia);
        vista.mostrarMensaje("Curso clonado: " + copia);
    }

    /** Nuevo: listar todos los cursos */
    public void listarCursos(ConsolaVista vista) {
        List<Curso> cursos = storage.obtenerCursos();
        if (cursos.isEmpty()) {
            vista.mostrarMensaje("No hay cursos disponibles.");
            return;
        }
        for (Curso c : cursos) {
            vista.mostrarMensaje(c.toString());
        }
    }

    public void agendarSesion(ConsolaVista vista) {
        String idTxt = vista.leerTexto("ID del curso");
        int idCurso;
        try {
            idCurso = Integer.parseInt(idTxt);
        } catch (NumberFormatException e) {
            vista.mostrarMensaje("ID invalido.");
            return;
        }
        Curso curso = storage.obtenerCurso(idCurso);
        if (curso == null) {
            vista.mostrarMensaje("Curso no existe.");
            return;
        }
        String fechaHora = vista.leerTexto("Fecha y hora (YYYY-MM-DD HH:mm)");
        List<Sesion> existentes = storage.obtenerSesiones();
        for (Sesion sExist : existentes) {
            if (sExist.getFechaHora().equals(fechaHora)) {
                vista.mostrarMensaje(
                    "No se puede agendar: conflicto con la sesion " +
                    sExist.getId() + " (" +
                    sExist.getCurso().getNombre() + " @ " + fechaHora + ")"
                );
                return;
            }
        }
        int idSes = storage.nextSesionId();
        Sesion nueva = new Sesion(idSes, curso, fechaHora);
        storage.guardarSesion(nueva);
        vista.mostrarMensaje(
            "Sesion agendada: ID " + nueva.getId() +
            " -> " + curso.getNombre() + " @ " + fechaHora
        );
    }

    public void listarSesiones(ConsolaVista vista) {
        List<Sesion> list = storage.obtenerSesiones();
        ColeccionSesiones cs = new ColeccionSesiones(list);
        SesionIterador it = cs.crearIterador();
        while (it.hasNext()) {
            it.next().mostrar();
        }
    }

    public void listarSesionesProfesor(Usuario usuario, ConsolaVista vista) {
        listarSesiones(vista);
    }

    public void gestionarUsuarios(ConsolaVista vista, Usuario usuario) {
        if (usuario.getRole() != Role.Administrador) {
            vista.mostrarMensaje("Solo ADMINISTRADOR puede gestionar usuarios.");
            return;
        }
        String nombre = vista.leerTexto("Nombre de nuevo usuario");
        String pass   = vista.leerTexto("Password");
        String rolTxt = vista.leerTexto("Rol (Administrador, Profesor, Estudiante)");
        Role role = null;
        for (Role cand : Role.values()) {
            if (cand.name().equalsIgnoreCase(rolTxt.trim())) {
                role = cand;
                break;
            }
        }
        if (role == null) {
            vista.mostrarMensaje("Rol invalido, asignando Estudiante.");
            role = Role.Estudiante;
        }
        Usuario nuevo = new Usuario(nombre, pass, role);
        storage.guardarUsuario(nuevo);
        vista.mostrarMensaje("Usuario creado: " + nombre + " -> " + role);
    }
}
