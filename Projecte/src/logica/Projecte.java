package logica;


import interficie.FLogin;
import java.io.IOException;
import java.net.InetAddress;


/**
 *
 * @author oscar
 */
public class Projecte {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException{
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
        InetAddress ia = InetAddress.getLocalHost();
//        System.out.println(ia.getHostAddress());
//        int a  = 3;
//        int b = a;
//        try(ServerSocket ss = new ServerSocket(9988);
//            Socket s = ss.accept();
//            BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
//            PrintWriter pw = new PrintWriter(s.getOutputStream())){
//            System.out.println(br.readLine());
//            pw.write("JA POTS COMENSAR");
//        }
//        catch(IOException e){
//            System.out.println("ERROR MISSATGE "+e.getMessage());
//        } 
        FLogin fLogin = new FLogin();
        fLogin.mostrarFrame();
        //System.out.println(log.obtenirDebugsLog());
        
    }
    
}
