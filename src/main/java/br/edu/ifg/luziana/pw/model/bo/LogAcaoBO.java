package br.edu.ifg.luziana.pw.model.bo;

import br.edu.ifg.luziana.pw.model.dao.LogAcaoDAO;
import br.edu.ifg.luziana.pw.model.dto.LogAcaoDTO;
import br.edu.ifg.luziana.pw.model.entity.LogAcao;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;

import java.util.List;

@RequestScoped
public class LogAcaoBO {

    @Inject
    LogAcaoDAO dao;

    @Transactional
    public void registrar(String acao, String usuarioNome, String usuarioEmail) {
        LogAcao log = new LogAcao(acao, usuarioNome, usuarioEmail);
        dao.insert(log);
    }

    public Response listar() {
        List<LogAcaoDTO> lista = dao.listarTodos();
        return Response.ok(lista).build();
    }
}
