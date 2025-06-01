package com.gpaweb.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class ProductoAgricola {
    private int id;
    private String nombre;
    private String descripcion;
    private String agricultor;
    private BigDecimal precio;
    private int cantidad;
    private String rutaImagen; // Ruta relativa a la imagen, ej: "images/tomate.jpg"
    private Timestamp createdAt;
    private Timestamp updatedAt;

    // Constructores
    public ProductoAgricola() {
    }

    public ProductoAgricola(String nombre, String descripcion, String agricultor, BigDecimal precio, int cantidad, String rutaImagen) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.agricultor = agricultor;
        this.precio = precio;
        this.cantidad = cantidad;
        this.rutaImagen = rutaImagen;
    }
    
    public ProductoAgricola(int id, String nombre, String descripcion, String agricultor, BigDecimal precio, int cantidad, String rutaImagen) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.agricultor = agricultor;
        this.precio = precio;
        this.cantidad = cantidad;
        this.rutaImagen = rutaImagen;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getAgricultor() { return agricultor; }
    public void setAgricultor(String agricultor) { this.agricultor = agricultor; }

    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public String getRutaImagen() { return rutaImagen; }
    public void setRutaImagen(String rutaImagen) { this.rutaImagen = rutaImagen; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public Timestamp getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Timestamp updatedAt) { this.updatedAt = updatedAt; }

    @Override
    public String toString() {
        return "ProductoAgricola{" +
               "id=" + id +
               ", nombre='" + nombre + '\'' +
               ", descripcion='" + descripcion + '\'' +
               ", agricultor='" + agricultor + '\'' +
               ", precio=" + precio +
               ", cantidad=" + cantidad +
               ", rutaImagen='" + rutaImagen + '\'' +
               ", createdAt=" + createdAt +
               ", updatedAt=" + updatedAt +
               '}';
    }
} 