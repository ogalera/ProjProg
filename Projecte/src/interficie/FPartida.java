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
import logica.Partida;

/**
 *
 * @author Moises
 */
public class FPartida extends JFrame implements IPintadorPartida{
    
    private JPanel partEsquerra;
    private JPanel partDreta;
    private JPanel partInferior;
    private JPanel partCentral;
    
    
    private Marcador marcadorPacman;
    private Marcador marcadorEnemic;
    private final Crono cronometre;

    public FPartida(PLaberint panellLaberint){
        getContentPane().setLayout(new BorderLayout());
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(panellLaberint, BorderLayout.CENTER);
        this.setFocusable(false);
        cronometre = new Crono();
        this.add(cronometre, BorderLayout.SOUTH);
    }
    
//    @Override
//    public void pintarPartida(){
//        setVisible(true);
//        
//    }

//    @Override
//    public void pintarPartida(){
////       assignaDimensions();
////       assignaImatges();
//        construeixPanells();
//        afegeixComponents();
//        setVisible(true);
//    }
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
//        partCentral.add(pintadorLaberint);
        partCentral.setBackground(Color.CYAN);
        //pintadorLaberint.setVisible(true);

    }
    
    private void colocaLaberint(){
        Dimension centre = partCentral.getSize();
//        Dimension laberint = pintadorLaberint.getSize();
        //pintadorLaberint.setLocation((centre.width - laberint.width) / 2, (centre.height - laberint.height) / 2);
//        pintadorLaberint.setLocation(100,10);
    }

    @Override
    public void pintarPuntsPacman(int punts) {
        marcadorPacman.canviarPuntuacio(punts);
    }

    @Override
    public void pintarPuntsEnemic(int punts) {
        marcadorEnemic.canviarPuntuacio(punts);
    }

    @Override
    public void pintarPartida(Partida partida) {
        marcadorPacman = new Marcador(partida.obtenirImatgePacman());
        marcadorEnemic= new Marcador(partida.obtenirImatgeFantasma());
        this.add(marcadorPacman, BorderLayout.WEST);
        this.add(marcadorEnemic, BorderLayout.EAST);
        this.setVisible(true);
    }

    @Override
    public void pintarFinalPartida() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void pintarIniciPartida() {
        cronometre.iniciarCrono();
    }
}
