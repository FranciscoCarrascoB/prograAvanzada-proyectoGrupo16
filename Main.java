import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Main {
    private static ListaOrdenes listaOrdenes = new ListaOrdenes();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        // Datos iniciales
        // SIA 2.2 - Carga inicial de datos desde el archivo CSV
        listaOrdenes = cargarDatos();

        System.out.println("=== SISTEMA DE ORDENES DE TRABAJO ===");
        System.out.println("Datos cargados exitosamente. " + listaOrdenes.getCantidadOrdenes() + " orden(es) en el sistema.");
        
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
                        modificarOrdenTrabajo();
                        break;
                    case 3:
                        eliminarOrdenTrabajo();
                        break;
                    case 4:
                        agregarAnalisisAOrden();
                        break;
                    case 5:
                        editarAnalisis();
                        break;
                    case 6:
                        eliminarAnalisis();
                        break;
                    case 7:
                        mostrarOrdenesYAnalisis();
                        break;
                    case 8:
                        buscarOrdenPorIndice();
                        break;
                    case 9:
                        buscarOrdenesPorEstado();
                        break;
                    case 10:
                        generarReporteTXT();
                        break;
                    case 11:
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
            
            if (opcion != 11) {
                System.out.println("\nPresione Enter para continuar...");
                scanner.nextLine();
            }
            
        } while (opcion != 11);

        // SIA 2.2 - Guardado de datos al salir de la aplicación
        guardarDatos(listaOrdenes);
        System.out.println("Datos guardados exitosamente en ordenes.csv.");
        
        scanner.close();
    }

    private static void mostrarMenu() {
        System.out.println("\n==========================================");
        System.out.println("           MENU PRINCIPAL");
        System.out.println("==========================================");
        System.out.println("--- Gestión de Ordenes ---");
        System.out.println("1. Agregar Orden de Trabajo");
        System.out.println("2. Modificar Orden de Trabajo");
        System.out.println("3. Eliminar Orden de Trabajo");
        System.out.println("--- Gestión de Análisis ---");
        System.out.println("4. Agregar Analisis a una Orden");
        System.out.println("5. Editar Analisis");
        System.out.println("6. Eliminar Analisis");
        System.out.println("--- Sistema ---");
        System.out.println("7. Mostrar Ordenes + Analisis");
        System.out.println("--- Búsqueda ---"); // SIA 2.13
        System.out.println("8. Buscar Orden por Índice");
        System.out.println("--- Funcionalidades Especiales ---"); // SIA 2.5
        System.out.println("9. Buscar Ordenes por Estado");
        System.out.println("10. Generar Reporte .txt"); // SIA 2.10
        System.out.println("\n11. Salir");
        System.out.println("==========================================");
        System.out.print("Seleccione una opcion (1-11): ");
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

    // SIA 2.2 - Carga de datos desde un archivo CSV al iniciar la aplicación
    private static ListaOrdenes cargarDatos() {
        String nombreArchivo = "ordenes.csv";
        ListaOrdenes nuevasOrdenes = new ListaOrdenes();
        // Usamos un HashMap para agrupar los análisis en la orden correcta usando el orden_id
        Map<String, OrdenTrabajo> ordenesMap = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            reader.readLine(); // Omitir la línea de la cabecera

            while ((linea = reader.readLine()) != null) {
                String[] campos = linea.split(",");
                if (campos.length < 10) continue; // Ignorar líneas malformadas

                String ordenId = campos[0].trim(); // Eliminar espacios en blanco
                
                OrdenTrabajo ordenActual;

                // Si es la primera vez que vemos este ID, creamos la Orden de Trabajo
                if (!ordenesMap.containsKey(ordenId)) {
                    Cliente cliente = new Cliente(campos[1].trim(), campos[2].trim());
                    Trabajador trabajador = new Trabajador(campos[3].trim(), campos[4].trim());
                    String fechaEstimada = campos[6].trim();
                    
                    ordenActual = new OrdenTrabajo(cliente, trabajador, campos[5].trim(), fechaEstimada);
                    ordenesMap.put(ordenId, ordenActual);
                } else {
                    // Si el ID ya existe, obtenemos la orden existente para agregarle otro análisis
                    ordenActual = ordenesMap.get(ordenId);
                }

                // Creamos y agregamos el análisis a la orden (si existe)
                String descripcion = campos[7].trim();
                if (!descripcion.isEmpty()) {
                    Analisis analisis = new Analisis(descripcion, campos[8].trim());
                    
                    // Procesar la lista de piezas
                    String piezasString = campos[9].trim();
                    if (!piezasString.isEmpty()) {
                        String[] piezasSku = piezasString.split("\\|");
                        ArrayList<String> piezasList = new ArrayList<>();
                        for (String sku : piezasSku) {
                            if (!sku.trim().isEmpty()) {
                                piezasList.add(sku.trim());
                            }
                        }
                        analisis.setPiezasNecesarias(piezasList);
                    } else {
                        // Si no hay SKUs, usar piezas por defecto
                        analisis.setPiezasNecesarias();
                    }
                    
                    ordenActual.agregarAnalisis(analisis);
                }
            }

            // Una vez procesado todo el archivo, agregamos las órdenes del mapa a la lista final
            for (OrdenTrabajo orden : ordenesMap.values()) {
                nuevasOrdenes.agregarOrden(orden);
            }

        } catch (FileNotFoundException e) {
            System.out.println("Archivo 'ordenes.csv' no encontrado. Se iniciará con una lista vacía.");
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de datos: " + e.getMessage());
            e.printStackTrace(); // Para debug
        }

        System.out.println("Órdenes cargadas: " + nuevasOrdenes.getOrdenes().size());
        for (OrdenTrabajo orden : nuevasOrdenes.getOrdenes()) {
            try {
                System.out.println("Orden cliente: " + orden.getCliente().getNombre() + 
                                ", análisis: " + orden.getListaAnalisis().size());
            } catch (ListaAnalisisVaciaException e) {
                System.out.println("Orden cliente: " + orden.getCliente().getNombre() + 
                                ", sin análisis");
            }
        }
        
        return nuevasOrdenes;
    }

    // SIA 2.2 - Guardado de datos a un archivo CSV al salir de la aplicación
    private static void guardarDatos(ListaOrdenes ordenes) {
        String nombreArchivo = "ordenes.csv";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo))) {
            // Escribir la cabecera del CSV
            writer.write("orden_id,cliente_rut,cliente_nombre,trabajador_rut,trabajador_nombre,estado,fecha_estimada,analisis_descripcion,analisis_diagnostico,analisis_piezas_sku");
            writer.newLine();

            int ordenIdCounter = 0;
            for (OrdenTrabajo orden : ordenes.getOrdenes()) {
                ordenIdCounter++; // Asignamos un ID interno secuencial a cada orden
                
                // Si una orden no tiene análisis, igual la guardamos con campos de análisis vacíos
                try {
                    // Intentar obtener la lista de análisis
                    ArrayList<Analisis> listaAnalisis = orden.getListaAnalisis();
                    for (Analisis analisis : listaAnalisis) {
                        escribirLineaCSV(writer, ordenIdCounter, orden, analisis);
                    }
                } catch (ListaAnalisisVaciaException e) {
                    // Si no hay análisis, guardar con campos de análisis vacíos
                    escribirLineaCSV(writer, ordenIdCounter, orden, null);
                }
            }
            
            writer.flush(); // Forzar escritura al disco
            
        } catch (IOException e) {
            System.out.println("Error al guardar los datos en el archivo: " + e.getMessage());
            e.printStackTrace(); // Para debug
        }
    }

    // MÉTODO AUXILIAR: Escribir una línea del CSV
    private static void escribirLineaCSV(BufferedWriter writer, int ordenId, OrdenTrabajo orden, Analisis analisis) throws IOException {
        StringBuilder linea = new StringBuilder();
        
        // Agregar datos básicos de la orden
        linea.append(ordenId).append(",");
        linea.append(escaparCampoCSV(orden.getCliente().getRut())).append(",");
        linea.append(escaparCampoCSV(orden.getCliente().getNombre())).append(",");
        linea.append(escaparCampoCSV(orden.getEncargado().getRut())).append(",");
        linea.append(escaparCampoCSV(orden.getEncargado().getNombre())).append(",");
        linea.append(escaparCampoCSV(orden.getEstado())).append(",");
        
        // Fecha estimada en formato día-mes-año
        linea.append(escaparCampoCSV(orden.getFechaEstimada())).append(",");
        
        if (analisis != null) {
            // Agregar datos del análisis
            linea.append(escaparCampoCSV(analisis.getDescripcionProblema())).append(",");
            linea.append(escaparCampoCSV(analisis.getDiagnostico())).append(",");
            
            // Manejar las piezas necesarias
            List<String> piezas = analisis.getPiezasNecesarias();
            if (piezas != null && !piezas.isEmpty()) {
                String piezasSku = String.join("|", piezas);
                linea.append(escaparCampoCSV(piezasSku));
            } else {
                linea.append(""); // Campo vacío
            }
        } else {
            // Campos de análisis vacíos
            linea.append(",").append(",").append("");
        }
        
        writer.write(linea.toString());
        writer.newLine();
    }

    // MÉTODO AUXILIAR: Escapar campos CSV que contengan comas, comillas o saltos de línea
    private static String escaparCampoCSV(String campo) {
        if (campo == null) {
            return "";
        }
        
        // Si el campo contiene comas, comillas o saltos de línea, necesita ser escapado
        if (campo.contains(",") || campo.contains("\"") || campo.contains("\n")) {
            // Escapar comillas duplicándolas
            String campoEscapado = campo.replace("\"", "\"\"");
            return "\"" + campoEscapado + "\"";
        }
        
        return campo;
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
                throw new IllegalArgumentException("El estado no puede estar vacio.");
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
                throw new IllegalArgumentException("El RUT del cliente no puede estar vacio.");
            }

            System.out.print("Nombre del cliente: ");
            String nombreCliente = scanner.nextLine().trim();
            
            if (nombreCliente.isEmpty()) {
                throw new IllegalArgumentException("El nombre del cliente no puede estar vacio.");
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
                throw new IllegalArgumentException("El RUT del trabajador no puede estar vacio.");
            }

            System.out.print("Nombre del trabajador: ");
            String nombreTrabajador = scanner.nextLine().trim();
            
            if (nombreTrabajador.isEmpty()) {
                throw new IllegalArgumentException("El nombre del trabajador no puede estar vacio.");
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
                throw new IllegalArgumentException("La fecha no puede estar vacia.");
            }
            
            return fechaInput;
            
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    // MÉTODO AUXILIAR: Seleccionar índice de orden
    private static int seleccionarIndiceOrden() {
        try {
            System.out.print("\nIngrese el numero de la orden (1-" + listaOrdenes.getCantidadOrdenes() + "): ");
            int numeroOrden = Integer.parseInt(scanner.nextLine().trim());

            if (numeroOrden < 1 || numeroOrden > listaOrdenes.getCantidadOrdenes()) {
                throw new IndexOutOfBoundsException("Numero de orden fuera de rango.");
            }
            
            return numeroOrden - 1;

        } catch (NumberFormatException e) {
            System.out.println("Error: Debe ingresar un numero valido.");
            return -1;
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Error: " + e.getMessage());
            return -1;
        }
    }

    // Opción 2: Modificar Orden de Trabajo
    private static void modificarOrdenTrabajo() {
        System.out.println("\n=== MODIFICAR ORDEN DE TRABAJO ===");
        
        try {
            if (!listaOrdenes.tieneOrdenes()) {
                throw new IllegalStateException("No hay ordenes de trabajo para modificar.");
            }
            
            listaOrdenes.listarOrdenes();
            OrdenTrabajo orden = seleccionarOrden();
            if (orden == null) return;

            System.out.println("\n--- Editando Orden de Trabajo ---");
            System.out.println("Deje el campo en blanco y presione Enter para mantener el valor actual.");

            System.out.print("Nuevo estado (" + orden.getEstado() + "): ");
            String nuevoEstado = scanner.nextLine().trim();
            if (!nuevoEstado.isEmpty()) {
                orden.setEstado(nuevoEstado);
            }

            System.out.print("Nueva fecha estimada (" + orden.getFechaEstimada() + "): ");
            String nuevaFecha = scanner.nextLine().trim();
            if (!nuevaFecha.isEmpty()) {
                orden.setFechaEstimada(nuevaFecha);
            }

            System.out.print("\n¿Desea cambiar el cliente actual (" + orden.getCliente().getNombre() + ")? (s/n): ");
            if (scanner.nextLine().trim().equalsIgnoreCase("s")) {
                Cliente nuevoCliente = crearCliente();
                if (nuevoCliente != null) {
                    orden.setCliente(nuevoCliente);
                }
            }

            System.out.print("\n¿Desea cambiar el trabajador encargado (" + orden.getEncargado().getNombre() + ")? (s/n): ");
            if (scanner.nextLine().trim().equalsIgnoreCase("s")) {
                Trabajador nuevoEncargado = crearTrabajador();
                if (nuevoEncargado != null) {
                    orden.setEncargado(nuevoEncargado);
                }
            }

            System.out.println("\nOrden de trabajo modificada exitosamente.");

        } catch (IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error inesperado al modificar la orden: " + e.getMessage());
        }
    }

    // Opción 3: Eliminar Orden de Trabajo
     private static void eliminarOrdenTrabajo() {
        System.out.println("\n=== ELIMINAR ORDEN DE TRABAJO ===");
        
        try {
            if (!listaOrdenes.tieneOrdenes()) {
                throw new IllegalStateException("No hay ordenes de trabajo para eliminar.");
            }

            listaOrdenes.listarOrdenes();
            int indice = seleccionarIndiceOrden();
            if (indice == -1) return; // El usuario canceló o hubo un error

            System.out.print("¿Esta seguro que desea eliminar esta orden de trabajo? Esta accion no se puede deshacer. (s/n): ");
            String confirmacion = scanner.nextLine().trim().toLowerCase();

            if (confirmacion.equals("s") || confirmacion.equals("si")) {
                OrdenTrabajo eliminada = listaOrdenes.eliminarOrden(indice);
                System.out.println("Orden de trabajo para el cliente '" + eliminada.getCliente().getNombre() + "' eliminada exitosamente.");
            } else {
                System.out.println("Eliminacion cancelada.");
            }

        } catch (IllegalStateException | IndexOutOfBoundsException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error inesperado al eliminar la orden: " + e.getMessage());
        }
    }

    // Opción 4: Agregar Análisis a una Orden
    private static void agregarAnalisisAOrden() {
        System.out.println("\n=== AGREGAR ANALISIS A ORDEN ===");
        
        try {
            // Verificar si hay órdenes
            if (!listaOrdenes.tieneOrdenes()) {
                throw new IllegalStateException("No hay ordenes de trabajo disponibles. Primero debe crear una orden de trabajo.");
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
            
            System.out.println("Analisis agregado exitosamente.");
            
        } catch (IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error inesperado al agregar analisis: " + e.getMessage());
        }
    }

    // Opción 5: Editar Análisis
    private static void editarAnalisis() {
        System.out.println("\n=== EDITAR ANALISIS ===");
        
        try {
            // Verificar si hay órdenes
            if (!listaOrdenes.tieneOrdenes()) {
                throw new IllegalStateException("No hay ordenes de trabajo disponibles.");
            }
            
            // Mostrar órdenes disponibles
            listaOrdenes.listarOrdenes();
            
            // Seleccionar orden
            OrdenTrabajo ordenSeleccionada = seleccionarOrden();
            if (ordenSeleccionada == null) return;
            
            // Mostrar análisis disponibles
            System.out.println("\n--- Analisis disponibles ---");
            // Si la lista de análisis está vacía, getListaAnalisis() lanzará una excepción personalizada
            ArrayList<Analisis> listaAnalisis = ordenSeleccionada.getListaAnalisis();
            for (int i = 0; i < listaAnalisis.size(); i++) {
                System.out.println((i + 1) + ". " + listaAnalisis.get(i).getDescripcionProblema() + 
                                 " - Diagnostico: " + listaAnalisis.get(i).getDiagnostico());
            }
            
            // Seleccionar análisis a editar
            Analisis analisisSeleccionado = seleccionarAnalisis(listaAnalisis);
            if (analisisSeleccionado == null) return;
            
            // Editar el análisis
            editarDatosAnalisis(analisisSeleccionado);
            
            System.out.println("Analisis editado exitosamente.");
            
        // Capturar excepción personalizada si la lista de análisis está vacía
        } catch (ListaAnalisisVaciaException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        // Error genérico para capturar cualquier otro error inesperado
        } catch (Exception e) {
            System.out.println("Error inesperado al editar analisis: " + e.getMessage());
        }
    }

    // Opción 6: Eliminar Análisis
    private static void eliminarAnalisis() {
        System.out.println("\n=== ELIMINAR ANALISIS ===");
        
        try {
            // Verificar si hay órdenes
            if (!listaOrdenes.tieneOrdenes()) {
                throw new IllegalStateException("No hay ordenes de trabajo disponibles.");
            }
            
            // Mostrar órdenes disponibles
            listaOrdenes.listarOrdenes();
            
            // Seleccionar orden
            OrdenTrabajo ordenSeleccionada = seleccionarOrden();
            if (ordenSeleccionada == null) return;
            
            // Verificar si la orden tiene análisis
            if (ordenSeleccionada.getListaAnalisis().isEmpty()) {
                throw new IllegalStateException("La orden seleccionada no tiene analisis para eliminar.");
            }
            
            // Mostrar análisis disponibles
            System.out.println("\n--- Analisis disponibles ---");
            ArrayList<Analisis> listaAnalisis = ordenSeleccionada.getListaAnalisis();
            for (int i = 0; i < listaAnalisis.size(); i++) {
                System.out.println((i + 1) + ". " + listaAnalisis.get(i).getDescripcionProblema() + 
                                 " - Diagnostico: " + listaAnalisis.get(i).getDiagnostico());
            }
            
            // Seleccionar análisis a eliminar
            int indiceAnalisis = seleccionarIndiceAnalisis(listaAnalisis.size());
            if (indiceAnalisis == -1) return;
            
            // Confirmar eliminación
            System.out.print("¿Esta seguro que desea eliminar este analisis? (s/n): ");
            String confirmacion = scanner.nextLine().trim().toLowerCase();
            
            if (!confirmacion.equals("s") && !confirmacion.equals("si")) {
                System.out.println("Eliminacion cancelada.");
                return;
            }
            
            // Eliminar el análisis
            Analisis analisisEliminado = ordenSeleccionada.eliminarAnalisis(indiceAnalisis);
            if (analisisEliminado == null) {
                throw new IllegalStateException("No se pudo eliminar el analisis. Indice invalido.");
            }
            
            System.out.println("Analisis eliminado exitosamente: " + analisisEliminado.getDescripcionProblema());
            
        } catch (IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Error: Indice de analisis invalido.");
        } catch (Exception e) {
            System.out.println("Error inesperado al eliminar analisis: " + e.getMessage());
        }
    }

    // MÉTODO AUXILIAR: Seleccionar orden
    private static OrdenTrabajo seleccionarOrden() {
        try {
            System.out.print("\nIngrese el numero de la orden (1-" + listaOrdenes.getCantidadOrdenes() + "): ");
            int numeroOrden = Integer.parseInt(scanner.nextLine().trim());
            
            if (numeroOrden < 1 || numeroOrden > listaOrdenes.getCantidadOrdenes()) {
                throw new IndexOutOfBoundsException("Numero de orden fuera de rango.");
            }
            
            return listaOrdenes.obtenerOrden(numeroOrden - 1);
            
        } catch (NumberFormatException e) {
            System.out.println("Error: Debe ingresar un numero valido.");
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
            System.out.println("Error: Indice de analisis invalido.");
            return null;
        }
    }

    // MÉTODO AUXILIAR: Seleccionar índice de análisis
    private static int seleccionarIndiceAnalisis(int totalAnalisis) {
        try {
            System.out.print("\nIngrese el numero del analisis (1-" + totalAnalisis + "): ");
            int numeroAnalisis = Integer.parseInt(scanner.nextLine().trim());
            
            if (numeroAnalisis < 1 || numeroAnalisis > totalAnalisis) {
                throw new IndexOutOfBoundsException("Numero de analisis fuera de rango.");
            }
            
            return numeroAnalisis - 1; // Convertir a índice base 0
            
        } catch (NumberFormatException e) {
            System.out.println("Error: Debe ingresar un numero valido.");
            return -1;
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Error: " + e.getMessage());
            return -1;
        }
    }

    // MÉTODO AUXILIAR: Editar datos del análisis
    private static void editarDatosAnalisis(Analisis analisis) {
        try {
            System.out.println("\n--- Editando Analisis ---");
            System.out.println("Valores actuales:");
            System.out.println("Descripcion: " + analisis.getDescripcionProblema());
            System.out.println("Diagnostico: " + analisis.getDiagnostico());
            
            // Editar descripción
            System.out.print("\nNueva descripcion (Enter para mantener actual): ");
            String nuevaDescripcion = scanner.nextLine().trim();
            if (!nuevaDescripcion.isEmpty()) {
                analisis.setDescripcionProblema(nuevaDescripcion);
            }
            
            // Editar diagnóstico
            System.out.println("\n¿Desea cambiar el diagnostico? (s/n): ");
            String cambiarDiagnostico = scanner.nextLine().trim().toLowerCase();
            
            if (cambiarDiagnostico.equals("s") || cambiarDiagnostico.equals("si")) {
                String nuevoDiagnostico = seleccionarDiagnostico();
                if (nuevoDiagnostico != null) {
                    try {
                        analisis.setDiagnostico(nuevoDiagnostico);
                    } catch (DiagnosticoInvalidoException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
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
            System.out.println("Error al editar analisis: " + e.getMessage());
        }
    }

    // MÉTODO AUXILIAR: Seleccionar diagnóstico
    private static String seleccionarDiagnostico() {
        try {
            System.out.println("\nDiagnosticos disponibles:");
            String[] diagnosticos = Analisis.getDiagnosticosPermitidos();
            for (int i = 0; i < diagnosticos.length; i++) {
                System.out.println((i + 1) + ". " + diagnosticos[i]);
            }
            
            System.out.print("Seleccione un diagnostico (1-" + diagnosticos.length + "): ");
            int opcionDiagnostico = Integer.parseInt(scanner.nextLine().trim());
            
            if (opcionDiagnostico < 1 || opcionDiagnostico > diagnosticos.length) {
                throw new IndexOutOfBoundsException("Opcion de diagnostico fuera de rango.");
            }
            
            return diagnosticos[opcionDiagnostico - 1];
            
        } catch (NumberFormatException e) {
            System.out.println("Error: Debe ingresar un numero valido.");
            return null;
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    // MÉTODO AUXILIAR: Crear análisis mediante input
    private static Analisis crearAnalisis() {
        try {
            System.out.println("\n--- Crear Nuevo Analisis ---");
            
            // Descripción del problema
            System.out.print("Descripcion del problema: ");
            String descripcion = scanner.nextLine().trim();
            
            if (descripcion.isEmpty()) {
                throw new IllegalArgumentException("La descripcion no puede estar vacia.");
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
            System.out.println("Error inesperado al crear analisis: " + e.getMessage());
            return null;
        }
    }

    // MÉTODO AUXILIAR: Mini-menú de configuración de piezas necesarias para un análisis
    private static void configurarPiezasAnalisis(Analisis analisis) {
        try {
            System.out.println("\n--- Configuracion de Piezas ---");
            System.out.println("¿Se necesitan piezas para esta reparacion?");
            System.out.println("1. Si, usar piezas sugeridas automaticamente");
            System.out.println("2. Si, ingresar piezas manualmente");
            System.out.println("3. No se necesitan piezas");
            System.out.print("Seleccione una opcion (1-3): ");
            
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
                    System.out.println("No se agregaran piezas al analisis.");
                    break;
                    
                default:
                    System.out.println("Opcion no valida. No se agregaran piezas.");
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
                    System.out.println("Error: El SKU no puede estar vacio.");
                }
                
            } while (!sku.equalsIgnoreCase("fin"));
            
            System.out.println("Ingreso finalizado. Total de piezas agregadas: " + piezasAgregadas);
            
        } catch (Exception e) {
            System.out.println("Error al ingresar piezas manualmente: " + e.getMessage());
        }
    }

    // Opción 7: Mostrar Órdenes y Análisis
    private static void mostrarOrdenesYAnalisis() {
        try {
            System.out.println("\n=== ORDENES DE TRABAJO Y ANALISIS ===");
            
            if (!listaOrdenes.tieneOrdenes()) {
                throw new IllegalStateException("No hay ordenes de trabajo registradas.");
            }
            
            listaOrdenes.listarOrdenes();
            
        } catch (IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error inesperado al mostrar ordenes: " + e.getMessage());
        }
    }

    // Opción 8: Buscar Orden por Índice
    public static void buscarOrdenPorIndice() {
        System.out.println("\n=== BUSCAR ORDEN POR INDICE ===");
        
        if (listaOrdenes.getCantidadOrdenes() == 0) {
            System.out.println("No hay ordenes registradas en el sistema.");
            return;
        }
        
        System.out.print("Ingrese # de la orden a buscar (1 a " + listaOrdenes.getCantidadOrdenes() + "): ");
        int indice;
        
        try {
            indice = Integer.parseInt(scanner.nextLine().trim()) - 1; // Convertir a índice base 0
        } catch (NumberFormatException e) {
            System.out.println("Error: Debe ingresar un numero valido.");
            return;
        }

        try {
            OrdenTrabajo orden = listaOrdenes.obtenerOrden(indice);
            
            System.out.println("\n=== INFORMACION DE LA ORDEN ===");
            System.out.println("ORDEN #" + (indice + 1));
            System.out.println(orden.getCliente().obtenerInformacion());
            System.out.println(orden.getEncargado().obtenerInformacion());
            System.out.println("Estado: " + orden.getEstado());
            System.out.println("Fecha estimada: " + orden.getFechaEstimada());
            System.out.println("Cantidad de analisis: " + orden.getListaAnalisis().size());
            
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Error: El numero de orden ingresado no existe.");
        } catch (Exception e) {
            System.out.println("Error inesperado al buscar la orden: " + e.getMessage());
        }
    }

    // Opción 9: Buscar Órdenes por Estado (SIA 2.5)
    private static void buscarOrdenesPorEstado() {
        System.out.println("\n=== BUSCAR ORDENES POR ESTADO ===");
        
        try {
            // Verificar si hay órdenes
            if (!listaOrdenes.tieneOrdenes()) {
                throw new IllegalStateException("No hay ordenes de trabajo registradas para buscar.");
            }
            
            // Solicitar estado a buscar
            System.out.print("\nIngrese el estado a buscar: \n");
            System.out.print("(Los estados disponibles son ");
            System.out.println(OrdenTrabajo.getEstadosPosiblesStringDescriptivo() + ")");
            String estadoBuscado = scanner.nextLine().trim();
            
            if (estadoBuscado.isEmpty()) {
                throw new IllegalArgumentException("El estado no puede estar vacio.");
            }

            if (!OrdenTrabajo.esEstadoValido(estadoBuscado)) {
                throw new IllegalArgumentException("El estado ingresado no es valido.");
            }
            
            // Filtrar órdenes por estado
            ArrayList<OrdenTrabajo> ordenesFiltradas = filtrarOrdenesPorEstado(estadoBuscado);
            
            // Mostrar resultados
            mostrarResultadosBusquedaEstado(estadoBuscado, ordenesFiltradas);
            
        } catch (IllegalStateException | IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error inesperado al buscar ordenes por estado: " + e.getMessage());
        }
    }

    // MÉTODO AUXILIAR: Mostrar resultados de búsqueda por estado
    private static ArrayList<OrdenTrabajo> filtrarOrdenesPorEstado(String estadoBuscado) {
        ArrayList<OrdenTrabajo> ordenesFiltradas = new ArrayList<>();
        
        // Recorrer todas las órdenes y filtrar por estado (ignorar mayúsculas/minúsculas)
        for (int i = 0; i < listaOrdenes.getCantidadOrdenes(); i++) {
            OrdenTrabajo orden = listaOrdenes.obtenerOrden(i);
            
            if (orden.getEstado().equalsIgnoreCase(estadoBuscado)) {
                ordenesFiltradas.add(orden);
            }
        }
        
        return ordenesFiltradas;
    }

    // MÉTODO AUXILIAR: Mostrar resultados de búsqueda por estado
    private static void mostrarResultadosBusquedaEstado(String estadoBuscado, ArrayList<OrdenTrabajo> ordenesFiltradas) {
        System.out.println("\n--- RESULTADOS DE BUSQUEDA ---");
        System.out.println("Estado buscado: '" + estadoBuscado + "'");
        System.out.println("Ordenes encontradas: " + ordenesFiltradas.size());
        
        if (ordenesFiltradas.isEmpty()) {
            System.out.println("\nNo se encontraron ordenes con el estado especificado.");
        } else {
            System.out.println("\n==========================================");
            
            for (int i = 0; i < ordenesFiltradas.size(); i++) {
                OrdenTrabajo orden = ordenesFiltradas.get(i);
                int numeroOrdenOriginal = encontrarNumeroOrdenOriginal(orden);
                
                System.out.println("ORDEN #" + numeroOrdenOriginal);
                System.out.println("Cliente: " + orden.getCliente().getNombre() + " (" + orden.getCliente().getRut() + ")");
                System.out.println("Encargado: " + orden.getEncargado().getNombre() + " (" + orden.getEncargado().getRut() + ")");
                System.out.println("Estado: " + orden.getEstado());
                System.out.println("Fecha estimada: " + orden.getFechaEstimada());
                System.out.println("Cantidad de analisis: " + orden.getListaAnalisis().size());
                
                if (i < ordenesFiltradas.size() - 1) {
                    System.out.println("------------------------------------------");
                }
            }
            System.out.println("==========================================");
        }
    }

    // MÉTODO AUXILIAR: Encontrar el número original de la orden en la lista completa
    private static int encontrarNumeroOrdenOriginal(OrdenTrabajo ordenBuscada) {
        for (int i = 0; i < listaOrdenes.getCantidadOrdenes(); i++) {
            OrdenTrabajo orden = listaOrdenes.obtenerOrden(i);
            if (orden == ordenBuscada) {  // Comparación por referencia
                return i + 1; // Retorna número de orden (base 1)
            }
        }
        return -1; // No encontrada (no debería pasar)
    }

    // SIA 2.10 - Generar un reporte en archivo .txt
    private static void generarReporteTXT() {
        String nombreArchivo = "reporte_sistema.txt";
        System.out.println("\nGenerando reporte en " + nombreArchivo + "...");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo))) {
            // Calcular estadísticas generales
            int totalOrdenes = listaOrdenes.getCantidadOrdenes();
            int totalAnalisis = 0;
            Map<String, Integer> conteoPorEstado = new HashMap<>();

            for (OrdenTrabajo orden : listaOrdenes.getOrdenes()) {
                // Contar análisis
                try {
                    totalAnalisis += orden.getListaAnalisis().size();
                } catch (ListaAnalisisVaciaException e) {
                    // No sumar nada si no hay análisis
                }

                // Contar órdenes por estado
                String estado = orden.getEstado();
                conteoPorEstado.put(estado, conteoPorEstado.getOrDefault(estado, 0) + 1);
            }

            // Escribir encabezado y resumen
            writer.write("=========================================\n");
            writer.write("        REPORTE DEL SISTEMA\n");
            writer.write("=========================================\n");
            writer.write("Fecha del reporte: " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + "\n");
            writer.write("\n--- RESUMEN GENERAL ---\n");
            writer.write("Total de Órdenes de Trabajo: " + totalOrdenes + "\n");
            writer.write("Total de Análisis registrados: " + totalAnalisis + "\n");
            writer.write("\n--- ÓRDENES POR ESTADO ---\n");
            if (conteoPorEstado.isEmpty()) {
                writer.write("No hay órdenes registradas.\n");
            } else {
                for (Map.Entry<String, Integer> entry : conteoPorEstado.entrySet()) {
                    writer.write("- " + entry.getKey() + ": " + entry.getValue() + " orden(es)\n");
                }
            }
            writer.write("\n=========================================\n");
            writer.write("        DETALLE DE ÓRDENES\n");
            writer.write("=========================================\n\n");

            // Escribir detalle de cada orden
            int ordenIndex = 1;
            for (OrdenTrabajo orden : listaOrdenes.getOrdenes()) {
                writer.write("--- ORDEN #" + ordenIndex + " ---\n");
                writer.write(orden.getCliente().obtenerInformacion() + "\n");
                writer.write(orden.getEncargado().obtenerInformacion() + "\n");
                writer.write("Estado: " + orden.getEstado() + "\n");
                writer.write("Fecha Estimada de Entrega: " + orden.getFechaEstimada() + "\n");
                
                try {
                    ArrayList<Analisis> analisisDeLaOrden = orden.getListaAnalisis();
                    writer.write("Análisis Asociados (" + analisisDeLaOrden.size() + "):\n");
                    int analisisIndex = 1;
                    for (Analisis analisis : analisisDeLaOrden) {
                        writer.write("  " + analisisIndex + ". Descripción: " + analisis.getDescripcionProblema() + "\n");
                        writer.write("     Diagnóstico: " + analisis.getDiagnostico() + "\n");
                        writer.write("     Piezas Requeridas (SKU): " + String.join(", ", analisis.getPiezasNecesarias()) + "\n");
                        analisisIndex++;
                    }
                } catch (ListaAnalisisVaciaException e) {
                    writer.write("Análisis Asociados: Esta orden no tiene análisis registrados.\n");
                }
                writer.write("\n-----------------------------------------\n\n");
                ordenIndex++;
            }

            System.out.println("¡Reporte generado exitosamente!");

        } catch (IOException e) {
            System.out.println("Error al generar el reporte: " + e.getMessage());
            e.printStackTrace();
        }
    }
}