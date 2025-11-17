package com.example.api_conexion;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ApiConsumer extends AsyncTask<Void, Void, List<Producto>> {

    private static final String API_URL = "http://demoapi.somee.com/api/productos";
    private static final String TAG = "ApiConsumer";


    public interface ApiConsumerListener {
        void onDataLoaded(List<Producto> productos);
        void onError(Exception e);
    }

    private ApiConsumerListener listener;

    public ApiConsumer(ApiConsumerListener listener) {
        this.listener = listener;
    }

    @Override
    protected List<Producto> doInBackground(Void... voids) {
        List<Producto> productos = new ArrayList<>();
        StringBuilder result = new StringBuilder();

        try {
            URL url = new URL(API_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                reader.close();

                // Parsear el JSON
                JSONArray jsonArray = new JSONArray(result.toString());
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject obj = jsonArray.getJSONObject(i);

                    String nombre = obj.optString("producio", "N/A");
                    String descripcion = obj.optString("descripcion", "N/A");
                    double precio = obj.optDouble("precio", 0.0);
                    int stock = obj.optInt("cantidadStock", 0);

                    productos.add(new Producto(nombre, descripcion, precio, stock));
                }
                return productos;
            } else {
                Log.e(TAG, "Error en la conexión: " + responseCode);
                return null;
            }
        } catch (Exception e) {
            Log.e(TAG, "Error al obtener datos de la API", e);
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<Producto> result) {
        if (result != null) {
            if (listener != null) {
                listener.onDataLoaded(result);
            }
        } else {

            if (listener != null) {
                listener.onError(new Exception("No se pudieron cargar los datos de la API."));
            }
        }
    }
}
