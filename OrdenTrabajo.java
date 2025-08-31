import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class OrdenTrabajo {
    private Cliente cliente;// Objeto de tipo cliente vinculado a la orden de trabajo
    private Trabajador encargado;// Objeto de tipo Trabajador vinculado a la orden de trabajo
    private Analisis analisis;// Objeto de tipo Analisis vinculado a la orden
    private String estado;// Estado en el que se encuentra la orden de trabajo (En proceso, finalizada)
    private LocalDate fechaEstimada;// Fecha estimada de finalizacion de orden ,formato (año-mes-dia)
    
    //#region CONSTRUCTORES

    public OrdenTrabajo(Cliente cliente, Trabajador encargado, Analisis analisis, String estado){//Constructor por defecto (Sin fecha)
        this.cliente = cliente;
        this.encargado = encargado;
        this.analisis = analisis;
        this.estado = estado;
        this.fechaEstimada = (LocalDate.now()).plusDays(7);//Asigna fecha estimada a la proxima semana
    }

    public OrdenTrabajo(Cliente cliente, Trabajador encargado, Analisis analisis, String estado,String fechaEstimada){//Constructor con fecha
        this.cliente = cliente;
        this.encargado = encargado;
        this.analisis = analisis;
        this.estado = estado;
        setFechaEstimada(fechaEstimada);//Asigna fecha estimada a lo ingresado por el usuario
    }

    //#endregion CONSTRUCTORES

    //#region GETTERS Y SETTERS

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Trabajador getEncargado() {
        return encargado;
    }

    public void setEncargado(Trabajador encargado) {
        this.encargado = encargado;
    }

    public Analisis getAnalisis() {
        return analisis;
    }

    public void setAnalisis(Analisis analisis) {
        this.analisis = analisis;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {// TO DO: Limitar entradas

        this.estado = estado;
    }

    public String getFechaEstimada() {// Retorna la fecha estimada como String en formato (día-mes-año)
        String fechaEstimadaString;
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        fechaEstimadaString = this.fechaEstimada.format(formato);

        return fechaEstimadaString;
    }

    public void setFechaEstimada(String fechaEstimada) {//
        LocalDate hoy = LocalDate.now();
        fechaEstimada.trim().toLowerCase();//Elimina espacios antes y despues del texto y lo convierte a minusculas para evitar errores

        //El caso más corto de input es "1 día" o "1 mes", ambos strings de 5 de largo
        //Si el input fecha estimada tiene menos de 5 caracteres o el primer caracter no es un numero positivo
        if (fechaEstimada.length() < 5 || fechaEstimada.split(" ")[0].matches("\\d+")) {
            throw new IllegalArgumentException("Formato incorrecto. Debe ser: '<número> dias/semanas/meses'");//Mensaje error
        }

        else if (fechaEstimada.contains("dia")) {
            int dias = Integer.parseInt(fechaEstimada.split(" ")[0]);
            this.fechaEstimada = hoy.plusDays(dias);
        }

        else if (fechaEstimada.contains("semana")) {
            int semanas = Integer.parseInt(fechaEstimada.split(" ")[0]);
            this.fechaEstimada = hoy.plusWeeks(semanas);
        }
        
        else if (fechaEstimada.contains("mes")) {
            int meses = Integer.parseInt(fechaEstimada.split(" ")[0]);
            this.fechaEstimada = hoy.plusMonths(meses);
        }
    }

    //#endregion GETTERS Y SETTERS
}