package model;

import java.util.List;

/**
 *
 * @author eavsx
 */

/** Bridge: abstracción de persistencia de cursos, sesiones y usuarios */
public interface Storage {
    // Cursos y sesiones
    int nextCursoId();
    int nextSesionId();
    void guardarCurso(Curso c);
    Curso obtenerCurso(int id);
    List<Curso> obtenerCursos();
    void guardarSesion(Sesion s);
    List<Sesion> obtenerSesiones();
    // Usuarios
    void guardarUsuario(Usuario u);
    Usuario obtenerUsuario(String nombre);
    List<Usuario> obtenerUsuarios();
}
