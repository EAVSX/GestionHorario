package model;

/**
 *
 * @author eavsx
 */

import java.util.List;


public class ColeccionSesiones {
    private List<Sesion> sesiones;

    public ColeccionSesiones(List<Sesion> sesiones) {
        this.sesiones = sesiones;
    }

    public SesionIterador crearIterador() {
        return new SesionIteradorImpl();
    }

    private class SesionIteradorImpl implements SesionIterador {
        private int indice = 0;

        @Override
        public boolean hasNext() {
            return indice < sesiones.size();
        }

        @Override
        public Sesion next() {
            return sesiones.get(indice++);
        }
    }
}
