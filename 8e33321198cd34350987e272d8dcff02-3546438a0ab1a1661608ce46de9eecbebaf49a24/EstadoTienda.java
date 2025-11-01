import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EstadoTienda implements Serializable {
    private static final long serialVersionUID = 1L;

    private final List<Categoria> categorias;
    private final List<Producto> productos;
    private final List<Cliente> clientes;

    public EstadoTienda() {
        this.categorias = new ArrayList<>();
        this.productos = new ArrayList<>();
        this.clientes = new ArrayList<>();
    }

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public List<Categoria> getCategoriasInmutables() {
        return Collections.unmodifiableList(categorias);
    }

    public List<Producto> getProductosInmutables() {
        return Collections.unmodifiableList(productos);
    }

    public List<Cliente> getClientesInmutables() {
        return Collections.unmodifiableList(clientes);
    }
}
