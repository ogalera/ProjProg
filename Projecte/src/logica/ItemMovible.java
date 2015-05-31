package logica;

import logica.laberints.Laberint;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.ImageIcon;
import logica.enumeracions.EDireccio;
import logica.enumeracions.EElement;
import logica.excepcions.EItemMovibleIniciat;

/**
 *
 * @author oscar
 * @brief Objecte amb la capacitat de moures per les diferents poscions d'un Laberint 
 * durant una Partida.
 */
public abstract class ItemMovible {
    private Timer temporitzador;
    private TascaAplicarMoviment tascaAplicarMoviment;
    
    protected final Laberint laberint;/*!< Laberint on es mou ItemMovible*/
    protected Punt posicio;/*!< Representacio de les coordenades (Fila,Columna) en un instant determinat de la partida */
    protected EDireccio seguentMoviment;/*!< Moviment calculat en un instant determinat de la partida */
    private long frequencia;/*!< Freqüencia amb la que ItemMovible efectua un moviment*/
    private boolean iniciat = false;
    public final ImageIcon imatgePerfil;/*!< Imatge que representa a un ItemMovible*/
    protected final Partida partida;/*!< partida en joc*/
    private long tempsTotalCalcul = 0;/*!< Temps total dedicat al càlcul de moviments durant una partida en milisegons*/
    
    
    public ItemMovible(Partida partida, ImageIcon imatge, Laberint laberint, Punt inici, long frequencia){
        this.partida = partida;
        this.imatgePerfil = imatge;
        this.laberint = laberint;
        this.posicio = inici;
        this.frequencia = frequencia;
        tascaAplicarMoviment = new TascaAplicarMoviment();
        seguentMoviment = EDireccio.QUIET;
    }
    
    
    /**
     * @brief Inicialitzacio de un ItemMovible
     * @post 
     * temporitzador inicialitzat
     * següent Moviment calculat
     * iniciat = true
     * @exception EItemMovibleIniciat si ItemMovible ja estava incialitzat.
     */
    public void iniciarItemMovible(){
        temporitzador = new Timer("Thread "+this.nomItemMovible());
        seguentMoviment = this.calcularMoviment();
        if(iniciat) throw new EItemMovibleIniciat(this.nomItemMovible());
        iniciat = true;
        temporitzador.scheduleAtFixedRate(tascaAplicarMoviment, 0, frequencia);
    }
    
    
    /**
     * @brief Modifica la frequencia de ItemMovible
     * @details a cada cicle de freqüència ItemMovible realitzara un moviment
     * i calcularà el proxim.
     * @post frequencia = _frequencia
     */
    protected void canviarFrequenciaMoviment(long _frequencia){
        this.frequencia = _frequencia;
        temporitzador.cancel();
        tascaAplicarMoviment = new TascaAplicarMoviment();
        temporitzador = new Timer();
        temporitzador.scheduleAtFixedRate(tascaAplicarMoviment, _frequencia, _frequencia);
    }
    
    public void finalitzarItem(){
        tascaAplicarMoviment.calcularProximMoviment = false;
        tascaAplicarMoviment.cancel();
        temporitzador.purge();
    }
    
    /**
     * @brief Tasca a executar a cada cicle de freqüència. S'obre un nou fil on
     * s'executa el moviment dins de Laberint i es calcula el proxim moviment.
     * @post Movimnt realitzat a Laberint i següent Moviment calculat
     */
    private class TascaAplicarMoviment extends TimerTask{
        private boolean calcularProximMoviment = true;
        @Override
        public void run() {
            realitzarMoviment();
            if(calcularProximMoviment){
                long tempsIniciCalcul = System.currentTimeMillis();
                seguentMoviment = calcularMoviment();
                long tempsFinalCalcul = System.currentTimeMillis();
                tempsTotalCalcul += tempsFinalCalcul-tempsIniciCalcul;
            }
        }
        
    }
    
    /**
     * @brief Retorna el temps utilitzat nomes en calcul de moviments de un ItemMovible, durant una partida.
     * @return Temps total de càlcul de moviments.
     */
    public long obtenirTempsTotalCalcul(){
        return tempsTotalCalcul;
    }
    
    /**
     * @brief Descripció del metode que realitza un moviment dins de la estructura de Laberint.
     * @return EELement que contenia la posicio on s'ha desplaçat ItemMovible.
     */
    public abstract EElement realitzarMoviment();
    
    /**
     * @brief Descripcio del metode on s'implementara les logiques de cada ItemMovible per a calcular el seu proxim moviment.
     * @return EDireccio Calculada.
     */
    public abstract EDireccio calcularMoviment();
    
    /**
     * @brief Descripcio del metode per obtenir el nom que identifica al subtipus de ItemMovible.
     * @return Nom que identifica al subtipus de ItemMovible.
     */
    public abstract String nomItemMovible();
}
