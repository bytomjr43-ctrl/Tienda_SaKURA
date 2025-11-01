import java.io.Serializable;

public abstract class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;

    private static int SEQ = 1;

    private final int id;
    private String nombre;
    private String email;
    private String passwordHash;
    private boolean activo;

    public Usuario(String nombre, String email, String passwordHash) {
        this.id = SEQ++;
        this.nombre = nombre;
        this.email = email;
        this.passwordHash = passwordHash;
        this.activo = true;
    }


    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }


    public boolean authenticate(String hash) {
        return this.passwordHash != null && this.passwordHash.equals(hash);
    }

    public static synchronized void actualizarSecuencia(int siguienteId) {
        if (siguienteId > SEQ) {
            SEQ = siguienteId;
        }
    }
}

