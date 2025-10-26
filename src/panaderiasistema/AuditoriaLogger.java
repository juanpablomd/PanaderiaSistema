/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package panaderiasistema;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.sql.Timestamp;
/**
 *
 * @author juanp
 */
public class AuditoriaLogger {
        public static void registrarAccion(String usuario, String accion) {
        String sql = "INSERT INTO auditoria (usuario, accion, fecha_hora) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario);
            stmt.setString(2, accion);
            stmt.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Podés loguear esto también si querés o mostrar un JOptionPane opcional
        }
    }
}
