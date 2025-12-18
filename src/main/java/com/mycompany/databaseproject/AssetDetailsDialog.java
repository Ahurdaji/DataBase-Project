/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package com.mycompany.databaseproject;

import java.sql.ResultSet;
import javax.swing.JOptionPane;

public class AssetDetailsDialog extends javax.swing.JDialog {

    private int assetId;

    public AssetDetailsDialog(java.awt.Frame parent, boolean modal, int assetId) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(parent);
        this.assetId = assetId;

        loadAssetDetails();
        makeReadOnly();
    }

    private void loadAssetDetails() {
        String sql
                = "SELECT fa.AssetName, ac.CategoryName, ast.StatusName, "
                + "l.LocationName, fa.SerialNumber, fa.PurchaseDate, "
                + "fa.PurchasePrice, fa.Notes "
                + "FROM FixedAsset fa "
                + "JOIN AssetCategory ac ON fa.CategoryID = ac.CategoryID "
                + "JOIN AssetStatus ast ON fa.StatusID = ast.StatusID "
                + "LEFT JOIN Location l ON fa.LocationID = l.LocationID "
                + "WHERE fa.AssetID = ?";

        try (ResultSet rs = DatabaseHelper.executeQuery(sql, assetId)) {

            if (rs.next()) {
                txtAssetName.setText(rs.getString("AssetName"));
                txtCategory.setText(rs.getString("CategoryName"));
                txtStatus.setText(rs.getString("StatusName"));
                txtLocation.setText(rs.getString("LocationName"));
                txtSerial.setText(rs.getString("SerialNumber"));
                txtPurchaseDate.setText(
                        rs.getDate("PurchaseDate") == null ? "" : rs.getDate("PurchaseDate").toString()
                );
                txtPrice.setText(
                        rs.getBigDecimal("PurchasePrice") == null ? "" : rs.getBigDecimal("PurchasePrice").toString()
                );
                txtNotes.setText(rs.getString("Notes"));
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error loading asset details: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            dispose();
        }
    }

    private void makeReadOnly() {
        txtAssetName.setEditable(false);
        txtCategory.setEditable(false);
        txtStatus.setEditable(false);
        txtLocation.setEditable(false);
        txtSerial.setEditable(false);
        txtPurchaseDate.setEditable(false);
        txtPrice.setEditable(false);
        txtNotes.setEditable(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnClose = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtAssetName = new javax.swing.JTextField();
        txtCategory = new javax.swing.JTextField();
        txtStatus = new javax.swing.JTextField();
        txtLocation = new javax.swing.JTextField();
        txtSerial = new javax.swing.JTextField();
        txtPurchaseDate = new javax.swing.JTextField();
        txtPrice = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtNotes = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        btnClose.setText("Close");
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Asset Details");

        txtNotes.setColumns(20);
        txtNotes.setRows(5);
        jScrollPane1.setViewportView(txtNotes);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(120, 120, 120)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtAssetName)
                    .addComponent(txtCategory)
                    .addComponent(txtStatus)
                    .addComponent(txtLocation)
                    .addComponent(txtSerial)
                    .addComponent(txtPurchaseDate)
                    .addComponent(txtPrice, javax.swing.GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(78, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addComponent(btnClose)
                .addGap(41, 41, 41))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45)
                .addComponent(txtAssetName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtLocation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtSerial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtPurchaseDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                .addComponent(btnClose)
                .addGap(41, 41, 41))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnCloseActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClose;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txtAssetName;
    private javax.swing.JTextField txtCategory;
    private javax.swing.JTextField txtLocation;
    private javax.swing.JTextArea txtNotes;
    private javax.swing.JTextField txtPrice;
    private javax.swing.JTextField txtPurchaseDate;
    private javax.swing.JTextField txtSerial;
    private javax.swing.JTextField txtStatus;
    // End of variables declaration//GEN-END:variables
}
