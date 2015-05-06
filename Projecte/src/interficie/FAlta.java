/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interficie;

import dades.BD;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import logica.Utils;
import logica.Utils.Constants;
import logica.log.Log;

/**
 *
 * @author oscar
 */
public class FAlta extends JFrame implements ActionListener{
    private final Log log;
    private BufferedImage imatgeRedimensionada;
//    private String rutaImatgeSeleccionada = Constants.rutaImatgeDefecteUsuari;
    /**
     * Creates new form FAlta2
     */
    public FAlta() {
        log = Log.getInstance(FAlta.class);
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

        jButton1 = new javax.swing.JButton();
        lblUsuari = new javax.swing.JLabel();
        lblPassword = new javax.swing.JLabel();
        txtUsuari = new javax.swing.JTextField();
        txtPassword = new javax.swing.JPasswordField();
        btnImatgeUsuari = new javax.swing.JButton();
        btnRegistrarse = new javax.swing.JButton();
        btnSortir = new javax.swing.JButton();

        jButton1.setText("jButton1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Formulari Registrar-se");
        setAlwaysOnTop(true);
        setResizable(false);

        lblUsuari.setText("Usuari:");

        lblPassword.setText("Password:");

        btnImatgeUsuari.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnImatgeUsuari.setDefaultCapable(false);

        btnRegistrarse.setText("Registrar-se");

        btnSortir.setText("Sortir");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(105, Short.MAX_VALUE)
                .addComponent(btnImatgeUsuari, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(94, 94, 94))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnRegistrarse, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblUsuari)
                            .addComponent(lblPassword))
                        .addGap(40, 40, 40)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtUsuari)
                            .addComponent(txtPassword)))
                    .addComponent(btnSortir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUsuari)
                    .addComponent(txtUsuari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPassword)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnImatgeUsuari, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnRegistrarse)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSortir)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnRegistrarse.getAccessibleContext().setAccessibleName("btnRegistrarse");
        btnSortir.getAccessibleContext().setAccessibleName("btnSortir");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void mostrarFrame(){
        ImageIcon icon = new ImageIcon("res/imatge_perfil.png");
        btnImatgeUsuari.setIcon(icon);
        btnImatgeUsuari.setContentAreaFilled(false);
        btnImatgeUsuari.addActionListener(this);
        btnRegistrarse.addActionListener(this);
        btnSortir.addActionListener(this);
        this.setVisible(true);
    }
    
    private class FiltrePNG extends FileFilter{
        @Override
        public boolean accept(File fitxer) {
            return fitxer.getAbsolutePath().endsWith(".png");
        }

        @Override
        public String getDescription() {
            return ".png";
        }
    }
    
    private String seleccionarImatge(){
        String pathImatge = Constants.rutaImatgeDefecteUsuari;
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FiltrePNG());
//        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int resultatSeleccio = fileChooser.showDialog(this, "OK");
        if(resultatSeleccio == JFileChooser.APPROVE_OPTION){
            pathImatge = fileChooser.getSelectedFile().getAbsolutePath();
        }
        return pathImatge;
    }
    
    private void guardarImatgeSeleccionada(String desti) throws IOException{
        File d = new File(desti);
        if(ImageIO.write(imatgeRedimensionada, "png", d)){
            log.afegirDebug("S'ha afegit una nova imatge amb el path "+desti);
        }
        else log.afegirError("Error al afegir la nova imatge amb desti "+desti);
    }
    
    private boolean guardar(String usuari, String password){
        boolean guardatOk = false;
        try{
            SimpleDateFormat format = new SimpleDateFormat("dd_MM_yyyy_kk_mm");
            String data = format.format(new Date());
            String rutaImatge = "res/imatges_usuaris/"+usuari+"_"+data+".png";
            guardarImatgeSeleccionada(rutaImatge);
            if(BD.afegirUsuari(usuari, password, rutaImatge)){
                guardatOk = true;
                log.afegirDebug("S'ha afegit un nou usuari "+usuari);
            }
        }
        catch(IOException ioe){
            log.afegirError("Al guardar una nova alta missatge: "+ioe.getMessage());
        }
        return guardatOk;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btnRegistrarse){
            String usuari, password;
            usuari = txtUsuari.getText();
            password = txtPassword.getText();
            if(usuari.length() > 0 && password.length() > 0){
                //S'ha entrat usuari i password
                if(!BD.usuariRegistrat(usuari)){
                    if(guardar(usuari, password)){
                        this.dispatchEvent(new WindowEvent(this,WindowEvent.WINDOW_CLOSING));
                    }
                }
                else{
                    JOptionPane.showMessageDialog(this, 
                                                    "Usuari ja registrat",
                                                    "Atenció", 
                                                    JOptionPane.ERROR_MESSAGE);
                }
            }
            else{
                JOptionPane.showMessageDialog(this, 
                                                "Introdueix usuari i password",
                                                "Atenció", 
                                                JOptionPane.ERROR_MESSAGE);
            }
        }
        else if(e.getSource() == btnImatgeUsuari){
            String rutaImatgeSeleccionada = seleccionarImatge();
            try{
                File fitxerOrigen = new File(rutaImatgeSeleccionada);
                BufferedImage imatge = ImageIO.read(fitxerOrigen);
                int tipus = imatge.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : imatge.getType();
                imatgeRedimensionada = Utils.resizeImage(imatge, tipus, Utils.Constants.MIDA_IMATGE, Utils.Constants.MIDA_IMATGE);
                btnImatgeUsuari.setIcon(new ImageIcon(imatgeRedimensionada));
            }
            catch(IOException ioe){
                log.afegirError("Error al redimensionar l'imatge, missatge: "+ioe.getMessage());
            }
        }
        else if(e.getSource() == btnSortir){
            this.dispose();
        }
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnImatgeUsuari;
    private javax.swing.JButton btnRegistrarse;
    private javax.swing.JButton btnSortir;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JLabel lblUsuari;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtUsuari;
    // End of variables declaration//GEN-END:variables
}