package com.mobilityX.models.vehicle;

public class Bicicleta extends Vehiculo {
    protected Base base;

    public Bicicleta(String id, int bateria, Base base, int x, int y) {
        super(id, bateria, x, y);
        this.base = base;
    }

    public Base getBase() {
        return base;
    }

    public void setBase(Base base) {
        this.base = base;
    }
}
