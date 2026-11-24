package com.zakariaabdelbaki.tp3.llm;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;

public interface GuideTouristique {

    @SystemMessage("""
            Tu es guide touristique. Le message utilisateur te donne une ville ou un pays.
            Indique ses 2 principaux endroits à visiter et le prix moyen d’un repas
            dans la devise du pays. Écris en français.
            N’utilise pas Markdown, réponds uniquement avec du JSON de cette forme :
            {"ville_ou_pays": "nom de la ville ou du pays", "endroits_a_visiter": ["endroit 1", "endroit 2"], "prix_moyen_repas": "<prix> <devise du pays>"}
            """)
    String guide(@UserMessage String lieu);
}
