
package panaderiasistema;


import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.sql.Statement;

import java.io.File;
import java.awt.Desktop;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.io.FileOutputStream;
import java.sql.*;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;


public class ventanaAgregarVenta extends javax.swing.JFrame {

    private int idVenta = -1;
    private DefaultTableModel tablaVentaBackup;
    private double totalVentaBackup;
    private String formaPagoBackup;
    private String observacionesBackup;

public ventanaAgregarVenta() {
    initComponents();
    jTable1.setModel(new javax.swing.table.DefaultTableModel(
        new Object[][] {},
        new String[] {"Código", "Nombre", "Cantidad", "Precio unitario", "Subtotal"}
    ));
    // Asegurar que solo un RadioButton esté seleccionado
        ButtonGroup grupoFormaPago = new ButtonGroup();
        grupoFormaPago.add(rdbEfectivo);
        grupoFormaPago.add(rdbTarjeta);
        grupoFormaPago.add(rdbTransferencia);
        
        // Seleccionar Efectivo por defecto (mejora UX)
        rdbEfectivo.setSelected(true);


// ***************************************************************
    // AÑADIR/MODIFICAR ESTE BLOQUE
    // ***************************************************************
    txtCodProd.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            
            // 1. Ejecutar la búsqueda para poblar los campos Nombre y Precio (si lo encuentras)
            UtilsBusquedas.buscarProductoSimple(
                txtCodProd.getText().trim(),
                txtNombreProd,
                txtPrecioUnitario,
                ventanaAgregarVenta.this
            );
            
            // 2. Si se encontró el producto y se llenaron los campos, asume CANTIDAD = 1.
            //    Si no se llenaron, 'agregarProductoATabla' gestionará el error.
            txtCantidad.setText("1");
            
            // 3. Llama directamente al método para agregar el producto a la tabla.
            //    ¡Esto simula presionar el botón de "Agregar" inmediatamente!
            agregarProductoATabla();
            
            // 4. Limpia el campo de código para el siguiente escaneo (si no lo hace ya agregarProductoATabla)
            //    Lo ideal es que este campo se limpie en agregarProductoATabla para asegurar que solo se limpie si se agregó.
            
            // 5. El foco DEBE volver al campo del código para que el próximo escaneo funcione.
            txtCodProd.requestFocus();
        }
    });
    // ***************************************************************
    
    configurarTeclasRapidas();
}

    private void calcularVuelto() {
        double total = 0;
        try {
            // Extraer el total (eliminando el símbolo de moneda para el cálculo)
            String totalStr = lblTotalFinal.getText().replace("$", "").replace(",", "");
            //total = formatoMoneda.parse(totalStr).doubleValue();
        } catch (Exception e) {
            // Manejar si el total no es un número válido (debería ser $0.00 al inicio)
            total = 0;
        }
    }

    
private double obtenerStockDisponible(String codigoProducto) {
    try (Connection con = DBConnection.getConnection()) {
        String sql = "SELECT stock FROM productos WHERE codigo = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, codigoProducto);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return rs.getDouble("stock");
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error al obtener stock: " + e.getMessage());
    }
    return -1; // Error o producto no encontrado
}

    
    

/*private void agregarProductoATabla() {
    String codigo = txtCodProd.getText().trim();
    String nombre = txtNombreProd.getText().trim();
    String precioStr = txtPrecioUnitario.getText().trim();
    String cantidadStr = txtCantidad.getText().trim();

    if (codigo.isEmpty() || nombre.isEmpty() || precioStr.isEmpty() || cantidadStr.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Completa todos los campos.");
        return;
    }

    double precio;
    int cantidad;

    try {
        precio = Double.parseDouble(precioStr);
        cantidad = Integer.parseInt(cantidadStr);
        if (precio <= 0 || cantidad <= 0) {
            JOptionPane.showMessageDialog(this, "La cantidad y el precio deben ser mayores a cero.");
            txtCodProd.requestFocus();
            return;
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Precio o cantidad inválidos.");
        txtCodProd.requestFocus();
        return;
    }

    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
    boolean encontrado = false;

    for (int i = 0; i < model.getRowCount(); i++) {
        Object valCodigo = model.getValueAt(i, 0);
        if (valCodigo == null) continue;

        String codTabla = valCodigo.toString();
        if (codTabla.equals(codigo)) {
            Object valCantidad = model.getValueAt(i, 2);
            int cantidadExistente = (valCantidad != null) ? Integer.parseInt(valCantidad.toString()) : 0;
            int nuevaCantidad = cantidadExistente + cantidad;
            double nuevoSubtotal = nuevaCantidad * precio;

            model.setValueAt(nuevaCantidad, i, 2); // cantidad
            model.setValueAt(nuevoSubtotal, i, 4); // subtotal
            encontrado = true;
            break;
        }
    }

    if (!encontrado) {
        model.addRow(new Object[]{codigo, nombre, cantidad, precio, cantidad * precio});
    }

    actualizarTotal();


txtCodProd.requestFocus(); // volver a código para el próximo producto

}*/

private void agregarProductoATabla() {
    String codigo = txtCodProd.getText().trim();
    String nombre = txtNombreProd.getText().trim();
    String precioStr = txtPrecioUnitario.getText().trim();
    String cantidadStr = txtCantidad.getText().trim();

    if (codigo.isEmpty() || nombre.isEmpty() || precioStr.isEmpty() || cantidadStr.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Completa todos los campos.");
        return;
    }

    double precio;
    int cantidad;

    try {
        precio = Double.parseDouble(precioStr);
        cantidad = Integer.parseInt(cantidadStr);
        if (precio <= 0 || cantidad <= 0) {
            JOptionPane.showMessageDialog(this, "La cantidad y el precio deben ser mayores a cero.");
            txtCodProd.requestFocus();
            return;
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Precio o cantidad inválidos.");
        txtCodProd.requestFocus();
        return;
    }

    double stockDisponible = obtenerStockDisponible(codigo);
    if (stockDisponible < cantidad) {
        JOptionPane.showMessageDialog(this, "Stock insuficiente. Disponible: " + stockDisponible);
        return;
    }

    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
    boolean encontrado = false;
    int cantidadTotalAInsertar = cantidad;

    for (int i = 0; i < model.getRowCount(); i++) {
        Object valCodigo = model.getValueAt(i, 0);
        if (valCodigo == null) continue;

        String codTabla = valCodigo.toString();
        if (codTabla.equals(codigo)) {
            int cantidadExistente = Integer.parseInt(model.getValueAt(i, 2).toString());
            cantidadTotalAInsertar += cantidadExistente;

            if (cantidadTotalAInsertar > stockDisponible) {
                JOptionPane.showMessageDialog(this, "Stock insuficiente para agregar más. Disponible: " + stockDisponible);
                return;
            }

            int nuevaCantidad = cantidadExistente + cantidad;
            double nuevoSubtotal = nuevaCantidad * precio;

            model.setValueAt(nuevaCantidad, i, 2);
            model.setValueAt(nuevoSubtotal, i, 4);
            encontrado = true;
            break;
        }
    }

    if (!encontrado) {
        model.addRow(new Object[]{codigo, nombre, cantidad, precio, cantidad * precio});
    }

    
    actualizarTotal();
    // Limpia los campos de entrada después de un agregado exitoso
    txtCodProd.setText(""); 
    txtNombreProd.setText("");
    txtPrecioUnitario.setText("");
    txtCantidad.setText("");
    
    txtCodProd.requestFocus(); // volver a código para el próximo producto
  
}



private DefaultTableModel copiarTabla(DefaultTableModel original) {
    DefaultTableModel copia = new DefaultTableModel();
    for (int i = 0; i < original.getColumnCount(); i++) {
        copia.addColumn(original.getColumnName(i));
    }

    for (int i = 0; i < original.getRowCount(); i++) {
        Object[] fila = new Object[original.getColumnCount()];
        for (int j = 0; j < original.getColumnCount(); j++) {
            fila[j] = original.getValueAt(i, j);
        }
        copia.addRow(fila);
    }
    return copia;
}


    private void actualizarTotal() {
    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
    double total = 0;

    for (int i = 0; i < model.getRowCount(); i++) {
        Object valor = model.getValueAt(i, 4); // Subtotal
        if (valor != null) {
            try {
                total += Double.parseDouble(valor.toString());
            } catch (NumberFormatException e) {
                
            }
        }
    }

    lblTotalFinal.setText(String.format("$%.2f", total));
    System.out.println("Total calculado: " + lblTotalFinal.getText());

}
    
    

private int confirmarVentaEnBD() {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();

        if (model.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "No hay productos en la tabla.");
            return -1;
        }

        String formaPago = getFormaPagoSeleccionada();
        if (formaPago.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccioná una forma de pago.");
            return -1;
        }

        String observacion = txtObservaciones.getText().trim();
        String usuario = "admin";
        double totalVenta = 0;

        for (int i = 0; i < model.getRowCount(); i++) {
            Object val = model.getValueAt(i, 4);
            if (val != null) {
                try {
                    totalVenta += Double.parseDouble(val.toString());
                } catch (NumberFormatException ignored) {}
            }
        }

        try (Connection con = DBConnection.getConnection()) {
            con.setAutoCommit(false);

            String sqlVenta = "INSERT INTO ventas (total, observacion, forma_pago, usuario) VALUES (?, ?, ?, ?)";
            PreparedStatement psVenta = con.prepareStatement(sqlVenta, Statement.RETURN_GENERATED_KEYS);
            psVenta.setDouble(1, totalVenta);
            psVenta.setString(2, observacion);
            psVenta.setString(3, formaPago);
            psVenta.setString(4, usuario);
            psVenta.executeUpdate();

            ResultSet rs = psVenta.getGeneratedKeys();
            if (!rs.next()) throw new SQLException("No se pudo obtener el ID de venta.");

           idVenta = rs.getInt(1);

            String sqlDetalle = "INSERT INTO detalle_ventas (id_venta, codigo_producto, cantidad, precio_unitario, subtotal) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement psDetalle = con.prepareStatement(sqlDetalle);

            for (int i = 0; i < model.getRowCount(); i++) {
                String codigo = model.getValueAt(i, 0).toString();
                double cantidad = Double.parseDouble(model.getValueAt(i, 2).toString());
                double precioUnitario = Double.parseDouble(model.getValueAt(i, 3).toString());
                double subtotal = Double.parseDouble(model.getValueAt(i, 4).toString());

                psDetalle.setInt(1, idVenta);
                psDetalle.setString(2, codigo);
                psDetalle.setDouble(3, cantidad);
                psDetalle.setDouble(4, precioUnitario);
                psDetalle.setDouble(5, subtotal);
                psDetalle.addBatch();
            }

            psDetalle.executeBatch();
            //Descontar stock
            // Descontar stock
                String sqlUpdateStock = "UPDATE productos SET stock = stock - ? WHERE codigo = ?";
                PreparedStatement psUpdateStock = con.prepareStatement(sqlUpdateStock);

                for (int i = 0; i < model.getRowCount(); i++) {
                    String codigo = model.getValueAt(i, 0).toString();
                    int cantidadVendida = Integer.parseInt(model.getValueAt(i, 2).toString());

                    psUpdateStock.setInt(1, cantidadVendida);
                    psUpdateStock.setString(2, codigo);
                    psUpdateStock.addBatch();
                }
            psUpdateStock.executeBatch();

            con.commit();

            
            JOptionPane.showMessageDialog(this, "Venta confirmada con éxito (ID: " + idVenta + ")");
            tablaVentaBackup = copiarTabla((DefaultTableModel) jTable1.getModel());
            totalVentaBackup = totalVenta;
            formaPagoBackup = formaPago;
            observacionesBackup = observacion;
            generarTicketVenta(idVenta);
            
            
            UtilsValidaciones.limpiarCampos(txtCodProd, txtNombreProd, txtPrecioUnitario, txtCantidad, txtObservaciones);
            AuditoriaLogger.registrarAccion(LoginSession.usuarioActual, "Agrego una venta con el código: " + idVenta + " )");
            txtCodProd.requestFocus();
            
            
            return idVenta;

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al confirmar venta:\n" + e.getMessage());
            return -1;
        }
    }


 private void configurarTeclasRapidas() {
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
            .put(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0), "CONFIRMAR_VENTA");
        getRootPane().getActionMap().put("CONFIRMAR_VENTA", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                
               //int idVenta = confirmarVentaEnBD();
               //if (idVenta != -1) generarTicketVenta(idVenta);
               
               confirmarVentaEnBD(); // ya se genera el ticket dentro del método
                UtilsValidaciones.limpiarCampos(txtCodProd, txtNombreProd, txtPrecioUnitario, txtCantidad, txtObservaciones);
                DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
                model.setRowCount(0);
                lblTotalFinal.setText("$0.00");
                txtCodProd.requestFocus();
                

                
            }
        });

       jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
    @Override
    public void mouseClicked(java.awt.event.MouseEvent evt) {
        int fila = jTable1.getSelectedRow();
        if (fila >= 0) {
            String codigo = jTable1.getValueAt(fila, 0).toString();
            txtCodProdEliminar.setText(codigo);
        }
    }
});


        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
    .put(KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0), "CANCELAR_VENTA");
getRootPane().getActionMap().put("CANCELAR_VENTA", new AbstractAction() {
    public void actionPerformed(ActionEvent e) {
        int confirmar = JOptionPane.showConfirmDialog(null, "¿Seguro que querés cancelar la venta actual?", "Cancelar venta", JOptionPane.YES_NO_OPTION);
        if (confirmar == JOptionPane.YES_OPTION) {
            UtilsValidaciones.limpiarCampos(txtCodProd, txtNombreProd, txtPrecioUnitario, txtCantidad, txtObservaciones);
            txtCodProd.requestFocus();
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setRowCount(0); // limpiar tabla
            lblTotalFinal.setText("$0.00");
        }
    }
});

    } 


/*private void generarTicketVenta(int idVenta) {
    try {
        Document document = new Document();

        // Ruta completa a tu carpeta "Tickets" en el escritorio
        String directorio = "C:\\Users\\juanp\\OneDrive\\Escritorio\\Tickets"; //esto va con un config despues
        java.io.File carpeta = new java.io.File(directorio);
        if (!carpeta.exists()) carpeta.mkdirs();

        String ruta = directorio + "\\venta_" + idVenta + ".pdf";
        PdfWriter.getInstance(document, new FileOutputStream(ruta));
        document.open();

        document.add(new Paragraph("TICKET DE VENTA"));
        document.add(new Paragraph("ID Venta: " + idVenta));
        document.add(new Paragraph("Fecha: " + java.time.LocalDateTime.now()));
        document.add(new Paragraph("\n"));

        PdfPTable table = new PdfPTable(4);
        table.addCell("Producto");
        table.addCell("Cantidad");
        table.addCell("P. Unitario");
        table.addCell("Subtotal");

        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            table.addCell(model.getValueAt(i, 1).toString());
            table.addCell(model.getValueAt(i, 2).toString());
            table.addCell(model.getValueAt(i, 3).toString());
            table.addCell(model.getValueAt(i, 4).toString());
        }

        document.add(table);
        document.add(new Paragraph("\nTotal: $" + String.format("%.2f", totalVentaBackup)));
        document.add(new Paragraph("Forma de pago: " + formaPagoBackup));
        document.add(new Paragraph("Observaciones: " + observacionesBackup));


        document.close();


    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error al generar PDF: " + e.getMessage());
    }
}*/  //Este código me enviaba los tickets a una carpeta de mi escritorio por lo tanto lo comento
 
 private void eliminarCantidadProducto() {
    String codigoEliminar = txtCodProdEliminar.getText().trim();
    int cantidadAEliminar = (int) jSpinner1.getValue();

    if (codigoEliminar.isEmpty() || cantidadAEliminar <= 0) {
        JOptionPane.showMessageDialog(this, "Ingresá un código válido y una cantidad mayor a 0.");
        return;
    }

    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
    boolean encontrado = false;

    for (int i = 0; i < model.getRowCount(); i++) {
        String codigoTabla = model.getValueAt(i, 0).toString();

        if (codigoTabla.equals(codigoEliminar)) {
            int cantidadActual = Integer.parseInt(model.getValueAt(i, 2).toString());
            double precioUnitario = Double.parseDouble(model.getValueAt(i, 3).toString());

            if (cantidadAEliminar >= cantidadActual) {
                model.removeRow(i);
            } else {
                int nuevaCantidad = cantidadActual - cantidadAEliminar;
                double nuevoSubtotal = nuevaCantidad * precioUnitario;

                model.setValueAt(nuevaCantidad, i, 2);
                model.setValueAt(nuevoSubtotal, i, 4);
            }

            encontrado = true;
            break;
        }
    }

    if (!encontrado) {
        JOptionPane.showMessageDialog(this, "Producto no encontrado en la tabla.");
    } else {
        actualizarTotal();
    }

    txtCodProdEliminar.setText("");
    jSpinner1.setValue(1);
}

       


private void generarTicketVenta(int idVenta) {
    try {
        // Elegir carpeta de guardado
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Seleccioná la carpeta donde guardar el ticket");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int resultado = chooser.showSaveDialog(this);
        if (resultado != JFileChooser.APPROVE_OPTION) {
            JOptionPane.showMessageDialog(this, "No se seleccionó ninguna carpeta. Operación cancelada.");
            return;
        }

        File carpetaSeleccionada = chooser.getSelectedFile();
        String ruta = carpetaSeleccionada.getAbsolutePath() + File.separator + "venta_" + idVenta + ".pdf";
        File archivoPDF = new File(ruta);

        // Crear documento
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(archivoPDF));
        document.open();

        document.add(new Paragraph("TICKET DE VENTA"));
        document.add(new Paragraph("ID Venta: " + idVenta));
        document.add(new Paragraph("Fecha: " + java.time.LocalDateTime.now()));
        document.add(new Paragraph("\n"));

        PdfPTable table = new PdfPTable(4);
        table.addCell("Producto");
        table.addCell("Cantidad");
        table.addCell("P. Unitario");
        table.addCell("Subtotal");

        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            table.addCell(model.getValueAt(i, 1).toString()); // Nombre
            table.addCell(model.getValueAt(i, 2).toString()); // Cantidad
            table.addCell(model.getValueAt(i, 3).toString()); // Precio
            table.addCell(model.getValueAt(i, 4).toString()); // Subtotal
        }

        document.add(table);
        document.add(new Paragraph("\nTotal: $" + String.format("%.2f", totalVentaBackup)));
        document.add(new Paragraph("Forma de pago: " + formaPagoBackup));
        document.add(new Paragraph("Observaciones: " + observacionesBackup));
        document.close();

        

        JOptionPane.showMessageDialog(this, "Ticket generado exitosamente en:\n" + ruta);

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error al generar PDF: " + e.getMessage());
    }
}





private String getFormaPagoSeleccionada() {
    if (rdbEfectivo.isSelected()) {
        return "Efectivo";
    } else if (rdbTarjeta.isSelected()) {
        return "Tarjeta";
    } else if (rdbTransferencia.isSelected()) {
        return "Transferencia";
    } else {
        return "";
    }
}



    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();
        txtCodProd = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtNombreProd = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtCantidad = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtPrecioUnitario = new javax.swing.JTextField();
        btnAgregarProd = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        lblTotalFinal = new javax.swing.JLabel();
        btnConfirmarVenta = new javax.swing.JButton();
        btnCancelarVenta = new javax.swing.JButton();
        btnEliminarProdTable = new javax.swing.JButton();
        txtCodProdEliminar = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSpinner1 = new javax.swing.JSpinner();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        rdbEfectivo = new javax.swing.JRadioButton();
        rdbTarjeta = new javax.swing.JRadioButton();
        rdbTransferencia = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();
        txtObservaciones = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        lblVueltoRes = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();

        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel3.setText("Código del producto");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, -1, -1));

        txtCodProd.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txtCodProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodProdActionPerformed(evt);
            }
        });
        getContentPane().add(txtCodProd, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, 160, -1));

        jLabel4.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel4.setText("Nombre del producto");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, -1, -1));

        txtNombreProd.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        getContentPane().add(txtNombreProd, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, 160, -1));

        jLabel5.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel5.setText("Cantidad");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 20, -1, -1));

        txtCantidad.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txtCantidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCantidadActionPerformed(evt);
            }
        });
        getContentPane().add(txtCantidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 40, 160, -1));

        jLabel6.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel6.setText("Precio unitario");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 80, -1, -1));

        txtPrecioUnitario.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        getContentPane().add(txtPrecioUnitario, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 100, 160, -1));

        btnAgregarProd.setBackground(new java.awt.Color(0, 204, 51));
        btnAgregarProd.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        btnAgregarProd.setForeground(new java.awt.Color(255, 255, 255));
        btnAgregarProd.setText("Agregar (ENTER)");
        btnAgregarProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarProdActionPerformed(evt);
            }
        });
        getContentPane().add(btnAgregarProd, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 150, 190, 40));

        jLabel7.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel7.setText("Tabla de productos");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 200, -1, 30));

        lblTotalFinal.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        lblTotalFinal.setText("$0.00");
        getContentPane().add(lblTotalFinal, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 400, 150, -1));

        btnConfirmarVenta.setBackground(new java.awt.Color(0, 204, 51));
        btnConfirmarVenta.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        btnConfirmarVenta.setForeground(new java.awt.Color(255, 255, 255));
        btnConfirmarVenta.setText("Confirmar venta  (F1)");
        btnConfirmarVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmarVentaActionPerformed(evt);
            }
        });
        getContentPane().add(btnConfirmarVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 530, 210, 60));

        btnCancelarVenta.setBackground(new java.awt.Color(204, 0, 0));
        btnCancelarVenta.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        btnCancelarVenta.setForeground(new java.awt.Color(255, 255, 255));
        btnCancelarVenta.setText("Cancelar (F3)");
        getContentPane().add(btnCancelarVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 530, 170, 60));

        btnEliminarProdTable.setBackground(new java.awt.Color(204, 0, 0));
        btnEliminarProdTable.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        btnEliminarProdTable.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminarProdTable.setText("Eliminar producto");
        btnEliminarProdTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarProdTableActionPerformed(evt);
            }
        });
        getContentPane().add(btnEliminarProdTable, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 460, 150, -1));

        txtCodProdEliminar.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        getContentPane().add(txtCodProdEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 420, 150, 30));

        jLabel2.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel2.setText("Código");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 400, -1, -1));
        getContentPane().add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 610, 650, -1));

        jSpinner1.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        getContentPane().add(jSpinner1, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 420, 70, 30));

        jTable1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 230, 600, 150));

        rdbEfectivo.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        rdbEfectivo.setText("Efectivo");
        getContentPane().add(rdbEfectivo, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 40, -1, -1));

        rdbTarjeta.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        rdbTarjeta.setText("Tarjeta");
        getContentPane().add(rdbTarjeta, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 70, -1, -1));

        rdbTransferencia.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        rdbTransferencia.setText("Transferencia");
        getContentPane().add(rdbTransferencia, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 100, 130, -1));

        jLabel1.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel1.setText("Forma de pago");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 20, 210, 20));
        getContentPane().add(txtObservaciones, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 160, 210, 30));

        jLabel9.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel9.setText("Observaciones");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 140, -1, -1));
        getContentPane().add(lblVueltoRes, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 460, 80, 30));

        jLabel11.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel11.setText("Total:");
        getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 394, -1, 40));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtCodProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodProdActionPerformed
        
    }//GEN-LAST:event_txtCodProdActionPerformed

    private void btnAgregarProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarProdActionPerformed
        // TODO add your handling code here:
        agregarProductoATabla();
    }//GEN-LAST:event_btnAgregarProdActionPerformed

    private void txtCantidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCantidadActionPerformed
        // TODO add your handling code here:
        agregarProductoATabla();
    }//GEN-LAST:event_txtCantidadActionPerformed

    
  
    
    private void btnConfirmarVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmarVentaActionPerformed
          int id = confirmarVentaEnBD(); // Usamos la respuesta directamente
    if (id != -1) {
        generarTicketVenta(id);
    } else {
        JOptionPane.showMessageDialog(this, "No se pudo confirmar la venta. No se generará el ticket.");
        
    }
    }//GEN-LAST:event_btnConfirmarVentaActionPerformed

    private void btnEliminarProdTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarProdTableActionPerformed
        // TODO add your handling code here:
        eliminarCantidadProducto();
    }//GEN-LAST:event_btnEliminarProdTableActionPerformed

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
            java.util.logging.Logger.getLogger(ventanaAgregarVenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ventanaAgregarVenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ventanaAgregarVenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ventanaAgregarVenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ventanaAgregarVenta().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarProd;
    private javax.swing.JButton btnCancelarVenta;
    private javax.swing.JButton btnConfirmarVenta;
    private javax.swing.JButton btnEliminarProdTable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblTotalFinal;
    private javax.swing.JLabel lblVueltoRes;
    private javax.swing.JRadioButton rdbEfectivo;
    private javax.swing.JRadioButton rdbTarjeta;
    private javax.swing.JRadioButton rdbTransferencia;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextField txtCodProd;
    private javax.swing.JTextField txtCodProdEliminar;
    private javax.swing.JTextField txtNombreProd;
    private javax.swing.JTextField txtObservaciones;
    private javax.swing.JTextField txtPrecioUnitario;
    // End of variables declaration//GEN-END:variables
}
