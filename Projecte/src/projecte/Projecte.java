package projecte;

/**
 *
 * @author oscar
 */
public class Projecte {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ValidadorLaberint.laberintValid(5);
        Log l = Log.getInstance(Projecte.class);
        System.out.println(l.obtenirContingutCompletDelLog());
    }
    
}
