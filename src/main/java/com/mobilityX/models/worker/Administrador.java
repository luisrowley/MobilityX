package main.java.com.mobilityX.models.worker;

public class Administrador extends Trabajador {
    public Administrador(String nombre, String correo, String telefono) {
        super(nombre, correo, telefono, "Administrador del Sistema");
    }

    @Override
    public void realizarTarea() {
        System.out.println(nombre + " está gestionando el sistema de movilidad.");
    }
}
