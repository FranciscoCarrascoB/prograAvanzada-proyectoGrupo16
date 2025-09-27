public class Cliente extends Persona {

    int puntosFidelidad; // Puntos de fidelidad del cliente (por implementar...)

    // Constructor
    public Cliente(String rut, String nombre) {
        super(rut, nombre);
        this.puntosFidelidad = 0;
    }

    public int getPuntosFidelidad() {
        return puntosFidelidad;
    }

    public void setPuntosFidelidad(int puntos) {
        puntosFidelidad = puntos;
    }

    public void agregarPuntosFidelidad(int puntos) {
        puntosFidelidad += puntos;
    }

    // SIA 2.7: Implementación de sobreescritura del método obtenerInformacion()
    @Override
    public String obtenerInformacion() {
        return "Trabajador: " + nombre + " (RUT: " + rut + " Puntos de fidelidad: " + puntosFidelidad + ")";
    }
}