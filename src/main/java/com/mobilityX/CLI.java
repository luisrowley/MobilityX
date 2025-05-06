package com.mobilityX;

import com.mobilityX.models.user.Usuario;
import com.mobilityX.models.user.UsuarioPremium;
import com.mobilityX.models.vehicle.Base;
import com.mobilityX.models.vehicle.Moto;
import com.mobilityX.models.worker.Administrador;
import com.mobilityX.models.worker.Trabajador;
import com.mobilityX.services.*;

import java.util.List;
import java.util.Scanner;

/**
 * Interfaz de línea de comandos muy sencilla para mostrar las principales funciones
 * exigidas en el Nivel 3.  No pretende ser robusta; sólo facilita la prueba manual.
 */
public class CLI {
    
    /**
     * Punto de entrada principal de la aplicación
     * @param args argumentos de línea de comandos (no utilizados)
     */
    public static void main(String[] args) {
        CLI cli = new CLI();
        cli.loop();
    }

    private final AlquilerService alquilerService;
    private final AdminService adminService;
    private final MantenimientoService mantenimientoService;
    private final StatsService statsService;
    private final Scanner scanner;

    public CLI() {
        // Crear servicios con datos mínimos de ejemplo
        this.alquilerService = new AlquilerService();
        this.adminService = new AdminService(new Administrador("Admin", "admin@mobilityx.com", "000"));
        this.mantenimientoService = new MantenimientoService(List.of());
        this.statsService = new StatsService();
        this.scanner = new Scanner(System.in);

        // Datos de arranque: una base vacía
        alquilerService.agregarBase(new Base("B001", 0, 0, 10));
    }

    public void loop() {
        boolean salir = false;
        while (!salir) {
            mostrarMenuPrincipal();
            String opcion = scanner.nextLine();
            switch (opcion) {
                case "1":
                    menuConsultasCercanas();
                    break;
                case "2":
                    menuListados();
                    break;
                case "0":
                    salir = true;
                    break;
                default:
                    System.out.println("Opción no válida");
                    break;
            }
        }
        System.out.println("Hasta pronto");
    }

    private void mostrarMenuPrincipal() {
        System.out.println("\n==== MENU PRINCIPAL ====");
        System.out.println("1. Consultas cercanas");
        System.out.println("2. Listados / estadísticas");
        System.out.println("0. Salir");
        System.out.print("Selecciona opción: ");
    }

    private void menuConsultasCercanas() {
        System.out.print("Introduce tu posición X Y (ej: 2 3): ");
        int x = scanner.nextInt();
        int y = scanner.nextInt();
        scanner.nextLine(); // consumir salto
        System.out.print("Cuántos resultados mostrar?: ");
        int n = scanner.nextInt();
        scanner.nextLine();

        List<Base> cercanas = alquilerService.basesMasCercanas(x, y, n);
        System.out.println("Bases más cercanas:");
        cercanas.forEach(b -> System.out.printf("%s (%d,%d) distancia=%.2f demanda=%d\n",
                b.getId(), b.getX(), b.getY(), Math.hypot(b.getX()-x, b.getY()-y), b.getDemanda()));

        List<Moto> motos = alquilerService.motosMasCercanas(x, y, n);
        System.out.println("Motos individuales más cercanas:");
        motos.forEach(m -> System.out.printf("%s (%d,%d) batería=%d%% distancia=%.2f\n",
                m.getId(), m.getX(), m.getY(), m.getBateria(), Math.hypot(m.getX()-x, m.getY()-y)));
    }

    private void menuListados() {
        System.out.println("\n--- LISTADOS DISPONIBLES ---");
        System.out.println("1. Bases por demanda");
        System.out.println("2. Trabajadores por intervenciones");
        System.out.println("3. Vehículos por uso (por tipo)");
        System.out.println("4. Usuarios por gasto total");
        System.out.print("Opción: ");
        String op = scanner.nextLine();
        switch (op) {
            case "1":
                listarBasesPorDemanda();
                break;
            case "2":
                listarTrabajadoresPorInterv();
                break;
            case "3":
                listarVehiculosPorUso();
                break;
            case "4":
                listarUsuariosPorGasto();
                break;
            default:
                System.out.println("Opción no válida");
                break;
        }
    }

    private void listarBasesPorDemanda() {
        List<Base> ordenadas = statsService.basesPorDemanda(alquilerService.getBases());
        System.out.println("Bases ordenadas por demanda:");
        ordenadas.forEach(b -> System.out.printf("%s demanda=%d\n", b.getId(), b.getDemanda()));
    }

    private void listarTrabajadoresPorInterv() {
        List<Trabajador> ord = statsService.trabajadoresPorIntervenciones(adminService.getTrabajadores());
        System.out.println("Trabajadores ordenados por intervenciones:");
        ord.forEach(t -> System.out.printf("%s (%s) intervenciones=%d\n", t.getNombre(), t.getPuesto(), t.getIntervenciones()));
    }

    private void listarVehiculosPorUso() {
        System.out.print("Introduce tipo (Bicicleta/Patinete/MotoPequena/MotoGrande): ");
        String tipo = scanner.nextLine();
        List<Base> bases = alquilerService.getBases();
        List<com.mobilityX.models.vehicle.Vehiculo> lista = statsService.vehiculosPorUso(bases, tipo);
        lista.forEach(v -> System.out.printf("%s uso=%d min\n", v.getId(), v.getTiempoUsoMinutosTotal()));
    }

    private void listarUsuariosPorGasto() {
        List<Usuario> usuarios = statsService.usuariosPorGasto(adminService.getUsuarios());
        usuarios.forEach(u -> System.out.printf("%s gasto=%.2f€\n", u.getNombre(), u.getGastoTotal()));
    }
}
