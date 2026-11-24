# Aide de l’IA pour le TP 3

Codex a consulté l’énoncé et l’introduction à JAX-RS, examiné le TP 2, puis vérifié les versions Maven, le catalogue Gemini et la méthode de configuration JSON dans le JAR de sources.

Pour le projet initial, Codex a écrit le POM et les deux classes REST. Le nom du WAR reprend l’artifactId pour que l’URL du test reste stable.

Codex a réalisé les variantes de l’introduction JAX-RS, testé le retour du lieu puis du tableau et enregistré les réponses de Payara. Il a adapté le client du TP 2 pour partager le modèle entre les requêtes et supprimer la mémoire de conversation.

Pour l’appel Gemini, Codex a rédigé le message système, configuré le mode JSON et limité les tentatives dans LangChain4j et le SDK Google. Il a ajouté la lecture JSON-P et les réponses HTTP en cas d’indisponibilité ou de réponse invalide.
