package com.zakariaabdelbaki.tp3.resource;

import com.zakariaabdelbaki.tp3.llm.LlmClient;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonException;
import jakarta.json.JsonObject;
import jakarta.json.JsonString;
import jakarta.json.stream.JsonParser;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.StringReader;

@RequestScoped
@Path("/guide")
public class GuideTouristiqueResource {

    @Inject
    private LlmClient client;

    /**
     * Donne les endroits à visiter et le prix moyen d'un repas en devise locale.
     *
     * @param lieu nom de la ville ou du pays
     * @param nombre nombre d’endroits à visiter, entre 1 et 10 (2 par défaut)
     * @return le guide au format JSON ou une réponse d’erreur
     */
    @GET
    @Path("lieu/{ville_ou_pays}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response endroitsDeVisite(@PathParam("ville_ou_pays") String lieu,
                                    @QueryParam("nb") @DefaultValue("2") String nombre) {
        int nb;
        try {
            nb = Integer.parseInt(nombre);
        } catch (NumberFormatException e) {
            return erreur(Response.Status.BAD_REQUEST, "Le paramètre nb doit être un entier.");
        }
        if (nb < 1 || nb > 10) {
            return erreur(Response.Status.BAD_REQUEST, "Le paramètre nb doit être compris entre 1 et 10.");
        }

        String reponse;
        try {
            reponse = client.guide(nb, lieu);
        } catch (RuntimeException e) {
            return erreur(Response.Status.SERVICE_UNAVAILABLE, "Le guide est momentanément indisponible.");
        }
        if (!guideValide(reponse, nb)) {
            return erreur(Response.Status.BAD_GATEWAY, "Le guide reçu est invalide.");
        }
        return Response.ok(reponse).build();
    }

    private boolean guideValide(String texte, int nb) {
        if (texte == null || texte.isBlank()) {
            return false;
        }
        try (JsonParser parser = Json.createParser(new StringReader(texte))) {
            if (parser.next() != JsonParser.Event.START_OBJECT) {
                return false;
            }
            JsonObject guide = parser.getObject();
            return !parser.hasNext() && guide.size() == 3
                    && guide.get("ville_ou_pays") instanceof JsonString lieu && !lieu.getString().isBlank()
                    && guide.get("prix_moyen_repas") instanceof JsonString prix && !prix.getString().isBlank()
                    && guide.get("endroits_a_visiter") instanceof JsonArray endroits
                    && endroits.size() == nb
                    && endroits.stream().allMatch(e -> e instanceof JsonString s && !s.getString().isBlank());
        } catch (JsonException e) {
            return false;
        }
    }

    private Response erreur(Response.Status statut, String message) {
        return Response.status(statut).entity(Json.createObjectBuilder().add("erreur", message).build()).build();
    }
}
