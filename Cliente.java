public class Cliente {
    private String rut; // RUT del cliente
    private String nombre; // Nombre del cliente

    // Constructor
    public Cliente(String rut, String nombre){
        this.rut = rut;
        this.nombre = nombre;
    }

    // Getters
    public String getRut() {
        return rut;
    }

    public String getNombre() {
        return nombre;
    }

    // Setters
    public void setRut(String rut) {
        this.rut = rut;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}