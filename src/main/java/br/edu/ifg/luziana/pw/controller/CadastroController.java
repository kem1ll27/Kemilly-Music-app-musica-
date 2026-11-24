package br.edu.ifg.luziana.pw.controller;

import br.edu.ifg.luziana.pw.model.bo.UsuarioBO;
import br.edu.ifg.luziana.pw.model.dto.CadastroDTO;
import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/")
public class CadastroController {

    @Inject
    UsuarioBO bo;

    @CheckedTemplate
    public static class Templates {
        public static native TemplateInstance cadastro();
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance cadastro() {
        return Templates.cadastro();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response cadastrar(CadastroDTO dto) {
        return bo.cadastrar(dto);
    }
}
