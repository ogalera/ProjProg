package logica.historic_moviments;

/**
 * @author oscar
 * @brief
 * Estructura de dades de tipus FILO on
 * afegir té cost O(1)
 * cim té cost O(1)
 * desenpilar té cost O(1)
 * esBuida té cost O(1)
 * 
 * @invariant
 * mentre la pila estigui buida cim ha de ser null altrament no pot ser null.
 */
public class Pila<T> {
    private Node cim=null; /**<cim de la pila*/
    
    /**
     * @author oscar
     * @brief
     * Node de la pila compost per una dada de tipus T i un punter al seguent
     * element.
     */
    private class Node{
        final private T dada; /**<dada del node*/
        final private Node seguent;/**<punter al seguent element*/
        
        /**
         * @pre --
         * @post s'ha creat un nou node amb dada i seguent.
         */
        public Node(T dada, Node seguent){
            this.dada = dada;
            this.seguent = seguent;
        }
    };
    
    /**
     * @pre: --;
     * @post: dada està en el cim de la pila, COST O(1);
     */
    public void afegir(T dada){
        cim = new Node(dada, cim);
    }
    
    /**
     * @pre: --;
     * @post: em retornat el cim de la pila, COST O(1);
     */
    public T cim(){
        return cim.dada;
    }
    
    /**
     * @pre: la pila no és buida;
     * @post: em desenpilat el cim de la pila, COST O(1);
     */
    public T desenpilar(){
        T dada = cim.dada;
        cim = cim.seguent;
        return dada;
    }
    
    /**
     * @pre: --;
     * @post: diu si la pila està buida, COST O(1);
     */
    public boolean esBuida(){
        return this.cim == null;
    }
}
