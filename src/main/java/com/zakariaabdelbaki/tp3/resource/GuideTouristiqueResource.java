package com.zakariaabdelbaki.tp3.resource;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/guide")
public class GuideTouristiqueResource {

    /**
     * Renvoie le lieu demandé pour tester le chemin de la ressource.
     *
     * @param lieu nom de la ville ou du pays
     * @return un tableau contenant le lieu reçu dans le chemin
     */
    @GET
    @Path("lieu/{ville_ou_pays}")
    @Produces(MediaType.APPLICATION_JSON)
    public String[] endroitsDeVisite(@PathParam("ville_ou_pays") String lieu) {
        return new String[] {lieu};
    }
}
