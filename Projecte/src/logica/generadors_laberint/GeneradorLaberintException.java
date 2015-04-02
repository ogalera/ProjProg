package logica.generadors_laberint;

/**
 * @author oscar
 * DECLARACIÓ DE INTENCIONS DE LA EXCEPCIÓ
 * Llencarem aquest tipus d'excepció quan hi hagui algún problema en el procés
 * de generar laberints;
 */
public class GeneradorLaberintException extends RuntimeException{
    public GeneradorLaberintException(String missatge){
        super(missatge);
    }
}
