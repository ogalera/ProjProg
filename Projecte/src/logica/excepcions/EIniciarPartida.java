/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica.excepcions;

/**
 *
 * @author oscar
 */
public class EIniciarPartida extends RuntimeException{
    public EIniciarPartida(String msg){
        super(msg);
    }
}
