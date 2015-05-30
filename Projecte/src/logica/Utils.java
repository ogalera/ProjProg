package logica;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.filechooser.FileFilter;
///De la llibreria Apache Commons Codec 1.10 per codificar/decodificar 
///el password del usuari;
import org.apache.commons.codec.binary.Base64;

/**
 * @author oscar
 * @brief
 * Modul funcional amb un conjunt d'operacions que s'utilitzen
 * a nivell de tota l'aplicació
 */
public class Utils{
    public static class Constants{
        public static final float LLINDAR_MONEDES = 0.7f; /**<TAN_PER_U de monedes que ha de tenir un laberint autogenerat*/
        public static final int TAN_X_CENT_MONEDES_ITEM = 10; /**< % de monedes que has de obtenir per que aparegui un item a la partida*/
        public static final int TAN_X_CENT_MONEDES_EXTRA = 10; /**< % de monedes simples que hi ha de haver per cada moneda doble*/
        public static final int VALOR_MONEDA_NORMAL = 10; /**<quantitat de punts que aporta una moneda simple*/
        public static final int VALOR_MONEDA_EXTRA = 30; /**<quantitat de punts que aporta una moneda extra*/
        public static final int MIDA_IMATGE = 100; /**<Mida de l'imatge de perfil*/
        public static final String PATH_IMATGES = "res/imatges/"; /**<Ruta on es guarden les imatges de l'aplicació*/
        public static final String PATH_BD = "res/raw/"; /**<Ruta del fitxer de la B.D.*/
        public static final String PATH_SONS = "res/sons"; /**<Ruta dels fitxers de so*/
        public static final String rutaImatgeDefecteUsuari = "res/imatges/imatge_perfil.png"; /**<Ruta de l'imatge per defecte del usuari*/
        public static final int PORT = 9988; /**<Port d'escolta per connectar dispositius móbils NO ACABAT!*/
        public static final int FREQUENCIA_ITEM = 400; /**<Cada quants milisegons es mou l'item*/
        public static final int FREQUENCIA_PERSONATGE = 250; /**<Cada quants milisegons es mou el personatge*/
        public static final int TEMPS_EFECTES_ITEM_MILISEGONS = 15_000; /**<Quants milisegons duran els efectes de un item*/
        public static final int MINIM_COSTAT_LABERINT = 5; /**<Mida mínima que ha de fer el costat de un laberint*/
        public static final int TOP_N_DEL_RANKING = 10; /**<Nombre de usuaris que formen el ranking de punts*/
    }
    
    private static final Random random = new Random(System.currentTimeMillis()); /**<Llavor per generar valors aleatoris 
                                                                                    a nivell de tota l'aplicació*/
    
    /**
     * @pre max > 0
     * @post es retorna un valor pseudoaleatori dins del rang [0, max)
     */
    public static int obtenirValorAleatori(int max){
        return random.nextInt(max);
    }
    
    /**
     * @pre cadena no és null;
     * @post em retornat la cadena codificada;
     */
    public static String codificarCadena(String cadena){
        return Base64.encodeBase64String(cadena.getBytes());
    }
    
    /**
     * @pre --;
     * @post em retornat l'hora del sistema en format HH:MI:ss
     */
    public static String obtenirHoraSistema(){
        return Utils.obtenirMomentEnFormatHoraMinutsSegons(System.currentTimeMillis());
    }
    
    /**
     * @pre --;
     * @post em retornat moment en format HH:MI:ss
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
    
    /**
     * @pre px > 0;
     * @post em retornat l'imatge redimensionada a px x px
     */
    public static Image redimensionarImatge(ImageIcon imatge, int px){
        return imatge.getImage().getScaledInstance(px, px, Image.SCALE_DEFAULT);
    }
    
    /**
     * @pre originalImage és un buffer de una imatge valida.
     * @post em retornat un buffer amb l'imatge redimensionada.
     */
    public static BufferedImage redimensionarImatge(BufferedImage originalImage,
                                                        int tipus, 
                                                        int amplada, 
                                                        int altura) {
        BufferedImage resizedImage = new BufferedImage(amplada, altura, tipus);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, amplada, altura, null);
        g.dispose();
        return resizedImage;
    }
    
    /**
     * @autor Oscar
     * @brief 
     * Classe per filtrar fitxers per extensió.
     * Al crear l'objecte s'especifica l'extensió dels fitxers per la qual es filtrarà
     */
    public static class FiltreExtensio extends FileFilter{
        private final String extensio;/**<extensió a filtrar*/
        
        /**
         * @pre --
         * @post s'ha creat un nou filtre per extensió.
         */
        public FiltreExtensio(String extensio){
            this.extensio = extensio;
        }

        @Override
        public boolean accept(File f) {
            ///El fitxer serà acceptable si acaba amb extensió.
            return f.getName().endsWith(extensio);
        }

        @Override
        public String getDescription() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }
}
