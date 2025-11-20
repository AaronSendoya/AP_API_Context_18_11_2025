package com.example.api_conexion;

import com.google.gson.annotations.SerializedName;

public class Producto {

    // @SerializedName vincula el campo del JSON con tu variable Java
    @SerializedName("idProducto")
    private int idProducto;

    @SerializedName("idEmpresa")
    private int idEmpresa;

    @SerializedName("producto1") // El nombre raro que viene de tu API
    private String nombreProducto;

    @SerializedName("precio")
    private double precio;

    @SerializedName("unidadMedida")
    private String unidadMedida;

    @SerializedName("categoria")
    private String categoria;

    // Constructor vacío (necesario para Retrofit/Gson)
    public Producto() {}

    // Constructor para enviar datos (POST)
    public Producto(int idEmpresa, String nombreProducto, double precio, String unidadMedida, String categoria) {
        this.idEmpresa = idEmpresa;
        this.nombreProducto = nombreProducto;
        this.precio = precio;
        this.unidadMedida = unidadMedida;
        this.categoria = categoria;
    }

    // Getters y Setters
    public int getIdProducto() { return idProducto; }
    public void setIdProducto(int idProducto) { this.idProducto = idProducto; }

    public int getIdEmpresa() { return idEmpresa; }
    public void setIdEmpresa(int idEmpresa) { this.idEmpresa = idEmpresa; }

    public String getNombreProducto() { return nombreProducto; }
    public void setNombreProducto(String nombreProducto) { this.nombreProducto = nombreProducto; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public String getUnidadMedida() { return unidadMedida; }
    public void setUnidadMedida(String unidadMedida) { this.unidadMedida = unidadMedida; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }


}