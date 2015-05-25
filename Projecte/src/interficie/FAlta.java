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
import logica.Utils;
import logica.Utils.Constants;
import logica.Utils.FiltreExtensio;
import logica.log.Log;

/**
 * @author oscar
 * @brief
 * Formulari per donar d'alta a un nou usuari.
 * Cada usuari haura de especificar:
 *      -Nom d'usuari
 *      -Password
 *      -Imatge de perfil (opcional) 
 */
public class FAlta extends JFrame implements ActionListener{
    private final Log log;
    private BufferedImage imatgeRedimensionada; /**<Com l'usuari podra elegir una imatge de perfil
                                                * aquesta imatge caldra redimensionar-la
                                                */
    /**
     * @pre --
     * @post s'ha creat el formulari d'alta;
     */
    public FAlta() {
        log = Log.getInstance(FAlta.class);
        initComponents();
    }

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

    /**
     * @pre el frame no s'esta mostrant;
     * @post s'ha mostrat el frame;
     */
    public void mostrarFrame(){
        ImageIcon icon = new ImageIcon("res/imatge_perfil.png");
        btnImatgeUsuari.setIcon(icon);
        btnImatgeUsuari.setContentAreaFilled(false);
        btnImatgeUsuari.addActionListener(this);
        btnRegistrarse.addActionListener(this);
        btnSortir.addActionListener(this);
        this.setVisible(true);
    }
    
    private String seleccionarImatge(){
        String pathImatge = Constants.rutaImatgeDefecteUsuari;
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
//        fileChooser.setFileFilter(new FiltreExtensio(".png"));
        int resultatSeleccio = fileChooser.showOpenDialog(this);
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
            if(imatgeRedimensionada == null){
                this.redimensionarImatge("res/imatge_perfil.png");
            }
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
            ///S'ha pressionat el boto per registrar l'usuari;
            String usuari, password;
            usuari = txtUsuari.getText();
            password = txtPassword.getText();
            if(usuari.length() > 0 && password.length() > 0){
                ///S'ha entrat usuari i password
                if(!BD.existeixUsuari(usuari)){
                    ///Aquest usuari està lliure;
                    if(guardar(usuari, password)){
                        this.dispatchEvent(new WindowEvent(this,WindowEvent.WINDOW_CLOSING));
                    }
                }
                else{
                    ///L'usuari que s'ha introduit ja està registrat llencem missatge;
                    JOptionPane.showMessageDialog(this, 
                                                    "Usuari ja registrat",
                                                    "Atenció", 
                                                    JOptionPane.ERROR_MESSAGE);
                }
            }
            else{
                ///Missatge d'error indicant que s'ha d'entrar usuari i password;
                JOptionPane.showMessageDialog(this, 
                                                "Introdueix usuari i password",
                                                "Atenció", 
                                                JOptionPane.ERROR_MESSAGE);
            }
        }
        else if(e.getSource() == btnImatgeUsuari){
            String rutaImatgeSeleccionada = seleccionarImatge();
            redimensionarImatge(rutaImatgeSeleccionada);
        }
        else if(e.getSource() == btnSortir){
            this.dispose();
        }
    }
    
    private void redimensionarImatge(String rutaImatgeSeleccionada){
        try{
            File fitxerOrigen = new File(rutaImatgeSeleccionada);
            BufferedImage imatge = ImageIO.read(fitxerOrigen);
            int tipus = imatge.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : imatge.getType();
            imatgeRedimensionada = Utils.redimensionarImatge(imatge, tipus, Utils.Constants.MIDA_IMATGE, Utils.Constants.MIDA_IMATGE);
            btnImatgeUsuari.setIcon(new ImageIcon(imatgeRedimensionada));
        }
        catch(IOException ioe){
            log.afegirError("Error al redimensionar l'imatge, missatge: "+ioe.getMessage());
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
