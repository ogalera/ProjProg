/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import logica.laberints.Laberint;
import logica.enumeracions.EDireccio;
import java.awt.event.KeyListener;
import logica.controladors_pacman.ControladorTeclat;
import logica.controladors_pacman.IControlador;
import logica.enumeracions.EElement;

/**
 *
 * @author oscar
 */
public class Pacman extends Personatge{
    private EDireccio teclaPremuda;
    
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
                    super.incrementarPunts(Utils.Constants.VALOR_MONEDA_NORMAL);
                    partida.assignarPuntsPacman(punts);
                }break;
                case MONEDA_EXTRA:{
                    super.incrementarPunts(Utils.Constants.VALOR_MONEDA_EXTRA);
                    partida.assignarPuntsPacman(punts);
                }break;
                case FANTASMA1:
                case FANTASMA2:
                case FANTASMA3:{
                    int puntsRobats = partida.reiniciarPuntsEnemic();
                    super.incrementarPunts(puntsRobats);
                    partida.assignarPuntsPacman(punts);
                }break;
                case PATINS:
                case MONEDES_X2:
                case MONGETA:{
                    //Em agafat algún item
                    partida.itemCapturat();
                    super.assignarEstatPersonatge(elementObtingut);
                    partida.assignarItemAPacman(elementObtingut);
                }break;
            }
            return elementObtingut;
        }
        return null;
    }

    @Override
    public EDireccio calcularMoviment() {
        if (teclaPremuda == null)seguentMoviment = EDireccio.QUIET;
        else{
            Punt desti = posicio.generarPuntDesplasat(teclaPremuda);
            if (laberint.posicioValida(desti)){
                EElement elementDesti = laberint.obtenirElement(desti);
                if (elementDesti == EElement.PARET){
                    seguentMoviment = EDireccio.QUIET;
                }
                else seguentMoviment = teclaPremuda;
            }
            else seguentMoviment = EDireccio.QUIET;
        }
        return seguentMoviment;
    }
    
    @Override
    public String nomItemMovible(){
        return "Pacman";
    }
    
    public void nouMoviment(EDireccio teclaPremuda){
        this.teclaPremuda = teclaPremuda;
    }
}
