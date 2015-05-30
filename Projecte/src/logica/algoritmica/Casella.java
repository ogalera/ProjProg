
package logica.algoritmica;
import logica.Punt;


/**
 *
 * @author Moises
 * @brief Conté la informació heuristica necessaria per a poder implementar els algoritmes 
 * de backTracking i AStar.  La Heuristica utilitzada en el algorisme de AStar es:
 * F = profunditat + distanciaAlObjectiu (on distanciaAlObjectiu, es la distancia Manhattan 
 * entre dos punts dintre de un Laberint format per cel·les)
 */
public class Casella implements Comparable<Casella> {
    private final Punt posicio;/*!< Guarda les coordenades que corresponen a una posicio en la taula de EElements de un Objecte tipus Laberint*/
    private Casella pare;/*!< En el algorisme de AStar s'utilitza per referenciar al predecessor d'una casella en la cerca del cami minim.*/
    private int distanciaAlObjectiu;/*!< Distancia Manhattan entre dos punts dintre de un Laberint format per cel·les.*/
    private int profunditat;/*!< En el algorisme de AStar s'utilitza per guardar en cuants pasos hem arrivat a la Casella actual.*/
    private boolean processat;/*!< Guarda l'estat si la casella ha estat processada o no.*/
    /**
     *@invariant: posicio != null.
     */
    
    
    public Casella(Punt p){
        posicio = p;
        distanciaAlObjectiu= 0;
        profunditat = 0;
        pare =null;
        processat = false;
    }
    
    
    /**
     * 
     * @brief Assigna un pare al objecte actual.
     * @pre _pare != null
     * @post Objecte actual te un punter a _pare.
     */
    public void assignarParent(Casella _pare){
        if (_pare != null)profunditat = _pare.profunditat + 1;
        this.pare = _pare;
    }
    
    /**
     * 
     * @brief Retorna el pare del objecte actual 
     */
    public Casella obtenirParent(){
        return pare;
    }
    
    /**
     * @brief Assigna la distancia al objecte actual.
     */
    public void assignarDistanciaAlObjectiu(int n){
        distanciaAlObjectiu = n;
    }
    
    /**
     * 
     * @brief Retorna la distancia del objecte actual 
     */
    public int obtenirDistanciaAlObjectiu(){
        return distanciaAlObjectiu;
    }
    /**
     * 
     * @brief Retorna la profunditat del objecte acutal.
     */
    public int obtenirProfunditat(){
        return profunditat;
    }
    
    /**
     * 
     * @brief Retorna el Punt del objecte actual. 
     */
    public Punt obtenirPunt(){
        return posicio;
    }
    
    /**
     * @brief Marca el objecte actual com a processat
     * @post Objecte Casella processat
     */
    public void processat(){
        processat = true;
    }
    
    /**
     * @brief Marca el objecte actual com a no processat
     * @post Objecte Casella no processat
     */
    public void noProcessat(){
        processat = false;
    }
    
    /**
     * @brief Retorna cert si l'objecte actual ha estat processat, altrament fals.
     */
    public boolean haEstatProcessat(){
        return processat;
    }
    
    /**
     * @brief Es reinicien els valors heuristics.
     * @post Els valors heurisitics reiniciats als valors inicials.
     */
    public void reset(){
        distanciaAlObjectiu= 0;
        profunditat = 0;
        pare =null;
        processat = false;
        
    }
    /**
     * @brief Diu si objecte actual es mes petit que o.
     * @pre o != null.
     * @return Objecte actual es mes gran, si la seva funcio heurisitca es mes gran que la funció heuristica de o.
     */
    @Override
    public int compareTo(Casella o) {
        //Es mesuraran per cost. cost = distancia al objectiu + profunditat
        int cost = distanciaAlObjectiu + profunditat;
        int costO = o.distanciaAlObjectiu + o.profunditat;
        if  (cost < costO ) return -1;
        else if (cost > costO )return 1;
        else return 0;
    }
    
    
    /**
     * @brief Diu si objecte actual es igual al objecte o.
     * @return Retorna cert si objecte actual i objecte o tenen la mateixa posicio.
     */
    @Override
    public boolean equals(Object o){
        //Son iguals si les coordenades son les mateixes
        if(o == null || !(o instanceof Casella)) return false;
        Casella c = (Casella)o;
        return this.posicio.equals(c.posicio);
    }
    
}
