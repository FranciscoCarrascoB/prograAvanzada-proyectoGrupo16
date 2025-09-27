public class Trabajador extends Persona {

    String rol; // Rol del trabajador

    // Constructor
    public Trabajador(String rut, String nombre) {
        super(rut, nombre);
        this.rol = "Técnico"; // Asigna rol por defecto
    }

    // SIA 2.7: Implementación de sobreescritura del método obtenerInformacion()
    @Override
    public String obtenerInformacion() {
        return "Trabajador: " + nombre + " (" + rol + " , RUT: " + rut + ")";
    }
}