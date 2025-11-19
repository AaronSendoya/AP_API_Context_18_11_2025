package com.example.api_conexion;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private List<Producto> listaProductos = new ArrayList<>();
    private ProductoAdapter adapter;

    private String apiUrl = "http://demoapi.somee.com/api/productos";

    private EditText txtProducto, txtPrecio, txtUnidad, txtCategoria;
    private Button btnAgregar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_main);

        Button btnInit = findViewById(R.id.init);
        btnInit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarPantallaDatos();
            }
        });
    }

    private void cargarPantallaDatos() {
        setContentView(R.layout.layaout_datos);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());

        listView = findViewById(R.id.recyclerViewProductos);
        txtProducto = findViewById(R.id.txtProducto);
        txtPrecio = findViewById(R.id.txtPrecio);
        txtUnidad = findViewById(R.id.txtUnidad);
        txtCategoria = findViewById(R.id.txtCategoria);
        btnAgregar = findViewById(R.id.btn_agregar);

        cargarProductos();

        if (btnAgregar != null) {
            btnAgregar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    agregarProducto();
                }
            });
        }
    }

    private void agregarProducto() {
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setDoOutput(true);

            JSONObject nuevo = new JSONObject();
            nuevo.put("idEmpresa", 1);
            nuevo.put("producto1", txtProducto.getText().toString());

            double precioVal = 0.0;
            try {
                precioVal = Double.parseDouble(txtPrecio.getText().toString());
            } catch (NumberFormatException e) {
                precioVal = 0.0;
            }
            nuevo.put("precio", precioVal);

            nuevo.put("unidadMedida", txtUnidad.getText().toString());
            nuevo.put("categoria", txtCategoria.getText().toString());

            con.getOutputStream().write(nuevo.toString().getBytes());

            int respuesta = con.getResponseCode();

            if (respuesta == HttpURLConnection.HTTP_OK || respuesta == HttpURLConnection.HTTP_CREATED) {
                runOnUiThread(() -> {
                    Toast.makeText(MainActivity.this, "Agregado correctamente", Toast.LENGTH_SHORT).show();
                    cargarProductos();

                    txtProducto.setText("");
                    txtPrecio.setText("");
                    txtUnidad.setText("");
                    txtCategoria.setText("");
                    txtProducto.requestFocus();
                });
            } else {
                final int codigo = respuesta;
                runOnUiThread(() -> Toast.makeText(MainActivity.this, "Error código: " + codigo, Toast.LENGTH_SHORT).show());
            }

        } catch (Exception e) {
            e.printStackTrace();
            final String errorMsg = e.getMessage();
            runOnUiThread(() -> Toast.makeText(MainActivity.this, "Error al agregar: " + errorMsg, Toast.LENGTH_SHORT).show());
        }
    }

    private void cargarProductos() {
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder respuesta = new StringBuilder();
            String linea;

            while ((linea = reader.readLine()) != null) {
                respuesta.append(linea);
            }

            reader.close();

            listaProductos.clear();

            JSONArray obj = new JSONArray(respuesta.toString());

            for (int i = 0; i < obj.length(); i++) {
                JSONObject productoJson = obj.getJSONObject(i);
                Producto p = new Producto();

                p.setIdProducto(productoJson.optInt("idProducto", 0));
                p.setProducto1(productoJson.optString("producto1", "Sin nombre"));
                p.setPrecio(productoJson.optDouble("precio", 0.0));
                p.setUnidadMedida(productoJson.optString("unidadMedida", "ND"));
                p.setCategoria(productoJson.optString("categoria", "ND"));

                listaProductos.add(p);
            }

            runOnUiThread(() -> {
                if (adapter == null) {
                    adapter = new ProductoAdapter(MainActivity.this, listaProductos);
                    listView.setAdapter(adapter);
                } else {
                    adapter.notifyDataSetChanged();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            final String errorMsg = e.getMessage();
            runOnUiThread(() -> Toast.makeText(MainActivity.this, "Error al cargar: " + errorMsg, Toast.LENGTH_LONG).show());
        }
    }
}