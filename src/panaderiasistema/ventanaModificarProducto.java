
package panaderiasistema;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;


public class ventanaModificarProducto extends javax.swing.JFrame {

  
    public ventanaModificarProducto() {
        initComponents();
        UtilsCargaDatos.cargarCategorias(cmbCategorias);
        UtilsCargaDatos.cargarProveedores(cmbProveedores);
    }

    
    private void modificarProducto() {
    try {
        String codigo = txtCodigo.getText().trim();
        String nombre = txtNombre.getText().trim();
        Double precio = UtilsValidaciones.obtenerDouble(txtPrecio.getText().trim(), "Precio", ventanaModificarProducto.this);
        Integer stock = UtilsValidaciones.obtenerEntero(txtStock.getText().trim(), "Stock", ventanaModificarProducto.this);
        Categoria categoria = (Categoria) cmbCategorias.getSelectedItem();
        Proveedores proveedor = (Proveedores) cmbProveedores.getSelectedItem();

        if (codigo.isEmpty() || nombre.isEmpty() || precio == null || stock == null) {
            UtilsValidaciones.mostrarError("Por favor, completá todos los campos correctamente.", ventanaModificarProducto.this);
            return;
        }

        try (Connection con = DBConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(
                     "UPDATE productos SET Nombre=?, IdCategoria=?, IdProveedor=?, Precio=?, Stock=? WHERE Codigo=?")) {

            stmt.setString(1, nombre);
            stmt.setInt(2, categoria.getIdCategoria());
            stmt.setInt(3, proveedor.getIdProveedor());
            stmt.setDouble(4, precio);
            stmt.setInt(5, stock);
            stmt.setString(6, codigo);

            int filas = stmt.executeUpdate();
            if (filas > 0) {
                JOptionPane.showMessageDialog(ventanaModificarProducto.this, "Producto modificado con éxito.");
                UtilsValidaciones.limpiarCampos(txtCodigo, txtNombre, txtPrecio, txtStock);
                UtilsValidaciones.limpiarComboBox(cmbCategorias, cmbProveedores);
                AuditoriaLogger.registrarAccion(LoginSession.usuarioActual, "Modifico el producto: " + nombre + " (Código: " + codigo + ")");
                // Si tenés una tabla de productos, podés actualizarla acá
                // listarProductos(); 
            } else {
                UtilsValidaciones.mostrarError("No se encontró un producto con ese código.", ventanaModificarProducto.this);
            }
        }
    } catch (SQLException e) {
        UtilsValidaciones.mostrarError("Error al modificar producto: " + e.getMessage(), ventanaModificarProducto.this);
    }
}

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel5 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtStock = new javax.swing.JTextField();
        btnGuardarCambios = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        txtPrecio = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        cmbCategorias = new javax.swing.JComboBox<>();
        cmbProveedores = new javax.swing.JComboBox<>();
        btnBuscar = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();

        setTitle("Modificar productos");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel5.setText("Código del producto:");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, -1, -1));

        txtCodigo.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        getContentPane().add(txtCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 20, 200, 30));

        jLabel6.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel6.setText("Nombre del producto:");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 70, -1, -1));

        txtNombre.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        getContentPane().add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 60, 200, 30));

        jLabel7.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel7.setText("Categoria:");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 150, 110, -1));

        jLabel8.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel8.setText("Stock:");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 250, 100, -1));

        txtStock.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        getContentPane().add(txtStock, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 250, 200, 30));

        btnGuardarCambios.setBackground(new java.awt.Color(0, 204, 51));
        btnGuardarCambios.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        btnGuardarCambios.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardarCambios.setText("Guardar cambios");
        btnGuardarCambios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarCambiosActionPerformed(evt);
            }
        });
        getContentPane().add(btnGuardarCambios, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 300, 180, 40));

        jLabel4.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel4.setText("Precio:");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 110, 90, -1));

        txtPrecio.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        getContentPane().add(txtPrecio, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 100, 200, 30));

        jLabel9.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel9.setText("Proveedor:");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 200, -1, -1));

        cmbCategorias.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        cmbCategorias.setForeground(new java.awt.Color(0, 0, 0));
        cmbCategorias.setModel(new javax.swing.DefaultComboBoxModel<Categoria>());
        getContentPane().add(cmbCategorias, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 150, 200, 30));

        cmbProveedores.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        cmbProveedores.setModel(new javax.swing.DefaultComboBoxModel<Proveedores>());
        getContentPane().add(cmbProveedores, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 200, 200, 30));

        btnBuscar.setBackground(new java.awt.Color(153, 153, 153));
        btnBuscar.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        btnBuscar.setForeground(new java.awt.Color(255, 255, 255));
        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });
        getContentPane().add(btnBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(389, 20, 120, 30));
        getContentPane().add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 370, 530, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        UtilsBusquedas.buscarProductoPorCodigo(
        txtCodigo.getText().trim(),
        txtNombre,
        txtPrecio,
        txtStock,
        cmbCategorias,
        cmbProveedores,
        ventanaModificarProducto.this
    );
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnGuardarCambiosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarCambiosActionPerformed
        // TODO add your handling code here:
        modificarProducto();
    }//GEN-LAST:event_btnGuardarCambiosActionPerformed

   
    public static void main(String args[]) {
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ventanaModificarProducto().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnGuardarCambios;
    private javax.swing.JComboBox<Categoria> cmbCategorias;
    private javax.swing.JComboBox<Proveedores> cmbProveedores;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtPrecio;
    private javax.swing.JTextField txtStock;
    // End of variables declaration//GEN-END:variables
}
