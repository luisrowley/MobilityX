package com.mobilityX;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.mobilityX.models.vehicle.Base;
import com.mobilityX.models.vehicle.Bicicleta;
import com.mobilityX.models.vehicle.Moto.MotoPequena;
import com.mobilityX.models.vehicle.Vehiculo;
import com.mobilityX.models.worker.Mecanico;
import com.mobilityX.models.worker.PersonalMantenimiento;
import com.mobilityX.services.MantenimientoService;

/**
 * Tests unitarios para {@link MantenimientoService}.
 */
public class MantenimientoServiceTest {

    private PersonalMantenimiento mantenimiento;
    private Mecanico mecanico;
    private MantenimientoService service;

    // Vehículos de prueba
    private Vehiculo bici;
    private Vehiculo moto;

    @Before
    public void setUp() {
        mantenimiento = new PersonalMantenimiento("Maint", "maint@mobilityx.com", "+34677770000");
        mecanico = new Mecanico("Mec", "mec@mobilityx.com", "+34677770001");
        service = new MantenimientoService(Arrays.asList(mantenimiento, mecanico));

        Base base = new Base("B1", 0, 0, 10);
        bici = new Bicicleta("BI001", 80, base, 0, 0);
        moto = new MotoPequena("MO001", 60, 1, 1);
    }

    @Test
    public void testAsignarYRetirarVehiculo() {
        // asignar a personal mantenimiento
        boolean asignado = service.asignarVehiculo(mantenimiento.getCorreo(), bici);
        assertTrue("Debería asignar la bicicleta", asignado);

        List<Vehiculo> asignados = service.getVehiculosAsignados(mantenimiento.getCorreo());
        assertEquals(1, asignados.size());
        assertFalse(bici.isDisponible());

        // marcar retirado -> averiado
        boolean retirado = service.marcarRetirado(mantenimiento.getCorreo(), bici);
        assertTrue(retirado);
        assertTrue(bici.isAveriado());
        assertFalse(bici.isDisponible());
    }

    @Test
    public void testRepararVehiculo() {
        // preparar moto averiada
        moto.setAveriado(true);
        service.asignarVehiculo(mecanico.getCorreo(), moto);

        boolean reparado = service.marcarReparado(mecanico.getCorreo(), moto, 45.5, "Cambio de rueda");
        assertTrue(reparado);
        assertFalse(moto.isAveriado());
        assertTrue(moto.isDisponible());
        assertEquals(1, moto.getHistorialReparaciones().size());
    }

    @Test
    public void testAsignacionDuplicada() {
        assertTrue(service.asignarVehiculo(mantenimiento.getCorreo(), bici));
        assertFalse("No debería permitir asignación duplicada", service.asignarVehiculo(mantenimiento.getCorreo(), bici));
    }
}
