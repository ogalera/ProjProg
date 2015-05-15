/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica.algoritmica;
import logica.Punt;


/**
 *
 * @author Moises
 * @brief Conte la informacio heuristica necessaria per a poder implementar els algoritmes 
 * de backTracking i A*. 
 */
public class Casella implements Comparable<Casella> {
    private final Punt posicio;
    private Casella pare;
    private int distanciaAlObjectiu;
    private int profunditat;
    private boolean processat;
    
    public Casella(Punt p){
        posicio = p;
        distanciaAlObjectiu= 0;
        profunditat = 0;
        pare =null;
        processat = false;
    }
    
    public void setParent(Casella _pare){
        if (_pare != null)profunditat = _pare.profunditat + 1;
        this.pare = _pare;
    }
    
    public Casella getParent(){
        return pare;
    }
    
    public int getProfunditat(){
        return profunditat;
    }
    
    public Punt obtenirPunt(){
        return posicio;
    }
    
    public void afegirDistanciaAlObjectiu(int n){
        distanciaAlObjectiu = n;
    }
    public int obtenirDistanciaAlObjectiu(){
        return distanciaAlObjectiu;
    }
    public void processat(){
        processat = true;
    }
    public void noProcessat(){
        processat = false;
    }
    public boolean haEstatProcessat(){
        return processat;
    }
    public void reset(){
        distanciaAlObjectiu= 0;
        profunditat = 0;
        pare =null;
        processat = false;
        
    }

    @Override
    public int compareTo(Casella o) {
        //Es mesuraran per cost. cost = distancia al objectiu + profunditat
        int cost = distanciaAlObjectiu + profunditat;
        int costO = o.distanciaAlObjectiu + o.profunditat;
        if  (cost < costO ) return -1;
        else if (cost > costO )return 1;
        else return 0;
    }
    
    @Override
    public boolean equals(Object o){
        //Son iguals si les coordenades son les mateixes
        if(o == null || !(o instanceof Casella)) return false;
        Casella c = (Casella)o;
        return this.posicio.equals(c.posicio);
    }
    
}
