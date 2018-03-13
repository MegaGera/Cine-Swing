package control;

import modelo.Platea;
import vista.CineVista;
import java.util.*;
import java.io.*;

/**
 *
 * Sesión de una sala
 *
 */
public class Sesion {
    private int numSesion;
    private String pelicula;
    private GregorianCalendar horario;
    private Platea platea;
    private static final String FICHERO_NO_ENCONTRADO = "No se ha encontrado"
            + " el fichero: ";
    private static final String ERROR_LEER = "Error al leer el fichero: ";
    private static final String PELICULA = "\nPelicula: ";
    private static final String SESION = "\nSesion: ";
    Scanner ficheroSesion;

    /**
     * Construye una sesión
     *
     */
    public Sesion(int numSesion, String sesion, String platea) {
        this.numSesion = numSesion;
        this.platea = new Platea(platea);
        int hora, minuto, dia, mes, anio;

        try {
            ficheroSesion = new Scanner(new File(sesion));
            pelicula = ficheroSesion.nextLine();
            hora = ficheroSesion.nextInt();
            minuto = ficheroSesion.nextInt();
            dia = ficheroSesion.nextInt();
            mes = ficheroSesion.nextInt() - 1;
            anio = ficheroSesion.nextInt();

            horario = new GregorianCalendar(anio, mes, dia, hora, minuto);

        } catch (FileNotFoundException e1) {
            System.out.println(FICHERO_NO_ENCONTRADO + sesion);
            System.exit(1);
        } catch (Exception e) {
            System.out.println(ERROR_LEER + sesion);
            System.exit(1);
        }
        ficheroSesion.close();
    }

    /**
     * Devuelve el numero de la sesión
     *
     */
    public int devolverNumero() {
        return numSesion;
    }

    /**
     * Devuelve la película de la sesión
     *
     */
    public String devolverPelicula() {
        return pelicula;
    }

    /**
     * Devuelve el horario de la sesión
     *
     */
    public GregorianCalendar devolverHorario() {
        return horario;
    }

    /**
     *
     * Ocupa la butaca indicada
     *
     */
    public boolean ocupar(Entrada entrada) {
        int fila = entrada.devolverFila();
        int numero = entrada.devolverNumero();

        return platea.ocupar(fila, numero);
    }
    
    /**
    * Devuelve la platea
    *
    */
    public Platea devolverPlatea() {
        return platea;
    }
}