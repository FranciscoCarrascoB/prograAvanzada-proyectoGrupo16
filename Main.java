import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        String opcion;
        BufferedReader lector = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Bienvenido al sistema\n¿Qué desea hacer?\n1. Agregar elementos\n2. Mostrar lista\n3. Salir");
        opcion = lector.readLine();
        
        switch(opcion) {
            case "1":
                // Aquí va el código para agregar elementos
                break;
                
            case "2":
                // Aquí va el código para mostrar la lista
                break;
                
            case "3":
                System.out.println("Saliendo del sistema...");
                break;
                
            default:
                System.out.println("Opción no válida. Por favor seleccione 1, 2 o 3.");
                break;
        }
    }
}