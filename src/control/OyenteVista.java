
package control;

import java.util.List;
import modelo.Platea;

/**
 *  Interfaz de oyente para recibir eventos de la interfaz de usuario
 * 
 */
public interface OyenteVista {

   public enum Evento { VENDER, MOSTRAR_SESIONES, MOSTRAR_PLATEA, NUMERO_SESION,
                        RESERVAR, NUEVA, SALIR }
  
   /**
    *  Llamado para notificar un evento de la interfaz de usuario
    * 
    */ 
   public void notificacion(Evento evento, Object obj);
}