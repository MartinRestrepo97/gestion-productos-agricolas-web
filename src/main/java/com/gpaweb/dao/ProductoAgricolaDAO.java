package com.gpaweb.dao;

import com.gpaweb.model.ProductoAgricola;
import java.util.List;
import java.sql.SQLException;

public interface ProductoAgricolaDAO {
    void agregarProducto(ProductoAgricola producto) throws SQLException;
    void actualizarProducto(ProductoAgricola producto) throws SQLException;
    void eliminarProducto(int id) throws SQLException;
    ProductoAgricola obtenerProductoPorId(int id) throws SQLException;
    List<ProductoAgricola> obtenerTodosLosProductos() throws SQLException;
} 