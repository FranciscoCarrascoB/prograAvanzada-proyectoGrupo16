import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Inventario {
    private HashMap<String, Pieza> inventario; // HashMap (SKU, Pieza) que contiene las piezas disponibles en el inventario

    // Constructor
    public Inventario() {
        inventario = new HashMap<>();
    }

    // Métodos
    public boolean agregarPieza(Pieza pieza) {
        if (pieza != null && pieza.getSKU() != null) {
            this.inventario.put(pieza.getSKU(), pieza); // (SKU, Pieza)
            return true;
        }
        return false;
    }

    public Pieza buscarPorSKU(String SKU) {
        // Se utiliza el método get(...) de HashMap para una búsqueda eficiente
        return inventario.get(SKU);
    }
    
    public List<Pieza> buscarPorNombre(String nombre) {
        List<Pieza> resultados = new ArrayList<>();
        for (Pieza pieza : inventario.values()) {
            // Si el nombre de la Pieza contiene el nombre que buscamos...
            if (pieza.getNombre().toLowerCase().contains(nombre.toLowerCase())) {
                // Agregar a la lista de resultados
                resultados.add(pieza);
            }
        }
        return resultados; // Retorna un ArrayList con todos los resultados de la búsqueda
    }

    public boolean hayStock(String SKU, int cantidadNecesaria) {
        Pieza pieza = buscarPorSKU(SKU);
        return pieza != null && pieza.hayStock(cantidadNecesaria);
    }

    public int obtenerCantidadDisponible(String SKU) {
        Pieza pieza = buscarPorSKU(SKU);
        // Si pieza es un objeto válido retornar cantidad disponible. En cualquier otro caso, retornar 0
        return pieza != null ? pieza.getCantidadDisponible() : 0;
    }

    public boolean usarPieza(String SKU, int cantidad) {
        Pieza pieza = buscarPorSKU(SKU);
        return pieza != null && pieza.reducirStock(cantidad);
    }

    public boolean reponerStock(String SKU, int cantidad) {
        Pieza pieza = buscarPorSKU(SKU);
        if (pieza != null) {
            pieza.agregarStock(cantidad);
            return true;
        }
        return false;
    }

    public boolean eliminarPieza(String SKU) {
        return inventario.remove(SKU) != null;
    }

    public List<Pieza> obtenerStockBajo() { // Sobrecarga del método obtenerStockBajo(); obtener stock menor a de 5 por defecto
        return this.obtenerStockBajo(5);
    }

    public List<Pieza> obtenerStockBajo(int cantidad) {
        List<Pieza> stockBajo = new ArrayList<>();
        for (Pieza pieza : inventario.values()) {
            if (pieza.getCantidadDisponible() <= cantidad)
                stockBajo.add(pieza);
        }
        return stockBajo; // Retorna un ArrayList de todas las piezas que tienen un stock menor a 'cantidad
    }

    public int obtenerTotalPiezas() {
        return inventario.size();
    }

    public boolean estaVacio() {
        return inventario.isEmpty();
    }
}