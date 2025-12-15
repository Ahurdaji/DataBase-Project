
package com.mycompany.databaseproject;

import java.sql.Date;
import java.sql.ResultSet;
import java.util.HashMap;
import javax.swing.JOptionPane;

/**
 *
 * @author HP
 */
public class NewContractDialog extends javax.swing.JDialog {

    /**
     * Creates new form NewContractDialog
     */
public NewContractDialog(java.awt.Frame parent, boolean modal) {
    super(parent, modal);
    initComponents();
    loadCustomers();
    loadCars();
    setLocationRelativeTo(parent);
}

private void loadCustomers() {
    try {
        cmbCustomer.removeAllItems();

        String sql = "SELECT CustomerID, FirstName, LastName FROM Customer";
        ResultSet rs = DatabaseHelper.executeQuery(sql);

        while (rs.next()) {
            String name = rs.getString("FirstName") + " " + rs.getString("LastName");
            int id = rs.getInt("CustomerID");

            customerMap.put(name, id);
            cmbCustomer.addItem(name);
        }

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error loading customers: " + e.getMessage());
    }
}

private void loadCars() {
    try {
        cmbCar.removeAllItems();

        String sql = "SELECT CarID, PlateNumber FROM Car";
        ResultSet rs = DatabaseHelper.executeQuery(sql);

        while (rs.next()) {
            String plate = rs.getString("PlateNumber");
            int id = rs.getInt("CarID");

            carMap.put(plate, id);
            cmbCar.addItem(plate);
        }

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error loading cars: " + e.getMessage());
    }
}


    private HashMap<String, Integer> customerMap = new HashMap<>();
private HashMap<String, Integer> carMap = new HashMap<>();

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cmbCustomer = new javax.swing.JComboBox<>();
        cmbCar = new javax.swing.JComboBox<>();
        txtStartDate = new javax.swing.JTextField();
        txtTotalAmount = new javax.swing.JTextField();
        btnSave = new javax.swing.JButton();
        btnCancle = new javax.swing.JButton();
        lblcustomer = new javax.swing.JLabel();
        lblCar = new javax.swing.JLabel();
        lblStartdate = new javax.swing.JLabel();
        lblTotalamount = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("New Contract");
        setModal(true);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        cmbCustomer.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Customer" }));
        getContentPane().add(cmbCustomer, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 100, 90, -1));

        cmbCar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Car" }));
        getContentPane().add(cmbCar, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 160, 90, -1));
        getContentPane().add(txtStartDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 220, 90, -1));
        getContentPane().add(txtTotalAmount, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 280, 90, -1));

        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });
        getContentPane().add(btnSave, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 350, -1, -1));

        btnCancle.setText("Cancle");
        btnCancle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancleActionPerformed(evt);
            }
        });
        getContentPane().add(btnCancle, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 350, -1, -1));

        lblcustomer.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblcustomer.setText("Customer :");
        getContentPane().add(lblcustomer, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 100, -1, -1));

        lblCar.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblCar.setText("Car :");
        getContentPane().add(lblCar, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 160, -1, -1));

        lblStartdate.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblStartdate.setText("Start Date :");
        getContentPane().add(lblStartdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 220, -1, -1));

        lblTotalamount.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblTotalamount.setText("Total Amount :");
        getContentPane().add(lblTotalamount, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 280, -1, -1));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jLabel1.setText("YYYY-MM-DD");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 240, 80, 20));
        getContentPane().add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, 280, 30));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("New Contract");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 30, 100, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed

    try {
        // 1. Read input
        String customerName = cmbCustomer.getSelectedItem().toString();
        String carPlate = cmbCar.getSelectedItem().toString();
        String startDateText = txtStartDate.getText().trim();
        String totalAmountText = txtTotalAmount.getText().trim();

        int customerId = customerMap.get(customerName);
        int carId = carMap.get(carPlate);
        double totalAmount = Double.parseDouble(totalAmountText);
        Date startDate = Date.valueOf(startDateText);

        // 2. Insert contract
        String contractSql =
            "INSERT INTO HireContract (CustomerID, CarID, StartDate, TotalAmount, StatusID) " +
            "VALUES (?, ?, ?, ?, ?)";

        int statusId = 1;

        int contractId = DatabaseHelper.executeInsertAndReturnId(
            contractSql,
            customerId,
            carId,
            startDate,
            totalAmount,
            statusId
        );

        // 3. INSERT INSTALLMENTS  (THIS IS THE IMPORTANT PART)
        int months = 12;
        double monthlyAmount = totalAmount / months;
        int defaultPaymentMethodId = 1;

        String paymentSql =
            "INSERT INTO InstallmentPayment " +
            "(ContractID, Amount, DueDate, IsPaid, PaymentMethodID) " +
            "VALUES (?, ?, ?, 0, ?)";

        for (int i = 1; i <= months; i++) {
            Date dueDate = Date.valueOf(startDate.toLocalDate().plusMonths(i));

            DatabaseHelper.executeUpdate(
                paymentSql,
                contractId,
                monthlyAmount,
                dueDate,
                defaultPaymentMethodId
            );
        }

        JOptionPane.showMessageDialog(this, "Contract created with installments!");
        dispose();

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Please fill all fields correctly:\n" + e.getMessage());
    }



    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnCancleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancleActionPerformed
        // TODO add your handling code here:
            dispose();
    }//GEN-LAST:event_btnCancleActionPerformed

    /**
     * @param args the command line arguments
     */
 

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancle;
    private javax.swing.JButton btnSave;
    private javax.swing.JComboBox<String> cmbCar;
    private javax.swing.JComboBox<String> cmbCustomer;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblCar;
    private javax.swing.JLabel lblStartdate;
    private javax.swing.JLabel lblTotalamount;
    private javax.swing.JLabel lblcustomer;
    private javax.swing.JTextField txtStartDate;
    private javax.swing.JTextField txtTotalAmount;
    // End of variables declaration//GEN-END:variables
}
