package main.java.com.mobilityX.models.user;

public class UsuarioPremium extends Usuario {
    private double descuento;
    private boolean puedeReservar;
    private boolean puedeUsarBateriaBaja;
    
    public UsuarioPremium(String nombre, String correo, String telefono) {
        super(nombre, correo, telefono);
        this.descuento = 0.2;
        this.puedeReservar = true;
        this.puedeUsarBateriaBaja = true;
    }

    public double getDescuento() {
        return descuento;
    }

    public boolean isPuedeReservar() {
        return puedeReservar;
    }

    public boolean isPuedeUsarBateriaBaja() {
        return puedeUsarBateriaBaja;
    }

}
