package main.java.com.mobilityX.models.user;

import java.util.HashSet;
import java.util.Set;

class Usuario {
    private String nombre;
    private String correo;
    private String telefono;
    private int alquileresUltimoMes;
    private int alquileresConsecutivos;
    private Set<String> tiposVehiculosUsados;
    private int mesesConTiposUsados;

    public Usuario(String nombre, String correo, String telefono) {
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
        this.alquileresUltimoMes = 0;
        this.alquileresConsecutivos = 0;
        this.tiposVehiculosUsados = new HashSet<>();
        this.mesesConTiposUsados = 0;
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

    public void registrarAlquiler(String tipoVehiculo) {
        alquileresUltimoMes++;
        tiposVehiculosUsados.add(tipoVehiculo);
    }
    
    public boolean esElegibleParaPremium() {
        return (alquileresUltimoMes >= 15 || alquileresConsecutivos >= 10) || mesesConTiposUsados >= 6;
    }
}
