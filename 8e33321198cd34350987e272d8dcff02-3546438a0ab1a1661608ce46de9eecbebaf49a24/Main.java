import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);


        List<Categoria> categorias = new ArrayList<>();
        List<Producto> productos = new ArrayList<>();
        List<Cliente> clientes = new ArrayList<>();

        Categoria tecnologia = new Categoria("Tecnología", "Dispositivos electrónicos");
        Categoria libros = new Categoria("Libros", "Lectura y conocimiento");
        categorias.add(tecnologia);
        categorias.add(libros);

        Producto laptop = new Producto("Laptop Gamer", "16GB RAM, SSD 1TB", 4500.0, 10, "2024-06-10", tecnologia);
        Producto celular = new Producto("Celular ProMax", "Cámara 108MP, 5G", 3000.0, 15, "2024-09-15", tecnologia);
        Producto novela = new Producto("El Principito", "Edición clásica", 50.0, 30, "2023-01-10", libros);
        productos.add(laptop);
        productos.add(celular);
        productos.add(novela);

        System.out.println("====================================");
        System.out.println("   SISTEMA DE TIENDA - POO (EIA)");
        System.out.println("====================================");


        System.out.print("Ingrese su nombre: ");
        String nombre = sc.nextLine();
        System.out.print("Ingrese su email: ");
        String email = sc.nextLine();
        System.out.print("Ingrese una contraseña: ");
        String pass = sc.nextLine();
        System.out.print("Dirección de envío: ");
        String direccion = sc.nextLine();
        System.out.print("Teléfono: ");
        String telefono = sc.nextLine();

        Cliente cliente = new Cliente(nombre, email, pass, direccion, telefono);
        clientes.add(cliente);
        System.out.println("\nCliente registrado correctamente.\n");

        boolean salir = false;


        while (!salir) {
            System.out.println("\n========= MENÚ PRINCIPAL =========");
            System.out.println("1. Ver catálogo de productos");
            System.out.println("2. Agregar producto al carrito");
            System.out.println("3. Ver carrito actual");
            System.out.println("4. Confirmar compra");
            System.out.println("5. Ver historial de compras");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opción: ");
            int opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    System.out.println("\n=== CATÁLOGO DE PRODUCTOS ===");
                    for (Producto p : productos) {
                        System.out.println(p.getId() + ". " + p.getNombre() +
                                " | $" + p.getPrecio() +
                                " | Stock: " + p.getStock());
                    }
                    break;

                case 2:
                    System.out.println("\nIngrese el ID del producto que desea agregar:");
                    int idProd = sc.nextInt();
                    System.out.print("Cantidad: ");
                    int cant = sc.nextInt();
                    sc.nextLine();
                    Producto seleccionado = null;
                    for (Producto p : productos) {
                        if (p.getId() == idProd) {
                            seleccionado = p;
                            break;
                        }
                    }
                    if (seleccionado != null) {
                        if (seleccionado.hayStock(cant)) {
                            cliente.getCarritoActivo().agregarProducto(seleccionado, cant);
                            System.out.println("Producto agregado al carrito.");
                        } else {
                            System.out.println("No hay suficiente stock disponible.");
                        }
                    } else {
                        System.out.println("Producto no encontrado.");
                    }
                    break;

                case 3:
                    Carrito carrito = cliente.getCarritoActivo();
                    if (carrito.getLineas().isEmpty()) {
                        System.out.println("El carrito está vacío.");
                    } else {
                        System.out.println("\n=== CARRITO ACTUAL ===");
                        for (LineaCarrito lc : carrito.getLineas()) {
                            System.out.println(lc.getProducto().getNombre() + " x" + lc.getCantidad() +
                                    " - Subtotal: $" + lc.getSubtotal());
                        }
                        System.out.println("TOTAL: $" + carrito.calcularTotal());
                    }
                    break;

                case 4:
                    Carrito c = cliente.getCarritoActivo();
                    if (c.getLineas().isEmpty()) {
                        System.out.println("El carrito está vacío. No se puede confirmar la compra.");
                    } else {
                        Compra compra = new Compra(cliente);
                        for (LineaCarrito lc : c.getLineas()) {
                            if (lc.getProducto().hayStock(lc.getCantidad())) {
                                lc.getProducto().descontarStock(lc.getCantidad());
                                compra.agregarLinea(new LineaCompra(lc.getProducto(), lc.getCantidad()));
                            }
                        }
                        compra.setEstado("PAGADA");
                        cliente.agregarCompraAlHistorial(compra);
                        c.limpiar();
                        System.out.println("Compra realizada exitosamente. Total pagado: $" + compra.getTotal());
                    }
                    break;

                case 5:
                    List<Compra> historial = cliente.getHistorialCompras();
                    if (historial.isEmpty()) {
                        System.out.println("No hay compras registradas.");
                    } else {
                        System.out.println("\n=== HISTORIAL DE COMPRAS ===");
                        for (Compra comp : historial) {
                            System.out.println("Compra #" + comp.getId() + " - Total: $" + comp.getTotal() +
                                    " - Estado: " + comp.getEstado());
                            for (LineaCompra lc : comp.getLineas()) {
                                System.out.println("   " + lc.getProducto().getNombre() + " x" + lc.getCantidad());
                            }
                        }
                    }
                    break;

                case 6:
                    salir = true;
                    System.out.println("Gracias por usar el sistema. ¡Hasta luego!");
                    break;

                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
                    break;
            }
        }

        sc.close();
    }
}