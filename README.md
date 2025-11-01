# Tienda SaKURA

Aplicación de consola para gestionar una tienda simplificada desarrollada en Java. El sistema permite registrar usuarios, gestionar un carrito de compras y confirmar pedidos con métodos de pago persistidos en disco.

## Requisitos

* Java 17 o superior (se probó con `javac` y `java` disponibles en el entorno).

## Ejecución

Desde la carpeta que contiene los archivos `.java` ejecute:

```bash
javac *.java
java Main
```

Al cerrar la aplicación se guarda automáticamente el estado de la tienda en `data/tienda.dat`. El archivo se vuelve a cargar en el próximo inicio.

## Características destacadas

* Registro e inicio de sesión de clientes con validaciones básicas y contraseñas almacenadas como hash.
* Catálogo de productos clasificados por categorías con control de stock al agregar artículos al carrito.
* Gestión completa del carrito de compras y confirmación de pedidos eligiendo un método de pago registrado.
* Persistencia del estado (clientes, carritos, compras y métodos de pago) mediante serialización en disco.
* Manejo robusto de entradas erróneas en consola (números inválidos, campos vacíos, etc.).

## Estructura de persistencia

El directorio `data/` se crea automáticamente para almacenar el archivo `tienda.dat`. El repositorio incluye un `.gitignore` para evitar versionar los datos generados durante la ejecución.

## Notas adicionales

* Para restablecer la aplicación a su estado inicial basta con eliminar el archivo `data/tienda.dat`.
* El catálogo inicial se genera automáticamente cuando no existe un archivo de persistencia previo.
