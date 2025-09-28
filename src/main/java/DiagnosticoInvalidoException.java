// SIA 2.9 - Excepción personalizada para diagnóstico inválido
public class DiagnosticoInvalidoException extends RuntimeException {
    public DiagnosticoInvalidoException(String mensaje) {
        super(mensaje);
    }
}