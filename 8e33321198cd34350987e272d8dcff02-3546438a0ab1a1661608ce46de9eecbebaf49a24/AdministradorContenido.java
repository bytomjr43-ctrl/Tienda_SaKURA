public class AdministradorContenido extends Usuario {
    private String permisosEdicion; // simple; en clase se vió string o flags

    public AdministradorContenido(String nombre, String email, String passwordHash, String permisosEdicion) {
        super(nombre, email, passwordHash);
        this.permisosEdicion = permisosEdicion;
    }

    public String getPermisosEdicion() { return permisosEdicion; }
    public void setPermisosEdicion(String p) { this.permisosEdicion = p; }


    public void publicarProducto(Producto p) {
    }
}