
package interficie;

import java.awt.Dimension;
import java.awt.event.KeyListener;
import javax.swing.ImageIcon;
import logica.Punt;
import logica.enumeracions.EDireccio;
import logica.enumeracions.EElement;
import logica.laberints.Laberint;

/**
 *
 * @author Moises
 * @brief Descriu els metodes que necessiten els objectes Laberint
 * per a mostrar-se per pantalla.
 * @details Laberint es representa gràficament per cel·les, on cada cel·la representa un posció
 * de la matriu bidimensional que conté el Objecte Laberint.
 */
public interface IPintadorLaberint extends KeyListener{
    
    
    /**
     * @brief Assignacio de controlador de teclat.
     * @pre controlador != null.
     * @post La representacio Grafica te assignat un controlador de teclat.
     */
    public void assignarControladorTeclat(KeyListener controlador);
    
    /**
     * @brief Mostra per pantalla el desplaçament de un objecte Personatge.
     * @param pOrigen Punt on es troba el Personatge
     * @param direccio Direcció a on es mou el Personatge
     * @param imatge Representació grafica del personatge
     * @pre pOrigen != null.
     * pOrigen es un Punt vàlid. 
     * El Punt generat per pOrigen + desplaçament és vàlid.
     * direcció != null && direcció != EDireccio.QUIET.
     * imatge != null.
     * @post pOrigen no conté cap imatge.
     *       La cel·la corresponent a la direccio desde pOrigen conté imatge
     */
    public void pintarMovimentPersonatge(Punt pOrigen, EDireccio direccio, ImageIcon imatge);
    
    
    /**
     * @brief Mostra per pantalla el desplaçament de un objecte Item.
     * @details En el moviment del Item s'ha de tindre en compte el EElement que contenia
     * la cel·la abans de que estigues el Item. Aques EElement és el element a restaurar.
     * @param pOrigen Punt desde on s'ha efectuat el moviment
     * @param direccio Direcció a on es mou el Item
     * @param imatge Representació grafica de EElement a restaurar
     * @pre pOrigen != null.
     * pOrigen es un Punt vàlid. 
     * El Punt generat per pOrigen + desplaçament és vàlid.
     * direcció != null && direcció != EDireccio.QUIET.
     * imatge != null.
     * @post La cel·la corresponent a la direccio desde pOrigen conté la imatge de pOrigen.
     * pOrigen conté la imatge del EElement a restaurar
     *       
     */
    public void pintarMovimentItem(Punt pOrigen, EDireccio direccio, ImageIcon imatge);
    
    
    /**
     *@brief Mostra per pantalla un item dintre del Laberint
     *@post La cel·la amb coordenades pNouItem conte la imatge de item
     */
    public void pintarNouItem(Punt pNouItem, EElement item);
    
    
    /**
     * @brief Construcció de la interficie grafica que representa graficament laberint
     * @param laberint Laberint a representar
     * @pre laberint != null
     * @post Representacio grafica preperada per mostrar per pantalla
     */
    public void pintarLaberint(Laberint laberint);
    
    
    /**
     * @brief retorna la mida amb la que es pinten les imatges que representen els EElements d'un Laberint.
     * @return Mida de les representacions grafiques de EElements.
     */
    public Dimension obtenirMidaImatge();
    
    
    /**
     * @brief Mostra la representacio de EElement.SORTIDA en les coordenades corresponents a pSortida
     * @post Representació de EElement.SORTIDA es veu per pantalla.
     */
    public void pintarSortida(Punt pSortida);
}
