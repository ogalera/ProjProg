package interficie;

import dades.BD;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import logica.Usuari;
import logica.Utils.Constants;

/**
 * @author oscar
 * 
 * @brief
 * Llistat amb els usuaris que tenen més punts ordenats pel nombre total de punts
 * obtinguts en el mode aventura.
 * 
 * Al seleccionar un usuari es mostra el seu historic.
 */
public class FRanking extends FFrameAmbLog implements ListSelectionListener, ActionListener{
    
    private final Usuari[] usuaris; /**<Conjunt d'usuaris registrats en el sistema*/
    
    /**
     * @pre --
     * @post em creat el ranking per els Utils.Constants.TOP_N_DEL_RANKING usuaris
     * amb més punts.
     */
    public FRanking() {
        usuaris = BD.obtenirRanking(Constants.TOP_N_DEL_RANKING);
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

        jScrollPane1 = new javax.swing.JScrollPane();
        jliRanking = new javax.swing.JList();
        btnSortir = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Ranking");
        setAlwaysOnTop(true);
        setResizable(false);

        jliRanking.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(jliRanking);

        btnSortir.setLabel("Sortir");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(btnSortir, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSortir)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnSortir.getAccessibleContext().setAccessibleName("btnSortir");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @pre el dialeg no s'ha mostrat.
     * @post el dialeg s'esta mostrant.
     */
    public void mostrarFrame(){
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                jliRanking.setListData(usuaris);
                jliRanking.setCellRenderer(new RegistreUsuari());
                jliRanking.addListSelectionListener(FRanking.this);
                btnSortir.addActionListener(FRanking.this);
                setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSortir;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList jliRanking;
    // End of variables declaration//GEN-END:variables

    @Override
    public void valueChanged(ListSelectionEvent e) {
        ///Em seleccionat un usuari del ranking.
        if(e.getValueIsAdjusting()){
            Usuari usuari = usuaris[jliRanking.getSelectedIndex()];
            int historic []= BD.obtenirHistoricPuntsUsuari(usuari);
            new FHistoricUsuari(usuari.obtenirNomUsuari(), usuari.obtenirImatge(), historic).mostrarFrame();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ///Em sortit del ranking.
        if(e.getSource() == btnSortir){
            this.dispose();
        }
    }
}

/**
 * @author oscar
 * @brief
 * cada un dels registrs (seleccionable) del llistat d'usuaris.
 * 
 * esta format per la imatge de perfil de l'usuari seguit del seu nom.
 */
class RegistreUsuari extends JLabel implements ListCellRenderer<Usuari>{
    private static final Color colorSeleccio = new Color(0,0,128); /**<Color de fons per el registre selecciona*/
    public RegistreUsuari(){
        this.setOpaque(true);
        this.setIconTextGap(12);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Usuari> llistat, 
                                                    Usuari usuari, 
                                                    int index, 
                                                    boolean estaSeleccionat, 
                                                    boolean celaAmbFocus) {
        this.setText(usuari.obtenirNomUsuari());
        this.setIcon(usuari.obtenirImatge());
        if(estaSeleccionat){
            this.setBackground(colorSeleccio);
            this.setForeground(Color.white);
        }
        else{
            this.setBackground(Color.white);
            this.setForeground(Color.black);
        }
        return this;
    }

}