package vista;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

import modelo.Butaca;

/**
 * Vista de una casilla de una butaca
 *
 */
public class ButacaVista extends JLabel { 
    private Butaca butaca;

    /**
     * Construye una vista de la butaca
     *
     */
    ButacaVista(Butaca butaca) {
        this.butaca = butaca;
        setIcon(null);

        setHorizontalAlignment(SwingConstants.CENTER);
        setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        //setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        //setBorder(null);
       
    }

    /**
     * Devuelve la butaca de la casilla
     *
     */
    public Butaca devuelveButaca() {
        return butaca;
    }
}