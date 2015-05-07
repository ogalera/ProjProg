/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import logica.laberints.Laberint;
import logica.enumeracions.EElement;
import logica.enumeracions.EDireccio;

/**
 * @author oscar
 */
public class Fantasma2 extends Personatge{
    private boolean marxaEnrrere;
    
    public Fantasma2(Partida partida, Laberint laberint, Punt inici, long millis) {
        super(partida, laberint, EElement.FANTASMA2.obtenirImatge(), inici, millis);
    }

    @Override
    public EDireccio calcularMoviment() {
        marxaEnrrere = false;
        int interesDreta = explorarDireccio(EDireccio.DRETA);
        int interesEsquerra = explorarDireccio(EDireccio.ESQUERRA);
        int interesAdalt = explorarDireccio(EDireccio.AMUNT);
        int interesAbaix = explorarDireccio(EDireccio.AVALL);
        
        EDireccio moviment;
        
        if(interesDreta > interesEsquerra && interesDreta > interesAdalt && interesDreta > interesAbaix){
            //Interessa mes anar a la dreta;
            moviment = EDireccio.DRETA;
        }
        else if (interesEsquerra > interesDreta && interesEsquerra > interesAdalt && interesEsquerra > interesAbaix){
            //Interessa mes anar a l'esquerra;
            moviment = EDireccio.ESQUERRA;
        }
        else if (interesAdalt > interesDreta && interesAdalt > interesEsquerra && interesAdalt > interesAbaix){
            //Interessa mes anar adalt;
            moviment = EDireccio.AMUNT;
        }
        else if (interesAbaix > interesDreta && interesAbaix > interesEsquerra && interesAbaix > interesAdalt){
            //Interessa mes anar adalt;
            moviment = EDireccio.AVALL;
        }
        else{
            int maxim = obtenirMaxim(interesDreta, interesEsquerra, interesAdalt, interesAbaix);
            if(maxim == 0){
                //Estem en un punt que no ens interessa anar en cap direcció, llavors
                //tenim dos opcions:
                //Opció 1 -> anar enrrere segons l'historic "Sempre que hi hagi algo en l'historic"
                //Opció 2 -> sortejar una direcció on (0 -> DRETA, 1 -> ESQUERRA, 2 -> AMUN, 3 -> AVALL)
                if(!historicMoviments.esBuida()){
                    //Tenim alguna direcció en l'historic per tant tirem enrrere;
                    marxaEnrrere = true;
                    moviment = historicMoviments.eliminarMoviment();
                    moviment = moviment.obtenirMovimentInvers();
                }
                else{
                    //No tenim res en l'historic llavors optem per l'opció 2;
                    //i sortegem una direcció pseudoaleatoria;
                    Punt p;
                    do{
                        int index = super.obtenirValorAleatori(4);
                        moviment = EDireccio.values()[index];
                        p = posicio.generarPuntDesplasat(moviment);
                    }while(!laberint.posicioValida(p));
                }
            }
            else{
                EDireccio possiblesDireccions[] = new EDireccio[4];
                int nPossiblesDireccions = 0;
                if(interesDreta == maxim) possiblesDireccions[nPossiblesDireccions++] = EDireccio.DRETA;
                if(interesEsquerra == maxim) possiblesDireccions[nPossiblesDireccions++] = EDireccio.ESQUERRA;
                if(interesAdalt == maxim) possiblesDireccions[nPossiblesDireccions++] = EDireccio.AMUNT;
                if(interesAbaix == maxim) possiblesDireccions[nPossiblesDireccions++] = EDireccio.AVALL;
                int index = super.obtenirValorAleatori(nPossiblesDireccions);
                moviment = possiblesDireccions[index];
            }
        }
        System.out.println("dreta "+interesDreta);
        System.out.println("esquerra "+interesEsquerra);
        System.out.println("amunt "+interesAdalt);
        System.out.println("abaix "+interesAbaix);
        System.out.println("calculat "+moviment);
        return moviment;
    }
    
    @Override
    public EElement realitzarMoviment(){
        EElement elementObtingut = super.realitzarMoviment();
        if(!marxaEnrrere){
            this.historicMoviments.afegirMoviment(super.seguentMoviment);
        }
        switch(elementObtingut){
            case MONEDA:{
                this.punts+= Utils.Constants.VALOR_MONEDA_NORMAL;
            }break;
            case MONEDA_EXTRA:{
                this.punts+= Utils.Constants.VALOR_MONEDA_EXTRA;
            }break;
        }
        partida.assignarPuntsEnemic(punts);
        return elementObtingut;
    }
    
    private int explorarDireccio(EDireccio direccio){
        Punt p = posicio;
        EElement element;
        float interes = 0;
        int n = 0;
        do{
            p = p.generarPuntDesplasat(direccio);
            element = laberint.obtenirElement(p);
            if(element == EElement.MONEDA){
                n++;
                interes += 10/n;
            }
            else if(element == EElement.SORTIDA && super.estaGuanyant()){
                //A tot tall cap a la sortida!!!
                System.out.println("Cap a la sortida!!!");
                interes = Integer.MAX_VALUE;
            }
        }while(element != EElement.PARET);
        return (int)interes;
    }

    
    private int obtenirMaxim(int ... valors){
        int maxim = valors[0];
        for(int i = 0; i < valors.length; i++){
            if(maxim < valors[i]){
                maxim = valors[i];
            }
        }
        return maxim;
    }
    
    @Override public String nomItemMovible(){
        return "Fantasma2";
    }
}
