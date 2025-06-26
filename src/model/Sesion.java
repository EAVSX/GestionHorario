package model;

/**
 *
 * @author eavsx
 */

public class Sesion {
    private int id;
    private Curso curso;
    private String fechaHora;

    public Sesion(int id, Curso curso, String fechaHora) {
        this.id = id;
        this.curso = curso;
        this.fechaHora = fechaHora;
    }

    public int getId() { return id; }
    public Curso getCurso() { return curso; }
    public String getFechaHora() { return fechaHora; }

    public void mostrar() {
        System.out.println("Sesion " + id + ": " +
            curso.getNombre() + " : " + fechaHora);
    }
}
