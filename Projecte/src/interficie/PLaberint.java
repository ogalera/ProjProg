/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interficie;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import logica.enumeracions.EElement;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import logica.Punt;
import logica.enumeracions.EDireccio;
import logica.laberints.Laberint;
import logica.log.Log;
/**
 *
 * @author Moises
 */
public class PLaberint extends JPanel implements IPintadorLaberint{
    private final Log log;
//    private ImageIcon imgPacmanRedimensionada;
//    private ImageIcon imgFantasma1Redimensionada;
//    private ImageIcon imgFantasma2Redimensionada;
//    private ImageIcon imgFantasma3Redimensionada;
//    private ImageIcon imgParetRedimensionada;
//    private ImageIcon imgMonedaRedimensionada;
    private JLabel[][] laberintGrafic;
    private static final int MIDA_PREFERIDA = 800;
    private static final int MIDA_MINIMA = 500;
    private static final int MIDA_MAXIMA = 900;
    private int midaLabels;
    
    public PLaberint(){
        log = Log.getInstance(PLaberint.class);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyPressed(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyReleased(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void pintarMovimentPersonatge(Punt pOrigen, EDireccio direccio, ImageIcon imatge) {
        this.laberintGrafic[pOrigen.obtenirFila()][pOrigen.obtenirColumna()].setIcon(null);
        Punt pDesplasat = pOrigen.generarPuntDesplasat(direccio);
        this.laberintGrafic[pDesplasat.obtenirFila()][pDesplasat.obtenirColumna()].setIcon(imatge);
    }

    @Override
    public void pintarNouItem(Punt pNouItem, EElement nouItem) {
        int fila = pNouItem.obtenirFila();
        int columna = pNouItem.obtenirColumna();
        this.laberintGrafic[fila][columna].setIcon(nouItem.obtenirImatge());
    }

    
    @Override
    public void pintarLaberint(Laberint laberint) {
        this.setPreferredSize(new Dimension(MIDA_PREFERIDA, MIDA_PREFERIDA));
        this.setMaximumSize(new Dimension(MIDA_MINIMA, MIDA_MINIMA));
        this.setMinimumSize(new Dimension(MIDA_MAXIMA, MIDA_MAXIMA));
        this.setBackground(Color.BLACK);
        
        int numCaselles = laberint.obtenirMidaCostatTauler();
        this.setLayout(new GridLayout(numCaselles, numCaselles));
        midaLabels = MIDA_PREFERIDA/numCaselles;
        laberintGrafic = new JLabel[numCaselles][numCaselles];
        EElement.redimensionarImatges(midaLabels);

        for (int fila = 0; fila < numCaselles; fila++){
            for (int columna = 0; columna < numCaselles; columna++){
                laberintGrafic[fila][columna] = new JLabel();
                EElement element = laberint.obtenirElement(new Punt(fila, columna));
                ImageIcon imatge = element.obtenirImatge();
                laberintGrafic[fila][columna].setIcon(imatge);
                this.add(laberintGrafic[fila][columna]);
            }
        }  
    }
    

    @Override
    public void assignarControladorTeclat(KeyListener controlador) {
        this.addKeyListener(controlador);
    }

    @Override
    public void pintarMovimentItem(Punt pOrigen, EDireccio direccio, ImageIcon imatge) {
        Punt pDesti = pOrigen.generarPuntDesplasat(direccio);
        laberintGrafic[pDesti.obtenirFila()][pDesti.obtenirColumna()].setIcon(laberintGrafic[pOrigen.obtenirFila()][pOrigen.obtenirColumna()].getIcon());
        laberintGrafic[pOrigen.obtenirFila()][pOrigen.obtenirColumna()].setIcon(imatge);
    }
    
    public int obtenirMidaImatge(){
        return this.midaLabels;
    }

    @Override
    public void pintarSortida(Punt pSortida) {
        this.laberintGrafic[pSortida.obtenirFila()][pSortida.obtenirColumna()].setIcon(EElement.SORTIDA.obtenirImatge());
    }
}

    

