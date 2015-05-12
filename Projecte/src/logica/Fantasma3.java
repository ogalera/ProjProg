/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import logica.laberints.Laberint;
import logica.enumeracions.EDireccio;
import logica.enumeracions.EElement;
import java.util.PriorityQueue;
import logica.algoritmica.Casella;
import logica.algoritmica.GestorCamins;
import logica.historic_moviments.HistoricMoviments;

/**
 *
 * @author oscar
 */
public class Fantasma3 extends Personatge{
    private boolean marxaEnrrere;
    private final GestorCamins gestorCami;
    private Objectiu objectiu;
    
    public Fantasma3(Partida partida, Laberint laberint, Punt inici) {
        super(partida, laberint, EElement.FANTASMA3.obtenirImatge(), inici);
        gestorCami = new GestorCamins(laberint);
        marxaEnrrere = false;
        objectiu = null;
    }

    @Override
    public EDireccio calcularMoviment() {
        //        marxaEnrrere = false;
        
        if (seguentMoviment == null || posicio == null)return EDireccio.QUIET;
        EDireccio res = EDireccio.QUIET;
        Punt posicioPacman = partida.obtenirPuntPacman();
        
     
        if (estatPersonatge == EEstatPersonatge.AMB_MONGETA){
            res = gestorCami.minimitzarDistancia(posicio, partida.obtenirPuntPacman());
        }
        else{
            if (historicMoviments == null || historicMoviments.esBuida()){
                if (objectiu == null){
                    buscaMonedaOSortida();
                }
                //Si Objectiu != null
                else{
                    //Si l'objectiu no ha cambiat de posicio
                    if ( objectiu.element == laberint.obtenirElement(objectiu.posicio)){
                        Punt p = buscaElement(objectiu.element);
                        historicMoviments = gestorCami.trobarCamiMinim(posicio, p);
                    }
                    //Si l'objectiu ha cambiat de posicio, torno a busca el meu element
                    else {
                        Punt p = buscaElement(objectiu.element);
                        objectiu.posicio = p;
                        historicMoviments = gestorCami.trobarCamiMinim(posicio, p);
                        
                    }
                }
            }
            res = historicMoviments.obtenirUltimMoviment();
            historicMoviments.eliminarMoviment();
        }
        return res;
    }
    
    @Override
    public EElement realitzarMoviment(){
        EElement elementObtingut = super.realitzarMoviment();
        if(!marxaEnrrere){
            this.historicMoviments.afegirMoviment(super.seguentMoviment);
        }
        partida.assignarPuntsEnemic(punts);
        return elementObtingut;
    }
    
    
    
    
    
    private int obtenirMaxim(int ... valors){
        int maxim = valors[0];
        for(int i = 0; i < valors.length; i++){
            if(maxim < valors[i]){
                maxim = valors[i];
            }
        }
        return maxim;
    }
    
    @Override public String nomItemMovible(){
        return "Fantasma3";
    }
    
    private void buscaMonedaOSortida(){
        Punt p = buscaMonedaMesProxima();
        //Si ha trobat alguna moneda
        if (p!= null){
            objectiu.posicio = p;
            objectiu.element = laberint.obtenirElement(p);
            historicMoviments = gestorCami.trobarCamiMinim(posicio, p);
        }
        //Si no ha trobat cap moneda busca la Sortida
        else{
            p = buscaElement(EElement.SORTIDA);
            objectiu.posicio = p;
            objectiu.element = laberint.obtenirElement(p);
            historicMoviments = gestorCami.trobarCamiMinim(posicio, p);
        }
    }
    private Punt buscaMonedaMesProxima(){
        Punt res = null;
        int mida = laberint.obtenirMidaCostatTauler();
        
        int nivell = 0;
        while (nivell < mida && res == null){
            nivell++;
            res = buscaMonedaAlVoltant(nivell); 
        }
        return res;
    }
    
    private Punt buscaMonedaAlVoltant(int nivell){
        boolean trobat = false;
        Punt moneda = null;
        EElement element;
        int fila = posicio.obtenirFila();
        int columna = posicio.obtenirColumna();
        for (int i = fila - nivell; i <= fila + nivell && !trobat; i++){
            for (int j = columna - nivell; j <= columna+nivell && !trobat; j++){
                element = laberint.obtenirElement(new Punt(i,j));
                if (element == EElement.MONEDA || element == EElement.MONEDA_EXTRA  ){
                    moneda = new Punt(i,j);
                    trobat = true;
                }
            }
        }
        return moneda;
    }
    private Punt buscaElement(EElement _element){
        Punt res = null;
        boolean trobat = false;
        int mida = laberint.obtenirMidaCostatTauler();
        //Si objectiu es una moneda es fara una cerca per trobar la moneda mes proxima
        if (_element == EElement.MONEDA || _element == EElement.MONEDA_EXTRA){
            res = this.buscaMonedaMesProxima();
        }
        //Si es qualssevol altre objecte es fa una cerca normal
        else{
            for (int i = 0; i < mida && !trobat; i++){
                for(int j = 0; j < mida && !trobat; j++){
                    Punt p = new Punt(i,j);
                    if (laberint.obtenirElement(p)== _element ){
                        trobat = true;
                        res = p;
                    }
                }
            }
        }
        
        return res;
    }

    private class Objectiu{
        public Punt posicio;
        public EElement element;
        
        public Objectiu(Punt _posicio, EElement _element){
            posicio = _posicio;
            element = _element;
        }
    }
}
