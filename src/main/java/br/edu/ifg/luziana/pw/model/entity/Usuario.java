package br.edu.ifg.luziana.pw.model.entity;

import br.edu.ifg.luziana.pw.model.dto.CadastroDTO;
import jakarta.persistence.*;

@Entity
@Table(name = "usuario", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email", name = "usuario_email_uk")
})
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String nome;
    String email;
    String senha;

    @ManyToOne
    @JoinColumn(name = "perfil_usuario_id")
    PerfilUsuario perfil;

    public Usuario() {
    }

    public Usuario(CadastroDTO dto) {
        this.nome = dto.getNome();
        this.email = dto.getEmail();
        this.senha = dto.getSenha();
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
    public PerfilUsuario getPerfil() { return perfil; }
    public void setPerfil(PerfilUsuario perfil) { this.perfil = perfil; }
}
