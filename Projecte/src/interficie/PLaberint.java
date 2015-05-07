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
    private Image imgPacmanRedimensionada;
    private Image imgFantasma1Redimensionada;
    private Image imgFantasma2Redimensionada;
    private Image imgFantasma3Redimensionada;
    private Image imgParetRedimensionada;
    private Image imgMonedaRedimensionada;
    private JLabel[][] laberintGrafic;
    private static final int MIDA_PREFERIDA = 800;
    private static final int MIDA_MINIMA = 500;
    private static final int MIDA_MAXIMA = 900;
    
    public PLaberint(){
        log = Log.getInstance(PLaberint.class);
    }
    
     
                    /////////////////////////////////////
                    // IMPLEMENTACIO AMB UN GridLayout //
                    /////////////////////////////////////
     
//    public FLaberint(EElement [][] _laberint) {
//       this.setSize(MIDA, MIDA);
//        this.setBackground(Color.BLACK);
//        
//        int numCaselles = _laberint[0].length;
//        this.setLayout(new GridLayout(numCaselles, numCaselles));
//        int midaLabels = MIDA/numCaselles;
//        laberintGrafic = new JLabel[numCaselles][numCaselles];
//        for (int i = 0; i < numCaselles; i++){
//            for (int j = 0; j < numCaselles; j++){
//                laberintGrafic[j][i] = new JLabel();
//                laberintGrafic[j][i].setBounds(i*midaLabels, j*midaLabels, midaLabels, midaLabels);
//                Icon icona = new ImageIcon(_laberint[j][i].obtenirImatge().getImage().getScaledInstance(laberintGrafic[j][i].getWidth(),laberintGrafic[j][i].getHeight(),Image.SCALE_DEFAULT));
//                laberintGrafic[j][i].setIcon(icona);
//            }
//        }  
//    }
    
//    @Override
//    public void pintarMoviment(Punt a, EElement _a, Punt b, EElement _b){
//        
//        if ( _a == EElement.RES){
//            laberintGrafic[a.obtenirY()][a.obtenirX()].setIcon(null);
//            laberintGrafic[a.obtenirY()][a.obtenirX()].validate();
//            laberintGrafic[a.obtenirY()][a.obtenirX()].repaint();
//        }
//        else
//        {
//            Icon icona = new ImageIcon(_a.obtenirImatge().getImage().getScaledInstance(laberintGrafic[a.obtenirY()][a.obtenirX()].getWidth(),laberintGrafic[a.obtenirY()][a.obtenirX()].getHeight(),Image.SCALE_DEFAULT));
//            laberintGrafic[a.obtenirY()][a.obtenirX()].setIcon(icona);
//            laberintGrafic[a.obtenirY()][a.obtenirX()].validate();
//            laberintGrafic[a.obtenirY()][a.obtenirX()].repaint();
//        }
//        if ( _b == EElement.RES){
//            laberintGrafic[b.obtenirY()][b.obtenirX()].setIcon(null);
//            laberintGrafic[b.obtenirY()][b.obtenirX()].validate();
//            laberintGrafic[b.obtenirY()][b.obtenirX()].repaint();
//        }
//        else{
//            Icon icona = new ImageIcon(_b.obtenirImatge().getImage().getScaledInstance(laberintGrafic[b.obtenirY()][b.obtenirX()].getWidth(),laberintGrafic[b.obtenirY()][b.obtenirX()].getHeight(),Image.SCALE_DEFAULT));
//            laberintGrafic[b.obtenirY()][b.obtenirX()].setIcon(icona);
//            laberintGrafic[b.obtenirY()][b.obtenirX()].validate();
//            laberintGrafic[b.obtenirY()][b.obtenirX()].repaint();
//        }
//    }
    
//    @Override
//    public  void pintarLaberint(){
//        int costat = laberintGrafic[0].length;
//        for (int i = 0; i < costat; i++){
//            for (int j = 0; j < costat; j++){
//                this.add(laberintGrafic[i][j]);
//                laberintGrafic[i][j].validate();
//            }
//        }
//        creaFinestra();
//    }
    
    private void creaFinestra(){
        this.setVisible(true);
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
    public void pintarMoviment(Punt pOrigen, EElement eOrigen, EDireccio direccio, EElement eDesti) {
        ImageIcon imgOrigen = obtenirImatge(eOrigen);
        ImageIcon imgDesti = obtenirImatge(eDesti);
        this.laberintGrafic[pOrigen.obtenirX()][pOrigen.obtenirY()].setIcon(imgOrigen);
        Punt pDesplasat = pOrigen.generarPuntDesplasat(direccio);
        this.laberintGrafic[pDesplasat.obtenirX()][pDesplasat.obtenirY()].setIcon(imgDesti);
    }

    @Override
    public void pintarNouItem(Punt pNouItem, EElement nouItem) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private ImageIcon obtenirImatge(EElement element){
        ImageIcon imatge = null;
        switch(element){
            case PACMAN:{
                imatge = new ImageIcon(imgPacmanRedimensionada);
            }break;
            case FANTASMA1:{
                imatge = new ImageIcon(imgFantasma1Redimensionada);
            }break;
            case FANTASMA2:{
                imatge = new ImageIcon(imgFantasma2Redimensionada);
            }break;
            case FANTASMA3:{
                imatge = new ImageIcon(imgFantasma3Redimensionada);
            }break;
            case PARET:{
                imatge = new ImageIcon(imgParetRedimensionada);
            }break;
            case MONEDA:{
                imatge = new ImageIcon(imgMonedaRedimensionada);
            }break;
            case RES:{
                imatge = null;
            }break;
            default:{
                log.afegirError("No existeix imatge per aquest element ("+element.name()+")");
            }break;
        }
        return imatge;
    }
    
    @Override
    public void pintarLaberint(Laberint laberint) {
        this.setPreferredSize(new Dimension(MIDA_PREFERIDA, MIDA_PREFERIDA));
        this.setMaximumSize(new Dimension(MIDA_MINIMA, MIDA_MINIMA));
        this.setMinimumSize(new Dimension(MIDA_MAXIMA, MIDA_MAXIMA));
        this.setBackground(Color.BLACK);
        
        int numCaselles = laberint.obtenirMidaCostatTauler();
        this.setLayout(new GridLayout(numCaselles, numCaselles));
        int midaLabels = MIDA_PREFERIDA/numCaselles;
        laberintGrafic = new JLabel[numCaselles][numCaselles];
        
        imgPacmanRedimensionada = EElement.PACMAN.obtenirImatge().getImage().getScaledInstance(midaLabels, midaLabels, Image.SCALE_DEFAULT);
        imgFantasma1Redimensionada = EElement.FANTASMA1.obtenirImatge().getImage().getScaledInstance(midaLabels, midaLabels, Image.SCALE_DEFAULT);
        imgFantasma2Redimensionada = EElement.FANTASMA2.obtenirImatge().getImage().getScaledInstance(midaLabels, midaLabels, Image.SCALE_DEFAULT);
        imgFantasma3Redimensionada = EElement.FANTASMA3.obtenirImatge().getImage().getScaledInstance(midaLabels, midaLabels, Image.SCALE_DEFAULT);
        imgParetRedimensionada = EElement.PARET.obtenirImatge().getImage().getScaledInstance(midaLabels, midaLabels, Image.SCALE_DEFAULT);
        imgMonedaRedimensionada = EElement.MONEDA.obtenirImatge().getImage().getScaledInstance(midaLabels, midaLabels, Image.SCALE_DEFAULT);
        
        for (int i = 0; i < numCaselles; i++){
            for (int j = 0; j < numCaselles; j++){
                laberintGrafic[i][j] = new JLabel();
                EElement element = laberint.obtenirElement(new Punt(i, j));
                ImageIcon imatge = obtenirImatge(element);
                laberintGrafic[i][j].setIcon(imatge);
                this.add(laberintGrafic[i][j]);
            }
        }  
    }

    @Override
    public void assignarControladorTeclat(KeyListener controlador) {
        this.addKeyListener(controlador);
    }
}

    

