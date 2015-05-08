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
        EElement elementObtingut = super.realitzarMoviment();
        switch(elementObtingut){
            case MONEDA:{
                this.punts+= Utils.Constants.VALOR_MONEDA_NORMAL;
                partida.assignarPuntsPacman(punts);
            }break;
            case MONEDA_EXTRA:{
                this.punts+= Utils.Constants.VALOR_MONEDA_EXTRA;
                partida.assignarPuntsPacman(punts);
            }break;
        }
        return elementObtingut;
    }

    @Override
    public EDireccio calcularMoviment() {
        if (teclaPremuda == null)seguentMoviment = EDireccio.QUIET;
        else{
            Punt desti = posicio.generarPuntDesplasat(teclaPremuda);
            if (laberint.posicioValida(desti)){
                EElement elementDesti = laberint.obtenirElement(desti);
                if (elementDesti == EElement.PARET || elementDesti.esEnemic()){
                    seguentMoviment = EDireccio.QUIET;
                }
                else seguentMoviment = teclaPremuda;
            }
            else seguentMoviment = EDireccio.QUIET;
        }
        System.out.println("En pacman vol anar a la "+seguentMoviment);
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
