public class Cliente {
    private String rut; // RUT del cliente
    private String nombre; // Nombre del cliente

    public Cliente(String rut, String nombre){
        this.rut = rut;
        this.nombre = nombre;
    }

    //#region GETTERS Y SETTERS
    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    //#endregion GETTERS Y SETTERS

}