package com.example.api_conexion;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ApiConsumer.ApiConsumerListener {

    Button btnIniciar;

    private EditText edtNombre, edtDescripcion, edtPrecio, edtStock, edtMedida, edtFecha, edtCategoria;
    private RecyclerView recyclerView;
    private ProductoAdapter adapter;
    private List<Producto> listaProductos = new ArrayList<>();
    private Button btnAgregar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnIniciar = findViewById(R.id.init);

        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.layaout_datos);
                inicializarVistaDatos();
            }
        });
    }

    private void inicializarVistaDatos() {
        edtNombre = findViewById(R.id.edt_nombre);
        edtDescripcion = findViewById(R.id.edt_descripcion);
        edtPrecio = findViewById(R.id.edt_precio);
        edtStock = findViewById(R.id.edt_stock);
        edtMedida = findViewById(R.id.edt_medida);
        edtFecha = findViewById(R.id.edt_fecha);
        edtCategoria = findViewById(R.id.edt_categoria);
        recyclerView = findViewById(R.id.recyclerViewProductos);
        btnAgregar = findViewById(R.id.btn_agregar);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ProductoAdapter(listaProductos);
        recyclerView.setAdapter(adapter);

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarProductoLocalmente();
            }
        });

        cargarDatosDeApi();
    }

    private void agregarProductoLocalmente() {
        String nombre = edtNombre.getText().toString();
        String descripcion = edtDescripcion.getText().toString();
        String precioStr = edtPrecio.getText().toString();
        String stockStr = edtStock.getText().toString();

        if (nombre.isEmpty() || precioStr.isEmpty() || stockStr.isEmpty()) {
            Toast.makeText(this, "Nombre, Precio y Stock son obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double precio = Double.parseDouble(precioStr);
            int stock = Integer.parseInt(stockStr);

            if (descripcion.isEmpty()) {
                descripcion = "Sin descripción";
            }

            Producto nuevoProducto = new Producto(nombre, descripcion, precio, stock);

            listaProductos.add(0, nuevoProducto);
            adapter.notifyItemInserted(0);
            recyclerView.scrollToPosition(0);

            edtNombre.setText("");
            edtDescripcion.setText("");
            edtPrecio.setText("");
            edtStock.setText("");
            edtMedida.setText("");
            edtFecha.setText("");
            edtCategoria.setText("");

            edtNombre.requestFocus();

            Toast.makeText(this, "Producto agregado localmente", Toast.LENGTH_SHORT).show();

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Precio o Stock no son números válidos", Toast.LENGTH_SHORT).show();
        }
    }

    private void cargarDatosDeApi() {
        Toast.makeText(this, "Cargando productos...", Toast.LENGTH_SHORT).show();
        new ApiConsumer(this).execute();
    }


    @Override
    public void onDataLoaded(List<Producto> productos) {
        Log.d("MainActivity", "Productos cargados: " + productos.size());
        Toast.makeText(this, "Productos cargados", Toast.LENGTH_SHORT).show();

        listaProductos.clear();
        listaProductos.addAll(productos);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onError(Exception e) {
        Log.e("MainActivity", "Error al cargar productos", e);
        Toast.makeText(this, "Error al cargar productos: " + e.getMessage(), Toast.LENGTH_LONG).show();
    }
}