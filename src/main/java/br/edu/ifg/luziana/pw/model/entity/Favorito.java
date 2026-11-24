package br.edu.ifg.luziana.pw.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "favorito", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"usuario_id", "musica"}, name = "favorito_usuario_musica_uk")
})
public class Favorito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    Usuario usuario;

    String musica;

    public Favorito() {
    }

    public Favorito(Usuario usuario, String musica) {
        this.usuario = usuario;
        this.musica = musica;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public String getMusica() { return musica; }
    public void setMusica(String musica) { this.musica = musica; }
}
