/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

/**
 *
 * @author oscar
 */
public class Utils {
    public static class Constants{
        public static final String PATH_BD = "raw/pacman.db";
        public static final String URL_BD = "jdbc:sqlite:"+PATH_BD;
    }
    
    public static String codificarCadena(String cadena){
        return Base64.encode(cadena.getBytes());
    }
    
    public static String decodiciarCadena(String cadena){
        return new String(Base64.decode(cadena));
    }
}
