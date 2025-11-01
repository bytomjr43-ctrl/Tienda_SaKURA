import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Producto implements Serializable {
    private static final long serialVersionUID = 1L;
    private static int SEQ = 1;
    private final int id;
    private String nombre;
    private String descripcion;
    private double precio;
    private int stock;
    private String fechaLanzamiento; // usamos String (lo básico visto en clase)
    private Categoria categoria;
    private List<DesarrolladorProducto> desarrolladores;

    public Producto(String nombre, String descripcion, double precio, int stock, String fechaLanzamiento, Categoria categoria) {
        this.id = SEQ++;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
        this.fechaLanzamiento = fechaLanzamiento;
        this.categoria = categoria;
        this.desarrolladores = new ArrayList<>();
        if (categoria != null) categoria.agregarProducto(this);
    }


    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public void setNombre(String n) { this.nombre = n; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String d) { this.descripcion = d; }
    public double getPrecio() { return precio; }
    public void setPrecio(double p) { this.precio = p; }
    public int getStock() { return stock; }
    public void setStock(int s) { this.stock = s; }
    public String getFechaLanzamiento() { return fechaLanzamiento; }
    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria c) { this.categoria = c; }
    public List<DesarrolladorProducto> getDesarrolladores() { return desarrolladores; }

    public void agregarDesarrollador(DesarrolladorProducto d) {
        if (d != null && !desarrolladores.contains(d)) desarrolladores.add(d);
    }


    public boolean hayStock(int cantidad) {
        return stock >= cantidad;
    }
    public boolean descontarStock(int cantidad) {
        if (cantidad <= 0) return false;
        if (hayStock(cantidad)) {
            stock -= cantidad;
            return true;
        }
        return false;
    }

    public static synchronized void actualizarSecuencia(int siguienteId) {
        if (siguienteId > SEQ) {
            SEQ = siguienteId;
        }
    }
}
