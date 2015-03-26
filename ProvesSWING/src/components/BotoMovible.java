/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package components;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;

/**
 *
 * @author Moi
 */
public class BotoMovible extends JFrame {
    
    private JButton boto;
    private int x=250, y=250;
    private ImageIcon imatge;
    
    public BotoMovible(){
        
       JDesktopPane p =new JDesktopPane();
       this.setTitle("moviments");
        imatge=new ImageIcon("res/pacman.png");
        boto=new JButton();
        boto.setIcon(imatge);
        
        p.add(boto);
        
        boto.setBounds(x, y, 50, 50);
        
        boto.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void keyPressed(KeyEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                int teclaPremuda= e.getKeyCode();
                System.out.println(teclaPremuda);
                if (teclaPremuda == 38){
                    System.out.println("Amunt");
                    y-=15;
                    boto.setBounds(x, y, 50, 50);
                }
                else if (teclaPremuda == 40){
                    System.out.println("Avall");
                    y+=15;
                    boto.setBounds(x, y, 50, 50);
                }
                else if (teclaPremuda == 37){
                    System.out.println("Esquerra");
                    x-=15;
                    boto.setBounds(x, y, 50, 50);
                }
                else if (teclaPremuda == 39){
                    System.out.println("Dreta");
                    x+=15;
                    boto.setBounds(x, y, 50, 50);
                }
                
        
                
                
            }

            @Override
            public void keyReleased(KeyEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        
        });
        
        this.add(p,BorderLayout.CENTER);
        this.setVisible(true);
        this.setSize(500,500);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
    }
    
}
