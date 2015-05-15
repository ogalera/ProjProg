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
 * @brief Estructura de dades ordenades
 */
public class LlistaOrdenadaCandidats {
    private final ArrayList<Casella> llista;//Necessitare accessos per posicio
    
    public LlistaOrdenadaCandidats(){
        llista = new ArrayList<>();
    }
    
    public Casella obtenirPrimer(){
        return llista.get(0);
    }
    public Casella obtenirUltim(){
        int index = llista.size() -1;
        return llista.get(index);
    }
    
    public void clear(){
        llista.clear();
    }
    
    public void afegir(Casella c){
        llista.add(c);
        Collections.sort(llista);
    }
    
    public void elimina(Casella c){
        llista.remove(c);
    }
    
    public boolean esBuida(){
        return llista.isEmpty();
    }
    
    public boolean conteElement(Casella c){
        return llista.contains(c);
    }
    
    public Casella getElement(Casella c){
        int i = llista.indexOf(c);
        return llista.get(i);
    }
    public int size(){
        return llista.size();
    }
}
