package br.edu.ifg.luziana.pw.model.bo;

import br.edu.ifg.luziana.pw.model.dao.FavoritoDAO;
import br.edu.ifg.luziana.pw.model.dao.PerfilUsuarioDAO;
import br.edu.ifg.luziana.pw.model.dao.UsuarioDAO;
import br.edu.ifg.luziana.pw.model.dto.AutenticacaoDTO;
import br.edu.ifg.luziana.pw.model.dto.CadastroDTO;
import br.edu.ifg.luziana.pw.model.dto.RemoverUsuarioDTO;
import br.edu.ifg.luziana.pw.model.dto.UsuarioDTO;
import br.edu.ifg.luziana.pw.model.entity.PerfilUsuario;
import br.edu.ifg.luziana.pw.model.entity.Usuario;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.stream.Collectors;

@RequestScoped
public class UsuarioBO {

    @Inject
    UsuarioDAO dao;

    @Inject
    PerfilUsuarioDAO perfilDao;

    @Inject
    FavoritoDAO favoritoDao;

    @Inject
    LogAcaoBO logBo;

    @Transactional
    public Response cadastrar(CadastroDTO dto) {
        Usuario existente = dao.buscarPorEmail(dto.getEmail());
        if (existente != null) {
            return Response.status(Response.Status.CONFLICT)
                    .entity("E-mail já cadastrado")
                    .build();
        }

        Usuario usuario = new Usuario(dto);
        PerfilUsuario perfilUser = perfilDao.buscarPorNome("USER");
        usuario.setPerfil(perfilUser);

        dao.insert(usuario);
        logBo.registrar("Cadastro realizado", usuario.getNome(), usuario.getEmail());

        return Response.ok().build();
    }

    public Response autenticar(AutenticacaoDTO dto) {
        Usuario usuario = dao.buscarPorEmail(dto.getEmail());

        if (usuario == null || !usuario.getSenha().equals(dto.getSenha())) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("E-mail ou senha inválidos")
                    .build();
        }

        logBo.registrar("Login realizado", usuario.getNome(), usuario.getEmail());

        // Devolve o perfil de verdade (vindo do banco) pro front saber se é ADMIN
        return Response.ok(toDTO(usuario)).build();
    }

    /** Usado pelas outras camadas (Musica/Playlist/AdminController) pra checar permissão. */
    public boolean isAdmin(String email) {
        if (email == null) return false;
        Usuario usuario = dao.buscarPorEmail(email);
        return usuario != null
                && usuario.getPerfil() != null
                && "ADMIN".equals(usuario.getPerfil().getNome());
    }

    @Transactional
    public Response promoverAdmin(Integer usuarioId) {
        Usuario usuario = dao.buscarPorId(usuarioId);
        if (usuario == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        PerfilUsuario perfilAdmin = perfilDao.buscarPorNome("ADMIN");
        usuario.setPerfil(perfilAdmin);
        dao.update(usuario);
        logBo.registrar("Promovido a administrador", usuario.getNome(), usuario.getEmail());

        return Response.ok().build();
    }

    @Transactional
    public Response rebaixarUsuario(Integer usuarioId) {
        Usuario usuario = dao.buscarPorId(usuarioId);
        if (usuario == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        PerfilUsuario perfilUser = perfilDao.buscarPorNome("USER");
        usuario.setPerfil(perfilUser);
        dao.update(usuario);
        logBo.registrar("Rebaixado para usuário comum", usuario.getNome(), usuario.getEmail());

        return Response.ok().build();
    }

    public Response listar() {
        List<Usuario> usuarios = dao.listarTodos();

        List<UsuarioDTO> lista = usuarios.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());

        return Response.ok(lista).build();
    }

    @Transactional
    public Response remover(RemoverUsuarioDTO dto) {
        Usuario usuario = dao.buscarPorEmail(dto.getEmail());
        if (usuario == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        // Remove favoritos do usuário antes de apagar (evita erro de FK em favorito.usuario_id)
        favoritoDao.removerPorUsuario(usuario.getId());

        dao.delete(usuario);
        logBo.registrar("Usuário removido", usuario.getNome(), usuario.getEmail());

        return Response.ok().build();
    }

    private UsuarioDTO toDTO(Usuario u) {
        return new UsuarioDTO(
                u.getId(),
                u.getNome(),
                u.getEmail(),
                u.getPerfil() != null ? u.getPerfil().getNome() : null
        );
    }
}
