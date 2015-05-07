/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import logica.laberints.Laberint;
import logica.enumeracions.EDireccio;
import java.awt.event.KeyListener;
import logica.controladors_pacman.ControladorTeclatGrafic;
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
        if(controlador instanceof ControladorTeclatGrafic){
            ControladorTeclatGrafic controladorTeclatGrafic = (ControladorTeclatGrafic) controlador;
            KeyListener keyListener = (KeyListener) controladorTeclatGrafic;
            laberint.assignarControladorTeclat(keyListener);
        }
    }

    @Override
    public EElement realitzarMoviment(){
        EElement elementObtingut = super.realitzarMoviment();
        partida.assignarPuntsPacman(punts);
        return elementObtingut;
    }
    
    @Override
    public EDireccio calcularMoviment() {
        return null;
    }
    
    @Override
    public String nomItemMovible(){
        return "Pacman";
    }
    
    public void nouMoviment(EDireccio teclaPremuda){
        if (teclaPremuda == null)teclaPremuda = EDireccio.QUIET;
        else{
            Punt desti = posicio.generarPuntDesplasat(teclaPremuda);
            if (laberint.posicioValida(desti)){
                EElement elementDesti = laberint.obtenirElement(desti);
                if (elementDesti == EElement.PARET || elementDesti.esEnemic()){
                    seguentMoviment = EDireccio.QUIET;
                }
                else seguentMoviment = teclaPremuda;
            }
            else seguentMoviment = teclaPremuda;
        }
    }
}
