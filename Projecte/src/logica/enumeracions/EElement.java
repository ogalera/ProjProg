package logica.enumeracions;

import java.awt.Image;
import logica.log.Log;
import javax.swing.ImageIcon;
import logica.Utils;
/**
 *
 * @author oscar
 * @brief Enumeracio dels diferents elements que conte un objecte del tipus
 * Laberint.
 */
public enum EElement {
    PACMAN(20, false, "res/imatges/pacmanD1.png", 'P'),
    FANTASMA1(21, true, "res/imatges/fantasma1.png", 'A'), 
    FANTASMA2(22, true, "res/imatges/fantasma2.png", 'B'), 
    FANTASMA3(23, true, "res/imatges/fantasma3.png", 'C'), 
    PARET(-1, false, "res/imatges/paret.png", 'X'), 
    MONEDA(1, false, "res/imatges/moneda.png", 'O'), 
    MONEDA_EXTRA(2, false, "res/imatges/moneda_extra.png", 'E'), 
    RES(0, false, null, 'R'),
    PATINS(3, false, "res/imatges/patins.png", 'U'),
    MONEDES_X2(4, false, "res/imatges/monedes_x2.png", 'M'),
    MONGETA(5, false, "res/imatges/mongeta.png", 'N'),
    INDEFINIT(-2 , false, "res/imatges/indefinit.png", 'I'),
    SORTIDA(15, false, "res/imatges/sortida.png", 'S');
    
    private final int id;/*!<identificador numeric de EElement. */
    private ImageIcon imatge;/*!<Imatge que identifica a EElement. */
    private boolean esEnemic;/*!<Diu si EELement es un FANTASMA */
    private char lletraRepresentacio;/*!<Lletra que representa a EElement. */
    
    private EElement(int id, boolean esEnemic, String recurs, char lletraRepresentacio){
        this.id = id;
        this.lletraRepresentacio = lletraRepresentacio;
        this.esEnemic = esEnemic;
        Log log = Log.getInstance(EElement.class);
        try{
            imatge = new ImageIcon(new ImageIcon(ClassLoader.getSystemResource(recurs)).getImage());
        }
        catch(Exception ioe){
            log.afegirError("error al carregar l'imatge, missatge: "+ioe.getMessage());
        }
    }
    
    /**
     * @brief Retorna el caracter que representa a EElement
     * @details S'utilitza per a mostrar el EElement en consola.
     * @return Carater que representa a EElement.
     */
    public char obtenirLletraRepresentacio(){
        return this.lletraRepresentacio;
    }
    
    
    /**
     * @brief Retorna la imatge que representa a EElement.
     */
    public ImageIcon obtenirImatge(){
        return this.imatge;
    }
    /**
     * @brief Retorna el identificador de EElement.
     * @details S'utilitza per identificar els EElements a l'hora de tractar
     * fitxers.
     */
    public final int obtenirId(){
        return this.id;
    }
    
    /**
     * @brief Diu si Objecte actual es un Fantasma.
     * @return Cert si Objecte actual es del tipus FANTASMA1, FANTASMA2, o FANTASMA3
     */
    public boolean esEnemic(){
        return this.esEnemic;
    }
    
    /**
     * @brief Redimensiona la imatge que te associada cada EElement
     * @pre px > 0.
     * @post imatges de EElements redimensionades a mida (px x px).
     */
    public static void redimensionarImatges(int px){
        for (EElement e : EElement.values()){
            ImageIcon img = e.obtenirImatge();
            if(img != null) e.establirImatge(Utils.redimensionarImatge(img, px));
        }
    }
    
    /**
     * @brief Estableix una imatge a EElement actual
     * @post La representació grafica de EElement actual és img.
     */
    private void establirImatge(Image img){
        this.imatge = new ImageIcon(img);
    }
    
    
    /**
     * @brief Retorna el EElement amb identificador = id.
     * @return EElement amb identificador = id.
     */
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
