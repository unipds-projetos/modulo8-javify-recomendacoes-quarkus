package br.com.unipds.javify.recomendacoes.repository;

import br.com.unipds.javify.recomendacoes.dto.RecomendacaoArtista;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.neo4j.driver.Driver;
import org.neo4j.driver.Session;
import org.neo4j.driver.Values;

import java.util.List;

@ApplicationScoped
public class UsuarioRepository {

    @Inject
    Driver driver;

    public List<RecomendacaoArtista> recomendacaoArtista(Long usuarioId) {
        String query = """
            MATCH (usuario:Usuario {id: $usuarioId})-[:SEGUE]->(amigo:Usuario)-[:OUVE]->(artista:Artista) \
            WHERE NOT (usuario)-[:OUVE]->(artista) \
            RETURN artista.nome AS nome, COUNT(amigo) AS forcaRecomendacao \
            ORDER BY forcaRecomendacao DESC \
            LIMIT 5""";

        try (Session session = driver.session()) {

            return session.run(
                            query,
                            Values.parameters("usuarioId", usuarioId)
                    )
                    .list(record -> new RecomendacaoArtista(
                            record.get("nome").asString(),
                            record.get("forcaRecomendacao").asLong()
                    ));
        }
    }

}
