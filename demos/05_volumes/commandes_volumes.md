# Commandes pour les volumes

## Créer un Volume Docker

```sh
docker volume create <volume_name>
```

## Lister tous les Volumes Docker

```sh
docker volume ls
```

## Inspecter un Volume Docker

```sh
docker volume inspect <volume_name>
```

## Supprimer un Volume Docker

```sh
docker volume rm <volume_name>
```

## Supprimer tous les Volumes non utilisés

```sh
docker volume prune
```

## Lancer un Conteneur avec un Volume

```sh
docker run --name <container_name> -v <volume_name>:/path/in/container -d <image_name>
```

## Exemple : Lancer un Conteneur PostgreSQL avec un Volume

```sh
docker run --name my_postgres -e POSTGRES_PASSWORD=mysecretpassword -v pgdata:/var/lib/postgresql/data -d postgres
```

## Accéder à un Conteneur en Cours d'Exécution

```sh
docker exec -it <container_name> bash
```

## Arrêter un Conteneur

```sh
docker stop <container_name>
```

## Démarrer un Conteneur Arrêté

```sh
docker start <container_name>
```

## Supprimer un Conteneur

```sh
docker rm <container_name>
```

## Supprimer un Conteneur et son Volume Associé

```sh
docker rm -v <container_name>
```
