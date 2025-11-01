import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        EstadoTienda estado = Optional.ofNullable(PersistenciaTienda.cargarEstado())
                .orElseGet(Main::crearEstadoInicial);

        System.out.println("====================================");
        System.out.println("   SISTEMA DE TIENDA - POO (EIA)");
        System.out.println("====================================");

        Cliente cliente = autenticarORegistrar(sc, estado.getClientes());
        PersistenciaTienda.guardarEstado(estado);

        boolean salir = false;
        while (!salir) {
            imprimirMenuPrincipal();
            int opcion = leerEntero(sc, "Seleccione una opción: ");

            switch (opcion) {
                case 1:
                    mostrarCatalogo(estado.getProductos());
                    break;
                case 2:
                    if (agregarProductoAlCarrito(sc, cliente, estado.getProductos())) {
                        PersistenciaTienda.guardarEstado(estado);
                    }
                    break;
                case 3:
                    if (gestionarCarrito(sc, cliente)) {
                        PersistenciaTienda.guardarEstado(estado);
                    }
                    break;
                case 4:
                    if (confirmarCompra(sc, cliente)) {
                        PersistenciaTienda.guardarEstado(estado);
                    }
                    break;
                case 5:
                    mostrarHistorial(cliente);
                    break;
                case 6:
                    if (gestionarMetodosPago(sc, cliente)) {
                        PersistenciaTienda.guardarEstado(estado);
                    }
                    break;
                case 7:
                    salir = true;
                    System.out.println("Gracias por usar el sistema. ¡Hasta luego!");
                    break;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
                    break;
            }
        }

        PersistenciaTienda.guardarEstado(estado);
        sc.close();
    }

    private static void imprimirMenuPrincipal() {
        System.out.println("\n========= MENÚ PRINCIPAL =========");
        System.out.println("1. Ver catálogo de productos");
        System.out.println("2. Agregar producto al carrito");
        System.out.println("3. Gestionar carrito");
        System.out.println("4. Confirmar compra");
        System.out.println("5. Ver historial de compras");
        System.out.println("6. Gestionar métodos de pago");
        System.out.println("7. Salir");
    }

    private static EstadoTienda crearEstadoInicial() {
        EstadoTienda estado = new EstadoTienda();

        Categoria tecnologia = new Categoria("Tecnología", "Dispositivos electrónicos");
        Categoria libros = new Categoria("Libros", "Lectura y conocimiento");
        estado.getCategorias().add(tecnologia);
        estado.getCategorias().add(libros);

        Producto laptop = new Producto("Laptop Gamer", "16GB RAM, SSD 1TB", 4500.0, 10, "2024-06-10", tecnologia);
        Producto celular = new Producto("Celular ProMax", "Cámara 108MP, 5G", 3000.0, 15, "2024-09-15", tecnologia);
        Producto novela = new Producto("El Principito", "Edición clásica", 50.0, 30, "2023-01-10", libros);
        estado.getProductos().add(laptop);
        estado.getProductos().add(celular);
        estado.getProductos().add(novela);

        return estado;
    }

    private static Cliente autenticarORegistrar(Scanner sc, List<Cliente> clientes) {
        while (true) {
            System.out.println("\n1. Registrarse");
            System.out.println("2. Iniciar sesión");
            int opcion = leerEntero(sc, "Seleccione una opción: ");

            if (opcion == 1) {
                return registrarNuevoCliente(sc, clientes);
            } else if (opcion == 2) {
                Cliente cliente = iniciarSesion(sc, clientes);
                if (cliente != null) {
                    return cliente;
                }
            } else {
                System.out.println("Opción no válida.");
            }
        }
    }

    private static Cliente registrarNuevoCliente(Scanner sc, List<Cliente> clientes) {
        System.out.println("\n=== Registro de nuevo cliente ===");
        String nombre = leerTextoNoVacio(sc, "Nombre completo: ");
        String email = leerEmailValido(sc, clientes);
        String password = leerTextoNoVacio(sc, "Contraseña: ");
        String confirmacion = leerTextoNoVacio(sc, "Confirme la contraseña: ");
        while (!password.equals(confirmacion)) {
            System.out.println("Las contraseñas no coinciden. Intente nuevamente.");
            password = leerTextoNoVacio(sc, "Contraseña: ");
            confirmacion = leerTextoNoVacio(sc, "Confirme la contraseña: ");
        }
        String direccion = leerTextoNoVacio(sc, "Dirección de envío: ");
        String telefono = leerTextoNoVacio(sc, "Teléfono de contacto: ");

        Cliente nuevo = new Cliente(nombre, email, hashPassword(password), direccion, telefono);
        clientes.add(nuevo);
        System.out.println("Cliente registrado correctamente.\n");
        return nuevo;
    }

    private static Cliente iniciarSesion(Scanner sc, List<Cliente> clientes) {
        if (clientes.isEmpty()) {
            System.out.println("No hay clientes registrados aún. Regístrese primero.");
            return null;
        }
        System.out.println("\n=== Inicio de sesión ===");
        String email = leerTextoNoVacio(sc, "Email: ");
        String password = leerTextoNoVacio(sc, "Contraseña: ");
        String hash = hashPassword(password);

        for (Cliente cliente : clientes) {
            if (cliente.getEmail().equalsIgnoreCase(email) && cliente.authenticate(hash)) {
                System.out.println("Bienvenido de nuevo, " + cliente.getNombre() + "!");
                return cliente;
            }
        }
        System.out.println("Credenciales no válidas. Verifique su email y contraseña.");
        return null;
    }

    private static void mostrarCatalogo(List<Producto> productos) {
        if (productos.isEmpty()) {
            System.out.println("No hay productos disponibles en este momento.");
            return;
        }
        System.out.println("\n=== CATÁLOGO DE PRODUCTOS ===");
        for (Producto p : productos) {
            System.out.println(p.getId() + ". " + p.getNombre()
                    + " | $" + p.getPrecio()
                    + " | Stock: " + p.getStock()
                    + " | Categoría: " + p.getCategoria().getNombre());
        }
    }

    private static boolean agregarProductoAlCarrito(Scanner sc, Cliente cliente, List<Producto> productos) {
        if (productos.isEmpty()) {
            System.out.println("No hay productos disponibles para agregar.");
            return false;
        }
        mostrarCatalogo(productos);
        int idProd = leerEntero(sc, "Ingrese el ID del producto que desea agregar: ");
        Producto seleccionado = buscarProductoPorId(productos, idProd);
        if (seleccionado == null) {
            System.out.println("Producto no encontrado.");
            return false;
        }
        int cantidad = leerEntero(sc, "Cantidad: ");
        if (cantidad <= 0) {
            System.out.println("La cantidad debe ser mayor que cero.");
            return false;
        }
        if (!seleccionado.hayStock(cantidad)) {
            System.out.println("No hay suficiente stock disponible.");
            return false;
        }
        cliente.getCarritoActivo().agregarProducto(seleccionado, cantidad);
        System.out.println("Producto agregado al carrito.");
        return true;
    }

    private static Producto buscarProductoPorId(List<Producto> productos, int id) {
        for (Producto producto : productos) {
            if (producto.getId() == id) {
                return producto;
            }
        }
        return null;
    }

    private static boolean gestionarCarrito(Scanner sc, Cliente cliente) {
        Carrito carrito = cliente.getCarritoActivo();
        if (carrito.getLineas().isEmpty()) {
            System.out.println("El carrito está vacío.");
            return false;
        }

        boolean huboCambios = false;
        boolean volver = false;
        while (!volver) {
            mostrarResumenCarrito(carrito);
            System.out.println("1. Cambiar cantidad");
            System.out.println("2. Eliminar producto");
            System.out.println("3. Vaciar carrito");
            System.out.println("4. Volver");
            int opcion = leerEntero(sc, "Seleccione una opción: ");

            switch (opcion) {
                case 1:
                    if (actualizarCantidadCarrito(sc, carrito)) {
                        huboCambios = true;
                    }
                    break;
                case 2:
                    if (eliminarProductoCarrito(sc, carrito)) {
                        huboCambios = true;
                    }
                    break;
                case 3:
                    carrito.limpiar();
                    System.out.println("Carrito vaciado correctamente.");
                    huboCambios = true;
                    volver = true;
                    break;
                case 4:
                    volver = true;
                    break;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
                    break;
            }

            if (carrito.getLineas().isEmpty()) {
                volver = true;
            }
        }

        return huboCambios;
    }

    private static void mostrarResumenCarrito(Carrito carrito) {
        System.out.println("\n=== CARRITO ACTUAL ===");
        for (LineaCarrito lc : carrito.getLineas()) {
            System.out.println(lc.getProducto().getId() + ". " + lc.getProducto().getNombre() + " x" + lc.getCantidad()
                    + " - Subtotal: $" + lc.getSubtotal());
        }
        System.out.println("TOTAL: $" + carrito.calcularTotal());
    }

    private static boolean actualizarCantidadCarrito(Scanner sc, Carrito carrito) {
        int idProducto = leerEntero(sc, "Ingrese el ID del producto: ");
        Optional<LineaCarrito> linea = carrito.buscarLineaPorProducto(idProducto);
        if (linea.isEmpty()) {
            System.out.println("No se encontró un producto con ese ID en el carrito.");
            return false;
        }

        int nuevaCantidad = leerEntero(sc, "Nueva cantidad: ");
        if (nuevaCantidad <= 0) {
            System.out.println("La cantidad debe ser mayor que cero.");
            return false;
        }
        Producto producto = linea.get().getProducto();
        if (!producto.hayStock(nuevaCantidad)) {
            System.out.println("No hay stock suficiente para esa cantidad.");
            return false;
        }

        carrito.actualizarCantidad(idProducto, nuevaCantidad);
        System.out.println("Cantidad actualizada correctamente.");
        return true;
    }

    private static boolean eliminarProductoCarrito(Scanner sc, Carrito carrito) {
        int idProducto = leerEntero(sc, "Ingrese el ID del producto a eliminar: ");
        if (carrito.eliminarProducto(idProducto)) {
            System.out.println("Producto eliminado del carrito.");
            return true;
        }
        System.out.println("No se encontró un producto con ese ID en el carrito.");
        return false;
    }

    private static boolean confirmarCompra(Scanner sc, Cliente cliente) {
        Carrito carrito = cliente.getCarritoActivo();
        if (carrito.getLineas().isEmpty()) {
            System.out.println("El carrito está vacío. No se puede confirmar la compra.");
            return false;
        }
        if (cliente.getMetodosPago().isEmpty()) {
            System.out.println("Necesita registrar al menos un método de pago antes de comprar.");
            return false;
        }

        mostrarResumenCarrito(carrito);
        listarMetodosPago(cliente);
        int idMetodo = leerEntero(sc, "Seleccione el ID del método de pago a utilizar: ");
        MetodoPago metodo = null;
        for (MetodoPago mp : cliente.getMetodosPago()) {
            if (mp.getId() == idMetodo) {
                metodo = mp;
                break;
            }
        }
        if (metodo == null) {
            System.out.println("Método de pago no encontrado.");
            return false;
        }

        Compra compra = new Compra(cliente);
        boolean hayProblemasStock = false;
        for (LineaCarrito lc : carrito.getLineas()) {
            if (lc.getProducto().hayStock(lc.getCantidad())) {
                lc.getProducto().descontarStock(lc.getCantidad());
                compra.agregarLinea(new LineaCompra(lc.getProducto(), lc.getCantidad()));
            } else {
                System.out.println("No hay stock suficiente para " + lc.getProducto().getNombre());
                hayProblemasStock = true;
            }
        }
        if (compra.getLineas().isEmpty()) {
            System.out.println("No se pudo generar la compra debido a problemas de stock.");
            return false;
        }
        compra.setEstado("PAGADA con " + metodo.getTipo());
        cliente.agregarCompraAlHistorial(compra);
        carrito.limpiar();
        if (hayProblemasStock) {
            System.out.println("La compra se completó parcialmente por falta de stock en algunos productos.");
        } else {
            System.out.println("Compra realizada exitosamente. Total pagado: $" + compra.getTotal());
        }
        return true;
    }

    private static void mostrarHistorial(Cliente cliente) {
        List<Compra> historial = cliente.getHistorialCompras();
        if (historial.isEmpty()) {
            System.out.println("No hay compras registradas.");
            return;
        }
        System.out.println("\n=== HISTORIAL DE COMPRAS ===");
        for (Compra compra : historial) {
            System.out.println("Compra #" + compra.getId() + " - Total: $" + compra.getTotal()
                    + " - Estado: " + compra.getEstado());
            for (LineaCompra lc : compra.getLineas()) {
                System.out.println("   " + lc.getProducto().getNombre() + " x" + lc.getCantidad()
                        + " (" + lc.getPrecioUnitario() + " c/u)");
            }
        }
    }

    private static boolean gestionarMetodosPago(Scanner sc, Cliente cliente) {
        boolean huboCambios = false;
        boolean volver = false;
        while (!volver) {
            System.out.println("\n=== Gestión de métodos de pago ===");
            System.out.println("1. Listar métodos registrados");
            System.out.println("2. Agregar nuevo método");
            System.out.println("3. Eliminar método");
            System.out.println("4. Volver al menú principal");
            int opcion = leerEntero(sc, "Seleccione una opción: ");

            switch (opcion) {
                case 1:
                    listarMetodosPago(cliente);
                    break;
                case 2:
                    agregarMetodoPago(sc, cliente);
                    huboCambios = true;
                    break;
                case 3:
                    if (eliminarMetodoPago(sc, cliente)) {
                        huboCambios = true;
                    }
                    break;
                case 4:
                    volver = true;
                    break;
                default:
                    System.out.println("Opción inválida.");
                    break;
            }
        }
        return huboCambios;
    }

    private static void listarMetodosPago(Cliente cliente) {
        if (cliente.getMetodosPago().isEmpty()) {
            System.out.println("No hay métodos de pago registrados.");
            return;
        }
        System.out.println("Métodos de pago:");
        for (MetodoPago mp : cliente.getMetodosPago()) {
            System.out.println(mp.getId() + ". " + mp.getTipo() + " - " + mp.getTitular()
                    + " - " + mp.getNumeroEnmascarado());
        }
    }

    private static void agregarMetodoPago(Scanner sc, Cliente cliente) {
        System.out.println("\n=== Nuevo método de pago ===");
        String tipo = leerTextoNoVacio(sc, "Tipo (TARJETA, PAYPAL, etc.): ");
        String titular = leerTextoNoVacio(sc, "Titular: ");
        String numero = leerTextoNoVacio(sc, "Número: ");
        cliente.agregarMetodoPago(new MetodoPago(tipo, titular, numero));
        System.out.println("Método agregado correctamente.");
    }

    private static boolean eliminarMetodoPago(Scanner sc, Cliente cliente) {
        if (cliente.getMetodosPago().isEmpty()) {
            System.out.println("No hay métodos de pago para eliminar.");
            return false;
        }
        listarMetodosPago(cliente);
        int id = leerEntero(sc, "Ingrese el ID del método a eliminar: ");
        if (cliente.eliminarMetodoPago(id)) {
            System.out.println("Método eliminado correctamente.");
            return true;
        }
        System.out.println("No se encontró un método con ese ID.");
        return false;
    }

    private static int leerEntero(Scanner sc, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                int valor = sc.nextInt();
                sc.nextLine();
                return valor;
            } catch (InputMismatchException e) {
                System.out.println("Ingrese un número válido.");
                sc.nextLine();
            }
        }
    }

    private static String leerTextoNoVacio(Scanner sc, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String valor = sc.nextLine().trim();
            if (!valor.isEmpty()) {
                return valor;
            }
            System.out.println("El valor no puede estar vacío.");
        }
    }

    private static String leerEmailValido(Scanner sc, List<Cliente> clientes) {
        while (true) {
            String email = leerTextoNoVacio(sc, "Email: ");
            if (!email.contains("@") || !email.contains(".")) {
                System.out.println("Ingrese un email válido.");
                continue;
            }
            boolean existe = false;
            for (Cliente c : clientes) {
                if (c.getEmail().equalsIgnoreCase(email)) {
                    existe = true;
                    break;
                }
            }
            if (existe) {
                System.out.println("Ya existe un cliente registrado con ese email.");
            } else {
                return email;
            }
        }
    }

    private static String hashPassword(String plain) {
        return Integer.toHexString(plain.hashCode());
    }
}
