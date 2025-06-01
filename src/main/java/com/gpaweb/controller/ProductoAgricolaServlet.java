package com.gpaweb.controller;

import com.gpaweb.dao.DAOFactory;
import com.gpaweb.dao.ProductoAgricolaDAO;
import com.gpaweb.model.ProductoAgricola;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

// La anotación @WebServlet simplifica la configuración (no se necesita web.xml para este servlet)
@WebServlet("/productos")
public class ProductoAgricolaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L; // Buena práctica para Servlets
    private ProductoAgricolaDAO productoDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        // Selecciona el tipo de DAO (MYSQL o MOCK) desde la fábrica
        // Para cambiar, modificar DAOFactory.DEFAULT_DAO_SOURCE
        productoDAO = DAOFactory.getProductoAgricolaDAO(); 
        System.out.println("ProductoAgricolaServlet inicializado. Usando DAO: " + productoDAO.getClass().getSimpleName());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Establecer codificación para manejar correctamente caracteres especiales (acentos, ñ)
        request.setCharacterEncoding("UTF-8");
        // Generalmente, doPost se usa para acciones que modifican datos (insertar, actualizar)
        // doGet se usa para obtener datos (listar, mostrar formulario)
        // Aquí, reenviamos a doGet para centralizar la lógica de acciones,
        // pero se podría tener lógica separada si se prefiere.
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("accion");
        if (action == null || action.isEmpty()) {
            action = "listar"; // Acción por defecto si no se especifica ninguna
        }
        System.out.println("Acción solicitada: " + action);

        try {
            switch (action.toLowerCase()) { // Usar toLowerCase para ser indulgente con mayúsculas/minúsculas
                case "nuevo":
                    mostrarFormularioNuevo(request, response);
                    break;
                case "insertar":
                    insertarProducto(request, response);
                    break;
                case "eliminar":
                    eliminarProducto(request, response);
                    break;
                case "editar":
                    mostrarFormularioEditar(request, response);
                    break;
                case "actualizar":
                    actualizarProducto(request, response);
                    break;
                case "listar":
                default: // Si la acción no es reconocida, listar productos
                    listarProductos(request, response);
                    break;
            }
        } catch (SQLException ex) {
            // Manejo de errores de base de datos
            System.err.println("Error de SQL en Servlet: " + ex.getMessage());
            ex.printStackTrace();
            request.setAttribute("errorGeneral", "Error de base de datos: " + ex.getMessage());
            // Reenviar a una página de error o a la lista con un mensaje
            try {
                listarProductos(request, response); 
            } catch (ServletException | IOException | SQLException e) {
                // Log the secondary error if forwarding fails
                System.err.println("Error forwarding to list page after initial error: " + e.getMessage());
                e.printStackTrace();
                // Optionally, redirect to a static error page as a last resort
                 response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error processing request after an initial error.");
            }
        } catch (NumberFormatException ex) {
            // Manejo de errores de conversión de números (ej. ID, precio, cantidad)
             System.err.println("Error de formato de número en Servlet: " + ex.getMessage());
            request.setAttribute("errorGeneral", "Error en el formato de los datos numéricos: " + ex.getMessage());
            // Reenviar a una página de error o a la lista con un mensaje
            try {
                listarProductos(request, response);
            } catch (ServletException | IOException | SQLException e) {
                 // Log the secondary error if forwarding fails
                System.err.println("Error forwarding to list page after number format error: " + e.getMessage());
                e.printStackTrace();
                // Optionally, redirect to a static error page as a last resort
                 response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error processing request after a number format error.");
            }
        }
         catch (Exception ex) {
            // Captura general para otros errores inesperados
            System.err.println("Error inesperado en Servlet: " + ex.getMessage());
            ex.printStackTrace();
            request.setAttribute("errorGeneral", "Ocurrió un error inesperado: " + ex.getMessage());
            // Reenviar a una página de error o a la lista con un mensaje
            try {
                listarProductos(request, response);
            } catch (ServletException | IOException | SQLException e) {
                // Log the secondary error if forwarding fails
                System.err.println("Error forwarding to list page after unexpected error: " + e.getMessage());
                e.printStackTrace();
                // Optionally, redirect to a static error page as a last resort
                 response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error processing request after an unexpected error.");
            }
        }
    }

    private void listarProductos(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        List<ProductoAgricola> listaProductos = productoDAO.obtenerTodosLosProductos();
        request.setAttribute("listaProductos", listaProductos);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/listaProductos.jsp");
        dispatcher.forward(request, response);
    }

    private void mostrarFormularioNuevo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("producto", new ProductoAgricola()); // Producto vacío para el formulario
        request.setAttribute("modo", "insertar"); // Indica al JSP que es para insertar
        request.setAttribute("tituloPagina", "Agregar Nuevo Producto");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/formularioProducto.jsp");
        dispatcher.forward(request, response);
    }

    private void mostrarFormularioEditar(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        ProductoAgricola productoExistente = productoDAO.obtenerProductoPorId(id);
        if (productoExistente == null) {
            // Manejo de producto no encontrado (podría redirigir a error o lista con mensaje)
            System.out.println("Producto con ID " + id + " no encontrado para editar.");
            response.sendRedirect("productos?accion=listar&error=ProductoNoEncontrado");
            return;
        }
        request.setAttribute("producto", productoExistente);
        request.setAttribute("modo", "actualizar"); // Indica al JSP que es para actualizar
        request.setAttribute("tituloPagina", "Editar Producto");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/formularioProducto.jsp");
        dispatcher.forward(request, response);
    }

    private void insertarProducto(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        // Obtener parámetros del request
        String nombre = request.getParameter("nombre");
        String descripcion = request.getParameter("descripcion");
        String agricultor = request.getParameter("agricultor");
        BigDecimal precio = new BigDecimal(request.getParameter("precio"));
        int cantidad = Integer.parseInt(request.getParameter("cantidad"));
        String rutaImagen = request.getParameter("rutaImagen");

        // Crear el objeto ProductoAgricola
        ProductoAgricola nuevoProducto = new ProductoAgricola(nombre, descripcion, agricultor, precio, cantidad, rutaImagen);
        
        // Validación básica (se podría expandir)
        if (nombre == null || nombre.trim().isEmpty() || precio.compareTo(BigDecimal.ZERO) < 0) {
            request.setAttribute("errorFormulario", "Nombre y precio son requeridos. El precio no puede ser negativo.");
            request.setAttribute("producto", nuevoProducto); // Devolver datos ingresados al formulario
            request.setAttribute("modo", "insertar");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/formularioProducto.jsp");
            try {
                dispatcher.forward(request, response);
            } catch (ServletException e) {
                 e.printStackTrace();
            }
            return;
        }

        productoDAO.agregarProducto(nuevoProducto);
        response.sendRedirect("productos?accion=listar&exito=ProductoAgregado"); // Redirigir a la lista con mensaje de éxito
    }

    private void actualizarProducto(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String nombre = request.getParameter("nombre");
        String descripcion = request.getParameter("descripcion");
        String agricultor = request.getParameter("agricultor");
        BigDecimal precio = new BigDecimal(request.getParameter("precio"));
        int cantidad = Integer.parseInt(request.getParameter("cantidad"));
        String rutaImagen = request.getParameter("rutaImagen");

        ProductoAgricola producto = new ProductoAgricola(id, nombre, descripcion, agricultor, precio, cantidad, rutaImagen);
        
         // Validación básica
        if (nombre == null || nombre.trim().isEmpty() || precio.compareTo(BigDecimal.ZERO) < 0) {
            request.setAttribute("errorFormulario", "Nombre y precio son requeridos. El precio no puede ser negativo.");
            request.setAttribute("producto", producto); // Devolver datos ingresados al formulario
            request.setAttribute("modo", "actualizar");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/formularioProducto.jsp");
            try {
                dispatcher.forward(request, response);
            } catch (ServletException e) {
                 e.printStackTrace();
            }
            return;
        }
        
        productoDAO.actualizarProducto(producto);
        response.sendRedirect("productos?accion=listar&exito=ProductoActualizado");
    }

    private void eliminarProducto(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        productoDAO.eliminarProducto(id);
        response.sendRedirect("productos?accion=listar&exito=ProductoEliminado");
    }
} 