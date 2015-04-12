package logica;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

/**
 * @author oscar
 * Classe de utilitats per tot el projecte;
 */
public class Utils {
    
    /**
     * @pre: cadena no és null;
     * @post: em retornat la cadena codificada;
     * @param cadena: a codificar;
     * @return cadena codificada;
     */
    public static String codificarCadena(String cadena){
        return Base64.encode(cadena.getBytes());
    }
    
    /**
     * @pre: cadena no és null;
     * @post: em retornat la cadena decodificada;
     * @param cadena: a decodificar;
     * @return cadena decodificada;
     */
    public static String decodiciarCadena(String cadena){
        return new String(Base64.decode(cadena));
    }
    
    /**
     * @pre: --;
     * @post: em retornat la hora del sistema en format HH:MI:ss
     * @return: hora del sistema en format HH:MI:ss
     */
    public static String obtenirHoraSistema(){
        return Utils.obtenirMomentEnFormatHoraMinutsSegons(System.currentTimeMillis());
    }
    
    /**
     * @pre: --;
     * @post: em retornat moment en format HH:MI:ss
     * @param moment: a formatar;
     * @return: moment en format HH:MI:ss
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
}
