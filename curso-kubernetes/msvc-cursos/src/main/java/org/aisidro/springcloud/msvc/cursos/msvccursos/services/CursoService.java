package org.aisidro.springcloud.msvc.cursos.msvccursos.services;

import org.aisidro.springcloud.msvc.cursos.msvccursos.entity.Curso;
import org.aisidro.springcloud.msvc.cursos.msvccursos.models.Usuario;

import java.util.List;
import java.util.Optional;

public interface CursoService {
    List<Curso> listar();
    Optional<Curso>porId(Long id);

    Optional<Curso>porIdConUsuarios(Long id);
    Curso guardar(Curso curso);
    void eliminar(Long id);

    void eliminarCursoUsuarioPorId(Long id);
    Optional<Usuario>asignarUsuarioCurso(Usuario usuario, Long cursoId);
    Optional<Usuario>crearUsuario(Usuario usuario, Long cursoId);
    Optional<Usuario>eliminarUsuario(Usuario usuario, Long idCurso);
}
