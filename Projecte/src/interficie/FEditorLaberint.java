/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javax.swing.WindowConstants;
import logica.enumeracions.EElement;
import logica.ValidadorLaberint;

/**
 *
 * @author oscar
 */
public class FEditorLaberint extends JFrame{
    private JButton btnPacman, btnFantasma, btnParet, btnMoneda, btnItemSeleccionat;
    private JButton btnValidar;
    private final int [][] laberint;
    private final Log log;
    private EElement elementSeleccionat = EElement.RES;
    private int costat;
    
    public FEditorLaberint(int costat){
        log = Log.getInstance(FEditorLaberint.class);
        
        this.laberint = new int[costat][costat];
        
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        
        /*JSplitPane spaFrame = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        spaFrame.add(this.crearMenu());
        JButton sortir = new JButton("Sortir");
        sortir.addActionListener(this);
        spaFrame.add(sortir);*/
        
        JSplitPane sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        sp.add(this.crearMenu());
        
        sp.add(crearContingut(costat));
        this.add(sp);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH); 
    }
    
    public void mostrarFrame(){
        this.setVisible(true);
    }
    
    private JPanel crearContingut(int costat){
        this.costat = costat;
        JPanel contingut = new JPanel();
        contingut.setLayout(new BoxLayout(contingut, BoxLayout.Y_AXIS));
        JPanel panellLaberint = new JPanel();
        panellLaberint.setLayout(new GridLayout(costat, costat,4, 4));
        Dimension dimensio = panellLaberint.getSize();
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
    
    private JPanel crearMenu(){
        //Split panel que contindrà els diferents elements que es poden afegir al laberint
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
        
        btnFantasma = new JButton();
        btnFantasma.setIcon(EElement.FANTASMA1.obtenirImatge());
        btnFantasma.setContentAreaFilled(false);
        btnFantasma.addActionListener(ace);
        
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
        btnFantasma.setBorder(BorderFactory.createEmptyBorder());
        btnParet.setBorder(BorderFactory.createEmptyBorder());
        btnMoneda.setBorder(BorderFactory.createEmptyBorder());
        btnItemSeleccionat.setBorder(BorderFactory.createEmptyBorder());
        
        panell.add(btnPacman);
        panell.add(new JLabel("Pacman"));
        panell.add(btnFantasma);
        panell.add(new JLabel("Fantasma 1"));
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
     * DECLARACIÓ D'INTENCIONS DE LA CLASSE
     * Aquesta classe ens controla els events de clik sobre els botons
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
            else if (origen == btnFantasma){
                btnItemSeleccionat.setIcon(EElement.FANTASMA1.obtenirImatge());
                elementSeleccionat = EElement.FANTASMA1;
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
    
    private class ActionValidarLaberint implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if(ValidadorLaberint.validarLaberint(laberint, costat)){
                this.exportarLaberint();
            }
            else{
                JOptionPane.showMessageDialog(FEditorLaberint.this,log.obtenirUltimError(), "ERROR",JOptionPane.ERROR_MESSAGE);
            }
        }
        
        private void exportarLaberint(){
            JFileChooser fc = new JFileChooser();
            fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int resultat = fc.showDialog(FEditorLaberint.this, "Exportar laberint");
            if(resultat == JFileChooser.APPROVE_OPTION){
                File fitxer = fc.getCurrentDirectory();
                SimpleDateFormat sdf = new SimpleDateFormat("_dd_MM_yyyy_kk_mm");
                String marcaTemps = sdf.format(new Date());
                String desti = fitxer.getPath()+"/laberint"+marcaTemps+".txt";
                boolean operacio = this.exportarFitxer(desti);
                if(operacio){
                    JOptionPane.showMessageDialog(FEditorLaberint.this,
                                                "S'ha exportat el mapa correctament a "+desti,
                                                "Mapa exportar correctament!",JOptionPane.INFORMATION_MESSAGE);
                }
                else{
                    JOptionPane.showMessageDialog(FEditorLaberint.this,
                            "Hi ha hagut un error al exportar el mapa\nverifiqui el log",
                            "Error al exportar el mapa",JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        
        public boolean exportarFitxer(String fitxer){
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
     * DECLARACIÓ D'INTENCIONS DE LA CLASSE
     * Defineix un botó amb dos coordenades que l'identifiquen, quan es
     * realitzi click sobre el botó aquest obtindrà l'imatge que està 
     * seleccionada i també assignara el valor pertinent a la casella del
     * laberint;
     */
    private class btnCasella extends JButton implements ActionListener{
        /**
         * Coordenades de la casella;
         */
        private int x;
        private int y;
        
        /**
         * @pre: x i y són >= 0 i <= costat
         * @post: em creeat un boto amb unes coordenades epecifiques;
         * @param x
         * @param y 
         */
        public btnCasella(int x, int y){
            this.x = x;
            this.y = y;
            this.addActionListener(this);
        }
        
        /**
         * @pre:--;
         * @post: em tractat l'event sobre el botó, aquest event consisteix
         * en assignar l'imatge que toca al boto i assignar el valor al 
         * tauler del laberint;
         * @param e 
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            this.setIcon(elementSeleccionat.obtenirImatge());
            laberint[x][y] = elementSeleccionat.obtenirId();
        }
    }
}
