package model;

/**
 *
 * @author eavsx
 */

public class Usuario {
    private String nombre;
    private String password;
    private Role role;

    public Usuario(String nombre, String password, Role role) {
        this.nombre = nombre;
        this.password = password;
        this.role = role;
    }

    public String getNombre() { return nombre; }
    public String getPassword() { return password; }
    public Role getRole()     { return role;     }
    public void setRole(Role role) { this.role = role; }
}
