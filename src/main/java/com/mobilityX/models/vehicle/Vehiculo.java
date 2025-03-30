package com.mobilityX.models.vehicle;

public class Vehiculo {
    protected String id;
    protected boolean disponible;
    protected int bateria;
    protected int x, y;
    protected static final int CONSUMO_BATERIA_POR_HORA = 10;
    
    public Vehiculo(String id, int bateria, int x, int y) {
        this.id = id;
        this.bateria = bateria;
        this.disponible = true;
        this.x = x;
        this.y = y;
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
}
