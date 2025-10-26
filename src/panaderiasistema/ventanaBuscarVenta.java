package panaderiasistema;


import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.sql.Connection;
import java.sql.SQLException;
import panaderiasistema.DBConnection;

/**
 *
 * @author juanp
 */
public class ventanaBuscarVenta extends javax.swing.JFrame {

    private JDateChooser dateDesde;
    private JDateChooser dateHasta;
    private JButton btnBuscar;
    private JTable tablaVentas;
    private JScrollPane scrollPane;

    public ventanaBuscarVenta() {
        initComponents();
        initCustomComponents();
    }

    private void initCustomComponents() {
        setTitle("Buscar ventas por fecha");
        setLayout(null);
        setSize(750, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JLabel lblDesde = new JLabel("Desde:");
        lblDesde.setBounds(20, 20, 100, 25);
        add(lblDesde);

        dateDesde = new JDateChooser();
        dateDesde.setBounds(80, 20, 150, 25);
        add(dateDesde);

        JLabel lblHasta = new JLabel("Hasta:");
        lblHasta.setBounds(250, 20, 100, 25);
        add(lblHasta);

        dateHasta = new JDateChooser();
        dateHasta.setBounds(310, 20, 150, 25);
        add(dateHasta);

        btnBuscar = new JButton("Buscar ventas");
        btnBuscar.setBounds(480, 20, 150, 25);
        add(btnBuscar);

        tablaVentas = new JTable();
        scrollPane = new JScrollPane(tablaVentas);
        scrollPane.setBounds(20, 70, 700, 350);
        add(scrollPane);

        btnBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                buscarVentasPorFechas();
            }
        });
    }

    private void buscarVentasPorFechas() {
        java.util.Date desde = dateDesde.getDate();
        java.util.Date hasta = dateHasta.getDate();

        if (desde == null || hasta == null) {
            JOptionPane.showMessageDialog(this, "Seleccioná ambas fechas.");
            return;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        String fechaDesde = sdf.format(desde);
        String fechaHasta = sdf.format(hasta);

        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"ID", "Fecha", "Total", "Observación", "Pago", "Usuario"});

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM ventas WHERE DATE(fecha) BETWEEN ? AND ? ORDER BY fecha ASC")) {

            ps.setString(1, fechaDesde);
            ps.setString(2, fechaHasta);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id_venta");
                Timestamp fecha = rs.getTimestamp("fecha");
                double total = rs.getDouble("total");
                String obs = rs.getString("observacion");
                String pago = rs.getString("forma_pago");
                String usuario = rs.getString("usuario");

                model.addRow(new Object[]{id, fecha.toString(), String.format("$%.2f", total), obs, pago, usuario});
            }

            tablaVentas.setModel(model);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error en la búsqueda: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(ventanaBuscarVenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ventanaBuscarVenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ventanaBuscarVenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ventanaBuscarVenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ventanaBuscarVenta().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
