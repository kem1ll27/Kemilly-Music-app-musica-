package br.edu.ifg.luziana.pw.model.bo;

import br.edu.ifg.luziana.pw.model.dao.MusicaDAO;
import br.edu.ifg.luziana.pw.model.dto.MusicaDTO;
import br.edu.ifg.luziana.pw.model.entity.Musica;
import br.edu.ifg.luziana.pw.model.entity.Usuario;
import br.edu.ifg.luziana.pw.model.dao.UsuarioDAO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;

@RequestScoped
public class MusicaBO {

    @Inject
    MusicaDAO dao;

    @Inject
    UsuarioDAO usuarioDao;

    @Inject
    UsuarioBO usuarioBo;

    @Inject
    LogAcaoBO logBo;

    public Response listarPorCategoria(String categoria) {
        return Response.ok(dao.listarPorCategoria(categoria)).build();
    }

    public Response listarTodas() {
        return Response.ok(dao.listarTodas()).build();
    }

    @Transactional
    public Response adicionar(MusicaDTO dto, String emailAdmin) {
        if (!usuarioBo.isAdmin(emailAdmin)) {
            return Response.status(Response.Status.FORBIDDEN)
                    .entity("Só o administrador pode adicionar músicas")
                    .build();
        }

        if (dto.getTitulo() == null || dto.getTitulo().isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Título é obrigatório").build();
        }

        Musica musica = new Musica(dto.getTitulo(), dto.getArtista(), dto.getImagemUrl(), dto.getLinkUrl(), dto.getCategoria());
        dao.insert(musica);

        Usuario admin = usuarioDao.buscarPorEmail(emailAdmin);
        if (admin != null) {
            logBo.registrar("Adicionou música: " + dto.getTitulo(), admin.getNome(), admin.getEmail());
        }

        return Response.ok().build();
    }

    @Transactional
    public Response remover(Integer id, String emailAdmin) {
        if (!usuarioBo.isAdmin(emailAdmin)) {
            return Response.status(Response.Status.FORBIDDEN)
                    .entity("Só o administrador pode remover músicas")
                    .build();
        }

        Musica musica = dao.buscarPorId(id);
        if (musica == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        String titulo = musica.getTitulo();
        dao.delete(musica);

        Usuario admin = usuarioDao.buscarPorEmail(emailAdmin);
        if (admin != null) {
            logBo.registrar("Removeu música: " + titulo, admin.getNome(), admin.getEmail());
        }

        return Response.ok().build();
    }
}
