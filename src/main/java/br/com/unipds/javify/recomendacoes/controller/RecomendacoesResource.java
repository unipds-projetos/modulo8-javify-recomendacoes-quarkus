package br.com.unipds.javify.recomendacoes.controller;

import br.com.unipds.javify.recomendacoes.dto.RecomendacaoArtista;
import br.com.unipds.javify.recomendacoes.repository.UsuarioRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/api/v1/recomendacoes")
public class RecomendacoesResource {

    @Inject
    UsuarioRepository usuarioRepository;

    @GET
    @Path("/artistas/{usuarioId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response recomendacoesArtistas(@PathParam("usuarioId") Long usuarioId) {
        List<RecomendacaoArtista> recomendacoes = usuarioRepository.recomendacaoArtista(usuarioId);
        if (recomendacoes.isEmpty()) {
            return Response.noContent().build();
        }
        return Response.ok(recomendacoes).build();
    }

}
