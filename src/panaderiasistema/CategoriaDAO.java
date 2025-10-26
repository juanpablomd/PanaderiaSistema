package panaderiasistema;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import panaderiasistema.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author juanp
 */
public class CategoriaDAO {
    public List<Categoria> obtenerCategorias() {
    List<Categoria> lista = new ArrayList<>();

    String sql = "SELECT IdCategoria, Nombre FROM categorias";

    try (Connection con = DBConnection.getConnection();
         PreparedStatement stmt = con.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            int id = rs.getInt("IdCategoria");
            String nombre = rs.getString("Nombre");
            lista.add(new Categoria(id, nombre));
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return lista;
    }
    
    
    public boolean agregarCategoria(String nombre) {
    String sql = "INSERT INTO categorias (Nombre) VALUES (?)";
    try (Connection con = DBConnection.getConnection();
         PreparedStatement stmt = con.prepareStatement(sql)) {

        stmt.setString(1, nombre);
        stmt.executeUpdate();
        return true;

    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

public boolean eliminarCategoriaPorId(int idCategoria) {
    String sql = "DELETE FROM categorias WHERE IdCategoria = ?";
    try (Connection con = DBConnection.getConnection();
         PreparedStatement stmt = con.prepareStatement(sql)) {

        stmt.setInt(1, idCategoria);
        int filas = stmt.executeUpdate();
        return filas > 0;

    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

   
}


