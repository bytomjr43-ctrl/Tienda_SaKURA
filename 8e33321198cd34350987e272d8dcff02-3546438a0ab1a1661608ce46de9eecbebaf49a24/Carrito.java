import java.util.ArrayList;
import java.util.List;

public class Carrito {
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
}
