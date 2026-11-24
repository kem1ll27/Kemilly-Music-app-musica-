package br.edu.ifg.luziana.pw.model.bo;

import br.edu.ifg.luziana.pw.model.dao.PlaylistDAO;
import br.edu.ifg.luziana.pw.model.dao.UsuarioDAO;
import br.edu.ifg.luziana.pw.model.dto.PlaylistDTO;
import br.edu.ifg.luziana.pw.model.entity.Playlist;
import br.edu.ifg.luziana.pw.model.entity.Usuario;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;

@RequestScoped
public class PlaylistBO {

    @Inject
    PlaylistDAO dao;

    @Inject
    UsuarioDAO usuarioDao;

    @Inject
    UsuarioBO usuarioBo;

    @Inject
    LogAcaoBO logBo;

    public Response listarTodas() {
        return Response.ok(dao.listarTodas()).build();
    }

    @Transactional
    public Response adicionar(PlaylistDTO dto, String emailAdmin) {
        if (!usuarioBo.isAdmin(emailAdmin)) {
            return Response.status(Response.Status.FORBIDDEN)
                    .entity("Só o administrador pode adicionar playlists")
                    .build();
        }

        if (dto.getTitulo() == null || dto.getTitulo().isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Título é obrigatório").build();
        }

        Playlist playlist = new Playlist(dto.getTitulo(), dto.getDescricao(), dto.getImagemUrl(), dto.getLinkUrl());
        dao.insert(playlist);

        Usuario admin = usuarioDao.buscarPorEmail(emailAdmin);
        if (admin != null) {
            logBo.registrar("Adicionou playlist: " + dto.getTitulo(), admin.getNome(), admin.getEmail());
        }

        return Response.ok().build();
    }

    @Transactional
    public Response remover(Integer id, String emailAdmin) {
        if (!usuarioBo.isAdmin(emailAdmin)) {
            return Response.status(Response.Status.FORBIDDEN)
                    .entity("Só o administrador pode remover playlists")
                    .build();
        }

        Playlist playlist = dao.buscarPorId(id);
        if (playlist == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        String titulo = playlist.getTitulo();
        dao.delete(playlist);

        Usuario admin = usuarioDao.buscarPorEmail(emailAdmin);
        if (admin != null) {
            logBo.registrar("Removeu playlist: " + titulo, admin.getNome(), admin.getEmail());
        }

        return Response.ok().build();
    }
}
