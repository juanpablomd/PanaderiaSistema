package panaderiasistema;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProveedoresDAO {

    public List<Proveedores> obtenerProveedores() {
        List<Proveedores> lista = new ArrayList<>();

        String sql = "SELECT IdProveedor, Nombre FROM proveedores";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("IdProveedor");
                String nombre = rs.getString("Nombre");
                lista.add(new Proveedores(id, nombre));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
}
