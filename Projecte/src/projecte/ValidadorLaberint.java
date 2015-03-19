package projecte;

import java.util.Arrays;

/**
 * @author Oscar.Galera
 * DECLARACIÓ D'INTENCIONS DE LA CLASSE
 * Aquesta classe conté la lògica per validar un laberint, entenem que un laberint
 * és valid si:
 *      -No hi ha cap punt inaccesible.
 *      -Hi ha un únic personatje principal.
 *      -Hi ha un únic enemic.
 * 
 * El format del laberint ha de ser:
 *      - -1 -> no és pot passar;
 *      -  0 -> no hi ha res;
 *      -  1 -> hi ha una moneda normal;
 *      -  2 -> hi ha una moneda especial;
 *      -100 -> posició del personatje principal;
 *      -101 -> posició del enemic;
 */
public class ValidadorLaberint {
    
    private static int laberint [][] = {
                                    {0,0,0,0,-1},
                                    {-1,0,0,-1,0},
                                    {0,0,0,0,-1},
                                    {0,0,-1,0,-1},
                                    {0,0,0,0,0}
                                };
    
    private static int laberint1 [][] = {
                                    {0,0,0,0,0},
                                    {0,-1,0,0,0},
                                    {0,-1,0,0,-1},
                                    {0,-1,0,-1,0},
                                    {0,-1,-1,-1,-1}
                                };
    
    public static boolean laberintValid(int costat){
        //Mode verbose pel Log
        Log l = Log.getInstance(ValidadorLaberint.class);
        l.afegirDebug("Validem un laberint de "+costat+" X "+costat);
        
        boolean valid = true;
        Particio p = new Particio(costat);
        int posActual = 0;
        int i = 0;
        while(valid && i < costat){
            int j = 0;
            while(valid && j < costat){
                if(laberint1[i][j] != -1){
                    //No és pared
                    if(j-1 >= 0 && laberint1[i][j-1] != -1){
                        int esquerra = i*costat+(j-1);
                        p.afegirALaParticio(esquerra, posActual);
                    }
                    else if(i-1 >= 0 && laberint1[i-1][j] != -1){
                        int adalt = (i-1)*costat+j;
                        p.afegirALaParticio(adalt,posActual);
                    }
                    else{
                        //Quan tenim una nova subpartició cal mirar que el laberint
                        //continua siguent valid, això ho farem mirant al costat
                        //dret i abaix, en cas que estiguin fora del tauler
                        //els dos o hi hagi pared llavors el laberint no serà valid;
                        valid = !((j+1 >= costat || laberint1[i][j+1] == -1) 
                                    &&
                                (i+1 >= costat || laberint1[i+1][j] == -1));
                        
                        //En cas que sigui valid continuem amb la validació
                        if(valid) p.afegirALaParticio(posActual,posActual);
                    }
                }
                j++;
                posActual++;
            }
            i++;
        }
        
        valid = valid && p.getNParticions() == 1;
        if(valid){
            l.afegirDebug("Laberint validat en "+posActual+" voltes, resultat: VALID");
        }
        else{
            l.afegirDebug("Laberint validat en "+posActual+" voltes, resultat: NO VALID");
        }
        
        return valid;
    }
    
    /**
     * @author Oscar.Galera
     * DECLARACIÓ D'INTENCIONS DE LA CLASSE
     * Aquesta classe l'utilitzarem amb la finalitat de validar un laberint,
     * el seu funcionament és bàsicament el de una partició
     */
    private static class Particio{
        /**
         * Vector per guardar tots els elements de la partició
         */
        private final int [] particio;
        
        /**
         * Determina la mida de la partició, la qual serà costat X costat
         */
        private final int costat;
        
        /**
         * Contador que ens indica el nombre de "subParticions" que tenim en cada moment
         */
        private int nParticions;
        
        /**
         * @pre: costat >= 0;
         * @post: Em creat una partició de costat X costat on tots els seus
         * elements són -1
         * @param costat determina la mida de la partició
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
         * @pre: pos >= 0 && pos < costat X costat
         * @post: retornem l'id de la posició especificada;
         * @param pos a buscar l'id
         * @return id de la posició;
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
         * @pre: pare i pos són >= 0 i <= costat x costat
         * @post: si pare i pos són iguals es crea una subpartició nova
         * altrament l'enter de la posició pos apunta a pare;
         * @param pare de la fusió;
         * @param pos de la fusió;
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
         * @pre: --;
         * @post: retornem el nombre de subparticions que té la partició;
         * @return nombre de subparticions de la partició;
         */
        public int getNParticions(){
            return this.nParticions;
        }
        
        /**
         * MÈTODE TEMPORAL!!!!
         * @pre: --;
         * @post: mostrem l'estat de la partició;
         */
        public void mostrarParticio(){
            System.out.print("[");
            for(int i = 0; i < particio.length; i++){
                System.out.print(i+", ");
            }
            System.out.print("]\n");
            System.out.println(Arrays.toString(particio));
            System.out.println("n particions "+nParticions);
        }
    }
}
