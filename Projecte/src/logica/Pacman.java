package logica;

import java.awt.Image;
import logica.laberints.Laberint;
import logica.enumeracions.EDireccio;
import java.awt.event.KeyListener;
import javax.swing.ImageIcon;
import logica.controladors_pacman.ControladorTeclat;
import logica.controladors_pacman.IControlador;
import logica.enumeracions.EElement;

/**
 * @author oscar
 */
public class Pacman extends Personatge{
    
    public Pacman(Partida partida, Laberint laberint, IControlador controlador, Punt inici) {
        super(partida, laberint, EElement.PACMAN.obtenirImatge(), inici);
        controlador.assignarPacman(this);
        if(controlador instanceof ControladorTeclat){
            ControladorTeclat controladorTeclatGrafic = (ControladorTeclat) controlador;
            KeyListener keyListener = (KeyListener) controladorTeclatGrafic;
            laberint.assignarControladorTeclat(keyListener);
        }
    }

    @Override
    public EElement realitzarMoviment(){
        EElement element = super.realitzarMoviment();
        if(element != null && element != EElement.PACMAN){
            switch(element){
                case MONEDA:{
                    Audio.reprodueixMenjaMoneda();
                    incrementarPunts(Utils.Constants.VALOR_MONEDA_NORMAL);
                    partida.assignarPuntsPacman(punts);
                    posicio = posicio.generarPuntDesplasat(seguentMoviment);
                }break;
                case MONEDA_EXTRA:{
                    Audio.reprodueixMenjaMoneda();
                    incrementarPunts(Utils.Constants.VALOR_MONEDA_EXTRA);
                    partida.assignarPuntsPacman(punts);
                    posicio = posicio.generarPuntDesplasat(seguentMoviment);
                }break;
                case FANTASMA1:
                case FANTASMA2:
                case FANTASMA3:{
                    int puntsRobats = partida.reiniciarPuntsEnemic();
                    incrementarPunts(puntsRobats);
                    partida.reiniciarPuntsEnemic();
                    partida.assignarPuntsPacman(punts);
                }break;
                case PATINS:
                case MONEDES_X2:
                case MONGETA:{
                    //Em agafat algún item
                    Audio.reprodueixMenjaItem();
                    partida.itemCapturat();
                    assignarEstatPersonatge(element);
                    partida.assignarItemAPacman(element);
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
    public  EDireccio calcularMoviment() { 
        Punt desti = posicio.generarPuntDesplasat(seguentMoviment);
        if(laberint.obtenirElement(desti) == EElement.PARET) seguentMoviment = EDireccio.QUIET;
        return seguentMoviment;
    }
    
    @Override
    public String nomItemMovible(){
        return "Pacman";
    }
    
    public void nouMoviment(EDireccio teclaPremuda){
        if(teclaPremuda != null){
            Punt desti = posicio.generarPuntDesplasat(teclaPremuda);
            if (laberint.obtenirElement(desti) != EElement.PARET){
                seguentMoviment = teclaPremuda;
            }
        }
    }

    @Override
    protected void assignarImatges() {
        int llargada = laberint.obtenirMidaImatge().height;
        this.imatges[0][0] = new ImageIcon(new ImageIcon("res/pacmanD0.png").getImage().getScaledInstance(llargada, llargada, Image.SCALE_DEFAULT));
        this.imatges[0][1] = new ImageIcon(new ImageIcon("res/pacmanD1.png").getImage().getScaledInstance(llargada, llargada, Image.SCALE_DEFAULT));
        this.imatges[1][0] = new ImageIcon(new ImageIcon("res/pacmanE0.png").getImage().getScaledInstance(llargada, llargada, Image.SCALE_DEFAULT));
        this.imatges[1][1] = new ImageIcon(new ImageIcon("res/pacmanE1.png").getImage().getScaledInstance(llargada, llargada, Image.SCALE_DEFAULT));
        this.imatges[2][0] = new ImageIcon(new ImageIcon("res/pacmanA0.png").getImage().getScaledInstance(llargada, llargada, Image.SCALE_DEFAULT));
        this.imatges[2][1] = new ImageIcon(new ImageIcon("res/pacmanA1.png").getImage().getScaledInstance(llargada, llargada, Image.SCALE_DEFAULT));
        this.imatges[3][0] = new ImageIcon(new ImageIcon("res/pacmanB0.png").getImage().getScaledInstance(llargada, llargada, Image.SCALE_DEFAULT));
        this.imatges[3][1] = new ImageIcon(new ImageIcon("res/pacmanB1.png").getImage().getScaledInstance(llargada, llargada, Image.SCALE_DEFAULT));
    }
    
    @Override
    protected void notificarPerduaEstat() {
        partida.assignarItemAPacman(EElement.RES);
    }
}
