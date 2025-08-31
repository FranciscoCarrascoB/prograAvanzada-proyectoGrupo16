import java.util.ArrayList;
import java.util.Scanner;

public class Analisis {
    private String descripcionProblema;
    private String diagnostico;
    private ArrayList<String> piezasNecesarias;

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

    // Método para ingresar datos del análisis mediante consola
    public void ingresarDatosAnalisis() {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("\n=== INGRESO DE ANÁLISIS ===");
        
        // Ingreso de descripción del problema
        System.out.print("Descripción del problema: ");
        this.descripcionProblema = scanner.nextLine();
        
        // Ingreso de diagnóstico con validación
        boolean diagnosticoValido = false;
        while (!diagnosticoValido) {
            System.out.print("Diagnóstico (Hardware/Software/Red/Periféricos/Otro): ");
            String inputDiagnostico = scanner.nextLine();
            
            if (inputDiagnostico != null && !inputDiagnostico.trim().isEmpty()) {
                this.setDiagnostico(inputDiagnostico);
                diagnosticoValido = true;
            } else {
                System.out.println("Error: El diagnóstico no puede estar vacío.");
            }
        }
        
        // Opción para piezas necesarias
        System.out.println("\n¿Se necesitan piezas para la reparación?");
        System.out.println("1. Sí, necesito piezas");
        System.out.println("2. No, no se necesitan piezas");
        System.out.print("Seleccione una opción (1-2): ");
        
        String opcionPiezas = scanner.nextLine();
        
        switch (opcionPiezas) {
            case "1":
                ingresarPiezasNecesarias(scanner);
                break;
            case "2":
                System.out.println("No se agregarán piezas al análisis.");
                break;
            default:
                System.out.println("Opción no válida. No se agregarán piezas.");
                break;
        }
        
        System.out.println("Análisis completado exitosamente.");
    }

    // Método para ingresar piezas necesarias
    private void ingresarPiezasNecesarias(Scanner scanner) {
        System.out.println("\nIngreso de piezas necesarias:");
        System.out.println("Opciones disponibles:");
        System.out.println("1. Ingresar piezas manualmente");
        System.out.println("2. Usar sugerencias basadas en el diagnóstico");
        System.out.print("Seleccione una opción (1-2): ");
        
        String opcionIngreso = scanner.nextLine();
        
        switch (opcionIngreso) {
            case "1":
                ingresarPiezasManual(scanner);
                break;
            case "2":
                this.setPiezasNecesarias(); // Usa sobrecarga sin parámetros
                break;
            default:
                System.out.println("Opción no válida. Se usarán sugerencias del diagnóstico.");
                this.setPiezasNecesarias();
                break;
        }
    }

    // Método para ingreso manual de piezas
    private void ingresarPiezasManual(Scanner scanner) {
        System.out.println("\nIngreso manual de piezas (ingrese 'fin' para terminar):");
        String pieza;
        do {
            System.out.print("SKU de la pieza: ");
            pieza = scanner.nextLine();
            
            if (!pieza.equalsIgnoreCase("fin") && !pieza.trim().isEmpty()) {
                this.agregarPiezaNecesaria(pieza);
                System.out.println("Pieza agregada: " + pieza);
            } else if (pieza.trim().isEmpty() && !pieza.equalsIgnoreCase("fin")) {
                System.out.println("Error: El SKU no puede estar vacío.");
            }
        } while (!pieza.equalsIgnoreCase("fin"));
        
        System.out.println("Ingreso manual de piezas finalizado.");
    }

    // Getters y setters
    public String getDescripcionProblema() {
        return descripcionProblema;
    }

    public void setDescripcionProblema(String descripcionProblema) {
        if (descripcionProblema != null && !descripcionProblema.trim().isEmpty()) {
            this.descripcionProblema = descripcionProblema;
        } else {
            System.out.println("Error: La descripción no puede estar vacía.");
        }
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        // Validación de diagnóstico permitido
        String[] diagnosticosPermitidos = {"Hardware", "Software", "Red", "Periféricos", "Otro"};
        for (String permitido : diagnosticosPermitidos) {
            if (permitido.equalsIgnoreCase(diagnostico)) {
                this.diagnostico = diagnostico;
                return;
            }
        }
        this.diagnostico = "Otro";
        System.out.println("Diagnóstico no reconocido. Se asignó como 'Otro'.");
    }

    public ArrayList<String> getPiezasNecesarias() {
        return piezasNecesarias;
    }

    // SOBRECARGA DE MÉTODOS
    // Método 1: Sin parámetros - usa el diagnóstico para sugerir piezas
    public void setPiezasNecesarias() {
        if (this.diagnostico != null) {
            System.out.println("\nSugiriendo piezas basadas en el diagnóstico: " + this.diagnostico);
            
            // Limpiar lista existente primero
            this.piezasNecesarias.clear();
            // Todos los casos son ejemplos; en un caso real, estos datos podrían venir de una base de datos
            switch (this.diagnostico.toLowerCase()) {
                case "hardware":
                    this.piezasNecesarias.add("SKU-CPU-001");
                    this.piezasNecesarias.add("SKU-RAM-001");
                    System.out.println("Piezas sugeridas para Hardware agregadas: CPU, RAM");
                    break;
                case "software":
                    this.piezasNecesarias.add("SKU-SO-001");
                    this.piezasNecesarias.add("SKU-OFFICE-001");
                    System.out.println("Piezas sugeridas para Software agregadas: Sistema Operativo, Suite Office");
                    break;
                case "red":
                    this.piezasNecesarias.add("SKU-NIC-001");
                    this.piezasNecesarias.add("SKU-CABLE-001");
                    System.out.println("Piezas sugeridas para Red agregadas: Tarjeta de Red, Cable");
                    break;
                case "periféricos":
                    this.piezasNecesarias.add("SKU-TECLADO-001");
                    this.piezasNecesarias.add("SKU-MOUSE-001");
                    System.out.println("Piezas sugeridas para Periféricos agregadas: Teclado, Mouse");
                    break;
                default:
                    this.piezasNecesarias.add("SKU-GEN-001");
                    System.out.println("Pieza general sugerida agregada: Componente General");
            }
        } else {
            System.out.println("Error: Primero debe establecer un diagnóstico.");
        }
    }

    // Método 2: Con parámetro - recibe una lista específica
    public void setPiezasNecesarias(ArrayList<String> piezasNecesarias) {
        if (piezasNecesarias != null) {
            this.piezasNecesarias = piezasNecesarias;
            System.out.println("Lista de piezas actualizada.");
        } else {
            System.out.println("Error: La lista de piezas no puede ser nula.");
        }
    }

    // Método para agregar pieza individual
    public void agregarPiezaNecesaria(String sku) {
        if (sku != null && !sku.trim().isEmpty()) {
            this.piezasNecesarias.add(sku);
        } else {
            System.out.println("Error: El SKU no puede estar vacío.");
        }
    }

    // Método para verificar si necesita piezas
    public boolean necesitaPiezas() {
        return !this.piezasNecesarias.isEmpty();
    }

    // Método para verificar disponibilidad de piezas en inventario
    public boolean verificarDisponibilidadPiezas(Inventario inventario) {
        if (!this.necesitaPiezas()) {
            return true; // No necesita piezas, siempre disponible
        }
        
        for (String sku : this.piezasNecesarias) {
            if (inventario.obtenerCantidadDisponible(sku) <= 0) {
                System.out.println("Pieza no disponible: " + sku);
                return false;
            }
        }
        return true;
    }

    // Método para mostrar el análisis en formato legible
    public void mostrarAnalisis() {
        System.out.println("\n=== DETALLES DEL ANÁLISIS ===");
        System.out.println("Descripción: " + (descripcionProblema != null ? descripcionProblema : "No especificada"));
        System.out.println("Diagnóstico: " + (diagnostico != null ? diagnostico : "No especificado"));
        
        if (this.necesitaPiezas()) {
            System.out.println("Piezas necesarias: " + piezasNecesarias.toString());
            System.out.println("Total de piezas requeridas: " + piezasNecesarias.size());
        } else {
            System.out.println("Piezas necesarias: No se requieren piezas para esta reparación");
        }
    }

    // Método para usar en listarAnalisis() de OrdenTrabajo
    public String getResumenAnalisis() {
        StringBuilder resumen = new StringBuilder();
        resumen.append("Diagnóstico: ").append(diagnostico != null ? diagnostico : "No especificado")
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

    // **Idea: Sobrecarga de método setPiezasNecesarias. Si no se proveen argumentos, utilizar this.diagnostico para 
    // poblar la lista de piezas necesarias.
    
    // Idea: Si nos sobra tiempo, implementar una orden de armado de PC 
    // Plantillas de piezasNecesarias que representan las gamas prehechas (baja, media, alta)
