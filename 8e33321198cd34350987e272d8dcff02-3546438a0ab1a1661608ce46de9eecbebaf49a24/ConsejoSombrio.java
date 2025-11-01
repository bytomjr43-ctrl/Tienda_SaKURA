import java.util.ArrayList;
import java.util.List;

public class ConsejoSombrio {
    private String nombre;
    private String clave;
    private List<Usuario> miembros;

    public ConsejoSombrio(String nombre, String clave) {
        this.nombre = nombre;
        this.clave = clave;
        this.miembros = new ArrayList<>();
    }

    public String getNombre() { return nombre; }
    public void setNombre(String n) { this.nombre = n; }
    public String getClave() { return clave; }

    public void agregarMiembro(Usuario u) {
        if (u != null && !miembros.contains(u)) miembros.add(u);
    }

    public List<Usuario> getMiembros() { return miembros; }
}
