package logica.historic_moviments;

import logica.enumeracions.EDireccio;
import logica.excepcions.EHistoricBuit;

/**
 * @author oscar
 * @brief
 * Historic de moviments de caràcter FILO
 */
public class HistoricMoviments {
    private final Pila<EDireccio> pila; /**<Estructura de dades de caràcter filo
                                            * on es van afegint els moviments que 
                                            * ha realitzat un personatge*/
    private int nElement; /**<Nombre d'elements que conté la pila */
    
    /**
     * @pre --;
     * @post s'ha creat un nou historic de moviments;
     */
    public HistoricMoviments(){
        pila = new Pila<>();
        nElement = 0;
    }
    
    /**
     * @pre --;
     * @post s'ha afegit un nou moviment al historic;
     */
    public void afegirMoviment(EDireccio nouMoviment){
        pila.afegir(nouMoviment);
        nElement ++;
    }
    
    /**
     * @pre l'historic no està buit;
     * @post s'ha retornat i eliminat l'últim element afegit al historic;
     */
    public EDireccio eliminarMoviment(){
        if(this.esBuida()) throw new EHistoricBuit();
        EDireccio ultimMoviment = pila.desenpilar();
        nElement--;
        return ultimMoviment;
    }
    
    /**
     * @pre l'historic no està buit;
     * @post s'ha retornat l'ultim moviment afegit;
     */
    public EDireccio obtenirUltimMoviment(){
        return pila.cim();
    }
    
    /**
     * @pre --;
     * @post diu si l'historic està buit;
     */
    public boolean esBuida(){
        return pila.esBuida();
    }
    
    /**
     * @pre --;
     * @post s'ha retornat el nombre d'elements que contè l'historic;
     */
    public int obtenirMida(){
        return nElement;
    }
}
