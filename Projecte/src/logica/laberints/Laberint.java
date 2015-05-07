/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica.laberints;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import logica.enumeracions.EDireccio;
import logica.enumeracions.EElement;
import logica.excepcions.EFormatLaberint;
import logica.log.Log;
import interficie.IPintadorLaberint;
import logica.Partida;
import logica.Punt;

/**
 *
 * @author oscar
 */
public class Laberint {
    protected EElement tauler[][];
    
    protected Log log = Log.getInstance(Laberint.class);
    
    protected int costat = -1;
    
    protected Partida partida;
    
    protected int nMonedes = 0;
    
    protected IPintadorLaberint pintador;
    
    public Laberint(String fitxer, Partida partida) throws EFormatLaberint{
        log.afegirDebug("Carreguem un laberint del fitxer "+fitxer);
        File f = new File(fitxer);
        this.partida = partida;
        try(BufferedReader br = new BufferedReader(new FileReader(f))){
            String linia;
            if((linia = br.readLine()) != null){
                this.costat = linia.split(" ").length;
                this.tauler = new EElement[costat][costat];
                int n = 0;
                EElement [] l = parseLiniaLaberint(linia);
                if(l.length != costat) throw new EFormatLaberint("El format del laberint no és correcte");
                this.tauler[n++] = l;
                while((linia = br.readLine()) != null){
                    l = parseLiniaLaberint(linia);
                    if(l.length != costat) throw new EFormatLaberint("El format del laberint no és correcte");
                    this.tauler[n++] = l;
                }
                if(costat != n){
                    throw new EFormatLaberint("El format del laberint no és correcte");
                }
            }
        }
        catch(IOException fnfe){
            throw new EFormatLaberint("No s'ha pogut llegir el fitxer per importar el laberint, missatge:\n"+fnfe.getMessage());
        }
        System.out.println(this);
        this.nMonedes = numeroMonedes();
    }
    
    private EElement [] parseLiniaLaberint(String linia) throws EFormatLaberint{
        String camps[] = linia.split(" ");
        EElement [] liniaElements = new EElement[camps.length];
        for(int i = 0; i < costat; i++){
            liniaElements[i] = EElement.buscarElementPerId(Integer.parseInt(camps[i]));
        }
        return liniaElements;
    }
    
    public Laberint(EElement elements[][], Partida partida){
        log.afegirDebug("Carreguem un laberint des de una matriu quadrada");
        this.costat = elements[0].length;
        this.tauler = elements;
        this.partida = partida;
        this.nMonedes = numeroMonedes();
    }
    
    protected Laberint(Partida partida){
        this.partida = partida;
    }
    
    protected int numeroMonedes(){
        int numMonedes = 0;
        for(int i = 0; i < costat; i++){
            for(int j = 0; j < costat; j++){
                if(tauler[i][j] == EElement.MONEDA || tauler[i][j] == EElement.MONEDA_EXTRA){
                    numMonedes++;
                }
            }
        }
        return numMonedes;
    }
    
    /**
     * @pre: --;
     * @post: retornem si la posició és valida dins del tauler;
     * @param posicio: a comparar;
     * @return si la posició està dins del tauler;
     */
    public boolean posicioValida(Punt posicio){
        int x = posicio.obtenirX();
        int y = posicio.obtenirY();
        return x >= 0 && y >= 0 && x < costat && y < costat;
    }
    
    /**
     * @pre: --;
     * @post: retornem l'element de ubicat en posició, si la posició està fora del
     * laberint es retorna PARET;
     * @param posicio: a consultar;
     * @return: element ubicat en posició;
     */
    public EElement obtenirElement(Punt posicio){
        EElement element = EElement.PARET;
        if(posicioValida(posicio)){
           int x = posicio.obtenirX();
           int y = posicio.obtenirY();
           element = tauler[y][x];
        }
        return element;
    }
    
    public EElement anotarElement(Punt posicio, EDireccio direccio){
        Punt origen = posicio;
        int x = posicio.obtenirX();
        int y = posicio.obtenirY();
        EElement objecteAMoure = this.tauler[y][x];
        EElement Edesti = objecteAMoure;
        this.tauler[y][x] = EElement.RES;
        Punt p = posicio.generarPuntDesplasat(direccio);
        Punt desti = p;
        EElement EOrigen = EElement.RES;
        x = p.obtenirX();
        y = p.obtenirY();
        EElement objecteAgafat = this.tauler[y][x];
        if(objecteAgafat == EElement.MONEDA || objecteAgafat == EElement.MONEDA_EXTRA){
            this.nMonedes--;
            if(nMonedes == 0){
               Punt sortida = sortejarSortida();
               int xSortida = sortida.obtenirX();
               int ySortida = sortida.obtenirY();
               this.tauler[ySortida][xSortida] = EElement.SORTIDA;
               partida.assignarGuanyador();
            }
        }
        if(objecteAgafat == EElement.SORTIDA){
            partida.finalitzarPartida();
        }
        this.tauler[y][x] = objecteAMoure;
        System.out.println(this);
        pintador.pintarMoviment(origen, EOrigen, direccio, Edesti);
        return objecteAgafat;
    }
    
    private Punt sortejarSortida(){
        return new Punt(costat-1, costat-1);
    }
    
    @Override
    public String toString(){
        String resultat = "";
        for(int i = 0; i < costat; i++){
            for(int j = 0; j < costat; j++){
                resultat += this.tauler[i][j].obtenirLletraRepresentacio()+" ";
            }
            resultat += "\n";
        }
        return resultat;
    }
    
    public Punt obtenirPosicioPacman(){
        Punt posicio = null;
        boolean trobat = false;
        int i = 0;
        while(!trobat && i < costat){
            int j = 0;
            while(!trobat && j < costat){
                if(tauler[i][j] == EElement.PACMAN){
                    trobat = true;
                    posicio = new Punt(i, j);
                }
                j++;
            }
            i++;
        }
        return posicio;
    }
    
    public Punt obtenirPosicioEnemic(){
        Punt posicio = null;
        boolean trobat = false;
        int i = 0;
        while(!trobat && i < costat){
            int j = 0;
            while(!trobat && j < costat){
                if(tauler[i][j].esEnemic()){
                    trobat = true;
                    posicio = new Punt(i, j);
                }
                j++;
            }
            i++;
        }
        return posicio;
    }
    
//    public FLaberint obtenirPintadorLaberint(){
//        return pintador;
//    }
//    
//    public void addKeyListener(KeyListener listener){
//        pintador.addKeyListener(listener);
//    }
    
    public void assignarPintador(IPintadorLaberint pintador){
        this.pintador = pintador;
    }
    
    public int obtenirMidaCostatTauler(){
        return this.costat;
    }
    
    
    public void pintarLaberint(){
        if(pintador == null) log.afegirError("No hi ha pintador!");
        pintador.pintarLaberint(this);
    }
}