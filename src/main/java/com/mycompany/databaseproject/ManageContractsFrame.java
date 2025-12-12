package com.mycompany.databaseproject;

import javax.swing.JOptionPane;

public class ManageContractsFrame extends javax.swing.JFrame {

    /**
     * Creates new form ManageContractsFrame
     */
    private String currentRole;
public ManageContractsFrame(String role) {
    initComponents();
    this.currentRole = role;
    loadContracts();
}


private void loadContracts() {
    String sql =
        "SELECT hc.ContractID, " +
        "       c.FirstName + ' ' + c.LastName AS CustomerName, " +
        "       car.PlateNumber AS CarPlate, " +
        "       hc.StartDate, " +
        "       hc.TotalAmount " +
        "FROM HireContract hc " +
        "JOIN Customer c ON hc.CustomerID = c.CustomerID " +
        "JOIN Car car ON hc.CarID = car.CarID";

    DatabaseHelper.fillTable(tableContracts, sql);
}

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        lblTitle = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableContracts = new javax.swing.JTable();
        btnNewContract = new javax.swing.JButton();
        btnViewDetails = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        jLabel1.setText("jLabel1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTitle.setText("Manage Contracts");
        getContentPane().add(lblTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 20, -1, -1));

        tableContracts.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ContractID", "CustomerName", "CarPlate", "StartDate", "TotalAmount"
            }
        ));
        jScrollPane1.setViewportView(tableContracts);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 70, -1, 240));

        btnNewContract.setText("New Contract");
        btnNewContract.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewContractActionPerformed(evt);
            }
        });
        getContentPane().add(btnNewContract, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 340, -1, -1));

        btnViewDetails.setText("View contract details");
        btnViewDetails.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewDetailsActionPerformed(evt);
            }
        });
        getContentPane().add(btnViewDetails, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 340, -1, -1));

        btnBack.setText("Go to main menu");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });
        getContentPane().add(btnBack, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 340, -1, 20));

        btnRefresh.setText("Refresh");
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });
        getContentPane().add(btnRefresh, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 390, -1, -1));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/WhatsApp Image 2025-12-04 at 6.19.13 PM.jpeg"))); // NOI18N
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 580, 420));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        // TODO add your handling code here:
        loadContracts();
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void btnViewDetailsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewDetailsActionPerformed
        // TODO add your handling code here:
    int row = tableContracts.getSelectedRow();

    if (row < 0) {
        JOptionPane.showMessageDialog(this, "Please select a contract first.");
        return;
    }

    int contractId = Integer.parseInt(
        tableContracts.getValueAt(row, 0).toString()
    );

    ManagePaymentsFrame paymentsFrame =
        new ManagePaymentsFrame(contractId, currentRole);

    paymentsFrame.setVisible(true);
    this.dispose();

    }//GEN-LAST:event_btnViewDetailsActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        // TODO add your handling code here:
    new MainMenuFrame(currentRole).setVisible(true);
    this.dispose();
    }//GEN-LAST:event_btnBackActionPerformed

    private void btnNewContractActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewContractActionPerformed
        // TODO add your handling code here:
    // Open New Contract dialog (modal)
    NewContractDialog dialog = new NewContractDialog(this, true);
    dialog.setVisible(true);

    // After dialog closes → refresh table
    loadContracts();
    }//GEN-LAST:event_btnNewContractActionPerformed

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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ManageContractsFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ManageContractsFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ManageContractsFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ManageContractsFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new ManageContractsFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnNewContract;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnViewDetails;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JTable tableContracts;
    // End of variables declaration//GEN-END:variables
}
