package com.mobilityX.models.vehicle;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Vehiculo {
    protected String id;
    protected boolean disponible;
    protected int bateria;
    protected int x, y;
    protected boolean averiado;
    private final List<Reparacion> historialReparaciones;
    protected static final int CONSUMO_BATERIA_POR_HORA = 10;

    public Vehiculo(String id, int bateria, int x, int y) {
        this.id = id;
        this.bateria = bateria;
        this.disponible = true;
        this.x = x;
        this.y = y;
        this.averiado = false;
        this.historialReparaciones = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public int getBateria() {
        return bateria;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setUbicacion(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean necesitaRecarga() {
        return bateria < 20;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public void actualizarBateria(int horasUso) {
        this.bateria = Math.max(0, this.bateria - (horasUso * CONSUMO_BATERIA_POR_HORA));
    }

    public boolean estaEnLimitesCiudad() {
        // Suponemos que los límites de la ciudad son un cuadrado de 10x10 km
        return x >= 0 && x <= 10 && y >= 0 && y <= 10;
    }

    public boolean isAveriado() {
        return averiado;
    }

    public void setAveriado(boolean averiado) {
        this.averiado = averiado;
    }

    public void setBateria(int bateria) {
        this.bateria = Math.max(0, bateria);
    }

    public List<Reparacion> getHistorialReparaciones() {
        return new ArrayList<>(historialReparaciones);
    }

    public void registrarReparacion(double coste, String descripcion) {
        this.averiado = false;
        historialReparaciones.add(new Reparacion(LocalDateTime.now(), coste, descripcion));
    }
}

class Reparacion {
    private LocalDateTime fecha;
    private double coste;
    private String descripcion;

    public Reparacion(LocalDateTime fecha, double coste, String descripcion) {
        this.fecha = fecha;
        this.coste = coste;
        this.descripcion = descripcion;
    }

    // Getters y setters para la clase Reparacion
    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public double getCoste() {
        return coste;
    }

    public void setCoste(double coste) {
        this.coste = coste;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
