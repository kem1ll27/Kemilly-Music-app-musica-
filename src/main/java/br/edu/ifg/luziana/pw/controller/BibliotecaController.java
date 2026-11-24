package br.edu.ifg.luziana.pw.controller;

import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/biblioteca")
public class BibliotecaController {

    @CheckedTemplate
    public static class Templates {
        public static native TemplateInstance biblioteca();
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance biblioteca() {
        return Templates.biblioteca();
    }
}
