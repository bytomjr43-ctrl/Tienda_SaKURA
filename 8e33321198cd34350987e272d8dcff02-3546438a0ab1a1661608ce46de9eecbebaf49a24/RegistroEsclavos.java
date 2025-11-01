import java.util.ArrayList;
import java.util.List;

public class RegistroEsclavos {
    private List<TrabajadorEsclavizado> lista;
    private String nivelCifrado; // dato simbólico
    private String ultimoAcceso;

    public RegistroEsclavos() {
        this.lista = new ArrayList<>();
        this.nivelCifrado = "ALTO";
        this.ultimoAcceso = "nunca";
    }

    public List<TrabajadorEsclavizado> getLista() { return lista; }
    public void agregar(TrabajadorEsclavizado t) {
        if (t != null && !lista.contains(t)) lista.add(t);
    }

    public void registrarAcceso(String fecha) { this.ultimoAcceso = fecha; }
}