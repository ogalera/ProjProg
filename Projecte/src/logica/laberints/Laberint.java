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
import java.awt.Dimension;
import java.awt.event.KeyListener;
import javax.swing.ImageIcon;
import logica.Item;
import logica.Partida;
import logica.Punt;
import logica.Utils;

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
    
    protected int nMondesPerItem;
    
    public Laberint(String fitxer,
                    Partida partida, 
                    IPintadorLaberint pintadorLaberint) throws EFormatLaberint{
        this.pintador = pintadorLaberint;
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
        this.nMonedes = numeroMonedes();
        this.nMondesPerItem = (int) (nMonedes*0.3);
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
        this.nMondesPerItem = (int) (nMonedes*0.3);
    }
    
    protected Laberint(Partida partida){
        this.partida = partida;
    }
    
    protected final int numeroMonedes(){
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
        int fila = posicio.obtenirColumna();
        int columna = posicio.obtenirFila();
        return fila >= 0 && columna >= 0 && fila < costat && columna < costat;
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
           int columna = posicio.obtenirColumna();
           int fila = posicio.obtenirFila();
           element = tauler[fila][columna];
        }
        return element;
    }
    
    public synchronized EElement moureItem(Punt posicio, EDireccio direccio, EElement elementARestaurar){
        int filaOrigen = posicio.obtenirFila();
        int columnaOrigen = posicio.obtenirColumna();
        Punt puntDesplasat = posicio.generarPuntDesplasat(direccio);
        int filaDesti = puntDesplasat.obtenirFila();
        int columnaDesti = puntDesplasat.obtenirColumna();
        EElement elementTrapitjat = tauler[filaDesti][columnaDesti];
        tauler[filaDesti][columnaDesti] = tauler[filaOrigen][columnaOrigen];
        tauler[filaOrigen][columnaOrigen] = elementARestaurar;
        pintador.pintarMovimentItem(posicio, direccio, elementARestaurar.obtenirImatge());
        return elementTrapitjat;
    }
    
    public synchronized EElement mourePersonatge(Punt posicio, EDireccio direccio, ImageIcon imatge){
        int columna = posicio.obtenirColumna();
        int fila = posicio.obtenirFila();
        EElement objecteAMoure = this.tauler[fila][columna];
        this.tauler[fila][columna] = EElement.RES;
        Punt p = posicio.generarPuntDesplasat(direccio);
        columna = p.obtenirColumna();
        fila = p.obtenirFila();
        EElement objecteAgafat = this.tauler[fila][columna];
        if(objecteAgafat == EElement.MONEDA || objecteAgafat == EElement.MONEDA_EXTRA){
            this.nMonedes--;
            if(nMonedes%nMondesPerItem == 0 && !partida.hiHaItemEspecial()){
                //Toca sortejar un nou item
                Punt puntItem = sortejarPosicioItem();
                EElement item = sortejarItem();
                Item nouItem = new Item(partida, item, objecteAgafat, this, puntItem);
                int filaItem = puntItem.obtenirFila();
                int columnaItem = puntItem.obtenirColumna();
                this.tauler[filaItem][columnaItem] = item;
                pintador.pintarNouItem(puntItem, item);
                partida.assignarItemEspecial(nouItem);
                System.out.println("S'ha de assignar un nou item a "+puntItem+" item "+nouItem);
            }
            if(nMonedes == 0){
               Punt sortida = sortejarSortida();
               int xSortida = sortida.obtenirColumna();
               int ySortida = sortida.obtenirFila();
               this.tauler[ySortida][xSortida] = EElement.SORTIDA;
               partida.assignarGuanyador();
            }
        }
        if(objecteAgafat == EElement.SORTIDA){
            partida.finalitzarPartida();
        }
        this.tauler[fila][columna] = objecteAMoure;
        pintador.pintarMovimentPersonatge(posicio, direccio, imatge);
        return objecteAgafat;
    }
    
    private Punt sortejarSortida(){
        return new Punt(costat-1, costat-1);
    }
    
    private EElement sortejarItem(){
        int index = Utils.obtenirValorAleatori(3);
        switch(index){
            case 1:{
                return EElement.PATINS;
            }
            case 2:{
                return EElement.MONEDES_X2;
            }
            default:{
                return EElement.MONGETA;
            }
        }
    }
    
    private Punt sortejarPosicioItem(){
        int fila;
        int columna;
        EElement element;
        do{
            fila = Utils.obtenirValorAleatori(costat);
            columna = Utils.obtenirValorAleatori(costat);
            element = tauler[fila][columna];
        }while(element != EElement.RES && element != EElement.MONEDA && element != EElement.MONEDA_EXTRA);
        return new Punt(fila, columna);
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
    
    public Punt obtenirPosicioInicialPacman(){
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
    
    public Punt obtenirPosicioInicialEnemic(){
        Punt posicio = null;
        boolean trobat = false;
        int fila = 0;
        while(!trobat && fila < costat){
            int columna = 0;
            while(!trobat && columna < costat){
                if(tauler[fila][columna].esEnemic()){
                    trobat = true;
                    posicio = new Punt(fila, columna);
                }
                columna++;
            }
            fila++;
        }
        return posicio;
    }
    
//    public FLaberint obtenirPintadorLaberint(){
//        return pintador;
//    }
//    
    public void assignarControladorTeclat(KeyListener listener){
        pintador.assignarControladorTeclat(listener);
    }
    
    public int obtenirMidaCostatTauler(){
        return this.costat;
    }
    
    public void pintarLaberint(){
        if(pintador == null) log.afegirError("No hi ha pintador!");
        pintador.pintarLaberint(this);
    }
    
    public int obtenirMidaImatge(){
        return this.pintador.obtenirMidaImatge();
    }
}