package main.java.com.mobilityX.models.user;

import main.java.com.mobilityX.models.vehicle.Vehiculo;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class Usuario {
    private String nombre;
    private String correo;
    private String telefono;
    private double saldo;
    private int alquileresUltimoMes;
    private int alquileresConsecutivos;
    private Set<String> tiposVehiculosUsados;
    private int mesesConTiposUsados;
    private Vehiculo vehiculoActual;
    private LocalDateTime inicioAlquiler;

    public Usuario(String nombre, String correo, String telefono) {
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
        this.saldo = 0.0;
        this.alquileresUltimoMes = 0;
        this.alquileresConsecutivos = 0;
        this.tiposVehiculosUsados = new HashSet<>();
        this.mesesConTiposUsados = 0;
        this.vehiculoActual = null;
        this.inicioAlquiler = null;
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

    public double getSaldo() {
        return saldo;
    }

    public void agregarSaldo(double cantidad) {
        if (cantidad > 0) {
            this.saldo += cantidad;
        }
    }

    public boolean tieneSaldoSuficiente() {
        return saldo > 0;
    }

    public boolean puedeAlquilar() {
        return tieneSaldoSuficiente() && vehiculoActual == null;
    }

    public void iniciarAlquiler(Vehiculo vehiculo) {
        if (!puedeAlquilar()) {
            throw new IllegalStateException("No se puede iniciar el alquiler: saldo insuficiente o ya tiene un vehículo alquilado");
        }
        vehiculo.setDisponible(false);
        this.vehiculoActual = vehiculo;
        this.inicioAlquiler = LocalDateTime.now();
        registrarAlquiler(vehiculo.getClass().getSimpleName());
    }

    public void finalizarAlquiler(double costoTotal) {
        if (vehiculoActual == null || inicioAlquiler == null) {
            throw new IllegalStateException("No hay alquiler activo");
        }
        if (costoTotal > saldo) {
            throw new IllegalStateException("Saldo insuficiente para finalizar el alquiler");
        }
        saldo -= costoTotal;
        vehiculoActual.setDisponible(true);
        vehiculoActual = null;
        inicioAlquiler = null;
    }

    public LocalDateTime getInicioAlquiler() {
        return inicioAlquiler;
    }

    public Vehiculo getVehiculoActual() {
        return vehiculoActual;
    }

    private void registrarAlquiler(String tipoVehiculo) {
        alquileresUltimoMes++;
        tiposVehiculosUsados.add(tipoVehiculo);
    }
    
    public boolean esElegibleParaPremium() {
        return (alquileresUltimoMes >= 15 || alquileresConsecutivos >= 10) || mesesConTiposUsados >= 6;
    }
}
