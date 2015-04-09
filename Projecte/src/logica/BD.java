/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import log.Log;

/**
 *
 * @author oscar
 */
public class BD {
    
    public static void afegirRegistre(String user, String password, int nivell, int punts) throws IOException{
        boolean taulesCreades = true;
        Log log = Log.getInstance(BD.class);
        if(BD.esPrimerAcces()){
            taulesCreades = crearTaules();
        }
        if(taulesCreades){
            //Try with resources since JAVA SE7
            try(Connection connexio = DriverManager.getConnection(Utils.Constants.URL_BD);
                Statement afegirUsuari = connexio.createStatement()){

                //Per temes de seguretat codifiquem el password;
                String passCodificat = Utils.codificarCadena(password);

                int i = afegirUsuari.executeUpdate(" INSERT INTO Usuaris (usu_nom, usu_pass)\n" +
                                                    " VALUES ('"+user+"', '"+passCodificat+"')");
                log.afegirDebug("S'ha afegit l'usuari: "+user);
            }
            catch(SQLException exepcio){
                log.afegirError(exepcio.getMessage());
            }
        }
        else{
            log.afegirError("No s'ha afegit l'usuari: "+user);
        }
    }
    
    public static String[][] obtenirRanking(int topN){
        Log log = Log.getInstance(BD.class);
        String [][]resultat = null;
        boolean taulesCreades = true;
        if(BD.esPrimerAcces()){
            taulesCreades = crearTaules();
        }
        if(taulesCreades){
            String consulta = "SELECT SUM(p.pnt_punts) AS \"PUNTS\", u.usu_nom AS \"USUARI\"\n" +
                                " FROM punts p, usuaris u\n" +
                                " WHERE u.usu_id = p.pnt_usu_id\n" +
                                " GROUP BY u.usu_id, u.usu_nom\n" +
                                " ORDER BY 1 DESC";
            try(Connection connexio = DriverManager.getConnection(Utils.Constants.URL_BD);
                Statement consultarTopN = connexio.createStatement();
                ResultSet rs = consultarTopN.executeQuery(consulta)){
                resultat = new String [topN][2];
                int i = 0; 
                while(i < topN && rs.next()){
                    resultat[i][0] = rs.getString("PUNTS");
                    resultat[i][1] = rs.getString("USUARI");
                    i++;
                }
            }
            catch(SQLException excepcio){
                log.afegirError("No em pogut obtenir el TOP "+topN+" missatge:\n"+excepcio.getMessage());
            }
        }
        return resultat;
    }
    
    public static boolean usuariRegistrat(String usuari){
        Log log = Log.getInstance(BD.class);
        boolean taulesCreades = true;
        boolean usuariRegistrat = false;
        if(BD.esPrimerAcces()){
            taulesCreades = crearTaules();
        }
        if(taulesCreades){
            String consulta = " SELECT 1\n" +
                              " WHERE EXISTS (SELECT usu_nom\n" +
                              "                 FROM USUARIS\n" +
                              "			WHERE usu_nom = '"+usuari+"')";
            try(Connection connexio = DriverManager.getConnection(Utils.Constants.URL_BD);
                Statement consultarUsuari = connexio.createStatement();
                ResultSet rs = consultarUsuari.executeQuery(consulta)){
                usuariRegistrat = rs.next();
                if(usuariRegistrat){
                    log.afegirWarning("Usuari "+usuari+" ja registrat");
                }
                else log.afegirDebug("Usuari "+usuari+" no registrat");
            }
            catch(SQLException excepcio){
                log.afegirError("No em pogut consultar l'usuari "+usuari+" missatge:\n"+excepcio.getMessage());
            }
        }
        return usuariRegistrat;
    }
    
    public static boolean esPrimerAcces(){
        File baseDades = new File(Utils.Constants.PATH_BD);
        return !baseDades.exists();
    }
    
    public static void main(String ... args)throws IOException{
        BD.usuariRegistrat("ari");
        afegirRegistre("ari", "pol", 0, 0);
        BD.usuariRegistrat("ari");
        Log log = Log.getInstance(BD.class);
        System.out.println(log.obtenirContingutCompletDelLogAmbColor());
    }
    
    private static boolean crearTaules(){
        Log log = Log.getInstance(BD.class);
        boolean totOk = true;
        try(Connection connexio = DriverManager.getConnection(Utils.Constants.URL_BD);
            Statement creacioTaulaUsuaris = connexio.createStatement();
            Statement creacioTaulaPunts = connexio.createStatement()
                ){
            creacioTaulaUsuaris.executeUpdate("CREATE TABLE Usuaris (\n" +
                                                "  usu_id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                                                "  usu_nom TEXT NOT NULL,\n" +
                                                "  usu_pass TEXT NOT NULL)");
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
