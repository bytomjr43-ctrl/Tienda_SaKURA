import java.util.ArrayList;
import java.util.List;

public class DuenaSakura extends Usuario {
    private String claveMaestra;
    private String fechaCoronacion;
    private List<RegistroEsclavos> registrosConfidenciales;

    public DuenaSakura(String nombre, String email, String passwordHash, String claveMaestra, String fechaCoronacion) {
        super(nombre, email, passwordHash);
        this.claveMaestra = claveMaestra;
        this.fechaCoronacion = fechaCoronacion;
        this.registrosConfidenciales = new ArrayList<>();
    }

    public String getClaveMaestra() { return claveMaestra; }
    public String getFechaCoronacion() { return fechaCoronacion; }
    public List<RegistroEsclavos> getRegistrosConfidenciales() { return registrosConfidenciales; }

    public void agregarRegistroConfidencial(RegistroEsclavos r) {
        if (r != null) registrosConfidenciales.add(r);
    }


    public boolean tieneAccesoExclusivo(String clave) {
        return claveMaestra != null && claveMaestra.equals(clave);
    }
}
