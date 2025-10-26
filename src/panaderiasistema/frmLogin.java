/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package panaderiasistema;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
//
import java.awt.Desktop;
import java.net.URI;


public class frmLogin extends javax.swing.JFrame {

 
    public frmLogin() {
        initComponents();
        this.setLocationRelativeTo(null);
        
    }

private void registrarAuditoria(String usuario, String accion) {
    String sql = "INSERT INTO auditoria (usuario, accion) VALUES (?, ?)";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, usuario);
        stmt.setString(2, accion);
        stmt.executeUpdate();

    } catch (SQLException e) {
        System.err.println("Error registrando en auditoría: " + e.getMessage());
    }
}

   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        txtUsuario = new javax.swing.JTextField();
        btnIngresar = new javax.swing.JButton();
        txtContraseña = new javax.swing.JPasswordField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lblWhatsapp = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(255, 153, 0));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtUsuario.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        getContentPane().add(txtUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 90, 180, 30));

        btnIngresar.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        btnIngresar.setText("Ingresar");
        btnIngresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIngresarActionPerformed(evt);
            }
        });
        getContentPane().add(btnIngresar, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 220, 190, 30));

        txtContraseña.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        getContentPane().add(txtContraseña, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 160, 180, 30));

        jLabel1.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel1.setText("Usuario");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 70, 180, -1));

        jLabel2.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel2.setText("Contraseña");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 140, 180, -1));

        jLabel3.setIcon(new javax.swing.ImageIcon("C:\\Users\\juanp\\Downloads\\bakery.png")); // NOI18N
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 260, 310));

        jLabel4.setFont(new java.awt.Font("Gadugi", 0, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 102, 51));
        jLabel4.setText("¡Hola de nuevo! :)");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 10, 210, 50));

        lblWhatsapp.setFont(new java.awt.Font("Dialog", 2, 12)); // NOI18N
        lblWhatsapp.setText("Click aquí para contactar por WhatsApp");
        lblWhatsapp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblWhatsappMouseClicked(evt);
            }
        });
        getContentPane().add(lblWhatsapp, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 290, 240, -1));

        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));
        getContentPane().add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 312, 230, 10));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnIngresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIngresarActionPerformed
         verificarCredenciales();
    }//GEN-LAST:event_btnIngresarActionPerformed

    private void lblWhatsappMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblWhatsappMouseClicked
       try {
            // Cambiá el número por el tuyo (ej: 5491123456789)
            Desktop.getDesktop().browse(new URI("https://wa.me/3512125597"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    
    }//GEN-LAST:event_lblWhatsappMouseClicked

        private void verificarCredenciales() {
        String usuario = txtUsuario.getText().trim();
        String contraseña = String.valueOf(txtContraseña.getPassword());

        if (usuario.isEmpty() || contraseña.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.");
            return;
        }

        String sql = "SELECT u.nombre_usuario, u.contraseña, r.nombre_rol " +
                     "FROM usuarios u " +
                     "JOIN roles r ON u.id_rol = r.id_rol " +
                     "WHERE u.nombre_usuario = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String contraseñaDB = rs.getString("contraseña");
                String rol = rs.getString("nombre_rol");

                if (contraseñaDB.equals(contraseña)) {
                        JOptionPane.showMessageDialog(this, "Bienvenido, " + usuario + " (" + rol + ")");
                        
                        LoginSession.usuarioActual = usuario;
   
                        registrarAuditoria(usuario, "Inicio de sesión exitoso");
                        this.dispose();
                        new frmPrincipal(usuario, rol).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(this, "Contraseña incorrecta.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Usuario no encontrado.");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al conectar: " + e.getMessage());
        }
    }

    
 
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
            java.util.logging.Logger.getLogger(frmLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmLogin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnIngresar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblWhatsapp;
    private javax.swing.JPasswordField txtContraseña;
    private javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables
}
