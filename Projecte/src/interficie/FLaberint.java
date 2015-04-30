/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interficie;
import java.awt.Color;
import java.awt.Image;
import logica.enumeracions.EElement;
import javax.swing.JInternalFrame;
import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.Icon;
import logica.Punt;
/**
 *
 * @author Moises
 */
public class FLaberint extends JPanel implements IPintadorLaberint{
    
    private JLabel[][] laberintGrafic;
    private static final int MIDA = 650;

    
                    /////////////////////////////////////
                    //      IMPLEMENTACIO AMB JLabel   //
                    /////////////////////////////////////
     public FLaberint(EElement [][] _laberint) {
        this.setSize(MIDA, MIDA);
        this.setBackground(Color.BLACK);
        this.setLayout(null);
        int numCaselles = _laberint[0].length;
        int midaLabels = MIDA/numCaselles;
        laberintGrafic = new JLabel[numCaselles][numCaselles];
        for (int i = 0; i < numCaselles; i++){
            for (int j = 0; j < numCaselles; j++){
                laberintGrafic[j][i] = new JLabel();
                laberintGrafic[j][i].setBounds(i*midaLabels, j*midaLabels, midaLabels, midaLabels);
                Icon icona = new ImageIcon(_laberint[j][i].obtenirImatge().getImage().getScaledInstance(laberintGrafic[j][i].getWidth(),laberintGrafic[j][i].getHeight(),Image.SCALE_DEFAULT));
                laberintGrafic[j][i].setIcon(icona);
            }
        }  
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
    
    //@Override
    public void pintarMoviment(Punt a, EElement _a, Punt b, EElement _b){
        
        if ( _a == EElement.RES){
            laberintGrafic[a.obtenirY()][a.obtenirX()].setIcon(null);
            laberintGrafic[a.obtenirY()][a.obtenirX()].validate();
            laberintGrafic[a.obtenirY()][a.obtenirX()].repaint();
        }
        else
        {
            Icon icona = new ImageIcon(_a.obtenirImatge().getImage().getScaledInstance(laberintGrafic[a.obtenirY()][a.obtenirX()].getWidth(),laberintGrafic[a.obtenirY()][a.obtenirX()].getHeight(),Image.SCALE_DEFAULT));
            laberintGrafic[a.obtenirY()][a.obtenirX()].setIcon(icona);
            laberintGrafic[a.obtenirY()][a.obtenirX()].validate();
            laberintGrafic[a.obtenirY()][a.obtenirX()].repaint();
        }
        if ( _b == EElement.RES){
            laberintGrafic[b.obtenirY()][b.obtenirX()].setIcon(null);
            laberintGrafic[b.obtenirY()][b.obtenirX()].validate();
            laberintGrafic[b.obtenirY()][b.obtenirX()].repaint();
        }
        else{
            Icon icona = new ImageIcon(_b.obtenirImatge().getImage().getScaledInstance(laberintGrafic[b.obtenirY()][b.obtenirX()].getWidth(),laberintGrafic[b.obtenirY()][b.obtenirX()].getHeight(),Image.SCALE_DEFAULT));
            laberintGrafic[b.obtenirY()][b.obtenirX()].setIcon(icona);
            laberintGrafic[b.obtenirY()][b.obtenirX()].validate();
            laberintGrafic[b.obtenirY()][b.obtenirX()].repaint();
        }
    }
    
   // @Override
    public  void pintarLaberint(){
        int costat = laberintGrafic[0].length;
        for (int i = 0; i < costat; i++){
            for (int j = 0; j < costat; j++){
                this.add(laberintGrafic[i][j]);
                laberintGrafic[i][j].validate();
            }
        }
        creaFinestra();
    }
    
    private void creaFinestra(){
        this.setVisible(true);

    }
}

    

