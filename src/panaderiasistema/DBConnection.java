package panaderiasistema; 

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.InputStream;
import java.util.Properties;

public class DBConnection {
    private static Properties props = new Properties();

    static {
        // La clase DBConnection busca el archivo db.properties en la ruta de clases (classpath)
        // Si db.properties está directamente en la carpeta 'src'
try (InputStream input = DBConnection.class.getResourceAsStream("db.properties")) {
            if (input == null) {
                System.out.println("❌ Error: No se pudo encontrar el archivo 'db/db.properties'.");
            }
            props.load(input);
        } catch (Exception ex) {
            System.err.println("Error al cargar la configuración de la DB: " + ex.getMessage());
            // Manejo de errores (la aplicación fallará si no se conecta)
        }
    }

    public static Connection getConnection() throws SQLException {
        // Lee los valores del objeto Properties cargado
        String URL = props.getProperty("db.url");
        String USER = props.getProperty("db.user");
        String PASS = props.getProperty("db.pass");
        
        return DriverManager.getConnection(URL, USER, PASS);
    }
}