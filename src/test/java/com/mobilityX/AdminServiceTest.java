package com.mobilityX;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import com.mobilityX.models.user.Usuario;
import com.mobilityX.models.user.UsuarioPremium;
import com.mobilityX.models.worker.Administrador;
import com.mobilityX.services.AdminService;

public class AdminServiceTest {
    private AdminService adminService;
    private Administrador admin;

    @Before
    public void setUp() {
        admin = new Administrador("Admin Test", "admin.test@mobilityx.com", "+34600000001");
        adminService = new AdminService(admin);
    }

    @Test
    public void testCrearUsuarioEstandar() {
        boolean resultado = adminService.crearUsuario("Test User", "test@email.com", "+34611111112", false);
        assertTrue("Debería crear un usuario estándar correctamente", resultado);
        
        Usuario usuario = adminService.getUsuario("test@email.com");
        assertNotNull("El usuario debería existir", usuario);
        assertFalse("El usuario no debería ser premium", usuario instanceof UsuarioPremium);
        assertEquals("El nombre debería coincidir", "Test User", usuario.getNombre());
        assertEquals("El teléfono debería coincidir", "+34611111112", usuario.getTelefono());
    }

    @Test
    public void testCrearUsuarioPremium() {
        boolean resultado = adminService.crearUsuario("Test Premium", "premium@email.com", "+34611111113", true);
        assertTrue("Debería crear un usuario premium correctamente", resultado);
        
        Usuario usuario = adminService.getUsuario("premium@email.com");
        assertNotNull("El usuario debería existir", usuario);
        assertTrue("El usuario debería ser premium", usuario instanceof UsuarioPremium);
    }

    @Test
    public void testCrearUsuarioDuplicado() {
        adminService.crearUsuario("Test User", "duplicate@email.com", "+34611111114", false);
        boolean resultado = adminService.crearUsuario("Test User 2", "duplicate@email.com", "+34611111115", false);
        assertFalse("No debería permitir crear usuarios con correo duplicado", resultado);
    }

    @Test
    public void testEliminarUsuario() {
        adminService.crearUsuario("Test Delete", "delete@email.com", "+34611111116", false);
        boolean resultado = adminService.eliminarUsuario("delete@email.com");
        assertTrue("Debería eliminar el usuario correctamente", resultado);
        assertNull("El usuario no debería existir después de eliminarlo", adminService.getUsuario("delete@email.com"));
    }

    @Test
    public void testModificarUsuario() {
        adminService.crearUsuario("Test Modify", "modify@email.com", "+34611111117", false);
        boolean resultado = adminService.modificarUsuario("modify@email.com", "Modified Name", "+34611111118");
        assertTrue("Debería modificar el usuario correctamente", resultado);
        
        Usuario usuario = adminService.getUsuario("modify@email.com");
        assertEquals("El nombre debería estar actualizado", "Modified Name", usuario.getNombre());
        assertEquals("El teléfono debería estar actualizado", "+34611111118", usuario.getTelefono());
    }

    @Test
    public void testCrearTrabajador() {
        boolean resultado = adminService.crearTrabajador("Test Mechanic", "mechanic@mobilityx.com", "+34611111119", "mecanico");
        assertTrue("Debería crear un mecánico correctamente", resultado);
        
        resultado = adminService.crearTrabajador("Test Maintenance", "maintenance@mobilityx.com", "+34611111120", "mantenimiento");
        assertTrue("Debería crear personal de mantenimiento correctamente", resultado);
        
        resultado = adminService.crearTrabajador("Test Admin", "admin2@mobilityx.com", "+34611111121", "admin");
        assertTrue("Debería crear un administrador correctamente", resultado);
    }

    @Test
    public void testCrearTrabajadorTipoInvalido() {
        boolean resultado = adminService.crearTrabajador("Test Invalid", "invalid@mobilityx.com", "+34611111122", "invalido");
        assertFalse("No debería crear un trabajador con tipo inválido", resultado);
    }

    @Test
    public void testEliminarTrabajador() {
        adminService.crearTrabajador("Test Delete Worker", "deleteworker@mobilityx.com", "+34611111123", "mecanico");
        boolean resultado = adminService.eliminarTrabajador("deleteworker@mobilityx.com");
        assertTrue("Debería eliminar el trabajador correctamente", resultado);
    }

    @Test
    public void testEliminarAdminActual() {
        boolean resultado = adminService.eliminarTrabajador(admin.getCorreo());
        assertFalse("No debería permitir eliminar al admin actual", resultado);
    }
}
