/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package panaderiasistema;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;

public class ventanaModificarEmpleado extends javax.swing.JFrame {
    private int idEmpleado; // Guarda el id del empleado que vas a modificar
    private ArrayList<Rol> listaRoles = new ArrayList<>();
    /**
     * Creates new form ventanaModificarEmpleado
     */
    public ventanaModificarEmpleado() {
        initComponents();
        cargarRoles();
        cargarUsuarios();
        this.setSize(550, 400); // Ancho x Alto en píxeles
        this.setResizable(false); // Si no querés que el usuario lo agrande
        this.setLocationRelativeTo(null); // Centra la ventana en la pantalla
    }
    private void cargarRoles() {
        cmbRoles.removeAllItems();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT id_rol, nombre_rol FROM roles");
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Rol rol = new Rol(rs.getInt("id_rol"), rs.getString("nombre_rol"));
                listaRoles.add(rol);
                cmbRoles.addItem(rol);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar roles: " + e.getMessage());
        }
    }

    private void cargarUsuarios() {
        cmbUsuarios.removeAllItems();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT id, nombre_usuario FROM usuarios");
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Usuario usuario = new Usuario(rs.getInt("id"), rs.getString("nombre_usuario"));
                cmbUsuarios.addItem(usuario);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error cargando usuarios: " + e.getMessage());
        }
    }

    private void cargarDatosUsuario(int idUsuario) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT nombre_usuario, contraseña, id_rol FROM usuarios WHERE id = ?")) {
            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                txtNombreUsuario.setText(rs.getString("nombre_usuario"));
                txtContraseña.setText(rs.getString("contraseña"));
                int idRol = rs.getInt("id_rol");
                for (int i = 0; i < cmbRoles.getItemCount(); i++) {
                    Rol rol = (Rol) cmbRoles.getItemAt(i);
                    if (rol.getId() == idRol) {
                        cmbRoles.setSelectedIndex(i);
                        break;
                    }
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error cargando datos usuario: " + e.getMessage());
        }
    }

    private void cargarSueldoUltimo(int idUsuario) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     "SELECT sueldo FROM sueldos WHERE id_usuario = ? ORDER BY año DESC, mes DESC LIMIT 1")) {
            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                txtSueldo.setText(rs.getBigDecimal("sueldo").toString());
            } else {
                txtSueldo.setText("");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error cargando sueldo: " + e.getMessage());
        }
    }

    private void modificarEmpleado() {
        Usuario usuarioSeleccionado = (Usuario) cmbUsuarios.getSelectedItem();
        if (usuarioSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Seleccioná un usuario.");
            return;
        }

        String nombreUsuario = txtNombreUsuario.getText().trim();
        String contraseña = new String(txtContraseña.getPassword()).trim();
        Rol rolSeleccionado = (Rol) cmbRoles.getSelectedItem();
        String sueldoStr = txtSueldo.getText().trim();

        if (nombreUsuario.isEmpty() || contraseña.isEmpty() || rolSeleccionado == null || sueldoStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Completá todos los campos.");
            return;
        }

        double sueldo;
        try {
            sueldo = Double.parseDouble(sueldoStr);
            if (sueldo < 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El sueldo debe ser un número válido y positivo.");
            return;
        }

        try (Connection con = DBConnection.getConnection()) {
            con.setAutoCommit(false);

            try (PreparedStatement psUser = con.prepareStatement(
                    "UPDATE usuarios SET nombre_usuario = ?, contraseña = ?, id_rol = ? WHERE id = ?")) {
                psUser.setString(1, nombreUsuario);
                psUser.setString(2, contraseña);
                psUser.setInt(3, rolSeleccionado.getId());
                psUser.setInt(4, usuarioSeleccionado.getId());
                psUser.executeUpdate();
            }

            try (PreparedStatement psSueldoCheck = con.prepareStatement(
                    "SELECT id FROM sueldos WHERE id_usuario = ? ORDER BY año DESC, mes DESC LIMIT 1")) {
                psSueldoCheck.setInt(1, usuarioSeleccionado.getId());
                ResultSet rs = psSueldoCheck.executeQuery();
                if (rs.next()) {
                    int idSueldo = rs.getInt("id");
                    try (PreparedStatement psUpdateSueldo = con.prepareStatement(
                            "UPDATE sueldos SET sueldo = ? WHERE id = ?")) {
                        psUpdateSueldo.setDouble(1, sueldo);
                        psUpdateSueldo.setInt(2, idSueldo);
                        psUpdateSueldo.executeUpdate();
                    }
                } else {
                    java.time.YearMonth ym = java.time.YearMonth.now();
                    try (PreparedStatement psInsertSueldo = con.prepareStatement(
                            "INSERT INTO sueldos (id_usuario, mes, año, sueldo, pagado) VALUES (?, ?, ?, ?, 0)")) {
                        psInsertSueldo.setInt(1, usuarioSeleccionado.getId());
                        psInsertSueldo.setInt(2, ym.getMonthValue());
                        psInsertSueldo.setInt(3, ym.getYear());
                        psInsertSueldo.setDouble(4, sueldo);
                        psInsertSueldo.executeUpdate();
                    }
                }
            }

            con.commit();
            JOptionPane.showMessageDialog(this, "Empleado y sueldo modificados correctamente.");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al modificar: " + e.getMessage());
        }
    }




   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btnModificar = new javax.swing.JButton();
        txtNombreUsuario = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtContraseña = new javax.swing.JPasswordField();
        cmbRoles = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        cmbUsuarios = new javax.swing.JComboBox<>();
        txtSueldo = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Modificar empleado");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel1.setText("Nombre usuario");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 120, -1));

        jLabel2.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel2.setText("Contraseña");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, -1, -1));

        btnModificar.setBackground(new java.awt.Color(0, 204, 51));
        btnModificar.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        btnModificar.setForeground(new java.awt.Color(255, 255, 255));
        btnModificar.setText("Modificar");
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });
        getContentPane().add(btnModificar, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 300, 210, 30));

        txtNombreUsuario.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        getContentPane().add(txtNombreUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 90, 210, 30));

        jLabel3.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel3.setText("Rol");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 240, 40, -1));

        txtContraseña.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        getContentPane().add(txtContraseña, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 140, 210, 30));

        cmbRoles.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        getContentPane().add(cmbRoles, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 240, 210, 30));

        jLabel4.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel4.setText("Elegir usuario");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 100, 30));

        cmbUsuarios.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        cmbUsuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbUsuariosActionPerformed(evt);
            }
        });
        getContentPane().add(cmbUsuarios, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 10, 210, 30));
        getContentPane().add(txtSueldo, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 190, 210, 30));

        jLabel5.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel5.setText("Sueldo");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 190, 80, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        // TODO add your handling code here:
        modificarEmpleado();
        
    }//GEN-LAST:event_btnModificarActionPerformed

    private void cmbUsuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbUsuariosActionPerformed
        // TODO add your handling code here:
        Usuario usuarioSeleccionado = (Usuario) cmbUsuarios.getSelectedItem();
    if (usuarioSeleccionado != null) {
        cargarDatosUsuario(usuarioSeleccionado.getId());
        cargarSueldoUltimo(usuarioSeleccionado.getId());
    }
    }//GEN-LAST:event_cmbUsuariosActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ventanaModificarEmpleado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ventanaModificarEmpleado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ventanaModificarEmpleado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ventanaModificarEmpleado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ventanaModificarEmpleado().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnModificar;
    private javax.swing.JComboBox<Rol> cmbRoles;
    private javax.swing.JComboBox<Usuario> cmbUsuarios;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPasswordField txtContraseña;
    private javax.swing.JTextField txtNombreUsuario;
    private javax.swing.JTextField txtSueldo;
    // End of variables declaration//GEN-END:variables
}
