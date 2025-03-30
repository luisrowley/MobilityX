package com.mobilityX;

import com.mobilityX.models.vehicle.*;
import com.mobilityX.services.*;

public class Main {
    public static void main(String[] args) {
        // Inicializar servicios
        System.out.println("=== Inicializando Sistema MobilityX ===");
        AlquilerService alquilerService = new AlquilerService();

        // Crear base
        System.out.println("\n=== Creando Base ===");
        Base base1 = new Base("B001", 1, 1, 5);
        alquilerService.agregarBase(base1);
        System.out.println("Base 1 creada en (1,1) con capacidad 5");
    }
}
