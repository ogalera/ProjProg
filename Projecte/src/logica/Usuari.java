/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import logica.enumeracions.EElement;

/**
 * @author oscar
 */
public class Usuari {
    private final String nomUsuari;
    private final String urlImatge;
    private ENivells nivell;
    private EDificultat dificultat;
    
    public static enum EDificultat{
        FACIL(EElement.FANTASMA2), MITJA(EElement.FANTASMA3), DIFICIL(EElement.FANTASMA3);
        EElement enemic;
        private EDificultat(EElement enemic){
            this.enemic = enemic;
        }
        
        public EElement obtenirEnemicAssignatADificultat(){
            return this.enemic;
        }
    };
    
    public static enum ENivells{
        PRIMER(10)/*10*/, SEGON(15)/*15*/, TERCER(20)/*20*/, QUART(25), CINQUE(35);
        private final int midaLaberint;
        
        private ENivells(int midaLaberint){
            this.midaLaberint = midaLaberint;
        }
        
        public int obtenirMidaLaberint(){
            return this.midaLaberint;
        }
        
        public ENivells seguentNivell(){
            switch(this){
                case PRIMER:{
                    return SEGON;
                }
                case SEGON:{
                    return TERCER;
                }
                case TERCER:{
                    return QUART;
                }
                default:{
                    return CINQUE;
                }
            }
        }
        
        public static ENivells obtenirNivellPerId(int id){
            switch(id){
                case 1:{
                    return PRIMER;
                }
                case 2:{
                    return SEGON;
                }
                case 3:{
                    return TERCER;
                }
                case 4:{
                    return QUART;
                }
                default:{
                    return CINQUE;
                }
            }
        }
    }
    
    public Usuari(int id, String nomUsuari, int nivell, String urlImatge){
        this.dificultat = EDificultat.FACIL;
        this.nomUsuari = nomUsuari;
        this.nivell = ENivells.obtenirNivellPerId(nivell);
        this.urlImatge = urlImatge;
    }
    
    public EDificultat obtenirDificultat(){
        return this.dificultat;
    }
    
    public String obtenirNomUsuari(){
        return this.nomUsuari;
    }
    
    public ENivells obtenirNivell(){
        return this.nivell;
    }
    
    public Icon obtenirImatge(){
        return new ImageIcon(urlImatge);
    }
    
    public void pantallaSuperada(){
        if(dificultat == EDificultat.FACIL){
            dificultat = EDificultat.MITJA;
        }
        else if(dificultat == EDificultat.MITJA){
            dificultat = EDificultat.DIFICIL;
        }
        else{
            nivell = nivell.seguentNivell();
            dificultat = EDificultat.FACIL;
        }
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
