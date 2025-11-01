import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Compra implements Serializable {
    private static final long serialVersionUID = 1L;
    private static int SEQ = 1;
    private final int id;
    private Cliente cliente;
    private List<LineaCompra> lineas;
    private double total;
    private String estado; // ejemplos: "PAGADA", "ENVIADA"

    public Compra(Cliente cliente) {
        this.id = SEQ++;
        this.cliente = cliente;
        this.lineas = new ArrayList<>();
        this.total = 0.0;
        this.estado = "CREADA";
    }

    public int getId() { return id; }
    public Cliente getCliente() { return cliente; }
    public List<LineaCompra> getLineas() { return lineas; }
    public double getTotal() { return total; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public void agregarLinea(LineaCompra lc) {
        if (lc != null) {
            lineas.add(lc);
            calcularTotal();
        }
    }

    private void calcularTotal() {
        double t = 0.0;
        for (LineaCompra lc : lineas) t += lc.getSubtotal();
        this.total = t;
    }

    public static synchronized void actualizarSecuencia(int siguienteId) {
        if (siguienteId > SEQ) {
            SEQ = siguienteId;
        }
    }
}
