package br.edu.ifg.luziana.pw.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "perfil_usuario", uniqueConstraints = {
        @UniqueConstraint(columnNames = "nome", name = "perfil_usuario_nome_uk")
})
public class PerfilUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String nome;

    public PerfilUsuario() {
    }

    public PerfilUsuario(String nome) {
        this.nome = nome;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
}
