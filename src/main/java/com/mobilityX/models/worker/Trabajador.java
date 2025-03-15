package main.java.com.mobilityX.models.worker;

public abstract class Trabajador {
    protected String nombre;
    protected String dni;
    protected String puesto;

    public Trabajador(String nombre, String dni, String puesto) {
        this.nombre = nombre;
        this.dni = dni;
        this.puesto = puesto;
    }

    public void mostrarInformacion() {
        System.out.println("Trabajador: " + nombre + " | DNI: " + dni + " | Puesto: " + puesto);
    }

    public abstract void realizarTarea();
}
