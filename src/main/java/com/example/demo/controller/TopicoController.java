package com.example.demo.controller;

import com.example.demo.domain.topicos.CrudTopicoService;
import com.example.demo.domain.topicos.DatosCrearTopico;
import com.example.demo.domain.topicos.Topico;
import com.example.demo.domain.topicos.TopicoRepository;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.List;

@RestController
@RequestMapping("/topicos")
@SecurityRequirement(name = "bearer-key")
public class TopicoController {
    @Autowired
    private CrudTopicoService crudTopicoService;

    @Autowired
    private TopicoRepository topicoRepository;

    @PostMapping
    @Transactional
    public ResponseEntity crear(@RequestBody @Valid DatosCrearTopico datos){
        var response = crudTopicoService.crear(datos);
        return ResponseEntity.ok(response);
    }
    @GetMapping
    @Operation(
            summary = "Retrieve all topics",
            description = "Fetches all topics from the repository",
            tags = {"topico", "get"}
    )
    public ResponseEntity<List<Topico>> listar() {
        var topicos = topicoRepository.findAll();
        return ResponseEntity.ok(topicos);
    }

    /*
    @DeleteMapping
    @Transactional
    @Operation(
            summary = "cancela una consulta de la agenda",
            description = "requiere motivo",
            tags = {"consulta","delete"}
    )
    public ResponseEntity eliminar(@RequestBody @Valid DatosCancelamientoConsulta datos) {
        agendaConsultaService.cancelar(datos);
        var consulta = repository.getReferenceById(datos.idConsulta());
        consulta.cancelar(datos.motivo());
        return ResponseEntity.noContent().build();
    }
     */
}
