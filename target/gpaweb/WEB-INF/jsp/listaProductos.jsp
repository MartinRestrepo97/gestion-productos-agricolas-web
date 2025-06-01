<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Lista de Productos Agrícolas</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="container">
        <header>
            <h1>Gestión de Productos Agrícolas</h1>
        </header>

        <nav>
            <a href="${pageContext.request.contextPath}/productos?accion=nuevo" class="boton">
                <img src="${pageContext.request.contextPath}/css/icons/add.svg" alt="Agregar" class="icon"/> Agregar Nuevo Producto
            </a>
        </nav>

        <%-- Mensajes de éxito o error --%>
        <c:if test="${not empty param.exito}">
            <p class="mensaje exito">¡Operación exitosa: ${param.exito}!</p>
        </c:if>
        <c:if test="${not empty param.error}">
            <p class="mensaje error">Error: ${param.error}</p>
        </c:if>
        <c:if test="${not empty errorGeneral}">
            <p class="mensaje error">Error del sistema: ${errorGeneral}</p>
        </c:if>

        <main>
            <c:choose>
                <c:when test="${not empty listaProductos}">
                    <table>
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Nombre</th>
                                <th>Descripción</th>
                                <th>Agricultor</th>
                                <th>Precio</th>
                                <th>Cantidad</th>
                                <th>Imagen</th>
                                <th>Acciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="producto" items="${listaProductos}">
                                <tr>
                                    <td><c:out value="${producto.id}" /></td>
                                    <td><c:out value="${producto.nombre}" /></td>
                                    <td><c:out value="${producto.descripcion}" /></td>
                                    <td><c:out value="${producto.agricultor}" /></td>
                                    <td><fmt:formatNumber value="${producto.precio}" type="CURRENCY" currencySymbol="$" minFractionDigits="2" maxFractionDigits="2"/></td>
                                    <td><c:out value="${producto.cantidad}" /></td>
                                    <td>
                                        <c:if test="${not empty producto.rutaImagen}">
                                            <%-- Se asume que las imágenes están en webapp/images/ --%>
                                            <img src="${pageContext.request.contextPath}/${producto.rutaImagen}" alt="Imagen de ${producto.nombre}" class="imagen-producto"
                                                 onerror="this.onerror=null; this.src='${pageContext.request.contextPath}/images/placeholder.png'; this.alt='Imagen no disponible';"/>
                                        </c:if>
                                        <c:if test="${empty producto.rutaImagen}">
                                            <span class="no-imagen">Sin imagen</span>
                                        </c:if>
                                    </td>
                                    <td class="acciones">
                                        <a href="${pageContext.request.contextPath}/productos?accion=editar&id=${producto.id}" class="botonEditar" title="Editar">
                                            <img src="${pageContext.request.contextPath}/css/icons/edit.svg" alt="Editar" class="icon"/>
                                        </a>
                                        <a href="${pageContext.request.contextPath}/productos?accion=eliminar&id=${producto.id}" class="botonEliminar" title="Eliminar"
                                           onclick="return confirm('¿Está seguro de que desea eliminar el producto '${producto.nombre}'?');">
                                            <img src="${pageContext.request.contextPath}/css/icons/delete.svg" alt="Eliminar" class="icon"/>
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:when>
                <c:otherwise>
                    <p class="mensaje info">No hay productos registrados actualmente.</p>
                </c:otherwise>
            </c:choose>
        </main>
        
        <footer>
            <p>&copy; 2024 Gestión de Productos Agrícolas</p>
        </footer>
    </div>
</body>
</html> 