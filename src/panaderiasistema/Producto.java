package panaderiasistema;


public class Producto {
    private String codigo;
    private String nombre;
    private int idCategoria;
    private int idProveedor;
    private double precio;
    private int stock;
    
    private String nombreCategoria;
    private String nombreProveedor;

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }
    
 
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
    
    
      // Constructor
    public Producto(String codigo, String nombre, int idCategoria, int idProveedor, double precio, int stock) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.idCategoria = idCategoria;
        this.idProveedor = idProveedor;
        this.precio = precio;
        this.stock = stock;
    }
    
    public Producto() {}  // para uso flexible con setters
     
    // Getters
    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public int getIdCategoria() { return idCategoria; }
    public int getIdProveedor() { return idProveedor; }
    public double getPrecio() { return precio; }
    public int getStock() { return stock; }
    public String getNombreCategoria() { return nombreCategoria; }
    public String getNombreProveedor() { return nombreProveedor; }
}
