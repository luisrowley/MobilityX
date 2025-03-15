package main.java.com.mobilityX.models.worker;

public class PersonalMantenimiento extends Trabajador {
    public PersonalMantenimiento(String nombre, String dni) {
        super(nombre, dni, "Mantenimiento");
    }

    @Override
    public void realizarTarea() {
        System.out.println(nombre + " está recargando baterías y recogiendo vehículos defectuosos.");
    }
}
