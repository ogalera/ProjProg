/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica.historic_moviments;

import logica.enumeracions.EDireccio;
import logica.excepcions.EHistoricBuit;

/**
 * @author oscar
 * DECLARACIÓ D'INTENCIONS DE LA CLASSE
 * Representació del historic de moviments realitzat per un personatge en el laberint
 */
public class HistoricMoviments {
    private final Pila<EDireccio> pila;
    
    public HistoricMoviments(){
        pila = new Pila<>();
    }
    
    public void afegirMoviment(EDireccio nouMoviment){
        pila.afegir(nouMoviment);
    }
    
    public EDireccio eliminarMoviment(){
        if(this.esBuida()) throw new EHistoricBuit();
        EDireccio ultimMoviment = pila.desenpilar();
        return ultimMoviment;
    }
    
    public EDireccio obtenirUltimMoviment(){
        return pila.cim();
    }
    
    public boolean esBuida(){
        return pila.esBuida();
    }
}
