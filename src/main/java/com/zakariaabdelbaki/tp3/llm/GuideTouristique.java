package com.zakariaabdelbaki.tp3.llm;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface GuideTouristique {

    @SystemMessage("""
            Tu es guide touristique. Le message utilisateur te donne une ville ou un pays.
            Donne exactement {{nb}} endroits à visiter, parmi les plus importants, et le prix moyen d’un repas
            dans la devise du pays. Écris en français.
            N’utilise pas Markdown, réponds uniquement avec du JSON de cette forme :
            {"ville_ou_pays": "nom de la ville ou du pays", "endroits_a_visiter": ["nom d’un endroit"], "prix_moyen_repas": "<prix> <devise du pays>"}
            Adapte la longueur du tableau au nombre demandé.
            """)
    String guide(@V("nb") int nb, @UserMessage String lieu);
}
