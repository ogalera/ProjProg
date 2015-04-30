/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interficie.components;
import javax.swing.BoxLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

/**
 * @author Moises
 * @Class Crono
 * @brief Mostra per pantalla un cronometre iniciat a 00:00:000 minuts:segons:milesimes
 */
public class Crono extends JPanel implements Runnable {
    JLabel temps;
    Thread fil;
    boolean cronoActiu;
    
    public Crono(){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        creaVisualitzacioTemps();
        
    }
    
    private void creaVisualitzacioTemps(){
        temps = new JLabel("00:00:000");
        temps.setFont( new Font("Consolas", Font.PLAIN, 50 ) );
        temps.setHorizontalAlignment( JLabel.CENTER );
        temps.setForeground( Color.YELLOW );
        temps.setBackground( Color.BLACK );
        temps.setOpaque(true);
        Border border = LineBorder.createGrayLineBorder();
        temps.setBorder(border);
        add( temps);
    }
    
    
    public void iniciarCrono(){
        cronoActiu = true;
        fil = new Thread(this);
        fil.start();
    }
    
    public void pararCrono(){
        cronoActiu = false;
    }

    @Override
    public void run() {
        int minuts = 0, segons = 0, milesimes = 0;
        String min="", seg="", mil="";
        try{
            while (cronoActiu){
                Thread.sleep(4);
                milesimes += 4;
                
                if (milesimes == 1000){
                    milesimes = 0;
                    segons += 1;
                    if (segons == 60){
                        segons = 0;
                        minuts++;
                    }
                }
                
                if (minuts < 10) min = "0" + minuts;
                else min = ""+minuts;
                
                if (segons < 10) seg = "0" + segons;
                else seg = ""+segons;
                
                if (milesimes < 10)mil = "00"+milesimes;
                else if (milesimes < 100)  mil = "0"+milesimes;
                else mil = ""+milesimes;
                
                temps.setText(min+":"+seg+":"+mil);
            }
        }
        catch (Exception e){
            temps.setText("00:00:000");
        }
    }
    
}
