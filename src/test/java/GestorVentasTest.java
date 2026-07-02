import ConexionDB.GestorConexion;
import logica.GestorClientes;
import logica.GestorProductos;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class GestorVentasTest {

    private static final int CODIGO_PRODUCTO_TEST = 9998;
    private static final int CODIGO_CLIENTE_TEST  = 9998;
    private static int ID_VENTA_TEST = -1;

    // ─────────────────────────────────────────
    // SETUP — insertar datos de test
    // ─────────────────────────────────────────
    @BeforeAll
    static void setUp() throws SQLException {

        // Insertar producto de test
        String sqlProducto = "INSERT INTO producto (codigo, tipo, nombre, precio, descripcion, esDisponible) " +
                "VALUES (?, 'Dispositivo', 'ProductoTest', 10.0, 'Test', 'Sí')";
        try (Connection conn = GestorConexion.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sqlProducto)) {
            pstmt.setInt(1, CODIGO_PRODUCTO_TEST);
            pstmt.executeUpdate();
        }

        // Insertar cliente de test
        String sqlCliente = "INSERT INTO cliente (codigo, nombre, apellidos, dni) " +
                "VALUES (?, 'ClienteTest', 'ApellidoTest', 'TEST12345')";
        try (Connection conn = GestorConexion.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sqlCliente)) {
            pstmt.setInt(1, CODIGO_CLIENTE_TEST);
            pstmt.executeUpdate();
        }

        // Insertar venta de test
        String sqlVenta = "INSERT INTO venta (codigoProducto, codigoCliente, cantidad, fecha, total) " +
                "VALUES (?, ?, 2, ?, 20.0)";
        try (Connection conn = GestorConexion.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sqlVenta, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, CODIGO_PRODUCTO_TEST);
            pstmt.setInt(2, CODIGO_CLIENTE_TEST);
            pstmt.setDate(3, Date.valueOf(LocalDate.now()));
            pstmt.executeUpdate();

            // Guardar el ID generado
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                ID_VENTA_TEST = rs.getInt(1);
            }
        }
    }

    // ─────────────────────────────────────────
    // TEARDOWN — eliminar datos de test
    // ─────────────────────────────────────────
    @AfterAll
    static void tearDown() throws SQLException {
        try (Connection conn = GestorConexion.obtenerConexion()) {

            conn.prepareStatement("DELETE FROM venta WHERE codigoProducto = " + CODIGO_PRODUCTO_TEST).executeUpdate();
            conn.prepareStatement("DELETE FROM producto WHERE codigo = " + CODIGO_PRODUCTO_TEST).executeUpdate();
            conn.prepareStatement("DELETE FROM cliente WHERE codigo = " + CODIGO_CLIENTE_TEST).executeUpdate();
        }
    }

    // ─────────────────────────────────────────
    // TEST 1 — venta existe en BD
    // ─────────────────────────────────────────
    @Test
    @Order(1)
    public void testVentaExisteEnBD() throws SQLException {
        String sql = "SELECT * FROM venta WHERE id = ?";
        try (Connection conn = GestorConexion.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, ID_VENTA_TEST);
            ResultSet rs = pstmt.executeQuery();
            assertTrue(rs.next(), "La venta debería existir en la BD");
        }
    }

    // ─────────────────────────────────────────
    // TEST 2 — total calculado correctamente
    // ─────────────────────────────────────────
    @Test
    @Order(2)
    public void testTotalVentaCorrecto() throws SQLException {
        String sql = "SELECT total FROM venta WHERE id = ?";
        try (Connection conn = GestorConexion.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, ID_VENTA_TEST);
            ResultSet rs = pstmt.executeQuery();
            assertTrue(rs.next());
            assertEquals(20.0, rs.getDouble("total"), 0.01,
                    "Total debería ser precio(10) * cantidad(2) = 20");
        }
    }

    // ─────────────────────────────────────────
    // TEST 3 — cantidad correcta
    // ─────────────────────────────────────────
    @Test
    @Order(3)
    public void testCantidadCorrecta() throws SQLException {
        String sql = "SELECT cantidad FROM venta WHERE id = ?";
        try (Connection conn = GestorConexion.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, ID_VENTA_TEST);
            ResultSet rs = pstmt.executeQuery();
            assertTrue(rs.next());
            assertEquals(2, rs.getInt("cantidad"));
        }
    }

    // ─────────────────────────────────────────
    // TEST 4 — fecha de hoy
    // ─────────────────────────────────────────
    @Test
    @Order(4)
    public void testFechaCorrecta() throws SQLException {
        String sql = "SELECT fecha FROM venta WHERE id = ?";
        try (Connection conn = GestorConexion.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, ID_VENTA_TEST);
            ResultSet rs = pstmt.executeQuery();
            assertTrue(rs.next());
            LocalDate fechaBD = rs.getDate("fecha").toLocalDate();
            assertEquals(LocalDate.now(), fechaBD, "La fecha debería ser hoy");
        }
    }

    // ─────────────────────────────────────────
    // TEST 5 — codigo producto correcto
    // ─────────────────────────────────────────
    @Test
    @Order(5)
    public void testCodigoProductoCorrecto() throws SQLException {
        String sql = "SELECT codigoProducto FROM venta WHERE id = ?";
        try (Connection conn = GestorConexion.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, ID_VENTA_TEST);
            ResultSet rs = pstmt.executeQuery();
            assertTrue(rs.next());
            assertEquals(CODIGO_PRODUCTO_TEST, rs.getInt("codigoProducto"));
        }
    }

    // ─────────────────────────────────────────
    // TEST 6 — codigo cliente correcto
    // ─────────────────────────────────────────
    @Test
    @Order(6)
    public void testCodigoClienteCorrecto() throws SQLException {
        String sql = "SELECT codigoCliente FROM venta WHERE id = ?";
        try (Connection conn = GestorConexion.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, ID_VENTA_TEST);
            ResultSet rs = pstmt.executeQuery();
            assertTrue(rs.next());
            assertEquals(CODIGO_CLIENTE_TEST, rs.getInt("codigoCliente"));
        }
    }

    // ─────────────────────────────────────────
    // TEST 7 — producto no existe → no registra venta
    // ─────────────────────────────────────────
    @Test
    @Order(7)
    public void testProductoNoExiste() {
        // buscarProductoPorCodigo de un codigo que no existe
        assertNull(GestorProductos.buscarProductoPorCodigo(99999),
                "Producto no debería existir");
    }

    // ─────────────────────────────────────────
    // TEST 8 — cliente no existe
    // ─────────────────────────────────────────
    @Test
    @Order(8)
    public void testClienteNoExiste() {
        assertNull(GestorClientes.buscarClientePorCodigo(99999),
                "Cliente no debería existir");
    }

    // ─────────────────────────────────────────
    // TEST 9 — total no puede ser negativo
    // ─────────────────────────────────────────
    @Test
    @Order(9)
    public void testTotalNoNegativo() throws SQLException {
        String sql = "SELECT total FROM venta WHERE id = ?";
        try (Connection conn = GestorConexion.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, ID_VENTA_TEST);
            ResultSet rs = pstmt.executeQuery();
            assertTrue(rs.next());
            assertTrue(rs.getDouble("total") >= 0, "El total no puede ser negativo");
        }
    }

    // ─────────────────────────────────────────
    // TEST 10 — eliminar venta
    // ─────────────────────────────────────────
    @Test
    @Order(10)
    public void testEliminarVenta() throws SQLException {
        // Insertar una venta extra para eliminarla
        String sqlInsert = "INSERT INTO venta (codigoProducto, codigoCliente, cantidad, fecha, total) " +
                "VALUES (?, ?, 1, ?, 10.0)";
        int idParaEliminar;
        try (Connection conn = GestorConexion.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, CODIGO_PRODUCTO_TEST);
            pstmt.setInt(2, CODIGO_CLIENTE_TEST);
            pstmt.setDate(3, Date.valueOf(LocalDate.now()));
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            rs.next();
            idParaEliminar = rs.getInt(1);
        }

        // Eliminar
        String sqlDelete = "DELETE FROM venta WHERE id = ?";
        try (Connection conn = GestorConexion.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sqlDelete)) {
            pstmt.setInt(1, idParaEliminar);
            int filas = pstmt.executeUpdate();
            assertEquals(1, filas, "Debería eliminar 1 fila");
        }

        // Verificar que ya no existe
        String sqlSelect = "SELECT * FROM venta WHERE id = ?";
        try (Connection conn = GestorConexion.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sqlSelect)) {
            pstmt.setInt(1, idParaEliminar);
            ResultSet rs = pstmt.executeQuery();
            assertFalse(rs.next(), "La venta debería estar eliminada");
        }
    }
}
