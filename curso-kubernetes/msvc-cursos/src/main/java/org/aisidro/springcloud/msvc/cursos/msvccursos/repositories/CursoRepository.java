package org.aisidro.springcloud.msvc.cursos.msvccursos.repositories;

import org.aisidro.springcloud.msvc.cursos.msvccursos.entity.Curso;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CursoRepository extends CrudRepository<Curso,Long> {

    @Modifying
    @Query("delete from CursoUsuario cu where cu.usuarioId=?1")
    public void eliminarCursoUsuarioPorId(Long id);

}
