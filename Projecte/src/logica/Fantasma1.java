package logica;

import java.awt.Image;
import javax.swing.ImageIcon;
import logica.laberints.Laberint;
import logica.enumeracions.EDireccio;
import logica.enumeracions.EElement;

/**
 * @author oscar
 * @brief
 * Eenemic més bàsic i facil de guanyar contra el que pots jugar en una partida.
 * 
 * CARACTERISTIQUES DEL FANTASMA 
 * Els seus moviments són totalment aleatoris.
 * Sempre serà igual de dolent (estigui com estigui el tauler)
 */
public class Fantasma1 extends Personatge{
    
    /**
     * @pre inici és un punt valid dins de laberint.
     * @post em creat el fantasma 1 i està dins de laberint en la posició inici i jugant partida.
     */
    public Fantasma1(Partida partida, Laberint laberint, Punt inici) {
        super(partida, laberint, EElement.FANTASMA1.obtenirImatge(), inici);
    }

    @Override
    public final EElement realitzarMoviment(){
        ///Apliquem el moviment que teniem pensat fer.
        EElement elementObtingut = super.realitzarMoviment();
        if(elementObtingut != null && elementObtingut != EElement.FANTASMA1){
            ///Que em obtingut?
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
    public EDireccio calcularMoviment() {
        ///El calcul del seguent moviment és simplement anar a una posició valida.
        EDireccio moviment;
        Punt p;
        do{
            int index = Utils.obtenirValorAleatori(4);
            moviment = EDireccio.values()[index];
            p = posicio.generarPuntDesplasat(moviment);
        }while(laberint.obtenirElement(p) == EElement.PARET);
        return moviment;
    }

    @Override public String nomItemMovible(){
        return "Fantasma1";
    }

    @Override
    protected void assignarImatges() {
        ///Carreguem el conjunt de imatges per aquest fantasma
        int llargada = laberint.obtenirMidaImatge().height;
        this.imatges[0][0] = new ImageIcon(new ImageIcon(ClassLoader.getSystemResource("res/imatges/enemic1D0.png")).getImage().getScaledInstance(llargada, llargada, Image.SCALE_DEFAULT));//EElement.PACMAN.obtenirImatge();
        this.imatges[0][1] = new ImageIcon(new ImageIcon(ClassLoader.getSystemResource("res/imatges/enemic1D1.png")).getImage().getScaledInstance(llargada, llargada, Image.SCALE_DEFAULT));
        this.imatges[1][0] = new ImageIcon(new ImageIcon(ClassLoader.getSystemResource("res/imatges/enemic1E0.png")).getImage().getScaledInstance(llargada, llargada, Image.SCALE_DEFAULT));
        this.imatges[1][1] = new ImageIcon(new ImageIcon(ClassLoader.getSystemResource("res/imatges/enemic1E1.png")).getImage().getScaledInstance(llargada, llargada, Image.SCALE_DEFAULT));
        this.imatges[2][0] = new ImageIcon(new ImageIcon(ClassLoader.getSystemResource("res/imatges/enemic1A0.png")).getImage().getScaledInstance(llargada, llargada, Image.SCALE_DEFAULT));
        this.imatges[2][1] = new ImageIcon(new ImageIcon(ClassLoader.getSystemResource("res/imatges/enemic1A1.png")).getImage().getScaledInstance(llargada, llargada, Image.SCALE_DEFAULT));
        this.imatges[3][0] = new ImageIcon(new ImageIcon(ClassLoader.getSystemResource("res/imatges/enemic1B0.png")).getImage().getScaledInstance(llargada, llargada, Image.SCALE_DEFAULT));
        this.imatges[3][1] = new ImageIcon(new ImageIcon(ClassLoader.getSystemResource("res/imatges/enemic1B1.png")).getImage().getScaledInstance(llargada, llargada, Image.SCALE_DEFAULT));
    }

    @Override
    protected void notificarPerduaEstat() {
        ///Anunciem a la partida que ja no tenim super poders.
        partida.assignarItemAEnemic(EElement.RES);
    }
}
