package com.mobilityX.models;

import java.util.ArrayList;
import java.util.List;

import com.mobilityX.models.vehicle.Vehiculo;

class Estacion {
    private String nombre;
    private List<Vehiculo> vehiculos;
    
    public Estacion(String nombre) {
        this.nombre = nombre;
        this.vehiculos = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }
    
    public void agregarVehiculo(Vehiculo vehiculo) {
        vehiculos.add(vehiculo);
    }
    
    public Vehiculo prestarVehiculo() {
        for (Vehiculo v : vehiculos) {
            if (v.isDisponible()) {
                v.setDisponible(false);
                return v;
            }
        }
        return null;
    }
}
