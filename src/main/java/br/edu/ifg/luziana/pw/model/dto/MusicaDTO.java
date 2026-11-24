package br.edu.ifg.luziana.pw.model.dto;

public class MusicaDTO {

    private Integer id;
    private String titulo;
    private String artista;
    private String imagemUrl;
    private String linkUrl;
    private String categoria; // "RECENTE" ou "MAIS_OUVIDA"

    public MusicaDTO() {
    }

    public MusicaDTO(Integer id, String titulo, String artista, String imagemUrl, String linkUrl, String categoria) {
        this.id = id;
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
