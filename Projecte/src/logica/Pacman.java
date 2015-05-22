/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 *
 * @author oscar
 */
public class Pacman extends Personatge{
    public Pacman(Partida partida, Laberint laberint, IControlador controlador, Punt inici) {
        super(partida, laberint, EElement.PACMAN.obtenirImatge(), inici);
        seguentMoviment = EDireccio.QUIET;
        controlador.assignarPacman(this);
        if(controlador instanceof ControladorTeclat){
            ControladorTeclat controladorTeclatGrafic = (ControladorTeclat) controlador;
            KeyListener keyListener = (KeyListener) controladorTeclatGrafic;
            laberint.assignarControladorTeclat(keyListener);
        }
    }

    @Override
    public EElement realitzarMoviment(){
        Punt puntDesplasat = posicio.generarPuntDesplasat(super.seguentMoviment);
        EElement element = laberint.obtenirElement(puntDesplasat);
        if(!element.esEnemic() || super.obtenirEstatPersonatge() == EEstatPersonatge.AMB_MONGETA){
            EElement elementObtingut = super.realitzarMoviment();
            switch(elementObtingut){
                case MONEDA:{
                    Audio.reprodueixMenjaMoneda();
                    super.incrementarPunts(Utils.Constants.VALOR_MONEDA_NORMAL);
                    partida.assignarPuntsPacman(punts);
                    
                }break;
                case MONEDA_EXTRA:{
                    Audio.reprodueixMenjaMoneda();
                    super.incrementarPunts(Utils.Constants.VALOR_MONEDA_EXTRA);
                    partida.assignarPuntsPacman(punts);
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
                    Audio.reprodueixMenjaItem();
                    //Em agafat algún item
                    partida.itemCapturat();
                    super.assignarEstatPersonatge(elementObtingut);
                    partida.assignarItemAPacman(elementObtingut);
                    Punt[] posicions = puntDesplasat.obtenirPosicionsDelVoltant();
                    if(laberint.posicioValida(posicions[0]))laberint.desmarcarIntencio(posicions[0]);
                    if(laberint.posicioValida(posicions[1]))laberint.desmarcarIntencio(posicions[1]);
                    if(laberint.posicioValida(posicions[2]))laberint.desmarcarIntencio(posicions[2]);
                    if(laberint.posicioValida(posicions[3]))laberint.desmarcarIntencio(posicions[3]);
                }break;
            }
            return null;
        }
        return null;
    }

    @Override
    public  EDireccio calcularMoviment() { 
        synchronized(laberint){
            Punt desti = posicio.generarPuntDesplasat(seguentMoviment);
            if(laberint.obtenirElement(desti) == EElement.PARET) return EDireccio.QUIET;
            laberint.marcarIntencio(desti);
            laberint.marcarIntencio(posicio);
        }
        return seguentMoviment;
    }
    
//    @Override
//    public EDireccio calcularMoviment() {
//        EDireccio direccio = EDireccio.QUIET;
//        EDireccio tmp;
//        synchronized(this){
//            tmp = teclaPremuda;
//        }
//        if(tmp != null){
//            Punt desti = posicio.generarPuntDesplasat(tmp);
//            if (laberint.obtenirElement(desti) != EElement.PARET){
//                if(laberint.esIntencioValida(desti)){
//                    direccio = tmp;
//                    laberint.marcarIntencio(desti);
//                }
//                else if(obtenirEstatPersonatge() == EEstatPersonatge.AMB_MONGETA){
//                    direccio = tmp;
//                }
//            }
//        }
//        return direccio;
//    }
    
    @Override
    public String nomItemMovible(){
        return "Pacman";
    }
    
    public void nouMoviment(EDireccio teclaPremuda){
      synchronized(laberint){ 
        if(teclaPremuda != null){
            Punt desti = posicio.generarPuntDesplasat(teclaPremuda);
            if (laberint.obtenirElement(desti) != EElement.PARET){
                if(laberint.esIntencioValida(desti)){
                    Punt tmp = posicio.generarPuntDesplasat(seguentMoviment);
                    seguentMoviment = teclaPremuda;
                    laberint.marcarIntencio(desti);
                    laberint.desmarcarIntencio(tmp);
                }
                else if(obtenirEstatPersonatge() == EEstatPersonatge.AMB_MONGETA){
                    seguentMoviment = teclaPremuda;
                }
            }
        }
      }
    }

    @Override
    protected void assignarImatges() {
        int midaImatge = laberint.obtenirMidaImatge();
        this.imatges[0][0] = new ImageIcon(new ImageIcon("res/pacmanD0.png").getImage().getScaledInstance(midaImatge, midaImatge, Image.SCALE_DEFAULT));//EElement.PACMAN.obtenirImatge();
        this.imatges[0][1] = new ImageIcon(new ImageIcon("res/pacmanD1.png").getImage().getScaledInstance(midaImatge, midaImatge, Image.SCALE_DEFAULT));
        this.imatges[1][0] = new ImageIcon(new ImageIcon("res/pacmanE0.png").getImage().getScaledInstance(midaImatge, midaImatge, Image.SCALE_DEFAULT));
        this.imatges[1][1] = new ImageIcon(new ImageIcon("res/pacmanE1.png").getImage().getScaledInstance(midaImatge, midaImatge, Image.SCALE_DEFAULT));
        this.imatges[2][0] = new ImageIcon(new ImageIcon("res/pacmanA0.png").getImage().getScaledInstance(midaImatge, midaImatge, Image.SCALE_DEFAULT));
        this.imatges[2][1] = new ImageIcon(new ImageIcon("res/pacmanA1.png").getImage().getScaledInstance(midaImatge, midaImatge, Image.SCALE_DEFAULT));
        this.imatges[3][0] = new ImageIcon(new ImageIcon("res/pacmanB0.png").getImage().getScaledInstance(midaImatge, midaImatge, Image.SCALE_DEFAULT));
        this.imatges[3][1] = new ImageIcon(new ImageIcon("res/pacmanB1.png").getImage().getScaledInstance(midaImatge, midaImatge, Image.SCALE_DEFAULT));
    }
    
    @Override
    protected void notificarPerduaEstat() {
        partida.assignarItemAPacman(EElement.RES);
    }
    
}
