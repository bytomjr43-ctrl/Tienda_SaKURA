import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Carrito implements Serializable {
    private static final long serialVersionUID = 1L;
    private static int SEQ = 1;
    private final int id;
    private Cliente cliente;
    private List<LineaCarrito> lineas;
    private String fechaCreacion; // string simple

    public Carrito(Cliente cliente) {
        this.id = SEQ++;
        this.cliente = cliente;
        this.lineas = new ArrayList<>();
        this.fechaCreacion = "hoy"; // placeholder simple
    }

    public int getId() { return id; }
    public Cliente getCliente() { return cliente; }
    public List<LineaCarrito> getLineas() { return lineas; }

    public void agregarProducto(Producto p, int cantidad) {
        if (p == null || cantidad <= 0) return;
        for (LineaCarrito lc : lineas) {
            if (lc.getProducto().equals(p)) {
                lc.setCantidad(lc.getCantidad() + cantidad);
                return;
            }
        }
        lineas.add(new LineaCarrito(p, cantidad));
    }

    public Optional<LineaCarrito> buscarLineaPorProducto(int productoId) {
        return lineas.stream()
                .filter(lc -> lc.getProducto().getId() == productoId)
                .findFirst();
    }

    public boolean actualizarCantidad(int productoId, int nuevaCantidad) {
        if (nuevaCantidad <= 0) {
            return eliminarProducto(productoId);
        }
        for (LineaCarrito lc : lineas) {
            if (lc.getProducto().getId() == productoId) {
                lc.setCantidad(nuevaCantidad);
                return true;
            }
        }
        return false;
    }

    public boolean eliminarProducto(int productoId) {
        return lineas.removeIf(lc -> lc.getProducto().getId() == productoId);
    }

    public double calcularTotal() {
        double total = 0.0;
        for (LineaCarrito lc : lineas) {
            total += lc.getSubtotal();
        }
        return total;
    }

    public void limpiar() {
        lineas.clear();
    }

    public static synchronized void actualizarSecuencia(int siguienteId) {
        if (siguienteId > SEQ) {
            SEQ = siguienteId;
        }
    }
}
