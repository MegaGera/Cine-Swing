package control;

import control.Sesion;
import control.Sala;
import control.Cine;
import java.util.*;
import java.io.*;

/**
 *
 * Entrada del cine
 *
 */
public class Entrada {
    private String nombreCine;
    private int numSala;
    private int numSesion;
    private String pelicula;
    private GregorianCalendar horario;
    private int fila;
    private int numero;
    private static final String ENTRADA = "Entrada";
    private static final String ERROR_IMPRESION = "Error al imprimir su "
            + "entrada";
    private static final String ERROR_ENTRADA = "Error inesperado con su "
            + "entrada";
    private static final String CINE = "Cine: ";
    private static final String SALA = "\nSala: ";
    private static final String PELICULA = "\nPelícula: ";
    private static final String HORARIO = "\nFecha: ";
    private static final String FILA = "\nFila: ";
    private static final String NUMERO = " Número: ";

    /**
     * Construye una entrada
     *
     */
    public Entrada(Cine cine, int numSala, int numSesion, int fila,
                   int numero) {
        this.nombreCine = cine.devolverNombre();
        this.numSala = numSala;
        this.numSesion = numSesion;
        Sala sala = cine.devolverSala(numSala);
        Sesion sesion = sala.devolverSesion(numSesion);
        this.pelicula = sesion.devolverPelicula();
        this.horario = sesion.devolverHorario();
        this.fila = fila;
        this.numero = numero;

        if (cine.ocupar(this)) {
            Venta();
        }
    }

    /**
     *
     * Devuelve la sala de la entrada
     *
     */
    public int devolverSala() {
        return numSala;
    }

    /**
     *
     * Devuelve la sesión de la entrada
     *
     */
    public int devolverSesion() {
        return numSesion;
    }

    /**
     *
     * Devuelve la fila del asiento de la entrada
     *
     */
    public int devolverFila() {
        return fila;
    }

    /**
     *
     * Devuelve el número del asiento de la entrada
     *
     */
    public int devolverNumero() {
        return numero;
    }

    /**
     *
     * Imprime la entrada a un fichero
     *
     */
    public void Venta() {
        String impresion = CINE + nombreCine + SALA + numSala
                + PELICULA + pelicula + HORARIO
                + horario.get(horario.DAY_OF_MONTH)
                + "/" + (horario.get(horario.MONTH) + 1) + "/"
                + horario.get(horario.YEAR) + " " + horario.get(horario.HOUR_OF_DAY)
                + ":" + horario.get(horario.MINUTE)
                + FILA + fila + NUMERO + numero;
        FileWriter nombre = null;
        BufferedWriter fichero = null;
        try {

            nombre = new FileWriter(ENTRADA + "(" + numSala + "-" + numSesion
                    + "-" + fila + "-" + numero + ").txt");
            fichero = new BufferedWriter(nombre);
            fichero.write(impresion);

            fichero.close();

        } catch (IOException ex) {
            System.out.println(ERROR_IMPRESION);
            System.exit(1);
        } catch (Exception e) {
            System.out.println(ERROR_ENTRADA);
            System.exit(1);
        }
    }
}