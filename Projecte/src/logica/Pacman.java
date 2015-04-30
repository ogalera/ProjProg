/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import logica.enumeracions.EDireccio;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import logica.enumeracions.EElement;

/**
 *
 * @author oscar
 */
public class Pacman extends Personatge implements KeyListener{
    
    private EDireccio teclaPremuda;
    
    public Pacman(Laberint laberint, Punt inici, long millis) {
        super(laberint, inici, millis);
        super.seguentMoviment = EDireccio.QUIET;
        laberint.addKeyListener(this);
        teclaPremuda = EDireccio.QUIET;
    }

    @Override
    public EDireccio calcularMoviment() {
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
        return super.seguentMoviment;
    }
    
    @Override
    public String nomItemMovible(){
        return "Pacman";
    }
    
    public void realitzarMoviment(){
        super.realitzarMoviment();
    }
    


    @Override
    public void keyPressed(KeyEvent e) {
        int _teclaPremuda= e.getKeyCode();
                System.out.println(_teclaPremuda);
                if (_teclaPremuda == 38){
                    System.out.println("tecla Premuda: Amunt");
                    teclaPremuda = EDireccio.AMUNT;
                    calcularMoviment();
                }
                else if (_teclaPremuda == 40){
                    System.out.println("tecla Premuda: Avall");
                    teclaPremuda = EDireccio.AVALL;
                    calcularMoviment();
                }
                else if (_teclaPremuda == 37){
                    System.out.println("tecla Premuda: Esquerra");
                    teclaPremuda = EDireccio.ESQUERRA;
                    calcularMoviment();
                }
                else if (_teclaPremuda == 39){
                    System.out.println("tecla Premuda: Dreta");
                    teclaPremuda = EDireccio.DRETA;
                    calcularMoviment();
                }
    }

    @Override
    public void keyReleased(KeyEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyTyped(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
