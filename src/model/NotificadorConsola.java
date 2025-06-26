package model;

/**
 *
 * @author eavsx
 */

public class NotificadorConsola implements Observador {
    @Override
    public void notificar(String mensaje) {
        System.out.println("=== NOTIFICACION ===");
        System.out.println(mensaje);
        System.out.println("====================");
    }
}
