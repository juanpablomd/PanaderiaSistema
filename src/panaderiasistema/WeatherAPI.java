
package panaderiasistema;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class WeatherAPI {
    private static final String API_KEY = "48a648f899d170be9cb741165b1a2237"; // ← pegá tu API KEY acá

    public static String obtenerClima(String ciudad) {
        try {
            // Codificar espacios y caracteres especiales
            String ciudadCodificada = ciudad.replace(" ", "%20");
            String urlStr = "https://api.openweathermap.org/data/2.5/weather?q=" 
                    + ciudadCodificada + ",AR"
                    + "&appid=" + API_KEY 
                    + "&units=metric&lang=es";

            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // Verificar respuesta HTTP
            int status = conn.getResponseCode();
            if (status != 200) {
                BufferedReader errorReader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                StringBuilder errorMsg = new StringBuilder();
                String linea;
                while ((linea = errorReader.readLine()) != null) {
                    errorMsg.append(linea);
                }
                errorReader.close();
                System.err.println("❌ Error HTTP " + status + ": " + errorMsg.toString());
                return "❌ No se pudo obtener el clima.";
            }

            BufferedReader lector = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder resultado = new StringBuilder();
            String linea;
            while ((linea = lector.readLine()) != null) {
                resultado.append(linea);
            }
            lector.close();

            JSONObject json = new JSONObject(resultado.toString());
            double temp = json.getJSONObject("main").getDouble("temp");
            String descripcion = json.getJSONArray("weather").getJSONObject(0).getString("description");

            return "🌤 Clima en " + ciudad + ": " + temp + "°C, " + descripcion;

        } catch (Exception e) {
            e.printStackTrace();
            return "❌ Error al obtener el clima.";
        }
    }
}


