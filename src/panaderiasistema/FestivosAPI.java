
package panaderiasistema;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.List;
 
public class FestivosAPI {
    public static void main(String[] args) {
        try {
            // Paso 1: Conectarse a la API
            URL url = new URL("https://date.nager.at/api/v3/PublicHolidays/2025/AR");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // Paso 2: Leer la respuesta
            BufferedReader in = new BufferedReader(
                new InputStreamReader(conn.getInputStream())
            );
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = in.readLine()) != null) {
                response.append(line);
            }

            in.close();
            conn.disconnect();

            // Paso 3: Convertir JSON en objetos Java
            Gson gson = new Gson();
            List<Feriado> feriados = gson.fromJson(response.toString(),
                    new TypeToken<List<Feriado>>() {}.getType());

            // Paso 4: Mostrar los feriados
            System.out.println("📅 Feriados en Argentina (2025):");
            for (Feriado f : feriados) {
                System.out.println(" - " + f);
            }

        } catch (Exception e) {
            System.err.println("Error al consultar los feriados: " + e.getMessage());
        }
    }
}
