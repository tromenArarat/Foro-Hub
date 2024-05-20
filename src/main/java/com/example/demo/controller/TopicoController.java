package com.example.demo.controller;

import com.example.demo.domain.topicos.*;
import com.example.demo.domain.usuarios.UsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/topicos")
@SecurityRequirement(name = "bearer-key")
public class TopicoController {
    @Autowired
    private CrudTopicoService crudTopicoService;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private TopicoRepository topicoRepository;

    @PostMapping
    @Transactional
    public ResponseEntity crear(@RequestBody @Valid DatosCrearTopico datos,
                                UriComponentsBuilder uriComponentsBuilder){
        var response = crudTopicoService.crear(datos);

        URI url = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(url).body(datos);

    }

    /*

   Registro de un nuevo tópico

    La API debe contar con un endpoint (punto final) para el registro de
    tópicos, y debe aceptar solicitudes del tipo POST para la URI /tópicos.

    Los datos del tópico (título, mensaje, autor y curso) deben ser enviados
    en el cuerpo de la solicitud, en formato JSON.

        → No olvides utilizar la anotación @RequestBody para que tu
        proyecto Spring reciba correctamente los datos del cuerpo de
        la solicitud.

        → Además, recuerda que el tópico debe ser guardado en la base
        de datos construida para el proyecto, así que aquí tienes el
        recordatorio de utilizar el método save del JpaRepository para
        realizar la persistencia de los datos del tópico creado.

    Sugerencia: para ayudar en la validación de los datos, intenta
    utilizar la anotación Java integrada en Spring @Valid.

    Reglas de negocio
    - Todos los campos son obligatorios, por lo tanto, es necesario
    verificar si todos los campos se están ingresando correctamente.
    - La API no debe permitir el registro de tópicos duplicados (con
    el mismo título y mensaje).

    MODELO A SEGUIR
    @PostMapping
    public ResponseEntity<DatosRespuestaMedico> registrarMedico(@RequestBody @Valid DatosRegistroMedico datosRegistroMedico,
                                                                UriComponentsBuilder uriComponentsBuilder) {
        Medico medico = medicoRepository.save(new Medico(datosRegistroMedico));
        DatosDireccion direccion = new DatosDireccion(
                datosRegistroMedico.direccion().calle(),
                datosRegistroMedico.direccion().numero(),
                datosRegistroMedico.direccion().complemento(),
                datosRegistroMedico.direccion().ciudad()
        );
        DatosRespuestaMedico datosRespuestaMedico = new DatosRespuestaMedico(
                medico.getId(),
                medico.getNombre(),
                medico.getEmail(),
                medico.getTelefono(),
                medico.getEspecialidad().toString(),
                direccion
        );
        //URI url = "http..."+ medico.getId();
        URI url = uriComponentsBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
        return ResponseEntity.created(url).body(datosRespuestaMedico);
    }
     */

    @GetMapping("/topicos")
    @Operation(
            summary = "Devuelve todos los tópicos",
            description = "Trae todos los tópicos de la base de datos ordenados por fecha.",
            tags = {"topico", "get"}
    )
    public ResponseEntity<Page<DatosListadoTopico>> listar(@PageableDefault(size=10, sort = "fecha", direction = Sort.Direction.ASC) Pageable paginacion) {
        return ResponseEntity.ok(topicoRepository.findAll(paginacion).map(DatosListadoTopico::new));
    }

    @GetMapping("/topicos/filtrar")
    @Operation(
            summary = "Devuelve todos los tópicos ordenados por curso",
            description = "Trae todos los tópicos de la base de datos ordenados por curso.",
            tags = {"topico", "get"}
    )
    public ResponseEntity<Page<DatosListadoTopico>> listarPorNombreDeCurso(@PageableDefault(size=10, sort = "nombreCurso", direction = Sort.Direction.ASC) Pageable paginacion) {
        return ResponseEntity.ok(topicoRepository.findAll(paginacion).map(DatosListadoTopico::new));
    }

    @GetMapping("/topicos/filtrar2")
    @Operation(
            summary = "Devuelve todos los tópicos ordenados por año",
            description = "Trae todos los tópicos de la base de datos ordenados por curso.",
            tags = {"topico", "get"}
    )
    public ResponseEntity<Page<DatosListadoTopico>> listarPorAnio(
            @RequestParam int year,
            @PageableDefault(size = 10, sort = "fecha", direction = Sort.Direction.ASC) Pageable paginacion) {
        Page<Topico> topicos = topicoRepository.findByFechaYear(year, paginacion);
        Page<DatosListadoTopico> datosListadoTopicos = topicos.map(DatosListadoTopico::new);
        return ResponseEntity.ok(datosListadoTopicos);
    }
    /*
    @GetMapping("/{id}")
    public ResponseEntity<DatosRespuestaMedico> retornaDatosMedico(@PathVariable Long id){
        Medico medico = medicoRepository.getReferenceById(id);
        var datosMedico = new DatosRespuestaMedico(
                medico.getId(),
                medico.getNombre(),
                medico.getEmail(),
                medico.getTelefono(),
                medico.getEspecialidad().toString(),
                new DatosDireccion(
                        medico.getDireccion().getCalle(),
                        medico.getDireccion().getNumero(),
                        medico.getDireccion().getComplemento(),
                        medico.getDireccion().getCiudad()

                ));
        return ResponseEntity.ok(datosMedico);
    }

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
