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
        String sql =
            "SELECT fa.AssetName, ac.CategoryName, ast.StatusName, " +
            "fa.LocationID, fa.Notes " +
            "FROM FixedAsset fa " +
            "JOIN AssetCategory ac ON fa.CategoryID = ac.CategoryID " +
            "JOIN AssetStatus ast ON fa.StatusID = ast.StatusID " +
            "WHERE fa.AssetID = ?";

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

        cmbStatus.setEnabled(editable);
        cmbLocation.setEnabled(editable);
        txtNotes.setEditable(editable);
        btnSave.setEnabled(editable);

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

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Edit Asset ");

        txtNotes.setColumns(20);
        txtNotes.setRows(5);
        jScrollPane1.setViewportView(txtNotes);

        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        jLabel2.setText("Asset Name:");

        jLabel3.setText("Category:");

        jLabel4.setText("Status:");

        jLabel5.setText("Location:");

        jLabel6.setText("Notes:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(62, 62, 62)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtAssetName)
                                            .addComponent(txtCategory)
                                            .addComponent(cmbStatus, 0, 123, Short.MAX_VALUE)
                                            .addComponent(cmbLocation, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(25, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(btnCancel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnSave)
                        .addGap(57, 57, 57))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel1)
                .addGap(41, 41, 41)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtAssetName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbLocation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jLabel6)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancel)
                    .addComponent(btnSave))
                .addGap(41, 41, 41))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        // TODO add your handling code here:
         try {
            ComboItem statusItem = (ComboItem) cmbStatus.getSelectedItem();
            ComboItem locationItem = (ComboItem) cmbLocation.getSelectedItem();

            String sql =
                "UPDATE FixedAsset " +
                "SET StatusID = ?, LocationID = ?, Notes = ? " +
                "WHERE AssetID = ?";

            DatabaseHelper.executeUpdate(
                sql,
                statusItem.getId(),
                locationItem == null ? null : locationItem.getId(),
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txtAssetName;
    private javax.swing.JTextField txtCategory;
    private javax.swing.JTextArea txtNotes;
    // End of variables declaration//GEN-END:variables
}
