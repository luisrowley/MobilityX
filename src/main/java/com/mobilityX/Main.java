package main.java.com.mobilityX;

import main.java.com.mobilityX.models.user.Usuario;
import main.java.com.mobilityX.models.vehicle.*;
import main.java.com.mobilityX.models.worker.*;
import main.java.com.mobilityX.services.*;

public class Main {
    public static void main(String[] args) {
        // Inicializar servicios
        System.out.println("=== Inicializando Sistema MobilityX ===");
        Administrador admin = new Administrador("Admin Principal", "admin@mobilityx.com", "+34600000000");
        AdminService adminService = new AdminService(admin);
        AlquilerService alquilerService = new AlquilerService();

        // Crear bases
        System.out.println("\n=== Creando Bases ===");
        Base base1 = new Base("B001", 1, 1, 5);
        Base base2 = new Base("B002", 5, 5, 5);
        alquilerService.agregarBase(base1);
        alquilerService.agregarBase(base2);
        System.out.println("Base 1 creada en (1,1) con capacidad 5");
        System.out.println("Base 2 creada en (5,5) con capacidad 5");

        // Agregar vehículos
        System.out.println("\n=== Agregando Vehículos ===");
        Bicicleta bici1 = new Bicicleta("BIKE001", 100, base1, 1, 1);
        Bicicleta bici2 = new Bicicleta("BIKE002", 80, base1, 1, 1);
        Patinete patinete1 = new Patinete("SCOOT001", 90, base1, 1, 1);

        base1.agregarVehiculo(bici1);
        base1.agregarVehiculo(bici2);
        base1.agregarVehiculo(patinete1);
        System.out.println("Vehículos agregados a Base 1: 2 bicicletas y 1 patinete");

        // Crear usuarios
        System.out.println("\n=== Creando Usuarios ===");
        adminService.crearUsuario("Juan Pérez", "juan@email.com", "+34611111111", true);
        adminService.crearUsuario("María García", "maria@email.com", "+34622222222", false);
        adminService.crearTrabajador("Carlos López", "carlos@mobilityx.com", "+34633333333", "mecanico");
        System.out.println("Usuarios creados: 1 premium, 1 estándar, 1 mecánico");

        // Simular alquiler
        System.out.println("\n=== Simulando Alquiler ===");
        Usuario usuario = adminService.getUsuario("juan@email.com");
        usuario.agregarSaldo(50.0);
        System.out.println("Saldo de Juan: " + usuario.getSaldo() + " euros");

        if (alquilerService.iniciarAlquiler(usuario, bici1, base1)) {
            System.out.println("Juan ha alquilado la bicicleta BIKE001");
            System.out.println("Vehículos disponibles en Base 1: " + base1.getVehiculosDisponibles().size());
            
            // Simular finalización de alquiler en base2
            if (alquilerService.finalizarAlquiler(usuario, base2)) {
                System.out.println("Alquiler finalizado con éxito en Base 2");
                System.out.println("Nuevo saldo de Juan: " + usuario.getSaldo() + " euros");
            }
        }

        // Mostrar estado final
        System.out.println("\n=== Estado Final del Sistema ===");
        System.out.println("Usuarios registrados: " + adminService.getUsuarios().size());
        System.out.println("Trabajadores registrados: " + adminService.getTrabajadores().size());
        System.out.println("Vehículos en Base 1: " + base1.getVehiculosDisponibles().size());
        System.out.println("Vehículos en Base 2: " + base2.getVehiculosDisponibles().size());
    }
}
