package panaderiasistema;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.google.gson.Gson;
import java.util.List;
import panaderiasistema.Feriado;

public class frmPrincipal extends javax.swing.JFrame {
    private String usuario;
    private String rol;
    /**
     * Creates new form frmPrincipal
     */
    public frmPrincipal(String usuario, String rol) {
        initComponents();
        this.usuario = usuario;
        this.rol = rol;

        setTitle("Panel Principal - " + usuario + " (" + rol + ")");
        controlarPermisos(); // Controla accesos según el rol
        txtFeriados.setEditable(false);
        txtFeriados.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 14));
        txtFeriados.setLineWrap(true);
        txtFeriados.setWrapStyleWord(true);

        pack(); // Colocá esto al final del constructor (después de initComponents y antes de setVisible)
        setLocationRelativeTo(null); // centrado


                // Mostrar el clima al abrir
        new Thread(() -> {
            String clima = WeatherAPI.obtenerClima("Cordoba");
            lblClima.setText(clima);
        }).start(); // se ejecuta en un hilo aparte para no congelar la interfaz
        cargarFeriados();
    }
    
    private void controlarPermisos() {
    if (rol.equalsIgnoreCase("empleado")) {
        btnAñadirCategoria.setEnabled(false);
        btnVEliminarProd.setEnabled(false);
        btnVerGraficos.setEnabled(false);
        btnVerEmpleados.setEnabled(false);
        btnVerProv.setEnabled(false);
    }
}
    
    
    public void cargarFeriados() {
    new Thread(() -> {
        try {
            URL url = new URL("https://date.nager.at/api/v3/PublicHolidays/2025/AR");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = in.readLine()) != null) {
                response.append(line);
            }

            in.close();
            conn.disconnect();

            Gson gson = new Gson();
            List<Feriado> feriados = gson.fromJson(response.toString(),
                    new com.google.gson.reflect.TypeToken<List<Feriado>>() {}.getType());

            StringBuilder texto = new StringBuilder("📅 Feriados 2025 en Argentina:\n\n");
            for (Feriado f : feriados) {
                texto.append("• ").append(f.getDate()).append(" - ").append(f.getLocalName()).append("\n");
            }

            // Mostrarlo en la interfaz
            txtFeriados.setText(texto.toString());

        } catch (Exception e) {
            txtFeriados.setText("Error al obtener feriados: " + e.getMessage());
        }
    }).start();
}


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnVerEmpleados = new javax.swing.JButton();
        btnVControlStock = new javax.swing.JButton();
        btnVerProv = new javax.swing.JButton();
        btnAñadirCategoria = new javax.swing.JButton();
        btnVerManual = new javax.swing.JButton();
        btnVerGraficos = new javax.swing.JButton();
        btnVVerProducto = new javax.swing.JButton();
        btnVEliminarProd = new javax.swing.JButton();
        btnVAgregarProd = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        btnVModificarProd = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        btnAgregarVenta = new javax.swing.JButton();
        btnBuscarVenta = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        btnVerVentas = new javax.swing.JButton();
        lblClima = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtFeriados = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(204, 204, 204));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnVerEmpleados.setBackground(new java.awt.Color(102, 102, 102));
        btnVerEmpleados.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        btnVerEmpleados.setForeground(new java.awt.Color(255, 255, 255));
        btnVerEmpleados.setIcon(new javax.swing.ImageIcon("C:\\Users\\juanp\\Downloads\\client.png")); // NOI18N
        btnVerEmpleados.setText("Empleados");
        btnVerEmpleados.setBorder(null);
        btnVerEmpleados.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnVerEmpleados.setIconTextGap(8);
        btnVerEmpleados.setSelectedIcon(new javax.swing.ImageIcon("C:\\Users\\juanp\\Downloads\\order (1).png")); // NOI18N
        btnVerEmpleados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerEmpleadosActionPerformed(evt);
            }
        });
        getContentPane().add(btnVerEmpleados, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 120, 190, 40));

        btnVControlStock.setBackground(new java.awt.Color(102, 102, 102));
        btnVControlStock.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        btnVControlStock.setForeground(new java.awt.Color(255, 255, 255));
        btnVControlStock.setIcon(new javax.swing.ImageIcon("C:\\Users\\juanp\\Downloads\\in-stock.png")); // NOI18N
        btnVControlStock.setText("Control stock");
        btnVControlStock.setBorder(null);
        btnVControlStock.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnVControlStock.setIconTextGap(8);
        btnVControlStock.setSelectedIcon(new javax.swing.ImageIcon("C:\\Users\\juanp\\Downloads\\order (1).png")); // NOI18N
        btnVControlStock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVControlStockActionPerformed(evt);
            }
        });
        getContentPane().add(btnVControlStock, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 150, 190, 40));

        btnVerProv.setBackground(new java.awt.Color(102, 102, 102));
        btnVerProv.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        btnVerProv.setForeground(new java.awt.Color(255, 255, 255));
        btnVerProv.setIcon(new javax.swing.ImageIcon("C:\\Users\\juanp\\Downloads\\supplier.png")); // NOI18N
        btnVerProv.setText("Proveedores");
        btnVerProv.setBorder(null);
        btnVerProv.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnVerProv.setIconTextGap(8);
        btnVerProv.setSelectedIcon(new javax.swing.ImageIcon("C:\\Users\\juanp\\Downloads\\order (1).png")); // NOI18N
        btnVerProv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerProvActionPerformed(evt);
            }
        });
        getContentPane().add(btnVerProv, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 170, 190, 40));

        btnAñadirCategoria.setBackground(new java.awt.Color(102, 102, 102));
        btnAñadirCategoria.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        btnAñadirCategoria.setForeground(new java.awt.Color(255, 255, 255));
        btnAñadirCategoria.setIcon(new javax.swing.ImageIcon("C:\\Users\\juanp\\Downloads\\plus.png")); // NOI18N
        btnAñadirCategoria.setText("Añadir categoria");
        btnAñadirCategoria.setBorder(null);
        btnAñadirCategoria.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnAñadirCategoria.setIconTextGap(8);
        btnAñadirCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAñadirCategoriaActionPerformed(evt);
            }
        });
        getContentPane().add(btnAñadirCategoria, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 350, 290, 40));

        btnVerManual.setBackground(new java.awt.Color(102, 102, 102));
        btnVerManual.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        btnVerManual.setForeground(new java.awt.Color(255, 255, 255));
        btnVerManual.setIcon(new javax.swing.ImageIcon("C:\\Users\\juanp\\Downloads\\categories.png")); // NOI18N
        btnVerManual.setText("Manual de uso");
        btnVerManual.setBorder(null);
        btnVerManual.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnVerManual.setIconTextGap(8);
        btnVerManual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerManualActionPerformed(evt);
            }
        });
        getContentPane().add(btnVerManual, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 480, 300, 40));

        btnVerGraficos.setBackground(new java.awt.Color(102, 102, 102));
        btnVerGraficos.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        btnVerGraficos.setForeground(new java.awt.Color(255, 255, 255));
        btnVerGraficos.setIcon(new javax.swing.ImageIcon("C:\\Users\\juanp\\Downloads\\bar-chart.png")); // NOI18N
        btnVerGraficos.setText("Gráficos");
        btnVerGraficos.setBorder(null);
        btnVerGraficos.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnVerGraficos.setIconTextGap(8);
        btnVerGraficos.setSelectedIcon(new javax.swing.ImageIcon("C:\\Users\\juanp\\Downloads\\order (1).png")); // NOI18N
        btnVerGraficos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerGraficosActionPerformed(evt);
            }
        });
        getContentPane().add(btnVerGraficos, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 480, 410, 40));

        btnVVerProducto.setBackground(new java.awt.Color(102, 102, 102));
        btnVVerProducto.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        btnVVerProducto.setForeground(new java.awt.Color(255, 255, 255));
        btnVVerProducto.setIcon(new javax.swing.ImageIcon("C:\\Users\\juanp\\Downloads\\eye.png")); // NOI18N
        btnVVerProducto.setText("Ver todos los productos");
        btnVVerProducto.setBorder(null);
        btnVVerProducto.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnVVerProducto.setIconTextGap(8);
        btnVVerProducto.setSelectedIcon(new javax.swing.ImageIcon("C:\\Users\\juanp\\Downloads\\order (1).png")); // NOI18N
        btnVVerProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVVerProductoActionPerformed(evt);
            }
        });
        getContentPane().add(btnVVerProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 420, 40));

        btnVEliminarProd.setBackground(new java.awt.Color(102, 102, 102));
        btnVEliminarProd.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        btnVEliminarProd.setForeground(new java.awt.Color(255, 255, 255));
        btnVEliminarProd.setIcon(new javax.swing.ImageIcon("C:\\Users\\juanp\\Downloads\\bin.png")); // NOI18N
        btnVEliminarProd.setText("Eliminar producto");
        btnVEliminarProd.setBorder(null);
        btnVEliminarProd.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnVEliminarProd.setIconTextGap(8);
        btnVEliminarProd.setSelectedIcon(new javax.swing.ImageIcon("C:\\Users\\juanp\\Downloads\\order (1).png")); // NOI18N
        btnVEliminarProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVEliminarProdActionPerformed(evt);
            }
        });
        getContentPane().add(btnVEliminarProd, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 80, 190, 40));

        btnVAgregarProd.setBackground(new java.awt.Color(102, 102, 102));
        btnVAgregarProd.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        btnVAgregarProd.setForeground(new java.awt.Color(255, 255, 255));
        btnVAgregarProd.setIcon(new javax.swing.ImageIcon("C:\\Users\\juanp\\Downloads\\plus.png")); // NOI18N
        btnVAgregarProd.setText("Agregar producto");
        btnVAgregarProd.setBorder(null);
        btnVAgregarProd.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnVAgregarProd.setIconTextGap(8);
        btnVAgregarProd.setSelectedIcon(new javax.swing.ImageIcon("C:\\Users\\juanp\\Downloads\\plus (1).png")); // NOI18N
        btnVAgregarProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVAgregarProdActionPerformed(evt);
            }
        });
        getContentPane().add(btnVAgregarProd, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 200, 40));

        jLabel1.setFont(new java.awt.Font("Javanese Text", 1, 24)); // NOI18N
        jLabel1.setText("SECCIÓN USUARIOS");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 80, -1, 40));

        btnVModificarProd.setBackground(new java.awt.Color(102, 102, 102));
        btnVModificarProd.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        btnVModificarProd.setForeground(new java.awt.Color(255, 255, 255));
        btnVModificarProd.setIcon(new javax.swing.ImageIcon("C:\\Users\\juanp\\Downloads\\pencil.png")); // NOI18N
        btnVModificarProd.setText("Modificar producto");
        btnVModificarProd.setBorder(null);
        btnVModificarProd.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnVModificarProd.setIconTextGap(8);
        btnVModificarProd.setSelectedIcon(new javax.swing.ImageIcon("C:\\Users\\juanp\\Downloads\\order (1).png")); // NOI18N
        btnVModificarProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVModificarProdActionPerformed(evt);
            }
        });
        getContentPane().add(btnVModificarProd, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 200, 40));

        jLabel2.setFont(new java.awt.Font("Javanese Text", 1, 24)); // NOI18N
        jLabel2.setText("SECCIÓN PRODUCTOS");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 30, -1, 40));

        btnAgregarVenta.setBackground(new java.awt.Color(102, 102, 102));
        btnAgregarVenta.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        btnAgregarVenta.setForeground(new java.awt.Color(255, 255, 255));
        btnAgregarVenta.setIcon(new javax.swing.ImageIcon("C:\\Users\\juanp\\Downloads\\acquisition.png")); // NOI18N
        btnAgregarVenta.setText("Agregar venta");
        btnAgregarVenta.setBorder(null);
        btnAgregarVenta.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnAgregarVenta.setIconTextGap(8);
        btnAgregarVenta.setSelectedIcon(new javax.swing.ImageIcon("C:\\Users\\juanp\\Downloads\\order (1).png")); // NOI18N
        btnAgregarVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarVentaActionPerformed(evt);
            }
        });
        getContentPane().add(btnAgregarVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 360, 180, 40));

        btnBuscarVenta.setBackground(new java.awt.Color(102, 102, 102));
        btnBuscarVenta.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        btnBuscarVenta.setForeground(new java.awt.Color(255, 255, 255));
        btnBuscarVenta.setIcon(new javax.swing.ImageIcon("C:\\Users\\juanp\\Downloads\\search-interface-symbol.png")); // NOI18N
        btnBuscarVenta.setText("Buscar venta");
        btnBuscarVenta.setBorder(null);
        btnBuscarVenta.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnBuscarVenta.setIconTextGap(8);
        btnBuscarVenta.setSelectedIcon(new javax.swing.ImageIcon("C:\\Users\\juanp\\Downloads\\order (1).png")); // NOI18N
        btnBuscarVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarVentaActionPerformed(evt);
            }
        });
        getContentPane().add(btnBuscarVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 360, 200, 40));

        jLabel3.setFont(new java.awt.Font("Javanese Text", 1, 24)); // NOI18N
        jLabel3.setText("SECCIÓN SISTEMA");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 440, 290, 40));

        jLabel4.setFont(new java.awt.Font("Javanese Text", 1, 24)); // NOI18N
        jLabel4.setText("SECCIÓN VENTAS");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 310, -1, 40));

        jLabel5.setFont(new java.awt.Font("Javanese Text", 1, 24)); // NOI18N
        jLabel5.setText("SECCIÓN CATEGORIAS");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 310, -1, 40));

        btnVerVentas.setBackground(new java.awt.Color(102, 102, 102));
        btnVerVentas.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        btnVerVentas.setForeground(new java.awt.Color(255, 255, 255));
        btnVerVentas.setIcon(new javax.swing.ImageIcon("C:\\Users\\juanp\\Downloads\\eye.png")); // NOI18N
        btnVerVentas.setText("Ver todas las ventas");
        btnVerVentas.setBorder(null);
        btnVerVentas.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnVerVentas.setIconTextGap(8);
        btnVerVentas.setSelectedIcon(new javax.swing.ImageIcon("C:\\Users\\juanp\\Downloads\\order (1).png")); // NOI18N
        btnVerVentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerVentasActionPerformed(evt);
            }
        });
        getContentPane().add(btnVerVentas, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 420, 410, 40));

        lblClima.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        getContentPane().add(lblClima, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 10, 400, 50));

        txtFeriados.setBackground(new java.awt.Color(255, 255, 204));
        txtFeriados.setColumns(20);
        txtFeriados.setForeground(new java.awt.Color(102, 102, 102));
        txtFeriados.setRows(5);
        jScrollPane1.setViewportView(txtFeriados);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 10, -1, 550));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnVerEmpleadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerEmpleadosActionPerformed
        ventanaEmpleados ventana = new ventanaEmpleados();
        ventana.setVisible(true);
        ventana.setLocationRelativeTo(null);
    }//GEN-LAST:event_btnVerEmpleadosActionPerformed

    private void btnVControlStockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVControlStockActionPerformed
        // TODO add your handling code here:
        ventanaControlStock ventana = new ventanaControlStock();
        ventana.setVisible(true);
        ventana.setLocationRelativeTo(null);
    }//GEN-LAST:event_btnVControlStockActionPerformed

    private void btnVerProvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerProvActionPerformed
        // TODO add your handling code here:
        ventanaAgregarProveedor ventana = new ventanaAgregarProveedor();
        ventana.setVisible(true);
        ventana.setLocationRelativeTo(null);
    }//GEN-LAST:event_btnVerProvActionPerformed

    private void btnAñadirCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAñadirCategoriaActionPerformed
        // TODO add your handling code here:
        ventanaAgregarCategoria ventana = new ventanaAgregarCategoria();
        ventana.setVisible(true);
        ventana.setLocationRelativeTo(null);
    }//GEN-LAST:event_btnAñadirCategoriaActionPerformed

    private void btnVerManualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerManualActionPerformed
        // TODO add your handling code here:
        ventanaVerManual ventana = new ventanaVerManual();
        ventana.setVisible(true);
        ventana.setLocationRelativeTo(null);
    }//GEN-LAST:event_btnVerManualActionPerformed

    private void btnVerGraficosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerGraficosActionPerformed
        // TODO add your handling code here:
        ventanaVerGraficos ventana = new ventanaVerGraficos();
        ventana.setVisible(true);
        ventana.setLocationRelativeTo(null);
    }//GEN-LAST:event_btnVerGraficosActionPerformed

    private void btnVVerProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVVerProductoActionPerformed
        ventanaVerProducto ventana = new ventanaVerProducto();
        ventana.setVisible(true);
        ventana.setLocationRelativeTo(null);
    }//GEN-LAST:event_btnVVerProductoActionPerformed

    private void btnVEliminarProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVEliminarProdActionPerformed
        // TODO add your handling code here:
        ventanaEliminarProducto ventana = new ventanaEliminarProducto();
        ventana.setVisible(true);
        ventana.setLocationRelativeTo(null);

    }//GEN-LAST:event_btnVEliminarProdActionPerformed

    private void btnVAgregarProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVAgregarProdActionPerformed
        // TODO add your handling code here:
        ventanaAgregarProducto ventana = new ventanaAgregarProducto();
        ventana.setVisible(true);
        ventana.setLocationRelativeTo(null);
    }//GEN-LAST:event_btnVAgregarProdActionPerformed

    private void btnVModificarProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVModificarProdActionPerformed
        ventanaModificarProducto ventana = new ventanaModificarProducto();
        ventana.setVisible(true);
        ventana.setLocationRelativeTo(null);
    }//GEN-LAST:event_btnVModificarProdActionPerformed

    private void btnAgregarVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarVentaActionPerformed
        // TODO add your handling code here:
        ventanaAgregarVenta ventana = new ventanaAgregarVenta();
        ventana.setVisible(true);
        ventana.setLocationRelativeTo(null);
    }//GEN-LAST:event_btnAgregarVentaActionPerformed

    private void btnBuscarVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarVentaActionPerformed
        ventanaBuscarVenta ventana = new ventanaBuscarVenta();
        ventana.setVisible(true);
        ventana.setLocationRelativeTo(null);
    }//GEN-LAST:event_btnBuscarVentaActionPerformed

    private void btnVerVentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerVentasActionPerformed
        // TODO add your handling code here:
        ventanaVerVenta ventana = new ventanaVerVenta();
        ventana.setVisible(true);
        ventana.setLocationRelativeTo(null);
    }//GEN-LAST:event_btnVerVentasActionPerformed

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
            java.util.logging.Logger.getLogger(frmPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
               // new frmPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarVenta;
    private javax.swing.JButton btnAñadirCategoria;
    private javax.swing.JButton btnBuscarVenta;
    private javax.swing.JButton btnVAgregarProd;
    private javax.swing.JButton btnVControlStock;
    private javax.swing.JButton btnVEliminarProd;
    private javax.swing.JButton btnVModificarProd;
    private javax.swing.JButton btnVVerProducto;
    private javax.swing.JButton btnVerEmpleados;
    private javax.swing.JButton btnVerGraficos;
    private javax.swing.JButton btnVerManual;
    private javax.swing.JButton btnVerProv;
    private javax.swing.JButton btnVerVentas;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblClima;
    private javax.swing.JTextArea txtFeriados;
    // End of variables declaration//GEN-END:variables
}
