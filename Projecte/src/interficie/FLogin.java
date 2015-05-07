/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interficie;
import dades.BD;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import java.io.IOException;
import interficie.components.Boto;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import java.awt.BorderLayout;
import java.awt.Dimension;


import logica.Usuari;
import logica.log.Log;


/**
 *
 * @author Moi
 */
public class FLogin extends FFrameAmbLog implements ActionListener {
    private static final int ALTURA_BOTO=6; // El tant per cent respecte el tamany de pantalla que volem que sigui la altura dels botons
    private static final int AMPLADA_BOTO=12; //El tant per cent respecte el tamany de pantalla que volem que sigui la amplada dels botons
    private static final Dimension DIMENSIO_MIN_BUTTONS = new Dimension(100, 30);
    private static final Dimension  DIMENSIO_MAX_BUTTONS = new Dimension(250,80);
    private final Log log;
    private static Usuari usuari;
    
    Boto btnAlta;
    Boto btnEntrar;
    Boto btnSortir;
    Boto btnRanking;
    
    JTextField campUsuari;
    JPasswordField campPass;
    
    JPanel panellInferior;
    
    public FLogin() throws IOException{
       super();
       log = Log.getInstance(FLogin.class);
       creaFinestra();
       //creaButons();
//       this.repaint();
    }
    
    public void mostrarFrame(){
        this.setVisible(true);
    }
    
    private void creaButons(){
        
        int alturaBoto= (ALTURA_BOTO * this.getHeight()) / 100;
        int ampladaBoto = (AMPLADA_BOTO * this.getWidth()) / 100;
        
        //btnEntrar = new Boto(new ImageIcon("res/btnEntrar.png"));
        btnEntrar = new Boto("ENTRAR");
        btnEntrar.setBounds((this.getWidth() /2) - (ampladaBoto /2), ((this.getHeight() * 65 ) / 100) , ampladaBoto, alturaBoto);
        btnEntrar.setOpaque(false);
        btnEntrar.setContentAreaFilled(false);
        btnEntrar.setBorderPainted(false);
        
        //btnAlta = new Boto(new ImageIcon("res/btnAlta.png"));
        btnAlta = new Boto("ALTA");
        btnAlta.setBounds((this.getWidth() /2) - (ampladaBoto /2), ((this.getHeight() * 65 ) / 100)+ alturaBoto  , ampladaBoto, alturaBoto);
        btnAlta.setOpaque(false);
        btnAlta.setContentAreaFilled(false);
        btnAlta.setBorderPainted(false);
        
        //btnRanking = new Boto(new ImageIcon("res/btnRanking.png"));
        btnRanking = new Boto("RANKING");
        btnRanking.setBounds((this.getWidth() /2) - (ampladaBoto /2), ((this.getHeight() * 65 ) / 100)+ (alturaBoto * 2), ampladaBoto, alturaBoto);
        btnRanking.setOpaque(false);
        btnRanking.setContentAreaFilled(false);
        btnRanking.setBorderPainted(false);
        
        btnSortir = new Boto("SORTIR");
        btnSortir.setBounds((this.getWidth() /2) - (ampladaBoto /2),((this.getHeight() * 65 ) / 100) + (alturaBoto * 3), ampladaBoto, alturaBoto);
        btnSortir.setOpaque(false);
        btnSortir.setContentAreaFilled(false);
        btnSortir.setBorderPainted(false);
        
        add(btnRanking);
        add(btnEntrar);
        add(btnAlta);
        add(btnSortir); 
        
        btnRanking.addActionListener(this);
        btnEntrar.addActionListener(this);
        btnAlta.addActionListener(this);
        btnSortir.addActionListener(this);
        
          
    }
//    private void creaFinestra() throws IOException{
//        setLayout(null);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setExtendedState(JFrame.MAXIMIZED_BOTH);
//        
//        BufferedImage bf = ImageIO.read(new File("res/Inici.png"));
//        setVisible(true);
//        setContentPane(new background(bf, this.getHeight(),this.getWidth() ));
//        setResizable(false);
//    }
    
    private void crearBotons(){
        int alturaBoto= (ALTURA_BOTO * this.getHeight()) / 100;
        int ampladaBoto = (AMPLADA_BOTO * this.getWidth()) / 100;
        
        //btnEntrar = new Boto(new ImageIcon("res/btnEntrar.png"));
        btnEntrar = new Boto("ENTRAR");
        btnEntrar.setPreferredSize(new Dimension(ampladaBoto,alturaBoto));
        btnEntrar.setMinimumSize(DIMENSIO_MIN_BUTTONS);
        btnEntrar.setMaximumSize(DIMENSIO_MAX_BUTTONS);
        
        //btnAlta = new Boto(new ImageIcon("res/btnAlta.png"));
        btnAlta = new Boto("ALTA");
        btnAlta.setPreferredSize(new Dimension(ampladaBoto,alturaBoto));
        btnAlta.setMinimumSize(DIMENSIO_MIN_BUTTONS);
        btnAlta.setMaximumSize(DIMENSIO_MAX_BUTTONS);
        
        //btnRanking = new Boto(new ImageIcon("res/btnRanking.png"));
        btnRanking = new Boto("RANKING");
        btnRanking.setPreferredSize(new Dimension(ampladaBoto,alturaBoto));
        btnRanking.setMinimumSize(DIMENSIO_MIN_BUTTONS);
        btnRanking.setMaximumSize(DIMENSIO_MAX_BUTTONS);
        
        btnSortir = new Boto("SORTIR");
        btnSortir.setPreferredSize(new Dimension(ampladaBoto,alturaBoto));
        btnSortir.setMinimumSize(DIMENSIO_MIN_BUTTONS);
        btnSortir.setMaximumSize(DIMENSIO_MAX_BUTTONS);
        
        btnRanking.addActionListener(this);
        btnEntrar.addActionListener(this);
        btnAlta.addActionListener(this);
        btnSortir.addActionListener(this);
    }
    
    private void creaFinestra() throws IOException{
        //setLayout(new FlowLayout(FlowLayout.CENTER));
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        
       //BufferedImage bf = ImageIO.read(new File("res/Inici.png"));
       //setContentPane(new background(bf, this.getHeight(),this.getWidth() ));
       //setResizable(false);
   
        creaPanellLogin();
        add(panellInferior, BorderLayout.SOUTH);
    }
    
    private void creaPanellLogin(){
        
        
        panellInferior = new JPanel();
        panellInferior.setLayout(new FlowLayout());

        JPanel panellLogin = new JPanel();
        panellLogin.setLayout(new BoxLayout(panellLogin, BoxLayout.Y_AXIS));
        //creaTextFields();
        crearBotons();
        //panellLogin.add(campUsuari);
        //panellLogin.add(campPass);
        panellLogin.add(btnEntrar);
        panellLogin.add(btnAlta);
        panellLogin.add(btnRanking);
        panellLogin.add(btnSortir);
        this.add(panellLogin);
        //panellInferior.add(panellLogin);
 
        
        
        
    }
    
    private void creaTextFields(){
        int alturaBoto= (ALTURA_BOTO * this.getHeight()) / 100;
        int ampladaBoto = (AMPLADA_BOTO * this.getWidth()) / 100;
        
        campUsuari = new JTextField();
        campUsuari.setPreferredSize(new Dimension(ampladaBoto,alturaBoto));
        campUsuari.setMinimumSize(DIMENSIO_MIN_BUTTONS);
        campUsuari.setMaximumSize(DIMENSIO_MAX_BUTTONS);
        
        campPass = new JPasswordField();
        campPass.setPreferredSize(new Dimension(ampladaBoto,alturaBoto));
        campPass.setMinimumSize(DIMENSIO_MIN_BUTTONS);
        campPass.setMaximumSize(DIMENSIO_MAX_BUTTONS);
        
    }
    
//     private void creaFinestra() throws IOException{
//        setLayout(null);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setExtendedState(JFrame.MAXIMIZED_BOTH);
//        
//        BufferedImage bf = ImageIO.read(new File("res/Inici.png"));
//        setVisible(true);
//        setContentPane(new background(bf, this.getHeight(),this.getWidth() ));
//        setResizable(false);
//    }

    @Override
    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

        if (e.getSource()==btnRanking) {
            setTitle("boto ranking");
            new FRanking().mostrarFrame();
        }
        else if (e.getSource()==btnEntrar) {
            String user = "kobe";
            usuari = BD.obtenirUsuari(user);
            if(usuari != null){
                new FMenu().mostrarFrame();
            }
//            Partida partida = new Partida(ELaberintsPredefinits.LABERINT_ALEATORI, 15, EElement.FANTASMA2);
//            Laberint laberint = partida.obtenirLaberint();
//            FLaberint fLaberint = new FLaberint(laberint);
//            FPartida fPartida = new FPartida(fLaberint);
//            partida.assignarPintador(fPartida);
//            partida.iniciarPartida();
        }
        else if (e.getSource()==btnAlta) {
            new FAlta().mostrarFrame();
        } 
        else if (e.getSource() == btnSortir){
            dispose();
            System.exit(0);
        }
    }
    
    public static Usuari obtenirUsuari(){
        return usuari;
    }
}
