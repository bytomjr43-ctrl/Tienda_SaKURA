import java.io.Serializable;

public class MetodoPago implements Serializable {
    private static final long serialVersionUID = 1L;
    private static int SEQ = 1;
    private final int id;
    private String tipo; // e.g., "TARJETA", "PAYPAL"
    private String titular;
    private String numeroEnmascarado;

    public MetodoPago(String tipo, String titular, String numero) {
        this.id = SEQ++;
        this.tipo = tipo;
        this.titular = titular;
        this.numeroEnmascarado = mask(numero);
    }

    private String mask(String numero) {
        if (numero == null) return "****";
        int n = numero.length();
        if (n <= 4) return "****";
        return "****" + numero.substring(n-4);
    }

    public int getId() { return id; }
    public String getTipo() { return tipo; }
    public String getTitular() { return titular; }
    public String getNumeroEnmascarado() { return numeroEnmascarado; }

    public static synchronized void actualizarSecuencia(int siguienteId) {
        if (siguienteId > SEQ) {
            SEQ = siguienteId;
        }
    }
}
