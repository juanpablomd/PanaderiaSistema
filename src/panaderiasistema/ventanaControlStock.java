
package panaderiasistema;
import javax.swing.JOptionPane;
import javax.swing.table.TableColumn;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class ventanaControlStock extends javax.swing.JFrame {


    public ventanaControlStock() {
        initComponents();
        tableProd.setAutoCreateRowSorter(true);
        tableProd.getColumnModel().getColumn(3).setCellRenderer(new StockCellRenderer());
        tableProd.addMouseListener(new MouseAdapter() {
    @Override
    public void mouseClicked(MouseEvent e) {
        int fila = tableProd.getSelectedRow();
        if (fila != -1) {
            String codigo = tableProd.getValueAt(fila, 0).toString(); // Columna 0 = Código
            txtCodigoProdEliminar.setText(codigo);
        }
    }
});

    }

  
    private void actualizarFilaProducto(String codigo) {
    try (Connection con = DBConnection.getConnection();
         PreparedStatement stmt = con.prepareStatement("SELECT Codigo, Nombre, Precio, Stock FROM productos WHERE Codigo = ?")) {

        stmt.setString(1, codigo);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            String codigoProd = rs.getString("Codigo");
            String nombre = rs.getString("Nombre");
            double precio = rs.getDouble("Precio");
            int stock = rs.getInt("Stock");

            javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) tableProd.getModel();

            // Buscar la fila en la tabla por código
            for (int i = 0; i < model.getRowCount(); i++) {
                if (model.getValueAt(i, 0).equals(codigoProd)) {
                    // Actualizar fila con los nuevos datos
                    model.setValueAt(nombre, i, 1);
                    model.setValueAt(precio, i, 2);
                    model.setValueAt(stock, i, 3);
                    break;
                }
            }
        }

    } catch (SQLException e) {
        UtilsValidaciones.mostrarError("Error al actualizar producto: " + e.getMessage(), this);
    }
}


    
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tableProd = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        txtCodNomProd = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        txtCodigoProdEliminar = new javax.swing.JTextField();
        btnEliminarTodos = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        spCantidadAEliminar = new javax.swing.JSpinner();
        btnEliminarCantidad = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        btnAñadirCantidad = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();

        setTitle("Control de stock productos existentes");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tableProd.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        tableProd.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Código", "Nombre", "Precio", "Stock"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tableProd);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 740, 390));

        jLabel2.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel2.setText("Buscar producto");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 160, 30));

        txtCodNomProd.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        getContentPane().add(txtCodNomProd, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 20, 180, 30));

        btnBuscar.setBackground(new java.awt.Color(153, 153, 153));
        btnBuscar.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        btnBuscar.setForeground(new java.awt.Color(255, 255, 255));
        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });
        getContentPane().add(btnBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(349, 20, 110, 30));

        txtCodigoProdEliminar.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        getContentPane().add(txtCodigoProdEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 500, 190, 30));

        btnEliminarTodos.setBackground(new java.awt.Color(204, 0, 0));
        btnEliminarTodos.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        btnEliminarTodos.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminarTodos.setText("Eliminar todos (stock 0)");
        btnEliminarTodos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarTodosActionPerformed(evt);
            }
        });
        getContentPane().add(btnEliminarTodos, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 500, 180, 80));

        jLabel4.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel4.setText("Cantidad");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 540, 90, 40));

        spCantidadAEliminar.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        getContentPane().add(spCantidadAEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 540, 110, 40));

        btnEliminarCantidad.setBackground(new java.awt.Color(204, 0, 0));
        btnEliminarCantidad.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        btnEliminarCantidad.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminarCantidad.setText("Eliminar cantidad");
        btnEliminarCantidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarCantidadActionPerformed(evt);
            }
        });
        getContentPane().add(btnEliminarCantidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 500, 140, 80));

        jLabel10.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel10.setText("Código del producto:");
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 470, -1, -1));

        btnAñadirCantidad.setBackground(new java.awt.Color(0, 204, 51));
        btnAñadirCantidad.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        btnAñadirCantidad.setForeground(new java.awt.Color(255, 255, 255));
        btnAñadirCantidad.setText("Añadir cantidad");
        btnAñadirCantidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAñadirCantidadActionPerformed(evt);
            }
        });
        getContentPane().add(btnAñadirCantidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 500, 150, 80));
        getContentPane().add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 620, 730, 20));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
    String textoBusqueda = txtCodNomProd.getText().trim();
    if (!textoBusqueda.isEmpty()) {
        UtilsBusquedas.buscarYMostrarEnTabla(textoBusqueda, tableProd, this);
        UtilsValidaciones.limpiarCampos(txtCodNomProd);
    } else {
        JOptionPane.showMessageDialog(this, "Ingresá un nombre o código.");
    }
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnEliminarCantidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarCantidadActionPerformed
        // TODO add your handling code here:
        String codigo = txtCodigoProdEliminar.getText().trim();
    int cantidadEliminar = (int) spCantidadAEliminar.getValue();

    if (codigo.isEmpty()) {
        UtilsValidaciones.mostrarError("Ingresá un código de producto.", ventanaControlStock.this);
        return;
    }

    try (Connection con = DBConnection.getConnection();
         PreparedStatement selectStmt = con.prepareStatement("SELECT Stock FROM productos WHERE Codigo = ?")) {

        selectStmt.setString(1, codigo);
        ResultSet rs = selectStmt.executeQuery();

        if (rs.next()) {
            int stockActual = rs.getInt("Stock");

            if (cantidadEliminar > stockActual) {
                UtilsValidaciones.mostrarError("No se puede eliminar más de lo disponible. Stock actual: " + stockActual, ventanaControlStock.this);
                return;
            }
                        
            int nuevoStock = stockActual - cantidadEliminar;

        // Confirmación
        int confirm = JOptionPane.showConfirmDialog(this,
            "¿Estás seguro de eliminar " + cantidadEliminar + " unidades del producto?",
            "Confirmar eliminación",
            JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        try (PreparedStatement updateStmt = con.prepareStatement("UPDATE productos SET Stock = ? WHERE Codigo = ?")) {
            updateStmt.setInt(1, nuevoStock);
            updateStmt.setString(2, codigo);
            updateStmt.executeUpdate();
            actualizarFilaProducto(codigo);
            UtilsValidaciones.limpiarCampos(txtCodNomProd);
            UtilsValidaciones.limpiarCampos(txtCodigoProdEliminar);
           AuditoriaLogger.registrarAccion(
            LoginSession.usuarioActual,
            "Actualizó stock del producto: " + codigo +
            " | Se eliminó la cantidad de: " + cantidadEliminar +
            " | Nuevo stock: " + nuevoStock
        );

        }
        } else {
            UtilsValidaciones.mostrarError("No se encontro un producto con ese código", ventanaControlStock.this);
        }

    } catch (SQLException ex) {
       UtilsValidaciones.mostrarError("Error al insertar cantidad", ventanaControlStock.this);
    }
        
    }//GEN-LAST:event_btnEliminarCantidadActionPerformed

    private void btnEliminarTodosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarTodosActionPerformed
    String codigo = txtCodigoProdEliminar.getText().trim();

    if (codigo.isEmpty()) {
        UtilsValidaciones.mostrarError("Ingresá un código de producto.", ventanaControlStock.this);
        return;
    }

    try (Connection con = DBConnection.getConnection();
         PreparedStatement selectStmt = con.prepareStatement("SELECT Stock FROM productos WHERE Codigo = ?")) {

        selectStmt.setString(1, codigo);
        ResultSet rs = selectStmt.executeQuery();

        if (rs.next()) {
            int stockActual = rs.getInt("Stock");

            if (stockActual == 0) {
                UtilsValidaciones.mostrarError("El producto ya tiene stock en 0.", ventanaControlStock.this);
                return;
            }
            
            // Confirmación
        int confirm = JOptionPane.showConfirmDialog(this,
            "¿Estás seguro de eliminar todo el stock del producto?",
            "Confirmar eliminación total",
            JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

            try (PreparedStatement updateStmt = con.prepareStatement("UPDATE productos SET Stock = 0 WHERE Codigo = ?")) {
                updateStmt.setString(1, codigo);
                updateStmt.executeUpdate();
                actualizarFilaProducto(codigo);
                UtilsValidaciones.limpiarCampos(txtCodNomProd);
                UtilsValidaciones.limpiarCampos(txtCodigoProdEliminar);
                AuditoriaLogger.registrarAccion(
    LoginSession.usuarioActual,
    "Eliminó TODO el stock del producto: " + codigo + " | Stock anterior: " + stockActual
);

            }
            
        } else {
            UtilsValidaciones.mostrarError("No se encontró un producto con ese código.", ventanaControlStock.this);
        }

    } catch (SQLException ex) {
        UtilsValidaciones.mostrarError("Error al eliminar todo el stock: " + ex.getMessage(), ventanaControlStock.this);
    }                           

    }//GEN-LAST:event_btnEliminarTodosActionPerformed

    private void btnAñadirCantidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAñadirCantidadActionPerformed

    String codigo = txtCodigoProdEliminar.getText().trim();
    int cantidadAñadir = (int) spCantidadAEliminar.getValue();

    if (codigo.isEmpty()) {
        UtilsValidaciones.mostrarError("Ingresá un código de producto.", ventanaControlStock.this);
        return;
    }

    if (cantidadAñadir <= 0) {
        UtilsValidaciones.mostrarError("La cantidad a añadir debe ser mayor a 0.", ventanaControlStock.this);
        return;
    }

    try (Connection con = DBConnection.getConnection();
         PreparedStatement selectStmt = con.prepareStatement("SELECT Stock FROM productos WHERE Codigo = ?")) {

        selectStmt.setString(1, codigo);
        ResultSet rs = selectStmt.executeQuery();

        if (rs.next()) {
            int stockActual = rs.getInt("Stock");
            int nuevoStock = stockActual + cantidadAñadir;

            // Confirmación
            int confirm = JOptionPane.showConfirmDialog(this,
                    "¿Estás seguro de añadir " + cantidadAñadir + " unidades al producto?",
                    "Confirmar adición",
                    JOptionPane.YES_NO_OPTION);

            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }

            try (PreparedStatement updateStmt = con.prepareStatement("UPDATE productos SET Stock = ? WHERE Codigo = ?")) {
                updateStmt.setInt(1, nuevoStock);
                updateStmt.setString(2, codigo);
                updateStmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "Se añadieron " + cantidadAñadir + " unidades al producto.");
                actualizarFilaProducto(codigo);
                UtilsValidaciones.limpiarCampos(txtCodNomProd);
                UtilsValidaciones.limpiarCampos(txtCodigoProdEliminar);
                AuditoriaLogger.registrarAccion(
    LoginSession.usuarioActual,
    "Añadió cantidad al producto: " + codigo +
    " | Se añadió: " + cantidadAñadir +
    " | Nuevo stock: " + nuevoStock
);

            }

        } else {
            UtilsValidaciones.mostrarError("No se encontró un producto con ese código.", ventanaControlStock.this);
        }

    } catch (SQLException ex) {
        UtilsValidaciones.mostrarError("Error al añadir cantidad: " + ex.getMessage(), ventanaControlStock.this);
    }
    

    }//GEN-LAST:event_btnAñadirCantidadActionPerformed

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
            java.util.logging.Logger.getLogger(ventanaControlStock.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ventanaControlStock.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ventanaControlStock.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ventanaControlStock.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ventanaControlStock().setVisible(true);
                
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAñadirCantidad;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnEliminarCantidad;
    private javax.swing.JButton btnEliminarTodos;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSpinner spCantidadAEliminar;
    private javax.swing.JTable tableProd;
    private javax.swing.JTextField txtCodNomProd;
    private javax.swing.JTextField txtCodigoProdEliminar;
    // End of variables declaration//GEN-END:variables
}
