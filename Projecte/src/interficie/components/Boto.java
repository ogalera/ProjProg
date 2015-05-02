/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interficie.components;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.RoundRectangle2D;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
/**
 *
 * @author Moises
 */
public class Boto extends JButton{
    
    private Color colorFons ;
    private Color colorHombra;
    private Color colorContorn;
    
     public Boto() {
        aplicaColors();
        aplicaListener();
        setOpaque(false);
        setContentAreaFilled(false);
        setForeground(Color.WHITE);
        setFocusPainted(false);
        setBorderPainted(false);
    }
     public Boto(String text) {
        super(text);
        aplicaColors();
        aplicaListener();
        setOpaque(false);
        setContentAreaFilled(false);
        setForeground(Color.WHITE);
        setFocusPainted(false);
        setBorderPainted(false);
    }
     public Boto(ImageIcon imtg) {
        super(imtg);
        aplicaColors();
        aplicaListener();
        setOpaque(false);
        setContentAreaFilled(false);
        setForeground(Color.WHITE);
        setFocusPainted(false);
        setBorderPainted(false);
    }

    protected void paintComponent(Graphics g) {
        Color c1,c2,c3;
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        ButtonModel m = getModel();

         Paint oldPaint = g2.getPaint();
        if (m.isArmed()){
           c2=colorFons.darker();
           c1=colorHombra.darker();
           c3=colorContorn;
        }else{
           c1=colorFons.darker();
           c2=colorHombra.darker();
           c3=colorContorn.brighter();
        }
        if (!m.isEnabled()){
           c2=colorFons.brighter();
           c1=colorHombra.brighter();
           c3=colorContorn.darker();
        }
          RoundRectangle2D.Float r2d = new RoundRectangle2D.Float(
                    0,0,getWidth(),getHeight()-1,20,20);
            g2.clip(r2d);
            g2.setPaint(new GradientPaint(0.0f, 0.0f, c1,
                    0.0f, getHeight(), c2));
            g2.fillRect(0,0,getWidth(),getHeight());

            g2.setStroke(new BasicStroke(4f));
            g2.setPaint(new GradientPaint(0.0f, 0.0f, c3,
                    0.0f, getHeight(), c3));
            g2.drawRoundRect(0, 0, getWidth()-2 , getHeight() -2, 18, 18);

        g2.setPaint(oldPaint);
        super.paintComponent(g);
    }
    private void aplicaColors(){
        colorFons = new Color(0x666f7f);
        colorHombra = new Color(0x262d3d);
        colorContorn = new Color(0x262d3d);
    }
    private void aplicaListener(){
        addMouseListener(new MouseAdapter(){
            public void mouseEntered(MouseEvent evt){
                colorContorn = new Color(0xc2c02a);
            }
            public void mouseExited(MouseEvent evt){
                colorContorn =new Color(0x262d3d);
            }
        });
    }
   

    
    

    
    
}
