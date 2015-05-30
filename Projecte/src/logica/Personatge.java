package logica;

import java.util.Timer;
import java.util.TimerTask;
import javax.swing.ImageIcon;
import logica.enumeracions.EDireccio;
import logica.enumeracions.EElement;
import logica.laberints.Laberint;
import logica.historic_moviments.HistoricMoviments;

/**
 * @author oscar
 * @breif
 * Representació de tot item que juga una partida dins d'un laberint, i que lluita per 
 * obtenir el maxim de punts, que pot anar canviant d'estat i que es comporta de forma diferent
 * segons el seu estat.
 */
public abstract class Personatge extends ItemMovible{
    /**
     * Estructura de dades de tipus FIFO on s'emagatzemen totes les direccions 
     * que ha agafat el personatge,
     * Aquesta estructura l'utilitzem per si el personatge ha de fer marxa enrrere;
     **/
    protected HistoricMoviments historicMoviments;
    
    protected int punts; /**<quantitat de punts acumulats per el personatge*/
    
    private boolean bocaOberta = false;/**<flag per indicar si el seguent moviment
                                        del personatge toca una imatge "amb la boca oberta"*/

    private int indexDireccioAnterior = 0;/**<index per accedir a la matriu d'imatges
                                              segons la direcció en que s'ha mogut el 
                                              personatge en el moviment anterior*/
    
    protected boolean guanya; /**<flag que indica si el personatge està guanyant
                                   tot personatge està guanyant fins que s'acaben 
                                    les monedes i es valora qui té menys punts*/
     
    protected EEstatPersonatge estatPersonatge = EEstatPersonatge.NORMAL; /**<Estat en que es troba el personatge
                                                                            en un moment donat*/
    
    protected ImageIcon imatges[][]; /**<Matriu de imatges per el personatge
                                        Tot personatge tindrà les imatges de
                                        cada una de les direccions amb la boca
                                        oberta i tencada*/
    
    /**
     * @author oscar
     * @brief
     * conjunt d'estats en que es pot trobar un personatge, el canvi d'estat
     * sempre va llgiat a la captura d'un item que hi havia a la partida.
     */
    protected enum EEstatPersonatge{
        NORMAL, AMB_PATINS, AMB_MONEDES_X2, AMB_MONGETA;
    }
    
    /**
     * @pre inici és una posició valida dins de laberint.
     * @post personatge que es juga una partida dins de un laberint en la posició inici
     * i que té una imatge de perfil
     */
    public Personatge(Partida partida, Laberint laberint, ImageIcon imatge, Punt inici) {
        super(partida, imatge, laberint, inici, Utils.Constants.FREQUENCIA_PERSONATGE);
        imatges = new ImageIcon[4][2];
        historicMoviments = new HistoricMoviments();
        punts = 0;
        guanya = true;
    }
    
    /**
     * @pre --
     * @post em retornat el nombre de punts que té el personatge.
     */
    public int obtenirPunts(){
        return this.punts;
    }
    
    /**
     * @pre ja no queden monedes en el laberint.
     * @post em canviat si guanya o perd el personatge.
     */
    public void assignarGuanya(boolean guanya){
        this.guanya = guanya;
    }

    /**
     * @pre --
     * @post em retornat si el personatge està guanyant.
     * @return 
     */
    public boolean estaGuanyant(){
        return guanya;
    }
    
    @Override
    public EElement realitzarMoviment(){
        ///A cada torn toca moure el personatge
        ///primer de tot canviem la seva imatge dins del laberint.
        EElement elementObtingut = null;
        ImageIcon imatge;
        switch(seguentMoviment){
            case DRETA:{
                if(bocaOberta) imatge = imatges[0][0];
                else imatge = imatges[0][1];
                indexDireccioAnterior = 0;
            }break;
            case ESQUERRA:{
                if(bocaOberta) imatge = imatges[1][0];
                else imatge = imatges[1][1];
                indexDireccioAnterior = 1;
            }break;
            case AMUNT:{
                if(bocaOberta) imatge = imatges[2][0];
                else imatge = imatges[2][1];
                indexDireccioAnterior = 2;
            }break;
            case AVALL:{
                if(bocaOberta) imatge = imatges[3][0];
                else imatge = imatges[3][1];
                indexDireccioAnterior = 3;
            }break;
            default:{
                if(bocaOberta) imatge = imatges[indexDireccioAnterior][0];
                else imatge = imatges[indexDireccioAnterior][1];
            }
        }
        ///Si tenia la boca oberta el proxim moviment la tindrà tancada i viceversa
        bocaOberta = !bocaOberta;
        
        ///Si el moviment del personatge no erà estar-se quiet llavors apliquem el 
        ///moviment sobre el laberint.
        if(seguentMoviment != EDireccio.QUIET){
            ///Tindrem superpoders quan o be tinguem una mongeta o be estiguem perdent
            ///només podem perdre quan s'han acabat les monedes, s'ha sortejat la porta de sortida
            ///i portem menys punts que el contrincant;
            boolean superPoder = !guanya || estatPersonatge == EEstatPersonatge.AMB_MONGETA;
            elementObtingut = laberint.mourePersonatge(posicio, seguentMoviment, imatge, superPoder);
        }
        return elementObtingut;
    }
    
    /**
     * @pre --
     * @post em canviat l'estat del personatge segons l'element que ha capturat.
     * pot haver capturat:
     *                      -PATINS -> Li doblen la velocitat.
     *                      -MONGETA -> permet absorvir el punts del contrincant.
     *                      -MONEDES X 2 -> el valor de les monedes que es capturin
     *                                      serà el doble.
     */
    protected final void assignarEstatPersonatge(EElement item){
        switch(item){
            case PATINS:{
                estatPersonatge = EEstatPersonatge.AMB_PATINS;
                activarTimerEstat();
                super.canviarFrequenciaMoviment(Utils.Constants.FREQUENCIA_PERSONATGE/2);
            }break;
            case MONEDES_X2:{
                ///Tan si capturem monedes x 2 com si capturem mongeta cal que 
                ///tornem a la normalitat al personatge si tenia patins 
                ///per tant cal canviar-li la freqüencia a la normal.
                if(estatPersonatge == EEstatPersonatge.AMB_PATINS){
                    super.canviarFrequenciaMoviment(Utils.Constants.FREQUENCIA_PERSONATGE);
                }
                estatPersonatge = EEstatPersonatge.AMB_MONEDES_X2;
                activarTimerEstat();
            }break;
            case MONGETA:{
                ///Tan si capturem monedes x 2 com si capturem mongeta cal que 
                ///tornem a la normalitat al personatge si tenia patins 
                ///per tant cal canviar-li la freqüencia a la normal.
                if(estatPersonatge == EEstatPersonatge.AMB_PATINS){
                    super.canviarFrequenciaMoviment(Utils.Constants.FREQUENCIA_PERSONATGE);
                }
                estatPersonatge = EEstatPersonatge.AMB_MONGETA;
                activarTimerEstat();
            }break;
        }
    }
    
    /**
     * @pre --
     * @post em posat a 0 el nombre de punts del personatge i n'hem retornat la
     * quantitat que tenia.
     */
    public int reiniciarPunts(){
        int p = punts;
        punts = 0;
        return p;
    }
    
    /**
     * @pre --
     * @post em incrementat el nombre de punts 
     */
    protected void incrementarPunts(int punts){
        if(estatPersonatge == EEstatPersonatge.AMB_MONEDES_X2){
            ///Estem en estat de monedes x 2 per tant el valor s'ha de multiplicar per 2
            this.punts+= punts*2;
        }
        else{
            this.punts+=punts;
        }
    }
    
    /**
     * @pre --
     * @post em posat un timer per treure els efectes al cap de Utils.Constants.TEMPS_EFECTES_ITEM_MILISEGONS
     */
    private void activarTimerEstat(){
        TimerTask timerEstat = new TimerTask() {
            @Override
            public void run() {
                if(estatPersonatge == EEstatPersonatge.AMB_PATINS) {
                    canviarFrequenciaMoviment(Utils.Constants.FREQUENCIA_PERSONATGE);
                }
                estatPersonatge = EEstatPersonatge.NORMAL;
                notificarPerduaEstat();
            }
        };
        Timer t = new Timer("Thread cambiar estat personatge a normal");
        t.schedule(timerEstat, Utils.Constants.TEMPS_EFECTES_ITEM_MILISEGONS);
    }
    
    /**
     * @pre --
     * @post em retornat l'estat en que es troba el personatge.
     */
    protected final EEstatPersonatge obtenirEstatPersonatge(){
        return estatPersonatge;
    }

    @Override
    public void iniciarItemMovible() {
        ///Carreguem les imatges per el moviment del personatge sobre el laberint
        ///i iniciem el moviment.
        this.imatges = new ImageIcon[4][2];
        assignarImatges();
        super.iniciarItemMovible();
    }
    
    /**
     * @pre teniem un super poder
     * @post em notificat a la partida que sens ha acabat els efectes del super poder.
     */
    protected abstract void notificarPerduaEstat();
    
    /**
     * @pre està creada la matriu d'imatges.
     * @post s'han assignat les imatges per el moviment dins del laberint.
     */
    protected abstract void assignarImatges();
}
