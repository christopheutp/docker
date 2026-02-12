## Exercice 1 : Premiers pas 

### 1.1 Vérification de l’installation 

**Commande :**

```bash
docker --version
```

---

### 1.2 Téléchargement d’images 

**Commandes :**

```bash
docker pull nginx:alpine
docker pull redis:7-alpine
```

**Quelle est la taille de chaque image ? (méthode + réponse)**

```bash
docker images
```

Ou plus ciblé :

```bash
docker images nginx:alpine
docker images redis:7-alpine
```


**Pourquoi utilise-t-on des images `alpine` ?**

* Elles sont basées sur **Alpine Linux**, une distribution très légère.
* Avantages typiques :

  * **Image plus petite**  pull plus rapide, stockage réduit.
  * **Surface d’attaque réduite** (moins de packages installés).
  * Démarrage souvent plus rapide.
* Limites fréquentes :

  * Moins d’outils préinstallés (debug parfois moins confortable).
  * Certaines compatibilités/paquets peuvent être différents (musl vs glibc).

---

### 1.3 Liste des images 

**Commande :**

```bash
docker images
```

**Quelle commande avez-vous utilisée ?**

* `docker images`

**Combien d’images sont présentes ?**

* Vous comptez les lignes retournées (hors en-tête).
* Variante automatique :

```bash
docker images -q | wc -l
```

(Sous Windows PowerShell, utilisez plutôt :)

```powershell
(docker images -q).Count
```

---

## Exercice 2 : Gestion des conteneurs 

### 2.1 Lancer un conteneur Nginx 

**Commande :**

```bash
docker run -d --name web-eval -p 8080:80 nginx:alpine
```

**Vérification :**

* Navigateur : `http://localhost:8080`

---

### 2.2 Inspection du conteneur 

Objectifs : IP, status, date de création.

**Commande “vue globale” :**

```bash
docker ps
```

* Donne le **status** et l’heure relative (`Up ...`), mais pas l’IP.

**Commande détaillée (recommandée) :**

```bash
docker inspect web-eval
```

**Récupérer précisément les infos :**

* **Adresse IP** (sur le réseau bridge par défaut) :

```bash
docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' web-eval
```

* **État / status** :

```bash
docker inspect -f '{{.State.Status}}' web-eval
```

(attendu : `running` si tout est OK)

* **Date de création** :

```bash
docker inspect -f '{{.Created}}' web-eval
```

---

### 2.3 Logs et processus

**10 dernières lignes de logs :**

```bash
docker logs --tail 10 web-eval
```

**Processus en cours dans le conteneur :**

```bash
docker top web-eval
```

(Alternative possible : `docker exec web-eval ps aux` mais `docker top` est le plus direct.)

---

### 2.4 Exécution de commandes 

1. **Ouvrir un shell interactif**

* Sur `nginx:alpine`, le shell est généralement `sh` :

```bash
docker exec -it web-eval sh
```

2. **Créer un fichier `/tmp/evaluation.txt` contenant votre nom**
   Dans le shell :

```sh
echo "Votre Nom" > /tmp/evaluation.txt
```

3. **Vérifier que le fichier existe**

```sh
ls -l /tmp/evaluation.txt
cat /tmp/evaluation.txt
```

4. **Quitter**

```sh
exit
```

---

## Exercice 3 : Cycle de vie 

### 3.1 Arrêt et redémarrage 

**Arrêter :**

```bash
docker stop web-eval
```

**Vérifier qu’il est arrêté :**

```bash
docker ps
```

(ne doit plus apparaître)
et/ou :

```bash
docker ps -a
```

(doit apparaître avec un status du type `Exited (...)`)

**Redémarrer :**

```bash
docker start web-eval
```

**Vérifier que le fichier existe toujours :**

```bash
docker exec web-eval sh -c 'ls -l /tmp/evaluation.txt && cat /tmp/evaluation.txt'
```

**Question : Le fichier existe-t-il toujours ? Pourquoi ?**

* **Oui**, il existe toujours **tant que le conteneur n’a pas été supprimé**.
* Explication :

  * `docker stop` et `docker start` **arrêtent / relancent le même conteneur**.
  * Son système de fichiers “écrivable” (layer du conteneur) est **conservé**.
  * En revanche, si vous faites `docker rm web-eval` puis recréez un nouveau conteneur, le fichier disparaît (sauf si stocké dans un volume).

---

### 3.2 Création d’un conteneur Redis 

**Lancer Redis (sans mapping de port) :**

```bash
docker run -d --name cache-eval redis:7-alpine
```

**Se connecter au CLI Redis et exécuter SET/GET :**

```bash
docker exec -it cache-eval redis-cli
```

Dans `redis-cli` :

```redis
SET evaluation "reussie"
GET evaluation
```

**Résultat attendu :**

* `OK`
* `"reussie"`

Puis quitter :

```redis
EXIT
```

---

### 3.3 Gestion multiple 

**Lister tous les conteneurs (actifs et inactifs) :**

```bash
docker ps -a
```

**Arrêter tous les conteneurs en une seule commande :**

```bash
docker stop $(docker ps -q)
```

```bash
docker stop (docker ps -q)
```


* Si aucun conteneur ne tourne, la commande peut ne rien arrêter (c’est OK).

**Supprimer tous les conteneurs arrêtés en une seule commande :**

* Option 1 (supprimer tous les conteneurs, y compris ceux arrêtés) :

```bash
docker rm $(docker ps -aq)
```

* Option 2 (purger tous les conteneurs arrêtés uniquement) :

```bash
docker container prune -f
```

**Quelles commandes avez-vous utilisées ?**

* Typiquement :

  * `docker ps -a`
  * `docker stop $(docker ps -q)`
  * `docker rm $(docker ps -aq)` (ou `docker container prune -f`)

**Différence entre `docker stop` et `docker rm` ?**

* `docker stop` :

  * **arrête** l’exécution d’un conteneur (il reste présent sur le système).
  * on peut le relancer avec `docker start`.
* `docker rm` :

  * **supprime** le conteneur (métadonnées + couche writable).
  * après suppression, il n’existe plus : il faut refaire `docker run` pour en recréer un.

---

## Exercice 4 : Volumes et persistance 

### 4.1 Création d’un volume 

**Commande :**

```bash
docker volume create data-eval
```

**Vérifier :**

```bash
docker volume ls
```

---

### 4.2 Utilisation du volume 

Objectif : lancer un conteneur `alpine` qui monte le volume, crée un fichier, puis se termine.

**Commande :**

```bash
docker run --rm -v data-eval:/data alpine sh -c 'echo "contenu persistant" > /data/persistant.txt'
```

* `--rm` supprime automatiquement le conteneur à la fin (pratique).
* Le volume, lui, reste.

---

### 4.3 Vérification de la persistance 

**Lancer un nouveau conteneur et vérifier fichier + contenu :**

```bash
docker run --rm -v data-eval:/data alpine sh -c 'ls -l /data/persistant.txt && cat /data/persistant.txt'
```

**Question : Pourquoi les données persistent entre les conteneurs ?**

* Parce que le fichier est écrit dans un **volume Docker** (`data-eval`), qui est un stockage **externe au cycle de vie** des conteneurs.
* Quand un conteneur est supprimé, son système de fichiers writable disparaît, **mais** le volume est conservé et peut être remonté par d’autres conteneurs.

---

## Nettoyage 

Contraintes : supprimer conteneurs + volume, conserver les images.

### Supprimer les conteneurs créés (`web-eval`, `cache-eval`)

(au cas où ils tournent encore)

```bash
docker stop web-eval cache-eval
```

Puis suppression :

```bash
docker rm web-eval cache-eval
```

### Supprimer le volume

```bash
docker volume rm data-eval
```

### Vérifications

```bash
docker ps -a
docker volume ls
docker images
```
