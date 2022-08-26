package org.aisidro.springcloud.msvc.cursos.msvccursos.services;

import org.aisidro.springcloud.msvc.cursos.msvccursos.clients.UsuarioClientRest;
import org.aisidro.springcloud.msvc.cursos.msvccursos.entity.Curso;
import org.aisidro.springcloud.msvc.cursos.msvccursos.entity.CursoUsuario;
import org.aisidro.springcloud.msvc.cursos.msvccursos.models.Usuario;
import org.aisidro.springcloud.msvc.cursos.msvccursos.repositories.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CursoServiceImpl implements CursoService{

    @Autowired
    private CursoRepository repository;

    @Autowired
    private UsuarioClientRest client;

    @Override
    @Transactional(readOnly = true)
    public List<Curso> listar() {
        return (List<Curso>) repository.findAll();
    }

    @Override
    @Transactional
    public Optional<Curso> porId(Long id) {
        return repository.findById(id);
    }

    @Override
    public Optional<Curso> porIdConUsuarios(Long id) {
        Optional<Curso>o = repository.findById(id);
        if(o.isPresent()){
            Curso curso = o.get();
            if(!curso.getCursoUsuarios().isEmpty()){
                List<Long>idsAlumnosCursos = curso.getCursoUsuarios().
                        stream().map(cu -> cu.getUsuarioId()).collect(Collectors.toList());
                List<Usuario>usuarios = client.obtenerAlumnosPorCurso(idsAlumnosCursos);
                curso.setUsuarios(usuarios);
            }
            return Optional.of(curso);
        }
        return Optional.empty();
    }

    @Override
    public Curso guardar(Curso curso) {
        return repository.save(curso);
    }

    @Override
    public void eliminar(Long id) {
        repository.deleteById(id);

    }

    @Override
    @Transactional
    public void eliminarCursoUsuarioPorId(Long id) {
        repository.eliminarCursoUsuarioPorId(id);
    }

    @Override
    @Transactional
    public Optional<Usuario> asignarUsuarioCurso(Usuario usuario, Long cursoId) {
        Optional<Curso> o = repository.findById(cursoId);
        if(o.isPresent()){
            Usuario usuarioMscv = client.detalle(usuario.getId());
            Curso curso = o.get();
            CursoUsuario cursoUsuario = new CursoUsuario();
            cursoUsuario.setUsuarioId(usuarioMscv.getId());
            curso.addCursoUsuario(cursoUsuario);
            repository.save(curso);
            return Optional.of(usuarioMscv);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<Usuario> crearUsuario(Usuario usuario, Long cursoId) {
        Optional<Curso> o = repository.findById(cursoId);
        if(o.isPresent()){
            Usuario usuarioNuevoMscv = client.crear(usuario);
            Curso curso = o.get();
            CursoUsuario cursoUsuario = new CursoUsuario();
            cursoUsuario.setUsuarioId(usuarioNuevoMscv.getId());
            curso.addCursoUsuario(cursoUsuario);
            repository.save(curso);
            return Optional.of(usuarioNuevoMscv);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<Usuario> eliminarUsuario(Usuario usuario, Long cursoId) {
        Optional<Curso> o = repository.findById(cursoId);
        if(o.isPresent()){
            Usuario usuarioMscv = client.detalle(usuario.getId());
            Curso curso = o.get();
            CursoUsuario cursoUsuario = new CursoUsuario();
            cursoUsuario.setUsuarioId(usuarioMscv.getId());
            curso.removeCursoUsuario(cursoUsuario);
            repository.save(curso);
            return Optional.of(usuarioMscv);
        }
        return Optional.empty();
    }
}
