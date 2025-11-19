package com.example.api_conexion;

public class Producto {
    private int idProducto;
    private int idEmpresa;
    private String producto1;
    private double precio;
    private String unidadMedida;
    private String categoria;
//    private String nombre;
//    private String descripcion;

    private int stock;

    public Producto(){}

    public Producto(String nombre, String descripcion, double precio, int stock) {
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getProducto1() {
        return producto1;
    }

    public void setProducto1(String producto1) {
        this.producto1 = producto1;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

//    public Producto(String nombre, String descripcion, double precio, int stock) {
//        this.nombre = nombre;
//        this.descripcion = descripcion;
//        this.precio = precio;
//        this.stock = stock;
//    }

    // Getters

}
