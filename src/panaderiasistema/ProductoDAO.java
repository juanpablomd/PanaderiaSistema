package panaderiasistema;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import panaderiasistema.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.sql.*;
import java.util.ArrayList;

public class ProductoDAO {
    public List<Producto> obtenerTodosProductos() {
        List<Producto> lista = new ArrayList();
        String sql = "SELECT p.Codigo, p.Nombre, c.Nombre AS Categoria, pv.Nombre AS Proveedor, p.Precio, p.Stock " +
                "FROM Productos p " +
                "JOIN categorias c ON p.IdCategoria = c.IdCategoria" +
                "JOIN proveedores pv ON p.IdProveedor = pv.IdProveedor";
        
        try (Connection con = DBConnection.getConnection();
        PreparedStatement stmt = con.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery()) {
            
            while(rs.next()){
                Producto p = new Producto();
                p.setCodigo(rs.getString("Codigo"));
                p.setNombre(rs.getString("Nombre"));
                p.setNombreCategoria(rs.getString("Categoria"));
                p.setNombreProveedor(rs.getString("Proveedor"));
                p.setPrecio(rs.getDouble("Precio"));
                p.setStock(rs.getInt("Stock"));
                lista.add(p);
            }
            
        } catch(SQLException e){
            e.printStackTrace();
        }
        return lista;
    }
            
    
     public void agregarProducto(Producto producto) {
        String sql = "INSERT INTO productos (Codigo, Nombre, IdCategoria, IdProveedor, Precio, Stock) VALUES (?, ?, ?, ?, ?, ?)";


         try (Connection con = DBConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

                stmt.setString(1, producto.getCodigo());
                stmt.setString(2, producto.getNombre());
                stmt.setInt(3, producto.getIdCategoria());
                stmt.setInt(4, producto.getIdProveedor());
                stmt.setDouble(5, producto.getPrecio());
                stmt.setInt(6, producto.getStock());
                

            stmt.executeUpdate();
            System.out.println("✅ Producto agregado correctamente.");
        } catch (SQLException e) {
            System.out.println("❌ Error al guardar producto:");
            e.printStackTrace();
        }
    }
}
