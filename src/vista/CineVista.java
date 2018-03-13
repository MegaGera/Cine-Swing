package vista;

import control.OyenteVista;
import control.Sala;
import control.Sesion;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import modelo.Platea;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import modelo.Butaca;

/**
 *
 * Clase vista general
 * 
 */
public class CineVista extends JFrame implements ActionListener, Observer {
    private OyenteVista oyenteVista;
    private PlateaVista plateaVista;
    private ImageIcon icono;
    private static CineVista instancia = null;
    private JMenuItem menuFicheroNuevaSesion
                      = new JMenuItem(MENU_ITEM_NUEVA, ATAJO_MENU_ITEM_NUEVA);
    private DefaultListModel lista_sesiones;
    private JList jlista;
    private JButton botonMostrar;
    private JLabel etiqueta_sesiones;
    private JLabel sesion_info = new JLabel();
    private JPanel panelNorte;
    private JMenu menuSalas = new JMenu(MENU_SALAS);
   
    private Sala salaMostrada;
    private Sesion sesionMostrada;
    private Platea platea;
    private String informacionBase;
    
    public static final int OPCION_SI = JOptionPane.YES_OPTION;
    
    private static final String TITULO = "CINE TERUEL";
    private static final String AUTOR = "Gonzalo Berné && Unai Esteban";
    private static final String VERSION = "2.0";
    
    private static final String MENU_FICHERO = "Fichero";
    private static final String MENU_ITEM_NUEVA = "Añadir Sesión";
    private static final char ATAJO_MENU_ITEM_NUEVA = 'A';
    private static final String MENU_ITEM_SALIR = "Salir";
    private static final char ATAJO_MENU_ITEM_SALIR = 'S';
    private static final String MENU_AYUDA = "Ayuda";
    private static final String MENU_SALAS = "Salas";
    private static final String MENU_ITEM_SALAS = "Sala ";
    private static final String MENU_SESIONES = "Sesion: ";
    private static final String MENU_ITEM_ACERCA_DE = "Acerca de...";
    private static final char ATAJO_ITEM_ACERCA_DE = 'A';
    private static final String VENDER = "~~VENDER~~";
    private static final String TOTALES = "Butacas Totales: ";
    private static final String LIBRES = "Butacas Libres: ";
    public static final String CONFIRMACION_SALIR = 
        "¿Quieres realizar las ventas antes de salir?";
    
    private static final int ANCHURA_LISTA = 160;
    private static final int ALTURA_LISTA = 200;
    
    public static final String EXT_FICHERO_SESION = ".txt";
    public static final String FILTRO_SESIONES = "Sesiones";

    private static String RUTA_RECURSOS = "/vista/recursos/";
    private static String BOTON_MOSTRAR = "Mostrar";
    private static String ICONO_RES = "naranja.jpg";
    private static String ICONO_OCU = "rojo.jpg";
    private static String ICONO_DES = "verde.jpg";
    private static String ICONO = "negro.png";

    

    public CineVista(OyenteVista oyenteVista) {
        super(TITULO);
        icono = new ImageIcon(
            this.getClass().getResource(RUTA_RECURSOS + ICONO));
        setIconImage(icono.getImage());
        this.oyenteVista = oyenteVista;

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                oyenteVista.notificacion(OyenteVista.Evento.SALIR, null);
            }
        });

        setLayout(new BorderLayout());
        panelNorte = new JPanel();
        panelNorte.setLayout(new GridLayout(2, 1));

        setVisible(true);
        creaMenus(panelNorte);
        add(panelNorte, BorderLayout.NORTH);
        
        
        JPanel panelSur= new JPanel();
        panelSur.setLayout(new FlowLayout());
        add(panelSur, BorderLayout.SOUTH);
        
        
        JButton vender = new JButton(VENDER);
        vender.setToolTipText(VENDER);
        vender.addActionListener(this);
        vender.setActionCommand(VENDER);
        panelSur.add(vender, BorderLayout.SOUTH);

        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new FlowLayout());
        add(panelCentral, BorderLayout.CENTER);
        creaPlatea(panelCentral);
        creaListaSesiones(panelCentral);
        
        pack();
        setLocationRelativeTo(null);  // centra en la pantalla

    }

    /**
     * Crea la vista mediante el patrón Singleton
     *
     */
    public static synchronized CineVista instancia(OyenteVista OyenteIU) {
        if (instancia == null) {
            instancia = new CineVista(OyenteIU);
        }
        return instancia;
    }

    /**
     * Crea los menus
     *
     */
    private void creaMenus(JPanel panelNorte) {
        JMenu menuFichero = new JMenu(MENU_FICHERO);

        menuFicheroNuevaSesion.addActionListener(this);
        menuFicheroNuevaSesion.setActionCommand(MENU_ITEM_NUEVA);
        menuFichero.add(menuFicheroNuevaSesion);
        menuFicheroNuevaSesion.setEnabled(false);

        menuFichero.addSeparator();

        JMenuItem menuFicheroSalir
                = new JMenuItem(MENU_ITEM_SALIR, ATAJO_MENU_ITEM_SALIR);
        menuFicheroSalir.addActionListener(this);
        menuFicheroSalir.setActionCommand(MENU_ITEM_SALIR);
        menuFichero.add(menuFicheroSalir);

        JMenu menuAyuda = new JMenu(MENU_AYUDA);

        JMenuItem menuAyudaAcercaDe
                = new JMenuItem(MENU_ITEM_ACERCA_DE, ATAJO_ITEM_ACERCA_DE);
        menuAyudaAcercaDe.addActionListener(this);
        menuAyudaAcercaDe.setActionCommand(MENU_ITEM_ACERCA_DE);
        menuAyuda.add(menuAyudaAcercaDe);

        JMenuBar menuPrincipal = new JMenuBar();
        menuPrincipal.add(menuFichero);
        menuPrincipal.add(menuSalas);
        menuPrincipal.add(menuAyuda);


        panelNorte.add(menuPrincipal, BorderLayout.NORTH);

    }

    /**
     * Crea la lista de salas
     *
     */
    public void crearListaSalas(Sala sala, int i){
        JMenuItem salaBoton = new JMenuItem(MENU_ITEM_SALAS + i);
            menuSalas.add(salaBoton);
            salaBoton.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    salaMostrada = sala;
                    cambiarSala();
                }
            });
    }
    /**
     * Crea la vista de las plateas
     *
     */
    private void creaPlatea(JPanel panelCentral) {
        plateaVista = new PlateaVista(this);
        
        panelCentral.add(plateaVista, BorderLayout.CENTER);
        panelCentral.add(new JPanel());

    }
    
    /**
     * Establece la sesión mostrada
     *
     */
    public void ponerSesion(Sesion sesion){
        sesionMostrada = sesion;
        mostrarSesiones();
    }
    
    /**
     * Muestra una platea
     *
     */
    public void mostrarPlatea(Platea platea, String informacion){
        limpiarPlatea();
        mostrarSesiones();
        platea.desreservarPlatea();
        this.platea = platea;
        platea.addObserver(this);
        plateaVista.mostrarPlatea(platea);
        this.informacionBase = informacion;
        informacion = informacion + "   Butacas Totales: " 
                      + platea.butacasTotales() + "   Butacas Libres: " 
                      + platea.butacasLibres();
        sesion_info.setText(informacion);
        panelNorte.add(sesion_info, BorderLayout.SOUTH);
    }
    
    /**
     * Crea la lista de sesiones
     *
     */
    private void creaListaSesiones(JPanel panelCentral) {
        JPanel panelListaSesiones = new JPanel();
        panelListaSesiones.setLayout(new BorderLayout());
        etiqueta_sesiones = new JLabel(MENU_SESIONES);
        panelListaSesiones.add(etiqueta_sesiones, BorderLayout.NORTH);

        lista_sesiones = new DefaultListModel();
        jlista = new JList(lista_sesiones);
        jlista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jlista.setLayoutOrientation(JList.VERTICAL);

        jlista.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent evt) {
                botonMostrar.setEnabled(true);
            }
        });

        JScrollPane panelListaSesionesScroller = new JScrollPane(jlista);
        panelListaSesiones.setPreferredSize(new Dimension(ANCHURA_LISTA,
                ALTURA_LISTA));
        panelListaSesiones.add(panelListaSesionesScroller, BorderLayout.CENTER);

        botonMostrar = new JButton(BOTON_MOSTRAR);
        botonMostrar.setEnabled(false);

        botonMostrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if (!jlista.isSelectionEmpty()) {
                    etiqueta_sesiones.setText(MENU_SESIONES
                            + jlista.getSelectedValue().toString().substring(0, 1));
                            notificacion(OyenteVista.Evento.NUMERO_SESION,
                                    Integer.parseInt(jlista.getSelectedValue()
                                    .toString().substring(0, 1)));
                            notificacion(OyenteVista.Evento.MOSTRAR_PLATEA,
                                    sesionMostrada);
                }
            }
        });
        panelListaSesiones.add(botonMostrar, BorderLayout.SOUTH);

        panelCentral.add(panelListaSesiones, BorderLayout.EAST);
    }
    
    
    /**
     * Cambia a la sala seleccionada
     *
     */
    public void cambiarSala() {      
        limpiarPlatea();
        mostrarSesiones();
    }
    
    /**
     * Devuelve la sala mostrada
     *
     */
    public Sala devolverSala() {      
        return salaMostrada;
    }
    
    /**
     * Devuelve la sesion mostrada
     *
     */
    public Sesion devolverSesion() {
        return sesionMostrada;
    }
   
    /**
     * Pone el icono de una butaca
     *
     */
    public void ponerIconoButaca(Butaca butaca) {
        Icon icono = null;
        if (!butaca.pasillo()){
        if (!butaca.ocupada() && butaca.reservada()) {
            icono = new ImageIcon(this.getClass().getResource(RUTA_RECURSOS 
                                                              + ICONO_RES));
        }
        if (!butaca.ocupada() && !butaca.reservada()) {
            icono = new ImageIcon(this.getClass().getResource(RUTA_RECURSOS 
                                                              + ICONO_DES));
        }
        if (butaca.ocupada()){
            icono = new ImageIcon(this.getClass().getResource(RUTA_RECURSOS 
                                                              + ICONO_OCU));
        }
        plateaVista.ponerIconoButaca(butaca, icono);
        }
    }
    
    /**
     * Muestra las sesiones de una sala en al lista
     *
     */
    public void mostrarSesiones() {
        menuFicheroNuevaSesion.setEnabled(true);
        lista_sesiones.clear();
        etiqueta_sesiones.setText(MENU_SESIONES);
        
        notificacion(OyenteVista.Evento.MOSTRAR_SESIONES, salaMostrada);
    }
    
    /**
     * Muestra las sesiones de una sala en al lista
     *
     */
    public void añadirSesiones(String pelicula) {    
        lista_sesiones.addElement(pelicula);
        informacionBase = "   Sala: " + salaMostrada.devolverNumero();
        sesion_info.setText(informacionBase);
        panelNorte.add(sesion_info, BorderLayout.SOUTH);
    }
    
        

    /**
     * Limpia la platea para mostrar una nueva
     *
     */
    public void limpiarPlatea() {
        plateaVista.removeAll();
        plateaVista.repaint();
        sesion_info.setText("");
    }

    /**
     * Abre un dialogo para seleccionar un fichero
     *
     */
    public File seleccionarFichero() {
        JFileChooser dialogoSeleccionar = new JFileChooser(new File("."));
        
        FileNameExtensionFilter filtro
                = new FileNameExtensionFilter(FILTRO_SESIONES,
                        EXT_FICHERO_SESION.substring(1));
        
        dialogoSeleccionar.setFileFilter(filtro);
        dialogoSeleccionar.showOpenDialog(this);
        return dialogoSeleccionar.getSelectedFile();
    }
    
    /**
     * Sobreescribe actionPerformed
     *
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {

            case MENU_ITEM_SALIR:
                oyenteVista.notificacion(OyenteVista.Evento.SALIR, null);
                break;
            case MENU_ITEM_NUEVA:
                oyenteVista.notificacion(OyenteVista.Evento.NUEVA, null);
                break;
            case VENDER:
                oyenteVista.notificacion(OyenteVista.Evento.VENDER, null);
                break;
            case MENU_ITEM_ACERCA_DE:
                JOptionPane.showMessageDialog(this,
                TITULO + "\n" + AUTOR + "\n" + VERSION, 
                MENU_ITEM_ACERCA_DE, JOptionPane.INFORMATION_MESSAGE,  icono);   
                break;
        }
    }
    
    /**
     * Sobreescribe update para recibir eventos de la platea observado
     *
     */
    public void update(Observable obj, Object arg) {
        Butaca butaca;

        if (arg instanceof Butaca) {
            butaca = (Butaca) arg;
            ponerIconoButaca(butaca);
            if (butaca.ocupada()){
            mostrarPlatea(platea, informacionBase);
            }
        }
    }

    /**
     * Notificación de un evento de la interfaz de usuario
     *
     */
    public void notificacion(OyenteVista.Evento evento, Object obj) {
        oyenteVista.notificacion(evento, obj);
    }
}