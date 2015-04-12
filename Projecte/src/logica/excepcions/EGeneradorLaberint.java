package logica.excepcions;

/**
 * @author oscar
 * DECLARACIÓ DE INTENCIONS DE LA EXCEPCIÓ
 * Llencarem aquest tipus d'excepció quan hi hagui algún problema en el procés
 * de generar laberints;
 */
public class EGeneradorLaberint extends RuntimeException{
    public EGeneradorLaberint(String missatge){
        super(missatge);
    }
}
