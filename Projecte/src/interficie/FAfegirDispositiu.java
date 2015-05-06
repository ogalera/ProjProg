/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interficie;

import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.swing.ImageIcon;
import logica.Utils.Constants;

/**
 *
 * @author oscar
 */
public class FAfegirDispositiu extends FFrameAmbLog {

    /**
     * Creates new form FAfegirDispositiu
     */
    public FAfegirDispositiu() {
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

        lblEscoltant = new javax.swing.JLabel();
        lblDispositiuConnectat = new javax.swing.JLabel();
        lblIp = new javax.swing.JLabel();
        lblPort = new javax.swing.JLabel();
        lblDispositiu = new javax.swing.JLabel();
        lblIP = new javax.swing.JLabel();
        lblPORT = new javax.swing.JLabel();
        lblDISPOSITIU = new javax.swing.JLabel();
        btnEscoltar = new javax.swing.JButton();
        btnAcceptar = new javax.swing.JButton();

        setAlwaysOnTop(true);
        setResizable(false);

        lblEscoltant.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        lblEscoltant.setPreferredSize(new java.awt.Dimension(100, 100));

        lblDispositiuConnectat.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        lblDispositiuConnectat.setPreferredSize(new java.awt.Dimension(100, 100));

        lblIp.setText("IP");

        lblPort.setText("PORT");

        lblDispositiu.setText("DISPOSITIU");

        lblIP.setText("IP");

        lblPORT.setText("PORT");

        lblDISPOSITIU.setText("NO");

        btnEscoltar.setText("ESCOLTAR");

        btnAcceptar.setActionCommand("ACCEPTAR");
        btnAcceptar.setLabel("ACCEPTAR");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblEscoltant, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblDispositiuConnectat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblPort)
                                    .addComponent(lblIp)
                                    .addComponent(lblDispositiu))
                                .addGap(71, 71, 71)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblIP)
                                    .addComponent(lblPORT)
                                    .addComponent(lblDISPOSITIU)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnEscoltar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnAcceptar)))
                        .addGap(0, 6, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblEscoltant, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblDispositiuConnectat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblIp)
                    .addComponent(lblIP))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPort)
                    .addComponent(lblPORT))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDispositiu)
                    .addComponent(lblDISPOSITIU))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEscoltar)
                    .addComponent(btnAcceptar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void mostrarFrame(){
        lblDispositiuConnectat.setIcon(new ImageIcon("res/led_off.png"));
        lblEscoltant.setIcon(new ImageIcon("res/led_off.png"));
        try{
//            InetAddress[] a = InetAddress.getAllByName(null);
//            lblIP.setText(InetAddress.getLocalHost().toString());
            InetAddress inet = InetAddress.getLocalHost();
            InetAddress[] ips = InetAddress.getAllByName(inet.getCanonicalHostName());
            if (ips  != null ) {
              String r = "";
              for (int i = 0; i < ips.length; i++) {
                r += ips[i]+"\n";
              }
              lblIP.setText(r);
            }
        }
        catch(UnknownHostException uhe){
            lblIP.setText("DESCONEGUDA");
        }
        lblPORT.setText(Constants.PORT+"");
        this.setVisible(true);
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAcceptar;
    private javax.swing.JButton btnEscoltar;
    private javax.swing.JLabel lblDISPOSITIU;
    private javax.swing.JLabel lblDispositiu;
    private javax.swing.JLabel lblDispositiuConnectat;
    private javax.swing.JLabel lblEscoltant;
    private javax.swing.JLabel lblIP;
    private javax.swing.JLabel lblIp;
    private javax.swing.JLabel lblPORT;
    private javax.swing.JLabel lblPort;
    // End of variables declaration//GEN-END:variables
}
