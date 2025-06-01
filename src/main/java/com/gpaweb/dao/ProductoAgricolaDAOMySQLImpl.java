package com.gpaweb.dao;

import com.gpaweb.model.ProductoAgricola;
import com.gpaweb.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoAgricolaDAOMySQLImpl implements ProductoAgricolaDAO {

    @Override
    public void agregarProducto(ProductoAgricola producto) throws SQLException {
        String sql = "INSERT INTO productos_agricolas (nombre, descripcion, agricultor, precio, cantidad, ruta_imagen) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, producto.getNombre());
            pstmt.setString(2, producto.getDescripcion());
            pstmt.setString(3, producto.getAgricultor());
            pstmt.setBigDecimal(4, producto.getPrecio());
            pstmt.setInt(5, producto.getCantidad());
            pstmt.setString(6, producto.getRutaImagen());
            pstmt.executeUpdate();
        }
    }

    @Override
    public void actualizarProducto(ProductoAgricola producto) throws SQLException {
        String sql = "UPDATE productos_agricolas SET nombre = ?, descripcion = ?, agricultor = ?, precio = ?, cantidad = ?, ruta_imagen = ?, updated_at = CURRENT_TIMESTAMP WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, producto.getNombre());
            pstmt.setString(2, producto.getDescripcion());
            pstmt.setString(3, producto.getAgricultor());
            pstmt.setBigDecimal(4, producto.getPrecio());
            pstmt.setInt(5, producto.getCantidad());
            pstmt.setString(6, producto.getRutaImagen());
            pstmt.setInt(7, producto.getId());
            pstmt.executeUpdate();
        }
    }

    @Override
    public void eliminarProducto(int id) throws SQLException {
        String sql = "DELETE FROM productos_agricolas WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    @Override
    public ProductoAgricola obtenerProductoPorId(int id) throws SQLException {
        String sql = "SELECT * FROM productos_agricolas WHERE id = ?";
        ProductoAgricola producto = null;
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    producto = mapResultSetToProducto(rs);
                }
            }
        }
        return producto;
    }

    @Override
    public List<ProductoAgricola> obtenerTodosLosProductos() throws SQLException {
        List<ProductoAgricola> productos = new ArrayList<>();
        String sql = "SELECT * FROM productos_agricolas ORDER BY nombre ASC"; // Ordenar alfabéticamente por nombre
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                productos.add(mapResultSetToProducto(rs));
            }
        }
        return productos;
    }

    // Método helper para mapear ResultSet a Objeto ProductoAgricola
    private ProductoAgricola mapResultSetToProducto(ResultSet rs) throws SQLException {
        ProductoAgricola producto = new ProductoAgricola();
        producto.setId(rs.getInt("id"));
        producto.setNombre(rs.getString("nombre"));
        producto.setDescripcion(rs.getString("descripcion"));
        producto.setAgricultor(rs.getString("agricultor"));
        producto.setPrecio(rs.getBigDecimal("precio"));
        producto.setCantidad(rs.getInt("cantidad"));
        producto.setRutaImagen(rs.getString("ruta_imagen"));
        producto.setCreatedAt(rs.getTimestamp("created_at"));
        producto.setUpdatedAt(rs.getTimestamp("updated_at"));
        return producto;
    }
} 