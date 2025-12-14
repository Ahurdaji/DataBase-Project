package com.mycompany.databaseproject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author HP
 */
public class ManageCustomersFrame extends javax.swing.JFrame {

    private String currentRole;

public ManageCustomersFrame(String role) {
    initComponents();
    this.currentRole = role;
    setLocationRelativeTo(null);

    DefaultTableModel model = new DefaultTableModel(
        new String[]{"CustomerID", "FirstName", "LastName", "Phone", "Email", "Address", "NationalID"}, 0
    ) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return column != 0;
        }
    };

    jTable2.setModel(model);
    applyRolePermissions();
    loadCustomers();
}

    private void applyRolePermissions() {

    // Default: everything disabled
    btnAdd.setEnabled(false);
    btnEdit.setEnabled(false);
    btnDelete.setEnabled(false);

    switch (currentRole) {

        case "Admin":
            btnAdd.setEnabled(true);
            btnEdit.setEnabled(true);
            btnDelete.setEnabled(true);
            break;

        case "Manager":
            btnAdd.setEnabled(true);
            btnEdit.setEnabled(true);
            btnDelete.setEnabled(true);
            break;

        case "SalesStaff":
            btnAdd.setEnabled(true);
            btnEdit.setEnabled(false);
            btnDelete.setEnabled(false);
            break;
    }
}

private boolean isOnlyLetters(String value) {
    return value != null && value.trim().matches("[a-zA-Z]+");
}

private boolean isNumeric(String value) {
    return value != null && value.trim().matches("\\d+");
}

private boolean isValidEmail(String value) {
    return value != null && value.contains("@");
}

private boolean isEmpty(String value) {
    return value == null || value.trim().isEmpty();
}

    
private void loadCustomers() {
    DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
    model.setRowCount(0); // Clear the table

    try {
        ResultSet rs = DatabaseHelper.executeQuery(
                "SELECT CustomerID, FirstName, LastName, Phone, Email, Address, NationalID FROM Customer"
        );

        while (rs.next()) {
            model.addRow(new Object[]{
                rs.getInt("CustomerID"),
                rs.getString("FirstName"),
                rs.getString("LastName"),
                rs.getString("Phone"),
                rs.getString("Email"),
                rs.getString("Address"),
                rs.getString("NationalID")
            });
        }

        rs.close();


    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Error loading customers: " + ex.getMessage());
    }
}


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        lblTitle = new javax.swing.JLabel();
        tableCustomers = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        btnAdd = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnRefrech = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();
        btnSearch = new javax.swing.JButton();
        lblicon = new javax.swing.JLabel();

        jLabel1.setText("jLabel1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTitle.setText("Manage Customers");
        getContentPane().add(lblTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 10, -1, -1));

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "CustomerID", "FirstName", "LastName", "Phone", "Email", "Address", "NationalID"
            }
        ));
        jTable2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tableCustomers.setViewportView(jTable2);

        getContentPane().add(tableCustomers, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 50, 510, 250));

        btnAdd.setText("Add Customer");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });
        getContentPane().add(btnAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 350, -1, -1));

        btnEdit.setText("Edit Customer");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });
        getContentPane().add(btnEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 350, -1, -1));

        btnDelete.setText("Delete Customer");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        getContentPane().add(btnDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 350, -1, -1));

        btnRefrech.setText("Refresh");
        btnRefrech.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefrechActionPerformed(evt);
            }
        });
        getContentPane().add(btnRefrech, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 350, -1, -1));

        btnBack.setText("Back");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });
        getContentPane().add(btnBack, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 400, -1, -1));

        btnSearch.setText("Search");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });
        getContentPane().add(btnSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 270, -1, -1));

        lblicon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/WhatsApp Image 2025-12-04 at 6.19.13 PM.jpeg"))); // NOI18N
        lblicon.setText("refrech");
        getContentPane().add(lblicon, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 660, 480));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:

    DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
    int row = jTable2.getSelectedRow();

    // No row selected → create empty row
    if (row < 0) {
        model.addRow(new Object[]{null, "", "", "", "", "", ""});
        JOptionPane.showMessageDialog(this,
            "A new empty row has been added.\nEnter all fields, select the row, then click Add again.");
        return;
    }

    // Row already exists in DB?
    if (model.getValueAt(row, 0) != null) {
        JOptionPane.showMessageDialog(this, "This customer already exists. Use Edit to update.");
        return;
    }

    // ---- VALIDATION ----
    String first = model.getValueAt(row, 1).toString().trim();
    String last = model.getValueAt(row, 2).toString().trim();
    String phone = model.getValueAt(row, 3).toString().trim();
    String email = model.getValueAt(row, 4).toString().trim();
    String address = model.getValueAt(row, 5).toString().trim();
    String national = model.getValueAt(row, 6).toString().trim();

    if (isEmpty(first) || isEmpty(last) || isEmpty(phone) || isEmpty(email) ||
        isEmpty(address) || isEmpty(national)) {
        JOptionPane.showMessageDialog(this, "All fields must be filled.");
        return;
    }

    if (!isNumeric(phone)) {
        JOptionPane.showMessageDialog(this, "Phone must contain only numbers.");
        return;
    }
    if (!isOnlyLetters(first) || !isOnlyLetters(last)) {
    JOptionPane.showMessageDialog(this,
        "First Name and Last Name must contain letters only.");
    return;
}


    if (!isValidEmail(email)) {
        JOptionPane.showMessageDialog(this, "Invalid email. Must contain '@'.");
        return;
    }

    if (!isNumeric(national)) {
        JOptionPane.showMessageDialog(this, "National ID must contain only numbers.");
        return;
    }

    // ---- INSERT INTO DATABASE ----
    try {
        DatabaseHelper.executeUpdate(
            "INSERT INTO Customer (FirstName, LastName, Phone, Email, Address, NationalID) VALUES (?, ?, ?, ?, ?, ?)",
            first, last, phone, email, address, national
        );

        JOptionPane.showMessageDialog(this, "Customer added successfully!");
        loadCustomers();

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error adding customer: " + e.getMessage());
    }
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        // TODO add your handling code here:
    DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
    int row = jTable2.getSelectedRow();

    if (row < 0) {
        JOptionPane.showMessageDialog(this, "Please select a row to edit.");
        return;
    }

    Object idObj = model.getValueAt(row, 0);

    if (idObj == null) {
        JOptionPane.showMessageDialog(this,
            "This row is not saved yet. Use Add to insert it.");
        return;
    }

    int customerID = Integer.parseInt(idObj.toString());

    // ---- VALIDATION ----
    String first = model.getValueAt(row, 1).toString().trim();
    String last = model.getValueAt(row, 2).toString().trim();
    String phone = model.getValueAt(row, 3).toString().trim();
    String email = model.getValueAt(row, 4).toString().trim();
    String address = model.getValueAt(row, 5).toString().trim();
    String national = model.getValueAt(row, 6).toString().trim();

    if (isEmpty(first) || isEmpty(last) || isEmpty(phone) || isEmpty(email) ||
        isEmpty(address) || isEmpty(national)) {
        JOptionPane.showMessageDialog(this, "All fields must be filled.");
        return;
    }
    if (!isOnlyLetters(first) || !isOnlyLetters(last)) {
    JOptionPane.showMessageDialog(this,
        "First Name and Last Name must contain letters only.");
    return;
}


    if (!isNumeric(phone)) {
        JOptionPane.showMessageDialog(this, "Phone must contain only numbers.");
        return;
    }

    if (!isValidEmail(email)) {
        JOptionPane.showMessageDialog(this, "Invalid email. Must contain '@'.");
        return;
    }

    if (!isNumeric(national)) {
        JOptionPane.showMessageDialog(this, "National ID must contain only numbers.");
        return;
    }

    // ---- UPDATE DATABASE ----
    try {
        DatabaseHelper.executeUpdate(
            "UPDATE Customer SET FirstName=?, LastName=?, Phone=?, Email=?, Address=?, NationalID=? WHERE CustomerID=?",
            first, last, phone, email, address, national, customerID
        );

        JOptionPane.showMessageDialog(this, "Customer updated successfully!");
        loadCustomers();

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error updating customer: " + e.getMessage());
    }

    }//GEN-LAST:event_btnEditActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:                                         
   
    DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
    int row = jTable2.getSelectedRow();

    if (row < 0) {
        JOptionPane.showMessageDialog(this, "Please select a row to delete.");
        return;
    }

    Object idObj = model.getValueAt(row, 0);

    // If row not saved in DB → just remove it
    if (idObj == null) {
        model.removeRow(row);
        return;
    }

    int customerID = Integer.parseInt(idObj.toString());

    try {
        int result = DatabaseHelper.executeUpdate(
                "DELETE FROM Customer WHERE CustomerID=?",
                customerID
        );

        if (result > 0) {
            JOptionPane.showMessageDialog(this, "Customer deleted successfully!");
        }

        loadCustomers();

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error deleting customer: " + e.getMessage());
    }

    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnRefrechActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefrechActionPerformed
        // TODO add your handling code here:                                           
    loadCustomers();

    }//GEN-LAST:event_btnRefrechActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        // TODO add your handling code here:                                        
    String search = JOptionPane.showInputDialog(this, "Enter First Name to Search:");

    if (search == null || search.trim().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please enter a name.");
        return;
    }

    DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
    model.setRowCount(0); // clear table

    try {
        ResultSet rs = DatabaseHelper.executeQuery(
                "SELECT CustomerID, FirstName, LastName, Phone, Email, Address, NationalID " +
                "FROM Customer WHERE FirstName LIKE ?",
                "%" + search + "%"
        );

        boolean found = false;

        while (rs.next()) {
            found = true;
            model.addRow(new Object[]{
                rs.getInt("CustomerID"),
                rs.getString("FirstName"),
                rs.getString("LastName"),
                rs.getString("Phone"),
                rs.getString("Email"),
                rs.getString("Address"),
                rs.getString("NationalID")
            });
        }

        rs.close();

        if (!found) {
            JOptionPane.showMessageDialog(this, "No customers found with this name.");
        }

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error searching: " + e.getMessage());
    }

    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        // TODO add your handling code here:
        new MainMenuFrame(currentRole).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnBackActionPerformed

    /**
     * @param args the command line arguments
     */


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnRefrech;
    private javax.swing.JButton btnSearch;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JTable jTable2;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblicon;
    private javax.swing.JScrollPane tableCustomers;
    // End of variables declaration//GEN-END:variables
}
