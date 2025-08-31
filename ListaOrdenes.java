import java.util.ArrayList;

public class ListaOrdenes{
    private ArrayList<OrdenTrabajo> ordenes; // ArrayList que contiene las ordenes de trabajo

    public ListaOrdenes(){
        this.ordenes = new ArrayList<>();
    }

    public void agregarOrden(OrdenTrabajo orden){
        ordenes.add(orden);
    }

    public void listarOrdenes(){
        if(ordenes.isEmpty()){
            return;
        }

        System.out.println("\n--- LISTA DE ÓRDENES ---");
        for (OrdenTrabajo orden : ordenes) {
            //System.out.println(orden.getCliente());
            System.out.println(orden.getCliente().getNombre());
            //System.out.println(orden.getEncargado());
            orden.listarAnalisis();
            System.out.println(orden.getEstado());
            System.out.println(orden.getFechaEstimada());
        }
        System.out.println("------------------------\n");
    }

    //TO DO: Hacer metodos para listarOrdenes según ciertos filtros (fecha,cliente,encargado,estado)
    // TO DO: Se podría implementar un método que reciba un trabajador y retorne una lista de ordenes de las que esté encargado
}



