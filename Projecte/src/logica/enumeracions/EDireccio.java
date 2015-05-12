package logica.enumeracions;
import logica.Punt;
import logica.excepcions.ExceptionPunt;

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
    public static EDireccio obtenirDireccio(Punt origen, Punt desti ){
        int diferenciaFila = Math.abs(origen.obtenirFila()-desti.obtenirFila());
        int diferenciaColumna = Math.abs(origen.obtenirColumna() - desti.obtenirColumna());
        
        if (diferenciaFila > 1 || diferenciaColumna > 1)throw new ExceptionPunt("Els punts no son adjacents");
        EDireccio res = EDireccio.QUIET;
        if (origen.obtenirFila() == desti.obtenirFila()){
            if (origen.obtenirColumna() > desti.obtenirColumna()){
                res = EDireccio.ESQUERRA;
            }
            else if (origen.obtenirColumna() < desti.obtenirColumna()){
                res = EDireccio.DRETA;
            }
        }
        else if (origen.obtenirColumna() == desti.obtenirColumna()){
            if(origen.obtenirFila() > desti.obtenirFila()){
                res = EDireccio.AMUNT;
            }
            else if (origen.obtenirFila() < desti.obtenirFila()){
                res = EDireccio.AVALL;
            }
        }
        return res;
    }
}
