package com.gpaweb.dao;

public class DAOFactory {
    // Cambia esta constante para alternar entre la base de datos real y los datos simulados.
    // Podría leerse de un archivo de propiedades o una variable de entorno en una app más compleja.
    public enum DAOSource { MYSQL, MOCK }

    private static final DAOSource DEFAULT_DAO_SOURCE = DAOSource.MYSQL; // O DAOSource.MOCK para pruebas

    public static ProductoAgricolaDAO getProductoAgricolaDAO() {
        return getProductoAgricolaDAO(DEFAULT_DAO_SOURCE);
    }
    
    public static ProductoAgricolaDAO getProductoAgricolaDAO(DAOSource source) {
        switch (source) {
            case MYSQL:
                System.out.println("Usando implementación DAO: MySQL");
                return new ProductoAgricolaDAOMySQLImpl();
            case MOCK:
                System.out.println("Usando implementación DAO: Mock");
                return new ProductoAgricolaDAOMockImpl();
            default:
                // Esto no debería ocurrir con un enum, pero es buena práctica tener un default.
                throw new IllegalArgumentException("Tipo de DAO no soportado: " + source);
        }
    }
} 