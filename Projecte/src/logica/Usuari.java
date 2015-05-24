/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import dades.BD;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import logica.enumeracions.EElement;

/**
 * @author oscar
 */
public class Usuari {
    private final int id;
    private final String nomUsuari;
    private final String urlImatge;
    private ENivells nivell;
    private EDificultat dificultat;
    private int punts;
    
    public static enum EDificultat{
        FACIL(EElement.FANTASMA1), MITJA(EElement.FANTASMA2), DIFICIL(EElement.FANTASMA3);
        EElement enemic;
        private EDificultat(EElement enemic){
            this.enemic = enemic;
        }
        
        public EElement obtenirEnemicAssignatADificultat(){
            return this.enemic;
        }
        
        @Override
        public String toString(){
            return this.name();
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
                case 1:return PRIMER;
                case 2:return SEGON;
                case 3:return TERCER;
                case 4:return QUART;
                default:return CINQUE;
            }
        }
        
        public int obtenirIdNivell(){
            switch(this){
                case PRIMER: return 1;
                case SEGON: return 2;
                case TERCER: return 3;
                case QUART: return 4;
                default: return 5;
            }
        }
        
        @Override 
        public String toString(){
            switch(this){
                case PRIMER:{
                    return "1";
                }
                case SEGON:{
                    return "2";
                }
                case TERCER:{
                    return "3";
                }
                case QUART:{
                    return "4";
                }
                default:{
                    return "5";
                }
            }
        }
    }
    
    public Usuari(int id, String nomUsuari, int nivell, String urlImatge){
        this.id = id;
        this.dificultat = EDificultat.FACIL;
        this.nomUsuari = nomUsuari;
        this.nivell = ENivells.obtenirNivellPerId(nivell);
        this.urlImatge = urlImatge;
        this.punts = 0;
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
    
    public void pantallaSuperada(int pnt){
        punts += pnt;
        switch(dificultat){
            case FACIL:{
                dificultat = EDificultat.MITJA;
            }break;
            case MITJA:{
                dificultat = EDificultat.DIFICIL;
            }break;
            case DIFICIL:{
                BD.nivellSuperat(nivell.obtenirIdNivell(), id, punts);
                nivell = nivell.seguentNivell();
                dificultat = EDificultat.FACIL;
                this.punts = 0;
            }break;
        }
    }
}
