/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package panaderiasistema;
import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;

public class ventanaAgregarEmpleado extends javax.swing.JFrame {
     // Lista para mantener los roles con sus IDs
    private final ArrayList<Rol> listaRoles = new ArrayList<>();
    /**
     * Creates new form ventanaAgregarEmpleado
     */
    public ventanaAgregarEmpleado() {
        initComponents();
        cargarRoles();
        this.setSize(500, 270); // Ancho x Alto en píxeles
        this.setResizable(false); // Si no querés que el usuario lo agrande
        this.setLocationRelativeTo(null); // Centra la ventana en la pantalla
    }
    // Cargar roles desde la base y llenar el combo con solo nombres
    private void cargarRoles() {
        listaRoles.clear();
        Vector<String> nombresRoles = new Vector<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT id_rol, nombre_rol FROM roles");
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int idRol = rs.getInt("id_rol");
                String nombreRol = rs.getString("nombre_rol");
                listaRoles.add(new Rol(idRol, nombreRol));
                nombresRoles.add(nombreRol);
            }

            cmbRoles.setModel(new DefaultComboBoxModel<>(nombresRoles));

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar roles: " + e.getMessage());
        }
    }

    // Método para agregar empleado usando el id del rol correspondiente
    private void agregarEmpleado() {
        String nombreUsuario = txtNombreUsuario.getText().trim();
        String contraseña = new String(txtContraseña.getPassword()).trim();
        int indexSeleccionado = cmbRoles.getSelectedIndex();

        if (nombreUsuario.isEmpty() || contraseña.isEmpty() || indexSeleccionado == -1) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.");
            return;
        }

        int idRol = listaRoles.get(indexSeleccionado).getId();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     "INSERT INTO usuarios (nombre_usuario, contraseña, id_rol) VALUES (?, ?, ?)")) {

            ps.setString(1, nombreUsuario);
            ps.setString(2, contraseña); // Si querés, aquí podés hacer hash de la contraseña
            ps.setInt(3, idRol);

            int filas = ps.executeUpdate();

            if (filas > 0) {
                JOptionPane.showMessageDialog(this, "Empleado agregado correctamente.");
                dispose(); // Cierra la ventana
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo agregar el empleado.");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al agregar empleado: " + e.getMessage());
        }
    }

   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btnAgregar = new javax.swing.JButton();
        txtNombreUsuario = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtContraseña = new javax.swing.JPasswordField();
        cmbRoles = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Agregar empleado");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel1.setText("Nombre usuario");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 120, -1));

        jLabel2.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel2.setText("Contraseña");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, -1, -1));

        btnAgregar.setBackground(new java.awt.Color(0, 204, 51));
        btnAgregar.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        btnAgregar.setForeground(new java.awt.Color(255, 255, 255));
        btnAgregar.setText("Agregar");
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });
        getContentPane().add(btnAgregar, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 160, 190, 30));

        txtNombreUsuario.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        getContentPane().add(txtNombreUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 10, 210, 30));

        jLabel3.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel3.setText("Rol");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, -1, -1));

        txtContraseña.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        getContentPane().add(txtContraseña, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 50, 210, 30));

        cmbRoles.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        cmbRoles.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        getContentPane().add(cmbRoles, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 100, 210, 30));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        // TODO add your handling code here:
        agregarEmpleado();
    }//GEN-LAST:event_btnAgregarActionPerformed

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
            java.util.logging.Logger.getLogger(ventanaAgregarEmpleado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ventanaAgregarEmpleado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ventanaAgregarEmpleado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ventanaAgregarEmpleado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
               // new ventanaAgregarEmpleado().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
    private javax.swing.JComboBox<String> cmbRoles;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPasswordField txtContraseña;
    private javax.swing.JTextField txtNombreUsuario;
    // End of variables declaration//GEN-END:variables
}
