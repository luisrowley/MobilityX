package com.mobilityX.services;

import com.mobilityX.models.user.Usuario;
import com.mobilityX.models.user.UsuarioPremium;
import com.mobilityX.models.vehicle.Base;
import com.mobilityX.models.vehicle.Vehiculo;
import com.mobilityX.models.vehicle.Moto;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AlquilerService {
    private final List<Base> bases;
    private final Map<String, Double> tarifas;
    private static final double TARIFA_BASE_BICICLETA = 2.0;  // euros por hora
    private static final double TARIFA_BASE_PATINETE = 3.0;   // euros por hora
    private static final double TARIFA_BASE_MOTO = 4.0;       // euros por hora

    public AlquilerService() {
        this.bases = new ArrayList<>();
        this.tarifas = new HashMap<>();
        inicializarTarifas();
    }

    private void inicializarTarifas() {
        tarifas.put("Bicicleta", TARIFA_BASE_BICICLETA);
        tarifas.put("Patinete", TARIFA_BASE_PATINETE);
        tarifas.put("MotoPequena", TARIFA_BASE_MOTO);
        tarifas.put("MotoGrande", TARIFA_BASE_MOTO * 1.5);
    }

    public void agregarBase(Base base) {
        bases.add(base);
    }

    public List<Base> getBasesConVehiculosDisponibles() {
        List<Base> basesDisponibles = new ArrayList<>();
        for (Base base : bases) {
            if (!base.getVehiculosDisponibles().isEmpty()) {
                basesDisponibles.add(base);
            }
        }
        return basesDisponibles;
    }

    public List<Moto> getMotosDisponibles() {
        List<Moto> motosDisponibles = new ArrayList<>();
        for (Base base : bases) {
            for (Vehiculo v : base.getVehiculosDisponibles()) {
                if (v instanceof Moto && v.isDisponible() && !v.necesitaRecarga()) {
                    motosDisponibles.add((Moto) v);
                }
            }
        }
        return motosDisponibles;
    }

    public boolean iniciarAlquiler(Usuario usuario, Vehiculo vehiculo, Base baseOrigen) {
        if (!usuario.puedeAlquilar()) {
            return false;
        }

        if (!vehiculo.isDisponible() || vehiculo.necesitaRecarga()) {
            return false;
        }

        if (baseOrigen != null && !baseOrigen.retirarVehiculo(vehiculo)) {
            return false;
        }

        try {
            usuario.iniciarAlquiler(vehiculo);
            return true;
        } catch (IllegalStateException e) {
            if (baseOrigen != null) {
                baseOrigen.agregarVehiculo(vehiculo);
            }
            return false;
        }
    }

    public boolean finalizarAlquiler(Usuario usuario, Base baseDestino) {
        Vehiculo vehiculo = usuario.getVehiculoActual();
        if (vehiculo == null) {
            return false;
        }

        // Verificar límites de la ciudad para motos
        if (vehiculo instanceof Moto && !vehiculo.estaEnLimitesCiudad()) {
            return false;
        }

        // Verificar espacio en la base para bicicletas y patinetes
        if (!(vehiculo instanceof Moto) && !baseDestino.tieneEspacioDisponible()) {
            return false;
        }

        LocalDateTime inicio = usuario.getInicioAlquiler();
        if (inicio == null) {
            return false;
        }

        // Calcular duración y costo
        Duration duracion = Duration.between(inicio, LocalDateTime.now());
        int horasUso = (int) Math.ceil(duracion.toMinutes() / 60.0);
        Double tarifa = getTarifa(vehiculo.getClass().getSimpleName());
        if (tarifa == null) {
            return false;
        }
        double costoTotal = tarifa * horasUso;

        // Descuento para usuarios premium (sin pattern matching para compatibilidad)
        if (usuario instanceof UsuarioPremium) {
            UsuarioPremium premium = (UsuarioPremium) usuario;
            costoTotal = costoTotal * (1 - premium.getDescuento());
        }

        try {
            usuario.finalizarAlquiler(costoTotal);
            vehiculo.actualizarBateria(horasUso);
            
            if (!(vehiculo instanceof Moto)) {
                baseDestino.agregarVehiculo(vehiculo);
            }
            
            return true;
        } catch (IllegalStateException e) {
            return false;
        }
    }

    public boolean actualizarTarifa(String tipoVehiculo, double nuevaTarifa) {
        if (nuevaTarifa < 0) {
            return false;
        }
        tarifas.put(tipoVehiculo, nuevaTarifa);
        return true;
    }

    public Double getTarifa(String tipoVehiculo) {
        return tarifas.get(tipoVehiculo);
    }

    /**
     * Devuelve un resumen del stock por tipo de vehículo.
     * El array devuelto contiene:
     *  index 0 → disponibles
     *  index 1 → averiados
     *  index 2 → en uso (no disponible y no averiado)
     */
    public Map<String, int[]> obtenerStock() {
        Map<String, int[]> stock = new HashMap<>();
        for (Base base : bases) {
            for (Vehiculo v : getAllVehiculos(base)) {
                String tipo = v.getClass().getSimpleName();
                stock.putIfAbsent(tipo, new int[3]);
                int[] arr = stock.get(tipo);
                if (v.isDisponible()) {
                    arr[0]++;
                } else if (v.isAveriado()) {
                    arr[1]++;
                } else {
                    arr[2]++;
                }
            }
        }
        return stock;
    }

    private List<Vehiculo> getAllVehiculos(Base base) {
        return base.getVehiculos();
    }

    public List<Base> getBases() {
        return new ArrayList<>(bases);
    }

    /**
     * Devuelve las N bases más cercanas a la posición dada, ordenadas por distancia ascendente.
     */
    public List<Base> basesMasCercanas(int x, int y, int n) {
        return bases.stream()
                .sorted((b1, b2) -> Double.compare(distancia(b1, x, y), distancia(b2, x, y)))
                .limit(n)
                .collect(Collectors.toList());
    }

    /**
     * Devuelve las N motos individuales más cercanas a la posición dada.
     */
    public List<Moto> motosMasCercanas(int x, int y, int n) {
        return getMotosDisponibles().stream()
                .sorted((m1, m2) -> Double.compare(distancia(m1, x, y), distancia(m2, x, y)))
                .limit(n)
                .collect(Collectors.toList());
    }

    private double distancia(Base b, int x, int y) {
        int dx = b.getX() - x;
        int dy = b.getY() - y;
        return Math.sqrt(dx*dx + dy*dy);
    }

    private double distancia(Vehiculo v, int x, int y) {
        int dx = v.getX() - x;
        int dy = v.getY() - y;
        return Math.sqrt(dx*dx + dy*dy);
    }
}
