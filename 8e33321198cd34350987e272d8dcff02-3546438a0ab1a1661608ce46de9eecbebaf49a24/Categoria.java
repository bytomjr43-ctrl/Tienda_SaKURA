import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Categoria implements Serializable {
    private static final long serialVersionUID = 1L;
    private static int SEQ = 1;
    private final int id;
    private String nombre;
    private String descripcion;
    private List<Producto> productos;

    public Categoria(String nombre, String descripcion) {
        this.id = SEQ++;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.productos = new ArrayList<>();
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public void setNombre(String n) { this.nombre = n; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String d) { this.descripcion = d; }
    public List<Producto> getProductos() { return productos; }

    public void agregarProducto(Producto p) {
        if (p != null && !productos.contains(p)) {
            productos.add(p);
        }
    }

    public static synchronized void actualizarSecuencia(int siguienteId) {
        if (siguienteId > SEQ) {
            SEQ = siguienteId;
        }
    }
}

