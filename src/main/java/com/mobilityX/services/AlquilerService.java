package main.java.com.mobilityX.services;

import main.java.com.mobilityX.models.user.Usuario;
import main.java.com.mobilityX.models.vehicle.Base;
import main.java.com.mobilityX.models.vehicle.Vehiculo;
import main.java.com.mobilityX.models.vehicle.Moto;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        double tarifa = tarifas.get(vehiculo.getClass().getSimpleName());
        double costoTotal = tarifa * horasUso;

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
}
