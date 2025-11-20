package com.example.api_conexion;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private List<Producto> listaProductos = new ArrayList<>();
    private ProductoAdapter adapter;

    private EditText txtProducto, txtPrecio, txtUnidad, txtCategoria;
    private Button btnAgregar;

    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_main);

        // Inicializamos Retrofit
        apiService = RetrofitClient.getApiService();

        Button btnInit = findViewById(R.id.init);
        btnInit.setOnClickListener(v -> cargarPantallaDatos());
    }

    private void cargarPantallaDatos() {
        setContentView(R.layout.layaout_datos); // Nota: Te sugiero renombrar este archivo a layout_datos en el futuro

        listView = findViewById(R.id.recyclerViewProductos);
        txtProducto = findViewById(R.id.txtProducto);
        txtPrecio = findViewById(R.id.txtPrecio);
        txtUnidad = findViewById(R.id.txtUnidad);
        txtCategoria = findViewById(R.id.txtCategoria);
        btnAgregar = findViewById(R.id.btn_agregar);

        // Cargar datos al iniciar esta vista
        cargarProductos();

        if (btnAgregar != null) {
            btnAgregar.setOnClickListener(v -> agregarProducto());
        }
    }

    private void agregarProducto() {
        // 1. Obtener datos de la UI
        String prodName = txtProducto.getText().toString();
        String prodUnidad = txtUnidad.getText().toString();
        String prodCat = txtCategoria.getText().toString();
        String precioStr = txtPrecio.getText().toString();

        if (prodName.isEmpty() || precioStr.isEmpty()) {
            Toast.makeText(this, "Nombre y precio son obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }

        double precioVal;
        try {
            precioVal = Double.parseDouble(precioStr);
        } catch (NumberFormatException e) {
            precioVal = 0.0;
        }

        // 2. Crear el objeto Producto
        Producto nuevoProducto = new Producto(1, prodName, precioVal, prodUnidad, prodCat);

        // 3. Llamada POST con Retrofit
        apiService.agregarProducto(nuevoProducto).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Agregado correctamente", Toast.LENGTH_SHORT).show();

                    txtProducto.setText("");
                    txtPrecio.setText("");
                    txtUnidad.setText("");
                    txtCategoria.setText("");
                    txtProducto.requestFocus();

                    cargarProductos();
                } else {
                    Toast.makeText(MainActivity.this, "Error código: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Fallo de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void cargarProductos() {
        apiService.obtenerProductos().enqueue(new Callback<List<Producto>>() {
            @Override
            public void onResponse(Call<List<Producto>> call, Response<List<Producto>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listaProductos.clear();
                    listaProductos.addAll(response.body());

                    if (adapter == null) {
                        adapter = new ProductoAdapter(MainActivity.this, listaProductos);
                        listView.setAdapter(adapter);
                    } else {
                        adapter.notifyDataSetChanged();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Error al obtener datos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Producto>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error de red: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}