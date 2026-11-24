package br.edu.ifg.luziana.pw.model.dto;

public class RemoverUsuarioDTO {

    private String email;

    public RemoverUsuarioDTO() {
    }

    public RemoverUsuarioDTO(String email) {
        this.email = email;
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
