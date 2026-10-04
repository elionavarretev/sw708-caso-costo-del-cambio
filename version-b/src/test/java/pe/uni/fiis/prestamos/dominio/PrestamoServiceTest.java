package pe.uni.fiis.prestamos.dominio;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/** Prueba de dominio: corre sin infraestructura. El doble vive acá mismo. */
class PrestamoServiceTest {

    private final LocalDate hoy = LocalDate.of(2026, 10, 5);

    /** Repositorio de mentira, en tres líneas. No hace falta base de datos. */
    static class RepositorioFalso implements RepositorioDePrestamos {
        final List<Prestamo> filas = new ArrayList<>();
        public void guardar(Prestamo p) { filas.removeIf(x -> x.id().equals(p.id())); filas.add(p); }
        public List<Prestamo> todos() { return filas; }
        public List<Prestamo> deAlumno(String c) { return filas.stream().filter(p -> p.codigoAlumno().equals(c)).toList(); }
        public Prestamo porId(String id) { return filas.stream().filter(p -> p.id().equals(id)).findFirst().orElse(null); }
    }

    @Test
    void presta_un_equipo_libre() {
        PrestamoService s = new PrestamoService(new RepositorioFalso());
        assertNotNull(s.prestar("20231234", "proyector-01", hoy));
        assertEquals(1, s.prestamosDe("20231234").size());
    }

    @Test
    void no_presta_un_equipo_que_ya_esta_prestado() {
        PrestamoService s = new PrestamoService(new RepositorioFalso());
        s.prestar("20231234", "proyector-01", hoy);
        assertThrows(IllegalStateException.class, () -> s.prestar("20235678", "proyector-01", hoy));
    }

    @Test
    void el_urgente_tambien_exige_quien_autoriza() {
        PrestamoService s = new PrestamoService(new RepositorioFalso());
        assertThrows(IllegalArgumentException.class, () -> s.prestarUrgente("20231234", "laptop-03", "", hoy));
    }

    @Test
    void al_devolver_el_equipo_queda_libre() {
        PrestamoService s = new PrestamoService(new RepositorioFalso());
        String id = s.prestar("20231234", "laptop-03", hoy);
        s.devolver(id);
        assertDoesNotThrow(() -> s.prestar("20235678", "laptop-03", hoy));
    }
}
