package com.example.api_conexion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ProductoAdapter extends ArrayAdapter<Producto> {

    public ProductoAdapter(Context context, List<Producto> listaProductos) {
        super(context, 0, listaProductos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Producto producto = getItem(position);


        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.fila_producto, parent, false);
        }


        TextView txtNombre = convertView.findViewById(R.id.lblNombreProducto);
        TextView txtPrecio = convertView.findViewById(R.id.lblPrecioProducto);


        if (producto != null) {
            if (txtNombre != null) {
                txtNombre.setText(producto.getNombreProducto());
            }
            if (txtPrecio != null) {
                txtPrecio.setText("Precio: " + producto.getPrecio() + " Bs");
            }
        }

        return convertView;
    }
}