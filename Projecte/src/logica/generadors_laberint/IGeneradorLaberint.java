package logica.generadors_laberint;

import logica.enumeracions.EElement;

/**
 * @author oscar
 * DECLARACIÓ D'INTENCIONS DE L'INTERFICIE
 * Ens genera una capa d'abstracció alhora de crear un nou laberint;
 */
public interface IGeneradorLaberint {
    /**
     * @pre: --;
     * @post: em retornat un laberint quadrat i valid;
     * @return una matriu que ens representa un laberint valid;
     */
    public EElement [][] generarLaberint();
}
