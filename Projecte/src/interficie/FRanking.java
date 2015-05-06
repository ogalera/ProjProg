/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interficie;

import dades.BD;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import logica.Usuari;

/**
 *
 * @author oscar
 */
public class FRanking extends FFrameAmbLog implements ListSelectionListener, ActionListener{
    
    private final Usuari[] usuaris;
    
    public FRanking() {
        usuaris = BD.obtenirRanking(10);
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jliRanking = new javax.swing.JList();
        btnSortir = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Ranking");
        setAlwaysOnTop(true);
        setResizable(false);

        jliRanking.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(jliRanking);

        btnSortir.setLabel("Sortir");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(btnSortir, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSortir)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnSortir.getAccessibleContext().setAccessibleName("btnSortir");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void mostrarFrame(){
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                jliRanking.setListData(usuaris);
                jliRanking.setCellRenderer(new RegistreUsuari());
                jliRanking.addListSelectionListener(FRanking.this);
                btnSortir.addActionListener(FRanking.this);
                setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSortir;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList jliRanking;
    // End of variables declaration//GEN-END:variables

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if(e.getValueIsAdjusting()){
            Usuari usuari = usuaris[e.getFirstIndex()];
            Usuari.PuntuacioNivell puntuacions[] = new Usuari.PuntuacioNivell[3];
            puntuacions[0] = new Usuari.PuntuacioNivell(1000, 100000);
            puntuacions[1] = new Usuari.PuntuacioNivell(1000, 100000);
            puntuacions[2] = new Usuari.PuntuacioNivell(1000, 100000);
            new FHistoricUsuari(usuari.obtenirNomUsuari(), usuari.obtenirImatge(), puntuacions).mostrarFrame();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btnSortir){
            this.dispose();
        }
    }
}

class RegistreUsuari extends JLabel implements ListCellRenderer<Usuari>{
    private static final Color colorSeleccio = new Color(0,0,128);
    public RegistreUsuari(){
        this.setOpaque(true);
        this.setIconTextGap(12);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Usuari> llistat, 
                                                    Usuari usuari, 
                                                    int index, 
                                                    boolean estaSeleccionat, 
                                                    boolean celaAmbFocus) {
        this.setText(usuari.obtenirNomUsuari());
        this.setIcon(usuari.obtenirImatge());
        if(estaSeleccionat){
            this.setBackground(colorSeleccio);
            this.setForeground(Color.white);
        }
        else{
            this.setBackground(Color.white);
            this.setForeground(Color.black);
        }
        return this;
    }

}