import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;

public class PersistenciaTienda {
    private static final String DIRECTORIO = "data";
    private static final String ARCHIVO = DIRECTORIO + "/tienda.dat";

    private PersistenciaTienda() {
        // Utilidad
    }

    public static EstadoTienda cargarEstado() {
        Path ruta = Paths.get(ARCHIVO);
        if (!Files.exists(ruta)) {
            return null;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ruta.toFile()))) {
            EstadoTienda estado = (EstadoTienda) ois.readObject();
            ajustarSecuencias(estado);
            return estado;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("[Advertencia] No se pudo cargar la información previa: " + e.getMessage());
            return null;
        }
    }

    public static void guardarEstado(EstadoTienda estado) {
        try {
            Files.createDirectories(Paths.get(DIRECTORIO));
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARCHIVO))) {
                oos.writeObject(estado);
            }
        } catch (IOException e) {
            System.err.println("[Error] No se pudo guardar el estado de la tienda: " + e.getMessage());
        }
    }

    private static void ajustarSecuencias(EstadoTienda estado) {
        int maxCategoria = estado.getCategorias().stream()
                .map(Categoria::getId)
                .max(Comparator.naturalOrder())
                .orElse(0);
        Categoria.actualizarSecuencia(maxCategoria + 1);

        int maxProducto = estado.getProductos().stream()
                .map(Producto::getId)
                .max(Comparator.naturalOrder())
                .orElse(0);
        Producto.actualizarSecuencia(maxProducto + 1);

        int maxCliente = estado.getClientes().stream()
                .map(Cliente::getId)
                .max(Comparator.naturalOrder())
                .orElse(0);
        Usuario.actualizarSecuencia(maxCliente + 1);

        int maxMetodoPago = estado.getClientes().stream()
                .flatMap(c -> c.getMetodosPago().stream())
                .map(MetodoPago::getId)
                .max(Comparator.naturalOrder())
                .orElse(0);
        MetodoPago.actualizarSecuencia(maxMetodoPago + 1);

        int maxCarrito = estado.getClientes().stream()
                .map(c -> c.getCarritoActivo())
                .filter(c -> c != null)
                .map(Carrito::getId)
                .max(Comparator.naturalOrder())
                .orElse(0);
        Carrito.actualizarSecuencia(maxCarrito + 1);

        int maxCompra = estado.getClientes().stream()
                .flatMap(c -> c.getHistorialCompras().stream())
                .map(Compra::getId)
                .max(Comparator.naturalOrder())
                .orElse(0);
        Compra.actualizarSecuencia(maxCompra + 1);
    }
}
