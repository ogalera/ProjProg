package interficie.components;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.awt.Image;
/**
 * @author Moises
 * @Class Marcador
 * @brief Encarregada de mostrar la imatge i la puntuacio de un Personatge durant una partida.
 * 
 * @invariant: imatge != null
 */
public class Marcador extends JPanel {
    JLabel imatge;
    JLabel puntuacio;
    
    /**
     * @brief Constructor de Marcador
     * @post Marcador preparat per mostrar per pantalla amb imatge img i puntuacio 0000
     */
    public Marcador(ImageIcon img){
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        inicialitzaComponents(img);
        this.setBackground(Color.YELLOW);
    }
    
    
    /**
     * @brief Modifica el marcador de punts de Personatge.
     * @post Es mostra per pantalla el valor de novaPuntuacio.
     */
    public void modificaPuntuacio(int novaPuntuacio){
        if (novaPuntuacio < 10) puntuacio.setText("000" + novaPuntuacio);
        else if (novaPuntuacio < 100) puntuacio.setText("00" + novaPuntuacio);
        else if (novaPuntuacio < 1000)puntuacio.setText("0" + novaPuntuacio); 
        else puntuacio.setText(""+ novaPuntuacio);
    }
    
    /**
     * @brief Inicialitza els components de Marcador
     * @param img Imatge que representa al personatge.
     * @post Es mostra per pantalla la imatge del personatge i la seva puntuacio
     */
    private void inicialitzaComponents(ImageIcon img){
        creaImatge(img);
        creaPuntuacio();
        add(imatge);
        add(puntuacio);
    }
    
    /**
     * @brief Construccio de la imatge que representa al personatge.
     * @param img Imatge que representa al personatge.
     * @post img es mostrat per pantalla,
     */
    private void creaImatge(ImageIcon img){
        imatge = new JLabel();
        imatge.setSize(100, 100);
        Icon iconaPac = new ImageIcon(img.getImage().getScaledInstance(imatge.getWidth(),imatge.getHeight(),Image.SCALE_DEFAULT));
        imatge.setIcon(iconaPac);
        imatge.validate();
        imatge.repaint();
    }
    
    /**
     * @brief Construccio del marcador que mostrara la puntuacio del personatge
     * @post puntuacio es mostrara per pantalla amb 0000
     */
    private void creaPuntuacio(){
        puntuacio = new JLabel("0000");
        puntuacio.setSize(60, 300);
        puntuacio.setFont( new Font("Consolas", Font.PLAIN, 50 ) );
        puntuacio.setHorizontalAlignment( JLabel.CENTER );
        puntuacio.setForeground( Color.YELLOW );
        puntuacio.setBackground( Color.BLACK );
        puntuacio.setOpaque(true);
        Border border = LineBorder.createGrayLineBorder();
        puntuacio.setBorder(border);
    }
    
    
    /**
     * @brief Mostra per pantalla la puntuacio p.
     * @post p es mostrat per pantalla.
     */
    public void canviarPuntuacio(int p){
        String pnt;
        if(p < 10){
            pnt="000"+p;
        }
        else if(p < 100){
            pnt="00"+p;
        }
        else if(p < 1000){
            pnt="0"+p;
        }
        else pnt = p+"";
        this.puntuacio.setText(pnt);
    }
}
