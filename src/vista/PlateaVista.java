package vista;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.Icon;
import javax.swing.JPanel;

import control.OyenteVista;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import modelo.Platea;
import modelo.Butaca;

/**
 * Vista Swing de la platea
 *
 */
public class PlateaVista extends JPanel {

    private static  int ALTURA_FILA = 30;
    private static  int ANCHURA_COLUMNA = 30;
    private Platea platea;
    private ButacaVista butacasVista[][];
    private Butaca butacas[][];
    private List<Butaca> reservas = new ArrayList<Butaca>();
    private CineVista vista;
    
    /**
     * Construye el panel del la platea
     *
     */
    PlateaVista(CineVista vista) {
        this.vista = vista;
        this.setPreferredSize(new Dimension(20 * ALTURA_FILA,
                20 * ANCHURA_COLUMNA));
    }

    /**
     * Muestra una platea
     *
     */
    public void mostrarPlatea(Platea platea) {
        this.platea = platea;
        int filas = platea.devolverFilas();
        int columnas = platea.devolverColumnas();

        setLayout(new GridLayout(filas, columnas));
        butacasVista = new ButacaVista[filas][columnas];
        butacas = platea.devolverMatriz();

        for (int fil = 0; fil < filas; fil++) {
            for (int col = 0; col < columnas; col++) {
                butacasVista[fil][col] = new ButacaVista(butacas[fil][col]);
                Butaca butacaSelecc = butacas[fil][col];
                
                add(butacasVista[fil][col]);
                
                vista.ponerIconoButaca(butacas[fil][col]);
                
                if (!butacas[fil][col].pasillo()){
                butacasVista[fil][col].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        vista.notificacion(OyenteVista.Evento.RESERVAR, butacaSelecc);
                    }
                });
                }
            }
        }

        this.setPreferredSize(new Dimension(columnas * ALTURA_FILA,
                filas * ANCHURA_COLUMNA));
    }

    /**
     * Devuelve el tamaño del tablero
     *
     */
    public Dimension dimensionCasilla() {
        return butacasVista[0][0].getSize();
    }
    
    /**
    * Pone un icono a una butaca
    *
    */
    public void ponerIconoButaca(Butaca butaca, Icon icono) {     
        
        int f = 0;
        int n = 0;

        while (butacas[f][n].devolverFila() != butaca.devolverFila()) {
            while (butacas[f][n].pasillo() && n < platea.devolverColumnas() - 1) {
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
        
    butacasVista[f][n].setIcon(icono);
    }
}