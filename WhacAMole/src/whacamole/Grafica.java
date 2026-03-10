/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package whacamole;
import java.awt.Image;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
/**
 *
 * @author compu
 */
public class Grafica extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Grafica.class.getName());
    private Gestore gestore = new Gestore();
    private ArrayList<JButton> bottoni = new ArrayList<>();
    private ArrayList<JLabel> buche = new ArrayList<>();
    Gestore punti = new Gestore();
    private boolean giocoAttivo = true;
    private Thread threadTalpa;
    
    
    /**
     * Creates new form Grafica
     */
    public Grafica() {
        initComponents();
        Panel.setLayout(null);
        Panel.setBounds(0, 0, getWidth(), getHeight());
        Panel.setVisible(true);

        this.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent evt) {
                Panel.setBounds(0, 0, getWidth(), getHeight());
                ridimensionaElementi(new ImageIcon(getClass().getResource("/images/BucaTalpa1.png")),
                                     new ImageIcon(getClass().getResource("/images/TalpaVera.png")));
                Panel.revalidate();
                Panel.repaint();
            }
        });
        ImageIcon iconTalpa = new ImageIcon(getClass().getResource("/images/TalpaVera.png"));
        int nuovaLarghezza = iconTalpa.getIconWidth() / 2;
        int nuovaAltezza = iconTalpa.getIconHeight() / 2;
        ImageIcon talpaIconRidimensionata = new ImageIcon(iconTalpa.getImage().getScaledInstance(nuovaLarghezza, nuovaAltezza, Image.SCALE_SMOOTH));
                
        ArrayList<JLabel> buche = new ArrayList<>();
        buche.add(BucaLabel);
        buche.add(BucaLabel1);
        buche.add(BucaLabel2);
        buche.add(BucaLabel3);
        buche.add(BucaLabel4);
        buche.add(BucaLabel5);
        buche.add(BucaLabel6);
        buche.add(BucaLabel7);
        buche.add(BucaLabel8);
        
        ImageIcon iconBucaOriginale = new ImageIcon(getClass().getResource("/images/BucaTalpa1.png"));
        
        ridimensionaElementi(iconBucaOriginale, iconTalpa);

        for (JLabel buca : buche) {
            int larghezza = (buca.getWidth() > 0) ? buca.getWidth() : 10;
            int altezza = (buca.getHeight() > 0) ? buca.getHeight() : 5;
            Image imgBuca = iconBucaOriginale.getImage().getScaledInstance(larghezza, altezza, Image.SCALE_SMOOTH);
            buca.setIcon(new ImageIcon(imgBuca));
        }
        
        int larghezzaTalpa = 20;
        int altezzaTalpa = 20;

        bottoni.add(Bottone1);
        bottoni.add(Bottone2);
        bottoni.add(Bottone3);
        bottoni.add(Bottone4);
        bottoni.add(Bottone5);
        bottoni.add(Bottone6);
        bottoni.add(Bottone7);
        bottoni.add(Bottone8);
        bottoni.add(Bottone9);
    
        for (int i = 0; i < bottoni.size(); i++) {
            JButton t = bottoni.get(i);
            JLabel buca = buche.get(i);

            t.setBounds(buca.getX() + (buca.getWidth() - nuovaLarghezza) / 2,
                        buca.getY() - nuovaAltezza / 2,
                        nuovaLarghezza,
                        nuovaAltezza);
            t.setOpaque(false);
            t.setContentAreaFilled(false);
            t.setBorderPainted(false);
            t.setVisible(false);
            t.setIcon(talpaIconRidimensionata);
        }
    
        for (JButton b : bottoni) {
            b.setIcon(talpaIconRidimensionata);
            b.setOpaque(false);
            b.setContentAreaFilled(false);
            b.setBorderPainted(false);
            b.setVisible(false);
        }
        
    Panel.setVisible(false);
    nascondiTutto();

        threadTalpa = new Thread(() -> {
            while (giocoAttivo) {
                try {
                    Thread.sleep(1000);

                    SwingUtilities.invokeLater(() -> mostraTalpa());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }
        });
        threadTalpa.start();
    }
    
    private void nascondiTutto() {
        for (JButton b : bottoni) {
            b.setVisible(false);
        }
    }
    
    private void ridimensionaElementi(ImageIcon iconBuca, ImageIcon iconTalpa) {
        int panelWidth = Panel.getWidth() > 0 ? Panel.getWidth() : 300;
        int panelHeight = Panel.getHeight() > 0 ? Panel.getHeight() : 300;

        int colonne = 3;
        int righe = 3;
        int distanzaX = panelWidth / colonne;
        int distanzaY = panelHeight / righe;

        for (int i = 0; i < buche.size(); i++) {
            JLabel buca = buche.get(i);

            int x = (i % colonne) * distanzaX + distanzaX / 4;
            int y = (i / colonne) * distanzaY + distanzaY / 4;
            int larghezzaBuca = distanzaX / 2;
            int altezzaBuca = distanzaY / 3;

            buca.setBounds(x, y, larghezzaBuca, altezzaBuca);
            Image imgBuca = iconBuca.getImage().getScaledInstance(larghezzaBuca, altezzaBuca, Image.SCALE_SMOOTH);
            buca.setIcon(new ImageIcon(imgBuca));

            JButton t = bottoni.get(i);
            int larghezzaTalpa = larghezzaBuca / 2;
            int altezzaTalpa = altezzaBuca / 2;
            int xTalpa = x + (larghezzaBuca - larghezzaTalpa) / 2;
            int yTalpa = y + (altezzaBuca - altezzaTalpa) / 2;

            t.setBounds(xTalpa, yTalpa, larghezzaTalpa, altezzaTalpa);
            Image imgTalpa = iconTalpa.getImage().getScaledInstance(larghezzaTalpa, altezzaTalpa, Image.SCALE_SMOOTH);
            t.setIcon(new ImageIcon(imgTalpa));
        }
    }
    
    private void mostraTalpa() {
        nascondiTutto();

        int indice = gestore.esciTalpa();
        bottoni.get(indice).setVisible(true);
    }
    
    public void fermaGioco() {
        giocoAttivo = false;
        if (threadTalpa != null) {
            threadTalpa.interrupt();
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Panel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtPunteggio = new javax.swing.JLabel();
        Bottone2 = new javax.swing.JButton();
        Bottone5 = new javax.swing.JButton();
        Bottone3 = new javax.swing.JButton();
        Bottone7 = new javax.swing.JButton();
        Bottone6 = new javax.swing.JButton();
        Bottone8 = new javax.swing.JButton();
        Bottone4 = new javax.swing.JButton();
        Bottone9 = new javax.swing.JButton();
        Bottone1 = new javax.swing.JButton();
        BucaLabel = new javax.swing.JLabel();
        BucaLabel1 = new javax.swing.JLabel();
        BucaLabel2 = new javax.swing.JLabel();
        BucaLabel3 = new javax.swing.JLabel();
        BucaLabel4 = new javax.swing.JLabel();
        BucaLabel5 = new javax.swing.JLabel();
        BucaLabel6 = new javax.swing.JLabel();
        BucaLabel7 = new javax.swing.JLabel();
        BucaLabel8 = new javax.swing.JLabel();
        txtTitolo = new javax.swing.JLabel();
        BottoneGioca = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Panel.setBackground(new java.awt.Color(255, 255, 255));
        Panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Ravie", 3, 24)); // NOI18N
        jLabel1.setText("Punteggio: ");
        Panel.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, -1, 32));

        txtPunteggio.setFont(new java.awt.Font("Ravie", 3, 24)); // NOI18N
        txtPunteggio.setText("0");
        Panel.add(txtPunteggio, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 27, 160, 40));

        Bottone2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Bottone2ActionPerformed(evt);
            }
        });
        Panel.add(Bottone2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 190, -1, 48));

        Bottone5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Bottone5ActionPerformed(evt);
            }
        });
        Panel.add(Bottone5, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 100, -1, 48));

        Bottone3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Bottone3ActionPerformed(evt);
            }
        });
        Panel.add(Bottone3, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 390, -1, 47));

        Bottone7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Bottone7ActionPerformed(evt);
            }
        });
        Panel.add(Bottone7, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 220, -1, 44));

        Bottone6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Bottone6ActionPerformed(evt);
            }
        });
        Panel.add(Bottone6, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 140, -1, 47));

        Bottone8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Bottone8ActionPerformed(evt);
            }
        });
        Panel.add(Bottone8, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 240, -1, 48));

        Bottone4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Bottone4ActionPerformed(evt);
            }
        });
        Panel.add(Bottone4, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 350, -1, 47));

        Bottone9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Bottone9ActionPerformed(evt);
            }
        });
        Panel.add(Bottone9, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 360, 74, 57));

        Bottone1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Bottone1ActionPerformed(evt);
            }
        });
        Panel.add(Bottone1, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 220, -1, 44));

        BucaLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/BucaTalpa1.png"))); // NOI18N
        Panel.add(BucaLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 370, 80, 56));

        BucaLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/BucaTalpa1.png"))); // NOI18N
        Panel.add(BucaLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 120, 80, 55));

        BucaLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/BucaTalpa1.png"))); // NOI18N
        Panel.add(BucaLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 240, 80, 56));

        BucaLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/BucaTalpa1.png"))); // NOI18N
        Panel.add(BucaLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 410, 80, 56));

        BucaLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/BucaTalpa1.png"))); // NOI18N
        Panel.add(BucaLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 110, 80, 56));

        BucaLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/BucaTalpa1.png"))); // NOI18N
        Panel.add(BucaLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 260, 80, 55));

        BucaLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/BucaTalpa1.png"))); // NOI18N
        Panel.add(BucaLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 240, 80, 56));

        BucaLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/BucaTalpa1.png"))); // NOI18N
        Panel.add(BucaLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 390, 80, 56));

        BucaLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/BucaTalpa1.png"))); // NOI18N
        Panel.add(BucaLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 220, 80, 56));

        txtTitolo.setFont(new java.awt.Font("Rockwell Extra Bold", 3, 36)); // NOI18N
        txtTitolo.setText("Whac-A-Mole");

        BottoneGioca.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        BottoneGioca.setText("Gioca");
        BottoneGioca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BottoneGiocaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Panel, javax.swing.GroupLayout.DEFAULT_SIZE, 779, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(328, Short.MAX_VALUE)
                    .addComponent(BottoneGioca, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(341, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(240, Short.MAX_VALUE)
                    .addComponent(txtTitolo)
                    .addContainerGap(240, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Panel, javax.swing.GroupLayout.DEFAULT_SIZE, 518, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(241, Short.MAX_VALUE)
                    .addComponent(BottoneGioca, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(230, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(txtTitolo, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(415, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void Bottone1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Bottone1ActionPerformed
        punti.setPunteggio(10);
        txtPunteggio.setText(" " + punti);
        Bottone1.setVisible(false);
    }//GEN-LAST:event_Bottone1ActionPerformed

    private void Bottone4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Bottone4ActionPerformed
        punti.setPunteggio(10);
        txtPunteggio.setText(" " + punti);
        Bottone4.setVisible(false);
    }//GEN-LAST:event_Bottone4ActionPerformed

    private void Bottone7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Bottone7ActionPerformed
        punti.setPunteggio(10);
        txtPunteggio.setText(" " + punti);
        Bottone7.setVisible(false);
    }//GEN-LAST:event_Bottone7ActionPerformed

    private void Bottone2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Bottone2ActionPerformed
        punti.setPunteggio(10);
        txtPunteggio.setText(" " + punti);
        Bottone2.setVisible(false);
    }//GEN-LAST:event_Bottone2ActionPerformed

    private void Bottone5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Bottone5ActionPerformed
        punti.setPunteggio(10);
        txtPunteggio.setText(" " + punti);
        Bottone5.setVisible(false);
    }//GEN-LAST:event_Bottone5ActionPerformed

    private void Bottone9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Bottone9ActionPerformed
        punti.setPunteggio(10);
        txtPunteggio.setText(" " + punti);
        Bottone9.setVisible(false);
    }//GEN-LAST:event_Bottone9ActionPerformed

    private void Bottone3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Bottone3ActionPerformed
        punti.setPunteggio(10);
        txtPunteggio.setText(" " + punti);
        Bottone3.setVisible(false);
    }//GEN-LAST:event_Bottone3ActionPerformed

    private void Bottone6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Bottone6ActionPerformed
        punti.setPunteggio(10);
        txtPunteggio.setText(" " + punti);
        Bottone6.setVisible(false);
    }//GEN-LAST:event_Bottone6ActionPerformed

    private void Bottone8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Bottone8ActionPerformed
        punti.setPunteggio(10);
        txtPunteggio.setText(" " + punti);
        Bottone8.setVisible(false);
    }//GEN-LAST:event_Bottone8ActionPerformed

    private void BottoneGiocaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BottoneGiocaActionPerformed
        Panel.setVisible(true);
        BottoneGioca.setVisible(false);
        txtTitolo.setVisible(false);
    }//GEN-LAST:event_BottoneGiocaActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new Grafica().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Bottone1;
    private javax.swing.JButton Bottone2;
    private javax.swing.JButton Bottone3;
    private javax.swing.JButton Bottone4;
    private javax.swing.JButton Bottone5;
    private javax.swing.JButton Bottone6;
    private javax.swing.JButton Bottone7;
    private javax.swing.JButton Bottone8;
    private javax.swing.JButton Bottone9;
    private javax.swing.JButton BottoneGioca;
    private javax.swing.JLabel BucaLabel;
    private javax.swing.JLabel BucaLabel1;
    private javax.swing.JLabel BucaLabel2;
    private javax.swing.JLabel BucaLabel3;
    private javax.swing.JLabel BucaLabel4;
    private javax.swing.JLabel BucaLabel5;
    private javax.swing.JLabel BucaLabel6;
    private javax.swing.JLabel BucaLabel7;
    private javax.swing.JLabel BucaLabel8;
    private javax.swing.JPanel Panel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel txtPunteggio;
    private javax.swing.JLabel txtTitolo;
    // End of variables declaration//GEN-END:variables
}
