import java.util.ArrayList;
import java.util.List;

public class Fabrica {
    private static int SEQ = 1;
    private final int id;
    private String pais;
    private String ciudad;
    private int capacidad;
    private boolean nivelAutomatizacion;
    private List<TrabajadorEsclavizado> trabajadores;

    public Fabrica(String pais, String ciudad, int capacidad, boolean nivelAutomatizacion) {
        this.id = SEQ++;
        this.pais = pais;
        this.ciudad = ciudad;
        this.capacidad = capacidad;
        this.nivelAutomatizacion = nivelAutomatizacion;
        this.trabajadores = new ArrayList<>();
    }

    public int getId() { return id; }
    public String getPais() { return pais; }
    public String getCiudad() { return ciudad; }
    public int getCapacidad() { return capacidad; }
    public boolean isNivelAutomatizacion() { return nivelAutomatizacion; }
    public List<TrabajadorEsclavizado> getTrabajadores() { return trabajadores; }

    public void contratarTrabajador(TrabajadorEsclavizado t) {
        if (t != null && !trabajadores.contains(t)) {
            trabajadores.add(t);
            t.setAsignadoAFabrica(this);
        }
    }
}
