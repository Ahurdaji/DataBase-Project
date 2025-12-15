/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package com.mycompany.databaseproject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author hadalkharouf
 */
public class EditCarDialog extends javax.swing.JDialog {

    private int carID;
    private boolean succeeded = false;

    public boolean isSucceeded() {
        return succeeded;
    }

    /**
     * Creates new form EditCarDialog
     */
    public EditCarDialog(java.awt.Frame parent, boolean modal, int carID) {
        super(parent, modal);
        initComponents();
        this.carID = carID;
        setLocationRelativeTo(parent);

        loadModels();
        loadStatuses();
        loadSuppliers();
        loadLocations();
        loadCarDetails();
        txtVIN.setEditable(false);
        txtPlate.setEditable(false);
        cmbModel.setEnabled(false);
        txtYear.setEditable(false);

    }

    private void loadCarDetails() {
        String sql = """
            SELECT c.*, s.StatusName
            FROM Car c
            JOIN CarStatus s ON c.StatusID = s.StatusID
            WHERE c.CarID = ?
        """;
        try (ResultSet rs = DatabaseHelper.executeQuery(sql, carID)) {
            if (rs.next()) {
                txtVIN.setText(rs.getString("VIN"));
                txtPlate.setText(rs.getString("PlateNumber"));
                txtYear.setText(rs.getString("Year"));
                txtColor.setText(rs.getString("Color"));
                txtMileage.setText(rs.getString("Mileage"));

                // Set combo boxes
                selectComboItem(cmbModel, rs.getInt("ModelID"));
                selectComboItem(cmbStatus, rs.getInt("StatusID"));
                selectComboItem(cmbSupplier, rs.getInt("SupplierID"));
                selectComboItem(cmbLocation, rs.getInt("LocationID"));

                String status = rs.getString("StatusName");
                applyStatusRules(status);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void applyStatusRules(String status) {

        if (status.equalsIgnoreCase("Sold")) {
            txtVIN.setEnabled(false);
            cmbModel.setEnabled(false);
            cmbSupplier.setEnabled(false);
            cmbLocation.setEnabled(false);
        }

        if (status.equalsIgnoreCase("Retired")) {
            txtVIN.setEnabled(false);
            txtPlate.setEnabled(false);
            txtYear.setEnabled(false);
            txtColor.setEnabled(false);
            txtMileage.setEnabled(false);

            cmbModel.setEnabled(false);
            cmbStatus.setEnabled(false);
            cmbSupplier.setEnabled(false);
            cmbLocation.setEnabled(false);

            btnSave.setEnabled(false);
        }
    }

    
    private void selectComboItem(javax.swing.JComboBox<ComboItem> combo, int id) {
        for (int i = 0; i < combo.getItemCount(); i++) {
            ComboItem item = combo.getItemAt(i);
            if (item.getId() == id) {
                combo.setSelectedIndex(i);
                break;
            }
        }
    }

    private void loadModels() {
        String sql = "SELECT ModelID, ModelName FROM CarModel";
        try (ResultSet rs = DatabaseHelper.executeQuery(sql)) {
            cmbModel.removeAllItems();
            while (rs.next()) {
                int id = rs.getInt("ModelID");
                String name = rs.getString("ModelName");
                cmbModel.addItem(new ComboItem(id, name));
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading models.");
        }
    }

    private void loadStatuses() {
        String sql = "SELECT StatusID, StatusName FROM CarStatus";
        try (ResultSet rs = DatabaseHelper.executeQuery(sql)) {
            cmbStatus.removeAllItems();
            while (rs.next()) {
                int id = rs.getInt("StatusID");
                String name = rs.getString("StatusName");
                cmbStatus.addItem(new ComboItem(id, name));
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading statuses.");
        }
    }

    private void loadSuppliers() {
        String sql = "SELECT SupplierID, SupplierName FROM Supplier";
        try (ResultSet rs = DatabaseHelper.executeQuery(sql)) {
            cmbSupplier.removeAllItems();
            while (rs.next()) {
                int id = rs.getInt("SupplierID");
                String name = rs.getString("SupplierName");
                cmbSupplier.addItem(new ComboItem(id, name));
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading suppliers.");
        }
    }

    private void loadLocations() {
        String sql = "SELECT LocationID, LocationName FROM Location";
        try (ResultSet rs = DatabaseHelper.executeQuery(sql)) {
            cmbLocation.removeAllItems();
            while (rs.next()) {
                int id = rs.getInt("LocationID");
                String name = rs.getString("LocationName");
                cmbLocation.addItem(new ComboItem(id, name));
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading locations.");
        }
    }

    // ================= VALIDATION METHODS =================
    private boolean isValidYear(String year) {
        return year.matches("^(19|20)\\d{2}$");
    }

    private boolean isValidMileage(String mileage) {
        return mileage.matches("^\\d+$");
    }

    private boolean isValidDate(String date) {
        return date.matches("^\\d{4}-\\d{2}-\\d{2}$");
    }

    private boolean isValidPrice(String price) {
        return price.matches("^\\d+(\\.\\d{1,2})?$");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtMileage = new javax.swing.JTextField();
        txtColor = new javax.swing.JTextField();
        txtYear = new javax.swing.JTextField();
        txtPlate = new javax.swing.JTextField();
        txtVIN = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        cmbLocation = new javax.swing.JComboBox<>();
        cmbSupplier = new javax.swing.JComboBox<>();
        cmbStatus = new javax.swing.JComboBox<>();
        cmbModel = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        btnCancel = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtDate = new javax.swing.JTextField();
        txtPrice = new javax.swing.JTextField();
        jSeparator3 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setText("Mileage");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 280, -1, -1));

        jLabel2.setText("Color");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 240, -1, -1));

        jLabel3.setText("Year");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 200, -1, -1));

        jLabel4.setText("Plate Number");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 130, -1, -1));

        jLabel5.setText("VIN");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 90, -1, -1));
        getContentPane().add(txtMileage, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 270, 180, 20));
        getContentPane().add(txtColor, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 230, 180, 20));
        getContentPane().add(txtYear, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 190, 180, 20));
        getContentPane().add(txtPlate, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 120, 180, 20));
        getContentPane().add(txtVIN, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 80, 180, 20));

        jLabel6.setText("Location");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 420, 70, 20));

        jLabel7.setText("Supplier");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 380, 60, 20));

        jLabel8.setText("Status");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 340, 60, 20));

        jLabel9.setText("Model");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 160, 60, 20));

        getContentPane().add(cmbLocation, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 420, 180, 20));

        getContentPane().add(cmbSupplier, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 380, 180, -1));

        getContentPane().add(cmbStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 340, 180, 20));

        getContentPane().add(cmbModel, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 160, 180, 20));

        jLabel10.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Edit Car ");
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 10, 80, 40));

        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });
        getContentPane().add(btnCancel, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 580, -1, -1));

        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });
        getContentPane().add(btnSave, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 580, -1, -1));
        getContentPane().add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 360, 250));
        getContentPane().add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 320, 360, 130));

        jLabel11.setText("Purchase Date");
        getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 490, -1, -1));

        jLabel12.setText("Purchase Price");
        getContentPane().add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 530, -1, -1));
        getContentPane().add(txtDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 490, 180, -1));
        getContentPane().add(txtPrice, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 530, 180, -1));
        getContentPane().add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 460, 370, 110));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed

        // TODO add your handling code here:
        String vin = txtVIN.getText().trim(); // read only
        String plate = txtPlate.getText().trim(); // read only
        String yearText = txtYear.getText().trim();
        String color = txtColor.getText().trim();
        String mileageText = txtMileage.getText().trim();
        String dateText = txtDate.getText().trim();
        String priceText = txtPrice.getText().trim();

        // 2️⃣ Required fields
        if (yearText.isEmpty() || color.isEmpty() || mileageText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all required fields.");
            return;
        }

        // 3️⃣ Year validation
        if (!isValidYear(yearText)) {
            JOptionPane.showMessageDialog(this,
                    "Year must be between 1900 and 2099.");
            return;
        }

        // 4️⃣ Mileage validation
        if (!isValidMileage(mileageText)) {
            JOptionPane.showMessageDialog(this,
                    "Mileage must be a number.");
            return;
        }

        // 5️⃣ Purchase Date (optional)
        if (!dateText.isEmpty() && !isValidDate(dateText)) {
            JOptionPane.showMessageDialog(this,
                    "Purchase Date must be yyyy-MM-dd.");
            return;
        }

        // 6️⃣ Purchase Price (optional)
        if (!priceText.isEmpty() && !isValidPrice(priceText)) {
            JOptionPane.showMessageDialog(this,
                    "Purchase Price must be a valid number.");
            return;
        }

        // 7️⃣ Convert numbers
        int year = Integer.parseInt(yearText);
        int mileage = Integer.parseInt(mileageText);

        // 8️⃣ ComboBoxes check
        if (cmbModel.getSelectedItem() == null
                || cmbStatus.getSelectedItem() == null
                || cmbSupplier.getSelectedItem() == null
                || cmbLocation.getSelectedItem() == null) {

            JOptionPane.showMessageDialog(this,
                    "Please select Model, Status, Supplier and Location.");
            return;
        }

        // 9️⃣ Update DB
        try {
            ComboItem modelItem = (ComboItem) cmbModel.getSelectedItem();
            ComboItem statusItem = (ComboItem) cmbStatus.getSelectedItem();
            ComboItem supplierItem = (ComboItem) cmbSupplier.getSelectedItem();
            ComboItem locationItem = (ComboItem) cmbLocation.getSelectedItem();

            String sql = "UPDATE Car SET "
                    + "Year = ?, "
                    + "Color = ?, "
                    + "Mileage = ?, "
                    + "PurchaseDate = ? ,"
                    + "PurchasePrice = ?, "
                    + "ModelID = ?, "
                    + "StatusID = ?, "
                    + "SupplierID = ?, "
                    + "LocationID = ? "
                    + "WHERE CarID = ?";

            java.sql.Date purchaseDate = null;
            if (!dateText.isEmpty()) {
                purchaseDate = java.sql.Date.valueOf(dateText);
            }

            Double purchasePrice = null;
            if (!priceText.isEmpty()) {
                purchasePrice = Double.parseDouble(priceText);
            }

            int rows = DatabaseHelper.executeUpdate(sql,
                    year,
                    color,
                    mileage,
                    purchaseDate,
                    purchasePrice,
                    modelItem.getId(),
                    statusItem.getId(),
                    supplierItem.getId(),
                    locationItem.getId(),
                    carID
            );

            if (rows > 0) {
                succeeded = true;
                JOptionPane.showMessageDialog(this,
                        "Car updated successfully!");
                dispose();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Error updating car.");
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        // TODO add your handling code here:
        succeeded = false;
        dispose();
    }//GEN-LAST:event_btnCancelActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnSave;
    private javax.swing.JComboBox<ComboItem> cmbLocation;
    private javax.swing.JComboBox<ComboItem> cmbModel;
    private javax.swing.JComboBox<ComboItem> cmbStatus;
    private javax.swing.JComboBox<ComboItem> cmbSupplier;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JTextField txtColor;
    private javax.swing.JTextField txtDate;
    private javax.swing.JTextField txtMileage;
    private javax.swing.JTextField txtPlate;
    private javax.swing.JTextField txtPrice;
    private javax.swing.JTextField txtVIN;
    private javax.swing.JTextField txtYear;
    // End of variables declaration//GEN-END:variables
}
