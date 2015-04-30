package logica;


import interficie.FEditorLaberint;
import logica.enumeracions.EElement;
import logica.generadors_laberint.GeneradorLaberintAleatori;
import logica.generadors_laberint.GeneradorLaberintLinealHoritzontal;
import logica.generadors_laberint.IGeneradorLaberint;
import logica.log.Log;
import interficie.FInici;
import interficie.FPartida;
import java.io.IOException;

/**
 *
 * @author oscar
 */
public class Projecte {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
        //Log log = Log.getInstance(Projecte.class);
        //IGeneradorLaberint generadorAleatori = new GeneradorLaberintAleatori(20, EElement.FANTASMA2);
        //generadorAleatori.toString();
        //Partida p = new Partida(generadorAleatori);
        //Laberint l = new Laberint(generadorAleatori, p );
        //l.toString();
        
        //Partida p = new Partida(generadorAleatori);
        //p.iniciarPartida();
//        System.out.println(log.obtenirContingutCompletDelLogAmbColor());
//        System.out.println(log.obtenirContingutCompletDelLogAmbColor());
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
    
        try{
            //new FInici();
            new FInici();
        }
        catch(IOException e){
            
        } 
        //System.out.println(log.obtenirDebugsLog());
        
    }
    
}
