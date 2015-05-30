package logica.excepcions;

/**
 * @author oscar
 * 
 * @brief
 * Excepció per quan el laberint no té un format correcte.
 */
public class EFormatLaberint extends Exception{
    
    /**
     * @pre --
     * @post s'ha creat l'excepció amb msg.
     */
    public EFormatLaberint(String msg){
        super(msg);
    }
}
