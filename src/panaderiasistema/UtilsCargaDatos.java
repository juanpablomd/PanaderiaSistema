
package panaderiasistema;

import java.sql.*;
import javax.swing.*;
import java.util.List;

public class UtilsCargaDatos {
    
    public static void cargarCategorias(JComboBox<Categoria> comboBox) {
        CategoriaDAO dao = new CategoriaDAO();
        List<Categoria> categorias = dao.obtenerCategorias();

        comboBox.removeAllItems();
        for (Categoria cat : categorias) {
            comboBox.addItem(cat);
        }
    }

    public static void cargarProveedores(JComboBox<Proveedores> comboBox) {
        ProveedoresDAO dao = new ProveedoresDAO(); 
        List<Proveedores> proveedores = dao.obtenerProveedores();

        comboBox.removeAllItems();
        for (Proveedores prov : proveedores) {
            comboBox.addItem(prov);
        }
    }
}
