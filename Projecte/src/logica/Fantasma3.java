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
    private EMode mode;
    private final LlistaOrdenadaCandidats moneder;
    private static final int DISTANCIA_PERILLOSA = 9; //distancia < DISTANCIA_PERILLOSA es considera perill 
    private static final int DISTANCIA_CONSIDERABLE = 0;//Si distancia a ItemMovible < DISTANCIA_CONSIDERABLE, llavors anirem a per ell
    
    public Fantasma3(Partida partida, Laberint laberint, Punt inici) {
        super(partida, laberint, EElement.FANTASMA3.obtenirImatge(), inici);
        gestorCami = new GestorCamins(laberint);
        objectiu = null;
        ruta = null;
        mode = EMode.NAVEGACIO;
        moneder = new LlistaOrdenadaCandidats();
        omplenaMoneder();
        buscaObjectiu();
    }
    private enum EMode{
        FUGIR, //En aquest mode es calcula un cami de n posicions que maximitza la distancia amb el perseguidor, on n es la profunditat del BackTracking
        SEGUIMENT, //Es persegueix un objectiu movible. Es calcula el cami minim a cada moviment i nomes es porta a terme el primer pas d'aquest cami calculat.
        NAVEGACIO;// Es persegueix un objectiu estatic. Es calcula una sola vegada el cami minim i es segueixen els passos fins a aquest objectiu.
    }
    
   
    @Override
    public EDireccio calcularMoviment(){
        if (seguentMoviment == null || posicio == null)return EDireccio.QUIET;
        
        Punt posicioPacman = partida.obtenirPuntPacman();     
        moneder.elimina(new Casella(posicioPacman));//Elimino Aquesta Casella perque si esta en Pacman ja no hi haura moneda
        moneder.elimina(new Casella(posicio));//Elimino tambe la de la meva posicio
        
        valorarSituacio();
        
        long ini = System.currentTimeMillis();
        switch (mode){
            case NAVEGACIO: modeNavegacio();
                break;
            case SEGUIMENT: modeSeguiment();
                break;
            case FUGIR: modeFugir();
                break;
            default: modeNavegacio();
                break;
        }
       
        if (ruta.esBuida()){
            buscaObjectiu();
        }
        EDireccio res = ruta.obtenirUltimMoviment();
        if (movimentValid(res))ruta.eliminarMoviment();
        else res = EDireccio.QUIET;
        long fi = System.currentTimeMillis();
        System.out.println("TEMPS DE FANTASMA3 A PENDRE UNA DECISIO (Calcul de distancies + Calcul de ruta)"+ (fi - ini) + " ms");
        return res;
    }
    
    @Override
    public EElement realitzarMoviment(){
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
    
    @Override 
    public String nomItemMovible(){
        return "Fantasma3";
    }
    
    private void valorarSituacio(){
        Punt posicioPacman = partida.obtenirPuntPacman();
        int distanciaPacman = gestorCami.trobarCamiMinim(posicio, posicioPacman).size();
        if (mode == EMode.NAVEGACIO){
            //Haure de canviar de mode si:   - S'han acabat les monedes i estic perdent -> MODE_SEGUIMENT ( Misio: atrapar en Pacman abans de que surti del laberint)
            //                               - S'han acabat les monedes i estic guanyant --> MODE_NAVEGACIO (Haig d'anar cap a la sortida
            //                               - En Pacman te una mongeta i esta a una DISTANCIA_PERILLOSA -> MODE_FUGIDA
            //                               - Hi ha algun Item a una distancia < DISTANCIA_CONSIDERABLE -> MODE_SEGUIMENT
            //                               - Tinc una mongeta --> MODE_SEGUIMENT (Haig d'atrapar en Pacman)
             if (!quedenMonedes()){
                if (guanya)mode = EMode.NAVEGACIO;
                else mode = EMode.SEGUIMENT;
                objectiu = null;
            }
            else if (pacmanEsPerillos(distanciaPacman)){
                mode = EMode.FUGIR;
                objectiu = null;
                ruta = null;
            }
            else if (hiHaItemEspecialAProp()){
                mode = EMode.SEGUIMENT;
                objectiu = null;
            }
            else if (estatPersonatge == EEstatPersonatge.AMB_MONGETA){
                mode = EMode.SEGUIMENT;
                objectiu = null;
            }
        }
        else if (mode == EMode.FUGIR){
            //Haure de canviar de mode si: - En Pacman NO representa un perill per a FANTASMA3
            if (!pacmanEsPerillos(distanciaPacman)){
                mode = EMode.NAVEGACIO;
                objectiu = null;
            }                             
        }
        else if (mode == EMode.SEGUIMENT){
            //Haure de canviar de mode si: - En Pacman te una mongeta i esta a una distancia < DISTANCIA_PERILLOSA  
            //                             - Estic perseguint el Item i (o be l'ha agafat en Pacman o be l'ha agafat el fantasma3(objecte actual))
            //                             - Estic perseguint a en Pacman i ja l'he atrapat
            
            if (pacmanEsPerillos(distanciaPacman)){//Si en Pacman te mongeta i esta a una distancia < DISTANCIA_PERILLOSA
                mode = EMode.FUGIR;
                objectiu = null;
                ruta = null;
            }
            else if (objectiu != null){//Si tinc objectiu
                if (objectiu.element == EElement.PACMAN){
                    Punt p = partida.obtenirPuntPacman();
                    if (posicio.equals(p)){//Si he atrapat a En PACMAN
                        objectiu = null;
                        mode = EMode.NAVEGACIO;
                    }
                }
                else if (elementEsItem(objectiu.element)){
                    Punt p = partida.obtenirPuntItem();
                    if (p == null){//El Item ja no es troba en el Laberint
                        objectiu = null;
                        mode = EMode.NAVEGACIO;
                    }
                }
               
            }
            
        }
    }
    
     private void modeFugir(){
        //Si estic fugint es perque en Pacman m'esta perseguint, si encara tinc un ruta de maximitzar distancia llavors segueixo
        //altrament torno a calcular una altre ruta d'escapament.
        if (ruta == null || ruta.esBuida()){
            Punt puntPacman = partida.obtenirPuntPacman();
            ruta = gestorCami.trobarCamiMaximitzarDist(posicio, puntPacman);
        }
    }
    
    private void modeNavegacio(){
        //Es Poden donar les seguents situacions:  - Acabo de sortir de un altre mode i no tinc objectiu --> buscar objectiu
        //                                         - Estic navegant i encara no he arribat al meu objectiu --> comprovar que en el punt del meu objectiu 
        //                                           encara troba el meu objectiu. ( Es a dir, en Pacman no m'ha pispat cap moneda). En cas contrari
        //                                           buscar un nou objectiu.
        //                                         - He arrivat al meu objectiu --> acabar objetiu i buscar un altre nou objectiu
        //                                         - S'han acabat les monedes i estic guanyant, llavors haig d'anar cap a la sortida
        reCalcularDistanciesMonedes();
        if (objectiu == null){//No tinc objetiu
            if (!quedenMonedes() && guanya){
                   exitLaberint();
            }
            else buscaObjectiu();
        }
        else{//Si tinc objectiu
            //Haure de canviar de objectiu si: - He arrivat al meu objectiu o el meu objectiu ja no es troba en la mateixa posicio.
            if (objectiu.posicio.equals(posicio) || !objectiuVerificat()){
                objectiu = null;
                buscaObjectiu();
            }
        }
    }
    private void modeSeguiment(){
        //Si no tinc objectiu vol dir que acabo de entrar en aquest mode. Els casos en els que 
        //s'adopta el mode seguiment son: - S'han acabat les monedes, estic perdent i haig d'anar a per en Pacman.   
        //                                - Tinc mongeta i encara no he pres la decisio d'anar a per en Pacman 
        //                                - Hi ha algun Item a una distancia < DISTANCIA_CONSIDERABLE   
        //                                   
        if (objectiu == null){//Si no tinc Objectiu
            if (!quedenMonedes() && !guanya){
                Punt p = partida.obtenirPuntPacman();
                EElement e = EElement.PACMAN;
                objectiu = new Objectiu(p,e);
                ruta = gestorCami.trobarCamiMinim(posicio, objectiu.posicio);
            }
            else if (estatPersonatge == EEstatPersonatge.AMB_MONGETA){
                Punt p = partida.obtenirPuntPacman();
                EElement e = EElement.PACMAN;
                objectiu = new Objectiu(p,e);
                ruta = gestorCami.trobarCamiMinim(posicio, objectiu.posicio);
            }
            else if (hiHaItemEspecialAProp()){
                Punt p = partida.obtenirPuntItem();
                EElement e = EElement.MONGETA;  //Fantasma 3 no diferencia entre els items, utilitzarem mongeta com a per defecte per a identificar un item
                objectiu = new Objectiu(p,e);
                ruta = gestorCami.trobarCamiMinim(posicio, objectiu.posicio);
            }
        }
        else{//Si tinc objectiu  (Torno a calcular la ruta) 
            switch(objectiu.element){
                case PATINS:
                case MONEDES_X2:
                case MONGETA: objectiu.posicio = partida.obtenirPuntItem();
                    break;
                case PACMAN: objectiu.posicio = partida.obtenirPuntPacman();
                    break;      
            }
            ruta = gestorCami.trobarCamiMinim(posicio, objectiu.posicio);
        }
            
    }

    private void exitLaberint(){
        EElement element = EElement.SORTIDA;
        Punt p = buscaElement(EElement.SORTIDA);
        objectiu = new Objectiu(p,element);
        ruta = gestorCami.trobarCamiMinim(posicio, objectiu.posicio);
    }
    
    
    private boolean movimentValid(EDireccio mov){
        boolean valid = true;
        Punt p = posicio.generarPuntDesplasat(mov);
        EElement element = laberint.obtenirElement(p);
        if (element == EElement.PACMAN && estatPersonatge != EEstatPersonatge.AMB_MONGETA)valid = false;
        return valid;
    }
    
    /**
     * @brief Busca una moneda o 
     * @post ruta conte un cami fins al objectiu
     */
    private void buscaObjectiu(){
        Punt p = buscaMonedaMesProxima();
        if (p == null){ //S'han acabat les monedes
            objectiu = null;
            ruta = null;
        }
        else{//Encara hi han Monedes
            EElement element = laberint.obtenirElement(p);
            Objectiu nouObjectiu = new Objectiu (p, element);
            objectiu = nouObjectiu;
            ruta = gestorCami.trobarCamiMinim(posicio, objectiu.posicio);
        }
    }

    private Punt buscaMonedaMesProxima(){
        if (!moneder.esBuida())return moneder.obtenirPrimer().obtenirPunt();
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

    private void omplenaMoneder(){
        int mida = laberint.obtenirMidaCostatTauler();
        for (int i = 0; i < mida; i++){
            for( int j = 0; j < mida; j++){
                Punt p = new Punt (i,j);
                EElement element = laberint.obtenirElement(p);
                if (elementEsMoneda(element)){
                    int dist = gestorCami.trobarCamiMinim(posicio, p).size();
                    Casella c = new Casella(p);
                    c.afegirDistanciaAlObjectiu(dist);
                    moneder.afegir(c);
                }
            }
        }
    }
    private void reCalcularDistanciesMonedes(){
        int mida = moneder.size();
        long ini = System.currentTimeMillis();
        for (int i = 0; i< mida; i++){
            Casella c = moneder.obtenirElement(i);
            c.afegirDistanciaAlObjectiu(gestorCami.trobarCamiMinim(posicio, c.obtenirPunt()).size());
        }
        long fi = System.currentTimeMillis();
        System.out.println("TEMPS A CALCULAR EL CAMI MINIM DE " + mida +"  MONEDES -> "+ (fi-ini) + " ms");
        moneder.ordena();
        long fin = System.currentTimeMillis();
        System.out.println("TEMPS A ORDENAR " + mida +"  MONEDES -> "+ (fin-fi) + " ms");
        
    }
    
    private boolean hiHaItemEspecialAProp(){
        boolean hiHaItemAProp = false;
        Punt p = partida.obtenirPuntItem();
        if (p != null){
            int distancia = gestorCami.trobarCamiMinim(posicio, p).size();
            if (distancia < DISTANCIA_CONSIDERABLE)hiHaItemAProp=true;
        }
        return hiHaItemAProp;
    }
    
    
    
    private boolean elementEsItem(EElement element){
        return element == EElement.MONGETA || element == EElement.MONEDES_X2 || element == EElement.PATINS;
    }
    
    private boolean elementEsMoneda(EElement element){
        return element == EElement.MONEDA ||element == EElement.MONEDA_EXTRA;
    }
    
    private boolean pacmanEsPerillos(int distanciaPacman){
        //Es perillos si te mongeta i esta a una distancia < DISTANCIA_PERILLOSA
        return partida.obtenirEstatPacman() == EEstatPersonatge.AMB_MONGETA && distanciaPacman < DISTANCIA_PERILLOSA;
    }
    private boolean quedenMonedes(){
        return !moneder.esBuida();
    }
 
    private boolean objectiuVerificat(){
        return laberint.obtenirElement(objectiu.posicio) == objectiu.element;
    }

    private class Objectiu{
        public Punt posicio;
        public EElement element;
        
        public Objectiu(Punt _posicio, EElement _element){
            posicio = _posicio;
            element = _element;
        }
    }

//    @Override
//    public EDireccio calcularMoviment() {
//        if (seguentMoviment == null || posicio == null)return EDireccio.QUIET;
//        long ini = System.currentTimeMillis();
//        Punt posicioPacman = partida.obtenirPuntPacman();
//        
//        monedes.elimina(new Casella(posicioPacman));//Elimino Aquesta Casella perque si esta en Pacman ja no hi haura moneda
//        monedes.elimina(new Casella(posicio));//Elimino tambe la de la meva posicio
//        
//        if (guanya){//Si s'han acabat les monedes i estic guanyant
//            modeExit();
//        }
//        //Si s'han acabat les monedes i no estic guanyant o tinc la mongeta, llavors anire a per en Pacman
//        else if (fiMonedes && !guanya || this.estatPersonatge == EEstatPersonatge.AMB_MONGETA){
//            return perseguirObjectiuMovible(posicioPacman);
//        }
//        else{ //Si fanstama esta en els estats: NORMAL || AMB_PATINS || AMB_MONEDES_X2
//            if (pacmanEsPerillos()){
//                int distanciaPacman = gestorCami.trobarCamiMinim(posicio, posicioPacman).size();
//                if (distanciaPacman < DISTANCIA_PERILLOSA){
//                    modeFugir(posicioPacman);
//                }
//                else{
//                    modeNavegacio();
//                }
//            }
//            else if(partida.hiHaItemEspecial()){
//                Punt posicioItem = partida.obtenirPuntItem();
//                int distanciaAItem = gestorCami.trobarCamiMinim(posicio, posicioItem).size();
//                if (distanciaAItem < DISTANCIA_CONSIDERABLE){
//                    return perseguirObjectiuMovible(posicioItem);
//                }
//                else modeNavegacio();
//                
//            }
//            else{//Tot normal
//                modeNavegacio();
//            }
//        }
//        
//        if (ruta.esBuida()){
//            modeNavegacio();
//        }
//        EDireccio res = ruta.obtenirUltimMoviment();
//        if (movimentValid(res))ruta.eliminarMoviment();
//        else res = EDireccio.QUIET;
//        long fi = System.currentTimeMillis();
//        System.out.println("TEMPS DE FANTASMA3 A PENDRE UNA DECISIO (Calcul de distancies + Calcul de ruta)"+ (fi - ini) + " ms");
//        return res;
//    }
   
//    private void modeNavegacio(){
//        //Si entro aqui, pot ser que ja estigues navegant, que estigues fugint o que estigues perseguint 
//        //si estava fugint o perseguint o he arrivat al meu objectiu o el meu objectiu ha desaperagut, llavors busco un nou objectiu
//        if (esticFugint || esticPerseguint() || posicio.equals(objectiu.posicio) || laberint.obtenirElement(objectiu.posicio) != objectiu.element){
//            reCalcularDistanciesMonedes();
//            esticFugint = false;
//            mode = EMode.NAVEGACIO;
//            ruta = null;
//            buscaObjectiu();
//        }
//    }
//    private void modeFugir(Punt p){
//        objectiu = null;
//        //Si no estava fugint, o m'he quedat sense moviments de fugida llavors necessito una ruta nova per fugir
//        if (!esticFugint || ruta == null ||ruta.esBuida()){
//            esticFugint = true;
//            mode = EMode.FUGIR;
//            ruta = gestorCami.trobarCamiMaximitzarDist(posicio, p);
//        }
//    }
//    
//    private void fesSeguiment(EElement element){
//        switch(element){
//            case: EElement.SORTIDA
//        }
//    }
//    private void seguimentPacman(){
//        objectiu.element = EElement.SORTIDA;
//        objectiu.posicio = buscaElement(EElement.SORTIDA);
//        ruta = gestorCami.trobarCamiMinim(posicio, objectiu.posicio);
//    }    
//    private boolean pacmanEstaAProp(){
//        int distanciaPacman = gestorCami.trobarCamiMinim(posicio, partida.obtenirPuntPacman()).size();
//        return distanciaPacman < DISTANCIA_PERILLOSA;
//    }    
//    private EDireccio perseguirObjectiuMovible(Punt p){
//        return gestorCami.minimitzarDistancia(posicio,p);
//    }
//    private boolean esticPerseguint(){
//        boolean perseguint = false;
//        if (objectiu != null){
//            if (objectiu.element == EElement.PACMAN)perseguint = true;
//            else if (objectiu.element == EElement.MONGETA)perseguint = true;
//            else if (objectiu.element == EElement.PATINS)perseguint = true;
//            else if (objectiu.element == EElement.MONEDES_X2)perseguint = true;
//        }
//        return perseguint;
//    }    
    
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
