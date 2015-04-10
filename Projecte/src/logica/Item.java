/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

/**
 *
 * @author oscar
 */
public class Item extends ItemMovible {
    public Item(Laberint laberint, Punt inici, long millis) {
        super(laberint, inici, millis);
    }

    @Override
    public Punt calcularMoviment() {
        return null;
    }
}
