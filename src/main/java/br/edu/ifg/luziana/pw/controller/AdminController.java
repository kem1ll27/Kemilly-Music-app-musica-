package br.edu.ifg.luziana.pw.controller;

import br.edu.ifg.luziana.pw.model.bo.LogAcaoBO;
import br.edu.ifg.luziana.pw.model.bo.UsuarioBO;
import br.edu.ifg.luziana.pw.model.dto.RemoverUsuarioDTO;
import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/admin")
public class AdminController {

    @Inject
    LogAcaoBO logBo;

    @Inject
    UsuarioBO usuarioBo;

    @CheckedTemplate
    public static class Templates {
        public static native TemplateInstance admin();
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance admin() {
        return Templates.admin();
    }

    // GET /admin/log?email=admin@x.com
    @Path("log")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarLog(@QueryParam("email") String email) {
        if (!usuarioBo.isAdmin(email)) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        return logBo.listar();
    }

    // GET /admin/usuario?email=admin@x.com
    @Path("usuario")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarUsuarios(@QueryParam("email") String email) {
        if (!usuarioBo.isAdmin(email)) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        return usuarioBo.listar();
    }

    // DELETE /admin/usuario?email=admin@x.com
    @Path("usuario")
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response removerUsuario(RemoverUsuarioDTO dto, @QueryParam("email") String email) {
        if (!usuarioBo.isAdmin(email)) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        return usuarioBo.remover(dto);
    }

    // POST /admin/promover/5?email=admin@x.com
    @Path("promover/{id}")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response promoverAdmin(@PathParam("id") Integer id, @QueryParam("email") String email) {
        if (!usuarioBo.isAdmin(email)) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        return usuarioBo.promoverAdmin(id);
    }

    // POST /admin/rebaixar/5?email=admin@x.com
    @Path("rebaixar/{id}")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response rebaixarUsuario(@PathParam("id") Integer id, @QueryParam("email") String email) {
        if (!usuarioBo.isAdmin(email)) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        return usuarioBo.rebaixarUsuario(id);
    }
}
