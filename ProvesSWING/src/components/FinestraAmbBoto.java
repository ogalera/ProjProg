/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;

/**
 *
 * @author oscar 
 */
public class FinestraAmbBoto implements ActionListener{
    private JFrame finestra;
    public FinestraAmbBoto(){
        finestra = new JFrame();
        finestra.setSize(500,500);
        finestra.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        JButton btnTancar = new JButton("Tencar");
        btnTancar.addActionListener(this);
        finestra.add(btnTancar);
    }
    
    public void mostrar(){
        finestra.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.exit(0);
    }
}
