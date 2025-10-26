/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package panaderiasistema;
import java.sql.*;
import java.time.YearMonth;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
/**
 *
 * @author juanp
 */
public class ventanaEmpleadoSueldo extends javax.swing.JFrame {

    /**
     * Creates new form ventanaEmpleadoSueldo
     */
    public ventanaEmpleadoSueldo() {
        initComponents();
        this.setSize(500, 300); // Ancho x Alto en píxeles
        this.setResizable(false); // Si no querés que el usuario lo agrande
        this.setLocationRelativeTo(null); // Centra la ventana en la pantalla
        cargarUsuarios();
        cargarFechas();
        btnGuardar.addActionListener(e -> asignarSueldo());
    }

    private void cargarUsuarios() {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT nombre_usuario FROM usuarios WHERE id_rol = 1"); // sólo empleados
             ResultSet rs = ps.executeQuery()) {

            Vector<String> usuarios = new Vector<>();
            while (rs.next()) {
                usuarios.add(rs.getString("nombre_usuario"));
            }
            cmbUsuarios.setModel(new DefaultComboBoxModel<>(usuarios));

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error cargando usuarios: " + e.getMessage());
        }
    }

    private void cargarFechas() {
        Vector<String> fechas = new Vector<>();

        // Cargar últimos 12 meses hasta el actual
        YearMonth actual = YearMonth.now();
        for (int i = 0; i < 12; i++) {
            YearMonth ym = actual.minusMonths(i);
            fechas.add(String.format("%02d/%d", ym.getMonthValue(), ym.getYear()));
        }
        cmbFecha.setModel(new DefaultComboBoxModel<>(fechas));
    }

    private void asignarSueldo() {
        String usuario = (String) cmbUsuarios.getSelectedItem();
        String fecha = (String) cmbFecha.getSelectedItem();
        String sueldoStr = txtSueldo.getText().trim();

        if (usuario == null || fecha == null || sueldoStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Completá todos los campos.");
            return;
        }

        // Parsear sueldo
        double sueldo;
        try {
            sueldo = Double.parseDouble(sueldoStr);
            if (sueldo < 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El sueldo debe ser un número positivo.");
            return;
        }

        // Parsear mes y año
        String[] parts = fecha.split("/");
        int mes = Integer.parseInt(parts[0]);
        int año = Integer.parseInt(parts[1]);

        // Obtener id_usuario
        int idUsuario = obtenerIdUsuarioPorNombre(usuario);
        if (idUsuario == -1) {
            JOptionPane.showMessageDialog(this, "Usuario no encontrado.");
            return;
        }

        // Insertar o actualizar sueldo
        try (Connection con = DBConnection.getConnection()) {
            // Verificar si existe
            String checkSql = "SELECT COUNT(*) FROM sueldos WHERE id_usuario = ? AND mes = ? AND año = ?";
            try (PreparedStatement psCheck = con.prepareStatement(checkSql)) {
                psCheck.setInt(1, idUsuario);
                psCheck.setInt(2, mes);
                psCheck.setInt(3, año);
                ResultSet rs = psCheck.executeQuery();
                rs.next();
                int count = rs.getInt(1);

                if (count > 0) {
                    // UPDATE
                    String updateSql = "UPDATE sueldos SET sueldo = ? WHERE id_usuario = ? AND mes = ? AND año = ?";
                    try (PreparedStatement psUpdate = con.prepareStatement(updateSql)) {
                        psUpdate.setDouble(1, sueldo);
                        psUpdate.setInt(2, idUsuario);
                        psUpdate.setInt(3, mes);
                        psUpdate.setInt(4, año);
                        psUpdate.executeUpdate();
                        JOptionPane.showMessageDialog(this, "Sueldo actualizado correctamente.");
                    }
                } else {
                    // INSERT
                    String insertSql = "INSERT INTO sueldos (id_usuario, mes, año, sueldo, pagado) VALUES (?, ?, ?, ?, 0)";
                    try (PreparedStatement psInsert = con.prepareStatement(insertSql)) {
                        psInsert.setInt(1, idUsuario);
                        psInsert.setInt(2, mes);
                        psInsert.setInt(3, año);
                        psInsert.setDouble(4, sueldo);
                        psInsert.executeUpdate();
                        JOptionPane.showMessageDialog(this, "Sueldo asignado correctamente.");
                    }
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al asignar sueldo: " + e.getMessage());
        }
    }

    private int obtenerIdUsuarioPorNombre(String nombreUsuario) {
        int id = -1;
        String sql = "SELECT id FROM usuarios WHERE nombre_usuario = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombreUsuario);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                id = rs.getInt("id");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al obtener usuario: " + e.getMessage());
        }
        return id;
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnGuardar = new javax.swing.JButton();
        cmbUsuarios = new javax.swing.JComboBox<>();
        cmbFecha = new javax.swing.JComboBox<>();
        txtSueldo = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setTitle("Asignar sueldo");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnGuardar.setBackground(new java.awt.Color(0, 204, 51));
        btnGuardar.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        btnGuardar.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardar.setText("Asignar");
        getContentPane().add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 190, 210, -1));

        cmbUsuarios.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        cmbUsuarios.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        getContentPane().add(cmbUsuarios, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 20, 210, 30));

        cmbFecha.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        cmbFecha.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        getContentPane().add(cmbFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 70, 210, -1));

        txtSueldo.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        getContentPane().add(txtSueldo, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 120, 210, 30));

        jLabel1.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel1.setText("Sueldo");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 80, 30));

        jLabel2.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel2.setText("Mes");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 70, 30));

        jLabel3.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel3.setText("Empleado");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, 30));

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(ventanaEmpleadoSueldo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ventanaEmpleadoSueldo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ventanaEmpleadoSueldo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ventanaEmpleadoSueldo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ventanaEmpleadoSueldo().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGuardar;
    private javax.swing.JComboBox<String> cmbFecha;
    private javax.swing.JComboBox<String> cmbUsuarios;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JTextField txtSueldo;
    // End of variables declaration//GEN-END:variables
}
