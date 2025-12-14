package com.mycompany.databaseproject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ACER
 */
public class ManageEmployeesFrame extends javax.swing.JFrame {

    private String currentRole;

    public ManageEmployeesFrame(String role) {
    initComponents();
    this.currentRole = role;
    setLocationRelativeTo(null);

    DefaultTableModel model = new DefaultTableModel(
        new String[]{"EmployeeID", "FirstName", "LastName", "Email", "Phone", "DepartmentID"}, 0
    ) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return column != 0 && column != 5;
        }
    };

    tableEmployee.setModel(model);

    applyRolePermissions();   // ⭐ مهم
    loadEmployees();
    loadDepartments();
}
    private void applyRolePermissions() {

    // Default: disable everything
    btnAddEmployee.setEnabled(false);
    btnEditEmployee.setEnabled(false);
    btnDeleteEmployee.setEnabled(false);
    comboBox1.setEnabled(false);

    switch (currentRole) {

        case "Admin":
            btnAddEmployee.setEnabled(true);
            btnEditEmployee.setEnabled(true);
            btnDeleteEmployee.setEnabled(true);
            comboBox1.setEnabled(true);
            break;

        case "Manager":
            // View only
            btnAddEmployee.setEnabled(false);
            btnEditEmployee.setEnabled(false);
            btnDeleteEmployee.setEnabled(false);
            comboBox1.setEnabled(false);
            break;
    }
}



    private boolean isNumeric(String value) {
        if (value == null) {
            return false;
        }

        value = value.trim(); // remove spaces
        return value.matches("\\d+"); // only digits
    }

    private void loadEmployees() {
        DatabaseHelper.fillTable(
                tableEmployee,
                "SELECT EmployeeID, FirstName, LastName, Email, Phone, DepartmentID FROM Employee"
        );
    }

    private void loadDepartments() {
        try {
            Connection conn = DatabaseConnection.getConnection();
            String sql = "SELECT DepartmentID, DepartmentName FROM Department";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            comboBox1.removeAllItems();

            while (rs.next()) {
                int id = rs.getInt("DepartmentID");
                String name = rs.getString("DepartmentName");
                comboBox1.addItem(id + " - " + name);
            }

            rs.close();
            pst.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading departments: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableEmployee = new javax.swing.JTable();
        btnAddEmployee = new javax.swing.JButton();
        btnEditEmployee = new javax.swing.JButton();
        btnDeleteEmployee = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();
        comboBox1 = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel1.setText("Manage Employees");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 10, 200, -1));

        tableEmployee.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Employee ID", "First Name", "Last Name", "Email", "Phone", "DepartmentID"
            }
        ));
        tableEmployee.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableEmployeeMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableEmployee);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 80, 580, 336));

        btnAddEmployee.setText("Add Employee");
        btnAddEmployee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddEmployeeActionPerformed(evt);
            }
        });
        getContentPane().add(btnAddEmployee, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 440, 120, 30));

        btnEditEmployee.setText("Edit Employee");
        btnEditEmployee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditEmployeeActionPerformed(evt);
            }
        });
        getContentPane().add(btnEditEmployee, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 440, 120, 30));

        btnDeleteEmployee.setText("Delete Employee");
        btnDeleteEmployee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteEmployeeActionPerformed(evt);
            }
        });
        getContentPane().add(btnDeleteEmployee, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 440, 130, 30));

        btnRefresh.setText("Refresh");
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });
        getContentPane().add(btnRefresh, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 440, 80, 30));

        btnBack.setText("Back");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });
        getContentPane().add(btnBack, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 500, 80, 30));

        getContentPane().add(comboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 50, 100, -1));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/WhatsApp Image 2025-12-04 at 6.19.13 PM.jpeg"))); // NOI18N
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 720, 660));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddEmployeeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddEmployeeActionPerformed

        DefaultTableModel model = (DefaultTableModel) tableEmployee.getModel();
        int row = tableEmployee.getSelectedRow();

        // 1) No row selected → create EMPTY new row
        if (row < 0) {
            model.addRow(new Object[]{null, "", "", "", "", ""});
            JOptionPane.showMessageDialog(this, "A new row was added. Fill all fields, select the row, then click Add again.");
            return;
        }

        Object idObj = model.getValueAt(row, 0);

        // 2) If ID exists → means employee already in DB
        if (idObj != null) {
            JOptionPane.showMessageDialog(this, "This employee already exists! Use Edit instead.");
            return;
        }

        // 3) Validation
        String first = model.getValueAt(row, 1).toString().trim();
        String last = model.getValueAt(row, 2).toString().trim();
        String email = model.getValueAt(row, 3).toString().trim();
        String phone = model.getValueAt(row, 4).toString().trim();

        if (first.isEmpty() || last.isEmpty() || email.isEmpty() || phone.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields must be filled.");
            return;
        }

        if (!isNumeric(phone)) {
            JOptionPane.showMessageDialog(this, "Phone must contain only numbers.");
            return;
        }

        if (!email.contains("@")) {
            JOptionPane.showMessageDialog(this, "Email must contain '@'.");
            return;
        }

        if (comboBox1.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Please select a Department.");
            return;
        }

        int departmentID = Integer.parseInt(comboBox1.getSelectedItem().toString().split(" - ")[0]);

        // 4) INSERT INTO DATABASE
        try {

            DatabaseHelper.executeUpdate(
                    "INSERT INTO Employee (FirstName, LastName, Email, Phone, DepartmentID) VALUES (?, ?, ?, ?, ?)",
                    first, last, email, phone, departmentID
            );

            JOptionPane.showMessageDialog(this, "Employee added successfully!");

            loadEmployees(); // refresh table

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error adding employee: " + e.getMessage());
        }


    }//GEN-LAST:event_btnAddEmployeeActionPerformed


    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        // TODO add your handling code here:
        loadEmployees();

    }//GEN-LAST:event_btnRefreshActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        // TODO add your handling code here:
        new MainMenuFrame(currentRole).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnBackActionPerformed

    private void btnEditEmployeeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditEmployeeActionPerformed
        // TODO add your handling code here:                                               

        DefaultTableModel model = (DefaultTableModel) tableEmployee.getModel();
        int row = tableEmployee.getSelectedRow();

        // No row selected
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Please select a row to edit.");
            return;
        }

        Object idObj = model.getValueAt(row, 0);

        // Row not saved yet → cannot edit
        if (idObj == null) {
            JOptionPane.showMessageDialog(this, "This row is new. Use Add first.");
            return;
        }

        int employeeID = Integer.parseInt(idObj.toString());

        // -------- VALIDATION --------
        String first = model.getValueAt(row, 1).toString().trim();
        String last = model.getValueAt(row, 2).toString().trim();
        String email = model.getValueAt(row, 3).toString().trim();
        String phone = model.getValueAt(row, 4).toString().trim();

        // EMPTY CHECK
        if (first.isEmpty() || last.isEmpty() || email.isEmpty() || phone.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields must be filled.");
            return; // stop update
        }

        // PHONE CHECK
        if (!isNumeric(phone)) {
            JOptionPane.showMessageDialog(this, "Phone must contain only numbers.");
            return; // stop update
        }

        // EMAIL CHECK
        if (!email.contains("@")) {
            JOptionPane.showMessageDialog(this, "Email must contain '@'.");
            return; // stop update
        }

        // DEPARTMENT CHECK
        if (comboBox1.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Select a department.");
            return; // stop update
        }

        int departmentID = Integer.parseInt(comboBox1.getSelectedItem().toString().split(" - ")[0]);

        // -------- UPDATE QUERY --------
        try {
            DatabaseHelper.executeUpdate(
                    "UPDATE Employee SET FirstName=?, LastName=?, Email=?, Phone=?, DepartmentID=? WHERE EmployeeID=?",
                    first, last, email, phone, departmentID, employeeID
            );

            JOptionPane.showMessageDialog(this, "Employee updated successfully!");
            loadEmployees();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error updating employee: " + e.getMessage());
        }


    }//GEN-LAST:event_btnEditEmployeeActionPerformed

    private void btnDeleteEmployeeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteEmployeeActionPerformed
        // TODO add your handling code here:
        DefaultTableModel model = (DefaultTableModel) tableEmployee.getModel();
        int row = tableEmployee.getSelectedRow();

        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Please select a row to delete.");
            return;
        }

        Object idObj = model.getValueAt(row, 0);

// If no ID → row not saved in DB → just remove it
        if (idObj == null) {
            model.removeRow(row);
            return;
        }

        int employeeID = (int) idObj;

        try {
            DatabaseHelper.executeUpdate(
                    "DELETE FROM Employee WHERE EmployeeID=?",
                    employeeID
            );

            JOptionPane.showMessageDialog(this, "Employee deleted successfully!");
            loadEmployees();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error deleting employee: " + e.getMessage());
        }

    }//GEN-LAST:event_btnDeleteEmployeeActionPerformed

    private void tableEmployeeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableEmployeeMouseClicked

    }//GEN-LAST:event_tableEmployeeMouseClicked

    /**
     * @param args the command line arguments
     */



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddEmployee;
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnDeleteEmployee;
    private javax.swing.JButton btnEditEmployee;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JComboBox<String> comboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tableEmployee;
    // End of variables declaration//GEN-END:variables
}
