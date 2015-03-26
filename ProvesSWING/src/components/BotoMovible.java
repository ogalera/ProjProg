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
import javax.swing.JLabel;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;

/**
 *
 * @author Moi
 */
public class BotoMovible extends JFrame implements KeyListener{
    
    private JLabel boto;
    private int x=250, y=250;
    private ImageIcon imatge;
    private ImageIcon amunt;
    private ImageIcon avall;
    private ImageIcon dreta;
    private ImageIcon esquerra;
    
    public BotoMovible(){
        
        this.setTitle("moviments");
        amunt=new ImageIcon("res/pacmanAmunt.png");
        avall=new ImageIcon("res/pacmanAvall.png");
        esquerra=new ImageIcon("res/pacmanDreta.png");
        dreta=new ImageIcon("res/pacmanEsquerra.png");
        
        boto=new JLabel();
        boto.setIcon(imatge);
        
        add(boto);
        boto.setBounds(x, y, 50, 50);
        
        addKeyListener(this);
        this.setVisible(true);
        this.setSize(500,500);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
    }

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
                    boto.setIcon(amunt);
                    boto.setBounds(x, y, 50, 50);
                    boto.repaint();
                }
                else if (teclaPremuda == 40){
                    System.out.println("Avall");
                    y+=15;
                    boto.setIcon(avall);
                    boto.setBounds(x, y, 50, 50);
                    boto.repaint();
                }
                else if (teclaPremuda == 37){
                    System.out.println("Esquerra");
                    x-=15;
                    boto.setIcon(dreta);
                    boto.setBounds(x, y, 50, 50);
                    boto.repaint();
                }
                else if (teclaPremuda == 39){
                    System.out.println("Dreta");
                    x+=15;
                    boto.setIcon(esquerra);
                    boto.setBounds(x, y, 50, 50);
                    boto.repaint();
                }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        int teclaPremuda= e.getKeyCode();
                System.out.println(teclaPremuda);
                if (teclaPremuda == 38){
                    System.out.println("Amunt");
                    y-=15;
                    boto.setIcon(amunt);
                    boto.setBounds(x, y, 50, 50);
                    boto.repaint();
                }
                else if (teclaPremuda == 40){
                    System.out.println("Avall");
                    y+=15;
                    boto.setIcon(avall);
                    boto.setBounds(x, y, 50, 50);
                    boto.repaint();
                }
                else if (teclaPremuda == 37){
                    System.out.println("Esquerra");
                    x-=15;
                    boto.setIcon(dreta);
                    boto.setBounds(x, y, 50, 50);
                    boto.repaint();
                }
                else if (teclaPremuda == 39){
                    System.out.println("Dreta");
                    x+=15;
                    boto.setIcon(esquerra);
                    boto.setBounds(x, y, 50, 50);
                    boto.repaint();
                }
    }
    
}
