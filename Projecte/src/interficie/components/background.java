/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interficie.components;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.*;

/**
 *
 * @author Moi
 */
public class background extends JComponent {
    Image _imtg;
    int _altura;
    int _amplada;
    
    public background(Image i, int altura, int amplada){
        _imtg = i;
        _altura = altura;
        _amplada = amplada;
    }
    
    @Override
    public void paintComponent(Graphics g){
        g.drawImage(_imtg, 0, 0, _amplada, _altura, null);
    }
    
}
