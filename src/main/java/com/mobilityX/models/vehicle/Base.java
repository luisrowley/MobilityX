package main.java.com.mobilityX.models.vehicle;

import java.util.ArrayList;
import java.util.List;

public class Base {
    private final String id;
    private final int x;
    private final int y;
    private final int capacidad;
    private final List<Vehiculo> vehiculos;

    public Base(String id, int x, int y, int capacidad) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.capacidad = capacidad;
        this.vehiculos = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean tieneEspacioDisponible() {
        return vehiculos.size() < capacidad;
    }

    public boolean agregarVehiculo(Vehiculo vehiculo) {
        if (!tieneEspacioDisponible()) {
            return false;
        }
        vehiculo.setUbicacion(x, y);
        return vehiculos.add(vehiculo);
    }

    public boolean retirarVehiculo(Vehiculo vehiculo) {
        return vehiculos.remove(vehiculo);
    }

    public List<Vehiculo> getVehiculosDisponibles() {
        List<Vehiculo> disponibles = new ArrayList<>();
        for (Vehiculo v : vehiculos) {
            if (v.isDisponible() && !v.necesitaRecarga()) {
                disponibles.add(v);
            }
        }
        return disponibles;
    }
}
