/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rozkladliczbygui;

import java.awt.event.KeyEvent;

/**
 *
 * @author Marcin
 */
public class RozkladGUI extends javax.swing.JFrame
{
    private RozkladLiczby RL;
    /**
     * Creates new form RozkladGUI
     */
    public RozkladGUI()
    {
        initComponents();
        errorLabel.setVisible(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        numberToFactor = new javax.swing.JTextField();
        buttonRun = new javax.swing.JButton();
        resultsLabel = new javax.swing.JLabel();
        promptLabel = new javax.swing.JLabel();
        errorLabel = new javax.swing.JLabel();
        sizeField = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        buttonCreate = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Rozkład liczby");
        setResizable(false);
        setType(java.awt.Window.Type.POPUP);

        numberToFactor.setToolTipText("");
        numberToFactor.setEnabled(false);
        numberToFactor.addKeyListener(new java.awt.event.KeyAdapter()
        {
            public void keyPressed(java.awt.event.KeyEvent evt)
            {
                numberToFactorKeyPressed(evt);
            }
        });

        buttonRun.setText("Rozłóż");
        buttonRun.setEnabled(false);
        buttonRun.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                buttonRunActionPerformed(evt);
            }
        });

        resultsLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        promptLabel.setText("Wpisz liczbę:");

        errorLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        errorLabel.setForeground(new java.awt.Color(240, 0, 0));
        errorLabel.setText("errorLabel");
        errorLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel1.setText("Rozmiar sita:");

        buttonCreate.setText("Stwórz");
        buttonCreate.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                buttonCreateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(resultsLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(sizeField, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(numberToFactor, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE))
                                    .addComponent(promptLabel))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(buttonRun, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(buttonCreate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addComponent(jLabel1)
                            .addComponent(errorLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 8, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(errorLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sizeField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonCreate))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(promptLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(numberToFactor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonRun))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(resultsLabel)
                .addGap(31, 31, 31))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonRunActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_buttonRunActionPerformed
    {//GEN-HEADEREND:event_buttonRunActionPerformed
        errorLabel.setVisible(false);
        resultsLabel.setVisible(false);
        int number;
        try
        {
            number = Integer.parseInt(numberToFactor.getText());
        }
        catch(NumberFormatException nfe)
        {
            errorLabel.setText("Liczba spoza zakresu/nieprawidłowa.");
            errorLabel.setVisible(true);
            return;
        }
        if (RL==null)
        {
            errorLabel.setText("Sito nie zostało jeszcze utworzone");
            errorLabel.setVisible(true);
            return;
        }
        int[] rozklad;
        try
        {
            rozklad = RL.czynniki(number);
        }
        catch (IllegalArgumentException bounds)
        {
            errorLabel.setText("Podana liczba jest większa niż rozmiar sita.");
            errorLabel.setVisible(true);
            return;
        }
        
        resultsLabel.setText(RozkladLiczby.rozkladString(number, rozklad));
        resultsLabel.setVisible(true);
        return;
    }//GEN-LAST:event_buttonRunActionPerformed

    private void numberToFactorKeyPressed(java.awt.event.KeyEvent evt)//GEN-FIRST:event_numberToFactorKeyPressed
    {//GEN-HEADEREND:event_numberToFactorKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER)
        {
            buttonRunActionPerformed(null);
        }
    }//GEN-LAST:event_numberToFactorKeyPressed

    private void buttonCreateActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_buttonCreateActionPerformed
    {//GEN-HEADEREND:event_buttonCreateActionPerformed
        int number;
        errorLabel.setVisible(false);
        try 
        {
            number=Integer.parseInt(sizeField.getText());
        }
        catch(NumberFormatException nfe)
        {
            errorLabel.setText("Rozmiar sita spoza zakresu/nieprawidłowy.");
            errorLabel.setVisible(true);
            return;
        }
        try
        {
            RL  = new RozkladLiczby(number);
        }
        catch (IllegalArgumentException iae)
        {
            errorLabel.setText(iae.getMessage());
            errorLabel.setVisible(true);
            return;
        }
        catch (OutOfMemoryError oome)
        {
            errorLabel.setText("Zbyt duża liczba (brak pamięci).");
            errorLabel.setVisible(true);
            return;
        }
        numberToFactor.setEnabled(true);
        buttonRun.setEnabled(true);
        
    }//GEN-LAST:event_buttonCreateActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[])
    {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        
        try
        {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
            {
                if ("Nimbus".equals(info.getName()))
                {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        }
        catch (ClassNotFoundException ex)
        {
            java.util.logging.Logger.getLogger(RozkladGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (InstantiationException ex)
        {
            java.util.logging.Logger.getLogger(RozkladGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (IllegalAccessException ex)
        {
            java.util.logging.Logger.getLogger(RozkladGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (javax.swing.UnsupportedLookAndFeelException ex)
        {
            java.util.logging.Logger.getLogger(RozkladGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                new RozkladGUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonCreate;
    private javax.swing.JButton buttonRun;
    private javax.swing.JLabel errorLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JTextField numberToFactor;
    private javax.swing.JLabel promptLabel;
    private javax.swing.JLabel resultsLabel;
    private javax.swing.JTextField sizeField;
    // End of variables declaration//GEN-END:variables
}
