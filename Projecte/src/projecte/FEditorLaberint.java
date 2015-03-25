/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projecte;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.WindowConstants;

/**
 *
 * @author oscar
 */
public class FEditorLaberint extends JFrame{
    private JButton btnPacman, btnFantasma, btnParet, btnMoneda, btnItemSeleccionat;
    private JButton btnValidar;
    private final int [][] laberint;
    private Log log;
    private EnumElement elementSeleccionat = EnumElement.RES;
    private int costat;
    
    public FEditorLaberint(int costat){
        super("Editor de laberints");
        
        log = Log.getInstance(FEditorLaberint.class);
        
        this.laberint = new int[costat][costat];
        
        JMenuBar menu = new JMenuBar();
        JMenu fitxer = new JMenu("Fitxer");
        JMenuItem importar = new JMenuItem("Importar laberint");
        fitxer.add(importar);
        fitxer.addSeparator();
        JMenuItem sortir = new JMenuItem("Sortir");
        fitxer.add(sortir);
        menu.add(fitxer);
        this.setJMenuBar(menu);
        
        
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
        this.setVisible(true);
    }
    
    private JPanel crearContingut(int costat){
        this.costat = costat;
        JPanel contingut = new JPanel();
        contingut.setLayout(new BoxLayout(contingut, BoxLayout.Y_AXIS));
        JPanel panellLaberint = new JPanel();
        panellLaberint.setLayout(new GridLayout(costat, costat,4, 4));
        for(int i = 0; i < costat; i++){
            for(int j = 0; j < costat; j++){
                BtnCasella b = new BtnCasella(i, j);
                b.setContentAreaFilled(false);
                panellLaberint.add(b);
                laberint[i][j] = EnumElement.RES.getId();
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
        btnPacman.setIcon(EnumElement.PACMAN.getImatge());
        btnPacman.setContentAreaFilled(false);
        btnPacman.addActionListener(ace);
        
        btnFantasma = new JButton();
        btnFantasma.setIcon(EnumElement.FANTASMA1.getImatge());
        btnFantasma.setContentAreaFilled(false);
        btnFantasma.addActionListener(ace);
        
        btnParet = new JButton();
        btnParet.setIcon(EnumElement.PARED.getImatge());
        btnParet.setContentAreaFilled(false);
        btnParet.addActionListener(ace);
        
        btnMoneda = new JButton();
        btnMoneda.setIcon(EnumElement.MONEDA.getImatge());
        btnMoneda.setContentAreaFilled(false);
        btnMoneda.addActionListener(ace);
        
        btnItemSeleccionat = new JButton();
        btnItemSeleccionat.setIcon(EnumElement.INDEFINIT.getImatge());
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
                btnItemSeleccionat.setIcon(EnumElement.PACMAN.getImatge());
                elementSeleccionat = EnumElement.PACMAN;
            }
            else if (origen == btnFantasma){
                btnItemSeleccionat.setIcon(EnumElement.FANTASMA1.getImatge());
                elementSeleccionat = EnumElement.FANTASMA1;
            }
            else if (origen == btnParet){
                btnItemSeleccionat.setIcon(EnumElement.PARED.getImatge());
                elementSeleccionat = EnumElement.PARED;
            }
            else if (origen == btnMoneda){
                btnItemSeleccionat.setIcon(EnumElement.MONEDA.getImatge());
                elementSeleccionat = EnumElement.MONEDA;
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
            fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            int resultat = fc.showDialog(FEditorLaberint.this, "ok");
            if(resultat == JFileChooser.APPROVE_OPTION){
                File fitxer = fc.getCurrentDirectory();
                JOptionPane.showMessageDialog(FEditorLaberint.this, fitxer.getAbsolutePath(),"Operacio valida",JOptionPane.INFORMATION_MESSAGE);
                this.exportarFitxer(fitxer.getAbsolutePath()+"/aaa.txt");
            }
            else{
                JOptionPane.showMessageDialog(FEditorLaberint.this, "Error al exportar el laberint","FAIL",JOptionPane.ERROR_MESSAGE);
            }
        }
        
        public void exportarFitxer(String fitxer){
            try(BufferedWriter bw = new BufferedWriter(new FileWriter(fitxer))){
                for(int i = 0; i < costat; i++){
                    StringBuilder stringBuilder = new StringBuilder();
                    for(int j = 0; j < costat; j++){
                        stringBuilder.append(laberint[i][j]);
                    }
                    bw.write(stringBuilder.toString());
                    if(i+1 < costat ) bw.newLine();
                }
            }
            catch(IOException ioe){
                log.afegirError(ioe.getMessage());
            }
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
    private class BtnCasella extends JButton implements ActionListener{
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
        public BtnCasella(int x, int y){
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
            this.setIcon(elementSeleccionat.getImatge());
            laberint[x][y] = elementSeleccionat.getId();
        }
    }
}
