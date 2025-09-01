import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static ListaOrdenes listaOrdenes = new ListaOrdenes();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        // Datos iniciales

        // Clientes
        Cliente cliente1 = new Cliente("12.345.678-9", "Pedro Rodrígez");
        Cliente cliente2 = new Cliente("11.111.111-1", "María González");

        // Trabajadores
        Trabajador trabajador1 = new Trabajador("17.111.622-8", "Carlos López");
        Trabajador trabajador2 = new Trabajador("10.039.628-9", "Ana Martínez");
    
        // Ordenes de trabajo
        OrdenTrabajo orden1 = new OrdenTrabajo(cliente1, trabajador1, "En proceso", "1 semana");
        OrdenTrabajo orden2 = new OrdenTrabajo(cliente2, trabajador2, "En proceso", "1 mes");

        // Crear análisis para orden 1
        Analisis analisis1_1 = new Analisis("Monitor no enciende", "Hardware");
        analisis1_1.setPiezasNecesarias(new ArrayList<String>() {{ add("SKU-PANT-001"); }});
        Analisis analisis1_2 = new Analisis("Teclado no responde", "Periféricos");
        analisis1_2.setPiezasNecesarias(); // Usar sugerencias automáticas
        
        // Crear análisis para orden 2
        Analisis analisis2_1 = new Analisis("Sistema operativo corrupto", "Software");
        analisis2_1.setPiezasNecesarias(); // Usar sugerencias automáticas
        Analisis analisis2_2 = new Analisis("No hay conexión a internet", "Red");
        analisis2_2.setPiezasNecesarias(); // Usar sugerencias automáticas

        // Agregar análisis a las órdenes
        orden1.agregarAnalisis(analisis1_1);
        orden1.agregarAnalisis(analisis1_2);
        orden2.agregarAnalisis(analisis2_1);
        orden2.agregarAnalisis(analisis2_2);

        // Agregar órdenes a la lista
        listaOrdenes.agregarOrden(orden1);
        listaOrdenes.agregarOrden(orden2);

        System.out.println("=== SISTEMA DE ORDENES DE TRABAJO ===");
        
        int opcion;
        do {
            mostrarMenu();
            opcion = leerOpcion();
            
            switch (opcion) {
                case 1:
                    agregarOrdenTrabajo();
                    break;
                case 2:
                    agregarAnalisisAOrden();
                    break;
                case 3:
                    mostrarOrdenesYAnalisis();
                    break;
                case 4:
                    System.out.println("¡Gracias por usar el sistema!");
                    break;
                default:
                    System.out.println("Opcion no valida. Intente nuevamente.");
            }
            
            if (opcion != 4) {
                System.out.println("\nPresione Enter para continuar...");
                scanner.nextLine();
            }
            
        } while (opcion != 4);
        
        scanner.close();
    }

    private static void mostrarMenu() {
        System.out.println("\n==========================================");
        System.out.println("           MENU PRINCIPAL");
        System.out.println("==========================================");
        System.out.println("1. Agregar Orden de Trabajo");
        System.out.println("2. Agregar Analisis a una Orden");
        System.out.println("3. Mostrar Ordenes + Analisis");
        System.out.println("4. Salir");
        System.out.println("==========================================");
        System.out.print("Seleccione una opción (1-4): ");
    }

    private static int leerOpcion() {
        try {
            int opcion = Integer.parseInt(scanner.nextLine());
            return opcion;
        } catch (NumberFormatException e) {
            return -1; // Opción inválida
        }
    }

    private static void agregarOrdenTrabajo() {
        System.out.println("\n=== AGREGAR ORDEN DE TRABAJO ===");
        
        // Crear cliente
        Cliente cliente = crearCliente();
        if (cliente == null) return;
        
        // Crear trabajador encargado
        Trabajador encargado = crearTrabajador();
        if (encargado == null) return;
        
        // Ingresar estado
        System.out.print("Estado de la orden: ");
        String estado = scanner.nextLine().trim();
        if (estado.isEmpty()) {
            System.out.println("Error: El estado no puede estar vacío.");
            return;
        }
        
        // Ingresar fecha estimada
        String fechaEstimada = ingresarFecha();
        if (fechaEstimada == null) return;
        
        // Crear la orden de trabajo
        OrdenTrabajo orden = new OrdenTrabajo(cliente, encargado, estado, fechaEstimada);
        listaOrdenes.agregarOrden(orden);
        
        System.out.println("Orden de trabajo agregada exitosamente.");
    }

    private static Cliente crearCliente() {
        System.out.println("\n--- Datos del Cliente ---");
        System.out.print("RUT del cliente: ");
        String rutCliente = scanner.nextLine().trim();
        
        if (rutCliente.isEmpty()) {
            System.out.println("Error: El RUT del cliente no puede estar vacío.");
            return null;
        }

        System.out.print("Nombre del cliente: ");
        String nombreCliente = scanner.nextLine().trim();
        
        if (nombreCliente.isEmpty()) {
            System.out.println("Error: El nombre del cliente no puede estar vacío.");
            return null;
        }
        
        return new Cliente(rutCliente, nombreCliente);
    }

    private static Trabajador crearTrabajador() {
        System.out.println("\n--- Datos del Trabajador Encargado ---");
        System.out.print("RUT del trabajador: ");
        String rutTrabajador = scanner.nextLine().trim();
        
        if (rutTrabajador.isEmpty()) {
            System.out.println("Error: El RUT del trabajador no puede estar vacío.");
            return null;
        }

        System.out.print("Nombre del trabajador: ");
        String nombreTrabajador = scanner.nextLine().trim();
        
        if (nombreTrabajador.isEmpty()) {
            System.out.println("Error: El nombre del trabajador no puede estar vacío.");
            return null;
        }
        
        return new Trabajador(rutTrabajador, nombreTrabajador);
    }

    private static String ingresarFecha() {
        System.out.print("Fecha estimada (x dias/semanas/meses): ");
        String fechaInput = scanner.nextLine().trim();
        
        if (fechaInput.isEmpty()) {
            System.out.println("Error: La fecha no puede estar vacía.");
            return null;
        }
        
        return fechaInput;
    }

    private static void agregarAnalisisAOrden() {
        System.out.println("\n=== AGREGAR ANÁLISIS A ORDEN ===");
        
        // Verificar si hay órdenes
        if (!listaOrdenes.tieneOrdenes()) {
            System.out.println("No hay órdenes de trabajo disponibles.");
            System.out.println("Primero debe crear una orden de trabajo.");
            return;
        }
        
        // Mostrar órdenes disponibles
        listaOrdenes.listarOrdenes();
        
        // Seleccionar orden
        System.out.print("\nIngrese el número de la orden (1-" + listaOrdenes.getCantidadOrdenes() + "): ");
        int numeroOrden;
        try {
            numeroOrden = Integer.parseInt(scanner.nextLine());
            if (numeroOrden < 1 || numeroOrden > listaOrdenes.getCantidadOrdenes()) {
                System.out.println("Error: Número de orden inválido.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Debe ingresar un número válido.");
            return;
        }
        
        // Crear el análisis
        Analisis nuevoAnalisis = crearAnalisis();
        if (nuevoAnalisis == null) return;
        
        // Agregar análisis a la orden
        OrdenTrabajo ordenSeleccionada = listaOrdenes.obtenerOrden(numeroOrden - 1);
        ordenSeleccionada.agregarAnalisis(nuevoAnalisis);
        
        System.out.println("Análisis agregado exitosamente a la orden #" + numeroOrden);
    }

    private static Analisis crearAnalisis() {
        System.out.println("\n--- Crear Nuevo Análisis ---");
        
        // Descripción del problema
        System.out.print("Descripción del problema: ");
        String descripcion = scanner.nextLine().trim();
        
        if (descripcion.isEmpty()) {
            System.out.println("Error: La descripción no puede estar vacía.");
            return null;
        }
        
        // Diagnóstico
        System.out.println("\nDiagnósticos disponibles:");
        String[] diagnosticos = Analisis.getDiagnosticosPermitidos();
        for (int i = 0; i < diagnosticos.length; i++) {
            System.out.println((i + 1) + ". " + diagnosticos[i]);
        }
        
        System.out.print("Seleccione un diagnóstico (1-" + diagnosticos.length + "): ");
        int opcionDiagnostico;
        try {
            opcionDiagnostico = Integer.parseInt(scanner.nextLine());
            if (opcionDiagnostico < 1 || opcionDiagnostico > diagnosticos.length) {
                System.out.println("Error: Opción de diagnóstico inválida.");
                return null;
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Debe ingresar un número válido.");
            return null;
        }
        
        String diagnosticoSeleccionado = diagnosticos[opcionDiagnostico - 1];
        
        // Crear análisis
        Analisis analisis = new Analisis(descripcion, diagnosticoSeleccionado);
        
        // Configurar piezas necesarias
        configurarPiezasAnalisis(analisis);
        
        return analisis;
    }

    private static void configurarPiezasAnalisis(Analisis analisis) {
        System.out.println("\n--- Configuración de Piezas ---");
        System.out.println("¿Se necesitan piezas para esta reparación?");
        System.out.println("1. Sí, usar piezas sugeridas automáticamente");
        System.out.println("2. Sí, ingresar piezas manualmente");
        System.out.println("3. No se necesitan piezas");
        System.out.print("Seleccione una opción (1-3): ");
        
        String opcion = scanner.nextLine().trim();
        
        switch (opcion) {
            case "1":
                if (analisis.setPiezasNecesarias()) {
                    ArrayList<String> descripciones = analisis.obtenerPiezasSugeridas();
                    System.out.println("Piezas sugeridas agregadas: " + descripciones);
                } else {
                    System.out.println("Error: No se pudieron sugerir piezas.");
                }
                break;
                
            case "2":
                ingresarPiezasManualmente(analisis);
                break;
                
            case "3":
                System.out.println("No se agregarán piezas al análisis.");
                break;
                
            default:
                System.out.println("Opción no válida. No se agregarán piezas.");
                break;
        }
    }

    private static void ingresarPiezasManualmente(Analisis analisis) {
        System.out.println("\nIngreso manual de piezas (escriba 'fin' para terminar):");
        
        String sku;
        int piezasAgregadas = 0;
        
        do {
            System.out.print("SKU de la pieza: ");
            sku = scanner.nextLine().trim();
            
            if (!sku.equalsIgnoreCase("fin") && !sku.isEmpty()) {
                if (analisis.agregarPiezaNecesaria(sku)) {
                    piezasAgregadas++;
                    System.out.println("Pieza agregada: " + sku);
                } else {
                    System.out.println("Error: No se pudo agregar la pieza.");
                }
            } else if (sku.isEmpty()) {
                System.out.println("Error: El SKU no puede estar vacío.");
            }
            
        } while (!sku.equalsIgnoreCase("fin"));
        
        System.out.println("Ingreso finalizado. Total de piezas agregadas: " + piezasAgregadas);
    }

    private static void mostrarOrdenesYAnalisis() {
        System.out.println("\n=== ÓRDENES DE TRABAJO Y ANÁLISIS ===");
        
        if (!listaOrdenes.tieneOrdenes()) {
            System.out.println("No hay órdenes de trabajo registradas.");
            return;
        }
        
        listaOrdenes.listarOrdenes();
    }
}