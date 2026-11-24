package br.edu.ifg.luziana.pw.model.bo;

import br.edu.ifg.luziana.pw.model.dao.PerfilUsuarioDAO;
import br.edu.ifg.luziana.pw.model.dto.PerfilUsuarioDTO;
import br.edu.ifg.luziana.pw.model.entity.PerfilUsuario;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;

import java.util.List;

@RequestScoped
public class PerfilUsuarioBO {

    @Inject
    PerfilUsuarioDAO dao;

    @Transactional
    public Response salvar(PerfilUsuarioDTO dto) {
        PerfilUsuario entity = new PerfilUsuario();
        entity.setId(dto.getId());
        entity.setNome(dto.getNome());

        if (entity.getId() == null) {
            dao.insert(entity);
            dto.setId(entity.getId());
        } else {
            dao.update(entity);
        }

        return Response.ok(dto).build();
    }

    public Response listar() {
        List<PerfilUsuarioDTO> lista = dao.listarTodos();
        return Response.ok(lista).build();
    }
}
