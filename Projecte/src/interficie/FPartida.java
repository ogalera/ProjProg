/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interficie;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import java.awt.Color;
import interficie.components.Crono;
import interficie.components.Marcador;
import javax.swing.ImageIcon;

/**
 *
 * @author Moises
 */
public class FPartida extends JFrame{
    
    private JPanel partEsquerra;
    private JPanel partDreta;
    private JPanel partInferior;
    private JPanel partCentral;
    
    
    private Marcador marcadorPacman;
    private Marcador marcadorEnemic;
    private Crono cronometre;
    private FLaberint pintadorLaberint;
   
    
    public FPartida(FLaberint _pintadorLaberint, ImageIcon imgPacman, ImageIcon imgEnemic){

        this.getContentPane().setLayout(new BorderLayout());
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pintadorLaberint = _pintadorLaberint;
        pintadorLaberint.setFocusable(true);//Per a que tingui el focus del keyListener
        inicialitzaComponents(imgPacman, imgEnemic);
        pintarPartida();
        cronometre.iniciarCrono();
    }
//    @Override
//    public void pintarPartida(){
//        setVisible(true);
//        
//    }

     private void pintarPartida(){
//       assignaDimensions();
//       assignaImatges();
        construeixPanells();
        afegeixComponents();
        setVisible(true);
        
    }
    private void inicialitzaComponents(ImageIcon imgPacman, ImageIcon imgEnemic){
        marcadorPacman = new Marcador(imgPacman);
        marcadorEnemic= new Marcador(imgEnemic);
        cronometre = new Crono();
    }
    private void afegeixComponents(){

        add(partEsquerra, BorderLayout.WEST);
        add(partDreta, BorderLayout.EAST);
        add(partInferior, BorderLayout.SOUTH);
        add(partCentral, BorderLayout.CENTER);
        colocaLaberint();
    }
    
    private void construeixPanells(){
        construeixPanellEsquerra();
        construeixPanellDret();
        construeixPanellInferior();
        construeixPanellCentral();  
    }
    
    private void construeixPanellEsquerra(){
        partEsquerra = new JPanel();
        partEsquerra.setLayout(new BoxLayout(partEsquerra, BoxLayout.X_AXIS));
        //partEsquerra.setLayout(new FlowLayout(FlowLayout.CENTER));
        partEsquerra.add(marcadorPacman);
        partEsquerra.setBackground(Color.green);
    }
    
    private void construeixPanellDret(){
        partDreta = new JPanel();
        partDreta.setLayout(new BoxLayout(partDreta, BoxLayout.X_AXIS));
        //partDreta.setLayout(new FlowLayout(FlowLayout.CENTER));
        partDreta.add(marcadorEnemic);
        partDreta.setBackground(Color.blue);
    }
    
    private void construeixPanellInferior(){
        partInferior = new JPanel();
        partInferior.setLayout(new FlowLayout());
        partInferior.add(cronometre);
        partInferior.setBackground(Color.red);
    }
    
    private void construeixPanellCentral(){   
        partCentral = new JPanel();
        //partCentral.setLayout(new BoxLayout(partCentral, BoxLayout.Y_AXIS));
        //partCentral.setLayout(new FlowLayout());
        partCentral.setLayout(null);
        partCentral.add(pintadorLaberint);
        partCentral.setBackground(Color.CYAN);
        //pintadorLaberint.setVisible(true);

    }
    
    private void colocaLaberint(){
        Dimension centre = partCentral.getSize();
        Dimension laberint = pintadorLaberint.getSize();
        //pintadorLaberint.setLocation((centre.width - laberint.width) / 2, (centre.height - laberint.height) / 2);
        pintadorLaberint.setLocation(100,10);
    }
    
}
