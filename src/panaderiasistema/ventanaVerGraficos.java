
package panaderiasistema;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import java.sql.*;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.*;

import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;


public class ventanaVerGraficos extends javax.swing.JFrame {

 
    public ventanaVerGraficos() {
        initComponents();
        // Panel principal con layout absoluto
JPanel panelDashboard = new JPanel();
panelDashboard.setLayout(null); // Usás absolute positioning

// Ubicás los gráficos como venías haciendo
panelGraficoProductos.setBounds(10, 10, 400, 360);
panelGraficoPagos.setBounds(440, 10, 430, 360);
panelGraficoTipoVenta.setBounds(10, 390, 400, 290);
panelGraficoVentas.setBounds(440, 390, 430, 290);
panelGraficoVentasHora.setBounds(880, 10, 500, 670); // más grande, de un lado

// Agregás todos al panel dashboard
panelDashboard.add(panelGraficoProductos);
panelDashboard.add(panelGraficoPagos);
panelDashboard.add(panelGraficoTipoVenta);
panelDashboard.add(panelGraficoVentas);
panelDashboard.add(panelGraficoVentasHora);

// Tamaño total del panel (más grande que la ventana si hace falta)
panelDashboard.setPreferredSize(new java.awt.Dimension(1400, 900));

// Ahora creás el scroll
JScrollPane scrollPane = new JScrollPane(panelDashboard);
scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
scrollPane.getVerticalScrollBar().setUnitIncrement(16); // suave

// Y lo agregás al contentPane
setContentPane(scrollPane);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
      
        crearGraficoDeVentas();
        crearGraficoProductosMasVendidos();
        crearGraficoFormasDePago();
        crearGraficoTipoVenta();
        crearGraficoVentasPorHora();

    }
    


private void crearGraficoVentasPorHora() {
    DefaultCategoryDataset dataset = new DefaultCategoryDataset();

    String sql = """
        SELECT HOUR(fecha) AS hora, SUM(total) AS total_ventas
        FROM ventas
        GROUP BY hora
        ORDER BY hora
    """;

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            int hora = rs.getInt("hora");
            double total = rs.getDouble("total_ventas");
            dataset.addValue(total, "Ventas", String.format("%02d:00", hora));
        }

        JFreeChart barChart = ChartFactory.createBarChart(
                "Ventas por Hora del Día",
                "Hora",
                "Monto ($)",
                dataset
        );

        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(450, 300));
        chartPanel.setMouseWheelEnabled(true);

        panelGraficoVentasHora.setLayout(new java.awt.BorderLayout());
        panelGraficoVentasHora.removeAll();
        panelGraficoVentasHora.add(chartPanel, java.awt.BorderLayout.CENTER);
        panelGraficoVentasHora.validate();

    } catch (SQLException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error al obtener datos de ventas por hora.");
    }
}

private void crearGraficoTipoVenta() {
    DefaultPieDataset dataset = new DefaultPieDataset();

    String sql = """
        SELECT 
            CASE 
                WHEN LOWER(observacion) LIKE '%mayorista%' THEN 'Mayorista'
                WHEN LOWER(observacion) LIKE '%minorista%' THEN 'Minorista'
                ELSE 'Otro'
            END AS tipo_venta,
            COUNT(*) AS cantidad
        FROM ventas
        GROUP BY tipo_venta
    """;

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            String tipo = rs.getString("tipo_venta");
            int cantidad = rs.getInt("cantidad");
            dataset.setValue(tipo, cantidad);
        }

        JFreeChart pieChart = ChartFactory.createPieChart(
            "Comparación: Mayorista vs Minorista",
            dataset,
            true, true, false
        );

        PiePlot plot = (PiePlot) pieChart.getPlot();
        plot.setLabelFont(new java.awt.Font("SansSerif", java.awt.Font.PLAIN, 12));
        plot.setCircular(true);

        ChartPanel chartPanel = new ChartPanel(pieChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(450, 300));

        panelGraficoTipoVenta.setLayout(new java.awt.BorderLayout());
        panelGraficoTipoVenta.removeAll();
        panelGraficoTipoVenta.add(chartPanel, java.awt.BorderLayout.CENTER);
        panelGraficoTipoVenta.validate();

    } catch (SQLException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error al generar gráfico de tipo de venta.");
    }
}
private void crearGraficoFormasDePago() {
    DefaultPieDataset dataset = new DefaultPieDataset();

    String sql = """
        SELECT forma_pago, COUNT(*) AS cantidad
        FROM ventas
        GROUP BY forma_pago
    """;

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            String forma = rs.getString("forma_pago");
            int cantidad = rs.getInt("cantidad");
            dataset.setValue(forma, cantidad);
        }

        JFreeChart pieChart = ChartFactory.createPieChart(
            "Formas de Pago Usadas",
            dataset,
            true, true, false
        );

        PiePlot plot = (PiePlot) pieChart.getPlot();
        plot.setLabelFont(new java.awt.Font("SansSerif", java.awt.Font.PLAIN, 12));
        plot.setCircular(true);

        ChartPanel chartPanel = new ChartPanel(pieChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(450, 300));
        chartPanel.setMouseWheelEnabled(true);

        panelGraficoPagos.setLayout(new java.awt.BorderLayout());
        panelGraficoPagos.removeAll();
        panelGraficoPagos.add(chartPanel, java.awt.BorderLayout.CENTER);
        panelGraficoPagos.validate();

    } catch (SQLException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error al obtener datos de formas de pago.");
    }
}

    
private Map<String, Double> obtenerVentasPorMes() {
        Map<String, Double> ventasPorMes = new LinkedHashMap<>();

        String sql = "SELECT DATE_FORMAT(fecha, '%Y-%m') AS mes, SUM(total) AS total_mensual " +
                     "FROM ventas GROUP BY mes ORDER BY mes";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String mes = rs.getString("mes");
                double total = rs.getDouble("total_mensual");
                ventasPorMes.put(mes, total);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al obtener datos: " + ex.getMessage());
        }

        return ventasPorMes;
    }

    private void crearGraficoDeVentas() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        Map<String, Double> datos = obtenerVentasPorMes();

        for (Map.Entry<String, Double> entrada : datos.entrySet()) {
            dataset.addValue(entrada.getValue(), "Ventas", entrada.getKey());
        }

        JFreeChart barChart = ChartFactory.createBarChart(
                "Ventas Mensuales",
                "Mes",
                "Monto ($)",
                dataset
        );

        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(450, 300));
        chartPanel.setMouseWheelEnabled(true);

        panelGraficoVentas.setLayout(new java.awt.BorderLayout()); // para que se adapte bien
        panelGraficoVentas.removeAll(); // limpiar si ya había algo
        panelGraficoVentas.add(chartPanel, java.awt.BorderLayout.CENTER);
        panelGraficoVentas.validate();
    }
 

private void crearGraficoProductosMasVendidos() {
    DefaultCategoryDataset dataset = new DefaultCategoryDataset();

    String sql = """
        SELECT p.Nombre, SUM(dv.cantidad) AS total_vendido
        FROM detalle_ventas dv
        JOIN productos p ON p.Codigo = dv.codigo_producto
        GROUP BY p.Nombre
        ORDER BY total_vendido DESC
        LIMIT 10
    """;

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            String nombre = rs.getString("Nombre");
            double cantidad = rs.getDouble("total_vendido");
            dataset.addValue(cantidad, "Cantidad", nombre);
        }

        JFreeChart barChart = ChartFactory.createBarChart(
            "Top 10 Productos más vendidos",
            "Producto",
            "Cantidad Vendida",
            dataset
        );

        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(450, 300));
        chartPanel.setMouseWheelEnabled(true);

        panelGraficoProductos.setLayout(new java.awt.BorderLayout());
        panelGraficoProductos.removeAll();
        panelGraficoProductos.add(chartPanel, java.awt.BorderLayout.CENTER);
        panelGraficoProductos.validate();

    } catch (SQLException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error al obtener datos: " + ex.getMessage());
    }
}


   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        panelDashboard = new javax.swing.JPanel();
        panelGraficoProductos = new javax.swing.JPanel();
        panelGraficoVentas = new javax.swing.JPanel();
        panelGraficoTipoVenta = new javax.swing.JPanel();
        panelGraficoPagos = new javax.swing.JPanel();
        panelGraficoVentasHora = new javax.swing.JPanel();

        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelDashboard.setLayout(null);
        jScrollPane2.setViewportView(panelDashboard);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 360, 220));

        javax.swing.GroupLayout panelGraficoProductosLayout = new javax.swing.GroupLayout(panelGraficoProductos);
        panelGraficoProductos.setLayout(panelGraficoProductosLayout);
        panelGraficoProductosLayout.setHorizontalGroup(
            panelGraficoProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        panelGraficoProductosLayout.setVerticalGroup(
            panelGraficoProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        getContentPane().add(panelGraficoProductos, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 250, -1, -1));

        javax.swing.GroupLayout panelGraficoVentasLayout = new javax.swing.GroupLayout(panelGraficoVentas);
        panelGraficoVentas.setLayout(panelGraficoVentasLayout);
        panelGraficoVentasLayout.setHorizontalGroup(
            panelGraficoVentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        panelGraficoVentasLayout.setVerticalGroup(
            panelGraficoVentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        getContentPane().add(panelGraficoVentas, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 260, -1, -1));

        javax.swing.GroupLayout panelGraficoTipoVentaLayout = new javax.swing.GroupLayout(panelGraficoTipoVenta);
        panelGraficoTipoVenta.setLayout(panelGraficoTipoVentaLayout);
        panelGraficoTipoVentaLayout.setHorizontalGroup(
            panelGraficoTipoVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        panelGraficoTipoVentaLayout.setVerticalGroup(
            panelGraficoTipoVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        getContentPane().add(panelGraficoTipoVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 270, -1, -1));

        javax.swing.GroupLayout panelGraficoPagosLayout = new javax.swing.GroupLayout(panelGraficoPagos);
        panelGraficoPagos.setLayout(panelGraficoPagosLayout);
        panelGraficoPagosLayout.setHorizontalGroup(
            panelGraficoPagosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        panelGraficoPagosLayout.setVerticalGroup(
            panelGraficoPagosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        getContentPane().add(panelGraficoPagos, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 270, -1, -1));

        javax.swing.GroupLayout panelGraficoVentasHoraLayout = new javax.swing.GroupLayout(panelGraficoVentasHora);
        panelGraficoVentasHora.setLayout(panelGraficoVentasHoraLayout);
        panelGraficoVentasHoraLayout.setHorizontalGroup(
            panelGraficoVentasHoraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        panelGraficoVentasHoraLayout.setVerticalGroup(
            panelGraficoVentasHoraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        getContentPane().add(panelGraficoVentasHora, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 120, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {
    


        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ventanaVerGraficos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel panelDashboard;
    private javax.swing.JPanel panelGraficoPagos;
    private javax.swing.JPanel panelGraficoProductos;
    private javax.swing.JPanel panelGraficoTipoVenta;
    private javax.swing.JPanel panelGraficoVentas;
    private javax.swing.JPanel panelGraficoVentasHora;
    // End of variables declaration//GEN-END:variables
}
