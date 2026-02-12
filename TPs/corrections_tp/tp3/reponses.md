# Réponses TP3 – Docker Compose

---

## Exercice 1 – Compose basique

### 1.2 Validation

**Question : Comment voir les logs des deux services en temps réel ?**

Commande (Windows PowerShell) :
docker compose logs -f

---

**Question : Comment accéder au CLI PostgreSQL depuis l’extérieur ?**

Commande :
docker compose exec db psql -U evaluser -d evaldb

---

## Exercice 2 – Application multi-tiers

### 2.2 Réseau personnalisé

**Question : Quel est l’avantage d’un réseau personnalisé par rapport au réseau par défaut ?**

Un réseau personnalisé permet :
- une isolation claire entre projets
- une résolution DNS automatique par nom de service
- une meilleure lisibilité et sécurité de l’architecture

---

### 2.3 Script d’initialisation DB

**Question : Comment exécuter automatiquement un script SQL au premier démarrage ?**

PostgreSQL exécute automatiquement les scripts `.sql` placés dans :
/docker-entrypoint-initdb.d/

Le fichier `init.sql` est monté dans ce dossier via un volume.

---

## Exercice 3 – Reverse Proxy Nginx

### 3.2 Test complet

**Question : Comment vérifier que Redis met bien en cache les données ?**

En appelant plusieurs fois `/api/stats` :
- premier appel → source = database
- appels suivants → source = cache

---

**Question : Que se passe-t-il si Redis est arrêté ?**

- Le cache devient indisponible
- L’API interroge directement PostgreSQL
- L’application continue de fonctionner, mais sans cache

---

## Exercice 4 – Environnements

### 4.1 Fichier .env

**Question : Pourquoi utiliser un fichier .env ?**

Le fichier `.env` permet :
- de centraliser la configuration
- d’éviter le hard-coding
- de faciliter le changement d’environnement

---

### 4.2 Override développement

**Question : À quoi sert docker-compose.override.yml ?**

Il permet :
- d’ajouter des comportements spécifiques au développement
- sans modifier le fichier principal
- automatiquement pris en compte par Docker Compose
