package pe.uni.fiis.prestamos;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class PrestamoServiceTest {

    private final LocalDate hoy = LocalDate.of(2026, 10, 5);

    @Test
    void presta_un_equipo_libre() {
        PrestamoService s = new PrestamoService(new BaseDeDatos());
        String id = s.prestar("20231234", "proyector-01", hoy);
        assertNotNull(id);
        assertEquals(1, s.prestamosDe("20231234").size());
    }

    @Test
    void no_presta_un_equipo_que_ya_esta_prestado() {
        PrestamoService s = new PrestamoService(new BaseDeDatos());
        s.prestar("20231234", "proyector-01", hoy);
        assertThrows(IllegalStateException.class, () -> s.prestar("20235678", "proyector-01", hoy));
    }

    @Test
    void el_urgente_tambien_exige_quien_autoriza() {
        PrestamoService s = new PrestamoService(new BaseDeDatos());
        assertThrows(IllegalArgumentException.class, () -> s.prestarUrgente("20231234", "laptop-03", "", hoy));
    }

    @Test
    void al_devolver_el_equipo_queda_libre() {
        PrestamoService s = new PrestamoService(new BaseDeDatos());
        String id = s.prestar("20231234", "laptop-03", hoy);
        s.devolver(id);
        assertDoesNotThrow(() -> s.prestar("20235678", "laptop-03", hoy));
    }
}
