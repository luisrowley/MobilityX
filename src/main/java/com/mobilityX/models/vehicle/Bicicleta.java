package main.java.com.mobilityX.models.vehicle;

class Bicicleta extends Vehiculo {
    protected Base base;

    public Bicicleta(String id, int bateria, Base base, int x, int y) {
        super(id, bateria, x, y);
        this.base = base;
    }
}
