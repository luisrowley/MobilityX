package com.mobilityX.models.worker;

public abstract class Trabajador {
    protected String nombre;
    protected String correo;
    protected String telefono;
    protected String puesto;
    private int intervenciones;

    public Trabajador(String nombre, String correo, String telefono, String puesto) {
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
        this.puesto = puesto;
        this.intervenciones = 0;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getPuesto() {
        return puesto;
    }

    public int getIntervenciones() {
        return intervenciones;
    }

    public void incrementarIntervenciones() {
        this.intervenciones++;
    }

    public void mostrarInformacion() {
        System.out.println("Trabajador: " + nombre + " | Correo: " + correo + " | Teléfono: " + telefono + " | Puesto: " + puesto);
    }

    public abstract void realizarTarea();
}
