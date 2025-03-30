package main.java.com.mobilityX.models.vehicle;

public class Patinete extends Vehiculo {
    protected Base base;

    public Patinete(String id, int bateria, Base base, int x, int y) {
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
