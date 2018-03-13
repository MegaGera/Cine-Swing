package control;

import java.util.*;
import modelo.Platea;

/**
 *
 * Sala de un cine
 *
 */
public class Sala {
    private int numero;
    private List<Sesion> sesiones = new ArrayList<Sesion>();
    private static final String SALA = "\nSala: ";

    /**
     * Construye una sala
     *
     */
    public Sala(int numero) {
        this.numero = numero;
    }

    /**
     * Devuelve el numero de una sala
     *
     */
    public int devolverNumero() {
        return numero;
    }

    /**
     * Introduce una sesión
     *
     */
    public boolean nuevaSesion(Sesion sesion) {
        return sesiones.add(sesion);
    }

    /**
     * Devuelve la sesión indicada
     *
     */
    public Sesion devolverSesion(int numSesion) {
        for(Sesion sesion : sesiones){
            if (sesion.devolverNumero() == numSesion) {
                return sesion;
            }
        }
        return null;
    }

    /**
     * Ocupa la butaca indicada
     *
     */
    public boolean ocupar(Entrada entrada) {
        for(Sesion sesion : sesiones){
            if (sesion.devolverNumero() == entrada.devolverSesion()) {
                return sesion.ocupar(entrada);
            }
        }
        return false;
    }
    
    /**
    * Devuelve la platea de una sesión
    *
    */
    public Platea devolverPlatea(int numSesion){ 
        return devolverSesion(numSesion).devolverPlatea();            
    }
    
    /**
    * Devuelve el tamaño de las sesiones de una sala
    *
    */
    public int devolverSesiones(){
        return sesiones.size();
    }
    
    /**
    * Devuelve la lista de las sesiones de una sala
    *
    */
    public List devolverListaSesiones(){
        return sesiones;
    }
}