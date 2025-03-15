package main.java.com.mobilityX.models.worker;

public class Administrador extends Trabajador {
    public Administrador(String nombre, String dni) {
        super(nombre, dni, "Administrador del Sistema");
    }

    @Override
    public void realizarTarea() {
        System.out.println(nombre + " está gestionando el sistema de movilidad.");
    }
}
