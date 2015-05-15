package logica;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;
///De la llibreria Apache Commons Codec 1.10 per codificar/decodificar 
///el password del usuari;
import org.apache.commons.codec.binary.Base64;

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
        public static final int FREQUENCIA_ITEM_MOVIBLE = 125;
        public static final int TAN_PER_CENT_MONEDES_DOBLES = 10;
        public static final int TEMPS_EFECTES_ITEM_MILISEGONS = 15_000;
    }
    
    private static final Random random = new Random(System.currentTimeMillis());
    
    public static int obtenirValorAleatori(int max){
        return random.nextInt(max);
    }
    
    /**
     * @pre: cadena no és null;
     * @post: em retornat la cadena codificada;
     */
    public static String codificarCadena(String cadena){
        return Base64.encodeBase64String(cadena.getBytes());
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
    
    public static BufferedImage redimensionarImatge(BufferedImage originalImage, int tipus, int amplada, int altura) {
        BufferedImage resizedImage = new BufferedImage(amplada, altura, tipus);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, amplada, altura, null);
        g.dispose();
        return resizedImage;
    }
}
