package modelo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * Platea de una sala
 *
 */
public class Platea extends Observable {

    private Butaca[][] butacas;
    int filas = 0;
    int columnas = 0;
    private static final String FICHERO_NO_ENCONTRADO = "No se ha encontrado "
            + "el fichero: ";
    private static final String ERROR_LEER = "Error al leer el fichero: ";
    private static final String FILA = "Fila: ";
    private static final String POSICION = "Posicion: ";
    private static final String BUTACAS_TOTALES = "\nButacas totales: ";
    private static final String BUTACAS_LIBRES = "\nButacas libres: ";
    Scanner ficheroPlatea;
    List<Butaca> reservas = new ArrayList<Butaca>();

    /**
     *
     * Construye una platea
     *
     */
    public Platea(String platea) {
        int fila = 0;
        int columna = 0;
        int numero = 0;

        try {
            ficheroPlatea = new Scanner(new File(platea));
            filas = ficheroPlatea.nextInt();
            columnas = ficheroPlatea.nextInt();
            butacas = new Butaca[filas][columnas];

            while (ficheroPlatea.hasNextInt()) {
                numero = ficheroPlatea.nextInt();
                butacas[fila][columna] = new Butaca(numero / 100, numero % 100);
                columna++;
                if (columna == columnas) {
                    columna = 0;
                    fila++;
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println(FICHERO_NO_ENCONTRADO + platea);
            System.exit(1);
        } catch (Exception e) {
            System.out.println(ERROR_LEER + platea);
            System.exit(1);
        }
        ficheroPlatea.close();
    }
    
    /**
     *
     * Ocupa la butaca indicada
     *
     */
    public boolean ocupar(int fila, int numero) {
        int f = 0;
        int n = 0;

        while (butacas[f][n].devolverFila() != fila) {
            while (butacas[f][n].pasillo() && n < columnas - 1) {
                n++;
            }
            if (butacas[f][n].devolverFila() != fila) {
                f++;
                n = 0;
            }
        }
        while (butacas[f][n].devolverNumero() != numero) {
            n++;
        }
        if (!butacas[f][n].ocupada()) {
            butacas[f][n].ocupar();
            setChanged();
            notifyObservers(butacas[f][n]);
            return true;
        }
        return false;
    }

    /**
     *
     * Reserva la butaca indicada
     *
     */
    public void reservar(Butaca butaca) {
        int f = 0;
        int n = 0;

        while (butacas[f][n].devolverFila() != butaca.devolverFila()) {
            while (butacas[f][n].pasillo() && n < devolverColumnas() - 1) {
                n++;
            }
            if (butacas[f][n].devolverFila() != butaca.devolverFila()) {
                f++;
                n = 0;
            }
        }
        while (butacas[f][n].devolverNumero() != butaca.devolverNumero()) {
            n++;
        }
        if (!butacas[f][n].reservada()) {
            butacas[f][n].reservar();
        } else {
            butacas[f][n].desreservar();
        }
        setChanged();
        notifyObservers(butacas[f][n]);
    }

    /**
     *
     * Devuelve las filas de la platea
     *
     */
    public int devolverFilas() {
        return filas;
    }

    /**
     *
     * Devuelve las columnas de la platea
     *
     */
    public int devolverColumnas() {
        return columnas;
    }

    /**
     *
     * Devuelve una butaca
     *
     */
    public Butaca devolverButaca(int fila, int numero) {
        int f = 0;
        int n = 0;

        while (butacas[fila][n].devolverFila() != fila) {
            while (butacas[f][n].pasillo() && n < columnas - 1) {

                System.out.println(f + "     " + n);
                n++;
            }
            if (butacas[f][n].devolverFila() != fila) {
                f++;
                n = 0;
            }
        }
        while (butacas[f][n].devolverNumero() != numero) {
            n++;
        }
        return butacas[f][n];
    }

    /**
     * Devuelve la matriz de las butacas
     *
     */
    public Butaca[][] devolverMatriz() {
        return butacas;
    }

    /**
     * Quita las reservas de los asientos de toda una platea
     *
     */
    public void desreservarPlatea() {
        int f = 0;
        int n = 0;
        while (f != filas) {
            while (n != columnas) {
                    butacas[f][n].desreservar();
                
                n++;
            }
            f++;
            n = 0;
        }
    }

    /**
     * Devuelve una lista con las butacas reservadas
     *
     */
    public List devolverReservas() {
        int fila = 0;
        int columna = 0;
        while (fila != filas) {
            while (columna != columnas) {
                if (butacas[fila][columna].reservada()) {
                    reservas.add(butacas[fila][columna]);
                }
                columna++;
            }
            fila++;
            columna = 0;
        }
        return reservas;
    }
    
    /**
     * Devuelve las butacas totales de la platea
     *
     */
    public int butacasTotales(){
        int f = 0;
        int n = 0;
        int butacasTotales = 0;
        while (f != filas) {
            while (n != columnas) {
                if (!butacas[f][n].pasillo()){
                    butacasTotales++;
                }
                n++;
            }
            f++;
            n = 0;
        }
        return butacasTotales;
    }
    
    /**
     * Devuelve las butacas totales de la platea
     *
     */
    public int butacasLibres(){
        int f = 0;
        int n = 0;
        int butacasLibres = 0;
        while (f != filas) {
            while (n != columnas) {
                if (!butacas[f][n].pasillo() && !butacas[f][n].ocupada()){
                    butacasLibres++;
                }
                n++;
            }
            f++;
            n = 0;
        }
        return butacasLibres;
    }
    
    /**
     * Vacia las reservas de la platea
     *
     */
    public void vaciarReservas(){
        reservas.clear();
        desreservarPlatea();
    }
}
