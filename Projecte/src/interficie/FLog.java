package interficie;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import logica.log.Log;

/**
 * @author oscar
 * 
 * @brief
 * Pantalla per mostrar el log de l'aplicació;
 * 
 * Aquesta pantalla permet:
 *      - Mostrar tot el log.
 *  
 *      - Exportar el log en format text (.txt)
 * 
 *      - Filtrar els missatges del log de un tipus concret
 *          - DEBUG.
 *          - WARNING.
 *          - ERROR.
 * 
 *      - Mostrar l'últim missatge afegit al log.
 */
public class FLog extends JFrame implements ItemListener, ActionListener{
    ///Flag que ens marca quin tipus de missatge està actiu.
    private boolean debugActiu = true; /**<missatges de debug actius*/
    private boolean warningActiu = true; /**<missatges de warning actius*/
    private boolean errorActiu = true; /**<missatges d'error actius*/
    private boolean nomesUltimMissatge = false; /**<només mostrar últim missatge actiu*/
    
    private final Log log;
    
    /**
     * @pre --
     * @post em creat la pantalla de log.
     */
    public FLog() {
        log = Log.getInstance(FLog.class);
        initComponents();
        setLocationRelativeTo(null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        chbDebug = new javax.swing.JCheckBox();
        chbWarning = new javax.swing.JCheckBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtLog = new javax.swing.JTextArea();
        chbError = new javax.swing.JCheckBox();
        jMenuBar2 = new javax.swing.JMenuBar();
        jMenu3 = new javax.swing.JMenu();
        btnExportarLog = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        chbmNomesUltimMissatge = new javax.swing.JCheckBoxMenuItem();

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);
        setResizable(false);

        chbDebug.setForeground(new java.awt.Color(0, 204, 51));
        chbDebug.setSelected(true);
        chbDebug.setLabel("Debug");

        chbWarning.setForeground(new java.awt.Color(255, 153, 0));
        chbWarning.setSelected(true);
        chbWarning.setText("WARNING");
        chbWarning.setActionCommand("Warning");

        txtLog.setColumns(20);
        txtLog.setRows(5);
        jScrollPane1.setViewportView(txtLog);

        chbError.setForeground(new java.awt.Color(255, 0, 0));
        chbError.setSelected(true);
        chbError.setText("ERROR");

        jMenu3.setText("File");

        btnExportarLog.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_MASK));
        btnExportarLog.setText("exportar log");
        jMenu3.add(btnExportarLog);
        jMenu3.add(jSeparator1);

        chbmNomesUltimMissatge.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_U, java.awt.event.InputEvent.CTRL_MASK));
        chbmNomesUltimMissatge.setLabel("Nomes ultim missatge");
        jMenu3.add(chbmNomesUltimMissatge);
        chbmNomesUltimMissatge.getAccessibleContext().setAccessibleName("chbmNomesUltimMissatge");

        jMenuBar2.add(jMenu3);

        setJMenuBar(jMenuBar2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(chbDebug)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
                        .addComponent(chbWarning)
                        .addGap(68, 68, 68)
                        .addComponent(chbError)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chbDebug)
                    .addComponent(chbWarning)
                    .addComponent(chbError))
                .addContainerGap())
        );

        chbDebug.getAccessibleContext().setAccessibleName("chbDebug");
        chbWarning.getAccessibleContext().setAccessibleName("Warning");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @pre --
     * @post em mostrar la pantalla de log.
     */
    public void mostrarFrame(){
        ///Afegim els events als diferents controls
        chbDebug.addItemListener(this);
        chbWarning.addItemListener(this);
        chbError.addItemListener(this);
        chbmNomesUltimMissatge.addItemListener(this);
        btnExportarLog.addActionListener(this);
        txtLog.setText(log.obtenirContingutCompletDelLogAmbColor());
        ///fem visible la pantalla.
        this.setVisible(true);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem btnExportarLog;
    private javax.swing.JCheckBox chbDebug;
    private javax.swing.JCheckBox chbError;
    private javax.swing.JCheckBox chbWarning;
    private javax.swing.JCheckBoxMenuItem chbmNomesUltimMissatge;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JTextArea txtLog;
    // End of variables declaration//GEN-END:variables

    @Override
    public void itemStateChanged(ItemEvent e) {
        ///Ens ha canviat algún checkBox dels filtres per el tipus de missatge.
        if(e.getSource() == chbDebug){
            debugActiu = !debugActiu;
        }
        else if(e.getSource() == chbWarning){
            warningActiu = !warningActiu;
        }
        else if(e.getSource() == chbError){
            errorActiu = !errorActiu;
        }
        else{
            nomesUltimMissatge = !nomesUltimMissatge;
        }
        
        refrescarLog();
    }

    /**
     * @pre --
     * @post s'ha refrescat el contingut del log tenint en compte els filtres actius.
     */
    private void refrescarLog(){
        ///Treiem el contingut del log.
        txtLog.setText(null);
        
        ///Mostrem el contingut del log de els filtres activats.
        if(debugActiu){
            if(nomesUltimMissatge) txtLog.append(log.obtenirUltimDebug());
            else txtLog.append(log.obtenirDebugsLog());
        }
        else if(warningActiu){
            if(nomesUltimMissatge) txtLog.append(log.obtenirUltimWarning());
            else txtLog.append(log.obtenirWarningsLog());
        }
        else if(errorActiu){
            if(nomesUltimMissatge) txtLog.append(log.obtenirUltimError());
            else txtLog.append(log.obtenirErrorsLog());
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btnExportarLog){
            String desti = seleccionarCarpeta();
            if(desti != null){
                exportarLog(desti);
            }
        }
    }
    
    /**
     * @pre --;
     * @post em mostrat un dialeg per seleccionar un directori, si s'ha seleccionat
     * de forma correct es retorna la seva ruta altrament es retorna null.
     */
    private String seleccionarCarpeta(){
        String carpetaDesti = null;
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if(fc.showDialog(FLog.this, "Exportar Log") == JFileChooser.APPROVE_OPTION){
            carpetaDesti = fc.getSelectedFile().getAbsolutePath();
        }
        return carpetaDesti;
    }
    
    /**
     * @pre desti és la ruta a un directori accessible per escriure.
     * @post em exportat el log en format text (.txt) en la carpeta destí,
     * el nom del fitxer és log_dia_mes_any_hora_minuts.txt.
     */
    private void exportarLog(String desti){
        SimpleDateFormat sdf = new SimpleDateFormat("_dd_MM_yyyy_kk_mm");
        String marcaTemps = sdf.format(new Date());
        String nomFitxer = desti+"/log"+marcaTemps+".txt";
        boolean operacio = this.exportarFitxer(nomFitxer);
        if(operacio){
            JOptionPane.showMessageDialog(FLog.this,
                                            "S'ha exportat el log correctament a "+nomFitxer,
                                            "Log exportar correctament!",JOptionPane.INFORMATION_MESSAGE);
        }
        else{
            JOptionPane.showMessageDialog(FLog.this,
                                            "Hi ha hagut un error al exportar el log",
                                            "Error al exportar el log",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * @pre fitxer correspont a una ruta valida.
     * @post em exportat el contingut del log sobre fitxer.
     */
    public boolean exportarFitxer(String fitxer){
        boolean operacio = true;
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(fitxer))){
            String contingutLog = txtLog.getText();
            bw.write(contingutLog);
        }
        catch(IOException ioe){
            log.afegirError(ioe.getMessage());
            operacio = false;
        }
        return operacio;
    }
}
