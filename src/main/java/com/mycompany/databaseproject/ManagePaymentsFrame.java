package com.mycompany.databaseproject;

import javax.swing.JOptionPane;

public class ManagePaymentsFrame extends javax.swing.JFrame {

    /**
     * Creates new form ManagePaymentsFrame
     */
    private int contractId;
public ManagePaymentsFrame() {
    initComponents();
    loadPayments(); // load ALL payments
}

public ManagePaymentsFrame(int contractId) {
    this.contractId = contractId;
    initComponents();
    loadPaymentsByContract(); // load ONLY this contract
}


private void loadPaymentsByContract() {
    String sql =
        "SELECT ip.PaymentID, ip.ContractID, " +
        "c.FirstName + ' ' + c.LastName AS CustomerName, " +
        "ip.Amount, ip.DueDate, " +
        "CASE WHEN ip.IsPaid = 1 THEN 'Paid' ELSE 'Unpaid' END AS Status " +
        "FROM InstallmentPayment ip " +
        "JOIN HireContract hc ON ip.ContractID = hc.ContractID " +
        "JOIN Customer c ON hc.CustomerID = c.CustomerID " +
        "WHERE ip.ContractID = ?";

    DatabaseHelper.fillTable(jtable1, sql, contractId);
}

private void loadPayments() {
    String sql =
        "SELECT " +
        " ip.PaymentID, " +
        " ip.ContractID, " +
        " CONCAT(c.FirstName, ' ', c.LastName) AS CustomerName, " +
        " ip.Amount, " +
        " ip.DueDate, " +
        " CASE " +
        "   WHEN ip.IsPaid = 1 THEN 'Paid' " +
        "   WHEN ip.IsPaid = 0 AND ip.DueDate < GETDATE() THEN 'Overdue' " +
        "   ELSE 'Unpaid' " +
        " END AS Status " +
        "FROM InstallmentPayment ip " +
        "JOIN HireContract hc ON ip.ContractID = hc.ContractID " +
        "JOIN Customer c ON hc.CustomerID = c.CustomerID";

    DatabaseHelper.fillTable(jtable1, sql);
}

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtable1 = new javax.swing.JTable();
        btmMarkAsPaid = new javax.swing.JButton();
        btnShowPaid = new javax.swing.JButton();
        btnShowUnpaid = new javax.swing.JButton();
        btnShowOverdue = new javax.swing.JButton();
        btnrefrech = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel1.setText("Manage Payments");
        jLabel1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 10, 190, 26));

        jtable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Payment ID", "Contract ID", "Customer Name", "Amount", "Due Date", "Status"
            }
        ));
        jScrollPane1.setViewportView(jtable1);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 60, 650, -1));

        btmMarkAsPaid.setText("Mark as Paid");
        btmMarkAsPaid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btmMarkAsPaidActionPerformed(evt);
            }
        });
        getContentPane().add(btmMarkAsPaid, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 500, -1, -1));

        btnShowPaid.setText("Show Paid");
        btnShowPaid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnShowPaidActionPerformed(evt);
            }
        });
        getContentPane().add(btnShowPaid, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 500, -1, -1));

        btnShowUnpaid.setText("Show Unpaid");
        btnShowUnpaid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnShowUnpaidActionPerformed(evt);
            }
        });
        getContentPane().add(btnShowUnpaid, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 500, -1, -1));

        btnShowOverdue.setText("Show Overdue");
        btnShowOverdue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnShowOverdueActionPerformed(evt);
            }
        });
        getContentPane().add(btnShowOverdue, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 500, -1, -1));

        btnrefrech.setText("Refresh");
        btnrefrech.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnrefrechActionPerformed(evt);
            }
        });
        getContentPane().add(btnrefrech, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 500, -1, -1));

        btnBack.setText("Back");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });
        getContentPane().add(btnBack, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 560, -1, -1));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/WhatsApp Image 2025-12-04 at 6.19.13 PM.jpeg"))); // NOI18N
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 740, 780));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnShowPaidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShowPaidActionPerformed
        // TODO add your handling code here:

    String sql =
        "SELECT ip.PaymentID, ip.ContractID, " +
        "c.FirstName + ' ' + c.LastName AS CustomerName, " +
        "ip.Amount, ip.DueDate, 'Paid' AS Status " +
        "FROM InstallmentPayment ip " +
        "JOIN HireContract hc ON ip.ContractID = hc.ContractID " +
        "JOIN Customer c ON hc.CustomerID = c.CustomerID " +
        "WHERE ip.IsPaid = 1";

    if (contractId > 0) {
        sql += " AND ip.ContractID = ?";
        DatabaseHelper.fillTable(jtable1, sql, contractId);
    } else {
        DatabaseHelper.fillTable(jtable1, sql);
    }


    }//GEN-LAST:event_btnShowPaidActionPerformed

    private void btnShowOverdueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShowOverdueActionPerformed
        // TODO add your handling code here:
    String sql =
        "SELECT ip.PaymentID, ip.ContractID, " +
        "c.FirstName + ' ' + c.LastName AS CustomerName, " +
        "ip.Amount, ip.DueDate, 'Overdue' AS Status " +
        "FROM InstallmentPayment ip " +
        "JOIN HireContract hc ON ip.ContractID = hc.ContractID " +
        "JOIN Customer c ON hc.CustomerID = c.CustomerID " +
        "WHERE ip.IsPaid = 0 AND ip.DueDate < GETDATE()";

    if (contractId > 0) {
        sql += " AND ip.ContractID = ?";
        DatabaseHelper.fillTable(jtable1, sql, contractId);
    } else {
        DatabaseHelper.fillTable(jtable1, sql);
    }


    }//GEN-LAST:event_btnShowOverdueActionPerformed

    private void btnrefrechActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnrefrechActionPerformed
        // TODO add your handling code here:
        loadPayments();
    }//GEN-LAST:event_btnrefrechActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnBackActionPerformed

    private void btnShowUnpaidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShowUnpaidActionPerformed
        // TODO add your handling code here:

    String sql =
        "SELECT ip.PaymentID, ip.ContractID, " +
        "c.FirstName + ' ' + c.LastName AS CustomerName, " +
        "ip.Amount, ip.DueDate, 'Unpaid' AS Status " +
        "FROM InstallmentPayment ip " +
        "JOIN HireContract hc ON ip.ContractID = hc.ContractID " +
        "JOIN Customer c ON hc.CustomerID = c.CustomerID " +
        "WHERE ip.IsPaid = 0 AND ip.DueDate >= GETDATE()";

    if (contractId > 0) {
        sql += " AND ip.ContractID = ?";
        DatabaseHelper.fillTable(jtable1, sql, contractId);
    } else {
        DatabaseHelper.fillTable(jtable1, sql);
    }


    }//GEN-LAST:event_btnShowUnpaidActionPerformed

    private void btmMarkAsPaidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btmMarkAsPaidActionPerformed
        // TODO add your handling code here:
    int row = jtable1.getSelectedRow();
    if (row < 0) {
        JOptionPane.showMessageDialog(this, "Select a payment first.");
        return;
    }

    int paymentID = Integer.parseInt(jtable1.getValueAt(row, 0).toString());

    String sql =
        "UPDATE InstallmentPayment " +
        "SET IsPaid = 1, PaymentDate = GETDATE() " +
        "WHERE PaymentID = ?";

    try {
        DatabaseHelper.executeUpdate(sql, paymentID);
        JOptionPane.showMessageDialog(this, "Payment marked as Paid!");

        if (contractId > 0)
            loadPaymentsByContract();
        else
            loadPayments();

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
    }


    }//GEN-LAST:event_btmMarkAsPaidActionPerformed

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
            java.util.logging.Logger.getLogger(ManagePaymentsFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ManagePaymentsFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ManagePaymentsFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ManagePaymentsFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ManagePaymentsFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btmMarkAsPaid;
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnShowOverdue;
    private javax.swing.JButton btnShowPaid;
    private javax.swing.JButton btnShowUnpaid;
    private javax.swing.JButton btnrefrech;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jtable1;
    // End of variables declaration//GEN-END:variables
}
