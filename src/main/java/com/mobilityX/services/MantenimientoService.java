package com.mobilityX.services;

import com.mobilityX.models.vehicle.Vehiculo;
import com.mobilityX.models.worker.Trabajador;
import com.mobilityX.models.worker.Mecanico;
import com.mobilityX.models.worker.PersonalMantenimiento;

import java.util.*;

/**
 * Servicio que gestiona la asignación de vehículos a los empleados de mantenimiento
 * (personal de mantenimiento y mecánicos), así como las acciones que éstos realizan
 * sobre los vehículos.
 */
public class MantenimientoService {

    /**
     * Tabla en memoria: trabajador → lista de vehículos asignados.
     */
    private final Map<String, List<Vehiculo>> asignaciones; // clave = correo del trabajador

    /**
     * Tabla auxiliar para acceder al propio trabajador por correo.
     */
    private final Map<String, Trabajador> trabajadores;

    public MantenimientoService(Collection<Trabajador> plantilla) {
        this.trabajadores = new HashMap<>();
        for (Trabajador t : plantilla) {
            trabajadores.put(t.getCorreo(), t);
        }
        this.asignaciones = new HashMap<>();
    }

    /**
     * Asigna un vehículo a un trabajador de mantenimiento o mecánico.
     * Devuelve true si la asignación se realiza correctamente.
     */
    public boolean asignarVehiculo(String correoTrabajador, Vehiculo vehiculo) {
        Trabajador t = trabajadores.get(correoTrabajador);
        if (t == null) return false;
        if (!(t instanceof PersonalMantenimiento) && !(t instanceof Mecanico)) {
            return false; // sólo personal válido
        }
        List<Vehiculo> lista = asignaciones.computeIfAbsent(correoTrabajador, k -> new ArrayList<>());
        if (lista.contains(vehiculo)) return false; // ya asignado
        lista.add(vehiculo);
        vehiculo.setDisponible(false); // reservado para mantenimiento
        return true;
    }

    /**
     * Devuelve copia de la lista de vehículos asignados a un trabajador.
     */
    public List<Vehiculo> getVehiculosAsignados(String correoTrabajador) {
        List<Vehiculo> lista = asignaciones.get(correoTrabajador);
        if (lista == null) return Collections.emptyList();
        return new ArrayList<>(lista);
    }

    /**
     * Personal de mantenimiento retira (p.ej. por batería baja) un vehículo. El vehículo
     * queda marcado como no disponible y averiado (a la espera de reparación).
     */
    public boolean marcarRetirado(String correoTrabajador, Vehiculo vehiculo) {
        Trabajador t = trabajadores.get(correoTrabajador);
        if (!(t instanceof PersonalMantenimiento)) return false;
        List<Vehiculo> lista = asignaciones.get(correoTrabajador);
        if (lista == null || !lista.contains(vehiculo)) return false;

        vehiculo.setAveriado(true);
        vehiculo.setDisponible(false);
        return true;
    }

    /**
     * Mecánico marca reparado un vehículo, registrando el importe de reparación y dejando
     * el vehículo disponible de nuevo.
     */
    public boolean marcarReparado(String correoTrabajador, Vehiculo vehiculo, double coste, String descripcion) {
        Trabajador t = trabajadores.get(correoTrabajador);
        if (!(t instanceof Mecanico)) return false;
        List<Vehiculo> lista = asignaciones.get(correoTrabajador);
        if (lista == null || !lista.contains(vehiculo)) return false;

        vehiculo.registrarReparacion(coste, descripcion);
        vehiculo.setDisponible(true);
        vehiculo.setAveriado(false);
        // tras reparar, se puede retirar de la asignación
        lista.remove(vehiculo);
        return true;
    }

    /**
     * Elimina todas las asignaciones de un trabajador (p.ej. fin de turno).
     */
    public void limpiarAsignaciones(String correoTrabajador) {
        asignaciones.remove(correoTrabajador);
    }
}
