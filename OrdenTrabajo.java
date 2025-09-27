import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class OrdenTrabajo {
    private Cliente cliente;    // Objeto de tipo cliente vinculado a la orden de trabajo
    private Trabajador encargado;   // Objeto de tipo Trabajador vinculado a la orden de trabajo
    private ArrayList<Analisis> listaAnalisis;  // Objeto de tipo Analisis vinculado a la orden
    private String estado;  // Estado en el que se encuentra la orden de trabajo (En proceso, finalizada)
    private LocalDate fechaEstimada;    // Fecha estimada de finalizacion de orden ,formato (año-mes-dia)

    // Constructor por defecto (sin fecha)
    public OrdenTrabajo(Cliente cliente, Trabajador encargado, String estado) {
        this.cliente = cliente;
        this.encargado = encargado;
        this.listaAnalisis = new ArrayList<>();
        setEstado(estado);
        this.fechaEstimada = (LocalDate.now()).plusDays(7); // Asigna fecha estimada a la proxima semana
    }

    // Constructor con fecha
    public OrdenTrabajo(Cliente cliente, Trabajador encargado, String estado, String fechaEstimada) {
        this.cliente = cliente;
        this.encargado = encargado;
        this.listaAnalisis = new ArrayList<>();
        setEstado(estado);
        setFechaEstimada(fechaEstimada); // Asigna fecha estimada a lo ingresado por el usuario
    }

    // Getters
    public Cliente getCliente() {
        return cliente;
    }

    public Trabajador getEncargado() {
        return encargado;
    }

    public String getEstado() {
        return estado;
    }

    public ArrayList<Analisis> getListaAnalisis() {
        return listaAnalisis;
    }

    public String getFechaEstimada() {  // Retorna la fecha estimada como String en formato (día-mes-año)
        String fechaEstimadaString;
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        fechaEstimadaString = this.fechaEstimada.format(formato);

        return fechaEstimadaString;
    }

    // Setters
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public void setEncargado(Trabajador encargado) {
        this.encargado = encargado;
    }

    public boolean setEstado(String estado) {
        //Se definen los estados posibles
        Set<String> estadosPosibles = new HashSet<>();
        estadosPosibles.add("En progreso");
        estadosPosibles.add("Completada");
        estadosPosibles.add("Atrasada");

        if(!estadosPosibles.contains(estado)) { //Si el estado ingresado no existe, retorna falso
            this.estado = "Desconocido";
            return false;
        }

        else this.estado = estado;  //Si no, definir como estado ingresado y retorna true
        return true;
    }

    public void setFechaEstimada(String fechaEstimada) {//
        LocalDate hoy = LocalDate.now();
        //Elimina espacios antes y despues del texto y lo convierte a minusculas para evitar errores
        fechaEstimada.trim().toLowerCase();

        //  El caso más corto de input es "1 día" o "1 mes", ambos strings de 5 de largo
        //  Si el input fecha estimada tiene menos de 5 caracteres o el primer caracter no es un numero positivo...
        if (fechaEstimada.length() < 5 || !fechaEstimada.split(" ")[0].matches("\\d+")) {
            this.fechaEstimada = hoy.plusDays(7); // Asigna fecha por defecto
            // throw new IllegalArgumentException("Formato incorrecto. Debe ser: '<número> dias/semanas/meses'"); // Mensaje de error para más adelante
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

    // Métodos

    public void agregarAnalisis(Analisis analisis) {
        this.listaAnalisis.add(analisis);
    }

    public void listarAnalisis() {
        if (listaAnalisis.isEmpty()) {
            System.out.println("Orden no tiene analisis. Falta diagnosticar el problema.");
            return;
        }

        System.out.println("--- LISTA DE ANALISIS ---\n");
        for (int i = 0; i < listaAnalisis.size(); i++) {
            Analisis analisis = listaAnalisis.get(i);
            System.out.println("    Analisis #" + (i + 1) + ":");
            System.out.println("    Descripcion: " + analisis.getDescripcionProblema());
            System.out.println("    Diagnostico: " + analisis.getDiagnostico());
            
            if (analisis.necesitaPiezas()) {
                System.out.println("    Piezas requeridas (" + analisis.getCantidadPiezas() + "):");
                ArrayList<String> piezas = analisis.getPiezasNecesarias();
                for (String pieza : piezas) {
                    System.out.println("      - " + pieza);
                }
            } else {
                System.out.println("    Piezas requeridas: Ninguna");
            }
            System.out.println(); // Línea en blanco entre análisis
        }
    }
}