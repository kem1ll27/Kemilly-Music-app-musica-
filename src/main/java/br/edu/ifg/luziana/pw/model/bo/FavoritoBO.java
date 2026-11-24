package br.edu.ifg.luziana.pw.model.bo;

import br.edu.ifg.luziana.pw.model.dao.FavoritoDAO;
import br.edu.ifg.luziana.pw.model.dao.UsuarioDAO;
import br.edu.ifg.luziana.pw.model.dto.FavoritoDTO;
import br.edu.ifg.luziana.pw.model.entity.Favorito;
import br.edu.ifg.luziana.pw.model.entity.Usuario;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;

@RequestScoped
public class FavoritoBO {

    @Inject
    FavoritoDAO dao;

    @Inject
    UsuarioDAO usuarioDao;

    @Inject
    LogAcaoBO logBo;

    @Transactional
    public Response favoritar(FavoritoDTO dto) {
        Usuario usuario = usuarioDao.buscarPorEmail(dto.getEmail());
        if (usuario == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Usuário não encontrado").build();
        }

        Favorito existente = dao.buscar(usuario.getId(), dto.getMusica());
        if (existente == null) {
            dao.insert(new Favorito(usuario, dto.getMusica()));
            logBo.registrar("Favoritou: " + dto.getMusica(), usuario.getNome(), usuario.getEmail());
        }

        return Response.ok().build();
    }

    @Transactional
    public Response desfavoritar(FavoritoDTO dto) {
        Usuario usuario = usuarioDao.buscarPorEmail(dto.getEmail());
        if (usuario == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Usuário não encontrado").build();
        }

        Favorito existente = dao.buscar(usuario.getId(), dto.getMusica());
        if (existente != null) {
            dao.delete(existente);
            logBo.registrar("Desfavoritou: " + dto.getMusica(), usuario.getNome(), usuario.getEmail());
        }

        return Response.ok().build();
    }

    public Response listar(String email) {
        Usuario usuario = usuarioDao.buscarPorEmail(email);
        if (usuario == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Usuário não encontrado").build();
        }

        return Response.ok(dao.listarMusicasPorUsuario(usuario.getId())).build();
    }
}
