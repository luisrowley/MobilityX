package main.java.com.mobilityX.models.vehicle;

public class Vehiculo {
    protected String id;
    protected boolean disponible;
    protected int bateria;
    protected int x, y;
    
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

    public boolean necesitaRecarga() {
        return bateria < 20;
    }
    
    public boolean isDisponible() {
        return disponible;
    }
    
    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }
}
