package com.gpaweb.dao;

import com.gpaweb.model.ProductoAgricola;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class ProductoAgricolaDAOMockImpl implements ProductoAgricolaDAO {
    private static final Map<Integer, ProductoAgricola> mockDB = new ConcurrentHashMap<>();
    private static final AtomicInteger idCounter = new AtomicInteger(3); // Empezar después de los datos de ejemplo

    static {
        // Datos de ejemplo iniciales para el mock
        ProductoAgricola p1 = new ProductoAgricola(1, "Maíz Amarillo (Mock)", "Maíz dulce y tierno, cultivado localmente.", "Granja El Sol", new BigDecimal("1800.50"), 150, "images/mock/maiz.jpg");
        p1.setCreatedAt(new Timestamp(System.currentTimeMillis() - 100000));
        p1.setUpdatedAt(new Timestamp(System.currentTimeMillis() - 100000));
        mockDB.put(p1.getId(), p1);

        ProductoAgricola p2 = new ProductoAgricola(2, "Frijol Cargamanto (Mock)", "Frijol rojo de alta calidad, ideal para sopas.", "Finca La Luna", new BigDecimal("4500.75"), 200, "images/mock/frijol.jpg");
        p2.setCreatedAt(new Timestamp(System.currentTimeMillis() - 50000));
        p2.setUpdatedAt(new Timestamp(System.currentTimeMillis() - 50000));
        mockDB.put(p2.getId(), p2);
         ProductoAgricola p3 = new ProductoAgricola(3, "Aguacate Hass (Mock)", "Aguacate cremoso, listo para consumir.", "Huerta San José", new BigDecimal("3200.00"), 80, "images/mock/aguacate.jpg");
        p3.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        p3.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        mockDB.put(p3.getId(), p3);
    }

    @Override
    public void agregarProducto(ProductoAgricola producto) throws SQLException { // SQLException para compatibilidad de interfaz
        producto.setId(idCounter.incrementAndGet());
        producto.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        producto.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        mockDB.put(producto.getId(), producto);
        System.out.println("[MockDAO] Producto agregado: " + producto.getNombre());
    }

    @Override
    public void actualizarProducto(ProductoAgricola producto) throws SQLException {
        if (mockDB.containsKey(producto.getId())) {
            producto.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
            // Asegurarse de que createdAt no se sobrescriba si no se proporciona
            ProductoAgricola existente = mockDB.get(producto.getId());
            if (existente != null && producto.getCreatedAt() == null) {
                producto.setCreatedAt(existente.getCreatedAt());
            } else if (existente != null) {
                 // Si se proporciona createdAt, se usa. Si no, se mantiene el existente.
                 producto.setCreatedAt(producto.getCreatedAt() != null ? producto.getCreatedAt() : existente.getCreatedAt());
            }
            mockDB.put(producto.getId(), producto);
            System.out.println("[MockDAO] Producto actualizado: " + producto.getNombre());
        } else {
            System.out.println("[MockDAO] Error: Producto no encontrado para actualizar con ID: " + producto.getId());
        }
    }

    @Override
    public void eliminarProducto(int id) throws SQLException {
        ProductoAgricola eliminado = mockDB.remove(id);
        if (eliminado != null) {
            System.out.println("[MockDAO] Producto eliminado: " + eliminado.getNombre());
        } else {
            System.out.println("[MockDAO] Error: Producto no encontrado para eliminar con ID: " + id);
        }
    }

    @Override
    public ProductoAgricola obtenerProductoPorId(int id) throws SQLException {
        ProductoAgricola producto = mockDB.get(id);
        System.out.println("[MockDAO] Obteniendo producto por ID: " + id + (producto != null ? " Encontrado." : " No encontrado."));
        return producto;
    }

    @Override
    public List<ProductoAgricola> obtenerTodosLosProductos() throws SQLException {
        System.out.println("[MockDAO] Obteniendo todos los productos. Total: " + mockDB.size());
        // Devolver una nueva lista ordenada para simular el comportamiento de la BD
        return new ArrayList<>(mockDB.values().stream()
                               .sorted((p1, p2) -> p1.getNombre().compareToIgnoreCase(p2.getNombre()))
                               .collect(Collectors.toList()));
    }
} 