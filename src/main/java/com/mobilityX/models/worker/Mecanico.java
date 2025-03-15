package main.java.com.mobilityX.models.worker;

public class Mecanico extends Trabajador {
    public Mecanico(String nombre, String dni) {
        super(nombre, dni, "Mecánico");
    }

    @Override
    public void realizarTarea() {
        System.out.println(nombre + " está reparando vehículos y bases.");
    }
}
