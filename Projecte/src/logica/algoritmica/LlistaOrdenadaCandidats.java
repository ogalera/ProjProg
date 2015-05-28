/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica.algoritmica;
import java.util.ArrayList;
import java.util.Collections;
/**
 *
 * @author Moises
 * @brief Estructura ordenada de Caselles (candidats) per a als algoritmes de Backtracking i AStar.
 */
public class LlistaOrdenadaCandidats {
    private final ArrayList<Casella> llista;//Son necessaris accesos per posicio, i un estructura ordenada.
    /**
     * @invariant llista != null;
     */
    
    
    
    public LlistaOrdenadaCandidats(){
        llista = new ArrayList<>();
    }
    
    
    /**
     * @brief Retorna la Casella de menys pes.
     * @pre LlistaOrdenadaCandidats no buida.
     * @return Retorna la primera Casella de LlistaOrdenadaCandidats.
     */
    public Casella obtenirPrimer(){
        return llista.get(0);
    }
    
    
    /**
     * @brief Retorna la Casella de mes pes.
     * @pre LlistaOrdenadaCandidats no buida.
     * @return Retorna la ultima Casella de LlistaOrdenadaCandidats
     */
    public Casella obtenirUltim(){
        int index = llista.size() -1;
        return llista.get(index);
    }
    
    
    /**
     * @brief Elimina totes les Caselles de LlistaOrdenadaCandidats.
     * @post LlistaOrdenadaCandidats buida.
     */
    public void clear(){
        llista.clear();
    }
    
    /**
     * @brief Insercio de c de forma ordenada a LlistaOrdenadaCandidats.
     * @post LlistaOrdenadaCandidats conté la Casella c.
     */
    public void afegir(Casella c){
        llista.add(c);
        Collections.sort(llista);
    }
    
    /**
     * @brief Elimina c de LlistaOrdenadaCandidats.
     * @post LlistaOrdenadaCandidats no conté c.
     */
    public void eliminar(Casella c){
        llista.remove(c);
    }
    
    /**
     * @brief Diu si LlistaOrdenadaCandidats no te cap element.
     * @return Retorna cert si LListaOrdenadaCandidats no conté cap Casella. Altrament retorna fals.
     */
    public boolean esBuida(){
        return llista.isEmpty();
    }

    /**
     * @brief Diu si LlistaOrdenadaCandidats conté la Casella c.
     * @return Retorna cert si c esta continguda en LlistaOrdenadaCandidats. Altrament retorna fals.
     */
    public boolean conteElement(Casella c){
        return llista.contains(c);
    }
    /**
     * @brief Diu el numero de objectes Casella continguts a LlistaOrdenadaCandidats.
     * @return Retorna el numero de objectes Casella continguts a LlistaOrdenadaCandidats.
     */
    public int mida(){
        return llista.size();
    }

}
