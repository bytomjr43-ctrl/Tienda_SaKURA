import java.util.ArrayList;
import java.util.List;

public class Cliente extends Usuario {
    private String direccionEnvio;
    private String telefono;
    private List<MetodoPago> metodosPago;
    private Carrito carritoActivo;
    private List<Compra> historialCompras;

    public Cliente(String nombre, String email, String passwordHash, String direccionEnvio, String telefono) {
        super(nombre, email, passwordHash);
        this.direccionEnvio = direccionEnvio;
        this.telefono = telefono;
        this.metodosPago = new ArrayList<>();
        this.carritoActivo = new Carrito(this);
        this.historialCompras = new ArrayList<>();
    }


    public String getDireccionEnvio() { return direccionEnvio; }
    public void setDireccionEnvio(String d) { this.direccionEnvio = d; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String t) { this.telefono = t; }
    public List<MetodoPago> getMetodosPago() { return metodosPago; }
    public Carrito getCarritoActivo() { return carritoActivo; }
    public List<Compra> getHistorialCompras() { return historialCompras; }

    public void agregarMetodoPago(MetodoPago m) { metodosPago.add(m); }
    public void agregarCompraAlHistorial(Compra c) { historialCompras.add(c); }
}
