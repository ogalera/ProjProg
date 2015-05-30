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
    private static final int DISTANCIA_PERILLOSA = 10;
    private boolean esticFugint;
    
    public Item(Partida partida, EElement tipusElement, EElement elementTrapitjat, Laberint laberint, Punt inici){
        super(partida, tipusElement.obtenirImatge(), laberint, inici, Utils.Constants.FREQUENCIA_ITEM);
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
        int distanciaPacman = gestorCami.trobarCamiMinim(posicio, posicioPacman).obtenirMida();
        int distanciaEnemic = gestorCami.trobarCamiMinim(posicio, posicioEnemic).obtenirMida();
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
        EDireccio res;
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
    
    /**
     * @brief Càlcul aleatori d'una posicio valida.
     * @details Si ningu persegueix a Item, aquest anira pasejant per el Laberint. Si
     * no te cap ruta, buscarà una punt aleatori del laberint i anira fins allà.
     * S'enten com a vàlid un punt que no sigui paret, que existeixi dins de Laberint i
     * que no sigui el punt on es troba actualment Item.
     * @return Punt com a desti vàlid dins de Laberint.
     */
    private Punt obtenirPosicioAleatoria(){
        boolean valid = false;
        Punt p = null;
        while (!valid){
            p = new Punt (Utils.obtenirValorAleatori(laberint.obtenirMidaCostatTauler() -1),Utils.obtenirValorAleatori(laberint.obtenirMidaCostatTauler() -1));
            valid = destiValid(p);
        }
        return p;
    }
    
    /**
     * @brief Diu si un desti p es vàlid.
     * @return Retorna cert si desti p no correspont a un EElement.PARET && p != posicioActual
     */
    private boolean destiValid(Punt p){
        boolean valid = false;
        EElement element = laberint.obtenirElement(p);
        if (element != EElement.PARET && p != posicio)valid = true;
        return valid;
    }
    /**
     * @brief Diu si un moviment es vàlid.
     * @return Retorna cert si el punt calculat amb posicio + mov != EElement.PARET
     */
    private boolean movimentValid(EDireccio mov){
        EElement e = laberint.obtenirElement(posicio.generarPuntDesplasat(mov));
        return e != EElement.PARET;
    }

    @Override
    public String nomItemMovible(){
        return "Item "+tipusElement;
    }

    public EElement obtenirElementTrapitgat(){
        return elementTrapitjat;
    }
    
    @Override
    public EElement realitzarMoviment() {
        if(seguentMoviment != EDireccio.QUIET){
            EElement tmp = laberint.moureItem(posicio, seguentMoviment, elementTrapitjat);
            if(tmp != null && tmp != EElement.MONEDES_X2 && tmp != EElement.MONGETA && tmp != EElement.PATINS){
                elementTrapitjat = tmp;
                if(!ruta.esBuida()) ruta.eliminarMoviment();
                posicio = posicio.generarPuntDesplasat(seguentMoviment);
            }
        }
        return null;
    }
    
    
}
