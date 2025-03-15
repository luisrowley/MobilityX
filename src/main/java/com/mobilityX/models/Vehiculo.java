package main.java.com.mobilityX.models;

public class Vehiculo {
    private String id;
    private String tipo;
    private boolean disponible;
    
    public Vehiculo(String id, String tipo) {
        this.id = id;
        this.tipo = tipo;
        this.disponible = true;
    }
    
    public boolean isDisponible() {
        return disponible;
    }
    
    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }
    
    public String getTipo() {
        return tipo;
    }

    public String getId() {
        return id;
    }
}
