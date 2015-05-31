package logica;

import dades.BD;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import logica.enumeracions.EElement;
import logica.enumeracions.ELaberintsPredefinits;

/**
 * @author oscar
 * @brief
 * usuari registrat a l'aplicació que consta de
 *      - id -> identificador de la B.D.
 *      - nomUsuari -> el nom del usuari
 *      - ulImatge -> ruta de l'imatge de perfil.
 *      - nivell -> en quin nivell es troba de l'aventura.
 *      - dificultat -> dins del nivell per quina dificultat va.
 *      - punts -> cumul de punts del usuari per el nivell en que està.
 * 
 * @invariant
 * id != null, nomUsuari != null, imatgePerfil != null, punts >= 0
 */
public class Usuari {
    private final int id; /**<identificador de la B.D*/
    private final String nomUsuari; /**<el nom del usuari*/
    private final ImageIcon imatgePerfil; /**<imatge de perfil asociada al usuari*/
    private ENivells nivell; /**<en quin nivell es troba de l'aventura*/
    private EDificultat dificultat; /**<dins del nivell per quina dificultat va*/
    private int punts; /**<cumul de punts del usuari per el nivell en que està*/
    
    /**
     * @author oscar
     * @brief
     * dificultat del laberint dins del nivell
     * 
     * principalment la dificultat la basarem amb el tipus de fantasma amb 
     * el que t'enfrontes poden ser:
     *      FANTASMA 1 -> serà el nivell fàcil
     *      FANTASMA 2 -> serà el nivell mitjà
     *      FANTASMA 3 -> serà el nivell difícil.
     */
    public static enum EDificultat{
        FACIL(EElement.FANTASMA1, ELaberintsPredefinits.LABERINT_LINEAL_HORITZONTAL),
        MITJA(EElement.FANTASMA2, ELaberintsPredefinits.LABERINT_ALEATORI),
        DIFICIL(EElement.FANTASMA3, ELaberintsPredefinits.LABERINT_ALEATORI);
        
        private final EElement enemic;/**<enemic que hi ha per aquesta dificultat*/
        private final ELaberintsPredefinits laberint;
        /**
         * @pre --
         * @post em creat una dificultat amb un enemic asociat.
         * @param enemic 
         */
        private EDificultat(EElement enemic, ELaberintsPredefinits laberint){
            this.enemic = enemic;
            this.laberint = laberint;
        }
        
        /**
         * @pre --
         * @post em retornat l'enemic que hi ha darrera d'aquesta dificultat.
         * @return 
         */
        public EElement obtenirEnemicAssignatADificultat(){
            return this.enemic;
        }
        
        /**
         * @pre --
         * @post em retornat el tipus de laberint asociat al fantasma.
         */
        public ELaberintsPredefinits obtenirTipusLaberint(){
            return laberint;
        }
        
        @Override
        public String toString(){
            return this.name();
        }
    };
    
    /**
     * @author oscar
     * @brief
     * diferents nivells en el mode aventura.
     * 
     * La diferencia entre nivells és la mida del laberint, on:
     *      PRIMER NIVELL -> laberint de 11 x 11
     *      SEGÓN NIVELL -> laberint de 15 x 15
     *      TERCER NIVELL -> laberint de 21 x 21
     *      QUART NIVELL -> laberint de 25 x 25
     *      CINQUE NIVELL -> laberint de 35 x 35
     * 
     */
    public static enum ENivells{
        PRIMER(11)/*11*/, SEGON(15)/*15*/, TERCER(21)/*21*/, QUART(25), CINQUE(35);
        private final int costatLaberint; /**<Mida del costat del laberint asociada al nivell */
        
        /**
         * @pre midaLaberint > Utils.Constants.MINIM_COSTAT_LABERINT
         * @post em creat un nivell amb costatLaberint asociada.
         */
        private ENivells(int costatLaberint){
            this.costatLaberint = costatLaberint;
        }
        
        /**
         * @pre --
         * @post em retornat la mida del costat del laberint asociada al nivell.
         */
        public int obtenirMidaLaberint(){
            return this.costatLaberint;
        }
        
        /**
         * @pre --
         * @post retornem el seguent nivell.
         */
        public ENivells seguentNivell(){
            switch(this){
                case PRIMER:{
                    return SEGON;
                }
                case SEGON:{
                    return TERCER;
                }
                case TERCER:{
                    return QUART;
                }
                default:{
                    return CINQUE;
                }
            }
        }
        
        /**
         * @pre --
         * @post em retornat el nivell segons el seu id PRIMER(1) SEGON(2) ... CINQUE (5>=)
         */
        public static ENivells obtenirNivellPerId(int id){
            switch(id){
                case 1:return PRIMER;
                case 2:return SEGON;
                case 3:return TERCER;
                case 4:return QUART;
                default:return CINQUE;
            }
        }
        
        /***
         * @pre --
         * @post em retornat l'id del nivell PRIMER -> 1, SEGÓN -> 2...
         */
        public int obtenirIdNivell(){
            switch(this){
                case PRIMER: return 1;
                case SEGON: return 2;
                case TERCER: return 3;
                case QUART: return 4;
                default: return 5;
            }
        }
        
        @Override 
        public String toString(){
            switch(this){
                case PRIMER:{
                    return "1";
                }
                case SEGON:{
                    return "2";
                }
                case TERCER:{
                    return "3";
                }
                case QUART:{
                    return "4";
                }
                default:{
                    return "5";
                }
            }
        }
    }
    /**
     * @pre l'urlImage és una ruta valida a una imatge.
     * @post em creat un usuari amb id, nomUsuari, nivell i ruta de l'imatge de perfil.
     */
    public Usuari(int id, String nomUsuari, int nivell, String urlImatge){
        this.id = id;
        this.dificultat = EDificultat.FACIL;
        this.nomUsuari = nomUsuari;
        this.nivell = ENivells.obtenirNivellPerId(nivell);
        this.imatgePerfil = new ImageIcon(urlImatge);
        this.punts = 0;
    }
    
    /**
     * @pre --
     * @post em retornat la dificultat dins del nivell en que esta l'usuari.
     */
    public EDificultat obtenirDificultat(){
        return this.dificultat;
    }
    
    /**
     * @pre --
     * @post em retornat l'id de l'usuari.
     */
    public int obtenirId(){
        return this.id;
    }

    /**
     * @pre --
     * @post em retornat el nom de l'usuari.
     */
    public String obtenirNomUsuari(){
        return this.nomUsuari;
    }
    
    /**
     * @pre --
     * @post em retornat el nivell en que està l'usuari
     */
    public ENivells obtenirNivell(){
        return this.nivell;
    }
    
    /**
     * @pre 
     * @return 
     */
    public Icon obtenirImatge(){
        return imatgePerfil;
    }
    
    /**
     * @pre --
     * @post l'usuari va per la seguent dificultat del nivell, si s'ha superat
     * la màxima dificultat per el nivell es va al seguent nivell.
     */
    public void pantallaSuperada(int pnt){
        punts += pnt;
        switch(dificultat){
            case FACIL:{
                dificultat = EDificultat.MITJA;
            }break;
            case MITJA:{
                dificultat = EDificultat.DIFICIL;
            }break;
            case DIFICIL:{
                BD.nivellSuperat(nivell.obtenirIdNivell(), id, punts);
                nivell = nivell.seguentNivell();
                dificultat = EDificultat.FACIL;
                this.punts = 0;
            }break;
        }
    }
}
