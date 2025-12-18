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
        this.assetId = assetId;
        loadStatuses();
        loadLocations();
        loadAsset();
    }

    private void loadAsset() {
        String sql
                = "SELECT fa.AssetName, ac.CategoryName, ast.StatusName, "
                + "fa.StatusID, fa.LocationID, fa.Notes "
                + "FROM FixedAsset fa "
                + "JOIN AssetCategory ac ON fa.CategoryID = ac.CategoryID "
                + "JOIN AssetStatus ast ON fa.StatusID = ast.StatusID "
                + "WHERE fa.AssetID = ?";

        try (ResultSet rs = DatabaseHelper.executeQuery(sql, assetId)) {
            if (rs.next()) {
//                txtAssetName.setText(rs.getString("AssetName"));
//                txtCategory.setText(rs.getString("CategoryName"));
//                currentStatus = rs.getString("StatusName");
//                txtNotes.setText(rs.getString("Notes"));

                applyEditRules();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading asset: " + e.getMessage());
            dispose();
        }
    }

    private void applyEditRules() {
        boolean editable
                = "In Stock".equals(currentStatus)
                || "In Use".equals(currentStatus)
                || "Under Maintenance".equals(currentStatus);

//            cmbStatus.setEnabled(editable);
//            cmbLocation.setEnabled(editable);
//            txtNotes.setEditable(editable);
//            btnSave.setEnabled(editable);

        if (!editable) {
            JOptionPane.showMessageDialog(this,
                    "This asset cannot be edited because of its current status: " + currentStatus,
                    "Edit Not Allowed",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }
    
    // load combos
    private void loadStatuses() {
        try (ResultSet rs = DatabaseHelper.executeQuery(
                "SELECT StatusID, StatusName FROM AssetStatus")) {
            while (rs.next()) {
//                cmbStatus.addItem(new ComboItem(
//                    rs.getInt("StatusID"),
//                    rs.getString("StatusName")
//                ));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading statuses");
        }
    }
    
    private void loadLocations() {
//        cmbLocation.addItem(null);
//        try (ResultSet rs = DatabaseHelper.executeQuery(
//                "SELECT LocationID, LocationName FROM Location")) {
//            while (rs.next()) {
//                cmbLocation.addItem(new ComboItem(
//                    rs.getInt("LocationID"),
//                    rs.getString("LocationName")
//                ));
//            }
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(this, "Error loading locations");
//        }
    }
    
    
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 445, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
