package logica.log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * @author Oscar.Galera
 * @brief
 * Sistema de log per veure que està passant en tot moment en el sistema;
 */
public class Log {
    /**
     * Estructura que enmagatzema els registres del log;
     */
    final private LinkedList<ParellaPrioritatMissatge> registres;
    
    /**
     * Classe activa que té el log
     */
    private Class clase;
    
    /**
     * Instancia única del log SINGLETON PATTERN DESIGN;
     */
    private static Log log;
    
    /**
     * @author Oscar.Galera
     * Classe interna que ens agrupa un missatge amb un grau de prioritat;
     */
    private class ParellaPrioritatMissatge{
        private final String missatge;
        private final Prioritat prioritat;
        
        /**
         * @pre: --;
         * @post: s'ha creat una niva parellaPrioritatMissatge amb els parametres
         * especificats
         */
        public ParellaPrioritatMissatge(String missatge, Prioritat prioritat){
            this.missatge = missatge;
            this.prioritat = prioritat;
        }
        
        /**
         * @pre:--;
         * @post: retornem el missatge;
         */
        public String getMissatge(){
            return missatge;
        }
        
        /**
         * @pre:--;
         * @post: retornem la prioritat del missatge;
         */
        public Prioritat getPrioritat(){
            return prioritat;
        }
    }
    
    /**
     * @author Oscar.Glaera
     * @brief
     * Utilitzem aquesta enumeració per mantenir diferents nivells de prioritat
     * en els missatges de el log, tenim les prioritats:
     *      -ERROR -> Màxima prioritat del missatge;
     *      -WARNING -> Prioritat mitja;
     *      -DEBUG -> Missatge sense gaire importancia útil per depurar;
     */
    private enum Prioritat{
        ERROR, WARNING, DEBUG;
    }
    
    /**
     * @pre: --;
     * @post: tenim una instancia del log;
     */
    private Log(){
        //Per el log ens decantem per una llista enllaçada ja que en el log
        //principalment afegirem registres (COST O(1)) i si necessitem consultar
        //el seu contingut serà una consulta seqüencial.
        registres = new LinkedList<>();
    }
    
    /**
     * @pre: clase no és null;
     * @post: em obtingut LA instancia del log; 
     */
    public static Log getInstance(Class clase){
        if(clase == null) throw new NullPointerException("Has de obtenir el log a partir de una classe");
        if(log == null){
            log = new Log();
        }
        log.clase = clase;
        return log;
    }
    
    /**
     * @pre: --;
     * @post: em afegit el registre a la estructura log amb prioritat ERROR;
     */
    public void afegirError(String registre){
        this.afegirRegistre(Prioritat.ERROR, registre);
    }
    
    /**
     * @pre: --;
     * @post: em afegit el registre a la estructura log amb prioritat WARNING;
     */
    public void afegirWarning(String registre){
        this.afegirRegistre(Prioritat.WARNING, registre);
    }
    
    /**
     * @pre: --;
     * @post: em afegit el registre a la estructura log amb prioritat DEBUG;
     */
    public void afegirDebug(String registre){
        this.afegirRegistre(Prioritat.DEBUG, registre);
    }
    
    /**
     * @pre: --;
     * @post: em afegit el registre a la estructura log amb prioritat especificada;
     */
    private void afegirRegistre(Prioritat prioritat, String registre){
        //FORMAT EN QUE ES GUARDA UN REGISTRE EN EL LOG:
        //Nom_classe_emissora_del_missatge  Data    registre
        Date d = new Date();
        String missatge = clase.getCanonicalName()+"\t"+d.toString()+"\t"+registre;
        ParellaPrioritatMissatge novaParella = new ParellaPrioritatMissatge(missatge, prioritat);
        registres.add(novaParella);
    }
    
    /**
     * @pre: --;
     * @post: Em retornat el contingut complet del registre en format String;
     */
    public String obtenirContingutCompletDelLogAmbColor(){
        //FORMAT EN QUE ES RETORNA CADA UN DELS REGISTRES DEL LOG
        //PRIORITAT Nom_classe_emissora_del_missatge    Data    registre
        String resultat = "";
        for(ParellaPrioritatMissatge registre: registres){
            switch(registre.getPrioritat()){
                case ERROR:{
                    resultat+=(char)27 + "[31m"+registre.getPrioritat()+"\t"+registre.getMissatge()+(char)27+"[0m\n";
                }break;
                case DEBUG:{
                    resultat+=(char)27 + "[32m"+registre.getPrioritat()+"\t"+registre.getMissatge()+(char)27+"[0m\n";
                }break;
                case WARNING:{
                    resultat+=(char)27 + "[33m"+registre.getPrioritat()+"\t"+registre.getMissatge()+(char)27+"[0m\n";
                }break;
            }
        }
        return resultat;
    }
    
    /**
     * @pre: --;
     * @post: Em retornat el contingut complet del registre en format String;
     */
    public String obtenirContingutCompletDelLog(){
        //FORMAT EN QUE ES RETORNA CADA UN DELS REGISTRES DEL LOG
        //PRIORITAT Nom_classe_emissora_del_missatge    Data    registre
        String resultat = "";
        for(ParellaPrioritatMissatge registre: registres){
            switch(registre.getPrioritat()){
                case ERROR:{
                    resultat+=registre.getPrioritat()+"\t"+registre.getMissatge()+"\n";
                }break;
                case DEBUG:{
                    resultat+=registre.getPrioritat()+"\t"+registre.getMissatge()+"\n";
                }break;
                case WARNING:{
                    resultat+=registre.getPrioritat()+"\t"+registre.getMissatge()+"\n";
                }break;
            }
        }
        return resultat;
    }
    
    /**
     * @pre:--;
     * @post: retornem tots els registres del log amb prioritat ERROR;
     */
    public String obtenirErrorsLog(){
        return obtenirRegistreDeUnaPrioritat(Prioritat.ERROR);
    }
    
    /**
     * @pre:--;
     * @post: retornem tots els registres del log amb prioritat WARNING;
     */
    public String obtenirWarningsLog(){
        return obtenirRegistreDeUnaPrioritat(Prioritat.WARNING);
    }
    
    /**
     * @pre:--;
     * @post: retornem tots els registres del log amb prioritat DEBUG;
     */
    public String obtenirDebugsLog(){
        return obtenirRegistreDeUnaPrioritat(Prioritat.DEBUG);
    }

    /**
     * @pre:--;
     * @post: retornem l'últim missatge d'error registrat.
     */
    public String obtenirUltimError(){
        return this.getUltimRegistreDeUnTipus(Prioritat.ERROR);
    }
    
    /**
     * @pre:--;
     * @post: retornem l'últim missatge de warning registrat.
     */
    public String obtenirUltimWarning(){
        return this.getUltimRegistreDeUnTipus(Prioritat.WARNING);
    }
    
    /**
     * @pre:--;
     * @post: retornem l'últim missatge de debug registrat.
     */
    public String obtenirUltimDebug(){
        return this.getUltimRegistreDeUnTipus(Prioritat.DEBUG);
    }

    /**
     * @pre:--;
     * @post: retornem l'últim missatge de prioritat registrat.
     */
    private String getUltimRegistreDeUnTipus(Prioritat prioritat){
        Iterator<ParellaPrioritatMissatge> i = registres.descendingIterator();
        boolean trobat = false;
        String missatge = null;
        while(!trobat && i.hasNext()){
            ParellaPrioritatMissatge p = i.next();
            if(p.getPrioritat() == prioritat){
                trobat = true;
                missatge = p.getMissatge();
            }
        }
        return missatge;
    }
    
    /**
     * @pre: prioritat != null;
     * @post: retornem un string amb la representació de tots els registres
     * que tinguin la prioritat especificada;
     * @param prioritat que especifica els registres a seleccionar
     * @return String que representa el conjunt de registres amb prioritat especificada;
     */
    private String obtenirRegistreDeUnaPrioritat(Prioritat prioritat){
        String resultat = "";
        for(ParellaPrioritatMissatge registre: registres){
            if(registre.getPrioritat() == prioritat){
                resultat+=prioritat.toString()+"\t"+registre.getMissatge()+"\n";
            }
        }
        if(resultat.length() == 0){
            resultat = "NO HI HA CONTINGUT EN EL LOG DE "+prioritat.toString();
        }
        return resultat;
    }
    
    /**
     * @pre fitxer és una ruta a un fitxer valida
     * @post s'ha exportat el log al fitxer.
     */
    public void exportarLogAFitxer(String fitxer){
        File f = new File(fitxer);
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(f))){
            String contingutLog = this.obtenirContingutCompletDelLog();
            bw.write(contingutLog);
            log.afegirDebug("Log exportat a "+fitxer+" amb exit");
        }
        catch(IOException ioe){
            log.afegirError("No s'ha pogut exportar el log, error: "+ioe.getMessage());
        }
    }
}
