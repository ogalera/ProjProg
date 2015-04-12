/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interficie;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.image.BufferedImage;
import java.io.IOException;
import interficie.components.background;


/**
 *
 * @author Moi
 */
public class FInici extends JFrame implements ActionListener {
    private static final int heightBoto=7; // El tant per cent respecte el tamany de pantalla que volem que sigui la altura dels botons
    private static final int widthBoto=12; //El tant per cent respecte el tamany de pantalla que volem que sigui la amplada dels botons
    JButton btnAlta;
    JButton btnEntrar;
    JButton btnSortir;
    JButton btnRanking;


    
    
    public FInici() throws IOException{
       super();
       creaFinestra();
       creaButons();
       this.repaint();
    }
    
    private void creaButons(){
        
        int alturaBoto= (heightBoto * this.getHeight()) / 100;
        int ampladaBoto = (widthBoto * this.getWidth()) / 100;
        
        btnEntrar = new JButton(new ImageIcon("res/btnEntrar.png"));
        btnEntrar.setBounds((this.getWidth() /2) - (ampladaBoto /2), ((this.getHeight() * 65 ) / 100) , ampladaBoto, alturaBoto);
        btnEntrar.setOpaque(false);
        btnEntrar.setContentAreaFilled(false);
        btnEntrar.setBorderPainted(false);
        
        btnAlta = new JButton(new ImageIcon("res/btnAlta.png"));
        btnAlta.setBounds((this.getWidth() /2) - (ampladaBoto /2), ((this.getHeight() * 65 ) / 100)+ alturaBoto , ampladaBoto, alturaBoto);
        btnAlta.setOpaque(false);
        btnAlta.setContentAreaFilled(false);
        btnAlta.setBorderPainted(false);
        
        btnRanking = new JButton(new ImageIcon("res/btnRanking.png"));
        btnRanking.setBounds((this.getWidth() /2) - (ampladaBoto /2), ((this.getHeight() * 65 ) / 100)+ (alturaBoto * 2), ampladaBoto, alturaBoto);
        btnRanking.setOpaque(false);
        btnRanking.setContentAreaFilled(false);
        btnRanking.setBorderPainted(false);
        
        btnSortir = new JButton(new ImageIcon("res/btnSortir.png"));
        btnSortir.setBounds((this.getWidth() /2) - (ampladaBoto /2),((this.getHeight() * 65 ) / 100) + (alturaBoto * 3), ampladaBoto, alturaBoto);
        btnSortir.setOpaque(false);
        btnSortir.setContentAreaFilled(false);
        btnSortir.setBorderPainted(false);
        
        add(btnRanking);
        add(btnEntrar);
        add(btnAlta);
        add(btnSortir); 
        
        btnRanking.addActionListener(this);
        btnEntrar.addActionListener(this);
        btnAlta.addActionListener(this);
        btnSortir.addActionListener(this);
        
          
    }
    private void creaFinestra() throws IOException{
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        BufferedImage bf = ImageIO.read(new File("res/Inici.png"));
        setVisible(true);
        setContentPane(new background(bf, this.getHeight(),this.getWidth() ));
        setResizable(false);
        
        
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

        if (e.getSource()==btnRanking) {
            setTitle("boto ranking");
        }
        else if (e.getSource()==btnEntrar) {
            setTitle("boto entrar");
        }
        else if (e.getSource()==btnAlta) {
            setTitle("boto alta");
        } 
        else if (e.getSource() == btnSortir){
            setTitle("boto sortir");
        }
    }
    
}
