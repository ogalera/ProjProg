
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
 * @brief Cronometre amb representacio gràfica amb 00:00:000 minuts:segons:milesimes
 */
public class Crono extends JPanel implements Runnable {
    JLabel temps;
    Thread fil;
    boolean cronoActiu;
    
    /**
     * @brief Constructor
     * @post Crono preperat per mostrar per pantalla i iniciar.
     */
    public Crono(){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        creaVisualitzacioTemps();
        
    }
    
    /**
     * @brief Construccio de la representació grafica de un cronometre
     * @post Crono preperat per mostrar per pantalla.
     */
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
    
    /**
     * @brief El crono inicia el seu compte
     * @post Cronometre iniciat
     */
    public void iniciarCrono(){
        cronoActiu = true;
        fil = new Thread(this);
        fil.start();
    }
    
    
    /**
     * @brief Crono para el seu compte
     * @post Cronometre parat.
     */
    public void pararCrono(){
        cronoActiu = false;
    }

    
    /**
     * @brief Fil que executa l'evolució d'un cronometre
     */
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
