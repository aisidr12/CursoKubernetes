package org.aisidro.springcloud.msvc.cursos.msvccursos.entity;

import org.aisidro.springcloud.msvc.cursos.msvccursos.models.Usuario;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cursos")
public class Curso {

    public Curso(){
        cursoUsuarios = new ArrayList<>();
        usuarios = new ArrayList<>();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String nombre;

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    @JoinColumn(name= "curso_id")
    private List<CursoUsuario> cursoUsuarios;



    @Transient
    private List<Usuario>usuarios;


    public void addCursoUsuario(CursoUsuario cursoUsuario){
        cursoUsuarios.add(cursoUsuario);
    }

    //Aqui al momento de borrar, va ir mirando instancia por instancia
    //para que no suceda eso, se modifica el metodo equals para que compare
    //unicamente por el Id
    public void  removeCursoUsuario(CursoUsuario cursoUsuario){
        cursoUsuarios.remove(cursoUsuario);

    }
    public List<CursoUsuario> getCursoUsuarios() {
        return cursoUsuarios;
    }

    public void setCursoUsuarios(List<CursoUsuario> cursoUsuarios) {
        this.cursoUsuarios = cursoUsuarios;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
}
