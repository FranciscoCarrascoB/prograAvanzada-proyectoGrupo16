import java.util.ArrayList;

public class ListaOrdenes {
    private ArrayList<OrdenTrabajo> ordenes; // ArrayList que contiene las ordenes de trabajo

    // Constructor
    public ListaOrdenes() {
        this.ordenes = new ArrayList<>();
    }

    // Métodos

    public void agregarOrden(OrdenTrabajo orden) {
        ordenes.add(orden);
    }

    public boolean tieneOrdenes() {
        return !ordenes.isEmpty();
    }

    public int getCantidadOrdenes() {
        return ordenes.size();
    }

    // Método auxiliar para obtener una orden por índice
    public OrdenTrabajo obtenerOrden(int index) {
        if (index >= 0 && index < ordenes.size()) {
            return ordenes.get(index);
        }
        return null;
    }

    public OrdenTrabajo eliminarOrden(int indice) {
        if (indice < 0 || indice >= ordenes.size()) {
            throw new IndexOutOfBoundsException("Índice de orden fuera de rango.");
        }
        return ordenes.remove(indice);
    }

    public void listarOrdenes() {
        if (ordenes.isEmpty())
            return;

        System.out.println("\n=== LISTA DE ORDENES ===\n");
        for (int i = 0; i < ordenes.size(); i++) {
            OrdenTrabajo orden = ordenes.get(i);
            
            System.out.println("====== ORDEN #" + (i + 1) + " ======");
            System.out.println(orden.getCliente().obtenerInformacion());
            System.out.println(orden.getEncargado().obtenerInformacion());
            System.out.println("Estado: " + orden.getEstado());
            System.out.println("Fecha Estimada: " + orden.getFechaEstimada());
            
            // Llamar al método listarAnalisis() de la orden
            orden.listarAnalisis();
        }
        System.out.println("========================");
    }

    // TO DO: Hacer metodos para listarOrdenes según ciertos filtros (fecha, cliente, encargado, estado)
    // TO DO: Se podría implementar un método que reciba un trabajador y retorne una lista de ordenes de las que esté encargado
}



