/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dades;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import logica.Usuari;
import logica.Utils;
import logica.log.Log;

/**
 *
 * @author oscar
 */
public class BD {
    public static final String PATH_BD = "raw/pacman.db";
    public static final String URL_BD = "jdbc:sqlite:"+PATH_BD;
    
    public static boolean afegirUsuari(String user, String password, String rutaImatge) throws IOException{
        boolean operacioOK = false;
        boolean taulesCreades = true;
        Log log = Log.getInstance(BD.class);
        if(BD.esPrimerAcces()){
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
    
    public static Usuari[] obtenirRanking(int topN){
        Log log = Log.getInstance(BD.class);
        Usuari[] usuaris = null;
        boolean taulesCreades = true;
        if(BD.esPrimerAcces()){
            taulesCreades = crearTaules();
        }
        if(taulesCreades){
            String consulta = " SELECT SUM(p.pnt_punts) AS pnt_punts, u.usu_nom, u.usu_ruta_imatge\n" +
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
                    tmp.add(new Usuari(-1, rs.getString("usu_nom"), -1, rs.getString("usu_ruta_imatge")));
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
    
    public static boolean existeixUsuari(String usuari){
        Log log = Log.getInstance(BD.class);
        boolean taulesCreades = true;
        boolean existeixUsuari = false;
        if(BD.esPrimerAcces()){
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
    
    public static Usuari obtenirUsuari(String user, String password){
        Usuari usuari = null;
        boolean taulesCreades = true;
        if(BD.esPrimerAcces()){
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
    
    public static boolean esPrimerAcces(){
        File baseDades = new File(PATH_BD);
        return !baseDades.exists();
    }

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
}
