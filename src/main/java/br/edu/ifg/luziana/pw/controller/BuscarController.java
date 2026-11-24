package br.edu.ifg.luziana.pw.controller;

import br.edu.ifg.luziana.pw.model.bo.LogAcaoBO;
import br.edu.ifg.luziana.pw.model.dao.UsuarioDAO;
import br.edu.ifg.luziana.pw.model.dto.BuscarMusicaDTO;
import br.edu.ifg.luziana.pw.model.entity.Usuario;
import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.stream.Collectors;

@Path("/buscar")
public class BuscarController {

    // Catálogo de músicas usado só pra busca por texto.
    // O catálogo "de verdade" (Home/Biblioteca) fica na tabela Musica, gerenciado pelo admin.
    private static final List<String> MUSICAS = List.of(
            "Duro Igual Concreto - 1Kilo",
            "Cartas - VND",
            "Pq Vc Não Me Liga? - NandaTsunami",
            "Gin Com Suco De Laranja - Ebony",
            "Sob a luz do seu olhar - AJULIACOSTA",
            "Nights - Frank Ocean",
            "8x5 - Slipmami",
            "Oi Linda - NandaTsunami",
            "Quero saber - AJULIACOSTA",
            "Hora H - BUDAH",
            "Pensamentos Intrusivos - Ebony",
            "Saudade - Luiz Lins",
            "Como Tudo Deve Ser - Charlie Brown Jr",
            "Tudo Vai Dar Certo - Natiruts"
    );

    @Inject
    LogAcaoBO logBo;

    @Inject
    UsuarioDAO usuarioDao;

    @CheckedTemplate
    public static class Templates {
        public static native TemplateInstance buscar();
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance buscar() {
        return Templates.buscar();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscar(BuscarMusicaDTO dto) {
        String texto = dto.getTexto() == null ? "" : dto.getTexto().toLowerCase().trim();

        List<String> resultado = MUSICAS.stream()
                .filter(m -> m.toLowerCase().contains(texto))
                .collect(Collectors.toList());

        if (dto.getEmail() != null) {
            Usuario usuario = usuarioDao.buscarPorEmail(dto.getEmail());
            if (usuario != null) {
                logBo.registrar("Buscou: " + texto, usuario.getNome(), usuario.getEmail());
            }
        }

        return Response.ok(resultado).build();
    }
}
