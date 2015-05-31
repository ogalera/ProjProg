package logica;

import interficie.FLogin;
import logica.laberints.Laberint;
import logica.enumeracions.EElement;
import logica.excepcions.EFinalitzarPartida;
import logica.excepcions.EFormatLaberint;
import logica.excepcions.EIniciarPartida;
import logica.log.Log;
import interficie.IPintadorLaberint;
import interficie.IPintadorPartida;
import javax.swing.ImageIcon;
import logica.Personatge.EEstatPersonatge;
import logica.controladors_pacman.IControlador;
import logica.enumeracions.ELaberintsPredefinits;
import logica.laberints.LaberintAleatori;
import logica.laberints.LaberintLinealHoritzontal;
import logica.laberints.LaberintLinealVertical;


/**
 * @author oscar
 * @brief
 * pantalla on hi ha un pacman i un enemic que es van desplaçant per un laberint
 * ple de monedes amb l'objectiu de aconseguir-ne el maxim nombre possible.
 * 
 * la partida finalitza quan no queden monedes.
 * 
 * @invariant
 * laberint != null, pacman != null, enemic != null i pintadorPartida != null
 */
public class Partida {
    private final Laberint laberint; /**<laberint on es desenvolupa el joc*/
    private Personatge pacman;/**<personatge principal*/
    private Personatge enemic;/**<Enemic controlat per la màquina*/
    private Item itemEspecial = null; /**<Item especial que corre pel laberint,
                                            aquest item apareix i desapareix quan
                                            algú l'agafa, per tant, nosaltres entenem
                                            qui hi ha un item a la partida si el seu
                                            valor no és null*/
    private IPintadorPartida pintador; /**<Frame per visualitzar de forma gràfica la partida*/

    /**
     * Instants de temps en que inicia i finalitza la partida (respectivament)
     * aquest atributs els tenim per tal de generar informació del cicle de vida
     * de la partida;
     */
    private long momentInici = -1; /**<Instant de temps en que s'inicia la partida*/
    private long momentFi = -1;/**<Instant de temps en que finalitza la partida*/
    
    private final Log log;
    
    /**
     * @pre: fitxer existeix i conté un laberint en format correcte;
     * @post: em carregat una partida a partir del laberint especificat per parametre;
     */
    public Partida(String fitxer, 
                    IPintadorPartida pintadorPartida,
                    IPintadorLaberint pintadorLaberint,
                    IControlador controlador) throws EFormatLaberint{
        log = Log.getInstance(Partida.class);
        pintador = pintadorPartida;
        log.afegirDebug("Carreguem la partida a traves del fitxer "+fitxer);
        laberint = new Laberint(fitxer, this, pintadorLaberint);
        crearPacman(controlador);
        crearEnemic();
    }
    
    /**
     * @pre Utils.Constants.MINIM_COSTAT_LABERINT <= costat i enemic
     * és realment un enemic.
     * @post em creat una partida amb un laberint concret de mida costat
     * amb en pacman a la posició [0, 0] un enemic en la posicio [costat-1, costat-1], 
     * amb un pintador per la partida i per el laberint i un controlador per moure
     * an pacman.
     */
    public Partida(ELaberintsPredefinits laberint,
                    int costat, 
                    EElement enemic, 
                    IPintadorPartida pintadorPartida,
                    IPintadorLaberint pintadorLaberint,
                    IControlador controlador){
        log = Log.getInstance(Partida.class);
        this.pintador = pintadorPartida;
        ///Creem el laberint del tipus que ens han dit.
        switch(laberint){
            case LABERINT_LINEAL_HORITZONTAL:{
                this.laberint = new LaberintLinealHoritzontal(this, costat, enemic, pintadorLaberint);
            }break;
            case LABERINT_LINEAL_VERTICAL:{
                this.laberint = new LaberintLinealVertical(this, costat, enemic, pintadorLaberint);
            }break;
            default:{
                this.laberint = new LaberintAleatori(this, costat, enemic, pintadorLaberint);
            }break;
        }
        crearEnemic();
        crearPacman(controlador);
    }
    
    /**
     * @pre el laberint és valid.
     * @post em creat l'enemic que juga la partida.
     */
    private void crearEnemic(){
        Punt posicioFantasma = laberint.obtenirPosicioInicialEnemic();
        EElement tipusEnemic = laberint.obtenirElement(posicioFantasma);
        switch(tipusEnemic){
            case FANTASMA1:{
                //Tenim un enemic de tipus FANTASMA1
                enemic = new Fantasma1(this, laberint, posicioFantasma);
            }break;
            case FANTASMA2:{
                //Tenim un enemic de tipus FANTASMA2
                enemic = new Fantasma2(this, laberint, posicioFantasma);
            }break;
            case FANTASMA3:{
                //Tenim un enemic de tipus FANTASMA3
                enemic = new Fantasma3(this, laberint, posicioFantasma);
            }
        }
        log.afegirDebug("L'enemic es de tipus "+enemic+" i esta en la posicio "+posicioFantasma);
    }
    
    /**
     * @pre el laberint és valid.
     * @post em creat en pacman que juga la partida.
     */
    private void crearPacman(IControlador controlador){
        Punt posicioPacman = laberint.obtenirPosicioPacman();
        pacman = new Pacman (this, laberint, controlador, posicioPacman);
    }
    
    /**
     * @pre la partida no ha sigut iniciada anteriorment.
     * @post em iniciat la partida juntament amb tots els seus elements.
     */
    public void iniciarPartida(){
        laberint.pintarLaberint();
        pintador.assignarPartida(this);
        pintador.pintarIniciPartida();
        if(momentInici != -1) throw new EIniciarPartida("La partida ja esta iniciada");
        if(momentFi != -1) throw new EIniciarPartida("No pots iniciar una partida que ja s'ha finalitzat");
        momentInici = System.currentTimeMillis();
        log.afegirDebug("S'ha iniciat la partida a les "+Utils.obtenirHoraSistema());
        Audio.reprodueixInici();
        enemic.iniciarItemMovible();
        pacman.iniciarItemMovible();
    }
    
    /**
     * @pre la partida està iniciada i no finalitzada.
     * @post em finalitzat la partida amb tots els seus elements.
     */
    public void finalitzarPartida(){
        synchronized(laberint){
            if(momentInici == -1) throw new EFinalitzarPartida("No pots finalitzar una partida sense haver-la iniciat abans");
            if(momentFi != -1) throw new EFinalitzarPartida("No pots finalitzar una partida ja finalitzada");
            enemic.finalitzarItem();
            pacman.finalitzarItem();
            momentFi = System.currentTimeMillis();
            log.afegirDebug("S'ha finalitzat la partida a les "+Utils.obtenirHoraSistema());
            long diferencia = momentFi-momentInici;
            log.afegirDebug("La partida a durat un total de "+Utils.obtenirMomentEnFormatHoraMinutsSegons(diferencia));
            if(pacman.obtenirPunts() > enemic.obtenirPunts()){
                FLogin.obtenirUsuari().pantallaSuperada(pacman.obtenirPunts());
                pintador.pintarFinalPartida(true);
            }
            else pintador.pintarFinalPartida(false);
            System.out.println("TEMPS TOTAL CALCUL PACMAN "+pacman.obtenirTempsTotalCalcul()+"s");
            System.out.println("TEMPS TOTAL CALCUL ENEMIC "+enemic.obtenirTempsTotalCalcul()+"s");
        }
    }
    
    /**
     * @pre la partida no ha sigut finalitzada
     * @post em tancat la partida matant al enemic, an pacman i al item (si ni 
     * havia en aquest moment).
     */
    public void tancarPartida(){
        enemic.finalitzarItem();
        pacman.finalitzarItem();
        if(itemEspecial != null) itemEspecial.finalitzarItem();
    }
    
    /**
     * @pre el laberint ja no té monedes
     * @post em assignat un guanyador (qui té més punts) i ara toca anar
     * cap la sortida.
     */
    public void assignarGuanyador(){
        if(itemEspecial != null){
            ///Si hi havia item el finalitzem
            itemEspecial.finalitzarItem();
            pintador.pintarItemPartida(null);
        }
        if(pacman.obtenirPunts() > enemic.obtenirPunts()){
            ///Està guanyant en pacman.
            log.afegirDebug("GUANYA PACMAN");
            pacman.assignarGuanya(true);
            enemic.assignarGuanya(false);
        }
        else {
            ///Està guanyant el fantasma.
            log.afegirDebug("GUANYA ENEMIC");
            pacman.assignarGuanya(false);
            enemic.assignarGuanya(true);
        }
    }
   
    /**
     * @pre hi ha un item en la partida
     * @post s'ha finalitzat l'item i em retornat que contenia sota seu.
     */
    public EElement itemCapturat(){
        EElement elementTrapitgat = itemEspecial.obtenirElementTrapitgat();
        this.itemEspecial.finalitzarItem();
        this.itemEspecial = null;
        this.pintador.pintarItemPartida(null);
        return elementTrapitgat;
    }
    
    /**
     * @pre --
     * @post em assignat un nou item a la partida.
     */
    public void assignarItemEspecial(Item item){
        this.itemEspecial = item;
        this.pintador.pintarItemPartida(itemEspecial.imatgePerfil);
        Audio.reprodueixAparicioItem();
    }
    
    /**
     * @pre --
     * @post diu si hi ha un item en la partida.
     */
    public boolean hiHaItemEspecial(){
        return itemEspecial != null;
    }
    
    /**
     * @pre --
     * @post em retornat l'imatge de en pacman.
     */
    public ImageIcon obtenirImatgePacman(){
        return pacman.imatgePerfil;
    }
    
    /**
     * @pre --
     * @post em retornat l'imatge del fantasma.
     */
    public ImageIcon obtenirImatgeFantasma(){
        return enemic.imatgePerfil;
    }
    
    /**
     * @pre --
     * @post em retornat el laberint.
     */
    public Laberint obtenirLaberint(){
        return this.laberint;
    }
   
    /**
     * @pre --
     * @post em pintat els punts que té en pacman.
     */
    public void assignarPuntsPacman(int punts){
        pintador.pintarPuntsPacman(punts);
    }
   
    /**
     * @pre --
     * @post em pintat els punts que té el fantasma.
     * @param punts 
     */
    public void assignarPuntsEnemic(int punts){
        pintador.pintarPuntsEnemic(punts);
    }
   
    /**
     * @pre --
     * @post em posat a 0 els punts del fantasma i n'hem retornat la quantitat
     * que tenia
     */
    public synchronized int reiniciarPuntsEnemic(){
        synchronized(laberint){
            Audio.reprodueixSubstraccioPunts();
            pintador.pintarPuntsEnemic(0);
            return enemic.reiniciarPunts();
        }
    }
   
    /**
     * @pre --
     * @post em posat a 0 els punts den pacman i n'hem retornat la quantitat
     * que tenia
     */
    public int reiniciarPuntsPacman(){
        synchronized(laberint){
            Audio.reprodueixSubstraccioPunts();
            pintador.pintarPuntsPacman(0);
            Audio.reprodueixSubstraccioPunts();
            return pacman.reiniciarPunts();
        }
    }
   
    /**
     * @pre --
     * @post em pintat el nou item que té en pacman.
     */
    public void assignarItemAPacman(EElement item){
        pintador.pintarItemPacman(item.obtenirImatge());
    }
   
    /**
     * @pre --
     * @post em pintat el nou item que té l'enemic
     */
    public void assignarItemAEnemic(EElement item){
        pintador.pintarItemEnemic(item.obtenirImatge());
    }
   
    /**
     * @pre --
     * @post em retornat la posició den pacman dins del laberint.
     */
    public Punt obtenirPuntPacman(){
       return pacman.posicio;
    }
    
    /**
     * @pre --
     * @post em retornat la posició del enemic dins del laberint.
     */
    public Punt obtenirPuntEnemic(){
        return enemic.posicio;
    }  
    
    /**
     * @pre --
     * @post em retornat l'estat en que es troba en pacman en aquest moment.
     */
    public EEstatPersonatge obtenirEstatPacman(){
        return pacman.obtenirEstatPersonatge();
    }
    
    /**
     * @pre --
     * @post em retornat l'item que hi ha a la partida (null si no n'hi ha cap)
     */
    public Item obtenirItem(){
        return itemEspecial;
    }
    
    /**
     * @pre --
     * @post em retornat el nombre de punts que porta en pacman.
     */
    public int obtenirPuntuacioPacman(){
        return pacman.obtenirPunts();
    }
}
