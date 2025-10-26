
package panaderiasistema;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ventanaVerDetalleVenta extends javax.swing.JFrame {

    private JTable tablaDetalle;
    private JLabel lblUsuario, lblFecha, lblTotal;

    public ventanaVerDetalleVenta(int idVenta) {
        setTitle("Detalle de Venta #" + idVenta);
        setSize(700, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Panel superior con info de cabecera
        JPanel panelSuperior = new JPanel(new GridLayout(1, 3));
        lblUsuario = new JLabel("Usuario: ");
        lblFecha = new JLabel("Fecha: ");
        lblTotal = new JLabel("Total: ");

        panelSuperior.add(lblUsuario);
        panelSuperior.add(lblFecha);
        panelSuperior.add(lblTotal);

        add(panelSuperior, BorderLayout.NORTH);

        // Tabla
        tablaDetalle = new JTable(new DefaultTableModel(
            new Object[]{"Código", "Nombre", "Cantidad", "Precio Unitario", "Subtotal"}, 0
        ));
        JScrollPane scroll = new JScrollPane(tablaDetalle);
        add(scroll, BorderLayout.CENTER);

        cargarDatos(idVenta);

        setVisible(true);
    }

    private void cargarDatos(int idVenta) {
        try (Connection con = DBConnection.getConnection()) {
            // Obtener datos generales de la venta
            String sqlVenta = "SELECT fecha, total, usuario FROM ventas WHERE id_venta = ?";
            try (PreparedStatement ps = con.prepareStatement(sqlVenta)) {
                ps.setInt(1, idVenta);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    lblFecha.setText("Fecha: " + rs.getString("fecha"));
                    lblTotal.setText(String.format("Total: $%.2f", rs.getDouble("total")));
                    lblUsuario.setText("Usuario: " + rs.getString("usuario"));
                }
            }

            // Obtener detalle de la venta
            String sqlDetalle = """
                SELECT dv.codigo_producto, p.nombre, dv.cantidad, dv.precio_unitario, dv.subtotal
                FROM detalle_ventas dv
                JOIN productos p ON dv.codigo_producto = p.codigo
                WHERE dv.id_venta = ?
                """;
            try (PreparedStatement ps = con.prepareStatement(sqlDetalle)) {
                ps.setInt(1, idVenta);
                ResultSet rs = ps.executeQuery();

                DefaultTableModel model = (DefaultTableModel) tablaDetalle.getModel();
                while (rs.next()) {
                    model.addRow(new Object[]{
                        rs.getString("codigo_producto"),
                        rs.getString("nombre"),
                        rs.getDouble("cantidad"),
                        rs.getDouble("precio_unitario"),
                        rs.getDouble("subtotal")
                    });
                }
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar datos:\n" + ex.getMessage());
        }
    }
    
 
    public ventanaVerDetalleVenta() {
        initComponents();
    }

   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

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
            java.util.logging.Logger.getLogger(ventanaVerDetalleVenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ventanaVerDetalleVenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ventanaVerDetalleVenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ventanaVerDetalleVenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ventanaVerDetalleVenta().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
