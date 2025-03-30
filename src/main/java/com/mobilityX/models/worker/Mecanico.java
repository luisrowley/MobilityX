package main.java.com.mobilityX.models.worker;

public class Mecanico extends Trabajador {
    public Mecanico(String nombre, String correo, String telefono) {
        super(nombre, correo, telefono, "Mecánico");
    }

    @Override
    public void realizarTarea() {
        System.out.println(nombre + " está reparando vehículos y bases.");
    }
}
