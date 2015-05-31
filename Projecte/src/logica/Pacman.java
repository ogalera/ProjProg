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
 * @brief
 * personatge principal controlat per l'usuari a traves d'un dispositiu físic.
 */
public class Pacman extends Personatge{
    
    /**
     * @pre inici és una posició valida dins de laberint
     * @post em creat en pacman que juga la partida dins de laberint (en la posició inici) i controlat
     * per controlador.
     */
    public Pacman(Partida partida, Laberint laberint, IControlador controlador, Punt inici) {
        super(partida, laberint, EElement.PACMAN.obtenirImatge(), inici);
        controlador.assignarPacman(this);
        ///Si el controlador és un teclat cal asociar l'event de teclat.
        if(controlador instanceof ControladorTeclat){
            ControladorTeclat controladorTeclatGrafic = (ControladorTeclat) controlador;
            KeyListener keyListener = (KeyListener) controladorTeclatGrafic;
            laberint.assignarControladorTeclat(keyListener);
        }
    }

    @Override
    public EElement realitzarMoviment(){
        ///Ens desplaçem per el tauler
        EElement element = super.realitzarMoviment();
        if(element != null && element != EElement.PACMAN){
            ///El nostre moviment ha sigut acceptat.
            ///Que em trapitjat?
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
        ///Com al presionar una tecla aquesta es queda fixe per el seguent
        ///moviment cal mirar que no sortim del laberint.
        Punt desti = posicio.generarPuntDesplasat(seguentMoviment);
        if(laberint.obtenirElement(desti) == EElement.PARET) seguentMoviment = EDireccio.QUIET;
        return seguentMoviment;
    }
    
    @Override
    public String nomItemMovible(){
        return "Pacman";
    }
    
    /**
     * @pre --
     * @post si direccioAMoure no surt del tauler llavors serà el proxim moviment
     * que intentarà fer en pacman.
     */
    public void nouMoviment(EDireccio direccioAMoure){
        ///Em rebut un missatge per mourens cap a direccioAMoure.
        if(direccioAMoure != null){
            Punt desti = posicio.generarPuntDesplasat(direccioAMoure);
            if (laberint.obtenirElement(desti) != EElement.PARET){
                seguentMoviment = direccioAMoure;
            }
        }
    }

    @Override
    protected void assignarImatges() {
        int llargada = laberint.obtenirMidaImatge().height;
        this.imatges[0][0] = new ImageIcon(new ImageIcon(ClassLoader.getSystemResource("res/imatges/pacmanD0.png")).getImage().getScaledInstance(llargada, llargada, Image.SCALE_DEFAULT));
        this.imatges[0][1] = new ImageIcon(new ImageIcon(ClassLoader.getSystemResource("res/imatges/pacmanD1.png")).getImage().getScaledInstance(llargada, llargada, Image.SCALE_DEFAULT));
        this.imatges[1][0] = new ImageIcon(new ImageIcon(ClassLoader.getSystemResource("res/imatges/pacmanE0.png")).getImage().getScaledInstance(llargada, llargada, Image.SCALE_DEFAULT));
        this.imatges[1][1] = new ImageIcon(new ImageIcon(ClassLoader.getSystemResource("res/imatges/pacmanE1.png")).getImage().getScaledInstance(llargada, llargada, Image.SCALE_DEFAULT));
        this.imatges[2][0] = new ImageIcon(new ImageIcon(ClassLoader.getSystemResource("res/imatges/pacmanA0.png")).getImage().getScaledInstance(llargada, llargada, Image.SCALE_DEFAULT));
        this.imatges[2][1] = new ImageIcon(new ImageIcon(ClassLoader.getSystemResource("res/imatges/pacmanA1.png")).getImage().getScaledInstance(llargada, llargada, Image.SCALE_DEFAULT));
        this.imatges[3][0] = new ImageIcon(new ImageIcon(ClassLoader.getSystemResource("res/imatges/pacmanB0.png")).getImage().getScaledInstance(llargada, llargada, Image.SCALE_DEFAULT));
        this.imatges[3][1] = new ImageIcon(new ImageIcon(ClassLoader.getSystemResource("res/imatges/pacmanB1.png")).getImage().getScaledInstance(llargada, llargada, Image.SCALE_DEFAULT));
    }
    
    @Override
    protected void notificarPerduaEstat() {
        partida.assignarItemAPacman(EElement.RES);
    }
}
