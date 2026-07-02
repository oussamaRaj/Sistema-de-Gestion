import ConexionDB.GestorConexion;
import logica.GestorProductos;
import model.Producto;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.*;

public class GestorProductosTest {

    private static final int CODIGO_TEST = 9999;

    // ─────────────────────────────────────────
    // SETUP — insertar producto de test antes de todo
    // ─────────────────────────────────────────
    @BeforeAll
    static void setUp() throws SQLException {
        String sql = "INSERT INTO producto (codigo, tipo, nombre, precio, descripcion, esDisponible) " +
                "VALUES (?, 'Dispositivo', 'ProductoTest', 0.8, 'Descripcion test', 'Sí')";
        try (Connection conn = GestorConexion.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, CODIGO_TEST);
            pstmt.executeUpdate();
        }
    }

    // ─────────────────────────────────────────
    // TEARDOWN — eliminar producto de test al final
    // ─────────────────────────────────────────
    @AfterAll
    static void tearDown() throws SQLException {
        String sql = "DELETE FROM producto WHERE codigo = ?";
        try (Connection conn = GestorConexion.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, CODIGO_TEST);
            pstmt.executeUpdate();
        }
    }

    @Test @Order(1)
    public void testBuscarProductoExistente() {
        Producto p = GestorProductos.buscarProductoPorCodigo(CODIGO_TEST);
        assertNotNull(p, "El producto debería existir");
    }

    @Test @Order(2)
    public void testBuscarProductoCodigo() {
        Producto p = GestorProductos.buscarProductoPorCodigo(CODIGO_TEST);
        assertNotNull(p);
        assertEquals(CODIGO_TEST, p.getCodigo());
    }

    @Test @Order(3)
    public void testBuscarProductoNombre() {
        Producto p = GestorProductos.buscarProductoPorCodigo(CODIGO_TEST);
        assertNotNull(p);
        assertEquals("ProductoTest", p.getNombre());
    }

    @Test @Order(4)
    public void testBuscarProductoPrecio() {
        Producto p = GestorProductos.buscarProductoPorCodigo(CODIGO_TEST);
        assertNotNull(p);
        assertEquals(0.8, p.getPrecio(), 0.001);
    }

    @Test @Order(5)
    public void testBuscarProductoTipo() {
        Producto p = GestorProductos.buscarProductoPorCodigo(CODIGO_TEST);
        assertNotNull(p);
        assertEquals("Dispositivo", p.getTipo());
    }

    @Test @Order(6)
    public void testBuscarProductoDisponible() {
        Producto p = GestorProductos.buscarProductoPorCodigo(CODIGO_TEST);
        assertNotNull(p);
        assertEquals("Sí", p.getEsDisponible());
    }

    @Test @Order(7)
    public void testBuscarProductoNoExistente() {
        Producto p = GestorProductos.buscarProductoPorCodigo(99999);
        assertNull(p, "El producto no debería existir");
    }

    @Test @Order(8)
    public void testBuscarProductoCodigoNegativo() {
        Producto p = GestorProductos.buscarProductoPorCodigo(-1);
        assertNull(p, "No debería existir producto con codigo negativo");
    }

    @Test @Order(9)
    public void testPrecioNoNegativo() {
        Producto p = GestorProductos.buscarProductoPorCodigo(CODIGO_TEST);
        assertNotNull(p);
        assertTrue(p.getPrecio() >= 0, "El precio no puede ser negativo");
    }

    @Test @Order(10)
    public void testNombreNoNullNiVacio() {
        Producto p = GestorProductos.buscarProductoPorCodigo(CODIGO_TEST);
        assertNotNull(p);
        assertNotNull(p.getNombre(), "El nombre no puede ser null");
        assertFalse(p.getNombre().isEmpty(), "El nombre no puede estar vacío");
    }
}
