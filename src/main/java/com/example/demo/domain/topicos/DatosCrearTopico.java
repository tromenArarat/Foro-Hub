package com.example.demo.domain.topicos;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DatosCrearTopico(
        @NotNull
        Long idTopico,
        Long idUsuario,
        String titulo,
        String mensaje,
        String nombreCurso,
        Estado status,
        @NotNull @Future
        LocalDateTime fecha
) {
}
