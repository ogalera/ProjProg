package interficie;//GEN-LINE:initComponents

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import logica.Partida;
import logica.Usuari;
import logica.Usuari.EDificultat;
import logica.Usuari.ENivells;
import logica.controladors_pacman.ControladorTeclat;
import logica.controladors_pacman.IControlador;
import logica.enumeracions.EElement;
import logica.enumeracions.ELaberintsPredefinits;
import logica.excepcions.EFormatLaberint;
import logica.log.Log;

/**
 * @author oscar
 */
public class FMenu extends FFrameAmbLog implements ActionListener{

    /**
     * Creates new form FMenu
     */
    public FMenu() {
        initComponents();
        setLocationRelativeTo(null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        btnAventura = new javax.swing.JButton();
        btnProvarMapa = new javax.swing.JButton();
        btnCrearMapa = new javax.swing.JButton();
        btnSortir = new javax.swing.JButton();
        lblImatgeUsuari = new javax.swing.JLabel();
        lblUsuari = new javax.swing.JLabel();
        lblNivell = new javax.swing.JLabel();
        lblUSUARI = new javax.swing.JLabel();
        lblNIVELL = new javax.swing.JLabel();
        lblDificultat = new javax.swing.JLabel();
        lblDIFICULTAT = new javax.swing.JLabel();

        setTitle("Menú");
        setAlwaysOnTop(true);
        setName("fMenu"); // NOI18N

        btnAventura.setText("Aventura");
        btnAventura.setActionCommand("btnAventura");

        btnProvarMapa.setText("Provar Mapa");

        btnCrearMapa.setText("Crear Mapa");

        btnSortir.setText("Sortir");
        btnSortir.setName("btnSortir"); // NOI18N

        lblImatgeUsuari.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        lblImatgeUsuari.setPreferredSize(new java.awt.Dimension(100, 100));

        lblUsuari.setText("Usuari");

        lblNivell.setText("Nivell");

        lblUSUARI.setText("USUARI");

        lblNIVELL.setText("NIVELL");

        lblDificultat.setText("Dificultat");

        lblDIFICULTAT.setText("DIFICULTAT");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(lblImatgeUsuari, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAventura, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                    .addComponent(btnProvarMapa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCrearMapa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSortir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblNivell)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblNIVELL))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(lblUsuari)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblUSUARI))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblDificultat)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblDIFICULTAT)))
                .addContainerGap())
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDificultat)
                    .addComponent(lblDIFICULTAT))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnAventura)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnProvarMapa)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCrearMapa)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSortir)
                .addContainerGap())
        );

        btnAventura.getAccessibleContext().setAccessibleName("btnAventura");
        btnProvarMapa.getAccessibleContext().setAccessibleName("btnProbarMapa");
        btnCrearMapa.getAccessibleContext().setAccessibleName("btnCrearMapa");
        btnSortir.getAccessibleContext().setAccessibleName("btnSortir");

        pack();
    }// </editor-fold>                        

    public void mostrarFrame(){
        Usuari usuari = FLogin.obtenirUsuari();
        lblUSUARI.setText(usuari.obtenirNomUsuari());
        lblNIVELL.setText(usuari.obtenirNivell().toString());
        lblDIFICULTAT.setText(usuari.obtenirDificultat().toString());
        lblImatgeUsuari.setIcon(usuari.obtenirImatge());
        btnAventura.addActionListener(this);
        btnProvarMapa.addActionListener(this);
        btnCrearMapa.addActionListener(this);
        btnSortir.addActionListener(this);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    // Variables declaration - do not modify                     
    private javax.swing.JButton btnAventura;
    private javax.swing.JButton btnCrearMapa;
    private javax.swing.JButton btnProvarMapa;
    private javax.swing.JButton btnSortir;
    private javax.swing.JLabel lblDIFICULTAT;
    private javax.swing.JLabel lblDificultat;
    private javax.swing.JLabel lblImatgeUsuari;
    private javax.swing.JLabel lblNIVELL;
    private javax.swing.JLabel lblNivell;
    private javax.swing.JLabel lblUSUARI;
    private javax.swing.JLabel lblUsuari;
    // End of variables declaration                   

    @Override
    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);
        if(e.getSource() == btnAventura){
            IControlador controlador = new ControladorTeclat();
            PLaberint fLaberint = new PLaberint();
            FPartida fPartida = new FPartida(fLaberint, lblNIVELL, lblDIFICULTAT);
            EDificultat dificultat = FLogin.obtenirUsuari().obtenirDificultat();
            ENivells nivell = FLogin.obtenirUsuari().obtenirNivell();
            System.out.println(nivell);
            Partida partida = new Partida(ELaberintsPredefinits.LABERINT_ALEATORI,
                                            nivell.obtenirMidaLaberint(), 
                                            dificultat.obtenirEnemicAssignatADificultat()/*dificultat.obtenirEnemicAssignatADificultat()*/, 
                                            fPartida,
                                            fLaberint,
                                            controlador);
            fPartida.pintarPartida(partida);
            partida.iniciarPartida();
        }
        else if(e.getSource() == btnProvarMapa){
            File fitxer = obtenirFitxerLaberint();
            if(fitxer != null){
                String laberint = fitxer.toString();
                IControlador controlador = new ControladorTeclat();
                PLaberint fLaberint = new PLaberint();
                FPartida fPartida = new FPartida(fLaberint);
                try{
                    Partida partida = new Partida(laberint, fPartida, fLaberint, controlador);
                    fPartida.pintarPartida(partida);
                    partida.iniciarPartida();
                }
                catch(EFormatLaberint formatLaberintException){
                    Log log = Log.getInstance(FMenu.class);
                    log.afegirError("Error en el format del fitxer missatge: "+formatLaberintException.getMessage());
                }
            }
        }
        else if(e.getSource() == btnCrearMapa){
            new FEditorLaberint(5).mostrarFrame();
        }
        else if(e.getSource() == btnSortir) {
            dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        }
    }
    
    private File obtenirFitxerLaberint(){
        File fitxerSeleccionat = null;
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int resultat = fc.showDialog(FMenu.this, "Importar laberint");
        if(resultat == JFileChooser.APPROVE_OPTION){
            fitxerSeleccionat = fc.getSelectedFile();
        }
        return fitxerSeleccionat;
    }
}
