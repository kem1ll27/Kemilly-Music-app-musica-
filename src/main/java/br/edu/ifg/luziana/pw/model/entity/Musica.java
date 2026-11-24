package br.edu.ifg.luziana.pw.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "musica")
public class Musica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String titulo;
    String artista;
    String imagemUrl;
    String linkUrl;

    // "RECENTE" (mostra na Home) ou "MAIS_OUVIDA" (mostra na Biblioteca)
    String categoria;

    public Musica() {
    }

    public Musica(String titulo, String artista, String imagemUrl, String linkUrl, String categoria) {
        this.titulo = titulo;
        this.artista = artista;
        this.imagemUrl = imagemUrl;
        this.linkUrl = linkUrl;
        this.categoria = categoria;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getArtista() { return artista; }
    public void setArtista(String artista) { this.artista = artista; }
    public String getImagemUrl() { return imagemUrl; }
    public void setImagemUrl(String imagemUrl) { this.imagemUrl = imagemUrl; }
    public String getLinkUrl() { return linkUrl; }
    public void setLinkUrl(String linkUrl) { this.linkUrl = linkUrl; }
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
}
