/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package com.mycompany.databaseproject;

import java.sql.ResultSet;
import javax.swing.JOptionPane;

public class EditAssetDialog extends javax.swing.JDialog {

    private int assetId;
    private String currentStatus;

    public EditAssetDialog(java.awt.Frame parent, boolean modal, int assetId) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(parent);
        txtAssetName.setEditable(false);
        txtCategory.setEditable(false);
        this.assetId = assetId;
        loadLocations();
        loadAsset(); // load status + apply rules correctly
    }

    private void loadAsset() {
        String sql
                = "SELECT fa.AssetName, ac.CategoryName, ast.StatusName, "
                + "fa.LocationID, fa.Quantity, fa.Notes "
                + "FROM FixedAsset fa "
                + "JOIN AssetCategory ac ON fa.CategoryID = ac.CategoryID "
                + "JOIN AssetStatus ast ON fa.StatusID = ast.StatusID "
                + "WHERE fa.AssetID = ?";
        try (ResultSet rs = DatabaseHelper.executeQuery(sql, assetId)) {
            if (!rs.next()) {
                JOptionPane.showMessageDialog(this, "Asset not found.");
                dispose();
                return;
            }
            txtAssetName.setText(rs.getString("AssetName"));
            txtCategory.setText(rs.getString("CategoryName"));
            txtNotes.setText(rs.getString("Notes"));
            currentStatus = rs.getString("StatusName");
            spnQuantity.setValue(rs.getInt("Quantity"));

            Integer locationId = rs.getObject("LocationID") == null
                    ? null
                    : rs.getInt("LocationID");
            if (locationId != null) {
                for (int i = 0; i < cmbLocation.getItemCount(); i++) {
                    ComboItem item = cmbLocation.getItemAt(i);
                    if (item != null && item.getId() == locationId) {
                        cmbLocation.setSelectedIndex(i);
                        break;
                    }
                }
            }

            loadStatusesForEdit(currentStatus);
            applyEditRules();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error loading asset: " + e.getMessage());
            dispose();
        }
    }

    private boolean isEditableStatus(String status) {
        return "In Stock".equals(status)
                || "In Use".equals(status)
                || "Under Maintenance".equals(status);
    }

    // edit rules
    private void applyEditRules() {
        boolean editable = isEditableStatus(currentStatus);
        boolean quantityEditable = "In Stock".equals(currentStatus);

        cmbStatus.setEnabled(editable);
        cmbLocation.setEnabled(editable);
        txtNotes.setEditable(editable);
        btnSave.setEnabled(editable);

        spnQuantity.setEnabled(quantityEditable);
        if (!editable) {
            JOptionPane.showMessageDialog(this,
                    "This asset cannot be edited because its status is: " + currentStatus,
                    "Edit Not Allowed",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    // load combos
    private void loadStatusesForEdit(String currentStatus) {
        cmbStatus.removeAllItems();
        // If editing not allowed, do not load statuses
        if (!isEditableStatus(currentStatus)) {
            return;
        }
        try (ResultSet rs = DatabaseHelper.executeQuery(
                "SELECT StatusID, StatusName FROM AssetStatus")) {
            while (rs.next()) {
                String statusName = rs.getString("StatusName");
                boolean allowed = false;

                switch (currentStatus) {
                    case "In Stock":
                        allowed = statusName.equals("In Stock")
                                || statusName.equals("In Use")
                                || statusName.equals("Under Maintenance")
                                || statusName.equals("Damaged");
                        break;

                    case "In Use":
                        allowed = statusName.equals("In Use")
                                || statusName.equals("In Stock")
                                || statusName.equals("Under Maintenance")
                                || statusName.equals("Damaged");
                        break;

                    case "Under Maintenance":
                        allowed = statusName.equals("Under Maintenance")
                                || statusName.equals("In Stock")
                                || statusName.equals("In Use")
                                || statusName.equals("Damaged");
                        break;
                }
                if (allowed) {
                    cmbStatus.addItem(new ComboItem(
                            rs.getInt("StatusID"),
                            statusName
                    ));
                }
            }

            // Select current status
            for (int i = 0; i < cmbStatus.getItemCount(); i++) {
                if (cmbStatus.getItemAt(i).toString().equals(currentStatus)) {
                    cmbStatus.setSelectedIndex(i);
                    break;
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error loading statuses: " + e.getMessage());
        }
    }

    private void loadLocations() {
        cmbLocation.addItem(null);

        try (ResultSet rs = DatabaseHelper.executeQuery(
                "SELECT LocationID, LocationName FROM Location")) {

            while (rs.next()) {
                cmbLocation.addItem(new ComboItem(
                        rs.getInt("LocationID"),
                        rs.getString("LocationName")
                ));
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error loading locations: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txtAssetName = new javax.swing.JTextField();
        txtCategory = new javax.swing.JTextField();
        cmbStatus = new javax.swing.JComboBox<>();
        cmbLocation = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtNotes = new javax.swing.JTextArea();
        btnSave = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        spnQuantity = new javax.swing.JSpinner();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Edit Asset ");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(111, 24, 149, -1));
        getContentPane().add(txtAssetName, new org.netbeans.lib.awtextra.AbsoluteConstraints(163, 78, 123, -1));
        getContentPane().add(txtCategory, new org.netbeans.lib.awtextra.AbsoluteConstraints(163, 118, 123, -1));

        getContentPane().add(cmbStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(163, 158, 123, -1));

        getContentPane().add(cmbLocation, new org.netbeans.lib.awtextra.AbsoluteConstraints(163, 198, 123, -1));

        txtNotes.setColumns(20);
        txtNotes.setRows(5);
        jScrollPane1.setViewportView(txtNotes);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(95, 282, 280, 101));

        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });
        getContentPane().add(btnSave, new org.netbeans.lib.awtextra.AbsoluteConstraints(271, 442, -1, -1));

        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });
        getContentPane().add(btnCancel, new org.netbeans.lib.awtextra.AbsoluteConstraints(41, 442, -1, -1));

        jLabel2.setText("Asset Name:");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 81, 80, -1));

        jLabel3.setText("Category:");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 121, 80, -1));

        jLabel4.setText("Status:");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 161, 80, -1));

        jLabel5.setText("Location:");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 201, 80, -1));

        jLabel6.setText("Notes:");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 306, 62, -1));

        jLabel7.setText("Quantity:");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 241, 80, -1));

        spnQuantity.setModel(new javax.swing.SpinnerNumberModel(1, 1, 1000, 1));
        spnQuantity.setValue(1);
        getContentPane().add(spnQuantity, new org.netbeans.lib.awtextra.AbsoluteConstraints(172, 238, 81, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        // TODO add your handling code here:
        int quantity = (int) spnQuantity.getValue();
        try {
            ComboItem statusItem = (ComboItem) cmbStatus.getSelectedItem();
            ComboItem locationItem = (ComboItem) cmbLocation.getSelectedItem();
            String sql
                    = "UPDATE FixedAsset "
                    + "SET StatusID = ?, LocationID = ?, Quantity = ?, Notes = ? "
                    + "WHERE AssetID = ?";

            DatabaseHelper.executeUpdate(
                    sql,
                    statusItem.getId(),
                    locationItem == null ? null : locationItem.getId(),
                    quantity,
                    txtNotes.getText(),
                    assetId
            );

            JOptionPane.showMessageDialog(this,
                    "Asset updated successfully.");
            dispose();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error saving asset: " + e.getMessage());
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnCancelActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnSave;
    private javax.swing.JComboBox<ComboItem> cmbLocation;
    private javax.swing.JComboBox<ComboItem> cmbStatus;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSpinner spnQuantity;
    private javax.swing.JTextField txtAssetName;
    private javax.swing.JTextField txtCategory;
    private javax.swing.JTextArea txtNotes;
    // End of variables declaration//GEN-END:variables
}
