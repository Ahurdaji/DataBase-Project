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

        buttonGroup1.add(radiobtndirectpay);
        buttonGroup1.add(radiobtnIntallPay);

        radiobtndirectpay.setSelected(true);

        txtcontractDate.setText(java.time.LocalDate.now().toString());
        txtcontractDate.setEnabled(false);

// Disable installment fields by default
        jTextField1.setEnabled(false);
        jComboBox1.setEnabled(false);

// Enable / disable based on selection
        radiobtnIntallPay.addActionListener(e -> {
            jTextField1.setEnabled(true);
            jComboBox1.setEnabled(true);
        });

        radiobtndirectpay.addActionListener(e -> {
            jTextField1.setEnabled(false);
            jComboBox1.setEnabled(false);
        });

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

//HashMap is used to store the relationship between displayed values and primary keys
    private HashMap<String, Integer> customerMap = new HashMap<>();
    private HashMap<String, Integer> carMap = new HashMap<>();

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        cmbCustomer = new javax.swing.JComboBox<>();
        cmbCar = new javax.swing.JComboBox<>();
        txtcontractDate = new javax.swing.JTextField();
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
        jLabel3 = new javax.swing.JLabel();
        radiobtndirectpay = new javax.swing.JRadioButton();
        radiobtnIntallPay = new javax.swing.JRadioButton();
        jLabel4 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtdeliveryDate = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("New Contract");
        setModal(true);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        cmbCustomer.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Customer" }));
        getContentPane().add(cmbCustomer, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 100, 120, -1));

        cmbCar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Car" }));
        getContentPane().add(cmbCar, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 160, 120, -1));
        getContentPane().add(txtcontractDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 220, 120, -1));
        getContentPane().add(txtTotalAmount, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 330, 120, -1));

        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });
        getContentPane().add(btnSave, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 550, -1, -1));

        btnCancle.setText("Cancle");
        btnCancle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancleActionPerformed(evt);
            }
        });
        getContentPane().add(btnCancle, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 550, -1, -1));

        lblcustomer.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblcustomer.setText("Customer :");
        getContentPane().add(lblcustomer, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 100, -1, -1));

        lblCar.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblCar.setText("Car :");
        getContentPane().add(lblCar, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 160, -1, -1));

        lblStartdate.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblStartdate.setText("Contract Date:");
        getContentPane().add(lblStartdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 220, -1, -1));

        lblTotalamount.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblTotalamount.setText("Total Amount :");
        getContentPane().add(lblTotalamount, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 340, -1, -1));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jLabel1.setText("YYYY-MM-DD");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 300, 110, 20));
        getContentPane().add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 50, 60, 540));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("New Contract");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 30, 100, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Payment type :");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 390, -1, -1));

        buttonGroup1.add(radiobtndirectpay);
        radiobtndirectpay.setText("Direct Payment");
        getContentPane().add(radiobtndirectpay, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 380, -1, -1));

        buttonGroup1.add(radiobtnIntallPay);
        radiobtnIntallPay.setText("Installment Payment");
        getContentPane().add(radiobtnIntallPay, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 420, -1, -1));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Installment Duration:");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 480, -1, -1));
        getContentPane().add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 480, 100, -1));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Years", "Months", " " }));
        getContentPane().add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 480, -1, -1));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jLabel5.setText("Installments are calculated monthly");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 510, 160, -1));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setText("Delivery Date:");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 280, -1, -1));
        getContentPane().add(txtdeliveryDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 280, 120, -1));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jLabel7.setText("YYYY-MM-DD");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 240, 110, 20));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed

        try {
            // 1️⃣ Read basic input
            String customerName = cmbCustomer.getSelectedItem().toString();
            String carPlate = cmbCar.getSelectedItem().toString();
            String totalAmountText = txtTotalAmount.getText().trim();

            int customerId = customerMap.get(customerName);
            int carId = carMap.get(carPlate);

//            ResultSet rs = DatabaseHelper.executeQuery(
//                    "SELECT OwnershipStatus FROM Car WHERE CarID = ?", carId
//            );
//
//            if (rs.next()) {
//                String status = rs.getString("OwnershipStatus");
//
//                if (!status.equals("CompanyOwned")) {
//                    JOptionPane.showMessageDialog(
//                            this,
//                            "This car is not available for sale.\nCurrent status: " + status
//                    );
//                    return;
//                }
//            } else {
//                JOptionPane.showMessageDialog(this, "Car not found.");
//                rs.close();
//                return;
//            }
//
//            rs.close();

            double totalAmount = Double.parseDouble(totalAmountText);
            // Validate total amount (car price)
            if (totalAmount < 20000) {
                JOptionPane.showMessageDialog(
                        this,
                        "Total amount must be at least 20,000.\nPlease enter a valid car price."
                );
                return;
            }

            String deliveryDateText = txtdeliveryDate.getText().trim();

            if (deliveryDateText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter the delivery date.");
                return;
            }

            Date deliveryDate = Date.valueOf(deliveryDateText);
            Date today = Date.valueOf(java.time.LocalDate.now());

// Validate delivery date
            if (deliveryDate.before(today)) {
                JOptionPane.showMessageDialog(
                        this,
                        "Delivery date cannot be in the past.\nPlease select today or a future date."
                );
                return;
            }

            // 2️⃣ Detect payment type
            boolean isInstallment = radiobtnIntallPay.isSelected();

            // 3️⃣ Calculate months FIRST
            int months = 1;

            if (isInstallment) {
                if (jTextField1.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please enter installment duration.");
                    return;
                }

                int value = Integer.parseInt(jTextField1.getText().trim());

                if (value <= 0) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Installment duration must be greater than 0."
                    );
                    return;
                }

                String unit = jComboBox1.getSelectedItem().toString();

                if (unit.equalsIgnoreCase("Years")) {
                    months = value * 12;
                } else {
                    months = value;
                }

            }

            // 4️⃣ Insert contract
            String contractType = isInstallment ? "Installment" : "Direct";
            int statusId = isInstallment ? 1 : 2;

            String contractSql
                    = "INSERT INTO HireContract "
                    + "(CustomerID, CarID, StartDate, TotalAmount, StatusID, ContractType, InstallmentCount) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?)";

            int contractId = DatabaseHelper.executeInsertAndReturnId(
                    contractSql,
                    customerId,
                    carId,
                    deliveryDate,
                    totalAmount,
                    statusId,
                    contractType,
                    months
            );

// 5️⃣ Handle payments
            if (isInstallment) {

                // Installment payments
                double monthlyAmount = totalAmount / months;
                int defaultPaymentMethodId = 1;

                String paymentSql
                        = "INSERT INTO InstallmentPayment "
                        + "(ContractID, InstallmentNo, Amount, DueDate, IsPaid, PaymentMethodID) "
                        + "VALUES (?, ?, ?, ?, 0, ?)";

                for (int i = 1; i <= months; i++) {
                    Date dueDate = Date.valueOf(deliveryDate.toLocalDate().plusMonths(i));

                    DatabaseHelper.executeUpdate(
                            paymentSql,
                            contractId,
                            i,
                            monthlyAmount,
                            dueDate,
                            defaultPaymentMethodId
                    );
                }

            } else {

                //  DIRECT PAYMENT = ONE PAID RECORD
                int defaultPaymentMethodId = 1;

                DatabaseHelper.executeUpdate(
                        "INSERT INTO InstallmentPayment "
                        + "(ContractID, InstallmentNo, Amount, DueDate, IsPaid, PaymentMethodID, PaymentDate) "
                        + "VALUES (?, 1, ?, ?, 1, ?, GETDATE())",
                        contractId,
                        totalAmount,
                        deliveryDate,
                        defaultPaymentMethodId
                );

            }

            int SOLD_ID = 0;
            ResultSet rsSold = DatabaseHelper.executeQuery(
                    "SELECT StatusID FROM CarStatus WHERE StatusName = 'Sold'"
            );
            if (rsSold.next()) {
                SOLD_ID = rsSold.getInt(1);
            }


            JOptionPane.showMessageDialog(this, "Contract created successfully!");
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
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cmbCar;
    private javax.swing.JComboBox<String> cmbCustomer;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel lblCar;
    private javax.swing.JLabel lblStartdate;
    private javax.swing.JLabel lblTotalamount;
    private javax.swing.JLabel lblcustomer;
    private javax.swing.JRadioButton radiobtnIntallPay;
    private javax.swing.JRadioButton radiobtndirectpay;
    private javax.swing.JTextField txtTotalAmount;
    private javax.swing.JTextField txtcontractDate;
    private javax.swing.JTextField txtdeliveryDate;
    // End of variables declaration//GEN-END:variables
}
