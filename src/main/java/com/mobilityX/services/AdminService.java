package com.mobilityX.services;

import com.mobilityX.models.user.Usuario;
import com.mobilityX.models.user.UsuarioPremium;
import com.mobilityX.models.worker.Administrador;
import com.mobilityX.models.worker.Mecanico;
import com.mobilityX.models.worker.PersonalMantenimiento;
import com.mobilityX.models.worker.Trabajador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminService {
    private final Map<String, Usuario> usuarios;
    private final Map<String, Trabajador> trabajadores;
    private final Administrador adminActual;

    public AdminService(Administrador admin) {
        this.adminActual = admin;
        this.usuarios = new HashMap<>();
        this.trabajadores = new HashMap<>();
        trabajadores.put(admin.getCorreo(), admin);
    }

    public boolean crearUsuario(String nombre, String correo, String telefono, boolean isPremium) {
        if (adminActual == null || usuarios.containsKey(correo)) {
            return false;
        }

        Usuario nuevoUsuario = isPremium ? 
            new UsuarioPremium(nombre, correo, telefono) :
            new Usuario(nombre, correo, telefono);
        
        usuarios.put(correo, nuevoUsuario);
        return true;
    }

    public boolean eliminarUsuario(String correo) {
        if (adminActual == null || !usuarios.containsKey(correo)) {
            return false;
        }

        Usuario usuario = usuarios.get(correo);
        if (usuario.getVehiculoActual() != null) {
            return false;  // No se puede eliminar un usuario con alquiler activo
        }

        usuarios.remove(correo);
        return true;
    }

    public boolean modificarUsuario(String correo, String nuevoNombre, String nuevoTelefono) {
        if (adminActual == null || !usuarios.containsKey(correo)) {
            return false;
        }

        Usuario usuario = usuarios.get(correo);
        usuario = new Usuario(nuevoNombre, correo, nuevoTelefono);
        usuarios.put(correo, usuario);
        return true;
    }

    public boolean convertirAPremium(String correo) {
        if (adminActual == null || !usuarios.containsKey(correo)) {
            return false;
        }

        Usuario usuario = usuarios.get(correo);
        if (usuario instanceof UsuarioPremium) {
            return false;
        }

        if (!usuario.esElegibleParaPremium()) {
            return false;
        }

        UsuarioPremium usuarioPremium = new UsuarioPremium(
            usuario.getNombre(),
            usuario.getCorreo(),
            usuario.getTelefono()
        );
        usuarios.put(correo, usuarioPremium);
        return true;
    }

    public boolean crearTrabajador(String nombre, String correo, String telefono, String tipo) {
        if (adminActual == null || trabajadores.containsKey(correo)) {
            return false;
        }

        Trabajador nuevoTrabajador;
        switch (tipo.toLowerCase()) {
            case "mecanico":
                nuevoTrabajador = new Mecanico(nombre, correo, telefono);
                break;
            case "mantenimiento":
                nuevoTrabajador = new PersonalMantenimiento(nombre, correo, telefono);
                break;
            case "admin":
                nuevoTrabajador = new Administrador(nombre, correo, telefono);
                break;
            default:
                return false;
        }

        trabajadores.put(correo, nuevoTrabajador);
        return true;
    }

    public boolean eliminarTrabajador(String correo) {
        if (adminActual == null || !trabajadores.containsKey(correo)) {
            return false;
        }

        if (correo.equals(adminActual.getCorreo())) {
            return false;  // No se puede eliminar al admin actual
        }

        trabajadores.remove(correo);
        return true;
    }

    public List<Usuario> getUsuarios() {
        return new ArrayList<>(usuarios.values());
    }

    public List<Trabajador> getTrabajadores() {
        return new ArrayList<>(trabajadores.values());
    }

    public Usuario getUsuario(String correo) {
        return usuarios.get(correo);
    }

    public Trabajador getTrabajador(String correo) {
        return trabajadores.get(correo);
    }
}
