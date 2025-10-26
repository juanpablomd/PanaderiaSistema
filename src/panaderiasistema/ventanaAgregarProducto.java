
package panaderiasistema;

import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class ventanaAgregarProducto extends javax.swing.JFrame {


    public ventanaAgregarProducto() {
        initComponents();
        UtilsCargaDatos.cargarCategorias(cmbCategorias);
        UtilsCargaDatos.cargarProveedores(cmbProveedores);
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
        btnAgregar = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        txtPrecio = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        cmbCategorias = new javax.swing.JComboBox<>();
        cmbProveedores = new javax.swing.JComboBox<>();
        jSeparator1 = new javax.swing.JSeparator();

        setTitle("Agrega un nuevo producto");
        setBackground(new java.awt.Color(102, 102, 102));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel5.setText("Código del producto:");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 19, 150, 30));

        txtCodigo.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        getContentPane().add(txtCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 20, 200, 30));

        jLabel6.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel6.setText("Nombre del producto:");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, 150, 30));

        txtNombre.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        getContentPane().add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 60, 200, 30));

        jLabel7.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel7.setText("Categoria:");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 150, 150, 30));

        jLabel8.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel8.setText("Stock:");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 240, 150, 30));

        txtStock.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        getContentPane().add(txtStock, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 240, 200, 30));

        btnAgregar.setBackground(new java.awt.Color(0, 204, 51));
        btnAgregar.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        btnAgregar.setForeground(new java.awt.Color(255, 255, 255));
        btnAgregar.setText("Agregar");
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });
        getContentPane().add(btnAgregar, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 300, 170, 40));

        jLabel4.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel4.setText("Precio:");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 99, 150, 30));

        txtPrecio.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        getContentPane().add(txtPrecio, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 100, 200, 30));

        jLabel9.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel9.setText("Proveedor:");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 190, 150, 30));

        cmbCategorias.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        cmbCategorias.setForeground(new java.awt.Color(0, 0, 0));
        cmbCategorias.setModel(new javax.swing.DefaultComboBoxModel<Categoria>());
        getContentPane().add(cmbCategorias, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 150, 200, 30));

        cmbProveedores.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        cmbProveedores.setModel(new javax.swing.DefaultComboBoxModel<Proveedores>());
        getContentPane().add(cmbProveedores, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 190, 200, 30));
        getContentPane().add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 370, 490, 0));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        try {
        String nombre = txtNombre.getText();
        String codigo = txtCodigo.getText();

        Categoria categoria = (Categoria) cmbCategorias.getSelectedItem();
        Proveedores proveedores = (Proveedores) cmbProveedores.getSelectedItem();

        if (nombre.isEmpty() || codigo.isEmpty()) {
            UtilsValidaciones.mostrarError("Por favor, complete todos los campos obligatorios.", ventanaAgregarProducto.this);
            return;
        }

        if (!UtilsValidaciones.esNumero(codigo)) {
            UtilsValidaciones.mostrarError("El código debe contener solo números", ventanaAgregarProducto.this);
            return;
        }

        Double precio = UtilsValidaciones.obtenerDouble(txtPrecio.getText(), "Precio", ventanaAgregarProducto.this);
        Integer stock = UtilsValidaciones.obtenerEntero(txtStock.getText(), "Stock", ventanaAgregarProducto.this);

        if (precio == null || stock == null) return;

        Producto producto = new Producto(codigo, nombre, categoria.getIdCategoria(), proveedores.getIdProveedor(), precio, stock);
        ProductoDAO productoDAO = new ProductoDAO();
        productoDAO.agregarProducto(producto);
       AuditoriaLogger.registrarAccion(LoginSession.usuarioActual, "Agregó el producto: " + nombre + " (Código: " + codigo + ")");



        JOptionPane.showMessageDialog(ventanaAgregarProducto.this, "Producto guardado con éxito");
        UtilsValidaciones.limpiarCampos(txtCodigo, txtNombre, txtPrecio, txtStock);
        UtilsValidaciones.limpiarComboBox(cmbCategorias, cmbProveedores);

    } catch (Exception ex) {
        JOptionPane.showMessageDialog(ventanaAgregarProducto.this, "Error: " + ex.getMessage());
    }
           
    }//GEN-LAST:event_btnAgregarActionPerformed

    
    
    
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
            java.util.logging.Logger.getLogger(ventanaAgregarProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ventanaAgregarProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ventanaAgregarProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ventanaAgregarProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ventanaAgregarProducto().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
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
