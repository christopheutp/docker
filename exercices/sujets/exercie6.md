# Exercice — Portabilité d’un site web avec Docker et volumes nommés

**Changement de serveur web sans perte de données**

---

## Mise en situation

Vous intervenez sur un projet interne qui consiste à déployer un **site web statique** à l’aide de Docker.
Le site est actuellement servi par **Nginx**, mais des décisions techniques successives vont imposer des changements de serveur web.

La contrainte métier est claire :
**le site ne doit jamais être perdu ni recopié manuellement**, quel que soit le serveur utilisé.
Les données doivent survivre à la suppression et à la recréation des conteneurs.

---

## Site web de départ

Vous utiliserez un site statique existant, librement réutilisable :

* **Start Bootstrap – Clean Blog** (licence MIT)

  * Lien : [https://github.com/StartBootstrap/startbootstrap-clean-blog](https://github.com/StartBootstrap/startbootstrap-clean-blog)

Vous pouvez également utiliser **votre propre site statique personnel** (HTML / CSS / JS uniquement), si vous en disposez déjà.

---

## Étape 1 — Dockerisation initiale avec Nginx

### Contexte

Le site doit être déployé dans un conteneur Docker à l’aide du serveur **Nginx**.

### Travail demandé

* Télécharger (ou utiliser) le site statique.
* Déployer le site dans un conteneur Docker basé sur Nginx.
* Vérifier que le site est accessible depuis un navigateur.

### Contraintes

* Le site doit être stocké dans un **volume nommé Docker**.
* L’utilisation de **bind mounts est interdite**.
* Les données du site doivent être indépendantes du conteneur.
* Vous êtes libres d’utiliser ou non un `Dockerfile` pour cette étape.

### Livrables attendus

* Toutes les **commandes Docker** exécutées.
* Une preuve que le site est bien accessible via Nginx.

---

## Étape 2 — Migration vers Apache HTTP Server

### Mise en situation

Une nouvelle politique de sécurité est appliquée dans l’entreprise.
Une **faille critique** a été identifiée sur la version de Nginx utilisée, et son usage est désormais interdit.

### Travail demandé

* Supprimer le conteneur Nginx.
* Redéployer le site à l’aide d’**Apache HTTP Server**.
* Réutiliser **le même volume nommé** contenant le site web.

### Contraintes

* Le volume ne doit pas être supprimé.
* Le contenu du site ne doit pas être modifié.
* Aucune copie manuelle du site ne doit être effectuée.

### Validation

* Le site doit être accessible via Apache.
* Le contenu doit être strictement identique à celui servi précédemment par Nginx.

---

## Étape 3 — Migration vers Caddy

### Mise en situation

Une phase de tests de performance et de simplicité d’exploitation est lancée.
L’équipe souhaite évaluer **Caddy**, réputé pour sa configuration simplifiée et ses performances.

### Travail demandé

* Supprimer le conteneur Apache.
* Déployer le site avec **Caddy**.
* Réutiliser **le même volume nommé** que précédemment.

### Validation

* Le site doit être accessible via Caddy.
* Les données doivent toujours provenir du même volume.
* Le contenu du site doit rester inchangé.

---

## Attendus globaux

Vous devez fournir :

* l’ensemble des **commandes Docker** exécutées pour les trois étapes ;


