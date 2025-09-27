// SIA 2.9 - Excepción personalizada para lista de análisis vacía
public class ListaAnalisisVaciaException extends Exception {
    public ListaAnalisisVaciaException(String mensaje) {
        super(mensaje);
    }
}