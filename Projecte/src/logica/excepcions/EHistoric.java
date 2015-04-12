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
public class EHistoric extends RuntimeException{
    public EHistoric(String missatge){
        super(missatge);
    }
}
