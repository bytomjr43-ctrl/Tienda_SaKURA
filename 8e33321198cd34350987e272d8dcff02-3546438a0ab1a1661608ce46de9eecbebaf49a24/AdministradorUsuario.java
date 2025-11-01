public class AdministradorUsuario extends Usuario {
    private String nivelAcceso;

    public AdministradorUsuario(String nombre, String email, String passwordHash, String nivelAcceso) {
        super(nombre, email, passwordHash);
        this.nivelAcceso = nivelAcceso;
    }

    public String getNivelAcceso() { return nivelAcceso; }
    public void setNivelAcceso(String n) { this.nivelAcceso = n; }

    public void suspenderUsuario(Usuario u) {
        if (u != null) u.setActivo(false);
    }

    public void reactivarUsuario(Usuario u) {
        if (u != null) u.setActivo(true);
    }
}