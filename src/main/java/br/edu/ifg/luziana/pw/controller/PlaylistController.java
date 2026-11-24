package br.edu.ifg.luziana.pw.controller;

import br.edu.ifg.luziana.pw.model.bo.PlaylistBO;
import br.edu.ifg.luziana.pw.model.dto.PlaylistDTO;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/playlist")
public class PlaylistController {

    @Inject
    PlaylistBO bo;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listar() {
        return bo.listarTodas();
    }

    // POST /playlist?email=admin@x.com   (só ADMIN consegue)
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response adicionar(PlaylistDTO dto, @QueryParam("email") String email) {
        return bo.adicionar(dto, email);
    }

    // DELETE /playlist/5?email=admin@x.com   (só ADMIN consegue)
    @Path("{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response remover(@PathParam("id") Integer id, @QueryParam("email") String email) {
        return bo.remover(id, email);
    }
}
