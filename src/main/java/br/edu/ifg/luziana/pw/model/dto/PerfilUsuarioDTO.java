package br.edu.ifg.luziana.pw.model.dto;

public class PerfilUsuarioDTO {

    private Integer id;
    private String nome;

    public PerfilUsuarioDTO() {
    }

    // Precisa bater com o "select new ...PerfilUsuarioDTO(pu.id, pu.nome)" do DAO
    public PerfilUsuarioDTO(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
}
