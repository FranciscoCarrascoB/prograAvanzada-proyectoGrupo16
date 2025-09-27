public abstract class Persona {
    protected String rut;
    protected String nombre;
    
    public Persona(String rut, String nombre) {
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
    
    // SIA 2.7: Método a sobrescribir en las subclases (Cliente y Trabajador)
    public abstract String obtenerInformacion();
}