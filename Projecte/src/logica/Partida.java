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
 */
public class Partida {
    /**
     * Laberint on es desenvolupa el joc;
     */
    private final Laberint laberint;
    
    /**
     * Personatge principal;
     */
    private Personatge pacman;
    
    /**
     * Enemic controlat per la màquina;
     */
    private Personatge enemic;
    
    private Item itemEspecial = null;
    
    /**
     * Frame per visualitzar per pantalla la partida
     */
    private IPintadorPartida pintador;

    /**
     * Instants de temps en que inicia i finalitza la partida (respectivament)
     * aquest atributs els tenim per tal de generar informació del cicle de vida
     * de la partida;
     */
    private long momentInici = -1;
    private long momentFi = -1;
    
    /**
     * Log per anar anotant les tot el que passa durant el cicle de vida de
     * la partida;
     */
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
        pacman = obtenirPacman(controlador);
        enemic = obtenirEnemic();
    }
    
    public Partida(ELaberintsPredefinits laberint,
                    int costat, 
                    EElement enemic, 
                    IPintadorPartida pintadorPartida,
                    IPintadorLaberint pintadorLaberint,
                    IControlador controlador){
        log = Log.getInstance(Partida.class);
        this.pintador = pintadorPartida;
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
        this.enemic = obtenirEnemic();
        this.pacman = obtenirPacman(controlador);
    }
    
    /**
     * @pre: el laberint té un únic enemic;
     * @post: em retornat l'enemic;
     */
    private Personatge obtenirEnemic(){
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
        return enemic;
    }
    
    private Personatge obtenirPacman(IControlador controlador){
        Punt posicioPacman = laberint.obtenirPosicioPacman();
        pacman = new Pacman (this, laberint, controlador, posicioPacman);
        return pacman;
    }
    
    /**
     * @pre:la partida no ha sigut iniciada anteriorment;
     * @post:em iniciat la partida juntament amb tots els seus elements;
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
     * @pre: la partida està iniciada i no finalitzada;
     * @post: em finalitzat la partida amb tots els seus elements;
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
    
    public void tancarPartida(){
        enemic.finalitzarItem();
        pacman.finalitzarItem();
        if(itemEspecial != null) itemEspecial.finalitzarItem();
    }
    
    public void assignarGuanyador(){
        if(itemEspecial != null){
            itemEspecial.finalitzarItem();
        }
        if(pacman.obtenirPunts() > enemic.obtenirPunts()){
            System.out.println("GUANYA PACMAN");
            pacman.assignarGuanya(true);
            enemic.assignarGuanya(false);
        }
        else {
            System.out.println("GUANYA ENEMIC");
            pacman.assignarGuanya(false);
            enemic.assignarGuanya(true);
        }
    }
   
    public EElement itemCapturat(){
        EElement elementTrapitgat = itemEspecial.obtenirElementTrapitgat();
        this.itemEspecial.finalitzarItem();
        this.itemEspecial = null;
        this.pintador.pintarItemPartida(null);
        return elementTrapitgat;
    }
    
    public void assignarItemEspecial(Item item){
        Audio.reprodueixAparicioItem();
        this.itemEspecial = item;
        this.pintador.pintarItemPartida(itemEspecial.imatgePerfil);
    }
    
    public boolean hiHaItemEspecial(){
        return itemEspecial != null;
    }
    
    public ImageIcon obtenirImatgePacman(){
        return pacman.imatgePerfil;
    }
    
    public ImageIcon obtenirImatgeFantasma(){
        return enemic.imatgePerfil;
    }
    
   public Laberint obtenirLaberint(){
       return this.laberint;
   }
   
   public void assignarPuntsPacman(int punts){
       pintador.pintarPuntsPacman(punts);
   }
   
   public void assignarPuntsEnemic(int punts){
       pintador.pintarPuntsEnemic(punts);
   }
   
   public synchronized int reiniciarPuntsEnemic(){
        synchronized(laberint){
            Audio.reprodueixSubstraccioPunts();
            pintador.pintarPuntsEnemic(0);
            return enemic.reiniciarPunts();
        }
   }
   
   public int reiniciarPuntsPacman(){
        synchronized(laberint){
            Audio.reprodueixSubstraccioPunts();
            pintador.pintarPuntsPacman(0);
            return pacman.reiniciarPunts();
        }
   }
   
   public void assignarItemAPacman(EElement item){
       pintador.pintarItemPacman(item.obtenirImatge());
   }
   
   public void assignarItemAEnemic(EElement item){
       pintador.pintarItemEnemic(item.obtenirImatge());
   }
   
   public void treureItemPacman(){
       pintador.pintarItemPacman(null);
   }
   
   public void treureItemEnemic(){
       pintador.pintarItemEnemic(null);
   }
   
   public Punt obtenirPuntPacman(){
       return pacman.posicio;
   }
   
   public Punt obtenirPuntEnemic(){
       return enemic.posicio;
   }  
   
   ///////////////////////////////////////////////////////////////////
   public EEstatPersonatge obtenirEstatPacman(){
       return pacman.obtenirEstatPersonatge();
   }
   
   public Item obtenirItem(){
       return itemEspecial;
   }
   
   public int obtenirPuntuacioPacman(){
       return pacman.obtenirPunts();
   }
   
   public int obtenirPuntsPacman(){
       return pacman.obtenirPunts();
   }
}
