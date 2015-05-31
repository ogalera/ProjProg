package logica.laberints;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import logica.enumeracions.EDireccio;
import logica.enumeracions.EElement;
import logica.excepcions.EFormatLaberint;
import logica.log.Log;
import interficie.IPintadorLaberint;
import java.awt.Dimension;
import java.awt.event.KeyListener;
import javax.swing.ImageIcon;
import logica.Item;
import logica.Partida;
import logica.Punt;
import logica.Utils;

/**
 * @author oscar
 * @brief
 * class que contè un tauler amb elements que es poden desplasar, no coneix
 * en cap moment l'estat el seu estat sino que només coneix la seva representació.
 * 
 * El laberint s'ha de poder comunicar amb la partida per noficiar quan no queden
 * monedes o quan s'ha obtingut un item (mongeta, patins o monedes x 2).
 * 
 * També caldrà que pugui enviar missatges a un pintador per així poder representar-se de forma gràfica.
 * 
 * @invariant
 * nMonedes conté en tot moment el nombre de monedes que hi ha en el laberint,
 * costat >= Utils.Constants.MINIM_COSTAT_LABERINT, tauler és una matriu cuadrada de costat x costat,
 * pintador no pot ser null i nMonedesPerItem > 0
 */
public class Laberint {
    protected EElement tauler[][]; /**<matriu de representacions per els elements que hi ha en el laberint**/
    
    protected Log log;
    
    protected int costat = -1; /**<costat del laberint, tot laberint ha de ser costat x costat*/
    
    protected Partida partida;/**<partida de la qual forma part el laberint i que li envia missatges*/
    
    protected int nMonedes = 0;/**<nombre de monedes que hi ha a en el laberint, al final de la partida
                                * hi haurà de valdre 0*/
    
    protected IPintadorLaberint pintador; /**<Representació gràfica del laberint, nosaltres no sabem
                                            * sobre que estem pintant el laberint però tampoc ens importa,
                                            * ens limitem a enviar missatges per pintar com evoluciona el laberint*/
    
    protected int nMondesPerItem; /**<nombre de monedes que s'han de capturar per que aparegui un item en la partida
                                      aquest valor sempre ha de cumplir 0 < nMonedesPerItem < nMonedes*/
    
    /**
     * @pre el fitxer existeix i té un format correcte;
     * @post em creat un laberint a partir del fitxer que forma part de la partida i
     * que la seva representació gràfica serà sobre pintadorLaberint;
     */
    public Laberint(String fitxer,
                    Partida partida, 
                    IPintadorLaberint pintadorLaberint) throws EFormatLaberint{
        this.pintador = pintadorLaberint;
        log = Log.getInstance(Laberint.class);
        log.afegirDebug("Carreguem un laberint del fitxer "+fitxer);
        File f = new File(fitxer);
        this.partida = partida;
        
        try(BufferedReader br = new BufferedReader(new FileReader(f))){
            String linia;
            if((linia = br.readLine()) != null){
                this.costat = linia.split(" ").length;
                this.tauler = new EElement[costat][costat];
                int n = 0;
                EElement [] l = parseLiniaLaberint(linia);
                if(l.length != costat) throw new EFormatLaberint("El format del laberint no és correcte");
                this.tauler[n++] = l;
                while((linia = br.readLine()) != null){
                    l = parseLiniaLaberint(linia);
                    if(l.length != costat) throw new EFormatLaberint("El format del laberint no és correcte");
                    this.tauler[n++] = l;
                }
                if(costat != n){
                    throw new EFormatLaberint("El format del laberint no és correcte");
                }
            }
        }
        catch(IOException fnfe){
            throw new EFormatLaberint("No s'ha pogut llegir el fitxer per importar el laberint, missatge:\n"+fnfe.getMessage());
        }
        this.nMonedes = numeroMonedes();
        this.nMondesPerItem = (int) (nMonedes*Utils.Constants.TAN_X_CENT_MONEDES_ITEM);
        if(nMondesPerItem == 0) nMondesPerItem = 1;
    }
    
    /**
     * @pre --;
     * @post em parsejat linia sobre un array de EElement
     */
    private EElement [] parseLiniaLaberint(String linia) throws EFormatLaberint{
        String camps[] = linia.split(" ");
        EElement [] liniaElements = new EElement[camps.length];
        for(int i = 0; i < costat; i++){
            liniaElements[i] = EElement.buscarElementPerId(Integer.parseInt(camps[i]));
        }
        return liniaElements;
    }
    
    /**
     * @pre elements ha de ser una matriu cuadrada;
     * @post em creat un laberint a partir de elements i forma part de partida;
     */
    public Laberint(EElement elements[][], Partida partida){
        log = Log.getInstance(Laberint.class);
        log.afegirDebug("Carreguem un laberint des de una matriu quadrada");
        this.costat = elements[0].length;
        this.tauler = elements;
        this.partida = partida;
        this.nMonedes = numeroMonedes();
        this.nMondesPerItem = (int) (nMonedes*Utils.Constants.TAN_X_CENT_MONEDES_ITEM);
        if(nMondesPerItem == 0) nMondesPerItem = 1;
    }
    
    /**
     * @pre --;
     * @post em creat un laberint que forma part de partida;
     */
    protected Laberint(Partida partida){
        log = Log.getInstance(Laberint.class);
        this.partida = partida;
    }
    
    /**
     * @pre --;
     * @post em retornat el nombre de monedes que hi ha en el laberint;
     */
    protected final int numeroMonedes(){
        int numMonedes = 0;
        for(int i = 0; i < costat; i++){
            for(int j = 0; j < costat; j++){
                if(tauler[i][j] == EElement.MONEDA || tauler[i][j] == EElement.MONEDA_EXTRA){
                    numMonedes++;
                }
            }
        }
        return numMonedes;
    }
    
    /**
     * @pre: --;
     * @post: retornem si la posició és valida dins del tauler, per valida entenem
     * que no surt del rang del tauler i no és paret;
     */
    public boolean posicioValida(Punt posicio){
        return this.obtenirElement(posicio) != EElement.PARET;
    }
    
    /**
     * @pre: --;
     * @post: retornem l'element ubicat en posició, si la posició no és valida es retorna PARET;
     */
    public EElement obtenirElement(Punt posicio){
        EElement element = EElement.PARET;
        int fila = posicio.obtenirFila();
        int columna = posicio.obtenirColumna();
        if(fila >= 0 && fila < costat && columna >= 0 && columna < costat){
           element = tauler[fila][columna];
        }
        return element;
    }
    
    /**
     * @pre en origen hi ha un item, origen desplaçat per direccio produeix una 
     * posició valida i direccio != QUIET;
     * @post en cas que origen desplaçat per direcció sigui legal (per legal 
     * entenem que no trapitja un enemic ni a en pacman)
     * 
     * llavors: em desplasat item de origen al punt generat amb la direcció i en origen
     *          hi em restaurat elementARestaurar, em retornat l'element capturat;
     * 
     * altrament, el moviment no és legal i
     *          retornem null;
     */
    public synchronized EElement moureItem(Punt origen, EDireccio direccio, EElement elementARestaurar){
        ///Em de rebre elementARestaurar ja que al desplasar-se un item pot contenir monedes a sota
        ///que s'han de restaurar per evitar inconsistencies;
        Punt desti = origen.generarPuntDesplasat(direccio);
        int filaDesti = desti.obtenirFila();
        int columnaDesti = desti.obtenirColumna();
        EElement elementTrapitjat = tauler[filaDesti][columnaDesti];
        if(!elementTrapitjat.esEnemic() && elementTrapitjat != EElement.PACMAN){
            ///no trapitjem a en pacman ni a un enemic;
            int filaOrigen = origen.obtenirFila();
            int columnaOrigen = origen.obtenirColumna();
            tauler[filaDesti][columnaDesti] = tauler[filaOrigen][columnaOrigen];
            tauler[filaOrigen][columnaOrigen] = elementARestaurar;
            pintador.pintarMovimentItem(origen, direccio, elementARestaurar.obtenirImatge());
        }
        else{
            ///el moviment no és legal, volem trapitjar a en pacman o a un enemic;
            elementTrapitjat = null;
        }
        return elementTrapitjat;
    }
    
    /**
     * @pre en origen hi ha un personatge, origen desplaçat per direccio produeix 
     * una posició valida i direccio != QUIET;
     * @post en cas que origen desplaçat per direcció sigui legal (per legal 
     * entenem que no trapitja un enemic ni a en pacman a no ser que tingui super poders)
     * 
     * llavors: em desplasat personatge de origen al punt generat amb la direcció i en origen
     *          hi em deixat RES, em retornat l'element capturat;
     * 
     * altrament, el moviment no és legal:
     *          retornem null;
     */
    public synchronized EElement mourePersonatge(Punt origen, EDireccio direccio, ImageIcon imatge, boolean superPoders){
        Punt desti = origen.generarPuntDesplasat(direccio);
        int filaDesti = desti.obtenirFila();
        int columnaDesti = desti.obtenirColumna();
        EElement elementObtingut = tauler[filaDesti][columnaDesti];
        ///Per que el moviment sigui "legal" no has de trapitjar a un enemic o a en pacman
        ///a no ser que tinguis "super poders" (al estar en poseció de la mongeta)
        if(!elementObtingut.esEnemic() && elementObtingut != EElement.PACMAN){
            ///Moviment legal
            int filaOrigen = origen.obtenirFila();
            int columnaOrigen = origen.obtenirColumna();
            EElement elementOrigen = tauler[filaOrigen][columnaOrigen];
            switch(elementObtingut){
                case MONEDA:
                case MONEDA_EXTRA:{
                    ///Em capturat una moneda
                    nMonedes--;
                    ///desplaçem al personatge i deixem RES en l'origen;
                    tauler[filaOrigen][columnaOrigen] = EElement.RES;
                    tauler[filaDesti][columnaDesti] = elementOrigen;
                    ///pintem el moviment;
                    pintador.pintarMovimentPersonatge(origen, direccio, imatge);
                    monedaCapturada();
                }break;
                case SORTIDA:{
                    ///S'ha arribat a la sortida, cal finalitzar la partida;
                    tauler[filaOrigen][columnaOrigen] = EElement.RES;
                    tauler[filaDesti][columnaDesti] = elementOrigen;
                    partida.finalitzarPartida();
                }break;
                case RES:{
                    ///Si no s'ha capturat res l'únic que cal és pintar el moviment;
                    tauler[filaOrigen][columnaOrigen] = EElement.RES;
                    tauler[filaDesti][columnaDesti] = elementOrigen;
                    pintador.pintarMovimentPersonatge(origen, direccio, imatge);
                }break;
                case MONGETA:
                case PATINS:
                case MONEDES_X2:{
                    ///S'ha capturat un item, que tenia sota?
                    EElement elementTrapitjat = partida.obtenirItem().obtenirElementTrapitgat();
                    if(elementTrapitjat == EElement.MONEDA || elementTrapitjat == EElement.MONEDA_EXTRA){
                        ///el que tenia sota l'item era una moneda per tant cal decrementar el nombre
                        ///de monedes i mirar si toca sortejar item;
                        nMonedes--;
                        monedaCapturada();
                    }
                    tauler[filaOrigen][columnaOrigen] = EElement.RES;
                    tauler[filaDesti][columnaDesti] = elementOrigen;
                    pintador.pintarMovimentPersonatge(origen, direccio, imatge);
                }break;
            }
        }
        else if(superPoders){
            ///Seria "ilegal" però no passa res per que tenim super poders per tant
            ///retornem l'element tocat;
            elementObtingut = tauler[desti.obtenirFila()][desti.obtenirColumna()];
        }
        else {
            ///Moviment "il·legal" retornarem null;
            elementObtingut = null;
        }
        
        return elementObtingut;
    }

    /**
     * @pre --;
     * @post es valora si cal sortejar sortida o cal sortejar un nou item
     * que formarà part de la partida;
     */
    private synchronized void monedaCapturada(){
        if(nMonedes == 0){
            ///No queden monedes, requisit únic i obligatori per sortejar una sortida
            Punt sortida = sortejarPosicioBuida();
            tauler[sortida.obtenirFila()][sortida.obtenirColumna()] = EElement.SORTIDA;
            pintador.pintarSortida(sortida);
            partida.assignarGuanyador();
        }
        else if(nMonedes%nMondesPerItem == 0 && !partida.hiHaItemEspecial()){
            ///No hi havia un item voltant per la partida i em obtingut un nombre 
            ///de monedes considerat per llençar-ne un de nou
            Punt puntItem = sortejarPosicioBuida();
            EElement item = sortejarItem();
            Item nouItem = new Item(partida, item, obtenirElement(puntItem), this, puntItem);
            tauler[puntItem.obtenirFila()][puntItem.obtenirColumna()] = item;
            pintador.pintarNouItem(puntItem, item);
            partida.assignarItemEspecial(nouItem);
        }
    }
    
    /**
     * @pre --;
     * @post em retornat un item pseudoaleatoriament;
     */
    private synchronized EElement sortejarItem(){
        int index = Utils.obtenirValorAleatori(3);
        switch(index){
            case 1:{
                return EElement.PATINS;
            }
            case 2:{
                return EElement.MONEDES_X2;
            }
            default:{
                return EElement.MONGETA;
            }
        }
    }
    
    /**
     * @pre --;
     * @post em retornat un punt on hi havia RES
     */
    private synchronized Punt sortejarPosicioBuida(){
        int fila;
        int columna;
        EElement element;
        do{
            fila = Utils.obtenirValorAleatori(costat);
            columna = Utils.obtenirValorAleatori(costat);
            element = tauler[fila][columna];
        }while(element != EElement.RES);
        return new Punt(fila, columna);
    }
    
    @Override
    public String toString(){
        String resultat = "";
        for(int i = 0; i < costat; i++){
            for(int j = 0; j < costat; j++){
                resultat += this.tauler[i][j].obtenirLletraRepresentacio()+" ";
            }
            resultat += "\n";
        }
        return resultat;
    }
    
    /**
     * @pre --
     * @post retornem la posició on hi ha en pacman;
     */
    public Punt obtenirPosicioPacman(){
        Punt posicio = null;
        boolean trobat = false;
        int i = 0;
        while(!trobat && i < costat){
            int j = 0;
            while(!trobat && j < costat){
                if(tauler[i][j] == EElement.PACMAN){
                    trobat = true;
                    posicio = new Punt(i, j);
                }
                j++;
            }
            i++;
        }
        return posicio;
    }
    
    /**
     * @pre --;
     * @post em retornat la posició del tauler on hi ha l'enemic;
     */
    public Punt obtenirPosicioInicialEnemic(){
        Punt posicio = null;
        boolean trobat = false;
        int fila = 0;
        while(!trobat && fila < costat){
            int columna = 0;
            while(!trobat && columna < costat){
                if(tauler[fila][columna].esEnemic()){
                    trobat = true;
                    posicio = new Punt(fila, columna);
                }
                columna++;
            }
            fila++;
        }
        return posicio;
    }
    
    /**
     * @pre --;
     * @post em assignat un controlador de teclat asociat al pintador;
     */
    public void assignarControladorTeclat(KeyListener listener){
        ///Aquest controlador l'utilitzarem per moure en pacman per el laberint
        ///utilitzant les tecles de direcció del keyboard;
        pintador.assignarControladorTeclat(listener);
    }
    
    /**
     * @pre --;
     * @post em retornat la mida del costat del laberint;
     */
    public int obtenirMidaCostatTauler(){
        return this.costat;
    }

    /**
     * @pre --;
     * @post em pintat el laberint sobre un surface gràfic;
     */
    public void pintarLaberint(){
        pintador.pintarLaberint(this);
    }

    /**
     * @pre --;
     * @post em obtingut la mida que ha de fer una imatge dins d'una casella del tauler;
     */
    public Dimension obtenirMidaImatge(){
        return pintador.obtenirMidaImatge();
    }
    
    /**
     * @pre --;
     * @post em retornat el nombre de monedes que hi ha en el laberint;
     */
    public synchronized int obtenirMonedes(){
        return nMonedes;
    }
}