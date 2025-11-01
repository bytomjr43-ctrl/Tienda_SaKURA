import java.util.ArrayList;
import java.util.List;

public class DesarrolladorProducto extends Usuario {
    private String especialidad;
    private List<Producto> productosDesarrollados;

    public DesarrolladorProducto(String nombre, String email, String passwordHash, String especialidad) {
        super(nombre, email, passwordHash);
        this.especialidad = especialidad;
        this.productosDesarrollados = new ArrayList<>();
    }

    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String s) { this.especialidad = s; }
    public List<Producto> getProductosDesarrollados() { return productosDesarrollados; }

    public void agregarProducto(Producto p) {
        if (p != null && !productosDesarrollados.contains(p)) {
            productosDesarrollados.add(p);
            p.agregarDesarrollador(this);
        }
    }
}