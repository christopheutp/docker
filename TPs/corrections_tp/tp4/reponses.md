# Réponses TP4 – Orchestration Avancée et Optimisation

---

## Exercice 1 – Sécurité et Secrets

### 1.1 Docker Secrets avec Compose

**Question : Comment sécuriser les credentials avec Docker Secrets ?**

Les secrets sont déclarés au niveau top-level et montés automatiquement dans
`/run/secrets/`. Les services lisent les credentials via les variables
`*_FILE`, évitant toute exposition en clair dans les fichiers Compose.

---

### 1.2 Scan de vulnérabilités

**Question : Combien de vulnérabilités critiques/hautes avez-vous trouvées ?**

Le nombre dépend de la date du scan et de l’image, mais les images Alpine
présentent significativement moins de vulnérabilités que les images Debian ou Ubuntu.

**Question : Comment réduire le nombre de vulnérabilités ?**

- Utiliser des images minimalistes
- Mettre à jour régulièrement les images
- Supprimer les dépendances inutiles
- Utiliser multi-stage build

**Question : Quelle image de base recommanderiez-vous ?**

`node:alpine` ou `distroless/nodejs` pour les environnements production.

---

## Exercice 2 – Optimisation des images

### 2.1 Analyse de la taille

**Question : Quel layer occupe le plus d’espace ?**

Le layer contenant `node_modules` est généralement le plus volumineux.

**Question : Quel est le ratio d’efficacité ?**

Un bon ratio dépasse 80 %, indiquant peu de fichiers inutiles.

---

### 2.3 .dockerignore

**Question : Quel impact a le .dockerignore ?**

Il réduit la taille du contexte envoyé au démon Docker, accélère le build
et diminue la taille finale de l’image.

---

## Exercice 3 – Monitoring et Observabilité

### 3.2 Dashboard Grafana

**Question : Quelles métriques sont essentielles à surveiller ?**

- CPU
- Mémoire
- I/O disque
- Latence API
- Taux d’erreur
- Disponibilité des services

---

## Exercice 4 – Déploiement production-ready

### 4.1 Profils Compose

**Question : À quoi servent les profils Docker Compose ?**

Ils permettent de lancer uniquement certains services selon l’environnement
(app seule, monitoring, stack complète).

---

### 4.2 Contraintes de ressources

**Question : Pourquoi définir des limites CPU/Mémoire ?**

Pour éviter qu’un service monopolise les ressources et garantir la stabilité
globale de la plateforme.

---

### 4.3 Politique de redémarrage

**Question : Pourquoi configurer une politique de redémarrage ?**

Pour améliorer la résilience face aux pannes temporaires sans intervention humaine.
