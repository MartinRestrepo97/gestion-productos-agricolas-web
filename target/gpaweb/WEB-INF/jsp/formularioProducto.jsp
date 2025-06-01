<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${tituloPagina} - Gestión de Productos</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="container">
        <header>
             <h1>${tituloPagina}</h1>
        </header>

        <c:if test="${not empty errorFormulario}">
            <p class="mensaje error">${errorFormulario}</p>
        </c:if>

        <main>
            <form action="${pageContext.request.contextPath}/productos" method="post" class="formulario-producto">
                <%-- Campo oculto para el ID en modo actualización --%>
                <c:if test="${modo == 'actualizar'}">
                    <input type="hidden" name="id" value="<c:out value='${producto.id}' />" />
                </c:if>
                <%-- Campo oculto para la acción (insertar o actualizar) --%>
                <input type="hidden" name="accion" value="${modo}" />

                <fieldset>
                    <legend>Información del Producto</legend>
                    
                    <div class="campo-formulario">
                        <label for="nombre">Nombre:</label>
                        <input type="text" id="nombre" name="nombre" value="<c:out value='${producto.nombre}' />" required maxlength="250">
                    </div>
                    
                    <div class="campo-formulario">
                        <label for="descripcion">Descripción:</label>
                        <textarea id="descripcion" name="descripcion" rows="4" maxlength="1000"><c:out value='${producto.descripcion}' /></textarea>
                    </div>
                    
                    <div class="campo-formulario">
                        <label for="agricultor">Agricultor:</label>
                        <input type="text" id="agricultor" name="agricultor" value="<c:out value='${producto.agricultor}' />" maxlength="250">
                    </div>
                    
                    <div class="campo-formulario">
                        <label for="precio">Precio ($):</label>
                        <input type="number" id="precio" name="precio" step="0.01" min="0" value="<c:out value='${producto.precio}' />" required>
                    </div>
                    
                    <div class="campo-formulario">
                        <label for="cantidad">Cantidad (Unidades):</label>
                        <input type="number" id="cantidad" name="cantidad" min="0" value="<c:out value='${producto.cantidad}' />" required>
                    </div>
                    
                    <div class="campo-formulario">
                        <label for="rutaImagen">Ruta de Imagen (ej: images/producto.jpg):</label>
                        <input type="text" id="rutaImagen" name="rutaImagen" value="<c:out value='${producto.rutaImagen}' />" maxlength="250" placeholder="Ej: images/mi_producto.png">
                        <small>Dejar vacío si no hay imagen. Las imágenes deben estar en `src/main/webapp/images/`</small>
                    </div>
                </fieldset>
                
                <div class="acciones-formulario">
                    <button type="submit" class="boton">
                        <img src="${pageContext.request.contextPath}/css/icons/save.svg" alt="Guardar" class="icon"/>
                        ${modo == 'insertar' ? 'Guardar Producto' : 'Actualizar Producto'}
                    </button>
                    <a href="${pageContext.request.contextPath}/productos?accion=listar" class="boton boton-secundario">
                         <img src="${pageContext.request.contextPath}/css/icons/cancel.svg" alt="Cancelar" class="icon"/>
                        Cancelar
                    </a>
                </div>
            </form>
        </main>
         <footer>
            <p>&copy; 2024 Gestión de Productos Agrícolas</p>
        </footer>
    </div>
</body>
</html> 