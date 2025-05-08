package com.mobilityX.services;

import com.mobilityX.models.user.Usuario;
import com.mobilityX.models.vehicle.Base;
import com.mobilityX.models.vehicle.Vehiculo;
import com.mobilityX.models.worker.Trabajador;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio auxiliar para generar listados y estadísticas.
 * No persiste datos: simplemente crea vistas ordenadas a partir de las colecciones
 * expuestas por otros servicios.
 */
public class StatsService {

    /** Bases ordenadas por demanda descendente */
    public List<Base> basesPorDemanda(List<Base> bases) {
        return bases.stream()
                .sorted(Comparator.comparingInt(Base::getDemanda).reversed())
                .collect(Collectors.toList());
    }

    /** Trabajadores ordenados por intervenciones descendente */
    public List<Trabajador> trabajadoresPorIntervenciones(List<Trabajador> trabajadores) {
        return trabajadores.stream()
                .sorted(Comparator.comparingInt(Trabajador::getIntervenciones).reversed())
                .collect(Collectors.toList());
    }

    /** Vehículos de un tipo dado ordenados por tiempo de uso descendente */
    public List<Vehiculo> vehiculosPorUso(List<Base> bases, String tipoSimpleName) {
        return bases.stream()
                .flatMap(b -> b.getVehiculos().stream())
                .filter(v -> v.getClass().getSimpleName().equalsIgnoreCase(tipoSimpleName))
                .sorted(Comparator.comparingLong(Vehiculo::getTiempoUsoMinutosTotal).reversed())
                .collect(Collectors.toList());
    }

    /** Usuarios ordenados por gasto total descendente */
    public List<Usuario> usuariosPorGasto(List<Usuario> usuarios) {
        return usuarios.stream()
                .sorted(Comparator.comparingDouble(Usuario::getGastoTotal).reversed())
                .collect(Collectors.toList());
    }

    /** Vehículos que han requerido al menos una reparación, ordenados por número de reparaciones desc */
    public List<Vehiculo> vehiculosReparados(List<Base> bases) {
        return bases.stream()
                .flatMap(b -> b.getVehiculos().stream())
                .filter(v -> !v.getHistorialReparaciones().isEmpty())
                .sorted(Comparator.comparingInt((Vehiculo v) -> v.getHistorialReparaciones().size()).reversed())
                .collect(Collectors.toList());
    }
}
