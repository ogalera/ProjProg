package logica;

import java.awt.Image;
import javax.swing.ImageIcon;
import logica.laberints.Laberint;
import logica.enumeracions.EDireccio;
import logica.enumeracions.EElement;
import logica.algoritmica.GestorCamins;
import logica.historic_moviments.HistoricMoviments;


/**
 *
 * @author oscar
 */
public class Fantasma3 extends Personatge{
    private final GestorCamins gestorCami;
    private Objectiu objectiu;
    private HistoricMoviments ruta;
    private EMode mode;
    private static final int DISTANCIA_PERILLOSA = 15; //distancia < DISTANCIA_PERILLOSA es considera perill 
    private static final int DISTANCIA_CONSIDERABLE = 8;//Si distancia a ItemMovible < DISTANCIA_CONSIDERABLE, llavors anirem a per ell

    
    public Fantasma3(Partida partida, Laberint laberint, Punt inici) {
        super(partida, laberint, EElement.FANTASMA3.obtenirImatge(), inici);
        gestorCami = new GestorCamins(laberint);
        objectiu = null;
        ruta = null;
        mode = EMode.NAVEGACIO;
        buscaMoneda();
    }

    @Override
    protected void assignarImatges() {
        int llargada = laberint.obtenirMidaImatge().height;
        this.imatges[0][0] = new ImageIcon(new ImageIcon(ClassLoader.getSystemResource("res/imatges/enemic3D0.png")).getImage().getScaledInstance(llargada, llargada, Image.SCALE_DEFAULT));//EElement.PACMAN.obtenirImatge();
        this.imatges[0][1] = new ImageIcon(new ImageIcon(ClassLoader.getSystemResource("res/imatges/enemic3D1.png")).getImage().getScaledInstance(llargada, llargada, Image.SCALE_DEFAULT));
        this.imatges[1][0] = new ImageIcon(new ImageIcon(ClassLoader.getSystemResource("res/imatges/enemic3E0.png")).getImage().getScaledInstance(llargada, llargada, Image.SCALE_DEFAULT));
        this.imatges[1][1] = new ImageIcon(new ImageIcon(ClassLoader.getSystemResource("res/imatges/enemic3E1.png")).getImage().getScaledInstance(llargada, llargada, Image.SCALE_DEFAULT));
        this.imatges[2][0] = new ImageIcon(new ImageIcon(ClassLoader.getSystemResource("res/imatges/enemic3A0.png")).getImage().getScaledInstance(llargada, llargada, Image.SCALE_DEFAULT));
        this.imatges[2][1] = new ImageIcon(new ImageIcon(ClassLoader.getSystemResource("res/imatges/enemic3A1.png")).getImage().getScaledInstance(llargada, llargada, Image.SCALE_DEFAULT));
        this.imatges[3][0] = new ImageIcon(new ImageIcon(ClassLoader.getSystemResource("res/imatges/enemic3B0.png")).getImage().getScaledInstance(llargada, llargada, Image.SCALE_DEFAULT));
        this.imatges[3][1] = new ImageIcon(new ImageIcon(ClassLoader.getSystemResource("res/imatges/enemic3B1.png")).getImage().getScaledInstance(llargada, llargada, Image.SCALE_DEFAULT));
    }
    
    /**
     * @brief Diferents Modes que adopta Fantasma 3 durant una partida
     */
    private enum EMode{
        FUGIR, //En aquest mode es calcula un cami de n posicions que maximitza la distancia amb el perseguidor, on n es la profunditat del BackTracking
        SEGUIMENT, //Es persegueix un objectiu movible. Es calcula el cami minim a cada moviment i nomes es porta a terme el primer pas d'aquest cami calculat.
        NAVEGACIO;// Es persegueix un objectiu estatic. Es calcula una sola vegada el cami minim i es segueixen els passos fins a aquest objectiu.
    }
    
   
    @Override
    public EDireccio calcularMoviment(){
//        System.out.println("CALCULAR MOVIMENT FANTASMA 3");
        EDireccio res;

        valorarSituacio();
        
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
        
        
        
//        System.out.println("Conclusio de la situacio:");
//        System.out.println("Posicio: " + posicio);
//        System.out.println("Estat: " + donamNomMode(mode));
//        if (objectiu != null)System.out.println("Objectiu: " + donamNomElement(objectiu.element));
//        else System.out.println("Objectiu: null");
        if(ruta.esBuida()){
            EElement e;
            do{
                int index = Utils.obtenirValorAleatori(4);
                res = EDireccio.values()[index];
                Punt p = posicio.generarPuntDesplasat(res);
                e = laberint.obtenirElement(p);
            }while(e == EElement.PARET || e == EElement.PACMAN || e.esEnemic());
        }
        else res = ruta.obtenirUltimMoviment();
        if (!movimentValid(res)) res = EDireccio.QUIET;
        return res;
    }
    
    @Override
    public EElement realitzarMoviment(){
        EElement elementObtingut = super.realitzarMoviment();
        if(elementObtingut != null && elementObtingut != EElement.FANTASMA3){
            if(!ruta.esBuida())ruta.eliminarMoviment();
            switch(elementObtingut){
                case MONEDA:{
                    incrementarPunts(Utils.Constants.VALOR_MONEDA_NORMAL);
                    partida.assignarPuntsEnemic(punts);
                    posicio = posicio.generarPuntDesplasat(seguentMoviment);
                }break;
                case MONEDA_EXTRA:{
                    incrementarPunts(Utils.Constants.VALOR_MONEDA_EXTRA);
                    partida.assignarPuntsEnemic(punts);
                    posicio = posicio.generarPuntDesplasat(seguentMoviment);
                }break;
                case PACMAN:{
                    int puntsPacman = partida.reiniciarPuntsPacman();
                    incrementarPunts(puntsPacman);
                    partida.assignarPuntsEnemic(punts);
                    objectiu = null;
                    mode = EMode.NAVEGACIO;
                }break;
                case PATINS:
                case MONEDES_X2:
                case MONGETA:{
                    Audio.reprodueixMenjaItem();
                    //Em agafat algún item
                    partida.itemCapturat();
                    assignarEstatPersonatge(elementObtingut);
                    partida.assignarItemAEnemic(elementObtingut);
                    posicio = posicio.generarPuntDesplasat(seguentMoviment);
                }break;
                case RES:{
                    posicio = posicio.generarPuntDesplasat(seguentMoviment);
                }break;
            }
        }
        return null;
    }
    
    @Override 
    public String nomItemMovible(){
        return "Fantasma3";
    }
    
    /**
     * @brief Valoració de la situacio abans de calcular un moviment.
     * @post 
     */
    private void valorarSituacio(){
        Punt posicioPacman = partida.obtenirPuntPacman();
        int distanciaPacman = gestorCami.trobarCamiMinim(posicio, posicioPacman).obtenirMida();
        if (mode == EMode.NAVEGACIO){
//            System.out.println("ESTIC EN MODE NAVEGACIO I VAIG A VALORAR LA SITUACIO:");
            //Haure de canviar de mode si:   - S'han acabat les monedes i estic perdent -> MODE_SEGUIMENT ( Misio: atrapar en Pacman abans de que surti del laberint)
            //                               - S'han acabat les monedes i estic guanyant --> MODE_NAVEGACIO (Haig d'anar cap a la sortida
            //                               - En Pacman te una mongeta i esta a una DISTANCIA_PERILLOSA -> MODE_FUGIDA
            //                               - Hi ha algun Item a una distancia < DISTANCIA_CONSIDERABLE -> MODE_SEGUIMENT
            //                               - Tinc una mongeta --> MODE_SEGUIMENT (Haig d'atrapar en Pacman)
            if (!quedenMonedes()){
                if (guanya)mode = EMode.NAVEGACIO;
                else{
                    mode = EMode.SEGUIMENT;
//                    System.out.println("No queden monedes i estic perdent, canvio a mode Seguiment:");
                }
                objectiu = null;
            }
            else if (pacmanEsPerillos(distanciaPacman)){
//                 System.out.println("En Pacman es perillos, canvio a mode fugir:");
                mode = EMode.FUGIR;
                objectiu = null;
                ruta = null;
            }
            else if (hiHaItemEspecialAProp()){
//                 System.out.println("Hi ha algun Item a prop, canvio a mode Seguiment:");
                mode = EMode.SEGUIMENT;
                objectiu = null;
            }
            else if (estatPersonatge == EEstatPersonatge.AMB_MONGETA && vullAtraparEnPacman()){
//                 System.out.println("Tinc mongeta i vull atrapar a en Pacman, canvio a mode Seguiment:");
                mode = EMode.SEGUIMENT;
                objectiu = null;
            }
//            else System.out.println(" CONCLUSIO: No canvio d'estat ");
        }
        else if (mode == EMode.FUGIR){
//            System.out.println("ESTIC EN MODE FUGIR I VAIG A VALORAR LA SITUACIO:");
            //Haure de canviar de mode si: - En Pacman NO representa un perill per a FANTASMA3
            if (!pacmanEsPerillos(distanciaPacman)){
//                 System.out.println("En Pacman ha deixat de ser perillos, canvio a mode Navegacio:");
                mode = EMode.NAVEGACIO;
                objectiu = null;
            }   
//            else System.out.println(" CONCLUSIO: No canvio d'estat ");
        }
        else if (mode == EMode.SEGUIMENT){
//            System.out.println("ESTIC EN MODE SEGUIMENT I VAIG A VALORAR LA SITUACIO:");
            //Haure de canviar de mode si: - En Pacman te una mongeta i esta a una distancia < DISTANCIA_PERILLOSA  
            //                             - Estic perseguint el Item i (o be l'ha agafat en Pacman o be l'ha agafat el fantasma3(objecte actual))
            //                             - Estic perseguint a en Pacman i ja l'he atrapat
            //                             - Estic perseguint a en Pacman i se m'han acabat els efectes de la mongeta 
            if (pacmanEsPerillos(distanciaPacman)){//Si en Pacman te mongeta i esta a una distancia < DISTANCIA_PERILLOSA
//                System.out.println("Em fico en estat de fugir perque en pacman es perillos:");
                mode = EMode.FUGIR;
                objectiu = null;
                ruta = null;
            }
            else if (objectiu != null){//Si tinc objectiu
                if (objectiu.element == EElement.PACMAN && !vullAtraparEnPacman()){
//                    System.out.println("Ja no m'interesa atrapar a en Pacman, canvio a mode Navegacio:");
                    objectiu = null;
                    mode = EMode.NAVEGACIO;
                }
                else if (elementEsItem(objectiu.element)){
//                    System.out.println("Estava perseguint un item i ja no esta, canvio a mode Navegacio:");
                    if (partida.obtenirItem() == null){//El Item ja no es troba en el Laberint
                        objectiu = null;
                        mode = EMode.NAVEGACIO;
                    }
                }
               
            }
//            else System.out.println(" CONCLUSIO: No canvio d'estat ");
            
        }
    }
    
     private void modeFugir(){
        //Si estic fugint es perque en Pacman m'esta perseguint, si encara tinc un ruta de maximitzar distancia llavors segueixo
        //altrament torno a calcular una altre ruta d'escapament.
         
        if (ruta == null || ruta.esBuida()){
//            System.out.println("Estic en mode fugida i m'he quedat sense ruta, torno a calcular una altre ");
            Punt puntPacman = partida.obtenirPuntPacman();
            ruta = gestorCami.trobarCamiMaximitzarDist(posicio, puntPacman);
        }
//      else System.out.println("Estic en mode fugida i tinc encara:" + ruta.obtenirMida()+ " moviments per fer");
    }
    
    private void modeNavegacio(){
        //Es Poden donar les seguents situacions:  - Acabo de sortir de un altre mode i no tinc objectiu --> Buscu monedes o vaig a la sortida.
        //                                         - Estic navegant i encara no he arribat al meu objectiu --> comprovar que en el punt del meu objectiu 
        //                                           encara troba el meu objectiu. ( Es a dir, en Pacman no m'ha pispat cap moneda). En cas contrari
        //                                           buscar un nou objectiu.
        //                                         - He arrivat al meu objectiu --> acabar objetiu i buscar un altre nou objectiu
        //                                         
        //reCalcularDistanciesMonedes();
        if (objectiu == null){//No tinc objetiu
            if (quedenMonedes()){
//                System.out.println("Estic en mode navegacio, no tinc objectiu i queden monedes, per tant buscu una moneda ");
                buscaMoneda();
            }
            else{
//                System.out.println("Estic en mode navegacio, no tinc objectiu i NO queden monedes, per tant buscu la sortida ");
                exitLaberint();
            }
        }
        else{//Si tinc objectiu
            //Haure de canviar de objectiu si: - He arrivat al meu objectiu o el meu objectiu ja no es troba en la mateixa posicio.
            if (objectiu.posicio.equals(posicio) || !objectiuVerificat()){
                objectiu = null;
                buscaMoneda();
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
//                System.out.println("Estic en mode seguiment, no tinc objectiu, no tinc objectiu, no queden monedes i no estic guanyant, per tant el meu objectiu:PACMAN ");
                Punt p = partida.obtenirPuntPacman();
                EElement e = EElement.PACMAN;
                objectiu = new Objectiu(p,e);
                ruta = gestorCami.trobarCamiMinim(posicio, objectiu.posicio);
            }
            else if (estatPersonatge == EEstatPersonatge.AMB_MONGETA && vullAtraparEnPacman()){
//                System.out.println("Estic en mode seguiment, no tinc objectiu i tinc mongeta, vaig a per en PACMAN ");
                Punt p = partida.obtenirPuntPacman();
                EElement e = EElement.PACMAN;
                objectiu = new Objectiu(p,e);
                ruta = gestorCami.trobarCamiMinim(posicio, objectiu.posicio);
            }
            else if (hiHaItemEspecialAProp()){
//                System.out.println("Estic en mode seguiment, no tinc objectiu i hi ha un Item pirulant, vaig a per el Item ");
                Punt p = partida.obtenirItem().posicio;
                EElement e = EElement.MONGETA;  //Fantasma 3 no diferencia entre els items, utilitzarem mongeta com a per defecte per a identificar un item
                objectiu = new Objectiu(p,e);
                ruta = gestorCami.trobarCamiMinim(posicio, objectiu.posicio);
            }
        }
        else{//Si tinc objectiu:
             //                   - Si es en Pacman segueixo a per ell
             //                   - Si es un Item, segueixo a per ell
            switch(objectiu.element){
                case PATINS:
                case MONEDES_X2:
                case MONGETA: objectiu.posicio = partida.obtenirItem().posicio;
                    break;
                case PACMAN: objectiu.posicio = partida.obtenirPuntPacman();
                    break;      
            }
//            System.out.println("Estic en mode seguiment, SI tinc objectiu i vaig cap a ell ");
            ruta = gestorCami.trobarCamiMinim(posicio, objectiu.posicio);
        }
            
    }
     /**
     * @brief Assigna la sortida com a objectiu.
     * @pre La sortida ha estat assignada en el objecte laberint.
     * @post Objecte actual te com a objectiu EElement.SORTIDA i ruta conte el cami mes curt cap a aquest objectiu.
     */
    private void exitLaberint(){
        EElement element = EElement.SORTIDA;
        Punt p = buscaElement(EElement.SORTIDA);
//        if ( p == null)System.out.println("IIEEEPP!! no queden monedes... he buscat la sortida i no la he trobat..... falla alguna cosa.");
//        else System.out.println("He trobat la sortida, vaig cap a ella");
        objectiu = new Objectiu(p,element);
        ruta = gestorCami.trobarCamiMinim(posicio, objectiu.posicio);
    }
    
    
    /**
     * @brief Diu si un moviment es vàlid.
     * @return Retorna cert si la posicio a la que accedirem efectuant el moviment mov, es diferent de EElement.PARET
     */
    private boolean movimentValid(EDireccio mov){
        return laberint.obtenirElement(posicio.generarPuntDesplasat(mov)) != EElement.PARET;
    }
    
    
    /**
     * @brief Assigna un objectiu del tipus Moneda al objecte actual. 
     * @pre Queden Monedes.
     * @post Objecte actual te com a objectiu EElement.MONEDA o EElement.MONEDA_DOBLE i ruta conte el cami mes curt cap a aquest objectiu.
     */
    private void buscaMoneda(){
        Punt p = buscaMonedaMesProxima();
        EElement element = laberint.obtenirElement(p);
        Objectiu nouObjectiu = new Objectiu (p, element);
        objectiu = nouObjectiu;
        ruta = gestorCami.trobarCamiMinim(posicio, objectiu.posicio);
        
    }

    
    /**
     * @brief Troba una moneda més próxima.
     * @pre Quede monedes en el Laberint
     * @return Retorna el Punt on es troba la moneda més próxima.
     */
    private Punt buscaMonedaMesProxima(){
        int mida = laberint.obtenirMidaCostatTauler();
        int monedesContades =0;
        int distanciaMinima = Integer.MAX_VALUE;
        Punt monedaMesProxima = null;
        synchronized(laberint){
            int totalMonedes = laberint.obtenirMonedes();
            for (int i = mida-1; i>= 0 && monedesContades < totalMonedes && distanciaMinima != 1; i--){//Mentres i < mida del tauler && no hagi calculat totes les monedes && no hagi trobat alguna moneda a distancia 1
                for (int j = mida-1; j >= 0 && monedesContades < totalMonedes && distanciaMinima != 1; j--){//Mentres i < mida del tauler && no hagi calculat totes les monedes && no hagi trobat alguna moneda a distancia 1
                    Punt p = new Punt(i,j);
                    EElement element = laberint.obtenirElement(p);
                    if (elementEsMoneda(element)){
                        int distanciaALaMoneda = gestorCami.trobarCamiMinim(posicio, p).obtenirMida();
                        if (distanciaALaMoneda < distanciaMinima){
                            distanciaMinima = distanciaALaMoneda;
                            monedaMesProxima = p;
                        }
                    }  
                }
            }
        }
        return monedaMesProxima;
    }
    
    /**
     * @brief Busca _element dintre de Laberint
     * @return Punt on es troba el _element. Si element no existeix retorna null.
     */
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
    private String donamNomMov(EDireccio dir){
        String res = "";
        switch (dir){
            case AVALL: res = "Avall";
                break;
            case AMUNT : res = "Amunt";
                break;
            case DRETA: res = "Dreta";
                break;
            case ESQUERRA: res = "ESQUERRA";
                break;
            default: res = "Quiet";
                break;
        }
        return res;
    }
    private String donamNomMode(EMode _mode){
        String res="";
        switch(mode){
            case FUGIR: res = "Fugir";
                break;
            case NAVEGACIO: res = "Navegacio";
                break;
            case SEGUIMENT: res = "Seguiment";
                break;
            default: res = "Falla algo";
                break;
        }
        return res;
    }
    
    private String donamNomElement(EElement element){
        String res = "";
        switch(element){
            case MONEDA: res = "moneda normal";
                break;
            case MONEDA_EXTRA: res = "moneda doble";
                break;
            case MONEDES_X2:
            case MONGETA:
            case PATINS: res = "Item especial";
                break;
            case PARET: res = "Paret";
                break;
            case PACMAN: res = "Pacman";
                break;
            case SORTIDA: res = "Sortida";
                break;
            case RES: res = "RES";
                break;
            default: res = "estic Fugint i no tinc cap objectiu en concret";
                break;
        }
        return res;
    }


    
    /**
     * @brief Diu si hi ha algun item a prop.
     * @return Retorna cert si existeix algun item especial dintre del Laberint a una distancia < DISTANCIA_CONSIDERABLE
     */
    private boolean hiHaItemEspecialAProp(){
        boolean hiHaItemAProp = false;
        Item item =  partida.obtenirItem();
        if (item != null){
            Punt p = item.posicio;
            int distancia = gestorCami.trobarCamiMinim(posicio, p).obtenirMida();
            if (distancia < DISTANCIA_CONSIDERABLE)hiHaItemAProp=true;
        }
        return hiHaItemAProp;
    }
    
    /**
     * @brief Conté part de la logica a seguir a la hora de decidir si Fantasma 3 vol perseguir a en PACMAN.
     * @return Retorna cert si es compleixen les seguents condicions:
     *     - Fantasma3 te EElement.MONGETA i en PACMAN te el 30 % de la  puntuacio que el Fantasma 3  
     *     - No queden monedes i Fantasma3 esta perdent. 
     */
    private boolean vullAtraparEnPacman(){
        boolean res = false;
        int puntuacioPacman = partida.obtenirPuntuacioPacman();
        if (estatPersonatge == EEstatPersonatge.AMB_MONGETA && this.punts * 0.30 < puntuacioPacman )res = true; //Si la puntuacio d'en Pacman es mes del 30% de la meva puntuacio, llavors m'interessa atrapar-lo
        if (!quedenMonedes() && !guanya)res = true;//Si no queden monedes i no estic guanyant llavors el vull atrapar
        return res;
    }
    
    /**
     * @brief Diu si element es un item.
     * @return Retorna cert si element es un item (Mongeta, Monedes_x2 o Patins).
     */
    private boolean elementEsItem(EElement element){
        return element == EElement.MONGETA || element == EElement.MONEDES_X2 || element == EElement.PATINS;
    }
    
    /**
     * @brief Diu si un element es del tipus Moneda
     * @return Retorna cert si element es EElement.MONEDA o EElement.MONEDA_EXTRA
     */
    private boolean elementEsMoneda(EElement element){
        return element == EElement.MONEDA ||element == EElement.MONEDA_EXTRA;
    }
    
    
    /**
     * @brief Conté la logica que defineix si fantasma3 considera perillos a en PACMAN
     * @return Retorna cert si PACMAN te mongeta i PACMAN esta a una distancia < DISTANCIA_PERILLOSA
     */
    private boolean pacmanEsPerillos(int distanciaPacman){
        //Es perillos si te mongeta i esta a una distancia < DISTANCIA_PERILLOSA
        return partida.obtenirEstatPacman() == EEstatPersonatge.AMB_MONGETA && distanciaPacman < DISTANCIA_PERILLOSA;
    }
    
    /**
     * @brief Diu si queden monedes dins del Laberint.
     * @return Retorna cert si queden monedes dins del Laberint.
     */
    private boolean quedenMonedes(){
        return laberint.obtenirMonedes() > 0;
    }
    
    /**
     * @brief Diu si objectiu actual de FANTASMA3 encara segueix en la mateixa posicio.
     * @return Retorna cert si objectiu actual de FANTASMA3 segueix en la mateixa posicio dintre del Laberint.
     */
    private boolean objectiuVerificat(){
        return laberint.obtenirElement(objectiu.posicio) == objectiu.element;
    }
    
    @Override
    protected void notificarPerduaEstat() {
        partida.assignarItemAEnemic(EElement.RES);
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
    //    private void omplenaMoneder(){
//        int mida = laberint.obtenirMidaCostatTauler();
//        for (int i = 0; i < mida; i++){
//            for( int j = 0; j < mida; j++){
//                Punt p = new Punt (i,j);
//                EElement element = laberint.obtenirElement(p);
//                if (elementEsMoneda(element)){
//                    int dist = gestorCami.trobarCamiMinim(posicio, p).size();
//                    Casella c = new Casella(p);
//                    c.afegirDistanciaAlObjectiu(dist);
//                    moneder.afegir(c);
//                }
//            }
//        }
//    }
//    private void reCalcularDistanciesMonedes(){
//        int mida = moneder.size();
//        long ini = System.currentTimeMillis();
//        for (int i = 0; i< mida; i++){
//            Casella c = moneder.obtenirElement(i);
//            c.afegirDistanciaAlObjectiu(gestorCami.trobarCamiMinim(posicio, c.obtenirPunt()).size());
//        }
//        long fi = System.currentTimeMillis();
////        System.out.println("TEMPS A CALCULAR EL CAMI MINIM DE " + mida +"  MONEDES -> "+ (fi-ini) + " ms");
//        moneder.ordena();
//        long fin = System.currentTimeMillis();
////        System.out.println("TEMPS A ORDENAR " + mida +"  MONEDES -> "+ (fin-fi) + " ms");
//        
//    }

    

}
