# TP Docker Compose - Multi-Services, Networks, Depends_on et Healthchecks

##  Objectif

Maîtriser l'orchestration de plusieurs services avec Docker Compose :
1. **Multi-services** : Application + Base de données + Cache + Adminer
2. **Networks** : Isolation et communication entre services
3. **Depends_on** : Ordre de démarrage avec conditions
4. **Healthchecks** : Vérification de l'état des services

---

##  Contexte

Vous disposez d'une application Spring Boot de gestion de produits (e-commerce simplifié).

Votre mission : **orchestrer cette application** avec ses dépendances en utilisant les fonctionnalités avancées de Docker Compose.

---

##  Architecture

```
┌──────────────────────────────────────────────────────────────────────┐
│                         Docker Compose                                │
│                                                                       │
│  ┌─────────────────────────── frontend-network ───────────────────┐  │
│  │                                                                 │  │
│  │  ┌─────────────┐                          ┌─────────────┐      │  │
│  │  │  Spring Boot│                          │   Adminer   │      │  │
│  │  │    (app)    │                          │   (admin)   │      │  │
│  │  │   :8080     │                          │   :8081     │      │  │
│  │  └──────┬──────┘                          └──────┬──────┘      │  │
│  │         │                                        │              │  │
│  └─────────┼────────────────────────────────────────┼──────────────┘  │
│            │                                        │                 │
│  ┌─────────┼──────────────── backend-network ───────┼──────────────┐  │
│  │         │                                        │              │  │
│  │         ▼                                        ▼              │  │
│  │  ┌─────────────┐                          ┌─────────────┐      │  │
│  │  │ PostgreSQL  │                          │    Redis    │      │  │
│  │  │    (db)     │◄─────────────────────────│   (cache)   │      │  │
│  │  │   :5432     │                          │   :6379     │      │  │
│  │  └─────────────┘                          └─────────────┘      │  │
│  │                                                                 │  │
│  └─────────────────────────────────────────────────────────────────┘  │
│                                                                       │
└───────────────────────────────────────────────────────────────────────┘

Flux de démarrage (depends_on + healthcheck) :
   db (healthy) ──► cache (healthy) ──► app (started)
                                            │
                                            ▼
                                        admin (started)
```

---

##  Spécifications

### Services à Configurer

| Service | Image | Port Externe | Réseau(x) | Rôle |
|---------|-------|--------------|-----------|------|
| `app` | Build local | 8080 | frontend, backend | Application Spring Boot |
| `db` | postgres:15-alpine | - (interne) | backend | Base de données |
| `cache` | redis:7-alpine | - (interne) | backend | Cache |
| `admin` | adminer | 8081 | frontend, backend | Interface BDD |

### Réseaux

| Réseau | Services | Objectif |
|--------|----------|----------|
| `frontend-network` | app, admin | Accès depuis l'extérieur |
| `backend-network` | app, db, cache, admin | Communication interne |

### Healthchecks

| Service | Commande de test | Intervalle | Retries |
|---------|------------------|------------|---------|
| `db` | `pg_isready -U $USER -d $DB` | 10s | 5 |
| `cache` | `redis-cli ping` | 5s | 3 |
| `app` | `curl -f http://localhost:8080/actuator/health` | 30s | 3 |

### Ordre de Démarrage (depends_on)

```
db (service_healthy)
    └──► cache (service_healthy)
              └──► app (service_started)
                       └──► admin (service_started)
```

---

##  Travail à Réaliser

### Créer le  docker-compose.yml

###  Critères de Validation

### Niveau 1 : Démarrage
- [ ] `docker compose up -d` démarre les 4 services sans erreur
- [ ] `docker compose ps` montre tous les services "running"

### Niveau 2 : Healthchecks
- [ ] `docker compose ps` montre "(healthy)" pour db, cache et app

### Niveau 3 : Ordre de démarrage
- [ ] Observer les logs : db démarre en premier, puis cache, puis app
- [ ] Si db n'est pas healthy, app ne démarre pas

### Niveau 4 : Réseaux
- [ ] `docker network ls` montre les deux réseaux créés

### Niveau 5 : Isolation
- [ ] Adminer peut accéder à PostgreSQL (même réseau backend)
- [ ] L'application communique avec db et cache
