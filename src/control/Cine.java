/**
 * Cine.java
 * 03/2017
 *
 * @author Gonzalo Berné && Unai Esteban
 *
 * Práctica 2 - TP - Cine con swing
 *
 */
package control;

import modelo.Platea;
import control.Sala;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import vista.CineVista;
import modelo.Butaca;

/**
 *
 * Clase cine
 *
 */
public class Cine implements OyenteVista {

    private String nombre;
    private List<Sala> salas = new ArrayList<Sala>();
    private CineVista vista;
    private static Cine instancia = null;
    private static final String CINE = "Cine: ";
    public int salasNum = 0;
    private String ficheroSesion;

    /**
     *
     * Construye un cine usando el patrón Singleton
     *
     */
    private Cine(String nombre) {
        this.nombre = nombre;
    }

    public static synchronized Cine instancia(String nombre) {
        if (instancia == null) {
            instancia = new Cine(nombre);
        }
        return instancia;
    }

    /**
     *
     * Llama a la instancia de la vista para crear el cine
     *
     */
    public void iniciarVista() {
        vista = CineVista.instancia(this);
        crearListaSalas();

    }

    /**
     *
     * Devuelve el nombre del cine
     *
     */
    public String devolverNombre() {
        return nombre;
    }

    /**
     *
     * Introduce una nueva sala
     *
     */
    public boolean nuevaSala(Sala sala) {
        return salas.add(sala);
    }

    /**
     *
     * Introduce una nueva sesión
     *
     */
    private boolean nuevaSesion(int numSala, Sesion sesion) {
        for (Sala sala : salas) {
            if (sala.devolverNumero() == numSala) {
                return sala.nuevaSesion(sesion);
            }
        }
        return false;
    }

    /**
     *
     * Devuelve la sala indicada
     *
     */
    public Sala devolverSala(int numSala) {
        for (Sala sala : salas) {
            if (sala.devolverNumero() == numSala) {
                return sala;
            }
        }
        return null;
    }

    /**
     * Devuelve la platea indicada
     *
     */
    private Platea devolverPlatea(int numSala, int numSesion) {
        return devolverSala(numSala).devolverPlatea(numSesion);
    }

    /**
     * Devuelve el nnumero de salas que tiene el cine
     *
     */
    private int devolverNumSalas() {
        return salas.size();
    }

    private void crearListaSalas() {
        for (int i = 1; i <= devolverNumSalas(); i++) {
            vista.crearListaSalas(devolverSala(i), i);
        }
    }

    /**
     * Devuelve la vista
     *
     */
    public CineVista devolverVista() {
        return vista;
    }

    /**
     * Ocupa la butaca indicada
     *
     */
    public boolean ocupar(Entrada entrada) {
        for (Sala sala : salas) {
            if (sala.devolverNumero() == entrada.devolverSala()) {
                return sala.ocupar(entrada);
            }
        }
        return false;
    }

    /**
     * Salir del programa
     *
     */
    public void salir() {
        System.exit(0);
    }

    /**
     * Crea una nueva sesión mediante la acción del usuario
     *
     */
    public void nueva() {
        int numTotalSesiones = devolverSala(vista.devolverSala()
                .devolverNumero()).devolverSesiones();
        File fichero = vista.seleccionarFichero();
        if (fichero != null) {
            ficheroSesion = fichero.getName();
            Sesion sesion = new Sesion(numTotalSesiones + 1, ficheroSesion,
                    "platea" + vista.devolverSala().devolverNumero() + ".txt");
            nuevaSesion(vista.devolverSala().devolverNumero(), sesion);
            vista.ponerSesion(sesion);
        }
    }

    /**
     *
     * Vender entrada
     *
     */
    public void vender() {
        Platea platea = this.devolverPlatea(vista.devolverSala()
                .devolverNumero(), vista.devolverSesion().devolverNumero());
        List<Butaca> reservas = platea.devolverReservas();

        for (Butaca butaca : reservas) {
            new Entrada(this, vista.devolverSala().devolverNumero(),
                vista.devolverSesion().devolverNumero(), butaca.devolverFila(),
                butaca.devolverNumero());
        }
        platea.vaciarReservas();
    }

    /**
     *
     * Reserva una butaca
     *
     */
    public void reservar(Butaca butaca) {
        devolverPlatea(vista.devolverSala().devolverNumero(),
                vista.devolverSesion().devolverNumero()).reservar(butaca);
    }

    /**
     *
     * Establece la sesión en la vista
     *
     */
    public void ponerSesion(int numSesion) {
        vista.ponerSesion(devolverSala(vista.devolverSala().devolverNumero())
                .devolverSesion(numSesion));
    }

    /**
     * Añade una sesión a la lista en la vista
     *
     */
    public void mostrarSesiones(Sala sala) {
        List<Sesion> sesiones = sala.devolverListaSesiones();
        for (Sesion sesion : sesiones) {
            String pelicula = sesion.devolverNumero() + " - "
                    + sesion.devolverPelicula();
            vista.añadirSesiones(pelicula);
        }
    }
    
    /**
     * Muestra la platea y su información en la vista
     *
     */
    public void mostrarPlatea(Sesion sesion) {
        Platea platea = sesion.devolverPlatea();
        GregorianCalendar horario = sesion.devolverHorario();
        String informacion = ("   Sala : " + vista.devolverSala()
                              .devolverNumero() + "     "
                              + sesion.devolverPelicula() + "   "
                              + horario.get(horario.DAY_OF_MONTH)
                              + "/" + (horario.get(horario.MONTH) + 1) + "/"
                              + horario.get(horario.YEAR) + " - "
                              + horario.get(horario.HOUR_OF_DAY)
                              + ":" + horario.get(horario.MINUTE));
        vista.mostrarPlatea(platea, informacion);
    }

    /**
     * Llamado desde vista para notificar evento de la interfaz de usuario
     *
     */
    @Override
    public void notificacion(OyenteVista.Evento evento, Object obj) {
        switch (evento) {

            case SALIR:
                salir();
                break;
            case NUEVA:
                nueva();
                break;
            case NUMERO_SESION:
                ponerSesion((int) obj);
                break;
            case MOSTRAR_SESIONES:
                mostrarSesiones((Sala) obj);
                break;
            case MOSTRAR_PLATEA:
                mostrarPlatea((Sesion) obj);
                break;
            case VENDER:
                vender();
                break;
            case RESERVAR:
                reservar((Butaca) obj);
                break;
        }
    }

    public static void main(String[] args) {
        Cine cine = Cine.instancia("Teruel");

        cine.nuevaSala(new Sala(1));
        cine.nuevaSala(new Sala(2));
        cine.nuevaSala(new Sala(3));
        cine.nuevaSala(new Sala(4));
        cine.nuevaSala(new Sala(5));

        cine.iniciarVista();

        CineVista vista = cine.devolverVista();
    }
}
