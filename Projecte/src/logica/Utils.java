package logica;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * @author oscar
 * Classe de utilitats per tot el projecte;
 */
public class Utils{
    public static class Constants{
        public static final int VALOR_MONEDA_NORMAL = 10;
        public static final int VALOR_MONEDA_EXTRA = 30;
        public static final int MIDA_IMATGE = 100;
        public static final String rutaImatgeDefecteUsuari = "res/imatge_perfil.png";
        public static final int PORT = 9988;
        public static final int FREQUENCIA_PACMAN_EN_MILISEGONS = 500;
        public static final int FREQUENCIA_FANTASMA_EN_MILISEGONS = 500;
    }
    
    /**
     * @pre: cadena no és null;
     * @post: em retornat la cadena codificada;
     */
    public static String codificarCadena(String cadena){
        return Base64.encode(cadena.getBytes());
    }
    
    /**
     * @pre: cadena no és null;
     * @post: em retornat la cadena decodificada;
     */
    public static String decodiciarCadena(String cadena){
        return new String(Base64.decode(cadena));
    }
    
    /**
     * @pre: --;
     * @post: em retornat la hora del sistema en format HH:MI:ss
     */
    public static String obtenirHoraSistema(){
        return Utils.obtenirMomentEnFormatHoraMinutsSegons(System.currentTimeMillis());
    }
    
    /**
     * @pre: --;
     * @post: em retornat moment en format HH:MI:ss
     */
    public static String obtenirMomentEnFormatHoraMinutsSegons(final long moment){
        String resultat = "";
        long hores = (moment/1_000/60/60)%24;
        resultat += hores < 10 ? "0"+hores: hores;
        resultat+=":";
        long minuts = (moment/1_000/60)%60;
        resultat += minuts < 10 ? "0"+minuts: minuts;
        resultat+=":";
        long segons = (moment/1_000)%60;
        resultat += segons < 10 ? "0"+segons: segons;
        return resultat;
    }
    
    public static BufferedImage resizeImage(BufferedImage originalImage, int tipus, int amplada, int altura) {
        BufferedImage resizedImage = new BufferedImage(amplada, altura, tipus);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, amplada, altura, null);
        g.dispose();
        return resizedImage;
    }
}
