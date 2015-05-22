package logica;

import java.awt.Image;
import javax.swing.ImageIcon;
import logica.laberints.Laberint;
import logica.enumeracions.EElement;
import logica.enumeracions.EDireccio;

/**
 * @author oscar
 */
public class Fantasma2 extends Personatge{
    
    public Fantasma2(Partida partida, Laberint laberint, Punt inici) {
        super(partida, laberint, EElement.FANTASMA2.obtenirImatge(), inici);
    }

    @Override
    public EDireccio calcularMoviment() {
        int interesDreta = explorarDireccio(EDireccio.DRETA);
        int interesEsquerra = explorarDireccio(EDireccio.ESQUERRA);
        int interesAdalt = explorarDireccio(EDireccio.AMUNT);
        int interesAbaix = explorarDireccio(EDireccio.AVALL);
        
        EDireccio moviment;
        
        if(interesDreta > interesEsquerra && interesDreta > interesAdalt && interesDreta > interesAbaix){
            //Interessa mes anar a la dreta;
            moviment = EDireccio.DRETA;
            historicMoviments.afegirMoviment(moviment);
        }
        else if (interesEsquerra > interesDreta && interesEsquerra > interesAdalt && interesEsquerra > interesAbaix){
            //Interessa mes anar a l'esquerra;
            moviment = EDireccio.ESQUERRA;
            historicMoviments.afegirMoviment(moviment);
        }
        else if (interesAdalt > interesDreta && interesAdalt > interesEsquerra && interesAdalt > interesAbaix){
            //Interessa mes anar adalt;
            moviment = EDireccio.AMUNT;
            historicMoviments.afegirMoviment(moviment);
        }
        else if (interesAbaix > interesDreta && interesAbaix > interesEsquerra && interesAbaix > interesAdalt){
            //Interessa mes anar avall;
            moviment = EDireccio.AVALL;
            historicMoviments.afegirMoviment(moviment);
        }
        else{
            int maxim = obtenirMaxim(interesDreta, interesEsquerra, interesAdalt, interesAbaix);
            if(maxim == 0){
                //Estem en un punt que no ens interessa anar en cap direcció, llavors
                //tenim dos opcions:
                //Opció 1 -> anar enrrere segons l'historic "Sempre que hi hagi algo en l'historic"
                //Opció 2 -> sortejar una direcció on (0 -> DRETA, 1 -> ESQUERRA, 2 -> AMUN, 3 -> AVALL)
                if(!historicMoviments.esBuida()){
                    //Tenim alguna direcció en l'historic per tant cal tirar enrrere "si es pot";
                    moviment = historicMoviments.obtenirUltimMoviment().obtenirMovimentInvers();
                    Punt puntDesplasat = posicio.generarPuntDesplasat(moviment);
                    if(laberint.obtenirElement(puntDesplasat)!= EElement.PARET){
                        historicMoviments.eliminarMoviment();
                    }
                    else{
                        moviment = EDireccio.QUIET;
                    }
                }
                else{
                    //No tenim res en l'historic llavors optem per l'opció 2;
                    //i sortegem una direcció pseudoaleatoria;
                    int index = Utils.obtenirValorAleatori(4);
                    moviment = EDireccio.values()[index];
                    Punt p = posicio.generarPuntDesplasat(moviment);
                    if(laberint.obtenirElement(p) == EElement.PARET){
                        moviment = EDireccio.QUIET;
                    }
                }
            }
            else{
                //Estem en una situacio en que hi ha un empat en l'interes per anar a una possicio
                //per tant mirem quin es la primera direccio del empat i si s'hi pot anar;
                if(maxim == interesDreta){
                    moviment = EDireccio.DRETA;
                }
                else if(maxim == interesEsquerra){
                    moviment = EDireccio.ESQUERRA;
                }
                else if(maxim == interesAdalt){
                    moviment = EDireccio.AMUNT;
                }
                else moviment = EDireccio.AVALL;
                Punt p = posicio.generarPuntDesplasat(moviment);
                if(laberint.obtenirElement(p) == EElement.PARET){
                    moviment = EDireccio.QUIET;
                }
                else{
                    historicMoviments.afegirMoviment(moviment);
                }
            }
        }
//        System.out.println("dreta "+interesDreta);
//        System.out.println("esquerra "+interesEsquerra);
//        System.out.println("amunt "+interesAdalt);
//        System.out.println("abaix "+interesAbaix);
//        System.out.println("calculat "+moviment);
        return moviment;
    }
    
    @Override
    public EElement realitzarMoviment(){
        EElement elementObtingut = super.realitzarMoviment();
        if(elementObtingut != null && elementObtingut != EElement.FANTASMA2){
            System.out.println("element "+elementObtingut);
            posicio = posicio.generarPuntDesplasat(seguentMoviment);
            switch(elementObtingut){
                case MONEDA:{
                    incrementarPunts(Utils.Constants.VALOR_MONEDA_NORMAL);
                    partida.assignarPuntsEnemic(punts);
                }break;
                case MONEDA_EXTRA:{
                    incrementarPunts(Utils.Constants.VALOR_MONEDA_EXTRA);
                    partida.assignarPuntsEnemic(punts);
                }break;
                case PACMAN:{

                }break;
                case PATINS:
                case MONEDES_X2:
                case MONGETA:{
                    //Em agafat algún item
                    partida.itemCapturat();
                    assignarEstatPersonatge(elementObtingut);
                    partida.assignarItemAEnemic(elementObtingut);
                }break;
            }
        }
        return null;
    }
    
    private int explorarDireccio(EDireccio direccio){
        Punt p = posicio.generarPuntDesplasat(direccio);
        EElement element = laberint.obtenirElement(p);
        if(element == EElement.PARET) return 0;
        float interes = 0;
        int n = 1;
        p = posicio;
        do{
            p = p.generarPuntDesplasat(direccio);
            element = laberint.obtenirElement(p);
            if(element == EElement.MONEDA){
                interes += 10/n;
            }
            else if(element == EElement.PACMAN && obtenirEstatPersonatge() == EEstatPersonatge.AMB_MONGETA){
                interes += Integer.MAX_VALUE;
            }
            else if(element == EElement.PATINS || element == EElement.MONGETA || element == EElement.MONEDES_X2){
                interes += Integer.MAX_VALUE;
            }
            else if(element == EElement.SORTIDA && super.estaGuanyant()){
                //A tot tall cap a la sortida!!!
                System.out.println("Cap a la sortida!!!");
                interes = Integer.MAX_VALUE;
            }
            n++;
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

    @Override
    protected void assignarImatges() {
        int midaImatge = laberint.obtenirMidaImatge();
        this.imatges[0][0] = new ImageIcon(new ImageIcon("res/enemic2D0.png").getImage().getScaledInstance(midaImatge, midaImatge, Image.SCALE_DEFAULT));//EElement.PACMAN.obtenirImatge();
        this.imatges[0][1] = new ImageIcon(new ImageIcon("res/enemic2D1.png").getImage().getScaledInstance(midaImatge, midaImatge, Image.SCALE_DEFAULT));
        this.imatges[1][0] = new ImageIcon(new ImageIcon("res/enemic2E0.png").getImage().getScaledInstance(midaImatge, midaImatge, Image.SCALE_DEFAULT));
        this.imatges[1][1] = new ImageIcon(new ImageIcon("res/enemic2E1.png").getImage().getScaledInstance(midaImatge, midaImatge, Image.SCALE_DEFAULT));
        this.imatges[2][0] = new ImageIcon(new ImageIcon("res/enemic2A0.png").getImage().getScaledInstance(midaImatge, midaImatge, Image.SCALE_DEFAULT));
        this.imatges[2][1] = new ImageIcon(new ImageIcon("res/enemic2A1.png").getImage().getScaledInstance(midaImatge, midaImatge, Image.SCALE_DEFAULT));
        this.imatges[3][0] = new ImageIcon(new ImageIcon("res/enemic2B0.png").getImage().getScaledInstance(midaImatge, midaImatge, Image.SCALE_DEFAULT));
        this.imatges[3][1] = new ImageIcon(new ImageIcon("res/enemic2B1.png").getImage().getScaledInstance(midaImatge, midaImatge, Image.SCALE_DEFAULT));
    }
    
    @Override
    protected void notificarPerduaEstat() {
        partida.assignarItemAEnemic(EElement.RES);
    }
}
