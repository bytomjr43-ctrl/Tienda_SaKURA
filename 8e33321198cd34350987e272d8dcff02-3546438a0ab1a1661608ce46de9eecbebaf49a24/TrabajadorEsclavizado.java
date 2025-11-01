public class TrabajadorEsclavizado {
    private static int SEQ = 1;
    private final int id;
    private String nombre;
    private String paisOrigen;
    private int edad;
    private String fechaCaptura;
    private String estadoSalud;
    private Fabrica asignadoAFabrica;

    public TrabajadorEsclavizado(String nombre, String paisOrigen, int edad, String fechaCaptura, String estadoSalud) {
        this.id = SEQ++;
        this.nombre = nombre;
        this.paisOrigen = paisOrigen;
        this.edad = edad;
        this.fechaCaptura = fechaCaptura;
        this.estadoSalud = estadoSalud;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getPaisOrigen() { return paisOrigen; }
    public int getEdad() { return edad; }
    public String getFechaCaptura() { return fechaCaptura; }
    public String getEstadoSalud() { return estadoSalud; }
    public Fabrica getAsignadoAFabrica() { return asignadoAFabrica; }
    public void setAsignadoAFabrica(Fabrica f) { this.asignadoAFabrica = f; }
}

