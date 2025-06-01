<%-- 
  Este archivo sirve como punto de entrada y redirige automáticamente 
  a la lista de productos, que es la vista principal de la aplicación.
--%>
<% response.sendRedirect(request.getContextPath() + "/productos?accion=listar"); %> 