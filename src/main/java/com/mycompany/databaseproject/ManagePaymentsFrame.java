package com.mycompany.databaseproject;

import javax.swing.JOptionPane;

public class ManagePaymentsFrame extends javax.swing.JFrame {

    /**
     * Creates new form ManagePaymentsFrame
     */
    private int contractId = -1; // means all contracts
    private String currentRole;
    private boolean openedFromContracts;
    private java.util.HashMap<String, Integer> paymentMethodMap = new java.util.HashMap<>();

// Open ALL payments
    public ManagePaymentsFrame(String role) {
        this.currentRole = role;
        this.openedFromContracts = false;
        initComponents();
        makeTableReadOnly();
        applyRolePermissions();
        loadPayments();
       // checkOverdueWarnings();

        setLocationRelativeTo(null);
        applyRowColors();
        loadPaymentMethods();
    }

// Open payments for ONE contract
    public ManagePaymentsFrame(int contractId, String role) {
        this.contractId = contractId;
        this.currentRole = role;
        this.openedFromContracts = true;
        initComponents();
        makeTableReadOnly();
        applyRolePermissions();
        setLocationRelativeTo(null);
        loadPaymentsByContract();
       // checkOverdueWarnings();
        applyRowColors();
        loadPaymentMethods();
    }

    private void makeTableReadOnly() {
        jtable1.setDefaultEditor(Object.class, null);
    }

    private void applyRowColors() {
        jtable1.setDefaultRenderer(Object.class,
                new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(
                    javax.swing.JTable table, Object value,
                    boolean isSelected, boolean hasFocus,
                    int row, int column) {

                java.awt.Component c = super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    Object statusObj = table.getValueAt(row, 6);
                    String status = statusObj == null ? "" : statusObj.toString();

                    switch (status) {
                        case "Paid" ->
                            c.setBackground(new java.awt.Color(200, 255, 200));
                        case "Unpaid" ->
                            c.setBackground(new java.awt.Color(255, 255, 200));
                        case "Overdue" ->
                            c.setBackground(new java.awt.Color(255, 200, 200));
                        default ->
                            c.setBackground(java.awt.Color.WHITE);
                    }
                }

                return c;
            }
        });
    }

    // role permissions
    private void applyRolePermissions() {
        // SalesStaff → View only
        if ("SalesStaff".equalsIgnoreCase(currentRole)) {
            btmMarkAsPaid.setEnabled(false);
        }
    }

    // Loads installment payments for one specific hire contract and displays them in a JTable
    private void loadPaymentsByContract() {
        String sql
                = "SELECT "
                + "ip.InstallmentNo AS InstallmentNo, "
                + "ip.PaymentID, ip.ContractID, "
                + "c.FirstName + ' ' + c.LastName AS CustomerName, "
                + "ip.Amount, ip.DueDate, "
                + "CASE "
                + "  WHEN ip.IsPaid = 1 THEN 'Paid' "
                + "  WHEN ip.IsPaid = 0 AND ip.DueDate < GETDATE() THEN 'Overdue' "
                + "  ELSE 'Unpaid' "
                + "END AS Status, "
                + "CASE "
                + "  WHEN ip.IsPaid = 1 THEN pm.MethodName "
                + "  ELSE NULL "
                + "END AS PaymentMethod "
                + "FROM InstallmentPayment ip "
                + "JOIN HireContract hc ON ip.ContractID = hc.ContractID "
                + "JOIN Customer c ON hc.CustomerID = c.CustomerID "
                + "LEFT JOIN PaymentMethod pm ON ip.PaymentMethodID = pm.PaymentMethodID "
                + "WHERE ip.ContractID = ?";

        DatabaseHelper.fillTable(jtable1, sql, contractId);
    }

    private void loadPaymentMethods() {
        try {
            jComboBox1.removeAllItems();
            paymentMethodMap.clear();

            jComboBox1.addItem("Select Payment Method");

            String sql = "SELECT PaymentMethodID, MethodName FROM PaymentMethod";
            var rs = DatabaseHelper.executeQuery(sql);

            while (rs.next()) {
                int id = rs.getInt("PaymentMethodID");
                String name = rs.getString("MethodName");

                paymentMethodMap.put(name, id);
                jComboBox1.addItem(name);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading payment methods");
        }
    }

    private void loadPayments() {
        String sql
                = "SELECT "
                + "ROW_NUMBER() OVER (PARTITION BY ip.ContractID ORDER BY ip.DueDate) AS InstallmentNo, "
                + "ip.PaymentID, ip.ContractID, "
                + "c.FirstName + ' ' + c.LastName AS CustomerName, "
                + "ip.Amount, ip.DueDate, "
                + "CASE "
                + "  WHEN ip.IsPaid = 1 THEN 'Paid' "
                + "  WHEN ip.IsPaid = 0 AND ip.DueDate < GETDATE() THEN 'Overdue' "
                + "  ELSE 'Unpaid' "
                + "END AS Status, "
                + "CASE "
                + "  WHEN ip.IsPaid = 1 THEN pm.MethodName "
                + "  ELSE NULL "
                + "END AS PaymentMethod "
                + "FROM InstallmentPayment ip "
                + "JOIN HireContract hc ON ip.ContractID = hc.ContractID "
                + "JOIN Customer c ON hc.CustomerID = c.CustomerID "
                + "LEFT JOIN PaymentMethod pm ON ip.PaymentMethodID = pm.PaymentMethodID";

        DatabaseHelper.fillTable(jtable1, sql);
        
        
    }
    
//private void checkOverdueWarnings() {
//    try {
//        var rs = DatabaseHelper.executeQuery(
//            "SELECT PaymentID, ContractID " +
//            "FROM InstallmentPayment " +
//            "WHERE IsPaid = 0 " +
//            "AND DueDate < GETDATE() " +
//            "AND WarningIssued = 0"
//        );
//
//        while (rs.next()) {
//            int paymentId = rs.getInt("PaymentID");
//            int cid = rs.getInt("ContractID");
//
//            // increase warning ONLY ONCE
//            DatabaseHelper.increaseWarning(cid);
//
//            // mark this installment as warned
//            DatabaseHelper.executeUpdate(
//                "UPDATE InstallmentPayment SET WarningIssued = 1 WHERE PaymentID = ?",
//                paymentId
//            );
//
//            int warnings = DatabaseHelper.getWarningCount(cid);
//
//            if (warnings >= 3) {
//                DatabaseHelper.repossessCar(cid);
//
//                JOptionPane.showMessageDialog(
//                    this,
//                    "Contract " + cid +
//                    " reached 3 warnings.\nCar has been repossessed."
//                );
//            }
//        }
//
//        rs.close();
//    } catch (Exception e) {
//        JOptionPane.showMessageDialog(this, "Error checking overdue warnings");
//    }
//}



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
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel1.setText("Manage Payments");
        jLabel1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 10, 190, 26));

        jtable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Installment", "Payment ID", "Contract ID", "Costomer Nmae", "Amount", "Due Date", "Status", "PaymentMethod"
            }
        ));
        jScrollPane1.setViewportView(jtable1);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 870, 430));

        btmMarkAsPaid.setText("Mark as Paid");
        btmMarkAsPaid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btmMarkAsPaidActionPerformed(evt);
            }
        });
        getContentPane().add(btmMarkAsPaid, new org.netbeans.lib.awtextra.AbsoluteConstraints(137, 520, 110, 80));

        btnShowPaid.setText("Show Paid");
        btnShowPaid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnShowPaidActionPerformed(evt);
            }
        });
        getContentPane().add(btnShowPaid, new org.netbeans.lib.awtextra.AbsoluteConstraints(262, 520, 110, 80));

        btnShowUnpaid.setText("Show Unpaid");
        btnShowUnpaid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnShowUnpaidActionPerformed(evt);
            }
        });
        getContentPane().add(btnShowUnpaid, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 520, -1, 80));

        btnShowOverdue.setText("Show Overdue");
        btnShowOverdue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnShowOverdueActionPerformed(evt);
            }
        });
        getContentPane().add(btnShowOverdue, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 520, 120, 80));

        btnrefrech.setText("Refresh");
        btnrefrech.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnrefrechActionPerformed(evt);
            }
        });
        getContentPane().add(btnrefrech, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 520, 80, 80));

        btnBack.setText("<- Back");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });
        getContentPane().add(btnBack, new org.netbeans.lib.awtextra.AbsoluteConstraints(17, 30, 80, -1));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        getContentPane().add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 30, 220, -1));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/WhatsApp Image 2025-12-04 at 6.19.13 PM.jpeg"))); // NOI18N
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 920, 640));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnShowPaidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShowPaidActionPerformed
        String sql
                = "SELECT "
                + "ROW_NUMBER() OVER (PARTITION BY ip.ContractID ORDER BY ip.DueDate) AS InstallmentNo, "
                + "ip.PaymentID, ip.ContractID, "
                + "c.FirstName + ' ' + c.LastName AS CustomerName, "
                + "ip.Amount, ip.DueDate, "
                + "'Paid' AS Status, "
                + "pm.MethodName AS PaymentMethod "
                + "FROM InstallmentPayment ip "
                + "JOIN HireContract hc ON ip.ContractID = hc.ContractID "
                + "JOIN Customer c ON hc.CustomerID = c.CustomerID "
                + "LEFT JOIN PaymentMethod pm ON ip.PaymentMethodID = pm.PaymentMethodID "
                + "WHERE ip.IsPaid = 1";

        if (contractId > 0)
            DatabaseHelper.fillTable(jtable1, sql + " AND ip.ContractID = ?", contractId);
        else
            DatabaseHelper.fillTable(jtable1, sql);
    }//GEN-LAST:event_btnShowPaidActionPerformed

    private void btnShowOverdueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShowOverdueActionPerformed
        String sql
                = "SELECT "
                + "ROW_NUMBER() OVER (PARTITION BY ip.ContractID ORDER BY ip.DueDate) AS InstallmentNo, "
                + "ip.PaymentID, ip.ContractID, "
                + "c.FirstName + ' ' + c.LastName AS CustomerName, "
                + "ip.Amount, ip.DueDate, "
                + "'Overdue' AS Status, "
                + "NULL AS PaymentMethod "
                + "FROM InstallmentPayment ip "
                + "JOIN HireContract hc ON ip.ContractID = hc.ContractID "
                + "JOIN Customer c ON hc.CustomerID = c.CustomerID "
                + "WHERE ip.IsPaid = 0 AND ip.DueDate < GETDATE()";

        if (contractId > 0) {
            DatabaseHelper.fillTable(jtable1, sql + " AND ip.ContractID = ?", contractId);
        } else {
            DatabaseHelper.fillTable(jtable1, sql);
        }
    }//GEN-LAST:event_btnShowOverdueActionPerformed

    private void btnrefrechActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnrefrechActionPerformed
        // TODO add your handling code here:
        if (contractId > 0)
            loadPaymentsByContract();
        else
            loadPayments();
        // checkOverdueWarnings();
    }//GEN-LAST:event_btnrefrechActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        if (openedFromContracts) {
            new ManageContractsFrame(currentRole).setVisible(true);
        } else {
            new MainMenuFrame(currentRole).setVisible(true);
        }
        this.dispose();
    }//GEN-LAST:event_btnBackActionPerformed

    private void btnShowUnpaidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShowUnpaidActionPerformed
        String sql
                = "SELECT "
                + "ROW_NUMBER() OVER (PARTITION BY ip.ContractID ORDER BY ip.DueDate) AS InstallmentNo, "
                + "ip.PaymentID, ip.ContractID, "
                + "c.FirstName + ' ' + c.LastName AS CustomerName, "
                + "ip.Amount, ip.DueDate, "
                + "'Unpaid' AS Status, "
                + "NULL AS PaymentMethod "
                + "FROM InstallmentPayment ip "
                + "JOIN HireContract hc ON ip.ContractID = hc.ContractID "
                + "JOIN Customer c ON hc.CustomerID = c.CustomerID "
                + "WHERE ip.IsPaid = 0 AND ip.DueDate >= GETDATE()";

        if (contractId > 0) {
            DatabaseHelper.fillTable(jtable1, sql + " AND ip.ContractID = ?", contractId);
        } else {
            DatabaseHelper.fillTable(jtable1, sql);
        }
    }//GEN-LAST:event_btnShowUnpaidActionPerformed

    private void btmMarkAsPaidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btmMarkAsPaidActionPerformed
        // Extra protection (even if button disabled)
        String selectedMethod = jComboBox1.getSelectedItem().toString();

        if (selectedMethod.equals("Select Payment Method")) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select a payment method before marking as paid."
            );
            return;
        }

        if ("SalesStaff".equalsIgnoreCase(currentRole)) {
            JOptionPane.showMessageDialog(this, "You are not allowed to update payments.");
            return;
        }

        int row = jtable1.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Please select a payment first.");
            return;
        }

        int paymentID = Integer.parseInt(jtable1.getValueAt(row, 1).toString());

        try {
            String methodName = jComboBox1.getSelectedItem().toString();
            int paymentMethodId = paymentMethodMap.get(methodName);

            DatabaseHelper.executeUpdate(
                    "UPDATE InstallmentPayment "
                    + "SET IsPaid = 1, PaymentDate = GETDATE(), PaymentMethodID = ? "
                    + "WHERE PaymentID = ?",
                    paymentMethodId,
                    paymentID
            );

            // خذ ContractID الحقيقي من الصف المختار (مهم إذا contractId = -1)
            int realContractId = Integer.parseInt(jtable1.getValueAt(row, 2).toString());

            // حدّث حالة العقد (Active/Late/Completed)
            DatabaseHelper.updateContractStatus(realContractId);

            // إذا ما بقي أقساط غير مدفوعة → انقل ملكية السيارة للزبون
            int unpaidCount = DatabaseHelper.getInt(
                    "SELECT COUNT(*) FROM InstallmentPayment WHERE ContractID=? AND IsPaid=0",
                    realContractId
            );

            if (unpaidCount == 0) {
                DatabaseHelper.executeUpdate(
                        "UPDATE Car SET OwnershipStatus='CustomerOwned' "
                        + "WHERE CarID=(SELECT CarID FROM HireContract WHERE ContractID=?)",
                        realContractId
                );
            }
            JOptionPane.showMessageDialog(this, "Payment marked as Paid!");

            if (contractId > 0) {
                loadPaymentsByContract();
            } else {
                loadPayments();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error updating payment: " + e.getMessage());
        }
    }//GEN-LAST:event_btmMarkAsPaidActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btmMarkAsPaid;
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnShowOverdue;
    private javax.swing.JButton btnShowPaid;
    private javax.swing.JButton btnShowUnpaid;
    private javax.swing.JButton btnrefrech;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jtable1;
    // End of variables declaration//GEN-END:variables
}
