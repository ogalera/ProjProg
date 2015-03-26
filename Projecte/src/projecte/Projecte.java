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
        Log log = Log.getInstance(Projecte.class);
        Laberint l = new Laberint(5, EnumElement.FANTASMA1);
        log.exportarLogAFitxer("/home/oscar/Desktop/log.txt");
        System.out.println(log.obtenirDebugsLog());
//        new FEditorLaberint(5);
//        ValidadorLaberint.laberintValid(5);
//        Log l = Log.getInstance(Projecte.class);
//        l.afegirWarning("prova warning");
//        System.out.println(l.obtenirContingutCompletDelLog());
        
//        Connection c = null;
//        try{
//            Class.forName("org.sqlite.JDBC");
//            c = DriverManager.getConnection("jdbc:sqlite:test:db");
//        }
//        catch (Exception e){
//            System.out.println(e.getMessage());
//            System.exit(-1);
//        }
//        System.out.println("Base de dades oberta");
    }
    
}
