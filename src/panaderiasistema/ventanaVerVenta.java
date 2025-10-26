/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package panaderiasistema;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.sql.PreparedStatement;
/**
 *
 * @author juanp
 */
public class ventanaVerVenta extends javax.swing.JFrame {

    /**
     * Creates new form ventanaVerVenta
     */
    public ventanaVerVenta() {
        initComponents();
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
    @Override
    public void mouseClicked(java.awt.event.MouseEvent evt) {
        int fila = jTable1.getSelectedRow();
        if (fila >= 0) {
            int idVenta = Integer.parseInt(jTable1.getValueAt(fila, 0).toString());
            mostrarDetalleVenta(idVenta);
        }
    }
});
    }
    private void listarVentas() {
    DefaultTableModel model = new DefaultTableModel();
    model.setColumnIdentifiers(new String[]{"ID Venta", "Fecha", "Total", "Observación", "Forma de Pago", "Usuario"});
    
    try (Connection con = DBConnection.getConnection();
         Statement stmt = con.createStatement();
         ResultSet rs = stmt.executeQuery("SELECT id_venta, fecha, total, observacion, forma_pago, usuario FROM ventas ORDER BY fecha DESC")) {

        while (rs.next()) {
            int id = rs.getInt("id_venta");
            Timestamp fecha = rs.getTimestamp("fecha");
            double total = rs.getDouble("total");
            String obs = rs.getString("observacion");
            String pago = rs.getString("forma_pago");
            String usuario = rs.getString("usuario");

            model.addRow(new Object[]{id, fecha.toString(), String.format("$%.2f", total), obs, pago, usuario});
        }

        jTable1.setModel(model);

    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, "Error al obtener ventas:\n" + ex.getMessage());
        ex.printStackTrace();
    }
}

private void mostrarDetalleVenta(int idVenta) {
    ventanaVerDetalleVenta ventanaDetalle = new ventanaVerDetalleVenta(idVenta);
    ventanaDetalle.setVisible(true);
}


    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnListarVentas = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setTitle("Ver todas las ventas");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnListarVentas.setBackground(new java.awt.Color(0, 204, 51));
        btnListarVentas.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        btnListarVentas.setForeground(new java.awt.Color(255, 255, 255));
        btnListarVentas.setText("Listar todas las ventas");
        btnListarVentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnListarVentasActionPerformed(evt);
            }
        });
        getContentPane().add(btnListarVentas, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 170, 60));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID Venta", "Fecha", "Total", "Observacion", "Usuario"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 690, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnListarVentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnListarVentasActionPerformed
        // TODO add your handling code here:
        listarVentas();
    }//GEN-LAST:event_btnListarVentasActionPerformed

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
            java.util.logging.Logger.getLogger(ventanaVerVenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ventanaVerVenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ventanaVerVenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ventanaVerVenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ventanaVerVenta().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnListarVentas;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
