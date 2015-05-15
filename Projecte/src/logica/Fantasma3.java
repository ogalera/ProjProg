/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import logica.laberints.Laberint;
import logica.enumeracions.EDireccio;
import logica.enumeracions.EElement;
import logica.algoritmica.GestorCamins;
import logica.historic_moviments.HistoricMoviments;
import logica.algoritmica.Casella;
import logica.algoritmica.LlistaOrdenadaCandidats;

/**
 *
 * @author oscar
 */
public class Fantasma3 extends Personatge{
    private final GestorCamins gestorCami;
    private Objectiu objectiu;
    private HistoricMoviments ruta;
    private boolean esticFugint;
    private boolean fiMonedes;
    private EMode mode;
    private final LlistaOrdenadaCandidats monedes;
    private static final int DISTANCIA_PERILLOSA = 6; //distancia < DISTANCIA_PERILLOSA es considera perill 
    private static final int DISTANCIA_CONSIDERABLE = 8;//Si distancia a ItemMovible < DISTANCIA_CONSIDERABLE, llavors anirem a per ell
    
    public Fantasma3(Partida partida, Laberint laberint, Punt inici) {
        super(partida, laberint, EElement.FANTASMA3.obtenirImatge(), inici);
        gestorCami = new GestorCamins(laberint);
        objectiu = null;
        ruta = null;
        mode = EMode.NAVEGACIO;
        esticFugint = false;
        monedes = new LlistaOrdenadaCandidats();
        fiMonedes = false;
        omplenadaMonedero();
        buscaObjectiu();
    }
    private enum EMode{
        FUGIR, SEGUIMENT, NAVEGACIO;
    }

    @Override
    public EDireccio calcularMoviment() {
        if (seguentMoviment == null || posicio == null)return EDireccio.QUIET;
        long ini = System.currentTimeMillis();
        Punt posicioPacman = partida.obtenirPuntPacman();
        
        monedes.elimina(new Casella(posicioPacman));//Elimino Aquesta Casella perque si esta en Pacman ja no hi haura moneda
        monedes.elimina(new Casella(posicio));//Elimino tambe la de la meva posicio
        
        if (guanya){//Si s'han acabat les monedes i estic guanyant
            modeExit();
        }
        //Si s'han acabat les monedes i no estic guanyant o tinc la mongeta, llavors anire a per en Pacman
        else if (fiMonedes && !guanya || this.estatPersonatge == EEstatPersonatge.AMB_MONGETA){
            return perseguirObjectiuMovible(posicioPacman);
        }
        else{ //Si fanstama esta en els estats: NORMAL || AMB_PATINS || AMB_MONEDES_X2
            if (pacmanEsPerillos()){
                int distanciaPacman = gestorCami.trobarCamiMinim(posicio, posicioPacman).size();
                if (distanciaPacman < DISTANCIA_PERILLOSA){
                    modeFugir(posicioPacman);
                }
                else{
                    modeNavegacio();
                }
            }
            else if(partida.hiHaItemEspecial()){
                Punt posicioItem = partida.obtenirPuntItem();
                int distanciaAItem = gestorCami.trobarCamiMinim(posicio, posicioItem).size();
                if (distanciaAItem < DISTANCIA_CONSIDERABLE){
                    return perseguirObjectiuMovible(posicioItem);
                }
                else modeNavegacio();
                
            }
            else{//Tot normal
                modeNavegacio();
            }
        }
        
        if (ruta.esBuida()){
            modeNavegacio();
        }
        EDireccio res = ruta.obtenirUltimMoviment();
        if (movimentValid(res))ruta.eliminarMoviment();
        else res = EDireccio.QUIET;
        long fi = System.currentTimeMillis();
        System.out.println("TEMPS DE FANTASMA3 A PENDRE UNA DECISIO (Calcul de distancies + Calcul de ruta)"+ (fi - ini) + " ms");
        return res;
    }
   
    private void modeNavegacio(){
        //Si entro aqui, pot ser que ja estigues navegant, que estigues fugint o que estigues perseguint 
        //si estava fugint o perseguint o he arrivat al meu objectiu o el meu objectiu ha desaperagut, llavors busco un nou objectiu
        if (esticFugint || esticPerseguint() || posicio.equals(objectiu.posicio) || laberint.obtenirElement(objectiu.posicio) != objectiu.element){
            reCalcularDistanciesMonedes();
            esticFugint = false;
            mode = EMode.NAVEGACIO;
            ruta = null;
            buscaObjectiu();
        }
    }
    private void modeFugir(Punt p){
        objectiu = null;
        //Si no estava fugint, o m'he quedat sense moviments de fugida llavors necessito una ruta nova per fugir
        if (!esticFugint || ruta == null ||ruta.esBuida()){
            esticFugint = true;
            mode = EMode.FUGIR;
            ruta = gestorCami.trobarCamiMaximitzarDist(posicio, p);
        }
    }
    private void modeSeguiment(Punt p){
        if (mode != EMode.SEGUIMENT){
            ruta = gestorCami.trobarCamiMinim(posicio, p);
            mode = EMode.SEGUIMENT;
        }
    }
    private void modeExit(){
        //Si no estava ananant cap a la sortida llavors necessitare un cami cap a la sortida
        if (objectiu.element != EElement.SORTIDA){
            objectiu.element = EElement.SORTIDA;
            objectiu.posicio = buscaElement(EElement.SORTIDA);
            ruta = gestorCami.trobarCamiMinim(posicio, objectiu.posicio);
        }
    }
    
    
    private boolean movimentValid(EDireccio mov){
        boolean valid = true;
        Punt p = posicio.generarPuntDesplasat(mov);
        EElement element = laberint.obtenirElement(p);
        if (element == EElement.PACMAN && estatPersonatge != EEstatPersonatge.AMB_MONGETA)valid = false;
        return valid;
    }
    
    private EDireccio perseguirObjectiuMovible(Punt p){
        return gestorCami.minimitzarDistancia(posicio,p);
    }
    private boolean esticPerseguint(){
        boolean perseguint = false;
        if (objectiu != null){
            if (objectiu.element == EElement.PACMAN)perseguint = true;
            else if (objectiu.element == EElement.MONGETA)perseguint = true;
            else if (objectiu.element == EElement.PATINS)perseguint = true;
            else if (objectiu.element == EElement.MONEDES_X2)perseguint = true;
        }
        return perseguint;
    }
    
    @Override
    public EElement realitzarMoviment(){
//        EElement elementObtingut = super.realitzarMoviment();
//        partida.assignarPuntsEnemic(punts);
//        return elementObtingut;
        Punt puntDesplasat = posicio.generarPuntDesplasat(super.seguentMoviment);
        EElement element = laberint.obtenirElement(puntDesplasat);
        if(element != EElement.PACMAN || super.obtenirEstatPersonatge() == EEstatPersonatge.AMB_MONGETA){
            EElement elementObtingut = super.realitzarMoviment();
            switch(elementObtingut){
                case MONEDA:{
                    super.incrementarPunts(Utils.Constants.VALOR_MONEDA_NORMAL);
                    partida.assignarPuntsEnemic(punts);
                }break;
                case MONEDA_EXTRA:{
                    super.incrementarPunts(Utils.Constants.VALOR_MONEDA_EXTRA);
                    partida.assignarPuntsEnemic(punts);
                }break;
                case PATINS:
                case MONEDES_X2:
                case MONGETA:{
                    //Em agafat algún item
                    partida.itemCapturat();
                    super.assignarEstatPersonatge(elementObtingut);
                    partida.assignarItemAEnemic(elementObtingut);
                }break;
            }
            return elementObtingut;
        }
        return null;
    }
    
    @Override public String nomItemMovible(){
        return "Fantasma3";
    }
    
    
    /**
     * @brief Busca una moneda o la sortida
     * @post ruta conte un cami fins al objectiu
     */
    private void buscaObjectiu(){
        Punt p = buscaMonedaMesProxima();
        EElement element;
        if (p == null){
            p = buscaElement(EElement.SORTIDA);
            fiMonedes = true;
        }
        element = laberint.obtenirElement(p);
        Objectiu nouObjectiu = new Objectiu (p, element);
        objectiu = nouObjectiu;
        ruta = gestorCami.trobarCamiMinim(posicio, objectiu.posicio);

    }

    private Punt buscaMonedaMesProxima(){
        if (!monedes.esBuida())return monedes.obtenirPrimer().obtenirPunt();
        else return null;
    }

    private Punt buscaElement(EElement _element){
        Punt res = null;
        boolean trobat = false;
        int mida = laberint.obtenirMidaCostatTauler();
        //Cerca Normal
        for (int i = 0; i < mida && !trobat; i++) {
            for (int j = 0; j < mida && !trobat; j++) {
                Punt p = new Punt(i, j);
                if (laberint.obtenirElement(p) == _element) {
                    trobat = true;
                    res = p;
                }
            }
        }
        return res;
    }

    
    private void omplenadaMonedero(){
        int mida = laberint.obtenirMidaCostatTauler();
        for (int i = 0; i < mida; i++){
            for( int j = 0; j < mida; j++){
                Punt p = new Punt (i,j);
                EElement element = laberint.obtenirElement(p);
                if (esMoneda(element)){
                    int dist = gestorCami.trobarCamiMinim(posicio, p).size();
                    Casella c = new Casella(p);
                    c.afegirDistanciaAlObjectiu(dist);
                    monedes.afegir(c);
                }
                
                
                
                
                
            }
        }
    }
    private void reCalcularDistanciesMonedes(){
        int mida = monedes.size();
        long ini = System.currentTimeMillis();
        for (int i = 0; i< mida; i++){
            Casella c = monedes.obtenirElement(i);
            c.afegirDistanciaAlObjectiu(gestorCami.trobarCamiMinim(posicio, c.obtenirPunt()).size());
        }
        long fi = System.currentTimeMillis();
        System.out.println("TEMPS A CALCULAR EL CAMI MINIM DE " + mida +"  MONEDES -> "+ (fi-ini) + " ms");
        monedes.ordena();
        long fin = System.currentTimeMillis();
        System.out.println("TEMPS A ORDENAR " + mida +"  MONEDES -> "+ (fin-fi) + " ms");
        
    }
    
    
    
    private boolean esMoneda(EElement element){
        return element == EElement.MONEDA ||element == EElement.MONEDA_EXTRA;
    }
    
    
    private boolean pacmanEsPerillos(){
        return partida.obtenirEstatPacman() == EEstatPersonatge.AMB_MONGETA;
    }

    private class Objectiu{
        public Punt posicio;
        public EElement element;
        
        public Objectiu(Punt _posicio, EElement _element){
            posicio = _posicio;
            element = _element;
        }
    }
    
    
//    private Punt obtenirPosicioAleatoria(){
//        boolean valid = false;
//        Punt p = null;
//        while (!valid){
//            p = new Punt (Utils.obtenirValorAleatori(laberint.obtenirMidaCostatTauler() -1),Utils.obtenirValorAleatori(laberint.obtenirMidaCostatTauler() -1));
//            valid = destiValid(p);
//        }
//        return p;
//    }
//    private boolean destiValid(Punt p){
//        boolean valid = false;
//        EElement element = laberint.obtenirElement(p);
//        if (element != EElement.PARET && p != posicio)valid = true;
//        return valid;
//    }
    
    
    
//        //Temps en el pitjor dels casos 19 ms (tauler de 35 x 35 i recorrer totes les caselles)
//    //Troba monedes
//    private Punt buscaMonedaMesProximas(){
//        Punt res = null;
//        int mida = laberint.obtenirMidaCostatTauler();
//        
//        int nivell = 0;
//        while (nivell < mida && res == null){
//            nivell++;
//            res = buscaMonedaAlVoltant(nivell); 
//        }
//        return res;
//    }
//    //Cerca perimetral per nivells. Ex: Estic a la casella (2,2) i nivell=1 doncs buscare a les caselles(1,1)(1,2)(1,3)(2,1)(2,3)(3,1)(3,2)(3,3)
//    private Punt buscaMonedaAlVoltant(int nivell){
//        System.out.print("Els candidats per a la posicio: " + posicio + " en nivell " + nivell +" son:\n");
//        boolean trobat = false;
//        Punt moneda = null;
//        EElement element;
//        int fila = posicio.obtenirFila();
//        int columna = posicio.obtenirColumna();
//        for (int i = fila - nivell; i <= fila + nivell && !trobat; i++){
//            for (int j = columna - nivell; j <= columna+nivell && !trobat; j++){
//                if (( i== fila -nivell ||  i == fila + nivell) ||
//                     j == columna - nivell || j == columna + nivell){
//                    element = laberint.obtenirElement(new Punt(i,j));
//                    if (esMoneda(element)) {
//                        moneda = new Punt(i, j);
//                        trobat = true;
//                    }
//                }
//            }
//        }
//        return moneda;
//    }

}
