package logica.enumeracions;

/**
 *
 * @author oscar
 */
public enum EDireccio {
    AMUNT(-1, 0), AVALL(1, 0), ESQUERRA(0, -1), DRETA(0, 1), QUIET(0,0);
    private final int incrementColumna;
    private final int incrementFila;
    
    private EDireccio(int incrementFila, int incrementColumna){
        this.incrementFila = incrementFila;
        this.incrementColumna = incrementColumna;
    }
    
    public int obtenirIncrementColumna(){
        return incrementColumna;
    }
    
    public int obtenirIncrementFila(){
        return incrementFila;
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
