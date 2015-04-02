/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica.generadors_laberint;

import excepcions.LaberintException;
import java.util.Random;
import log.Log;
import logica.EnumElement;
import logica.Posicio;
import logica.ValidadorLaberint;
/**
 * @author oscar
 * DECLARACIÓ D'INTENCIONS DE LA CLASSE
 * Ens encapsula la lògica per generar un laberint quadrat i aleatori;
 */
public class GeneradorLaberintAleatori implements IGeneradorLaberint{
    /**
     * Costat del laberint, tot laberint ha de ser quadrat;
     */
    private final int costat;
    
    /**
     * Enemic que estarà en l'extrem inferior dret (casella [costat-1, costat-1])
     */
    private final EnumElement enemic;
    
    public GeneradorLaberintAleatori(int costat, EnumElement enemic){
        if(!enemic.esEnemic()) throw new LaberintException("Hi ha de haver un enemic en el laberint");
        this.costat = costat;
        this.enemic = enemic;
    }
    
    @Override
    public EnumElement[][] generarLaberint() {
        Log log = Log.getInstance(GeneradorLaberintAleatori.class);

        //Obtenim el moment d'inici de la creació;
        long tIniciCreacio = System.currentTimeMillis();
        
        if(costat < 4) throw new LaberintException("Mida del laberint incorrecte: "+costat);
        log.afegirDebug("Creem un laberint de "+costat+"X"+costat);
        EnumElement[][] tauler = new EnumElement[costat][costat];
        
        //En un principi tot laberint està cobert de parets;
        for(int i = 0; i < costat; i++){
            for(int j = 0; j < costat; j++){
                tauler[i][j] = EnumElement.PARED;
            }
        }
        
        //Omplirem el 50% del tauler;
        int elementsAOmplir = (int)(costat*costat*0.10);
        System.out.println(elementsAOmplir);
        //A la primera posició i va en pacman i a lúltima el fantasma;
        tauler[0][0] = EnumElement.PACMAN;
        tauler[costat-1][costat-1] = enemic;
        
        //Plantem la llavor per obtenir valors aleatoris
        Random r = new Random(System.currentTimeMillis());
        
        int n = 4;
        Posicio [] posiblesPossicions = new Posicio[4];
        //Extrem superior esquerrat
        posiblesPossicions[0] = new Posicio(0,1);
        posiblesPossicions[1] = new Posicio(1,0);
        
        //Extrem inferior dret
        posiblesPossicions[2] = new Posicio(costat-1, costat-2);
        posiblesPossicions[3] = new Posicio(costat-2, costat-1);
        
        int casellesTractades;
        int intentsCrearLaberint = 0;
        do{
            casellesTractades = 0;
            //Cal omplir elementsAOmplir caselles amb monedes
            for(int i = 0; i < elementsAOmplir; i++){
                int indexPosicions = r.nextInt(n);
                Posicio posicioSeleccionada = posiblesPossicions[indexPosicions];
                tauler[posicioSeleccionada.obtenirX()][posicioSeleccionada.obtenirY()] = EnumElement.MONEDA;
                casellesTractades++;
                posiblesPossicions[indexPosicions] = posiblesPossicions[--n];
                Posicio[] posicionsDelVoltant = posicioSeleccionada.obtenirPosicionsDelVoltant();
                if(n == 0){
                    //No queden candidats per tant afegim tots els possibles
                    for(int j = 0; j < 4; j++){
                        Posicio p = posicionsDelVoltant[j];
                        if(posicioValida(p)){
                            posiblesPossicions[n++] = p;
                        }
                    }
                }
                else{
                    //Només afegim una casella valida com a candidat per tal
                    //d'equilibrar el laberint;
                    boolean agafada = false;
                    int j = 0;
                    while(!agafada && j < 4){
                        indexPosicions = r.nextInt(4-j);
                        Posicio p = posicionsDelVoltant[indexPosicions];
                        //Si no queden candidats (n == 0 per tal de evitar bloquejar-nos
                        //en el procés de creació) o la posició és valida i és pared, 
                        //llavors l'afegim com a possible candidat;
                        if((posicioValida(p) && tauler[p.obtenirX()][p.obtenirY()] == EnumElement.PARED)){
                            posiblesPossicions[n++] = p;
                            agafada = true;
                        }
                        //Intercanviem la posició ja tractada;
                        posicionsDelVoltant[indexPosicions] = posicionsDelVoltant[4-j-1];
                        j++;
                    }
                }
            }
            intentsCrearLaberint++;
        }while(casellesTractades != elementsAOmplir || !ValidadorLaberint.validarLaberint(tauler, costat));
//            }
//        }
//        catch(Exception e){
//            int a = 3;
//            int b= a;
//        }
        System.out.println("S'han afegit "+casellesTractades+" caselles");
        
        //MOSTREM EL LABERINT (TEMPORAL!!!)
        for(int i = 0; i < costat; i++){
            for(int j = 0; j < costat; j++){
                if(tauler[i][j].getId() == -1) System.out.print("P");
                else System.out.print("A");
            }
            System.out.println();
        }
        //Obtenim l'hora del rellotge
        long tFiCreacio = System.currentTimeMillis();
        //Afegim al log un missatge indicant el temps que ha tardat l'algoritme
        //a generar el laberint;
        log.afegirDebug("Intents per crear el laberint "+intentsCrearLaberint);
        log.afegirDebug("Temps de creacio del laberint "+(tFiCreacio-tIniciCreacio)+ "ms");
        return tauler;
    }
    
    /**
    * @pre: --;
    * @post: retornem si la posició és valida dins del tauler;
    * @param posicio: a comparar;
    * @param costat: costat del laberint;
    * @return si la posició està dins del tauler;
    */
    private boolean posicioValida(Posicio posicio){
        int x = posicio.obtenirX();
        int y = posicio.obtenirY();
        return x >= 0 && y >= 0 && x < costat && y < costat;
    }
}
