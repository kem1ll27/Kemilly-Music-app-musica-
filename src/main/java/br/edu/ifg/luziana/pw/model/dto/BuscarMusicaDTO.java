package br.edu.ifg.luziana.pw.model.dto;

public class BuscarMusicaDTO {

    private String texto;
    private String email; // email de quem busca, pra registrar no log

    public BuscarMusicaDTO() {
    }

    public BuscarMusicaDTO(String texto, String email) {
        this.texto = texto;
        this.email = email;
    }

    public String getTexto() { return texto; }
    public void setTexto(String texto) { this.texto = texto; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
