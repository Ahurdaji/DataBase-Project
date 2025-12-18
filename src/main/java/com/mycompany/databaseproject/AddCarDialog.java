/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package com.mycompany.databaseproject;

import java.sql.ResultSet;
import javax.swing.JOptionPane;

/**
 *
 * @author hadalkharouf
 */
public class AddCarDialog extends javax.swing.JDialog {

    private boolean succeeded = false; // to tell parent if insert worked

    /**
     * Creates new form AddCarDialog
     */
    public AddCarDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(parent);
        loadModels();
        loadStatuses();
        loadSuppliers();
        loadLocations();

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

    private boolean plateExists(String plate) {
        String sql = "SELECT COUNT(*) FROM Car WHERE PlateNumber = ?";
        try (ResultSet rs = DatabaseHelper.executeQuery(sql, plate)) {
            if (rs.next()) {
                return rs.getInt(1) > 0; // true if exists
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean vinExists(String vin) {
        String sql = "SELECT COUNT(*) FROM Car WHERE VIN = ?";
        try (ResultSet rs = DatabaseHelper.executeQuery(sql, vin)) {
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean isValidVIN(String vin) {
        // بسيط: 10-17 حرف/رقم (اذا بدك strict خليه 17)
        return vin.matches("^[A-Za-z0-9]{9,17}$");
    }

    // ================= VALIDATION METHODS =================
    private boolean isYearInRange(int year) {
        int currentYear = java.time.LocalDate.now().getYear();
        return year >= 1900 && year <= currentYear + 1;
    }

    private boolean isValidMileage(String mileage) {
        return mileage.matches("^\\d+$");
    }

    private boolean isValidDate(String date) {
        return date.matches("^\\d{4}-\\d{2}-\\d{2}$");
    }

    private boolean isFutureDate(String dateText) {
        try {
            java.sql.Date d = java.sql.Date.valueOf(dateText);
            java.sql.Date today = new java.sql.Date(System.currentTimeMillis());
            return d.after(today);
        } catch (Exception e) {
            return true; // اعتبره خطأ
        }
    }

    private boolean isValidPrice(String price) {
        return price.matches("^\\d+(\\.\\d{1,2})?$");
    }

    public boolean isSucceeded() {
        return succeeded;
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
        txtMileage = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtVIN = new javax.swing.JTextField();
        txtColor = new javax.swing.JTextField();
        txtYear = new javax.swing.JTextField();
        txtPlate = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        cmbLocation = new javax.swing.JComboBox<>();
        cmbSupplier = new javax.swing.JComboBox<>();
        cmbStatus = new javax.swing.JComboBox<>();
        cmbModel = new javax.swing.JComboBox<>();
        btnSave = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtDate = new javax.swing.JTextField();
        txtPrice = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setText("VIN:");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, 60, -1));

        jLabel2.setText("Plate Number:");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 130, 90, -1));

        jLabel3.setText("Year:");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 160, 60, -1));

        jLabel4.setText("Color:");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 190, 60, -1));
        getContentPane().add(txtMileage, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 220, 130, -1));

        jLabel5.setText("Mileage:");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 220, 60, -1));
        getContentPane().add(txtVIN, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 100, 130, -1));
        getContentPane().add(txtColor, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 190, 130, -1));
        getContentPane().add(txtYear, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 160, 130, -1));
        getContentPane().add(txtPlate, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 130, 130, -1));

        jLabel6.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        jLabel6.setText("Car Details ");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 30, 100, -1));

        jLabel7.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        jLabel7.setText("Car Attributes");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 30, 130, -1));

        jLabel8.setText("Location:");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 190, 70, -1));

        jLabel9.setText("Supplier:");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 160, 80, -1));

        jLabel10.setText("Status:");
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 130, 70, -1));

        jLabel11.setText("Model:");
        getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 100, 70, -1));

        getContentPane().add(cmbLocation, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 190, 110, 20));

        getContentPane().add(cmbSupplier, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 160, 110, 20));

        getContentPane().add(cmbStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 130, 110, 20));

        cmbModel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbModelActionPerformed(evt);
            }
        });
        getContentPane().add(cmbModel, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 93, 110, 20));

        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });
        getContentPane().add(btnSave, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 360, -1, -1));

        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });
        getContentPane().add(btnCancel, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 360, -1, -1));
        getContentPane().add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 270, 290));
        getContentPane().add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 60, 260, 170));

        jLabel12.setText("Purchase Date:");
        getContentPane().add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 270, 90, -1));

        jLabel13.setText("Purchase Price:");
        getContentPane().add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 310, 90, -1));
        getContentPane().add(txtDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 270, 130, -1));
        getContentPane().add(txtPrice, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 310, 130, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        // TODO add your handling code here:
        // Read values
        String vin = txtVIN.getText().trim().toUpperCase();
        String plate = txtPlate.getText().trim().toUpperCase();
        String yearText = txtYear.getText().trim();
        String color = txtColor.getText().trim();
        String mileageText = txtMileage.getText().trim();
        String dateText = txtDate.getText().trim();
        String priceText = txtPrice.getText().trim();

        // 1) Required fields
        if (vin.isEmpty() || plate.isEmpty() || yearText.isEmpty()
                || color.isEmpty() || mileageText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all required fields.");
            return;
        }

        // 2) VIN format (simple)
        if (!vin.matches("^[A-Za-z0-9]{10,17}$")) {
            JOptionPane.showMessageDialog(this, "VIN must be 10-17 letters/numbers (no spaces).");
            return;
        }

        // 3) Year must be numeric
        if (!yearText.matches("\\d{4}")) {
            JOptionPane.showMessageDialog(this, "Year must be a 4-digit number.");
            return;
        }

        int year = Integer.parseInt(yearText);
        if (!isYearInRange(year)) {
            JOptionPane.showMessageDialog(this,
                    "Year must be between 1900 and " + (java.time.LocalDate.now().getYear() + 1));
            return;
        }

        // 4) Mileage
        if (!isValidMileage(mileageText)) {
            JOptionPane.showMessageDialog(this, "Mileage must be a number.");
            return;
        }
        int mileage = Integer.parseInt(mileageText);
        if (mileage < 0) {
            JOptionPane.showMessageDialog(this, "Mileage cannot be negative.");
            return;
        }

        // 5) Purchase Date (format then future)
        if (!dateText.isEmpty() && !isValidDate(dateText)) {
            JOptionPane.showMessageDialog(this, "Purchase Date must be in format yyyy-MM-dd.");
            return;
        }
        if (!dateText.isEmpty() && isFutureDate(dateText)) {
            JOptionPane.showMessageDialog(this, "Purchase Date cannot be in the future.");
            return;
        }

        // 6) Purchase Price (>0)
        Double purchasePrice = null;
        if (!priceText.isEmpty()) {
            if (!isValidPrice(priceText)) {
                JOptionPane.showMessageDialog(this, "Purchase Price must be a valid number.");
                return;
            }
            double p = Double.parseDouble(priceText);
            if (p <= 0) {
                JOptionPane.showMessageDialog(this, "Purchase Price must be greater than 0.");
                return;
            }
            purchasePrice = p;
        }

        // 7) Duplicate checks
        if (plateExists(plate)) {
            JOptionPane.showMessageDialog(this, "Plate number already exists!");
            return;
        }
        if (vinExists(vin)) {
            JOptionPane.showMessageDialog(this, "VIN already exists!");
            return;
        }

        // 8) ComboBoxes
        if (cmbModel.getSelectedItem() == null
                || cmbStatus.getSelectedItem() == null
                || cmbSupplier.getSelectedItem() == null
                || cmbLocation.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Please select Model, Status, Supplier and Location.");
            return;
        }

        // 9) Insert
        try {
            ComboItem modelItem = (ComboItem) cmbModel.getSelectedItem();
            ComboItem statusItem = (ComboItem) cmbStatus.getSelectedItem();

            if ("Sold".equalsIgnoreCase(statusItem.getName())) {
                JOptionPane.showMessageDialog(this, "You cannot add a car as Sold. Use Contracts to sell.");
                return;
            }
            
            ComboItem supplierItem = (ComboItem) cmbSupplier.getSelectedItem();
            ComboItem locationItem = (ComboItem) cmbLocation.getSelectedItem();

            java.sql.Date purchaseDate = null;
            if (!dateText.isEmpty()) {
                purchaseDate = java.sql.Date.valueOf(dateText);
            }

            String sql = "INSERT INTO Car "
                    + "(ModelID, VIN, PlateNumber, Year, Color, Mileage, "
                    + "PurchaseDate, PurchasePrice, "
                    + "StatusID, SupplierID, LocationID, OwnershipStatus) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            int rows = DatabaseHelper.executeUpdate(sql,
                    modelItem.getId(),
                    vin,
                    plate,
                    year,
                    color,
                    mileage,
                    purchaseDate,
                    purchasePrice,
                    statusItem.getId(),
                    supplierItem.getId(),
                    locationItem.getId(),
                    "CompanyOwned"
            );

            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Car added successfully.");
                succeeded = true;
                dispose();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving car: " + ex.getMessage());
        }

    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        // TODO add your handling code here:
        succeeded = false;
        dispose();
    }//GEN-LAST:event_btnCancelActionPerformed

    private void cmbModelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbModelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbModelActionPerformed

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
    private javax.swing.JLabel jLabel13;
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
    private javax.swing.JTextField txtColor;
    private javax.swing.JTextField txtDate;
    private javax.swing.JTextField txtMileage;
    private javax.swing.JTextField txtPlate;
    private javax.swing.JTextField txtPrice;
    private javax.swing.JTextField txtVIN;
    private javax.swing.JTextField txtYear;
    // End of variables declaration//GEN-END:variables
}
