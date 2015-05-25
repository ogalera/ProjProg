package logica;

import java.awt.Dimension;
import java.awt.Image;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.ImageIcon;
import logica.enumeracions.EDireccio;
import logica.enumeracions.EElement;
import logica.laberints.Laberint;
import logica.historic_moviments.HistoricMoviments;

/**
 * @author oscar
 * Representació de tot item que lluita per obtenir el maxim de punts,
 * que pot anar canviant d'estat i que es comporta de forma diferent
 * segons el seu estat.
 */
public abstract class Personatge extends ItemMovible{
    /**
     * Estructura de dades de tipus FIFO on s'emagatzemen totes les direccions 
     * que ha agafat el personatge,
     * Aquesta estructura l'utilitzem per si el personatge ha de fer marxa enrrere;
     **/
    protected HistoricMoviments historicMoviments;
    
    protected int punts;
    
    private boolean oberta = false;
    private int r = 0;
    
    /**
     * Partida en la que es troba el personatge
     **/
    protected boolean guanya;
    protected EEstatPersonatge estatPersonatge = null;
    protected ImageIcon imatges[][];
    
    protected enum EEstatPersonatge{
        NORMAL, AMB_PATINS, AMB_MONEDES_X2, AMB_MONGETA;
    }
    
    public Personatge(Partida partida, Laberint laberint, ImageIcon imatge, Punt inici) {
        super(partida, imatge, laberint, inici, Utils.Constants.FREQUENCIA_PERSONATGE);
        imatges = new ImageIcon[4][2];
        historicMoviments = new HistoricMoviments();
        punts = 0;
        guanya = true;
    }
    
    public int obtenirPunts(){
        return this.punts;
    }
    
    public void assignarGuanya(boolean guanya){
        this.guanya = guanya;
    }
    
    public boolean estaGuanyant(){
        return this.guanya;
    }
    
    @Override
    public EElement realitzarMoviment(){
        EElement elementObtingut = null;
        ImageIcon imatge;
        switch(seguentMoviment){
            case DRETA:{
                if(oberta) imatge = imatges[0][0];
                else imatge = imatges[0][1];
                r = 0;
            }break;
            case ESQUERRA:{
                if(oberta) imatge = imatges[1][0];
                else imatge = imatges[1][1];
                r = 1;
            }break;
            case AMUNT:{
                if(oberta) imatge = imatges[2][0];
                else imatge = imatges[2][1];
                r = 2;
            }break;
            case AVALL:{
                if(oberta) imatge = imatges[3][0];
                else imatge = imatges[3][1];
                r = 3;
            }break;
            default:{
                if(oberta) imatge = imatges[r][0];
                else imatge = imatges[r][1];
            }
        }
        oberta = !oberta;
        if(seguentMoviment != EDireccio.QUIET){
            ///Tindrem superpoders quan o be tinguem una mongeta o be estiguem perdent
            ///només podem perdre quan s'han acabat les monedes, s'ha sortejat la porta de sortida
            ///i portem menys punts que el contrincant;
            boolean superPoder = !guanya || estatPersonatge == EEstatPersonatge.AMB_MONGETA;
            elementObtingut = laberint.mourePersonatge(posicio, seguentMoviment, imatge, superPoder);
        }
        return elementObtingut;
    }
    
    protected final void assignarEstatPersonatge(EElement item){
        switch(item){
            case PATINS:{
                estatPersonatge = EEstatPersonatge.AMB_PATINS;
                activarTimerEstat();
                super.canviarFrequenciaMoviment(Utils.Constants.FREQUENCIA_PERSONATGE/2);
            }break;
            case MONEDES_X2:{
                if(estatPersonatge == EEstatPersonatge.AMB_PATINS){
                    super.canviarFrequenciaMoviment(Utils.Constants.FREQUENCIA_PERSONATGE);
                }
                estatPersonatge = EEstatPersonatge.AMB_MONEDES_X2;
                activarTimerEstat();
            }break;
            case MONGETA:{
                if(estatPersonatge == EEstatPersonatge.AMB_PATINS){
                    super.canviarFrequenciaMoviment(Utils.Constants.FREQUENCIA_PERSONATGE);
                }
                estatPersonatge = EEstatPersonatge.AMB_MONGETA;
                activarTimerEstat();
            }break;
        }
    }
    
    public int reiniciarPunts(){
        int p = punts;
        punts = 0;
        return p;
    }
    
    protected void incrementarPunts(int punts){
        if(estatPersonatge == EEstatPersonatge.AMB_MONEDES_X2){
            this.punts+= punts*2;
        }
        else{
            this.punts+=punts;
        }
    }
    
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
    
    protected final EEstatPersonatge obtenirEstatPersonatge(){
        return estatPersonatge;
    }

    @Override
    public void iniciarItemMovible() {
        this.imatges = new ImageIcon[4][2];
        assignarImatges();
        super.iniciarItemMovible();
    }
    
    protected abstract void notificarPerduaEstat();
    protected abstract void assignarImatges();
}
