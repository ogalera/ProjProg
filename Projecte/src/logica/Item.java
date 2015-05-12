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
    private static final int DISTANCIA_PERILLOSA = 6;
    private boolean esticFugint;
    
    public Item(Partida partida, EElement tipusElement, EElement elementTrapitjat, Laberint laberint, Punt inici){
        super(partida, tipusElement.obtenirImatge(), laberint, inici);
        log = Log.getInstance(Item.class);
        this.tipusElement = tipusElement;
        this.elementTrapitjat = elementTrapitjat;
        gestorCami = new GestorCamins(laberint);
        esticFugint = false;
        super.iniciarItemMovible();
    }

    @Override
    public EDireccio calcularMoviment(){
       
        if (seguentMoviment == null || posicio == null) {
            return EDireccio.QUIET;
        }
        Punt posicioPacman = partida.obtenirPuntPacman();
        Punt posicioEnemic = partida.obtenirPuntEnemic();
        Punt puntAFugir = posicioEnemic;
        int distanciaAFugir;

        long tempsInici = System.currentTimeMillis();
        int distanciaPacman = gestorCami.trobarCamiMinim(posicio, posicioPacman).size();
        int distanciaEnemic = gestorCami.trobarCamiMinim(posicio, posicioEnemic).size();
        distanciaAFugir = distanciaEnemic;
        long tempsFinal = System.currentTimeMillis();
        log.afegirDebug("Temps que triga l'algorisme A* a trobar els enemics: " + (tempsFinal - tempsInici) + " ms");
        
        
        if (distanciaPacman < distanciaEnemic) {
            puntAFugir = posicioPacman;
            distanciaAFugir = distanciaPacman;
        } 

        if (distanciaAFugir <= DISTANCIA_PERILLOSA) {
            if (!esticFugint) {
                esticFugint = true;
                ruta = gestorCami.trobarCamiMaximitzarDist(posicio, puntAFugir);
            } 
            else{
                if (ruta == null || ruta.esBuida()) {
                    ruta = gestorCami.trobarCamiMaximitzarDist(posicio, puntAFugir);
                } 
            }
        } else {
            esticFugint = false;
            if (ruta == null || ruta.esBuida()) {
                Punt p = obtenirPosicioAleatoria();
                ruta = gestorCami.trobarCamiMinim(posicio, p);
            }
        }
        tempsFinal = System.currentTimeMillis();
        log.afegirDebug("Temps total que triga el Item a recalcular una ruta: " + (tempsFinal - tempsInici) + " ms \n");

        EDireccio res = ruta.obtenirUltimMoviment();
        ruta.eliminarMoviment();
        return res;
    }
    

    private Punt obtenirPosicioAleatoria(){
        boolean valid = false;
        Punt p = null;
        while (!valid){
            p = new Punt (Utils.obtenirValorAleatori(laberint.obtenirMidaCostatTauler() -1),Utils.obtenirValorAleatori(laberint.obtenirMidaCostatTauler() -1));
            valid = destiValid(p);
        }
        return p;
    }
    private boolean destiValid(Punt p){
        boolean valid = false;
        EElement element = laberint.obtenirElement(p);
        if (element != EElement.PARET && p != posicio)valid = true;
        return valid;
    }

    @Override
    public String nomItemMovible(){
        return "Item "+tipusElement;
    }

    @Override
    public EElement realitzarMoviment() {
        if(seguentMoviment != EDireccio.QUIET){
            elementTrapitjat = laberint.moureItem(posicio, seguentMoviment, elementTrapitjat);
            posicio = posicio.generarPuntDesplasat(seguentMoviment);
            return elementTrapitjat;
        }
        return null;
    }
}
