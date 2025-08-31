public class Pieza {
    private String SKU; // Codigo identificador interno de la pieza
    private String nombre; // Nombre de la pieza
    private String categoria; // Categoría de la pieza
    private int cantidadDisponible; // Cantidad de piezas disponibles

    // Constructor
    public Pieza(String SKU, String nombre, String categoria, int cantidadDisponible) {
        this.SKU = SKU;
        this.nombre = nombre;
        this.categoria = categoria;
        this.cantidadDisponible = cantidadDisponible;
    }

    // Getters
    public String getSKU() {
        return SKU;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public String getCategoria() {
        return categoria;
    }
    
    public int getCantidadDisponible() {
        return cantidadDisponible;
    }
    
    // Setters
    public void setNombre(String nombre) {
        if (nombre != null && !nombre.trim().isEmpty())
            this.nombre = nombre;
    }
    
    public void setCategoria(String categoria) {
        if (categoria != null && !categoria.trim().isEmpty())
            this.categoria = categoria;
    }
    
    public void setCantidadDisponible(int cantidadDisponible) {
        if (cantidadDisponible > 0)
            this.cantidadDisponible = cantidadDisponible;
    }

    // Métodos
    public boolean hayStock(int cantidadNecesaria) {
        return this.cantidadDisponible >= cantidadNecesaria;
    }

    public boolean reducirStock(int cantidad) {
        if (this.hayStock(cantidad)) {
            this.cantidadDisponible -= cantidad;
            return true;
        }
        return false;
    }

    public void agregarStock(int cantidad) {
        if (cantidad > 0) {
            this.cantidadDisponible += cantidad;
        }
    }
}