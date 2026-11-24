package br.edu.ifg.luziana.pw.controller;

import br.edu.ifg.luziana.pw.model.bo.PerfilUsuarioBO;
import br.edu.ifg.luziana.pw.model.dto.PerfilUsuarioDTO;
import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/perfil")
public class PerfilController {

    @Inject
    PerfilUsuarioBO bo;

    @CheckedTemplate
    public static class Templates {
        public static native TemplateInstance perfil();
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance perfil() {
        return Templates.perfil();
    }

    @Path("salvar")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response salvar(PerfilUsuarioDTO dto) {
        return bo.salvar(dto);
    }

    @Path("listar")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listar() {
        return bo.listar();
    }
}
