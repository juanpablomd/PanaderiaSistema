package panaderiasistema;

import javax.swing.*;
import java.sql.*;
import java.awt.Component; 
import javax.swing.table.DefaultTableModel;
import java.util.Vector;

public class UtilsBusquedas {

    public static void buscarProductoPorCodigo(
            String codigo,
            JTextField txtNombre,
            JTextField txtPrecio,
            JTextField txtStock,
            JComboBox<Categoria> cmbCategorias,
            JComboBox<Proveedores> cmbProveedores,
            Component parentComponent
    ) {
        if (codigo.isEmpty()) {
            UtilsValidaciones.mostrarError("Ingrese un código para buscar.", parentComponent);
            return;
        }

        try (Connection con = DBConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(
                     "SELECT Nombre, Precio, Stock, IdCategoria, IdProveedor FROM productos WHERE Codigo = ?")) {
            stmt.setString(1, codigo);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                txtNombre.setText(rs.getString("Nombre"));
                txtPrecio.setText(String.valueOf(rs.getDouble("Precio")));
                txtStock.setText(String.valueOf(rs.getInt("Stock")));

                int idCategoria = rs.getInt("IdCategoria");
                int idProveedor = rs.getInt("IdProveedor");

                for (int i = 0; i < cmbCategorias.getItemCount(); i++) {
                    if (cmbCategorias.getItemAt(i).getIdCategoria() == idCategoria) {
                        cmbCategorias.setSelectedIndex(i);
                        break;
                    }
                }

                for (int i = 0; i < cmbProveedores.getItemCount(); i++) {
                    if (cmbProveedores.getItemAt(i).getIdProveedor() == idProveedor) {
                        cmbProveedores.setSelectedIndex(i);
                        break;
                    }
                }
            } else {
                UtilsValidaciones.mostrarError("Producto no encontrado.", parentComponent);
            }

        } catch (SQLException e) {
            UtilsValidaciones.mostrarError("Error al buscar producto: " + e.getMessage(), parentComponent);
        }
    }

    public static void buscarYMostrarEnTabla(String valor, JTable tabla, Component parentComponent) {
        DefaultTableModel model = (DefaultTableModel) tabla.getModel();
        model.setRowCount(0);

        String sqlCodigo = "SELECT Codigo, Nombre, Precio, Stock FROM productos WHERE Codigo = ?";
        String sqlNombre = "SELECT Codigo, Nombre, Precio, Stock FROM productos WHERE LOWER(Nombre) LIKE ?";

        try (Connection con = DBConnection.getConnection()) {
            try (PreparedStatement stmt = con.prepareStatement(sqlCodigo)) {
                stmt.setString(1, valor);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    Vector<Object> fila = new Vector<>();
                    fila.add(rs.getString("Codigo"));
                    fila.add(rs.getString("Nombre"));
                    fila.add(rs.getDouble("Precio"));
                    fila.add(rs.getInt("Stock"));
                    model.addRow(fila);
                }
            }

            if (model.getRowCount() == 0) {
                try (PreparedStatement stmt2 = con.prepareStatement(sqlNombre)) {
                    stmt2.setString(1, "%" + valor.toLowerCase() + "%");
                    ResultSet rs2 = stmt2.executeQuery();

                    while (rs2.next()) {
                        Vector<Object> fila = new Vector<>();
                        fila.add(rs2.getString("Codigo"));
                        fila.add(rs2.getString("Nombre"));
                        fila.add(rs2.getDouble("Precio"));
                        fila.add(rs2.getInt("Stock"));
                        model.addRow(fila);
                    }
                }
            }

            if (model.getRowCount() == 0) {
                UtilsValidaciones.mostrarError("No se encontraron coincidencias.", parentComponent);
            }

        } catch (SQLException e) {
            UtilsValidaciones.mostrarError("Error en la búsqueda: " + e.getMessage(), parentComponent);
        }
    }

    public static void buscarYMostrarCompleto(String valor, JTable tabla, Component parentComponent) {
        DefaultTableModel model = (DefaultTableModel) tabla.getModel();
        model.setRowCount(0);

        String sqlBase = "SELECT p.Codigo, p.Nombre, c.Nombre AS Categoria, pr.Nombre AS Proveedor, p.Precio, p.Stock " +
                         "FROM productos p " +
                         "JOIN categorias c ON p.IdCategoria = c.IdCategoria " +
                         "JOIN proveedores pr ON p.IdProveedor = pr.IdProveedor ";

        String sqlCodigo = sqlBase + "WHERE p.Codigo = ?";
        String sqlNombre = sqlBase + "WHERE LOWER(p.Nombre) LIKE ?";

        try (Connection con = DBConnection.getConnection()) {
            boolean encontrado = false;

            try (PreparedStatement stmt = con.prepareStatement(sqlCodigo)) {
                stmt.setString(1, valor);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    Object[] fila = {
                        rs.getString("Codigo"),
                        rs.getString("Nombre"),
                        rs.getString("Categoria"),
                        rs.getString("Proveedor"),
                        rs.getDouble("Precio"),
                        rs.getInt("Stock")
                    };
                    model.addRow(fila);
                    encontrado = true;
                }
            }

            if (!encontrado) {
                try (PreparedStatement stmt2 = con.prepareStatement(sqlNombre)) {
                    stmt2.setString(1, "%" + valor.toLowerCase() + "%");
                    ResultSet rs2 = stmt2.executeQuery();
                    while (rs2.next()) {
                        Object[] fila = {
                            rs2.getString("Codigo"),
                            rs2.getString("Nombre"),
                            rs2.getString("Categoria"),
                            rs2.getString("Proveedor"),
                            rs2.getDouble("Precio"),
                            rs2.getInt("Stock")
                        };
                        model.addRow(fila);
                        encontrado = true;
                    }
                }
            }

            if (!encontrado) {
                UtilsValidaciones.mostrarError("No se encontraron coincidencias.", parentComponent);
            }

        } catch (SQLException e) {
            UtilsValidaciones.mostrarError("Error en la búsqueda: " + e.getMessage(), parentComponent);
        }
    }

    /*public static boolean buscarProductoSimple(
            String codigo,
            JTextField txtNombre,
            JTextField txtPrecio,
            Component parentComponent
    ) {
        if (codigo.isEmpty()) {
            UtilsValidaciones.mostrarError("Ingrese un código de producto.", parentComponent);
            return false;
        }

        try (Connection con = DBConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(
                     "SELECT Nombre, Precio FROM productos WHERE Codigo = ?")) {

            stmt.setString(1, codigo);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                txtNombre.setText(rs.getString("Nombre"));
                txtPrecio.setText(String.valueOf(rs.getDouble("Precio")));
                return true;
            } else {
                UtilsValidaciones.mostrarError("Producto no encontrado.", parentComponent);
                txtNombre.setText("");
                txtPrecio.setText("");
                return false;
            }

        } catch (SQLException e) {
            UtilsValidaciones.mostrarError("Error al buscar producto: " + e.getMessage(), parentComponent);
            return false;
        }
    }*/

   public static void buscarProductoSimple(String codigo, JTextField txtNombre, JTextField txtPrecio, Component parentComponent) {
    if (codigo.isEmpty()) return;

    String sql = "SELECT Nombre, Precio FROM productos WHERE Codigo = ?";

    try (Connection con = DBConnection.getConnection();
         PreparedStatement stmt = con.prepareStatement(sql)) {
        
        stmt.setString(1, codigo);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            txtNombre.setText(rs.getString("Nombre"));
            txtPrecio.setText(String.valueOf(rs.getDouble("Precio")));
        } else {
            txtNombre.setText("");
            txtPrecio.setText("");
            UtilsValidaciones.mostrarError("Producto no encontrado.", parentComponent);
        }

    } catch (SQLException e) {
        UtilsValidaciones.mostrarError("Error en la búsqueda: " + e.getMessage(), parentComponent);
    }
}

}
