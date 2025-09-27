import java.util.ArrayList;

public class Analisis {
    private String descripcionProblema; // Descripción del problema reportado
    private String diagnostico; // Diagnóstico realizado
    private ArrayList<String> piezasNecesarias; // Lista de SKUs de piezas necesarias para la reparación

    // Diagnósticos permitidos
    private static final String[] DIAGNOSTICOS_PERMITIDOS = {
        "Hardware", "Software", "Red", "Perifericos", "Otro"
    };

    // Constructor por defecto
    public Analisis() {
        this.piezasNecesarias = new ArrayList<>();
    }

    // Constructor con parámetros básicos
    public Analisis(String descripcionProblema, String diagnostico) {
        this.descripcionProblema = descripcionProblema;
        this.setDiagnostico(diagnostico);
        this.piezasNecesarias = new ArrayList<>();
    }

    // Getters
    public String getDescripcionProblema() {
        return descripcionProblema;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public ArrayList<String> getPiezasNecesarias() {
        return new ArrayList<>(piezasNecesarias); // Retorna copia
    }

    public static String[] getDiagnosticosPermitidos() {
        return DIAGNOSTICOS_PERMITIDOS.clone(); // Retorna copia
    }

    // Setters
    public boolean setDescripcionProblema(String descripcionProblema) {
        if (descripcionProblema != null && !descripcionProblema.trim().isEmpty()) {
            this.descripcionProblema = descripcionProblema.trim();
            return true;
        }
        return false;
    }

    public void setDiagnostico(String diagnostico) throws DiagnosticoInvalidoException {
    if (!esDiagnosticoValido(diagnostico)) {
        // Tirar excepción si el diagnóstico no es válido, para que lo maneje el me
        throw new DiagnosticoInvalidoException("Diagnóstico inválido: '" + diagnostico + 
            "'. Los diagnósticos permitidos son: " + String.join(", ", DIAGNOSTICOS_PERMITIDOS));
        }
        this.diagnostico = diagnostico;
    }

    // Método para validar diagnóstico sin asignar
    public boolean esDiagnosticoValido(String diagnostico) {
        if (diagnostico == null) return false;
        
        for (String permitido : DIAGNOSTICOS_PERMITIDOS) {
            if (permitido.equalsIgnoreCase(diagnostico)) {
                return true;
            }
        }
        return false;
    }

    // Sobrecarga del método setPiezasNecesarias; usa el diagnóstico para agregar piezas "por defecto"
    public boolean setPiezasNecesarias() {
        if (this.diagnostico == null) {
            return false;
        }
        
        ArrayList<String> piezasSugeridas = obtenerPiezasSugeridas();
        this.piezasNecesarias = piezasSugeridas;
        return true;
    }

    // Método para asignar piezasNecesarias a apartir de una lista específica
    public boolean setPiezasNecesarias(ArrayList<String> piezasNecesarias) {
        if (piezasNecesarias != null) {
            this.piezasNecesarias = new ArrayList<>(piezasNecesarias);
            return true;
        }
        return false;
    }

    // Método auxiliar para obtener piezas sugeridas sin asignarlas
    public ArrayList<String> obtenerPiezasSugeridas() {
        ArrayList<String> sugerencias = new ArrayList<>();
        
        if (this.diagnostico == null) {
            return sugerencias;
        }
        
        switch (this.diagnostico.toLowerCase()) {
            case "hardware":
                sugerencias.add("SKU-CPU-001");
                sugerencias.add("SKU-RAM-001");
                break;
            case "software":
                sugerencias.add("SKU-SO-001");
                sugerencias.add("SKU-OFFICE-001");
                break;
            case "red":
                sugerencias.add("SKU-NIC-001");
                sugerencias.add("SKU-CABLE-001");
                break;
            case "perifericos":
                sugerencias.add("SKU-TECLADO-001");
                sugerencias.add("SKU-MOUSE-001");
                break;
            default:
                sugerencias.add("SKU-GEN-001");
        }
        
        return sugerencias;
    }

    // Método para agregar pieza individual
    public boolean agregarPiezaNecesaria(String sku) {
        if (sku != null && !sku.trim().isEmpty()) {
            this.piezasNecesarias.add(sku.trim());
            return true;
        }
        return false;
    }

    // Método para limpiar todas las piezas
    public void limpiarPiezasNecesarias() {
        this.piezasNecesarias.clear();
    }

    // Método para remover pieza específica
    public boolean removerPiezaNecesaria(String sku) {
        return this.piezasNecesarias.remove(sku);
    }

    // Método para verificar si necesita piezas
    public boolean necesitaPiezas() {
        return !this.piezasNecesarias.isEmpty();
    }

    // Método para obtener cantidad de piezas
    public int getCantidadPiezas() {
        return this.piezasNecesarias.size();
    }

    // Método para verificar disponibilidad de piezas en inventario
    public boolean verificarDisponibilidadPiezas(Inventario inventario) {
        if (!this.necesitaPiezas()) {
            return true; // No necesita piezas, siempre disponible
        }
        
        for (String sku : this.piezasNecesarias) {
            if (inventario.obtenerCantidadDisponible(sku) <= 0) {
                return false;
            }
        }
        return true;
    }

    // Método para obtener piezas no disponibles
    public ArrayList<String> obtenerPiezasNoDisponibles(Inventario inventario) {
        ArrayList<String> noDisponibles = new ArrayList<>();
        
        for (String sku : this.piezasNecesarias) {
            if (inventario.obtenerCantidadDisponible(sku) <= 0) {
                noDisponibles.add(sku);
            }
        }
        
        return noDisponibles;
    }

    // Método para validar que el análisis esté completo
    public boolean esAnalisisCompleto() {
        return descripcionProblema != null && !descripcionProblema.trim().isEmpty() &&
               diagnostico != null && !diagnostico.trim().isEmpty();
    }

    // Método para mostrar el análisis en formato legible
    public void mostrarAnalisis() {
        System.out.println("\n=== DETALLES DEL ANALISIS ===");
        System.out.println("Descripcion: " + (descripcionProblema != null ? descripcionProblema : "No especificada"));
        System.out.println("Diagnostico: " + (diagnostico != null ? diagnostico : "No especificado"));
        
        if (this.necesitaPiezas()) {
            System.out.println("Piezas necesarias: " + piezasNecesarias.toString());
            System.out.println("Total de piezas requeridas: " + piezasNecesarias.size());
        } else {
            System.out.println("Piezas necesarias: No se requieren piezas para esta reparacion");
        }
    }

    // Método para usar en listarAnalisis() de OrdenTrabajo
    public String getResumenAnalisis() {
        StringBuilder resumen = new StringBuilder();
        resumen.append("Diagnostico: ").append(diagnostico != null ? diagnostico : "No especificado")
               .append(" | Piezas: ").append(necesitaPiezas() ? piezasNecesarias.size() : "0");
        return resumen.toString();
    }

    @Override
    public String toString() {
        return "Analisis{" +
                "descripcionProblema='" + descripcionProblema + '\'' +
                ", diagnostico='" + diagnostico + '\'' +
                ", piezasNecesarias=" + piezasNecesarias +
                ", necesitaPiezas=" + this.necesitaPiezas() +
                '}';
    }
}

    // Idea: Sobrecarga de método setPiezasNecesarias. Si no se proveen argumentos, utilizar this.diagnostico para 
    // poblar la lista de piezas necesarias.
    
    // Idea: Si nos sobra tiempo, implementar una orden de armado de PC.
    // Plantillas de piezasNecesarias que representan las gamas prehechas (baja, media, alta).