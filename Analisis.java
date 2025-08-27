import java.util.ArrayList;

public class Analisis {
    private String descripcionProblema; // Descripción general en palabras del problema
    private String diagnostico; // Tipo de problema (Más especifico) (Limitado a una cantidad de problemas por el setter)
    private ArrayList<String> piezasNecesarias; // Lista de String que representan los SKU de las piezas necesarias

    // **Idea: Sobrecarga de método setPiezasNecesarias. Si no se proveen argumentos, utilizar this.diagnostico para 
    // poblar la lista de piezas necesarias.
    
    // Idea: Si nos sobra tiempo, implementar una orden de armado de PC 
    // Plantillas de piezasNecesarias que representan las gamas prehechas (baja, media, alta)
}