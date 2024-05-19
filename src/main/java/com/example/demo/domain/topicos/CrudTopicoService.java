package com.example.demo.domain.topicos;

import com.example.demo.domain.usuarios.Usuario;
import com.example.demo.domain.usuarios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CrudTopicoService {
    @Autowired
    private TopicoRepository topicoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    /*
    @Autowired
    List<ValidadorDeConsultas> validadores;
    @Autowired
    List<ValidadorCancelamientoDeConsulta> validadoresCancelamiento;
     */

    public DatosDetalleTopico crear(DatosCrearTopico datos){

        /*
        if(!topicoRepository.findById(datos.idUsuario()).isPresent()){
            throw new ValidacionDeIntegridad("Id de usuario no fue encontrado")
        }
        validadores.forEach(v->v.validar(datos));
        */

        var usuario = usuarioRepository.findById(datos.idUsuario()).get();
        var topico = new Topico(
                datos.titulo(),
                datos.mensaje(),
                datos.status(),
                usuario,
                datos.nombreCurso()
        );

        topicoRepository.save(topico);
        return new DatosDetalleTopico(topico);

    }

}
