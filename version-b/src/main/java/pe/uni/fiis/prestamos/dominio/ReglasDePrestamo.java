package pe.uni.fiis.prestamos.dominio;

import java.util.List;

/** Las reglas del préstamo, en un solo lugar. Cualquier forma de prestar
 *  pasa por acá, así que una regla nueva se escribe una sola vez. */
public final class ReglasDePrestamo {

    private ReglasDePrestamo() {
    }

    public static void validar(String codigoAlumno, String equipo, List<Prestamo> yaRegistrados) {
        if (codigoAlumno == null || codigoAlumno.isBlank()) {
            throw new IllegalArgumentException("El código del alumno es obligatorio");
        }
        if (equipo == null || equipo.isBlank()) {
            throw new IllegalArgumentException("El equipo es obligatorio");
        }
        boolean ocupado = yaRegistrados.stream().anyMatch(p -> p.esDelEquipo(equipo) && p.sigueAfuera());
        if (ocupado) {
            throw new IllegalStateException("El equipo ya está prestado: " + equipo);
        }
    }
}
