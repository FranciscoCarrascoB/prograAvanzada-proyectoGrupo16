public class Trabajador {
    private String rut;
    private String nombre;

    // Constructor
    public Trabajador(String rut, String nombre) {
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