

package panaderiasistema;
import javax.swing.JOptionPane;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author juanp
 */
public class ventanaEmpleados extends javax.swing.JFrame {

    /**
     * Creates new form ventanaEmpleados
     */
    public ventanaEmpleados() {
        initComponents();
        this.setLocationRelativeTo(null); // Centra la ventana en la pantalla
    }
    


 private void cargarEmpleadosConSueldos() {
    DefaultTableModel modelo = (DefaultTableModel) jTable1.getModel();
    modelo.setRowCount(0); // Limpiar tabla

    String sql = """
        SELECT u.nombre_usuario, 
               r.nombre_rol, 
               COALESCE(s.sueldo, 0) AS sueldo, 
               COALESCE(s.pagado, 0) AS pagado
        FROM usuarios u
        JOIN roles r ON u.id_rol = r.id_rol
        LEFT JOIN sueldos s 
            ON u.id = s.id_usuario 
            AND s.mes = MONTH(CURDATE()) 
            AND s.año = YEAR(CURDATE())
        WHERE r.id_rol = 1
    """;
    double totalPagados = 0;
    double totalPendientes = 0;


    try (Connection con = DBConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            String nombre = rs.getString("nombre_usuario");
            String rol = rs.getString("nombre_rol");
            double sueldo = rs.getDouble("sueldo");
            boolean pagado = rs.getBoolean("pagado");
            if (sueldo > 0) {
                if (pagado) {
                    totalPagados += sueldo;
                } else {
                    totalPendientes += sueldo;
                }
            }


            modelo.addRow(new Object[]{
                nombre,
                rol,
                sueldo > 0 ? "$" + sueldo : "Sin asignar",
                pagado ? "Sí" : "No"
            });
        }

        lblTotalPagados.setText("Total Pagados: $" + totalPagados);
        lblTotalPendientes.setText("Total Pendientes: $" + totalPendientes);
        double totalGeneral = totalPagados + totalPendientes;
        lblTotalGeneral.setText("Total General: $" + totalGeneral);

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Error al cargar empleados: " + e.getMessage());
    }
}

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnAgregarEmpleado = new javax.swing.JButton();
        btnModificarEmpleado = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        btnVerEmpleados = new javax.swing.JButton();
        btnMarcarPagado = new javax.swing.JButton();
        btnAsignarSueldo = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lblTotalPagados = new javax.swing.JLabel();
        lblTotalPendientes = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblTotalGeneral = new javax.swing.JLabel();
        btnHistorialAcciones = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();

        setTitle("Empleados");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnAgregarEmpleado.setBackground(new java.awt.Color(0, 204, 51));
        btnAgregarEmpleado.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        btnAgregarEmpleado.setForeground(new java.awt.Color(255, 255, 255));
        btnAgregarEmpleado.setText("Agregar empleado");
        btnAgregarEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarEmpleadoActionPerformed(evt);
            }
        });
        getContentPane().add(btnAgregarEmpleado, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 220, 30));

        btnModificarEmpleado.setBackground(new java.awt.Color(153, 153, 153));
        btnModificarEmpleado.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        btnModificarEmpleado.setForeground(new java.awt.Color(255, 255, 255));
        btnModificarEmpleado.setText("Modificar empleado");
        btnModificarEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarEmpleadoActionPerformed(evt);
            }
        });
        getContentPane().add(btnModificarEmpleado, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 220, 30));

        jTable1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Empleado", "Rol", "Sueldo", "Pagado"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 170, 620, 230));

        btnVerEmpleados.setBackground(new java.awt.Color(153, 153, 153));
        btnVerEmpleados.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        btnVerEmpleados.setForeground(new java.awt.Color(255, 255, 255));
        btnVerEmpleados.setText("Ver empleados");
        btnVerEmpleados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerEmpleadosActionPerformed(evt);
            }
        });
        getContentPane().add(btnVerEmpleados, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 410, 130, 30));

        btnMarcarPagado.setBackground(new java.awt.Color(0, 204, 51));
        btnMarcarPagado.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        btnMarcarPagado.setForeground(new java.awt.Color(255, 255, 255));
        btnMarcarPagado.setText("Marcar pagado");
        btnMarcarPagado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMarcarPagadoActionPerformed(evt);
            }
        });
        getContentPane().add(btnMarcarPagado, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 20, 180, 30));

        btnAsignarSueldo.setBackground(new java.awt.Color(153, 153, 153));
        btnAsignarSueldo.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        btnAsignarSueldo.setForeground(new java.awt.Color(255, 255, 255));
        btnAsignarSueldo.setText("Asignar sueldo");
        btnAsignarSueldo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAsignarSueldoActionPerformed(evt);
            }
        });
        getContentPane().add(btnAsignarSueldo, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 70, 180, 30));

        jLabel1.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel1.setText("Total pagados:");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 420, -1, -1));

        jLabel2.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel2.setText("Total pendientes:");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 450, 120, -1));

        lblTotalPagados.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblTotalPagados.setForeground(new java.awt.Color(0, 204, 0));
        lblTotalPagados.setText("$0.00");
        getContentPane().add(lblTotalPagados, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 420, -1, -1));

        lblTotalPendientes.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblTotalPendientes.setForeground(new java.awt.Color(255, 51, 51));
        lblTotalPendientes.setText("$0.00");
        getContentPane().add(lblTotalPendientes, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 450, -1, -1));

        jLabel3.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel3.setText("Total general:");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 480, -1, -1));

        lblTotalGeneral.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblTotalGeneral.setForeground(new java.awt.Color(0, 0, 0));
        lblTotalGeneral.setText("$0.00");
        getContentPane().add(lblTotalGeneral, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 480, -1, -1));

        btnHistorialAcciones.setBackground(new java.awt.Color(153, 153, 153));
        btnHistorialAcciones.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        btnHistorialAcciones.setForeground(new java.awt.Color(255, 255, 255));
        btnHistorialAcciones.setText("Historial de acciones");
        btnHistorialAcciones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHistorialAccionesActionPerformed(evt);
            }
        });
        getContentPane().add(btnHistorialAcciones, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 120, 180, 30));
        getContentPane().add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 140, 120, -1));

        jButton2.setBackground(new java.awt.Color(153, 153, 153));
        jButton2.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Buscar");
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 140, 70, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void marcarSueldoPagado(String usuario) {
    String sql = """
        UPDATE sueldos 
        SET pagado = 1, fecha_pago = CURDATE()
        WHERE id_usuario = (
            SELECT id FROM usuarios WHERE nombre_usuario = ?
        ) 
        AND mes = MONTH(CURDATE()) AND año = YEAR(CURDATE())
    """;

    try (Connection con = DBConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, usuario);
        int filas = ps.executeUpdate();

        if (filas > 0) {
            JOptionPane.showMessageDialog(this, "Sueldo marcado como pagado.");
            cargarEmpleadosConSueldos(); // refrescar tabla
        } else {
            JOptionPane.showMessageDialog(this, "No se encontró sueldo para este mes.");
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Error al actualizar: " + e.getMessage());
    }
}

    private void btnVerEmpleadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerEmpleadosActionPerformed
        // TODO add your handling code here:
        cargarEmpleadosConSueldos();
    }//GEN-LAST:event_btnVerEmpleadosActionPerformed

    private void btnMarcarPagadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMarcarPagadoActionPerformed
       int fila = jTable1.getSelectedRow();
    if (fila == -1) {
        JOptionPane.showMessageDialog(this, "Seleccioná un empleado primero.");
        return;
    }

    String usuario = jTable1.getValueAt(fila, 0).toString(); // columna Nombre Usuario
    int confirm = JOptionPane.showConfirmDialog(this, 
        "¿Querés marcar como pagado el sueldo de " + usuario + "?", 
        "Confirmar pago", JOptionPane.YES_NO_OPTION);

    if (confirm == JOptionPane.YES_OPTION) {
        marcarSueldoPagado(usuario);
    }
    }//GEN-LAST:event_btnMarcarPagadoActionPerformed

    private void btnAsignarSueldoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAsignarSueldoActionPerformed
        // TODO add your handling code here:
        ventanaEmpleadoSueldo ventana = new ventanaEmpleadoSueldo();
        ventana.setVisible(true);
        ventana.setLocationRelativeTo(null);
    }//GEN-LAST:event_btnAsignarSueldoActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
       if (evt.getClickCount() == 2 && evt.getButton() == java.awt.event.MouseEvent.BUTTON1) {
        int fila = jTable1.getSelectedRow();
        if (fila != -1) {
            String usuario = jTable1.getValueAt(fila, 0).toString(); // columna Empleado
            ventanaHistorialSueldos historial = new ventanaHistorialSueldos(usuario);
            historial.setVisible(true);
            historial.setLocationRelativeTo(this);
        }
    }
    }//GEN-LAST:event_jTable1MouseClicked

    private void btnAgregarEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarEmpleadoActionPerformed
        // TODO add your handling code here:
        ventanaAgregarEmpleado ventana = new ventanaAgregarEmpleado();
        ventana.setVisible(true);
        ventana.setLocationRelativeTo(null);
    }//GEN-LAST:event_btnAgregarEmpleadoActionPerformed

    private void btnModificarEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarEmpleadoActionPerformed
        // TODO add your handling code here:
        ventanaModificarEmpleado ventana = new ventanaModificarEmpleado();
        ventana.setVisible(true);
        ventana.setLocationRelativeTo(null);
    }//GEN-LAST:event_btnModificarEmpleadoActionPerformed

    private void btnHistorialAccionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHistorialAccionesActionPerformed
        // TODO add your handling code here:
        ventanaHistorialAcciones ventana = new ventanaHistorialAcciones();
        ventana.setVisible(true);
        ventana.setLocationRelativeTo(null);
    }//GEN-LAST:event_btnHistorialAccionesActionPerformed

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
            java.util.logging.Logger.getLogger(ventanaEmpleados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ventanaEmpleados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ventanaEmpleados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ventanaEmpleados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ventanaEmpleados().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarEmpleado;
    private javax.swing.JButton btnAsignarSueldo;
    private javax.swing.JButton btnHistorialAcciones;
    private javax.swing.JButton btnMarcarPagado;
    private javax.swing.JButton btnModificarEmpleado;
    private javax.swing.JButton btnVerEmpleados;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel lblTotalGeneral;
    private javax.swing.JLabel lblTotalPagados;
    private javax.swing.JLabel lblTotalPendientes;
    // End of variables declaration//GEN-END:variables
}
