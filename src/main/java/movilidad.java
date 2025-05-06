/**
 * Clase principal que inicia la aplicación MobilityX.
 * Esta clase sirve como punto de entrada al sistema y delega
 * en la clase CLI para la interfaz de usuario.
 */
public class movilidad {
    /**
     * Método principal que inicia la aplicación.
     * @param args argumentos de línea de comandos (no utilizados)
     */
    public static void main(String[] args) {
        System.out.println("Iniciando aplicación MobilityX...");
        
        // Crear y ejecutar la interfaz de línea de comandos
        com.mobilityX.CLI cli = new com.mobilityX.CLI();
        cli.loop();
    }
}
