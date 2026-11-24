package br.edu.ifg.luziana.pw.model.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "log_acao")
public class LogAcao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String acao;
    String usuarioNome;
    String usuarioEmail;
    LocalDateTime dataHora;

    public LogAcao() {
    }

    public LogAcao(String acao, String usuarioNome, String usuarioEmail) {
        this.acao = acao;
        this.usuarioNome = usuarioNome;
        this.usuarioEmail = usuarioEmail;
        this.dataHora = LocalDateTime.now();
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getAcao() { return acao; }
    public void setAcao(String acao) { this.acao = acao; }
    public String getUsuarioNome() { return usuarioNome; }
    public void setUsuarioNome(String usuarioNome) { this.usuarioNome = usuarioNome; }
    public String getUsuarioEmail() { return usuarioEmail; }
    public void setUsuarioEmail(String usuarioEmail) { this.usuarioEmail = usuarioEmail; }
    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }
}
