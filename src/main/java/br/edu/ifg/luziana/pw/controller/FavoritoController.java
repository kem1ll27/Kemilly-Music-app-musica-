package br.edu.ifg.luziana.pw.controller;

import br.edu.ifg.luziana.pw.model.bo.FavoritoBO;
import br.edu.ifg.luziana.pw.model.dto.FavoritoDTO;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/favorito")
public class FavoritoController {

    @Inject
    FavoritoBO bo;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response favoritar(FavoritoDTO dto) {
        return bo.favoritar(dto);
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response desfavoritar(FavoritoDTO dto) {
        return bo.desfavoritar(dto);
    }

    @Path("{email}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listar(@PathParam("email") String email) {
        return bo.listar(email);
    }
}
