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
public class EItemMovibleIniciat extends RuntimeException{
    public EItemMovibleIniciat(String nomItem){
        super("L'item movible "+nomItem+" ja estava iniciat");
    }
}
