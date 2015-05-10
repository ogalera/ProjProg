package logica.enumeracions;

/**
 *
 * @author oscar
 */
public enum EItems {
    PATINS(""), MONJETA(""), MONEDES_X2("");
    private final String rutaImatge;
    
    private EItems(String rutaImatge){
        this.rutaImatge = rutaImatge;
    }
    
    public String obtenirRutaImatge(){
        return rutaImatge;
    }
}
