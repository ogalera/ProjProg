/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica.algoritmica.AEstrella;
import logica.algoritmica.Casella;
import logica.algoritmica.LlistaOrdenadaCandidats;
import logica.laberints.Laberint;
import logica.Punt;
import logica.historic_moviments.HistoricMoviments;
import logica.enumeracions.EElement;
import logica.enumeracions.EDireccio;
import logica.excepcions.ExceptionBuscadorCamins;


/**
 *
 * @author Moises
 */
public class BuscadorCamiMinim {
    private final LlistaOrdenadaCandidats llistaOberta;//<! Conte les caselles que encara hem de processar
    private final Laberint laberint;
    private final Casella [][] tauler;
   
    
    
    public BuscadorCamiMinim(Laberint lab){
        laberint = lab;
        llistaOberta = new LlistaOrdenadaCandidats();
        //profunditatMaxima = Integer.MAX_VALUE;

        int mida = lab.obtenirMidaCostatTauler();
        tauler = new Casella  [mida][mida];
        
        for (int i = 0; i < mida; i++){
            for (int j = 0; j < mida; j++){
                tauler[i][j]= new Casella(new Punt(i,j));   
            }
        }
    }    
    
    public HistoricMoviments BuscaCamiMesCurt(Punt origen, Punt desti){
        EElement d = laberint.obtenirElement(desti);
        EElement o = laberint.obtenirElement(origen);
        if (d == EElement.PARET || o == EElement.PARET)throw new ExceptionBuscadorCamins("Els punts de origen o desti son del tipus PARET");

        
        llistaOberta.clear();
        llistaOberta.afegir(tauler[origen.obtenirFila()][origen.obtenirColumna()]);
        
        //long tempsInici = System.currentTimeMillis();
        Casella actual = llistaOberta.obtenirPrimer();
        while(!llistaOberta.esBuida() && !actual.obtenirPunt().equals(desti)){
            llistaOberta.elimina(actual);
            actual.processat();
            
            Punt [] adjacents = actual.obtenirPunt().obtenirPosicionsDelVoltant();
            
            for (Punt adjacent : adjacents){
                if (laberint.posicioValida(adjacent)){
                    EElement aux = laberint.obtenirElement(adjacent);
                    if (aux != EElement.PARET){
                        Casella vei = tauler[adjacent.obtenirFila()][adjacent.obtenirColumna()];
                        if (!vei.haEstatProcessat()){
                            if (!llistaOberta.conteElement(vei)){//No esta a la llista oberta
                                vei.setParent(actual);
                                vei.afegirDistanciaAlObjectiu(adjacent.distancia(desti)); 
                                llistaOberta.afegir(vei);
                            }
                            else{//Ja estava a la llista oberta
                                if (vei.getProfunditat() > actual.getProfunditat() +1 ){
                                    vei.setParent(actual);
                                }
                                
                            }
                        }
                    }
                   
                }
            }
            actual = llistaOberta.obtenirPrimer();
        }
       
        //long tempsFi = System.currentTimeMillis();
        //System.out.println("Temps a trobar el cami mes curt: "+(tempsFi-tempsInici)+"ms");
        
        HistoricMoviments ruta = generaRuta(origen, desti);
        reset();
        
//                 // Per Mostrar Per Pantalla... fa canvis al laberint er poder-lo mostrar per pantalla despres
//        
//        Casella fi = tauler[desti.obtenirFila()][desti.obtenirColumna()];
//        
//        while (fi != null){
//            laberint.assignaElement(fi.obtenirPunt(), EElement.INDEFINIT); // Per comprovar Algoritmica
//            fi = fi.getParent();
//        }
//        
        return ruta;
    }
    
    private HistoricMoviments generaRuta(Punt inici, Punt desti){
        HistoricMoviments ruta = new HistoricMoviments();
        Casella fi = tauler[desti.obtenirFila()][desti.obtenirColumna()];
        Casella seguent = fi.getParent();
        //System.out.print("EL CAMI TROBAT ES: (en ordre invers) \n");
        while(seguent != null){
            //System.out.print(fi.obtenirPunt() + "\n");
            //System.out.print(seguent.obtenirPunt() + "\n");
            EDireccio aux = EDireccio.obtenirDireccio(seguent.obtenirPunt(), fi.obtenirPunt());
            ruta.afegirMoviment(aux);
            fi = seguent;
            seguent = seguent.getParent();
        }
        return ruta;
    }
    
    private void reset(){
        int mida = laberint.obtenirMidaCostatTauler();
        for (int i = 0; i < mida; i++){
            for (int j = 0; j < mida; j++){
                 tauler[i][j].reset();
            }
        }
    }
    
    
}
