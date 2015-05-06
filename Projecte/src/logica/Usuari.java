/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 *
 * @author oscar
 */
public class Usuari {
    private int id;
    private final String nomUsuari;
    private final String urlImatge;
    private int nivell;
    
    public Usuari(int id, String nomUsuari, int nivell, String urlImatge){
        this.nomUsuari = nomUsuari;
        this.nivell = nivell;
        this.urlImatge = urlImatge;
    }
    
    public String obtenirNomUsuari(){
        return this.nomUsuari;
    }
    
    public int obtenirNivell(){
        return this.nivell;
    }
    
    public Icon obtenirImatge(){
        return new ImageIcon(urlImatge);
    }
    
    public void augmentarNivell(){
        nivell++;
    }
    
    public static class PuntuacioNivell{
        int punts;
        long tempsMillis;
        
        public PuntuacioNivell(int punts, long temps){
            this.punts = punts;
            this.tempsMillis = temps;
        }

        public int obtenirPuntsNivell(){
            return this.punts;
        }
        
        public int obtenirSegonsNivell(){
            return (int)this.tempsMillis/1_000;
        }
    }
}
