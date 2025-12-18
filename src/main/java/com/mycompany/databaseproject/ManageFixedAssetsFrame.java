/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.databaseproject;

import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ACER
 */
public class ManageFixedAssetsFrame extends javax.swing.JFrame {

    private String currentRole; // Admin / Manager

    public ManageFixedAssetsFrame(String role) {
        initComponents();
        setLocationRelativeTo(null);
        this.currentRole = role;

        initializeTable();
        loadCategories();
        loadStatuses();
        loadLocations();
        loadAssetsTable();
        disableActionButtons();
    }

    private void initializeTable() {
        tableAssets.setDefaultEditor(Object.class, null); // prevent direct edit
        tableAssets.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableAssetsMouseClicked(evt);
            }
        });
    }

    private void loadCategories() {
        cmbCategory.removeAllItems();
        cmbCategory.addItem(null); // for "All"
        try {
            ResultSet rs = DatabaseHelper.executeQuery(
                    "SELECT CategoryID, CategoryName FROM AssetCategory ORDER BY CategoryName"
            );
            while (rs.next()) {
                cmbCategory.addItem(
                        new ComboItem(
                                rs.getInt("CategoryID"),
                                rs.getString("CategoryName")
                        )
                );
            }
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading categories: " + e.getMessage());
        }
    }

    private void loadStatuses() {
        cmbStatus.removeAllItems();
        cmbStatus.addItem(null);
        try {
            ResultSet rs = DatabaseHelper.executeQuery(
                    "SELECT StatusID, StatusName FROM AssetStatus ORDER BY StatusName"
            );
            while (rs.next()) {
                cmbStatus.addItem(
                        new ComboItem(
                                rs.getInt("StatusID"),
                                rs.getString("StatusName")
                        )
                );
            }
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading statuses: " + e.getMessage());
        }
    }

    private void loadLocations() {
        cmbLocation.removeAllItems();
        cmbLocation.addItem(null);
        try {
            ResultSet rs = DatabaseHelper.executeQuery(
                    "SELECT LocationID, LocationName FROM Location ORDER BY LocationName"
            );
            while (rs.next()) {
                cmbLocation.addItem(
                        new ComboItem(
                                rs.getInt("LocationID"),
                                rs.getString("LocationName")
                        )
                );
            }
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading locations: " + e.getMessage());
        }
    }

    private void loadAssetsTable() {
        DefaultTableModel model = (DefaultTableModel) tableAssets.getModel();
        model.setRowCount(0);
        String sql
                = "SELECT fa.AssetID, fa.AssetName, ac.CategoryName, ast.StatusName, "
                + "l.LocationName, fa.PurchaseDate, fa.PurchasePrice "
                + "FROM FixedAsset fa "
                + "JOIN AssetCategory ac ON fa.CategoryID = ac.CategoryID "
                + "JOIN AssetStatus ast ON fa.StatusID = ast.StatusID "
                + "LEFT JOIN Location l ON fa.LocationID = l.LocationID ";
        try {
            ResultSet rs = DatabaseHelper.executeQuery(sql);
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("AssetID"),
                    rs.getString("AssetName"),
                    rs.getString("CategoryName"),
                    rs.getString("StatusName"),
                    rs.getString("LocationName"),
                    rs.getDate("PurchaseDate"),
                    rs.getBigDecimal("PurchasePrice")
                });
            }
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading assets: " + e.getMessage());
        }
        disableActionButtons();
    }

    private void disableActionButtons() {
        btnEditAsset.setEnabled(false);
        btnDeleteAsset.setEnabled(false);
        btnViewDetails.setEnabled(false);
    }

    private int getStatusIdByName(String statusName) throws Exception {
        String sql = "SELECT StatusID FROM AssetStatus WHERE StatusName = ?";
        try (ResultSet rs = DatabaseHelper.executeQuery(sql, statusName)) {
            if (rs.next()) {
                return rs.getInt("StatusID");
            }
        }
        throw new Exception("Status not found: " + statusName);
    }

    private Integer getSelectedId(javax.swing.JComboBox<ComboItem> combo) {
        ComboItem item = (ComboItem) combo.getSelectedItem();
        return item == null ? null : item.getId();
    }

    private void tableAssetsMouseClicked(java.awt.event.MouseEvent evt) {
        int row = tableAssets.getSelectedRow();
        if (row == -1) {
            return;
        }
        String status = tableAssets.getValueAt(row, 3).toString();
        btnViewDetails.setEnabled(true);
        // Edit rules
        if ("In Stock".equals(status) || "In Use".equals(status) || "Under Maintenance".equals(status)) {
            btnEditAsset.setEnabled(true);
        } else {
            btnEditAsset.setEnabled(false);
        }
        // Delete rules (soft delete logic)
        if ("Admin".equals(currentRole) && "In Stock".equals(status)) {
            btnDeleteAsset.setEnabled(true);
        } else {
            btnDeleteAsset.setEnabled(false);
        }
    }

    private void loadAssetsTableWithFilters(
            Integer categoryId,
            Integer statusId,
            Integer locationId) {
        DefaultTableModel model = (DefaultTableModel) tableAssets.getModel();
        model.setRowCount(0);

        StringBuilder sql = new StringBuilder(
                "SELECT fa.AssetID, fa.AssetName, ac.CategoryName, ast.StatusName, "
                + "l.LocationName, fa.PurchaseDate, fa.PurchasePrice "
                + "FROM FixedAsset fa "
                + "JOIN AssetCategory ac ON fa.CategoryID = ac.CategoryID "
                + "JOIN AssetStatus ast ON fa.StatusID = ast.StatusID "
                + "LEFT JOIN Location l ON fa.LocationID = l.LocationID "
                + "WHERE 1=1 "
        );
        java.util.List<Object> params = new java.util.ArrayList<>();
        if (categoryId != null) {
            sql.append("AND fa.CategoryID = ? ");
            params.add(categoryId);
        }
        if (statusId != null) {
            sql.append("AND fa.StatusID = ? ");
            params.add(statusId);
        }
        if (locationId != null) {
            sql.append("AND (fa.LocationID = ? OR fa.LocationID IS NULL) ");
            params.add(locationId);
        }

        sql.append("ORDER BY fa.AssetID");
        try (ResultSet rs = DatabaseHelper.executeQuery(
                sql.toString(), params.toArray())) {

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("AssetID"),
                    rs.getString("AssetName"),
                    rs.getString("CategoryName"),
                    rs.getString("StatusName"),
                    rs.getString("LocationName"),
                    rs.getDate("PurchaseDate"),
                    rs.getBigDecimal("PurchasePrice")
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error filtering assets: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
        disableActionButtons();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cmbCategory = new javax.swing.JComboBox<>();
        cmbStatus = new javax.swing.JComboBox<>();
        cmbLocation = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        btnSearch = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableAssets = new javax.swing.JTable();
        btnAddAsset = new javax.swing.JButton();
        btnEditAsset = new javax.swing.JButton();
        btnViewDetails = new javax.swing.JButton();
        btnDeleteAsset = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();
        iconlabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Fixed Assets Management ");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 40, 280, 30));

        jPanel1.add(cmbCategory, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 130, -1, -1));

        jPanel1.add(cmbStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 130, -1, -1));

        jPanel1.add(cmbLocation, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 130, -1, -1));

        jLabel2.setFont(new java.awt.Font("Segoe Print", 3, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Filter");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 86, 70, 20));

        btnSearch.setText("Search");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });
        jPanel1.add(btnSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 110, -1, -1));

        btnReset.setText("Reset");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });
        jPanel1.add(btnReset, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 140, -1, -1));

        tableAssets.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Asset ID", "Asset Name", "Category", "Status", "Location", "Purchase Date", "Purchase Price"
            }
        ));
        jScrollPane1.setViewportView(tableAssets);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 200, 840, -1));

        btnAddAsset.setText("Add Asset");
        btnAddAsset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddAssetActionPerformed(evt);
            }
        });
        jPanel1.add(btnAddAsset, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 680, 110, -1));

        btnEditAsset.setText("Edit Asset");
        btnEditAsset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditAssetActionPerformed(evt);
            }
        });
        jPanel1.add(btnEditAsset, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 680, 110, -1));

        btnViewDetails.setText("View Details");
        btnViewDetails.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewDetailsActionPerformed(evt);
            }
        });
        jPanel1.add(btnViewDetails, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 680, 110, -1));

        btnDeleteAsset.setText("Delete Asset");
        btnDeleteAsset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteAssetActionPerformed(evt);
            }
        });
        jPanel1.add(btnDeleteAsset, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 680, 110, -1));

        btnBack.setText("Back");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });
        jPanel1.add(btnBack, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 740, 90, -1));

        iconlabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/WhatsApp Image 2025-12-04 at 6.19.13 PM.jpeg"))); // NOI18N
        jPanel1.add(iconlabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 940, 800));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 940, 770));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddAssetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddAssetActionPerformed
        AddAssetDialog dialog = new AddAssetDialog(this, true);
        dialog.setVisible(true);
        // refresh table after closing dialog
        loadAssetsTable();
    }//GEN-LAST:event_btnAddAssetActionPerformed

    private void btnEditAssetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditAssetActionPerformed
        int row = tableAssets.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select an asset first.");
            return;
        }
        int assetId = (int) tableAssets.getValueAt(row, 0);

        EditAssetDialog dialog = new EditAssetDialog(this, true, assetId); 
        dialog.setVisible(true);
        loadAssetsTable();
    }//GEN-LAST:event_btnEditAssetActionPerformed

    private void btnViewDetailsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewDetailsActionPerformed
        // TODO add your handling code here:
        int row = tableAssets.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select an asset first.");
            return;
        }
        int assetId = (int) tableAssets.getValueAt(row, 0);
        AssetDetailsDialog dialog = new AssetDetailsDialog(this, true, assetId ); // assetId
        dialog.setVisible(true);
    }//GEN-LAST:event_btnViewDetailsActionPerformed

    private void btnDeleteAssetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteAssetActionPerformed
        // TODO add your handling code here:
        int row = tableAssets.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select an asset first.");
            return;
        }
        String status = tableAssets.getValueAt(row, 3).toString();
        // Safety check (should already be disabled by UI logic)
        if (!"In Stock".equals(status)) {
            JOptionPane.showMessageDialog(this,
                    "Only assets that are in stock can be deleted.",
                    "Action Not Allowed",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "This asset will be marked as DAMAGED and removed from use.\n"
                + "This action cannot be undone.\n\n"
                + "Do you want to continue?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            int assetId = (int) tableAssets.getValueAt(row, 0);
            int damagedStatusId = getStatusIdByName("Damaged");

            String sql = "UPDATE FixedAsset SET StatusID = ? WHERE AssetID = ?";
            DatabaseHelper.executeUpdate(sql, damagedStatusId, assetId);

            JOptionPane.showMessageDialog(this,
                    "Asset has been marked as damaged successfully.");
            loadAssetsTable(); // refresh view
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error deleting asset: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }//GEN-LAST:event_btnDeleteAssetActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        // TODO add your handling code here:
        cmbCategory.setSelectedItem(null);
        cmbStatus.setSelectedItem(null);
        cmbLocation.setSelectedItem(null);
        loadAssetsTable();
    }//GEN-LAST:event_btnResetActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        // TODO add your handling code here:
        Integer categoryId = getSelectedId(cmbCategory);
        Integer statusId = getSelectedId(cmbStatus);
        Integer locationId = getSelectedId(cmbLocation);

        loadAssetsTableWithFilters(categoryId, statusId, locationId);
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        // TODO add your handling code here:
        new MainMenuFrame(currentRole).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnBackActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            new ManageFixedAssetsFrame("Admin").setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddAsset;
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnDeleteAsset;
    private javax.swing.JButton btnEditAsset;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnViewDetails;
    private javax.swing.JComboBox<ComboItem> cmbCategory;
    private javax.swing.JComboBox<ComboItem> cmbLocation;
    private javax.swing.JComboBox<ComboItem> cmbStatus;
    private javax.swing.JLabel iconlabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tableAssets;
    // End of variables declaration//GEN-END:variables
}
