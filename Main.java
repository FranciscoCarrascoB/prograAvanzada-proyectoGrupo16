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
            
            try {
                switch (opcion) {
                    case 1:
                        agregarOrdenTrabajo();
                        break;
                    case 2:
                        agregarAnalisisAOrden();
                        break;
                    case 3:
                        editarAnalisis();
                        break;
                    case 4:
                        eliminarAnalisis();
                        break;
                    case 5:
                        mostrarOrdenesYAnalisis();
                        break;
                    case 6:
                        System.out.println("¡Gracias por usar el sistema!");
                        // Salir
                        break;
                    default:
                        System.out.println("Opcion no valida. Intente nuevamente.");
                }
            } catch (Exception e) {
                System.out.println("Error inesperado: " + e.getMessage());
                System.out.println("Por favor, intente nuevamente.");
            }
            
            if (opcion != 6) {
                System.out.println("\nPresione Enter para continuar...");
                scanner.nextLine();
            }
            
        } while (opcion != 6);
        
        scanner.close();
    }

    private static void mostrarMenu() {
        System.out.println("\n==========================================");
        System.out.println("           MENU PRINCIPAL");
        System.out.println("==========================================");
        System.out.println("1. Agregar Orden de Trabajo");
        System.out.println("2. Agregar Analisis a una Orden");
        System.out.println("3. Editar Analisis");
        System.out.println("4. Eliminar Analisis");
        System.out.println("5. Mostrar Ordenes + Analisis");
        System.out.println("6. Salir");
        System.out.println("==========================================");
        System.out.print("Seleccione una opción (1-6): ");
    }

    // MÉTODO AUXILIAR: Leer opción del menú
    private static int leerOpcion() {
        try {
            int opcion = Integer.parseInt(scanner.nextLine().trim());
            return opcion;
        } catch (NumberFormatException e) {
            return -1; // Opción inválida
        }
    }

    // Opción 1: Agregar Orden de Trabajo
    private static void agregarOrdenTrabajo() {
        System.out.println("\n=== AGREGAR ORDEN DE TRABAJO ===");
        
        try {
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
                throw new IllegalArgumentException("El estado no puede estar vacío.");
            }
            
            // Ingresar fecha estimada
            String fechaEstimada = ingresarFecha();
            if (fechaEstimada == null) return;
            
            // Crear la orden de trabajo
            OrdenTrabajo orden = new OrdenTrabajo(cliente, encargado, estado, fechaEstimada);
            listaOrdenes.agregarOrden(orden);
            
            System.out.println("Orden de trabajo agregada exitosamente.");
            
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error inesperado al agregar orden: " + e.getMessage());
        }
    }

    // MÉTODO AUXILIAR: Crear cliente mediante input
    private static Cliente crearCliente() {
        try {
            System.out.println("\n--- Datos del Cliente ---");
            System.out.print("RUT del cliente: ");
            String rutCliente = scanner.nextLine().trim();
            
            if (rutCliente.isEmpty()) {
                throw new IllegalArgumentException("El RUT del cliente no puede estar vacío.");
            }

            System.out.print("Nombre del cliente: ");
            String nombreCliente = scanner.nextLine().trim();
            
            if (nombreCliente.isEmpty()) {
                throw new IllegalArgumentException("El nombre del cliente no puede estar vacío.");
            }
            
            return new Cliente(rutCliente, nombreCliente);
            
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    // MÉTODO AUXILIAR: Crear trabajador mediante input
    private static Trabajador crearTrabajador() {
        try {
            System.out.println("\n--- Datos del Trabajador Encargado ---");
            System.out.print("RUT del trabajador: ");
            String rutTrabajador = scanner.nextLine().trim();
            
            if (rutTrabajador.isEmpty()) {
                throw new IllegalArgumentException("El RUT del trabajador no puede estar vacío.");
            }

            System.out.print("Nombre del trabajador: ");
            String nombreTrabajador = scanner.nextLine().trim();
            
            if (nombreTrabajador.isEmpty()) {
                throw new IllegalArgumentException("El nombre del trabajador no puede estar vacío.");
            }
            
            return new Trabajador(rutTrabajador, nombreTrabajador);
            
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    // MÉTODO AUXILIAR: Ingresar fecha estimada
    private static String ingresarFecha() {
        try {
            System.out.print("Fecha estimada (x dias/semanas/meses): ");
            String fechaInput = scanner.nextLine().trim();
            
            if (fechaInput.isEmpty()) {
                throw new IllegalArgumentException("La fecha no puede estar vacía.");
            }
            
            return fechaInput;
            
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    // Opción 2: Agregar Análisis a una Orden
    private static void agregarAnalisisAOrden() {
        System.out.println("\n=== AGREGAR ANÁLISIS A ORDEN ===");
        
        try {
            // Verificar si hay órdenes
            if (!listaOrdenes.tieneOrdenes()) {
                throw new IllegalStateException("No hay órdenes de trabajo disponibles. Primero debe crear una orden de trabajo.");
            }
            
            // Mostrar órdenes disponibles
            listaOrdenes.listarOrdenes();
            
            // Seleccionar orden
            OrdenTrabajo ordenSeleccionada = seleccionarOrden();
            if (ordenSeleccionada == null) return;
            
            // Crear el análisis
            Analisis nuevoAnalisis = crearAnalisis();
            if (nuevoAnalisis == null) return;
            
            // Agregar análisis a la orden
            ordenSeleccionada.agregarAnalisis(nuevoAnalisis);
            
            System.out.println("Análisis agregado exitosamente.");
            
        } catch (IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error inesperado al agregar análisis: " + e.getMessage());
        }
    }

    // Opción 3: Editar Análisis
    private static void editarAnalisis() {
        System.out.println("\n=== EDITAR ANÁLISIS ===");
        
        try {
            // Verificar si hay órdenes
            if (!listaOrdenes.tieneOrdenes()) {
                throw new IllegalStateException("No hay órdenes de trabajo disponibles.");
            }
            
            // Mostrar órdenes disponibles
            listaOrdenes.listarOrdenes();
            
            // Seleccionar orden
            OrdenTrabajo ordenSeleccionada = seleccionarOrden();
            if (ordenSeleccionada == null) return;
            
            // Verificar si la orden tiene análisis
            if (ordenSeleccionada.getListaAnalisis().isEmpty()) {
                throw new IllegalStateException("La orden seleccionada no tiene análisis para editar.");
            }
            
            // Mostrar análisis disponibles
            System.out.println("\n--- Análisis disponibles ---");
            ArrayList<Analisis> listaAnalisis = ordenSeleccionada.getListaAnalisis();
            for (int i = 0; i < listaAnalisis.size(); i++) {
                System.out.println((i + 1) + ". " + listaAnalisis.get(i).getDescripcionProblema() + 
                                 " - Diagnóstico: " + listaAnalisis.get(i).getDiagnostico());
            }
            
            // Seleccionar análisis a editar
            Analisis analisisSeleccionado = seleccionarAnalisis(listaAnalisis);
            if (analisisSeleccionado == null) return;
            
            // Editar el análisis
            editarDatosAnalisis(analisisSeleccionado);
            
            System.out.println("Análisis editado exitosamente.");
            
        } catch (IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Error: Índice de análisis inválido.");
        } catch (Exception e) {
            System.out.println("Error inesperado al editar análisis: " + e.getMessage());
        }
    }

    // Opción 4: Eliminar Análisis
    private static void eliminarAnalisis() {
        System.out.println("\n=== ELIMINAR ANÁLISIS ===");
        
        try {
            // Verificar si hay órdenes
            if (!listaOrdenes.tieneOrdenes()) {
                throw new IllegalStateException("No hay órdenes de trabajo disponibles.");
            }
            
            // Mostrar órdenes disponibles
            listaOrdenes.listarOrdenes();
            
            // Seleccionar orden
            OrdenTrabajo ordenSeleccionada = seleccionarOrden();
            if (ordenSeleccionada == null) return;
            
            // Verificar si la orden tiene análisis
            if (ordenSeleccionada.getListaAnalisis().isEmpty()) {
                throw new IllegalStateException("La orden seleccionada no tiene análisis para eliminar.");
            }
            
            // Mostrar análisis disponibles
            System.out.println("\n--- Análisis disponibles ---");
            ArrayList<Analisis> listaAnalisis = ordenSeleccionada.getListaAnalisis();
            for (int i = 0; i < listaAnalisis.size(); i++) {
                System.out.println((i + 1) + ". " + listaAnalisis.get(i).getDescripcionProblema() + 
                                 " - Diagnóstico: " + listaAnalisis.get(i).getDiagnostico());
            }
            
            // Seleccionar análisis a eliminar
            int indiceAnalisis = seleccionarIndiceAnalisis(listaAnalisis.size());
            if (indiceAnalisis == -1) return;
            
            // Confirmar eliminación
            System.out.print("¿Está seguro que desea eliminar este análisis? (s/n): ");
            String confirmacion = scanner.nextLine().trim().toLowerCase();
            
            if (!confirmacion.equals("s") && !confirmacion.equals("si")) {
                System.out.println("Eliminación cancelada.");
                return;
            }
            
            // Eliminar el análisis
            Analisis analisisEliminado = listaAnalisis.remove(indiceAnalisis);
            
            System.out.println("Análisis eliminado exitosamente: " + analisisEliminado.getDescripcionProblema());
            
        } catch (IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Error: Índice de análisis inválido.");
        } catch (Exception e) {
            System.out.println("Error inesperado al eliminar análisis: " + e.getMessage());
        }
    }

    // MÉTODO AUXILIAR: Seleccionar orden
    private static OrdenTrabajo seleccionarOrden() {
        try {
            System.out.print("\nIngrese el número de la orden (1-" + listaOrdenes.getCantidadOrdenes() + "): ");
            int numeroOrden = Integer.parseInt(scanner.nextLine().trim());
            
            if (numeroOrden < 1 || numeroOrden > listaOrdenes.getCantidadOrdenes()) {
                throw new IndexOutOfBoundsException("Número de orden fuera de rango.");
            }
            
            return listaOrdenes.obtenerOrden(numeroOrden - 1);
            
        } catch (NumberFormatException e) {
            System.out.println("Error: Debe ingresar un número válido.");
            return null;
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    // MÉTODO AUXILIAR: Seleccionar análisis
    private static Analisis seleccionarAnalisis(ArrayList<Analisis> listaAnalisis) {
        try {
            int indice = seleccionarIndiceAnalisis(listaAnalisis.size());
            if (indice == -1) return null;
            
            return listaAnalisis.get(indice);
            
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Error: Índice de análisis inválido.");
            return null;
        }
    }

    // MÉTODO AUXILIAR: Seleccionar índice de análisis
    private static int seleccionarIndiceAnalisis(int totalAnalisis) {
        try {
            System.out.print("\nIngrese el número del análisis (1-" + totalAnalisis + "): ");
            int numeroAnalisis = Integer.parseInt(scanner.nextLine().trim());
            
            if (numeroAnalisis < 1 || numeroAnalisis > totalAnalisis) {
                throw new IndexOutOfBoundsException("Número de análisis fuera de rango.");
            }
            
            return numeroAnalisis - 1; // Convertir a índice base 0
            
        } catch (NumberFormatException e) {
            System.out.println("Error: Debe ingresar un número válido.");
            return -1;
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Error: " + e.getMessage());
            return -1;
        }
    }

    // MÉTODO AUXILIAR: Editar datos del análisis
    private static void editarDatosAnalisis(Analisis analisis) {
        try {
            System.out.println("\n--- Editando Análisis ---");
            System.out.println("Valores actuales:");
            System.out.println("Descripción: " + analisis.getDescripcionProblema());
            System.out.println("Diagnóstico: " + analisis.getDiagnostico());
            
            // Editar descripción
            System.out.print("\nNueva descripción (Enter para mantener actual): ");
            String nuevaDescripcion = scanner.nextLine().trim();
            if (!nuevaDescripcion.isEmpty()) {
                analisis.setDescripcionProblema(nuevaDescripcion);
            }
            
            // Editar diagnóstico
            System.out.println("\n¿Desea cambiar el diagnóstico? (s/n): ");
            String cambiarDiagnostico = scanner.nextLine().trim().toLowerCase();
            
            if (cambiarDiagnostico.equals("s") || cambiarDiagnostico.equals("si")) {
                String nuevoDiagnostico = seleccionarDiagnostico();
                if (nuevoDiagnostico != null) {
                    analisis.setDiagnostico(nuevoDiagnostico);
                }
            }
            
            // Editar piezas necesarias
            System.out.println("\n¿Desea modificar las piezas necesarias? (s/n): ");
            String modificarPiezas = scanner.nextLine().trim().toLowerCase();
            
            if (modificarPiezas.equals("s") || modificarPiezas.equals("si")) {
                // Limpiar piezas actuales y configurar nuevas
                analisis.limpiarPiezasNecesarias(); // Asumiendo que existe este método
                configurarPiezasAnalisis(analisis);
            }
            
        } catch (Exception e) {
            System.out.println("Error al editar análisis: " + e.getMessage());
        }
    }

    // MÉTODO AUXILIAR: Seleccionar diagnóstico
    private static String seleccionarDiagnostico() {
        try {
            System.out.println("\nDiagnósticos disponibles:");
            String[] diagnosticos = Analisis.getDiagnosticosPermitidos();
            for (int i = 0; i < diagnosticos.length; i++) {
                System.out.println((i + 1) + ". " + diagnosticos[i]);
            }
            
            System.out.print("Seleccione un diagnóstico (1-" + diagnosticos.length + "): ");
            int opcionDiagnostico = Integer.parseInt(scanner.nextLine().trim());
            
            if (opcionDiagnostico < 1 || opcionDiagnostico > diagnosticos.length) {
                throw new IndexOutOfBoundsException("Opción de diagnóstico fuera de rango.");
            }
            
            return diagnosticos[opcionDiagnostico - 1];
            
        } catch (NumberFormatException e) {
            System.out.println("Error: Debe ingresar un número válido.");
            return null;
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    // MÉTODO AUXILIAR: Crear análisis mediante input
    private static Analisis crearAnalisis() {
        try {
            System.out.println("\n--- Crear Nuevo Análisis ---");
            
            // Descripción del problema
            System.out.print("Descripción del problema: ");
            String descripcion = scanner.nextLine().trim();
            
            if (descripcion.isEmpty()) {
                throw new IllegalArgumentException("La descripción no puede estar vacía.");
            }
            
            // Diagnóstico
            String diagnosticoSeleccionado = seleccionarDiagnostico();
            if (diagnosticoSeleccionado == null) return null;
            
            // Crear análisis
            Analisis analisis = new Analisis(descripcion, diagnosticoSeleccionado);
            
            // Configurar piezas necesarias
            configurarPiezasAnalisis(analisis);
            
            return analisis;
            
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        } catch (Exception e) {
            System.out.println("Error inesperado al crear análisis: " + e.getMessage());
            return null;
        }
    }

    // MÉTODO AUXILIAR: Mini-menú de configuración de piezas necesarias para un análisis
    private static void configurarPiezasAnalisis(Analisis analisis) {
        try {
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
        } catch (Exception e) {
            System.out.println("Error al configurar piezas: " + e.getMessage());
        }
    }

    // MÉTODO AUXILIAR: Ingreso manual de piezas necesarias
    private static void ingresarPiezasManualmente(Analisis analisis) {
        try {
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
            
        } catch (Exception e) {
            System.out.println("Error al ingresar piezas manualmente: " + e.getMessage());
        }
    }

    // Opción 5: Mostrar Órdenes y Análisis
    private static void mostrarOrdenesYAnalisis() {
        try {
            System.out.println("\n=== ÓRDENES DE TRABAJO Y ANÁLISIS ===");
            
            if (!listaOrdenes.tieneOrdenes()) {
                throw new IllegalStateException("No hay órdenes de trabajo registradas.");
            }
            
            listaOrdenes.listarOrdenes();
            
        } catch (IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error inesperado al mostrar órdenes: " + e.getMessage());
        }
    }
}