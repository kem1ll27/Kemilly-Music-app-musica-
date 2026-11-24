package br.edu.ifg.luziana.pw.model.dto;

public class FavoritoDTO {

    private String email;
    private String musica;

    public FavoritoDTO() {
    }

    public FavoritoDTO(String email, String musica) {
        this.email = email;
        this.musica = musica;
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getMusica() { return musica; }
    public void setMusica(String musica) { this.musica = musica; }
}
