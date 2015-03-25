/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projecte;

import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author oscar
 */
public enum EnumElement {
    PACMAN(20, "res/pacman.png"),
    FANTASMA1(21, "res/fantasma1.png"), 
    FANTASMA2(22, "res/fantasma2.png"), 
    FANTASMA3(23, "res/fantasma3.png"), 
    PARED(-1, "res/paret.png"), 
    MONEDA(1, "res/moneda.png"), 
    MONEDA_EXTRA(2, "res/moneda.png"), 
    RES(0, null),
    INDEFINIT(-2 , "res/indefinit.png");
    
    private int id;
    private ImageIcon imatge;
    
    private EnumElement(int id, String recurs){
        this.id = id;
        Log log = Log.getInstance(EnumElement.class);
        try{
            File f = new File(recurs);
            imatge = new ImageIcon(ImageIO.read(f));
        }
        catch(Exception ioe){
            log.afegirError("error al carregar l'imatge, missatge: "+ioe.getMessage());
        }
    }
    
    public ImageIcon getImatge(){
        return this.imatge;
    }
    
    public int getId(){
        return this.id;
    }
}
