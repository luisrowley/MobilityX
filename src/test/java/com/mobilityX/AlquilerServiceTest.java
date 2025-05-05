package com.mobilityX;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import com.mobilityX.models.user.Usuario;
import com.mobilityX.models.vehicle.Base;
import com.mobilityX.models.vehicle.Bicicleta;
import com.mobilityX.models.vehicle.Patinete;
import com.mobilityX.models.vehicle.Moto;
import com.mobilityX.models.vehicle.Moto.MotoPequena;
import com.mobilityX.models.vehicle.Moto.MotoGrande;
import com.mobilityX.services.AlquilerService;

import java.util.List;

public class AlquilerServiceTest {
    private AlquilerService alquilerService;
    private Base base1;
    private Base base2;
    private Bicicleta bicicleta;
    private Patinete patinete;
    private Moto motoPequena;
    private Moto motoGrande;
    private Usuario usuario;

    @Before
    public void setUp() {
        // Inicializar el servicio de alquiler
        alquilerService = new AlquilerService();
        
        // Crear bases
        base1 = new Base("B001", 1, 1, 3);
        base2 = new Base("B002", 5, 5, 2);
        
        // Crear vehículos
        bicicleta = new Bicicleta("BIC001", 100, base1, 1, 1);
        patinete = new Patinete("PAT001", 100, base1, 1, 1);
        motoPequena = new MotoPequena("MP001", 100, 1, 1);
        motoGrande = new MotoGrande("MG001", 100, 1, 1);
        
        // Agregar vehículos a las bases
        base1.agregarVehiculo(bicicleta);
        base1.agregarVehiculo(patinete);
        base1.agregarVehiculo(motoPequena);
        
        // Agregar bases al servicio
        alquilerService.agregarBase(base1);
        alquilerService.agregarBase(base2);
        
        // Crear usuario
        usuario = new Usuario("Test User", "test@email.com", "+34611111112");
        usuario.agregarSaldo(50.0); // Añadir saldo para que pueda alquilar
    }

    @Test
    public void testAgregarBase() {
        Base nuevaBase = new Base("B003", 8, 8, 5);
        alquilerService.agregarBase(nuevaBase);
        
        List<Base> basesDisponibles = alquilerService.getBasesConVehiculosDisponibles();
        assertTrue("La nueva base debería estar en la lista de bases", basesDisponibles.contains(base1));
    }

    @Test
    public void testGetBasesConVehiculosDisponibles() {
        List<Base> basesDisponibles = alquilerService.getBasesConVehiculosDisponibles();
        
        assertEquals("Debería haber una base con vehículos disponibles", 1, basesDisponibles.size());
        assertTrue("La base1 debería tener vehículos disponibles", basesDisponibles.contains(base1));
        assertFalse("La base2 no debería tener vehículos disponibles", basesDisponibles.contains(base2));
    }

    @Test
    public void testGetMotosDisponibles() {
        List<Moto> motosDisponibles = alquilerService.getMotosDisponibles();
        
        assertEquals("Debería haber una moto disponible", 1, motosDisponibles.size());
        assertTrue("La motoPequena debería estar disponible", motosDisponibles.contains(motoPequena));
    }

    @Test
    public void testIniciarAlquilerExitoso() {
        boolean resultado = alquilerService.iniciarAlquiler(usuario, bicicleta, base1);
        
        assertTrue("El alquiler debería iniciarse correctamente", resultado);
        assertEquals("El usuario debería tener asignada la bicicleta", bicicleta, usuario.getVehiculoActual());
        assertFalse("La bicicleta no debería estar disponible", bicicleta.isDisponible());
        assertNotNull("El inicio del alquiler debería registrarse", usuario.getInicioAlquiler());
    }

    @Test
    public void testIniciarAlquilerUsuarioSinSaldo() {
        Usuario usuarioSinSaldo = new Usuario("Sin Saldo", "sinsaldo@email.com", "+34611111113");
        boolean resultado = alquilerService.iniciarAlquiler(usuarioSinSaldo, bicicleta, base1);
        
        assertFalse("El alquiler no debería iniciarse sin saldo", resultado);
        assertNull("El usuario no debería tener vehículo asignado", usuarioSinSaldo.getVehiculoActual());
        assertTrue("La bicicleta debería seguir disponible", bicicleta.isDisponible());
    }

    @Test
    public void testIniciarAlquilerVehiculoNoDisponible() {
        // Primero hacemos que el vehículo no esté disponible
        bicicleta.setDisponible(false);
        
        boolean resultado = alquilerService.iniciarAlquiler(usuario, bicicleta, base1);
        
        assertFalse("El alquiler no debería iniciarse con vehículo no disponible", resultado);
        assertNull("El usuario no debería tener vehículo asignado", usuario.getVehiculoActual());
    }

    @Test
    public void testIniciarAlquilerVehiculoConBateriaBaja() {
        // Simulamos batería baja
        bicicleta.actualizarBateria(9); // Consume 90% de batería
        
        boolean resultado = alquilerService.iniciarAlquiler(usuario, bicicleta, base1);
        
        assertFalse("El alquiler no debería iniciarse con batería baja", resultado);
        assertNull("El usuario no debería tener vehículo asignado", usuario.getVehiculoActual());
    }

    @Test
    public void testFinalizarAlquilerExitoso() {
        // Primero iniciamos un alquiler
        alquilerService.iniciarAlquiler(usuario, bicicleta, base1);
        
        boolean resultado = alquilerService.finalizarAlquiler(usuario, base2);
        
        assertTrue("El alquiler debería finalizarse correctamente", resultado);
        assertNull("El usuario no debería tener vehículo asignado", usuario.getVehiculoActual());
        assertTrue("La bicicleta debería estar disponible nuevamente", bicicleta.isDisponible());
        assertTrue("La base de destino debería contener el vehículo", 
                   base2.getVehiculosDisponibles().contains(bicicleta));
    }

    @Test
    public void testFinalizarAlquilerSinAlquilerActivo() {
        boolean resultado = alquilerService.finalizarAlquiler(usuario, base2);
        
        assertFalse("No debería poder finalizar un alquiler sin uno activo", resultado);
    }

    @Test
    public void testFinalizarAlquilerMotoFueraLimites() {
        // Iniciamos alquiler de moto
        alquilerService.iniciarAlquiler(usuario, motoPequena, base1);
        
        // Movemos la moto fuera de los límites de la ciudad
        motoPequena.setUbicacion(15, 15);
        
        boolean resultado = alquilerService.finalizarAlquiler(usuario, base2);
        
        assertFalse("No debería poder finalizar alquiler con moto fuera de límites", resultado);
        assertEquals("El usuario debería seguir con la moto asignada", motoPequena, usuario.getVehiculoActual());
    }

    @Test
    public void testFinalizarAlquilerBaseDestinoSinEspacio() {
        // Llenamos la base2 hasta su capacidad
        Bicicleta v1 = new Bicicleta("EXTRA1", 100, base2, 5, 5);
        Bicicleta v2 = new Bicicleta("EXTRA2", 100, base2, 5, 5);
        base2.agregarVehiculo(v1);
        base2.agregarVehiculo(v2);
        
        // Iniciamos alquiler
        alquilerService.iniciarAlquiler(usuario, bicicleta, base1);
        
        boolean resultado = alquilerService.finalizarAlquiler(usuario, base2);
        
        assertFalse("No debería poder finalizar alquiler si la base destino está llena", resultado);
        assertEquals("El usuario debería seguir con la bicicleta asignada", bicicleta, usuario.getVehiculoActual());
    }
}
