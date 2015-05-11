/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import logica.laberints.Laberint;
import logica.enumeracions.EDireccio;
import logica.enumeracions.EElement;
import logica.historic_moviments.HistoricMoviments;
import logica.log.Log;
import logica.algoritmica.GestorCamins;
/**
 *
 * @author oscar
 */
public class Item extends ItemMovible {
    private final EElement tipusElement;
    private EElement elementTrapitjat;
    private final GestorCamins gestorCami;
    private HistoricMoviments ruta;
    private final Log log;
    
    public Item(Partida partida, EElement tipusElement, EElement elementTrapitjat, Laberint laberint, Punt inici){
        super(partida, tipusElement.obtenirImatge(), laberint, inici);
        log = Log.getInstance(Item.class);
        this.tipusElement = tipusElement;
        this.elementTrapitjat = elementTrapitjat;
        gestorCami = new GestorCamins(laberint);
        super.iniciarItemMovible();
    }

    @Override
    public EDireccio calcularMoviment(){
//        Punt posDesplasada = super.posicio.generarPuntDesplasat(EDireccio.ESQUERRA);
//        if(laberint.posicioValida(posDesplasada) && laberint.obtenirElement(posDesplasada) != EElement.PARET){
//            return EDireccio.ESQUERRA;
//        }
//        return EDireccio.QUIET;
        
        if (seguentMoviment == null)return EDireccio.QUIET;
        if (posicio == null)return EDireccio.QUIET;
        if (ruta == null || ruta.esBuida()){
            Punt posicioPacman = partida.obtenirPuntPacman();
            Punt posicioEnemic = partida.obtenirPuntEnemic();
            Punt puntAFugir = posicioEnemic;
           
           
            long tempsInici = System.currentTimeMillis();
            int distanciaPacman = gestorCami.trobarCamiMinim(posicio, posicioPacman).size();
            int distanciaEnemic = gestorCami.trobarCamiMinim(posicio, posicioEnemic).size();
            long tempsFinal = System.currentTimeMillis();
            log.afegirDebug("Temps que triga l'algorisme A* a trobar els enemics: " + (tempsFinal - tempsInici) + " ms");

            if (distanciaPacman < distanciaEnemic)puntAFugir = posicioPacman;

            ruta = gestorCami.trobarCamiMaximitzarDist(posicio, puntAFugir);
            tempsFinal = System.currentTimeMillis();
            log.afegirDebug("Temps total que triga el Item a recalcular una ruta: " + (tempsFinal - tempsInici) + " ms");
            
            
        }
        return ruta.obtenirUltimMoviment();
    }
    
    @Override
    public String nomItemMovible(){
        return "Item "+tipusElement;
    }

    @Override
    public EElement realitzarMoviment() {
        if(seguentMoviment != EDireccio.QUIET){
            System.out.print("Estic a la posicio: "+ posicio+ " i haig d'anar a la pos " + posicio.generarPuntDesplasat(seguentMoviment));
            elementTrapitjat = laberint.moureItem(posicio, seguentMoviment, elementTrapitjat);
            posicio = posicio.generarPuntDesplasat(seguentMoviment);
            return elementTrapitjat;
        }
        return null;
    }
}
