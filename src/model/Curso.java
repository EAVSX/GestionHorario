package model;

/**
 *
 * @author eavsx
 */

public class Curso implements CursoPrototype {
    private int id;
    private String nombre;
    private int duracionHoras;

    public Curso(int id, String nombre, int duracionHoras) {
        this.id = id;
        this.nombre = nombre;
        this.duracionHoras = duracionHoras;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public int getDuracionHoras() { return duracionHoras; }

    public void setId(int id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    @Override
    public CursoPrototype clone() {
        try {
            return (Curso) super.clone();
        } catch (CloneNotSupportedException e) {
            return new Curso(this.id, this.nombre, this.duracionHoras);
        }
    }

    @Override
    public String toString() {
        return "[" + id + "] " + nombre + " (" + duracionHoras + "h)";
    }
}
