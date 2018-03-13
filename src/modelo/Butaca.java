package modelo;

/**
 *
 * Butaca del cine
 *
 */
public class Butaca {
    private int fila;
    private int numero;
    private boolean ocupada = false;
    private boolean reservada = false;

    /**
     * Construye una butaca
     *
     */
    public Butaca(int fila, int numero) {
        this.fila = fila;
        this.numero = numero;
    }

    /**
     * Devuelve si la butaca está ocupada o no
     *
     */
    public boolean ocupada() {
        return ocupada;
    }
    /**
     * Devuelve si la butaca está reservada o no
     *
     */
    public boolean reservada() {
        return reservada;
    }
    /**
     * Devuelve si esa posición es pasillo
     *
     */
    public boolean pasillo() {
        if ((fila == 0) && (numero == 0)) {
            return true;
        }
        return false;
    }

    /**
     * Ocupa una butaca
     *
     */
    public void ocupar() {
        ocupada = true;
    }
    
    /**
     * Reserva una butaca
     *
     */
    public void reservar() {
        reservada = true;
    }

    /**esreserva una butaca
     *
     */
    public void desreservar() {
        reservada = false;
    }
    

    /**
     * Devuelve la fila donde está la butaca
     *
     */
    public int devolverFila() {
        return fila;
    }

    /**
     * Devuelve la posición donde está la butaca
     *
     */
    public int devolverNumero() {
        return numero;
    }
}