# Réponses TP2 – Dockerfile et Construction d’Images

## Exercice 1

### Build image Node v1
Commande (Windows PowerShell) :
docker build -t eval-app:v1 .

Lancement :
docker run -p 3001:3000 eval-app:v1

Test :
curl http://localhost:3001

### Layers
Nombre de layers :
docker history eval-app:v1

Commande pour voir l’historique :
docker history <image>

---

## Exercice 2

### Cache des dépendances
Copier package.json avant le code permet de réutiliser le cache Docker tant que les dépendances ne changent pas, ce qui accélère fortement les builds.

### Multi-stage
Image construite :
docker build -f Dockerfile.multistage -t eval-app:v2 .

L’image v2 est plus petite car les dépendances de développement ne sont pas incluses.

### Utilisateur non-root
Exécuter l’application avec un utilisateur non-root limite l’impact d’une compromission du conteneur.

---

## Exercice 3

### ARG vs ENV
- ARG : variable disponible uniquement au build
- ENV : variable disponible à l’exécution

Build avec argument :
docker build --build-arg NODE_VERSION=20 -t eval-app:v3-node20 .

Run avec surcharge ENV :
docker run -e APP_ENV=development -p 3001:3000 eval-app:v3-node20

---

## Exercice 4

### Build Flask
docker build -t eval-flask:v1 .

Run :
docker run -p 5000:5000 eval-flask:v1

Healthcheck :
docker inspect --format='{{.State.Health.Status}}' <container_id>
