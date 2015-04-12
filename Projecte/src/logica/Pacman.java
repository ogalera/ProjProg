/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import logica.enumeracions.EDireccio;

/**
 *
 * @author oscar
 */
public class Pacman extends Personatge{

    public Pacman(Laberint laberint, Punt inici, long millis) {
        super(laberint, inici, millis);
    }

    @Override
    public EDireccio calcularMoviment() {
        return EDireccio.QUIET;
    }
    
    @Override
    public String nomItemMovible(){
        return "Pacman";
    }
    
//    @Override
//    public String nomItemMovible() {
//        return "Pacman";
//    }
}
