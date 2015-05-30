package interficie;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;

/**
 * @author oscar
 * 
 * @brief
 * Frame amb un menuBar que conté un menuItem per obre la pantalla de log.
 */
public abstract class FFrameAmbLog extends JFrame implements ActionListener{
    /**
     * @pre --;
     * @post s'ha el frame amb log.
     */
    public FFrameAmbLog() {
        initComponents();
        itmMostrarLog.addActionListener(this);
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

        mbaBarraMenu = new javax.swing.JMenuBar();
        mnuOpcioFitxer = new javax.swing.JMenu();
        itmMostrarLog = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        mnuOpcioFitxer.setLabel("Fitxer");

        itmMostrarLog.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.CTRL_MASK));
        itmMostrarLog.setToolTipText("Mostrem el log de l'aplicatiu");
        itmMostrarLog.setLabel("Mostrar Log");
        mnuOpcioFitxer.add(itmMostrarLog);
        itmMostrarLog.getAccessibleContext().setAccessibleName("btnMostrarLog");

        mbaBarraMenu.add(mnuOpcioFitxer);

        setJMenuBar(mbaBarraMenu);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 279, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem itmMostrarLog;
    private javax.swing.JMenuBar mbaBarraMenu;
    private javax.swing.JMenu mnuOpcioFitxer;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == itmMostrarLog){
            new FLog().mostrarFrame();
        }
    }
}
