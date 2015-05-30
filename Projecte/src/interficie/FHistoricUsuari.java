package interficie;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 * @author oscar
 * 
 * @brief
 * pantalla que utilitzem per mostrar l'historic d'un usuari
 * que està format per els nivells que ha superat i de cada un d'aquests
 * el nombre de punts que ha fet.
 */
public class FHistoricUsuari extends JFrame implements MouseListener{
    
    private final int punts[];/**<vector de longitud nombre de nivells superats
                                 i on la posició del vector indica el nivell i 
                                 el contingut de la posició el nombre de punts 
                                 que s'han fet en aquest nivell*/
    
    /**
     * @pre puntuacions conté les puntuacions de cada nivell on puntuacions[1] 
     * conté el nombre de punts que s'han fet en aquest nivell.
     * @post em creat la pantalla de historic.
     */
    public FHistoricUsuari(String nomUsuari, Icon imatge, int []puntuacions) {
        initComponents();
        carregarImatges(puntuacions.length-1);
        this.lblImatgePerfil.setIcon(imatge);
        this.lblUsuari.setText(nomUsuari);
        this.punts = puntuacions;
        setLocationRelativeTo(null);
    }

    /**
     * @pre --
     * @post em carregat les imatges per els nivells segons si s'ha superat o no
     */
    private void carregarImatges(int nivell){
        ImageIcon imgEstrellaOff = obtenirImatge("res/imatges/star_off.png");
        ImageIcon imgEstrellaNivell;
        ///S'ha superat el nivell 1?
        if(nivell > 0 && (imgEstrellaNivell = obtenirImatge("res/imatges/star_nivell1.png")) != null){
            lblStart1Gran.setIcon(imgEstrellaNivell);
            lblStart1Gran.setToolTipText("Nivell 1 superat");
            lblStart1Gran.addMouseListener(this);
        }
        else{
            lblStart1Gran.setIcon(imgEstrellaOff);
            lblStart1Gran.setToolTipText("Nivell 1 no superat");
        }
        
        ///S'ha superat el nivell 2?
        if(nivell > 1 && (imgEstrellaNivell = obtenirImatge("res/imatges/star_nivell2.png")) != null){
            lblStart2Gran.setIcon(imgEstrellaNivell);
            lblStart2Gran.setToolTipText("Nivell 2 superat");
            lblStart2Gran.addMouseListener(this);
        }
        else{
            lblStart2Gran.setIcon(imgEstrellaOff);
            lblStart2Gran.setToolTipText("Nivell 2 no superat");
        }
        
        ///S'ha superat el nivell 3?
        if(nivell > 2 && (imgEstrellaNivell = obtenirImatge("res/imatges/star_nivell3.png")) != null){
            lblStart3Gran.setIcon(imgEstrellaNivell);
            lblStart3Gran.setToolTipText("Nivell 3 superat");
            lblStart3Gran.addMouseListener(this);
        }
        else{
            lblStart3Gran.setIcon(imgEstrellaOff);
            lblStart3Gran.setToolTipText("Nivell 3 no superat");
        }
        
        ///S'ha superat el nivell 4?
        if(nivell > 3 && (imgEstrellaNivell = obtenirImatge("res/imatges/star_nivell4.png")) != null){
            lblStart4Gran.setIcon(imgEstrellaNivell);
            lblStart4Gran.setToolTipText("Nivell 4 superat");
            lblStart4Gran.addMouseListener(this);
        }
        else{
            lblStart4Gran.setIcon(imgEstrellaOff);
            lblStart4Gran.setToolTipText("Nivell 4 no superat");
        }
        
        ///S'ha superat el nivell 5?
        if(nivell > 4 && (imgEstrellaNivell = obtenirImatge("res/imatges/star_nivell5.png")) != null){
            lblStart5Gran.setIcon(imgEstrellaNivell);
            lblStart5Gran.setToolTipText("Nivell 5 superat");
            lblStart5Gran.addMouseListener(this);
        }
        else{
            lblStart5Gran.setIcon(imgEstrellaOff);
            lblStart5Gran.setToolTipText("Nivell 5 no superat");
        }
    }
    
    /**
     * @pre ruta és valida.
     * @post s'ha retornat l'imatge de la ruta especificada.
     */
    private ImageIcon obtenirImatge(String ruta){
        return new ImageIcon(ClassLoader.getSystemResource(ruta));
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
        lblPunts = new javax.swing.JLabel();
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

        lblPunts.setText("---");

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
                .addComponent(lblPUNTS)
                .addGap(42, 42, 42)
                .addComponent(lblPunts)
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
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @pre el dialeg no s'ha mostrat.
     * @post em mostrat el dialeg.
     */
    public void mostrarFrame(){
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblImatgePerfil;
    private javax.swing.JLabel lblNivell;
    private javax.swing.JLabel lblPUNTS;
    private javax.swing.JLabel lblPunts;
    private javax.swing.JLabel lblStart1Gran;
    private javax.swing.JLabel lblStart2Gran;
    private javax.swing.JLabel lblStart3Gran;
    private javax.swing.JLabel lblStart4Gran;
    private javax.swing.JLabel lblStart5Gran;
    private javax.swing.JLabel lblUsuari;
    // End of variables declaration//GEN-END:variables

    @Override
    public void mouseClicked(MouseEvent e) {
        ///S'ha seleccionat algún nivell, per tant, mostrem el nombre 
        ///de punts d'aquest nivell en el marcador.
        if(e.getButton() == MouseEvent.BUTTON1){
            if(e.getSource() == lblStart1Gran){
                lblNivell.setText("Nivell 1");
                lblPunts.setText(punts[1]+"");
            }
            else if(e.getSource() == lblStart2Gran){
                lblNivell.setText("Nivell 2");
                lblPunts.setText(punts[2]+"");
            }
            else if(e.getSource() == lblStart3Gran){
                lblNivell.setText("Nivell 3");
                lblPunts.setText(punts[3]+"");
            }
            else if(e.getSource() == lblStart4Gran){
                lblNivell.setText("Nivell 4");
                lblPunts.setText(punts[4]+"");
            }
            else if(e.getSource() == lblStart5Gran){
                lblNivell.setText("Nivell 5");
                lblPunts.setText(punts[5]+"");
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
