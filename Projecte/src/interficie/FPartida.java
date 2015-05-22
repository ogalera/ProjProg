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
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
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
    private JPanel partSuperior;
    private JLabel lblItem;
    private JLabel itemPacman;
    private JLabel itemEnemic;
    
    private Marcador marcadorPacman;
    private Marcador marcadorEnemic;
    private final Crono cronometre;

    public FPartida(PLaberint panellLaberint){
        getContentPane().setLayout(new BorderLayout());
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(panellLaberint, BorderLayout.CENTER);
        panellLaberint.setFocusable(true);
        this.setFocusable(false);
        cronometre = new Crono();
        this.setAlwaysOnTop(true);
        //this.add(cronometre, BorderLayout.SOUTH);
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
        
        //Creacio part esquerra
        JPanel zonaEsquerra = new JPanel();
        zonaEsquerra.setLayout(new BoxLayout(zonaEsquerra, BoxLayout.X_AXIS));
        marcadorPacman = new Marcador(partida.obtenirImatgePacman());
        JPanel panellPacman = new JPanel();
        panellPacman.setLayout(new BoxLayout(panellPacman, BoxLayout.Y_AXIS));
        itemPacman = new JLabel();
        itemPacman.setPreferredSize(new Dimension(50, 50));
        panellPacman.add(marcadorPacman);
        panellPacman.add(itemPacman);
        zonaEsquerra.add(panellPacman);
        this.add(zonaEsquerra, BorderLayout.WEST);
        
        //Creacio part dreta
        JPanel zonaDreta = new JPanel();
        zonaDreta.setLayout(new BoxLayout(zonaDreta, BoxLayout.X_AXIS));
        marcadorEnemic= new Marcador(partida.obtenirImatgeFantasma());
        JPanel panellEnemic = new JPanel();
        panellEnemic.setLayout(new BoxLayout(panellEnemic, BoxLayout.Y_AXIS));
        itemEnemic = new JLabel();
        itemEnemic.setPreferredSize(new Dimension(50, 50));
        panellEnemic.add(marcadorEnemic);
        panellEnemic.add(itemEnemic);
        zonaDreta.add(panellEnemic);
        this.add(zonaDreta, BorderLayout.EAST);
        
        //Creacio part baixa
        JPanel zonaBaixa = new JPanel();
        zonaBaixa.setLayout(new FlowLayout());
        zonaBaixa.add(cronometre);
        this.add(zonaBaixa, BorderLayout.SOUTH);
        
        //Creacio part alta
        JPanel zonaAlta = new JPanel();
        zonaAlta.setLayout(new FlowLayout());
        lblItem = new JLabel(new ImageIcon("res/item.png"));
        lblItem.setPreferredSize(new Dimension(50, 50));
        zonaAlta.add(lblItem);
        this.add(zonaAlta, BorderLayout.NORTH);
        this.setVisible(true);
    }

    public void pintarItemPartida(ImageIcon item){
        this.lblItem.setIcon(item);
        this.lblItem.repaint();
    }
    
    @Override
    public void pintarFinalPartida() {
        cronometre.pararCrono();
    }

    @Override
    public void pintarIniciPartida() {
        cronometre.iniciarCrono();
    }

    @Override
    public void pintarItemPacman(ImageIcon imatge) {
        //itemPacman.setIcon(imatge);
        if (imatge != null) itemPacman.setIcon(new ImageIcon(imatge.getImage().getScaledInstance(50,50,Image.SCALE_AREA_AVERAGING)));
        else itemPacman.setIcon(imatge);
    }

    @Override
    public void pintarItemEnemic(ImageIcon imatge) {
        if (imatge != null)itemEnemic.setIcon(new ImageIcon(imatge.getImage().getScaledInstance(50,50,Image.SCALE_AREA_AVERAGING)));
        else itemEnemic.setIcon(imatge);
    }

    @Override
    public void tancarPantalla() {
        this.dispose();
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
//    private void afegeixComponents(){
//
//        add(partEsquerra, BorderLayout.WEST);
//        add(partDreta, BorderLayout.EAST);
//        add(partInferior, BorderLayout.SOUTH);
//        add(partCentral, BorderLayout.CENTER);
//        colocaLaberint();
//    }
    
//    private void construeixPanells(){
//        construeixPanellEsquerra();
//        construeixPanellDret();
//        construeixPanellInferior();
//        construeixPanellCentral();  
//    }
    
//    private void construeixPanellEsquerra(){
//        partEsquerra = new JPanel();
//        partEsquerra.setLayout(new BoxLayout(partEsquerra, BoxLayout.X_AXIS));
//        //partEsquerra.setLayout(new FlowLayout(FlowLayout.CENTER));
//        partEsquerra.add(marcadorPacman);
//        partEsquerra.setBackground(Color.green);
//    }
//    
//    private void construeixPanellDret(){
//        partDreta = new JPanel();
//        partDreta.setLayout(new BoxLayout(partDreta, BoxLayout.X_AXIS));
//        //partDreta.setLayout(new FlowLayout(FlowLayout.CENTER));
//        partDreta.add(marcadorEnemic);
//        partDreta.setBackground(Color.blue);
//    }
//    
//    private void construeixPanellInferior(){
//        partInferior = new JPanel();
//        partInferior.setLayout(new FlowLayout());
//        partInferior.add(cronometre);
//        partInferior.setBackground(Color.red);
//    }
//    
//    private void construeixPanellCentral(){   
//        partCentral = new JPanel();
//        //partCentral.setLayout(new BoxLayout(partCentral, BoxLayout.Y_AXIS));
//        //partCentral.setLayout(new FlowLayout());
//        partCentral.setLayout(null);
////        partCentral.add(pintadorLaberint);
//        partCentral.setBackground(Color.CYAN);
//        //pintadorLaberint.setVisible(true);
//
//    }
    
//    private void colocaLaberint(){
//        Dimension centre = partCentral.getSize();
////        Dimension laberint = pintadorLaberint.getSize();
//        //pintadorLaberint.setLocation((centre.width - laberint.width) / 2, (centre.height - laberint.height) / 2);
////        pintadorLaberint.setLocation(100,10);
//    }
}
