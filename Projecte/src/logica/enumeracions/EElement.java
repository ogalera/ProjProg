/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica.enumeracions;

import java.awt.Image;
import logica.log.Log;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import logica.Utils;
/**
 *
 * @author oscar
 */
public enum EElement {
    PACMAN(20, false, "res/pacmanD1.png", 'P'),
    FANTASMA1(21, true, "res/fantasma1.png", 'A'), 
    FANTASMA2(22, true, "res/fantasma2.png", 'B'), 
    FANTASMA3(23, true, "res/fantasma3.png", 'C'), 
    PARET(-1, false, "res/paret.png", 'X'), 
    MONEDA(1, false, "res/moneda.png", 'O'), 
    MONEDA_EXTRA(2, false, "res/moneda_extra.png", 'E'), 
    RES(0, false, null, 'R'),
    PATINS(3, false, "res/patins.png", 'U'),
    MONEDES_X2(4, false, "res/monedes_x2.png", 'M'),
    MONGETA(5, false, "res/mongeta.png", 'N'),
    INDEFINIT(-2 , false, "res/indefinit.png", 'I'),
    SORTIDA(15, false, "res/sortida.png", 'S');
    
    private final int id;
    private ImageIcon imatge;
    private boolean esEnemic;
    private char lletraRepresentacio;
    
    private EElement(int id, boolean esEnemic, String recurs, char lletraRepresentacio){
        this.id = id;
        this.lletraRepresentacio = lletraRepresentacio;
        this.esEnemic = esEnemic;
        Log log = Log.getInstance(EElement.class);
        try{
            File f = new File(recurs);
            imatge = new ImageIcon(ImageIO.read(f));
        }
        catch(Exception ioe){
            log.afegirError("error al carregar l'imatge, missatge: "+ioe.getMessage());
        }
    }
    
    public char obtenirLletraRepresentacio(){
        return this.lletraRepresentacio;
    }
    
    public ImageIcon obtenirImatge(){
        return this.imatge;
    }
    
    public final int obtenirId(){
        return this.id;
    }
    
    public boolean esEnemic(){
        return this.esEnemic;
    }
    
//    public static void redimensionarImatges(int px){
//        for (EElement e : EElement.values()){
//            ImageIcon img = e.obtenirImatge();
//            if(img != null) e.establirImatge(Utils.redimensionarImatge(img, px));
//        }
//    }
    
    private void establirImatge(Image img){
        this.imatge = new ImageIcon(img);
    }
    
    public static EElement buscarElementPerId(int id){
        EElement element;
        if(id == MONEDA.obtenirId()) element = MONEDA;
        else if(id == MONEDA_EXTRA.obtenirId()) element = MONEDA_EXTRA;
        else if(id == PARET.obtenirId()) element = PARET;
        else if(id == FANTASMA1.obtenirId()) element = FANTASMA1;
        else if(id == FANTASMA2.obtenirId()) element = FANTASMA2;
        else if(id == FANTASMA3.obtenirId()) element = FANTASMA3;
        else if(id == PACMAN.obtenirId()) element = PACMAN;
        else if(id == RES.obtenirId()) element = RES;
        else if(id == SORTIDA.obtenirId()) element = SORTIDA;
        else element = INDEFINIT;
        return element;
    }
    
    @Override
    public String toString(){
        return  this.name();
    }
}
