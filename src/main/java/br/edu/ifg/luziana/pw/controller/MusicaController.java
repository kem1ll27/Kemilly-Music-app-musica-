package br.edu.ifg.luziana.pw.controller;

import br.edu.ifg.luziana.pw.model.bo.MusicaBO;
import br.edu.ifg.luziana.pw.model.dto.MusicaDTO;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/musica")
public class MusicaController {

    @Inject
    MusicaBO bo;

    // GET /musica            -> lista todas
    // GET /musica?categoria=RECENTE   -> lista só de uma categoria
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listar(@QueryParam("categoria") String categoria) {
        if (categoria == null || categoria.isBlank()) {
            return bo.listarTodas();
        }
        return bo.listarPorCategoria(categoria);
    }

    // POST /musica?email=admin@x.com   (só ADMIN consegue)
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response adicionar(MusicaDTO dto, @QueryParam("email") String email) {
        return bo.adicionar(dto, email);
    }

    // DELETE /musica/5?email=admin@x.com   (só ADMIN consegue)
    @Path("{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response remover(@PathParam("id") Integer id, @QueryParam("email") String email) {
        return bo.remover(id, email);
    }
}
