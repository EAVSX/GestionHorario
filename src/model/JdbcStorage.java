package model;

import java.io.*;
import java.util.*;

/**
 *
 * @author eavsx
 */

public class JdbcStorage implements Storage {
    private List<Curso> cursos       = new ArrayList<Curso>();
    private List<Sesion> sesiones    = new ArrayList<Sesion>();
    private Map<String,Usuario> usuarios = new HashMap<String,Usuario>();
    private int seqCurso  = 1;
    private int seqSesion = 1;

    private static final String ARCHIVO_USUARIOS  = "usuarios.txt";
    private static final String ARCHIVO_CURSOS    = "cursos.txt";
    private static final String ARCHIVO_SESIONES  = "sesiones.txt";

    public JdbcStorage() {
        cargarUsuarios();
        // Asegura usuario admin por defecto
        if (!usuarios.containsKey("admin")) {
            Usuario admin = new Usuario("admin","admin",Role.Administrador);
            usuarios.put(admin.getNombre(), admin);
        }
        cargarCursos();
        cargarSesiones();
        // Guarda por si creó admin
        guardarUsuariosEnDisco();
        guardarCursosEnDisco();
        guardarSesionesEnDisco();
    }

    // --- Usuarios ---

    private void cargarUsuarios() {
        File f = new File(ARCHIVO_USUARIOS);
        if (!f.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] p = linea.split(",",3);
                if (p.length == 3) {
                    String n = p[0], pw = p[1];
                   Role r = null;
                    String raw = p[2].trim();
                    for (Role cand : Role.values()) {
                        if (cand.name().equalsIgnoreCase(raw)) {
                            r = cand;
                            break;
                        }
                    }
                    if (r == null) {
                        System.err.println("Rol desconocido en archivo: " + raw + " – asignando Estudiante.");
                        r = Role.Estudiante;
                    }
                usuarios.put(n, new Usuario(n, pw, r));
                    usuarios.put(n, new Usuario(n, pw, r));
                }
            }
        } catch (IOException e) {
            System.err.println("Error cargando usuarios: " + e.getMessage());
        }
    }

    private void guardarUsuariosEnDisco() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO_USUARIOS, false))) {
            for (Usuario u : usuarios.values()) {
                bw.write(u.getNombre() + "," + u.getPassword() + "," + u.getRole().name());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error guardando usuarios: " + e.getMessage());
        }
    }

    @Override
    public void guardarUsuario(Usuario u) {
        usuarios.put(u.getNombre(), u);
        guardarUsuariosEnDisco();
    }

    @Override
    public Usuario obtenerUsuario(String nombre) {
        return usuarios.get(nombre);
    }

    @Override
    public List<Usuario> obtenerUsuarios() {
        return new ArrayList<Usuario>(usuarios.values());
    }

    // --- Cursos ---

    private void cargarCursos() {
        File f = new File(ARCHIVO_CURSOS);
        if (!f.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String linea;
            int maxId = 0;
            while ((linea = br.readLine()) != null) {
                String[] p = linea.split(",",3);
                if (p.length == 3) {
                    int id = Integer.parseInt(p[0]);
                    String nombre = p[1];
                    int dur = Integer.parseInt(p[2]);
                    cursos.add(new Curso(id, nombre, dur));
                    if (id > maxId) maxId = id;
                }
            }
            seqCurso = maxId + 1;
        } catch (IOException e) {
            System.err.println("Error cargando cursos: " + e.getMessage());
        }
    }

    private void guardarCursosEnDisco() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO_CURSOS, false))) {
            for (Curso c : cursos) {
                bw.write(c.getId() + "," + c.getNombre() + "," + c.getDuracionHoras());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error guardando cursos: " + e.getMessage());
        }
    }

    @Override
    public void guardarCurso(Curso c) {
        cursos.add(c);
        guardarCursosEnDisco();
    }

    @Override
    public Curso obtenerCurso(int id) {
        for (Curso c : cursos) {
            if (c.getId() == id) return c;
        }
        return null;
    }

    @Override
    public List<Curso> obtenerCursos() {
        return cursos;
    }

    // --- Sesiones ---

    private void cargarSesiones() {
        File f = new File(ARCHIVO_SESIONES);
        if (!f.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String linea;
            int maxId = 0;
            while ((linea = br.readLine()) != null) {
                String[] p = linea.split(",",3);
                if (p.length == 3) {
                    int id = Integer.parseInt(p[0]);
                    int cursoId = Integer.parseInt(p[1]);
                    String fechaHora = p[2];
                    Curso curso = obtenerCurso(cursoId);
                    if (curso != null) {
                        sesiones.add(new Sesion(id, curso, fechaHora));
                        if (id > maxId) maxId = id;
                    }
                }
            }
            seqSesion = maxId + 1;
        } catch (IOException e) {
            System.err.println("Error cargando sesiones: " + e.getMessage());
        }
    }

    private void guardarSesionesEnDisco() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO_SESIONES, false))) {
            for (Sesion s : sesiones) {
                bw.write(s.getId() + "," + s.getCurso().getId() + "," + s.getFechaHora());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error guardando sesiones: " + e.getMessage());
        }
    }

    @Override
    public void guardarSesion(Sesion s) {
        sesiones.add(s);
        guardarSesionesEnDisco();
    }

    @Override
    public List<Sesion> obtenerSesiones() {
        return sesiones;
    }

    // --- Secuencias de IDs ---

    @Override
    public int nextCursoId() {
        return seqCurso++;
    }

    @Override
    public int nextSesionId() {
        return seqSesion++;
    }
}
