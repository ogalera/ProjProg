/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interficie;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import logica.Usuari;

/**
 *
 * @author oscar
 */
public class FHistoricUsuari extends JFrame implements MouseListener{

    public FHistoricUsuari(String nomUsuari, Icon imatge, Usuari.PuntuacioNivell []puntuacions) {
        initComponents();
        carregarImatges(puntuacions.length);
        this.lblImatgePerfil.setIcon(imatge);
        this.lblUsuari.setText(nomUsuari);
    }

    private void carregarImatges(int nivell){
        ImageIcon imgEstrellaOff = obtenirImatge("res/star_off.png");
        ImageIcon imgEstrellaNivell;
        if(nivell > 0 && (imgEstrellaNivell = obtenirImatge("res/star_nivell1.png")) != null){
            lblStart1Gran.setIcon(imgEstrellaNivell);
            lblStart1Gran.setToolTipText("Nivell 1 superat");
            lblStart1Gran.addMouseListener(this);
        }
        else{
            lblStart1Gran.setIcon(imgEstrellaOff);
            lblStart1Gran.setToolTipText("Nivell 1 no superat");
        }
        if(nivell > 1 && (imgEstrellaNivell = obtenirImatge("res/star_nivell2.png")) != null){
            lblStart2Gran.setIcon(imgEstrellaNivell);
            lblStart2Gran.setToolTipText("Nivell 2 superat");
            lblStart2Gran.addMouseListener(this);
        }
        else{
            lblStart2Gran.setIcon(imgEstrellaOff);
            lblStart2Gran.setToolTipText("Nivell 2 no superat");
        }
        if(nivell > 2 && (imgEstrellaNivell = obtenirImatge("res/star_nivell3.png")) != null){
            lblStart3Gran.setIcon(imgEstrellaNivell);
            lblStart3Gran.setToolTipText("Nivell 3 superat");
            lblStart3Gran.addMouseListener(this);
        }
        else{
            lblStart3Gran.setIcon(imgEstrellaOff);
            lblStart3Gran.setToolTipText("Nivell 3 no superat");
        }
        if(nivell > 3 && (imgEstrellaNivell = obtenirImatge("res/star_nivell4.png")) != null){
            lblStart4Gran.setIcon(imgEstrellaNivell);
            lblStart4Gran.setToolTipText("Nivell 4 superat");
            lblStart4Gran.addMouseListener(this);
        }
        else{
            lblStart4Gran.setIcon(imgEstrellaOff);
            lblStart4Gran.setToolTipText("Nivell 4 no superat");
        }
        if(nivell > 4 && (imgEstrellaNivell = obtenirImatge("res/star_nivell5.png")) != null){
            lblStart5Gran.setIcon(imgEstrellaNivell);
            lblStart5Gran.setToolTipText("Nivell 5 superat");
            lblStart5Gran.addMouseListener(this);
        }
        else{
            lblStart5Gran.setIcon(imgEstrellaOff);
            lblStart5Gran.setToolTipText("Nivell 5 no superat");
        }
    }
    
    private ImageIcon obtenirImatge(String ruta){
        return new ImageIcon(ruta);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblImatgePerfil = new javax.swing.JLabel();
        lblStart1Gran = new javax.swing.JLabel();
        lblStart2Gran = new javax.swing.JLabel();
        lblStart3Gran = new javax.swing.JLabel();
        lblStart4Gran = new javax.swing.JLabel();
        lblStart5Gran = new javax.swing.JLabel();
        lblUsuari = new javax.swing.JLabel();
        lblPUNTS = new javax.swing.JLabel();
        lblTEMPS = new javax.swing.JLabel();
        lblDATA = new javax.swing.JLabel();
        lblPunts = new javax.swing.JLabel();
        lblTemps = new javax.swing.JLabel();
        lblData = new javax.swing.JLabel();
        lblNivell = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);
        setResizable(false);

        lblImatgePerfil.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        lblImatgePerfil.setPreferredSize(new java.awt.Dimension(100, 100));

        lblStart1Gran.setToolTipText("");
        lblStart1Gran.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblStart1Gran.setPreferredSize(new java.awt.Dimension(75, 75));

        lblStart2Gran.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblStart2Gran.setPreferredSize(new java.awt.Dimension(75, 75));

        lblStart3Gran.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblStart3Gran.setPreferredSize(new java.awt.Dimension(75, 75));

        lblStart4Gran.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblStart4Gran.setPreferredSize(new java.awt.Dimension(75, 75));

        lblStart5Gran.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblStart5Gran.setPreferredSize(new java.awt.Dimension(75, 75));

        lblUsuari.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lblUsuari.setText("Usuari");

        lblPUNTS.setText("Punts");

        lblTEMPS.setText("Temps");

        lblDATA.setText("Data");

        lblPunts.setText("---");

        lblTemps.setText("---");

        lblData.setText("---");

        lblNivell.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lblNivell.setText("Nivell");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 13, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(148, 148, 148)
                                .addComponent(lblImatgePerfil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblStart1Gran, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblStart2Gran, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblNivell)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblStart3Gran, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblStart4Gran, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblStart5Gran, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(175, 175, 175)
                        .addComponent(lblUsuari)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(152, 152, 152)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblPUNTS, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTEMPS, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblDATA, javax.swing.GroupLayout.Alignment.LEADING))
                .addGap(37, 37, 37)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblData)
                    .addComponent(lblPunts, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblTemps, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblUsuari)
                .addGap(18, 18, 18)
                .addComponent(lblImatgePerfil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblStart1Gran, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblStart2Gran, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblStart3Gran, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblStart4Gran, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblStart5Gran, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblNivell, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPUNTS)
                    .addComponent(lblPunts))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTEMPS)
                    .addComponent(lblTemps))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDATA)
                    .addComponent(lblData))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void mostrarFrame(){
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblDATA;
    private javax.swing.JLabel lblData;
    private javax.swing.JLabel lblImatgePerfil;
    private javax.swing.JLabel lblNivell;
    private javax.swing.JLabel lblPUNTS;
    private javax.swing.JLabel lblPunts;
    private javax.swing.JLabel lblStart1Gran;
    private javax.swing.JLabel lblStart2Gran;
    private javax.swing.JLabel lblStart3Gran;
    private javax.swing.JLabel lblStart4Gran;
    private javax.swing.JLabel lblStart5Gran;
    private javax.swing.JLabel lblTEMPS;
    private javax.swing.JLabel lblTemps;
    private javax.swing.JLabel lblUsuari;
    // End of variables declaration//GEN-END:variables

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1){
            if(e.getSource() == lblStart1Gran){
                lblNivell.setText("Nivell 1");
            }
            else if(e.getSource() == lblStart2Gran){
                lblNivell.setText("Nivell 2");

            }
            else if(e.getSource() == lblStart3Gran){
                lblNivell.setText("Nivell 3");

            }
            else if(e.getSource() == lblStart4Gran){
                lblNivell.setText("Nivell 4");

            }
            else if(e.getSource() == lblStart5Gran){
                lblNivell.setText("Nivell 5");

            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
