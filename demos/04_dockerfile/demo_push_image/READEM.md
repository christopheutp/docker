# Push image dockerhub

### 1. Construction de l'image Docker
Assurez-vous que votre image Docker est construite localement. Si vous n'avez pas encore créé votre image, utilisez la commande suivante :

```bash
docker build -t NOM_UTILISATEUR/NOM_IMAGE:TAG .
```

Remplacez `NOM_UTILISATEUR`, `NOM_IMAGE`, et `TAG` par vos propres valeurs.

### 2. Connexion à Docker Hub
Connectez-vous à votre compte Docker Hub en utilisant la commande suivante :

```bash
docker login
```

Vous serez invité à saisir votre nom d'utilisateur et votre mot de passe Docker Hub.

### 3. Tag de l'image
Taguez votre image avec le nom d'utilisateur Docker Hub et le nom de l'image que vous avez créée :

```bash
docker tag NOM_UTILISATEUR/NOM_IMAGE:TAG NOM_UTILISATEUR/NOM_IMAGE:TAG
```

### 4. Push de l'image
Poussez votre image taguée vers Docker Hub :

```bash
docker push NOM_UTILISATEUR/NOM_IMAGE:TAG
```
