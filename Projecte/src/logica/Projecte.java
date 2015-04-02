package logica;

import log.Log;
import grafics.FInici;
import java.io.IOException;
import logica.generadors_laberint.GeneradorLaberintAleatori;
import logica.generadors_laberint.IGeneradorLaberint;

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
        IGeneradorLaberint generadorAleatori = new GeneradorLaberintAleatori(10, EnumElement.FANTASMA1);
        generadorAleatori.generarLaberint();
        System.out.println(log.obtenirContingutCompletDelLog());
//        Laberint l = new Laberint(generadorAleatori);
//        log.exportarLogAFitxer("/home/oscar/Desktop/log.txt");
//        System.out.println(log.obtenirDebugsLog());
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
        
        
        ////////////////////
        //Prova de Entorns//
        ////////////////////
//        try{
//            new FInici();
//        }
//        catch(IOException e){
//            
//        }
        
        
    }
    
}
