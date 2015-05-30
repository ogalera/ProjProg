package interficie;

import java.awt.Dimension;
import logica.log.Log;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import logica.enumeracions.EElement;
import logica.ValidadorLaberint;

/*****************************************************************/
/*                          COMPONENT EXTRA                      */
/*****************************************************************/

/**
 * @author oscar
 * @brief 
 *  Pantalla que ens permet crear i dissenyar nous mapes
 * 
 *  En la part esquerra hi ha el conjunt de elemnts que es poden afegir al laberint
 *  i nomes cal seleccionar l'element i fer click en la posicio on es vol afegir
 *  Un cop creada la pantalla aquesta es validada i exportada en format .txt.)
 * 
 *  @invariant
 *  laberint != null, el costat sempre ha de ser > Utils.Constants.MINIM_COSTAT_LABERINT i 
 *  la matriu ha de ser cuadrada de costat x costat
 */
public class FEditorLaberint extends JFrame{
    private JButton btnPacman; /**<botó per seleccionar en pacman*/
    private JButton btnFantasma1; /**<botó per seleccionar un fantasma1*/
    private JButton btnFantasma2; /**<botó per seleccionar un fantasma2*/
    private JButton btnFantasma3; /**<botó per seleccionar un fantasma3*/
    private JButton btnParet; /**<botó per seleccionar una paret*/
    private JButton btnMoneda; /**<botó per seleccionar una moneda*/
    private JButton btnItemSeleccionat; /**<botó que indica l'element seleccionat*/
    private JButton btnValidar; /**<botó per validar el laberint*/
    
    private final int [][] laberint; /**< matriu \b(costat x costat) que conté els identificadors
                                         dels elements que s'han afegit al nou mapa*/
    private final Log log;
    
    private EElement elementSeleccionat = EElement.RES; /**<Element seleccionat en cada instant
                                                            obiament al principi no en tenim cap*/
    private int costat; /**<mida del costat del laberint*/

    /**
     * @pre costat > Utils.Constants.MINIM_COSTAT_LABERINT;
     * @post em creat l'editor de laberints per un laberint de mida costat
     */
    public FEditorLaberint(int costat){
        log = Log.getInstance(FEditorLaberint.class);
        this.laberint = new int[costat][costat];
        
        JSplitPane sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        sp.add(crearMenu());
        
        sp.add(crearMatriu(costat));
        this.add(sp);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH); 
    }
    
    /**
     * @pre el frame no s'ha mostrat
     * @post s'ha mostrat el frame;
     */
    public void mostrarFrame(){
        this.setVisible(true);
    }
    
    /**
     * @pre costat > Utils.Constants.MINIM_COSTAT_LABERINT
     * @post retornem un panell que conté una matriu (de mida costat x costata) de botons i
     * el boto de validar en la part inferior.
     */
    private JPanel crearMatriu(int costat){
        this.costat = costat;
        JPanel contingut = new JPanel();
        contingut.setLayout(new BoxLayout(contingut, BoxLayout.Y_AXIS));
        JPanel panellLaberint = new JPanel();
        panellLaberint.setLayout(new GridLayout(costat, costat,4, 4));
        Dimension dimensio = panellLaberint.getSize();
        ///llargada i amplada per cada un dels botons que formen la matriu.
        int llargadaCasella = dimensio.height/costat;
        int ampladaCasella = dimensio.width/costat;
        for(int i = 0; i < costat; i++){
            for(int j = 0; j < costat; j++){
                btnCasella b = new btnCasella(i, j);
                b.setPreferredSize(new Dimension(ampladaCasella, llargadaCasella));
                b.setContentAreaFilled(false);
                panellLaberint.add(b);
                laberint[i][j] = EElement.RES.obtenirId();
            }
        }
        contingut.add(panellLaberint);
        btnValidar = new JButton("Validar");
        btnValidar.addActionListener(new ActionValidarLaberint());
        contingut.add(btnValidar);
        return contingut;
    }
    
    /**
     * @pre --
     * @post retornem un panell que conté els items que es poden posar en el tauler;
     */
    private JPanel crearMenu(){
        //contindrà els diferents elements que es poden afegir al laberint
        //  Pacman
        //  Pared
        //  Fantasma
        //  ...
        JPanel panell = new JPanel();
        panell.setSize(200, 0);
        panell.setLayout(new BoxLayout(panell, BoxLayout.Y_AXIS));
        
        panell.setMaximumSize(panell.getSize());
        
        ActionCanviarElementSeleccionat ace = new ActionCanviarElementSeleccionat();
        
        btnPacman = new JButton();
        btnPacman.setIcon(EElement.PACMAN.obtenirImatge());
        btnPacman.setContentAreaFilled(false);
        btnPacman.addActionListener(ace);
        
        btnFantasma1 = new JButton();
        btnFantasma1.setIcon(EElement.FANTASMA1.obtenirImatge());
        btnFantasma1.setContentAreaFilled(false);
        btnFantasma1.addActionListener(ace);
        
        btnFantasma2 = new JButton();
        btnFantasma2.setIcon(EElement.FANTASMA2.obtenirImatge());
        btnFantasma2.setContentAreaFilled(false);
        btnFantasma2.addActionListener(ace);
        
        btnFantasma3 = new JButton();
        btnFantasma3.setIcon(EElement.FANTASMA3.obtenirImatge());
        btnFantasma3.setContentAreaFilled(false);
        btnFantasma3.addActionListener(ace);
        
        btnParet = new JButton();
        btnParet.setIcon(EElement.PARET.obtenirImatge());
        btnParet.setContentAreaFilled(false);
        btnParet.addActionListener(ace);
        
        btnMoneda = new JButton();
        btnMoneda.setIcon(EElement.MONEDA.obtenirImatge());
        btnMoneda.setContentAreaFilled(false);
        btnMoneda.addActionListener(ace);
        
        btnItemSeleccionat = new JButton();
        btnItemSeleccionat.setIcon(EElement.INDEFINIT.obtenirImatge());
        btnItemSeleccionat.setContentAreaFilled(false);
        
        //Treiem els bordes dels botons
        btnPacman.setBorder(BorderFactory.createEmptyBorder());
        btnFantasma1.setBorder(BorderFactory.createEmptyBorder());
        btnParet.setBorder(BorderFactory.createEmptyBorder());
        btnMoneda.setBorder(BorderFactory.createEmptyBorder());
        btnItemSeleccionat.setBorder(BorderFactory.createEmptyBorder());
        
        panell.add(btnPacman);
        panell.add(new JLabel("Pacman"));
        panell.add(btnFantasma1);
        panell.add(new JLabel("Fantasma 1"));
        panell.add(btnFantasma2);
        panell.add(new JLabel("Fantasma 2"));
        panell.add(btnFantasma3);
        panell.add(new JLabel("Fantasma 3"));
        panell.add(btnMoneda);
        panell.add(new JLabel("Paret"));
        panell.add(btnParet);
        panell.add(new JLabel("Moneda"));
        panell.add(btnItemSeleccionat);
        panell.add(new JLabel("I.S."));
        
        return panell;
    }
    
    /**
     * @author Oscar.Galera
     * @brief
     * controla els events de clik sobre els botons
     *      -btnPacman
     *      -btnFantasma
     *      -btnParet
     *      -btnMoneda
     */
    private class ActionCanviarElementSeleccionat implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            Object origen = e.getSource();
            //Comparem a traves de la referencia al emisor del event
            //per classificar quin boto ha sigut el que s'ha clicat;
            if(origen == btnPacman){
                //S'ha clicat el boto del pacman;
                btnItemSeleccionat.setIcon(EElement.PACMAN.obtenirImatge());
                elementSeleccionat = EElement.PACMAN;
            }
            else if (origen == btnFantasma1){
                btnItemSeleccionat.setIcon(EElement.FANTASMA1.obtenirImatge());
                elementSeleccionat = EElement.FANTASMA1;
            }
            else if (origen == btnFantasma2){
                btnItemSeleccionat.setIcon(EElement.FANTASMA2.obtenirImatge());
                elementSeleccionat = EElement.FANTASMA2;
            }
            else if (origen == btnFantasma3){
                btnItemSeleccionat.setIcon(EElement.FANTASMA3.obtenirImatge());
                elementSeleccionat = EElement.FANTASMA3;
            }
            else if (origen == btnParet){
                btnItemSeleccionat.setIcon(EElement.PARET.obtenirImatge());
                elementSeleccionat = EElement.PARET;
            }
            else if (origen == btnMoneda){
                btnItemSeleccionat.setIcon(EElement.MONEDA.obtenirImatge());
                elementSeleccionat = EElement.MONEDA;
            }
        }
    }
    
    /**
     * @author Oscar.Galera
     * @brief
     * controla els events sobre el boto de validar el laberint
     */
    private class ActionValidarLaberint implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if(ValidadorLaberint.validarLaberint(laberint, costat)){
                String carpetaDesti = seleccionarCarpeta();
                if(carpetaDesti != null){
                    this.exportarLaberint(carpetaDesti);
                }
            }
            else{
                JOptionPane.showMessageDialog(FEditorLaberint.this,
                                                log.obtenirUltimError(), 
                                                "ERROR",JOptionPane.ERROR_MESSAGE);
            }
        }
        
        /**
        * @pre --;
        * @post em mostrat un dialeg per seleccionar un directori, si s'ha seleccionat
        * de forma correct es retorna la seva ruta altrament es retorna null.
        */
        private String seleccionarCarpeta(){
            String carpetaDesti = null;
            JFileChooser fc = new JFileChooser();
            fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            if(fc.showDialog(FEditorLaberint.this, "Exportar Log") == JFileChooser.APPROVE_OPTION){
                carpetaDesti = fc.getSelectedFile().getAbsolutePath();
            }
            return carpetaDesti;
        }
        
        /**
         * @pre desti és la ruta a un directori accessible per escriure.
         * @post em exportat el el laberint en format text (.txt) en la carpeta destí,
         * el nom del fitxer és laberint_dia_mes_any_hora_minuts.txt.
         */
        private void exportarLaberint(String desti){
            SimpleDateFormat sdf = new SimpleDateFormat("_dd_MM_yyyy_kk_mm");
            String marcaTemps = sdf.format(new Date());
            String fitxerDesti = desti+"/laberint"+marcaTemps+".txt";
            boolean operacio = this.exportarFitxer(fitxerDesti);
            if(operacio){
                JOptionPane.showMessageDialog(FEditorLaberint.this,
                                            "S'ha exportat el mapa correctament a "+fitxerDesti,
                                            "Mapa exportar correctament!",JOptionPane.INFORMATION_MESSAGE);
                dispose();
            }
            else{
                JOptionPane.showMessageDialog(FEditorLaberint.this,
                        "Hi ha hagut un error al exportar el mapa\nverifiqui el log",
                        "Error al exportar el mapa",JOptionPane.ERROR_MESSAGE);
            }
        }
        
        /**
         * @pre fitxer és el nom d'un fitxer de destí valid;
         * @post em exportat el laberint en format text sobre el fitxer;
         */
        private boolean exportarFitxer(String fitxer){
            boolean operacio = true;
            try(BufferedWriter bw = new BufferedWriter(new FileWriter(fitxer))){
                for(int i = 0; i < costat; i++){
                    StringBuilder stringBuilder = new StringBuilder();
                    for(int j = 0; j < costat; j++){
                        stringBuilder.append(laberint[i][j]);
                        stringBuilder.append(" ");
                    }
                    bw.write(stringBuilder.toString().trim());
                    if(i+1 < costat ) bw.newLine();
                }
            }
            catch(IOException ioe){
                log.afegirError(ioe.getMessage());
                operacio = false;
            }
            return operacio;
        }
    }
    
    /**
     * @author Oscar.Galera
     * @brief
     * Defineix un botó amb dos coordenades, quan es realitzi click sobre el 
     * botó aquest obtindrà l'imatge que està seleccionada i també assignara el 
     * id del element seleccionat a la casella del laberint.
     */
    private class btnCasella extends JButton implements ActionListener{
        private final int columna;
        private final int fila;
        
        /**
         * @pre: x i y són >= 0 i <= costat
         * @post: em creeat un boto amb unes coordenades epecifiques;
         */
        public btnCasella(int columna, int fila){
            this.columna = columna;
            this.fila = fila;
            this.addActionListener(this);
        }
        
        /**
         * @pre:--;
         * @post: em tractat l'event sobre el botó, aquest event consisteix
         * en assignar l'imatge que toca al boto i assignar el valor al 
         * tauler del laberint.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            this.setIcon(elementSeleccionat.obtenirImatge());
            laberint[columna][fila] = elementSeleccionat.obtenirId();
        }
    }
}
