import ConexionDB.GestorConexion;
import logica.GestorClientes;
import model.Cliente;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)

public class GestorClientesTest {


    @Test
    void testBuscarClientePorCodigo_encontrado() throws Exception {

        Connection conn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        try (MockedStatic<GestorConexion> mc = mockStatic(GestorConexion.class)) {
            mc.when(GestorConexion::obtenerConexion).thenReturn(conn);
            when(conn.prepareStatement(anyString())).thenReturn(ps);
            when(ps.executeQuery()).thenReturn(rs);

            when(rs.next()).thenReturn(true);
            when(rs.getInt("codigo")).thenReturn(1);
            when(rs.getString("dni")).thenReturn("12345678A");
            when(rs.getString("nombre")).thenReturn("Juan");
            when(rs.getString("apellidos")).thenReturn("García");
            when(rs.getString("email")).thenReturn("juan@gmail.com");
            when(rs.getString("telefono")).thenReturn("600000001");
            when(rs.getString("direccion")).thenReturn("Calle 1");

            // 2. ACT
            Cliente cliente = GestorClientes.buscarClientePorCodigo(1);

            // 3. ASSERT
            assertNotNull(cliente);
            assertEquals("Juan", cliente.getNombre());
            assertEquals("12345678A", cliente.getDni());
        }
    }

    @Test
    void testBuscarClientePorCodigo_noEncontrado() throws Exception {

        Connection conn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        try (MockedStatic<GestorConexion> mc = mockStatic(GestorConexion.class)) {
            mc.when(GestorConexion::obtenerConexion).thenReturn(conn);
            when(conn.prepareStatement(anyString())).thenReturn(ps);
            when(ps.executeQuery()).thenReturn(rs);
            when(rs.next()).thenReturn(false);

            Cliente cliente = GestorClientes.buscarClientePorCodigo(999);

            assertNull(cliente);
        }
    }

    // ══════════════════════════════════════
// eliminarCliente
// ══════════════════════════════════════

    @Test
    void testEliminarCliente_exitoso() throws Exception {
        Connection conn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);

        try (MockedStatic<GestorConexion> mc = mockStatic(GestorConexion.class)) {
            mc.when(GestorConexion::obtenerConexion).thenReturn(conn);
            when(conn.prepareStatement(anyString())).thenReturn(ps);
            when(ps.executeUpdate()).thenReturn(1);

            System.setIn(new java.io.ByteArrayInputStream("1\n".getBytes()));

            assertDoesNotThrow(() -> GestorClientes.eliminarCliente());
        }
    }

    @Test
    void testEliminarCliente_noExiste() throws Exception {
        Connection conn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);

        try (MockedStatic<GestorConexion> mc = mockStatic(GestorConexion.class)) {
            mc.when(GestorConexion::obtenerConexion).thenReturn(conn);
            when(conn.prepareStatement(anyString())).thenReturn(ps);
            when(ps.executeUpdate()).thenReturn(0);

            System.setIn(new java.io.ByteArrayInputStream("999\n".getBytes()));

            assertDoesNotThrow(() -> GestorClientes.eliminarCliente());
        }
    }

// ══════════════════════════════════════
// buscarCliente
// ══════════════════════════════════════

    @Test
    void testBuscarCliente_encontrado() throws Exception {
        Connection conn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        try (MockedStatic<GestorConexion> mc = mockStatic(GestorConexion.class)) {
            mc.when(GestorConexion::obtenerConexion).thenReturn(conn);
            when(conn.prepareStatement(anyString())).thenReturn(ps);
            when(ps.executeQuery()).thenReturn(rs);
            when(rs.next()).thenReturn(true);
            when(rs.getInt("codigo")).thenReturn(1);
            when(rs.getString("dni")).thenReturn("12345678A");
            when(rs.getString("nombre")).thenReturn("Juan");
            when(rs.getString("Apellidos")).thenReturn("García");
            when(rs.getString("email")).thenReturn("juan@gmail.com");
            when(rs.getString("telefono")).thenReturn("600000001");
            when(rs.getString("direccion")).thenReturn("Calle 1");

            System.setIn(new java.io.ByteArrayInputStream("12345678A\n".getBytes()));

            assertDoesNotThrow(() -> GestorClientes.buscarCliente());
        }
    }

    @Test
    void testBuscarCliente_noEncontrado() throws Exception {
        Connection conn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        try (MockedStatic<GestorConexion> mc = mockStatic(GestorConexion.class)) {
            mc.when(GestorConexion::obtenerConexion).thenReturn(conn);
            when(conn.prepareStatement(anyString())).thenReturn(ps);
            when(ps.executeQuery()).thenReturn(rs);
            when(rs.next()).thenReturn(false);

            System.setIn(new java.io.ByteArrayInputStream("00000000Z\n".getBytes()));

            assertDoesNotThrow(() -> GestorClientes.buscarCliente());
        }
    }

// ══════════════════════════════════════
// añadirCliente
// ══════════════════════════════════════

    @Test
    void testAñadirCliente_exitoso() throws Exception {
        Connection conn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);

        try (MockedStatic<GestorConexion> mc = mockStatic(GestorConexion.class)) {
            mc.when(GestorConexion::obtenerConexion).thenReturn(conn);
            when(conn.prepareStatement(anyString())).thenReturn(ps);
            when(ps.executeUpdate()).thenReturn(1);

            String input = "12345678A\nJuan\nGarcía\njuan@gmail.com\n600000001\nCalle 1\n";
            System.setIn(new java.io.ByteArrayInputStream(input.getBytes()));

            assertDoesNotThrow(() -> GestorClientes.añadirCliente());
        }
    }

    @Test
    void testAñadirCliente_sqlException() throws Exception {
        Connection conn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);

        try (MockedStatic<GestorConexion> mc = mockStatic(GestorConexion.class)) {
            mc.when(GestorConexion::obtenerConexion).thenReturn(conn);
            when(conn.prepareStatement(anyString())).thenReturn(ps);
            when(ps.executeUpdate()).thenThrow(new SQLException("Error DB"));

            String input = "12345678A\nJuan\nGarcía\njuan@gmail.com\n600000001\nCalle 1\n";
            System.setIn(new java.io.ByteArrayInputStream(input.getBytes()));

            assertDoesNotThrow(() -> GestorClientes.añadirCliente());
        }
    }

// ══════════════════════════════════════
// modificarCliente
// ══════════════════════════════════════

    @Test
    void testModificarCliente_exitoso() throws Exception {
        Connection conn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);

        try (MockedStatic<GestorConexion> mc = mockStatic(GestorConexion.class)) {
            mc.when(GestorConexion::obtenerConexion).thenReturn(conn);
            when(conn.prepareStatement(anyString())).thenReturn(ps);
            when(ps.executeUpdate()).thenReturn(1);

            String input = "1\n2\nPedro\n";
            System.setIn(new java.io.ByteArrayInputStream(input.getBytes()));

            assertDoesNotThrow(() -> GestorClientes.modificarCliente());
        }
    }

    @Test
    void testModificarCliente_sqlException() throws Exception {
        Connection conn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);

        try (MockedStatic<GestorConexion> mc = mockStatic(GestorConexion.class)) {
            mc.when(GestorConexion::obtenerConexion).thenReturn(conn);
            when(conn.prepareStatement(anyString())).thenReturn(ps);
            when(ps.executeUpdate()).thenThrow(new SQLException("Error DB"));

            String input = "1\n2\nPedro\n";
            System.setIn(new java.io.ByteArrayInputStream(input.getBytes()));

            assertDoesNotThrow(() -> GestorClientes.modificarCliente());
        }
    }

}
