/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica.enumeracions;

/**
 *
 * @author oscar
 */
public enum EDireccio {
    AMUNT(0, -1), AVALL(0, 1), ESQUERRA(-1, 0), DRETA(1, 0), QUIET(0,0);
    private final int incrementX;
    private final int incrementY;
    
    private EDireccio(int incrementX, int incrementY){
        this.incrementX = incrementX;
        this.incrementY = incrementY;
    }
    
    public int obtenirIncrementEixX(){
        return incrementX;
    }
    
    public int obtenirIncrementEixY(){
        return incrementY;
    }
    
    public EDireccio obtenirMovimentInvers(){
        EDireccio invers;
        switch(this){
            case AMUNT:{
                invers = AVALL;
            }break;
            case AVALL:{
                invers = AMUNT;
            }break;
            case ESQUERRA:{
                invers = DRETA;
            }break;
            case DRETA:{
                invers = ESQUERRA;
            }break;
            default:{
                invers = QUIET;
            }
        }
        return invers;
    }
    
    public static EDireccio[] obtenirRestaDireccions(EDireccio direccio){
        EDireccio [] direccions = null;
        switch(direccio){
            case DRETA:{
                direccions = new EDireccio[3];
                direccions[0] = DRETA;
                direccions[1] = AMUNT;
                direccions[2] = AVALL;
            }break;
            case ESQUERRA:{
                direccions = new EDireccio[3];
                direccions[0] = ESQUERRA;
                direccions[1] = AMUNT;
                direccions[2] = AVALL;
            }break;
            case AMUNT:{
                direccions = new EDireccio[3];
                direccions[0] = DRETA;
                direccions[1] = ESQUERRA;
                direccions[2] = AMUNT;
            }break;
            case AVALL:{
                direccions = new EDireccio[3];
                direccions[0] = DRETA;
                direccions[1] = ESQUERRA;
                direccions[2] = AVALL;
            }break;
        }
        return direccions;
    }
}
