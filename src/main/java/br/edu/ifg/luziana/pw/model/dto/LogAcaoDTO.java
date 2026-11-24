package br.edu.ifg.luziana.pw.model.dto;

import java.time.LocalDateTime;

public class LogAcaoDTO {

    private String acao;
    private String usuarioNome;
    private String usuarioEmail;
    private LocalDateTime dataHora;

    public LogAcaoDTO() {
    }

    public LogAcaoDTO(String acao, String usuarioNome, String usuarioEmail, LocalDateTime dataHora) {
        this.acao = acao;
        this.usuarioNome = usuarioNome;
        this.usuarioEmail = usuarioEmail;
        this.dataHora = dataHora;
    }

    public String getAcao() { return acao; }
    public void setAcao(String acao) { this.acao = acao; }
    public String getUsuarioNome() { return usuarioNome; }
    public void setUsuarioNome(String usuarioNome) { this.usuarioNome = usuarioNome; }
    public String getUsuarioEmail() { return usuarioEmail; }
    public void setUsuarioEmail(String usuarioEmail) { this.usuarioEmail = usuarioEmail; }
    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }
}
