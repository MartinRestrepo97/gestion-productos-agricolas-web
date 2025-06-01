# Gestión de Productos Agrícolas Web

Este proyecto web Java permite la gestión (CRUD) de productos agrícolas, utilizando Servlets, JSP, JSTL y conexión a base de datos MySQL o datos simulados.

## Estructura del Proyecto

```
gestion-productos-agricolas-web/
├── pom.xml                                 # Archivo de configuración de Maven
├── task_memo_GA7-220501096-AA2-EV02.md     # Memo de la tarea
└── src/
    └── main/
        ├── java/
        │   └── com/gpaweb/              # Paquete raíz
        │       ├── controller/             # Servlets que manejan las peticiones
        │       │   └── ProductoAgricolaServlet.java
        │       ├── dao/                    # Objetos de Acceso a Datos (Data Access Objects)
        │       │   ├── ProductoAgricolaDAO.java           # Interfaz DAO
        │       │   ├── ProductoAgricolaDAOMySQLImpl.java  # Implementación MySQL
        │       │   ├── ProductoAgricolaDAOMockImpl.java   # Implementación Mock
        │       │   └── DAOFactory.java                    # Fábrica para obtener el DAO
        │       ├── model/                  # Clases de modelo (JavaBeans)
        │       │   └── ProductoAgricola.java              # JavaBean/Entidad ProductoAgrícola
        │       └── util/                   # Clases de utilidad
        │           └── DBUtil.java                        # Utilidad para conexión a BD
        └── webapp/                 # Contenido web (JSP, CSS, imágenes, etc.)
            ├── WEB-INF/
            │   ├── jsp/                  # Páginas JSP (vistas) que no son de acceso directo
            │   │   ├── listaProductos.jsp        # Vista para listar productos
            │   │   └── formularioProducto.jsp      # Vista para agregar/editar producto
            │   └── web.xml                       # Descriptor de despliegue (config. avanzada)
            ├── css/                    # Archivos CSS
            │   └── style.css                 # Estilos principales
            │   └── icons/                # Íconos SVG
            │       ├── add.svg
            │       ├── cancel.svg
            │       ├── delete.svg
            │       ├── edit.svg
            │       └── save.svg
            ├── images/                 # Directorio para imágenes (productos, placeholder)
            │   └── placeholder.png
            └── index.jsp               # Página de inicio (normalmente redirige)
```

## Configuración

### Base de Datos MySQL

1.  Asegúrate de tener un servidor MySQL instalado y corriendo.
2.  Crea una base de datos llamada `agric_app_db`.
3.  Ejecuta el siguiente script SQL para crear la tabla `productos_agricolas`:

    ```sql
    CREATE TABLE `productos_agricolas` (
      `id` int NOT NULL AUTO_INCREMENT,
      `nombre` varchar(255) NOT NULL,
      `descripcion` text,
      `agricultor` varchar(255),
      `precio` decimal(10,2) NOT NULL,
      `cantidad` int NOT NULL DEFAULT '0',
      `ruta_imagen` varchar(255),
      `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
      `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
      PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
    ```
4.  Edita el archivo `src/main/java/com/gpaweb/util/DBUtil.java` y reemplaza `"tu_usuario_mysql"` y `"tu_contraseña_mysql"` con tus credenciales de MySQL. Si tu base de datos no está en `localhost:3306`, actualiza también la `DB_URL`.

### Construcción del Proyecto (Maven)

1.  Asegúrate de tener Maven instalado.
2.  Abre una terminal en la raíz del proyecto (`gestion-productos-agricolas-web`).
3.  Ejecuta el siguiente comando para compilar el proyecto y empaquetarlo como un archivo WAR:

    ```bash
    mvn clean package
    ```
4.  Esto creará el archivo `gpaweb.war` en el directorio `target/`.

## Ejecución

### Opción 1: Usando un servidor Tomcat instalado

1.  Necesitas un servidor de aplicaciones web que soporte Servlets y JSP, como Apache Tomcat.
2.  Implementa el archivo `gpaweb.war` generado en el paso de construcción en tu servidor Tomcat (generalmente copiando el archivo a la carpeta `webapps` de Tomcat).
3.  Inicia o reinicia tu servidor Tomcat.
4.  Una vez que el servidor esté corriendo y la aplicación desplegada, podrás acceder a ella a través de tu navegador en la URL correspondiente (típicamente `http://localhost:8080/gpaweb/productos`).

### Opción 2: Usando Tomcat embebido con Maven (recomendado para desarrollo)

1.  No necesitas instalar Tomcat manualmente. Puedes ejecutar la aplicación directamente con Maven usando el plugin Tomcat.
2.  Ejecuta el siguiente comando en la raíz del proyecto:

    ```bash
    mvn tomcat7:run
    ```

3.  Maven descargará y levantará un servidor Tomcat embebido, desplegando automáticamente la aplicación.
4.  Accede a la aplicación en tu navegador en la URL: `http://localhost:8080/gpaweb/productos`

> Si no tienes el plugin Tomcat configurado, agrégalo en la sección `<plugins>` de tu `pom.xml`:
>
> ```xml
> <plugin>
>   <groupId>org.apache.tomcat.maven</groupId>
>   <artifactId>tomcat7-maven-plugin</artifactId>
>   <version>2.2</version>
>   <configuration>
>     <path>/gpaweb</path>
>   </configuration>
> </plugin>
> ```


export PATH="/Users/martinernestorestrepopalacios/apache-maven-3.9.9/bin:$PATH"
source ~/.zshrc