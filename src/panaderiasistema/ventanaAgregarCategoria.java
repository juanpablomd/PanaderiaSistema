
package panaderiasistema;

import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.List;


public class ventanaAgregarCategoria extends javax.swing.JFrame {

    private CategoriaDAO categoriaDAO = new CategoriaDAO();
    
    public ventanaAgregarCategoria() {
        initComponents();
        btnAgregarCat.addActionListener(e -> agregarCategoria());
        btnEliminarCat.addActionListener(e -> eliminarCategoria());
        btnListarTodas.addActionListener(e -> listarCategorias());

    }

private void agregarCategoria() {
    String nombre = txtNomCat.getText().trim();
    if (nombre.isEmpty()) {
        JOptionPane.showMessageDialog(this, "El nombre no puede estar vacío.");
        return;
    }

    if (categoriaDAO.agregarCategoria(nombre)) {
        JOptionPane.showMessageDialog(this, "Categoría agregada correctamente.");
        txtNomCat.setText("");
        listarCategorias();
    } else {
        JOptionPane.showMessageDialog(this, "Error al agregar la categoría.");
    }
}

private void eliminarCategoria() {
    String codStr = txtCodCat.getText().trim();
    if (codStr.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Ingresá el código.");
        return;
    }

    try {
        int id = Integer.parseInt(codStr);
        boolean eliminado = categoriaDAO.eliminarCategoriaPorId(id);
        if (eliminado) {
            JOptionPane.showMessageDialog(this, "Categoría eliminada.");
            txtCodCat.setText("");
            listarCategorias();
        } else {
            JOptionPane.showMessageDialog(this, "No se encontró esa categoría.");
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Código inválido.");
    }
}

private void listarCategorias() {
    List<Categoria> lista = categoriaDAO.obtenerCategorias();
    DefaultTableModel model = new DefaultTableModel();
    model.addColumn("Código");
    model.addColumn("Nombre");

    for (Categoria cat : lista) {
        model.addRow(new Object[]{cat.getIdCategoria(), cat.getNombre()});
    }

    jTable1.setModel(model);
}

   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txtNomCat = new javax.swing.JTextField();
        btnAgregarCat = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        txtCodCat = new javax.swing.JTextField();
        btnEliminarCat = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        btnListarTodas = new javax.swing.JButton();

        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel1.setText("Agregar categoria");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 160, -1));

        txtNomCat.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        getContentPane().add(txtNomCat, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, 160, 30));

        btnAgregarCat.setBackground(new java.awt.Color(0, 204, 51));
        btnAgregarCat.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        btnAgregarCat.setForeground(new java.awt.Color(255, 255, 255));
        btnAgregarCat.setText("Agregar");
        getContentPane().add(btnAgregarCat, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, 160, -1));

        jLabel2.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel2.setText("Eliminar categoria");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, 160, -1));

        txtCodCat.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        getContentPane().add(txtCodCat, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 170, 160, 30));

        btnEliminarCat.setBackground(new java.awt.Color(204, 0, 0));
        btnEliminarCat.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        btnEliminarCat.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminarCat.setText("Eliminar");
        getContentPane().add(btnEliminarCat, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 210, 160, -1));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Código", "Nombre"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 30, 470, 210));

        jLabel4.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel4.setText("Todas las categorias");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 10, 450, -1));

        btnListarTodas.setBackground(new java.awt.Color(0, 204, 51));
        btnListarTodas.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        btnListarTodas.setForeground(new java.awt.Color(255, 255, 255));
        btnListarTodas.setText("Listar");
        getContentPane().add(btnListarTodas, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 250, 140, 40));

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
            java.util.logging.Logger.getLogger(ventanaAgregarCategoria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ventanaAgregarCategoria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ventanaAgregarCategoria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ventanaAgregarCategoria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ventanaAgregarCategoria().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarCat;
    private javax.swing.JButton btnEliminarCat;
    private javax.swing.JButton btnListarTodas;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField txtCodCat;
    private javax.swing.JTextField txtNomCat;
    // End of variables declaration//GEN-END:variables
}
