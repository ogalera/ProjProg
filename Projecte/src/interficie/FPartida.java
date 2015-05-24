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
import interficie.components.Crono;
import interficie.components.Marcador;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
        this.add(panellLaberint, BorderLayout.CENTER);
        panellLaberint.setFocusable(true);
        this.setFocusable(false);
        cronometre = new Crono();
        this.setAlwaysOnTop(true);
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

    @Override
    public void pintarItemPartida(ImageIcon item){
        this.lblItem.setIcon(item);
        this.lblItem.repaint();
    }
    
    @Override
    public void pintarFinalPartida(boolean guanyat) {
        cronometre.pararCrono();
//        if(guanyat){
//            JOptionPane.showMessageDialog("HAS GUANYAT!", null,JOptionPane.INFORMATION_MESSAGE);
//        }
//        else{
//            JOptionPane.showMessageDialog("HAS PERDUT","ERROR",JOptionPane.INFORMATION_MESSAGE);
//        }
        this.dispose();
        
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
    public void assignarPartida(final Partida partida) {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e){
                partida.finalitzarPartida();
            }
        });
    }
    
}
