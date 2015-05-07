/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interficie;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import logica.Partida;
import logica.Usuari;
import logica.enumeracions.EElement;
import logica.enumeracions.ELaberintsPredefinits;

/**
 *
 * @author oscar
 */
public class FMenu extends FFrameAmbLog implements ActionListener{

    /**
     * Creates new form FMenu
     */
    public FMenu() {
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

        btnAventura = new javax.swing.JButton();
        btnProvarMapa = new javax.swing.JButton();
        btnCrearMapa = new javax.swing.JButton();
        btnAfegirDispositiu = new javax.swing.JButton();
        btnSortir = new javax.swing.JButton();
        lblImatgeUsuari = new javax.swing.JLabel();
        lblUsuari = new javax.swing.JLabel();
        lblNivell = new javax.swing.JLabel();
        lblUSUARI = new javax.swing.JLabel();
        lblNIVELL = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Menú");
        setAlwaysOnTop(true);
        setName("fMenu"); // NOI18N

        btnAventura.setText("Aventura");
        btnAventura.setActionCommand("btnAventura");

        btnProvarMapa.setText("Provar Mapa");

        btnCrearMapa.setText("Crear Mapa");

        btnAfegirDispositiu.setText("Afegir Dispositiu");

        btnSortir.setText("Sortir");
        btnSortir.setName("btnSortir"); // NOI18N

        lblImatgeUsuari.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        lblImatgeUsuari.setPreferredSize(new java.awt.Dimension(100, 100));

        lblUsuari.setText("Usuari");

        lblNivell.setText("Nivell");

        lblUSUARI.setText("USUARI");

        lblNIVELL.setText("NIVELL");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAventura, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                    .addComponent(btnProvarMapa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCrearMapa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAfegirDispositiu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSortir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblNivell)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblNIVELL))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(lblUsuari)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblUSUARI)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(lblImatgeUsuari, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(lblImatgeUsuari, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUsuari)
                    .addComponent(lblUSUARI))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNivell)
                    .addComponent(lblNIVELL))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnAventura)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnProvarMapa)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCrearMapa)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAfegirDispositiu)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSortir)
                .addContainerGap())
        );

        btnAventura.getAccessibleContext().setAccessibleName("btnAventura");
        btnProvarMapa.getAccessibleContext().setAccessibleName("btnProbarMapa");
        btnCrearMapa.getAccessibleContext().setAccessibleName("btnCrearMapa");
        btnAfegirDispositiu.getAccessibleContext().setAccessibleName("btnAfegirDispositiu");
        btnSortir.getAccessibleContext().setAccessibleName("btnSortir");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void mostrarFrame(){
        Usuari usuari = FLogin.obtenirUsuari();
        lblUSUARI.setText(usuari.obtenirNomUsuari());
        lblNIVELL.setText(usuari.obtenirNivell()+"");
        lblImatgeUsuari.setIcon(usuari.obtenirImatge());
        btnAventura.addActionListener(this);
        btnProvarMapa.addActionListener(this);
        btnCrearMapa.addActionListener(this);
        btnAfegirDispositiu.addActionListener(this);
        btnSortir.addActionListener(this);
        this.setVisible(true);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAfegirDispositiu;
    private javax.swing.JButton btnAventura;
    private javax.swing.JButton btnCrearMapa;
    private javax.swing.JButton btnProvarMapa;
    private javax.swing.JButton btnSortir;
    private javax.swing.JLabel lblImatgeUsuari;
    private javax.swing.JLabel lblNIVELL;
    private javax.swing.JLabel lblNivell;
    private javax.swing.JLabel lblUSUARI;
    private javax.swing.JLabel lblUsuari;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);
        if(e.getSource() == btnAventura){
            Partida partida = new Partida(ELaberintsPredefinits.LABERINT_ALEATORI, 5, EElement.FANTASMA2);
            PLaberint fLaberint = new PLaberint();
            FPartida fPartida = new FPartida(fLaberint);
            partida.assignarPintadors(fPartida, fLaberint);
            fPartida.pintarPartida(partida);
            partida.iniciarPartida();
        }
        else if(e.getSource() == btnProvarMapa){
            
        }
        else if(e.getSource() == btnCrearMapa){
            new FEditorLaberint(10).mostrarFrame();
        }
        else if(e.getSource() == btnAfegirDispositiu){
            new FAfegirDispositiu().mostrarFrame();
        }
        else{
            this.dispose();
        }
    }
}
