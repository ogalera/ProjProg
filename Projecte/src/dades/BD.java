package dades;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import logica.Usuari;
import logica.Utils;
import logica.log.Log;

/**
 * @author oscar
 * @brief
 * Classe d'utilitats amb les operacions per treballar sobre la B.D. "sqlite"
 * utilitzada en el projecte;
 */
public abstract class BD {
    public static final String PATH_BD = "raw/pacman.db";/**<ruta relativa de la B.D. */
    public static final String URL_BD = "jdbc:sqlite:"+PATH_BD; /**<path del driver per connectar-nos a la B.D.*/
    
    /**
     * @pre --;
     * @post s'ha registrat un nou usuari a la B.D. i retorna si l'operació s'ha realitzat correctament;
     */
    public static boolean afegirUsuari(String user, String password, String rutaImatge){
        boolean operacioOK = false;
        boolean taulesCreades = true;
        Log log = Log.getInstance(BD.class);
        if(BD.existeixBaseDeDades()){
            taulesCreades = crearTaules();
        }
        if(taulesCreades){
            //Try with resources since JAVA SE7
            try(Connection connexio = DriverManager.getConnection(URL_BD);
                Statement afegirUsuari = connexio.createStatement()){

                //Per temes de seguretat codifiquem el password;
                String passCodificat = Utils.codificarCadena(password);

                int resultatOperacio = afegirUsuari.executeUpdate(" INSERT INTO Usuaris (usu_nom, usu_pass, usu_ruta_imatge)\n" +
                                                                    " VALUES ('"+user+"', '"+passCodificat+"', '"+rutaImatge+"')");
                if(resultatOperacio > 0){
                    resultatOperacio = afegirUsuari.executeUpdate(" INSERT INTO Punts (pnt_nivell, pnt_usu_id, pnt_punts)\n" +
                                                                  " VALUES (1, (SELECT usu_id FROM usuaris WHERE usu_nom = '"+user+"'), 0)");
                    operacioOK = resultatOperacio > 0 ;
                }
                log.afegirDebug("S'ha afegit l'usuari: "+user);
            }
            catch(SQLException exepcio){
                log.afegirError(exepcio.getMessage());
            }
        }
        else{
            log.afegirError("No s'ha afegit l'usuari: "+user);
        }
        return operacioOK;
    }
    
    /**
     * @pre topN > 0;
     * @post em retornat el conjunt de usuaris que conformen el topN per ordre segons el nombre de punts;
     */
    public static Usuari[] obtenirRanking(int topN){
        Log log = Log.getInstance(BD.class);
        Usuari[] usuaris = null;
        boolean taulesCreades = true;
        if(BD.existeixBaseDeDades()){
            taulesCreades = crearTaules();
        }
        if(taulesCreades){
            String consulta = " SELECT SUM(p.pnt_punts) AS pnt_punts, u.usu_id, u.usu_nom, u.usu_ruta_imatge\n" +
                                " FROM punts p, usuaris u\n" +
                                " WHERE u.usu_id = p.pnt_usu_id\n" +
                                " GROUP BY u.usu_id, u.usu_nom, usu_ruta_imatge\n" +
                                " ORDER BY 1 DESC";
            try(Connection connexio = DriverManager.getConnection(URL_BD);
                Statement consultarTopN = connexio.createStatement();
                ResultSet rs = consultarTopN.executeQuery(consulta)){
                int n = 0; 
                ArrayList<Usuari> tmp = new ArrayList<>();
                while(n < topN && rs.next()){
                    tmp.add(new Usuari(rs.getInt("usu_id"), rs.getString("usu_nom"), -1, rs.getString("usu_ruta_imatge")));
                    n++;
                }
                usuaris = new Usuari[n];
                for(int i = 0; i<  n; i++){
                    usuaris[i] = tmp.get(i);
                }
            }
            catch(SQLException excepcio){
                log.afegirError("No em pogut obtenir el TOP "+topN+" missatge:\n"+excepcio.getMessage());
            }
        }
        return usuaris;
    }
    
    /**
     * @pre --;
     * @post diu si està o no registrat usuari;
     */
    public static boolean existeixUsuari(String usuari){
        Log log = Log.getInstance(BD.class);
        boolean taulesCreades = true;
        boolean existeixUsuari = false;
        if(BD.existeixBaseDeDades()){
            taulesCreades = crearTaules();
        }
        if(taulesCreades){
            String consulta = " SELECT 1\n" +
                              " WHERE EXISTS (SELECT usu_nom\n" +
                              "                 FROM USUARIS\n" +
                              "			WHERE usu_nom = '"+usuari+"')";
            try(Connection connexio = DriverManager.getConnection(URL_BD);
                Statement consultarUsuari = connexio.createStatement();
                ResultSet rs = consultarUsuari.executeQuery(consulta)){
                existeixUsuari = rs.next();
                if(existeixUsuari){
                    log.afegirWarning("Usuari "+usuari+" existeix");
                }
                else log.afegirDebug("Usuari "+usuari+" no existeix");
            }
            catch(SQLException excepcio){
                log.afegirError("No em pogut consultar l'usuari "+usuari+" missatge:\n"+excepcio.getMessage());
            }
        }
        return existeixUsuari;
    }
    
    /**
     * @pre nivell > 0 usuId valid i punts > 0
     * @post s'ha registrat el nou nivell superat per l'usuari amb usuId;
     */
    public static void nivellSuperat(int nivell, int usuId, int punts){
        Log log = Log.getInstance(BD.class);
        String consulta = "UPDATE punts SET pnt_punts = "+punts+"\n" +
                            "WHERE pnt_nivell = "+nivell+" and pnt_usu_id = "+usuId;
        try(Connection connexio = DriverManager.getConnection(URL_BD);
            Statement nivellSuperat = connexio.createStatement()){
            if(nivellSuperat.executeUpdate(consulta) < 0){
                throw new RuntimeException("ERROR AL MODIFICAR EL NIVELL AL USUARI");
            }
            else{
                consulta = " INSERT INTO PUNTS (pnt_nivell, pnt_usu_id, pnt_punts)\n" +
                            " VALUES ("+(nivell+1)+", "+usuId+", 0)";
                if(nivellSuperat.executeUpdate(consulta) < 0){
                    throw new RuntimeException("ERROR AL AFEGIR UN NOU NIVELL AL USUARI");
                }
            }
        }
        catch(SQLException excepcio){
            log.afegirError("Error amb la B.D. "+excepcio.getMessage());
        }
    }
    
    /**
     * @pre --;
     * @post si existeix l'usuari i el password és correcte es retorna la seva
     * representació en forma d'objecte altrament es retorna null;
     **/
    public static Usuari obtenirUsuari(String user, String password){
        Usuari usuari = null;
        boolean taulesCreades = true;
        if(BD.existeixBaseDeDades()){
            taulesCreades = crearTaules();
        }
        if(taulesCreades){
            Log log = Log.getInstance(BD.class);
            String consulta = "SELECT usu_id, usu_nom, usu_ruta_imatge, MAX(pnt_nivell) nivell\n" +
                                " FROM usuaris,  punts\n" +
                                " WHERE usu_nom = '"+user+"' AND usu_pass = '"+password +"'AND usu_id = pnt_usu_id\n" +
                                " GROUP BY usu_id, usu_nom, usu_ruta_imatge";
            try(Connection connexio = DriverManager.getConnection(URL_BD);
                Statement consultarUsuari = connexio.createStatement();
                ResultSet rs = consultarUsuari.executeQuery(consulta)){
                if(rs.next()){
                    int id = rs.getInt("usu_id");
                    String nomUsuari = rs.getString("usu_nom");
                    String rutaImatge = rs.getString("usu_ruta_imatge");
                    int nivell = rs.getInt("nivell");
                    usuari = new Usuari(id, nomUsuari, nivell, rutaImatge);
                }
                else log.afegirDebug("Usuari "+user+" no registrat");
            }
            catch(SQLException excepcio){
                log.afegirError("No em pogut consultar l'usuari "+user+" missatge:\n"+excepcio.getMessage());
            }
        }
        return usuari;
    }
    
    /**
     * @pre --;
     * @post diu si existeix el fitxer "sqlite" de la Base de dades;
     */
    public static boolean existeixBaseDeDades(){
        File baseDades = new File(PATH_BD);
        return !baseDades.exists();
    }

    /**
     * @pre --;
     * @post s'han creat les taules d'Usuaris (usu_id, usu_nom, usu_pass, usu_ruta_imatge)
     * i Punts(pnt_nivell, pnt_usu_id, pnt_punts);
     */
    private static boolean crearTaules(){
        Log log = Log.getInstance(BD.class);
        boolean totOk = true;
        try(Connection connexio = DriverManager.getConnection(URL_BD);
            Statement creacioTaulaUsuaris = connexio.createStatement();
            Statement creacioTaulaPunts = connexio.createStatement()
                ){
            creacioTaulaUsuaris.executeUpdate("CREATE TABLE Usuaris (\n" +
                                                "  usu_id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                                                "  usu_nom TEXT NOT NULL,\n" +
                                                "  usu_pass TEXT NOT NULL,\n"+
                                                "  usu_ruta_imatge TEXT NOT NULL)");
            creacioTaulaPunts.executeUpdate("CREATE TABLE Punts(\n" +
                                            " pnt_nivell INTEGER NOT NULL,\n" +
                                            " pnt_usu_id INTEGER NOT NULL,\n" +
                                            " pnt_punts INTEGER NOT NULL,\n" +
                                            " PRIMARY KEY (pnt_nivell, pnt_usu_id),\n" +
                                            " FOREIGN KEY (pnt_usu_id) REFERENCES Usuaris(usu_id))");
            log.afegirDebug("S'ha creat de forma correcte la taula Usuaris i Punts");
        }
        catch(SQLException sqlException){
            log.afegirError("Error al crear les taules Usuaris i Punts missatge: "+sqlException.getMessage());
            totOk = false;
        }
        return totOk;
    }
    
    /**
     * @pre usuari està registrat en el sistema;
     * @post s'ha retornat un array amb el conjunt de nivells superats on 
     * l'index correspon al nivell i el contingut als punts.
     * 
     * nivell[5] -> 4000 vol dir que del nivell 5 s'han fet 4000 punts
     * 
     * En cas de haver un problema amb la B.D. es retorna null;
     */
    public static int [] obtenirHistoricPuntsUsuari(Usuari usuari){
        int resultat[] = null;
        try(Connection connexio = DriverManager.getConnection(URL_BD);
            Statement consultarHistoricUsuari = connexio.createStatement()){
            ResultSet rs = consultarHistoricUsuari.executeQuery(" SELECT pnt_punts, pnt_nivell\n" +
                                                                " FROM punts\n" +
                                                                " WHERE pnt_usu_id = "+usuari.obtenirId()+" AND pnt_punts <> 0\n" +
                                                                " ORDER BY pnt_nivell ASC");
            resultat = new int[6];
            int x = 0;
            while(rs.next()){
                resultat[++x] = rs.getInt("pnt_punts");
            }
            resultat = Arrays.copyOf(resultat, x+1);
        }
        catch(SQLException sqlException){
            System.err.println(sqlException.getMessage());
        }
        return resultat;
    }
}
