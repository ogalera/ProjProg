/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica.historic_moviments;

import logica.enumeracions.EDireccio;

/**
 * @author oscar
 * DECLARACIÓ D'INTENCIONS DE LA CLASSE
 * Estructura de dades de tipus FILO on
 * afegir té cost O(1)
 * cim té cost O(1)
 * desenpilar té cost O(1)
 * esBuida té cost O(1)
 */
public class Pila<T> {
    private Node cim=null;
    
    private class Node{
        final private T dada;
        final private Node seguent;
        public Node(T dada, Node seguent){
            this.dada = dada;
            this.seguent = seguent;
        }
    };
    
    /**
     * @pre: --;
     * @post: dada està en el cim de la pila, COST O(1);
     * @param dada s'ha afegit al sim de la pila;
     */
    public void afegir(T dada){
        cim = new Node(dada, cim);
    }
    
    /**
     * @pre: --;
     * @post: em retornat el cim de la pila, COST O(1);
     * @return el cim de la pila;
     */
    public T cim(){
        return cim.dada;
    }
    
    /**
     * @pre: la pila no és buida;
     * @post: em desenpilat el cim de la pila, COST O(1);
     * @return el cim de la pila;
     */
    public T desenpilar(){
        T dada = cim.dada;
        cim = cim.seguent;
        return dada;
    }
    
    /**
     * @pre: --;
     * @post: diu si la pila està buida, COST O(1);
     * @return 
     */
    public boolean esBuida(){
        return this.cim == null;
    }
//                          Metodes que he utilitzat per poder veure les solucions dels algoritmes
    public void mostrar(){
        System.out.print("LA PILA CONTE EL SEGUENT: \n");
        Node aux = cim;
        while (aux != null){
            System.out.print((EDireccio)cim.dada + "\n");
            aux = aux.seguent;
        }
    }
    
    private String donamNom(EDireccio dir){
        String s = "QUIET";
        switch(dir){
            case AVALL: s= "AVALL";
                break;
            case AMUNT: s="AMUNT";
                break;
            case DRETA: s="DRETA";
                break;
            case ESQUERRA: s="ESQUERRA";
                break;
            default: s="QUIET";
                break;       
        }
        return s;
    }
}
