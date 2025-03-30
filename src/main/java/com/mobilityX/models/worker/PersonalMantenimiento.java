package com.mobilityX.models.worker;

public class PersonalMantenimiento extends Trabajador {
    public PersonalMantenimiento(String nombre, String correo, String telefono) {
        super(nombre, correo, telefono, "Mantenimiento");
    }

    @Override
    public void realizarTarea() {
        System.out.println(nombre + " está recargando baterías y recogiendo vehículos defectuosos.");
    }
}
