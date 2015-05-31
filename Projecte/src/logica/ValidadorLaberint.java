package logica;

import logica.enumeracions.EElement;
import logica.log.Log;

/**
 * @author Oscar.Galera
 * @brief
 * Aquesta classe conté la lògica per validar un laberint, entenem que un laberint
 * és valid si:
 *      -No hi ha cap punt inaccesible.
 *      -Com a mínim hi ha 2 caselles accesibles.
 *      -Hi ha un únic personatje principal.
 *      -Hi ha un únic enemic.
 */
public class ValidadorLaberint {
    
    /**
     * @pre costat >= 2 i laberint és de mida costat x costat
     * @post diu si el laberint és valid o no.
     */
    public static boolean validarLaberint(int [][] laberint, int costat){
        //Mode verbose pel Log
        Log l = Log.getInstance(ValidadorLaberint.class);
        l.afegirDebug("Validem un laberint de "+costat+" X "+costat);
        int idParet = EElement.PARET.obtenirId();
        int casellaPacman = -1;
        int casellaFantasma = -1;
        //Per indicar el nombre de pacmans que hi ha en el log;
        int nPacmans = 0; 
        //Per indicar el nombre de fantasmes que hi ha en el log;
        int nFantasmes = 0;
        
        boolean valid = true;
        Particio p = new Particio(costat);
        int posActual = 0;
        int i = 0;
        while(valid && i < costat){
            int j = 0;
            while(valid && j < costat){
                if(laberint[i][j] != idParet){
                    if(laberint[i][j] == EElement.PACMAN.obtenirId()){
                        //Em trobat un pacman
                        if(casellaPacman != idParet){
                            nPacmans++;
                            valid = false;
                        }
                        else{
                            //Pacman trobat;
                            casellaPacman = posActual;
                            nPacmans++;
                            l.afegirDebug("S'ha trobat en pacman en la posicio "+casellaPacman);
                        }
                    }
                    else if (laberint[i][j] >= EElement.FANTASMA1.obtenirId()){
                        //Em trobat un fantasma
                        if(casellaFantasma != -1){
                            nFantasmes++;
                            valid = false;
                        }
                        else{
                            //fantasma trobat;
                            nFantasmes++;
                            casellaFantasma = posActual;
                            l.afegirDebug("S'ha trobat el fantasma en la posició "+casellaFantasma);
                        }
                    }
                    //Mirem esquerra
                    if(j-1 >= 0 && laberint[i][j-1] != idParet){
                        //No és pared ni estem fora;
                        int esquerra = i*costat+(j-1);
                        p.afegirALaParticio(esquerra, posActual);
                    }
                    //Altrament mirem amunt
                    else if(i-1 >= 0 && laberint[i-1][j] != idParet){
                        //No és pared ni estem fora;
                        int adalt = (i-1)*costat+j;
                        p.afegirALaParticio(adalt,posActual);
                    }
                    else{
                        //Quan tenim una nova subpartició cal mirar que el laberint
                        //continua siguent valid, això ho farem mirant al costat
                        //dret i abaix, en cas que estiguin fora del tauler
                        //els dos o hi hagi pared llavors el laberint no serà valid;
                        if((j+1 >= costat || laberint[i][j+1] == idParet) && (i+1 >= costat || laberint[i+1][j] == idParet)){
                            l.afegirError("La casella "+posActual+" esta aillada");
                            valid = false;
                        }
                        
                        //En cas que sigui valid continuem amb la validació,
                        if(valid) p.afegirALaParticio(posActual,posActual);
                    }
                }
                j++;
                posActual++;
            }
            i++;
        }
        
        valid = valid && p.getNParticions() == 1 && casellaPacman != -1 && casellaFantasma != -1;
        if(valid){
            l.afegirError("Laberint validat en "+posActual+" voltes, resultat: VALID");
        }
        else{
            String missatge = "Laberint validat en "+posActual+" voltes, resultat: NO VALID";
            if(p.getNParticions() != 1){
                missatge+="\n\tEl laberint no és connex";
            }
            else if(nPacmans == 0){
                missatge+="\n\t-No hi ha pacman en el laberint";
            }
            else if (nFantasmes == 0){
                missatge+="\n\t-No hi ha fantasma en el laberint";
            }
            else if(nPacmans > 1){
                missatge+="\n\t-Hi ha mes de un pacman en el laberint";
            }
            else{
                missatge+="\n\t-Hi ha mes de un fantasma en el laberint";
            }
            l.afegirError(missatge);
        }
        return valid;
    }
    
    /**
     * @pre costat >= 2 i laberint és de mida costat x costat
     * @post diu si el laberint és valid o no.
     */
    public static boolean validarLaberint(EElement [][] laberint, int costat){
        //Mode verbose pel Log
        Log l = Log.getInstance(ValidadorLaberint.class);
        l.afegirDebug("Validem un laberint de "+costat+" X "+costat);
        
        int casellaPacman = -1;
        int casellaFantasma = -1;
        //Per indicar el nombre de pacmans que hi ha en el log;
        int nPacmans = 0; 
        //Per indicar el nombre de fantasmes que hi ha en el log;
        int nFantasmes = 0;
        
        boolean valid = true;
        Particio p = new Particio(costat);
        int posActual = 0;
        int i = 0;
        while(valid && i < costat){
            int j = 0;
            while(valid && j < costat){
                if(laberint[i][j] != EElement.PARET){
                    if(laberint[i][j] == EElement.PACMAN){
                        //Em trobat un pacman
                        if(casellaPacman != EElement.PARET.obtenirId()){
                            nPacmans++;
                            valid = false;
                        }
                        else{
                            //Pacman trobat;
                            casellaPacman = posActual;
                            nPacmans++;
                            l.afegirDebug("S'ha trobat en pacman en la posicio "+casellaPacman);
                        }
                    }
                    else if (laberint[i][j].esEnemic()){
                        //Em trobat un fantasma
                        if(casellaFantasma != -1){
                            nFantasmes++;
                            valid = false;
                        }
                        else{
                            //fantasma trobat;
                            nFantasmes++;
                            casellaFantasma = posActual;
                            l.afegirDebug("S'ha trobat el fantasma en la posició "+casellaFantasma);
                        }
                    }
                    //Mirem esquerra
                    if(j-1 >= 0 && laberint[i][j-1]!= EElement.PARET){
                        //No és pared ni estem fora;
                        int esquerra = i*costat+(j-1);
                        p.afegirALaParticio(esquerra, posActual);
                    }
                    //Altrament mirem amunt
                    else if(i-1 >= 0 && laberint[i-1][j] != EElement.PARET){
                        //No és pared ni estem fora;
                        int adalt = (i-1)*costat+j;
                        p.afegirALaParticio(adalt,posActual);
                    }
                    else{
                        //Quan tenim una nova subpartició cal mirar que el laberint
                        //continua siguent valid, això ho farem mirant al costat
                        //dret i abaix, en cas que estiguin fora del tauler
                        //els dos o hi hagi pared llavors el laberint no serà valid;
                        if((j+1 >= costat || laberint[i][j+1] == EElement.PARET) && (i+1 >= costat || laberint[i+1][j] == EElement.PARET)){
                            l.afegirError("La casella "+posActual+" esta aillada");
                            valid = false;
                        }
                        
                        //En cas que sigui valid continuem amb la validació,
                        if(valid) p.afegirALaParticio(posActual,posActual);
                    }
                }
                j++;
                posActual++;
            }
            i++;
        }
        
        valid = valid && p.getNParticions() == 1 && casellaPacman != -1 && casellaFantasma != -1;
        if(valid){
            l.afegirDebug("Laberint validat en "+posActual+" voltes, resultat: VALID");
        }
        else{
            String missatge = "Laberint validat en "+posActual+" voltes, resultat: NO VALID";
            if(p.getNParticions() != 1){
                missatge+="\n\tEl laberint no és connex";
            }
            else if(nPacmans == 0){
                missatge+="\n\t-No hi ha pacman en el laberint";
            }
            else if (nFantasmes == 0){
                missatge+="\n\t-No hi ha fantasma en el laberint";
            }
            else if(nPacmans > 1){
                missatge+="\n\t-Hi ha mes de un pacman en el laberint";
            }
            else{
                missatge+="\n\t-Hi ha mes de un fantasma en el laberint";
            }
            l.afegirDebug(missatge);
        }
        return valid;
    }
    
    /**
     * @author Oscar.Galera
     * @breif
     * Aquesta classe l'utilitzarem amb la finalitat de validar que el laberint sigui connex,
     * el seu funcionament és bàsicament el de una partició
     */
    private static class Particio{
        private final int [] particio; /**<Vector per guardar tots els elements de la partició*/
        private final int costat; /**<Determina la mida de la partició, la qual serà costat X costat*/
        private int nParticions; /**<Contador que ens indica el nombre de "subParticions" que tenim en cada moment*/
        
        /**
         * @pre costat >= 0;
         * @post Em creat una partició de costat X costat on tots els seus
         * elements són -1
         */
        public Particio(int costat){
            this.costat = costat;
            this.particio = new int[costat*costat];
            this.nParticions = 0;
            for(int i = 0; i < costat*costat; i++){
                this.particio[i] = -1;
            }
        }
        
        /**
         * @pre pos >= 0 && pos < costat X costat
         * @post retornem l'id de la posició especificada;
         */
        public int obtenirId(int pos){
            boolean trobat = false;
            //Notar que realitzem un bucle que podria semblar infinit
            //però sempre acabarà trobant l'arrel de l'element, per tant
            //trobat passarà a ser true i acabarà el bucle;
            do{
                pos = particio[pos];
                if(pos == particio[pos]) trobat = true;
            }while(!trobat);
            return pos;
        }
        
        /**
         * @pre pare i pos són >= 0 i <= costat x costat
         * @post si pare i pos són iguals es crea una subpartició nova
         * altrament l'enter de la posició pos apunta a pare;
         */
        public void afegirALaParticio(int pare, int pos){
            if(pare == pos){
                //Tenim nova subpartició
                particio[pare] = pare;
                nParticions++;
            }
            else{
                //afegim pos a la subparticio de pare;
                particio[pos] = pare;
                //cada vegada que afegim un element a una subpartició mirem si
                //es pot fusionar l'element amb el subconjunt de la fila superior;
                if(pos-costat >= 0 && particio[pos-costat] != -1){
                    int idFilaSuperior = obtenirId(pos-costat);
                    int idPare = obtenirId(pare);
                    if(idFilaSuperior != idPare){
                        //Tenim arrels diferents per tant procedim a fusionar
                        //dos subparticions i ara tenim una subpartició menys;
                        particio[idPare] = pos;
                        particio[pos] = pos-costat;
                        this.nParticions--;
                    }
                }
            }
        }
        
        /**
         * @pre --
         * @post retornem el nombre de subparticions que té la partició.
         */
        public int getNParticions(){
            return this.nParticions;
        }
    }
}
