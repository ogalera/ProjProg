/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import log.Log;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author oscar
 */
public enum EnumElement {
    PACMAN(20, false, "res/pacman.png"),
    FANTASMA1(21, true, "res/fantasma1.png"), 
    FANTASMA2(22, true, "res/fantasma2.png"), 
    FANTASMA3(23, true, "res/fantasma3.png"), 
    PARED(-1, false, "res/paret.png"), 
    MONEDA(1, false, "res/moneda.png"), 
    MONEDA_EXTRA(2, false, "res/moneda.png"), 
    RES(0, false, null),
    INDEFINIT(-2 , false, "res/indefinit.png");
    
    private int id;
    private ImageIcon imatge;
    private boolean esEnemic;
    
    private EnumElement(int id, boolean esEnemic, String recurs){
        this.id = id;
        this.esEnemic = esEnemic;
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
    
    public boolean esEnemic(){
        return this.esEnemic;
    }
}
