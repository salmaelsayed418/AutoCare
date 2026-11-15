# AutoCare – Système de gestion de garage automobile

## 1. Présentation du projet

**AutoCare** est une application Java développée dans le cadre d'un projet de programmation orientée objet. Son objectif est de faciliter la gestion quotidienne d'un garage automobile en regroupant les principales opérations dans une seule application.

L'application est destinée au personnel du garage. Elle permet de gérer les clients, leurs véhicules, les mécaniciens, les rendez-vous, les réparations, les services proposés, les pièces détachées, les factures et les paiements.

L'objectif principal est de simplifier l'organisation du garage, de centraliser les informations et d'assurer un meilleur suivi des interventions réalisées sur les véhicules.

Par exemple, lorsqu'un client apporte son véhicule au garage, le personnel peut :

1. Enregistrer le client et son véhicule.
2. Consulter les mécaniciens et leurs spécialisations.
3. Créer un rendez-vous.
4. Créer un ordre de réparation contenant les services nécessaires et les pièces utilisées.
5. Calculer automatiquement le coût total de la réparation.
6. Générer une facture associée à cette réparation.
7. Enregistrer le paiement et mettre à jour le statut de la facture.

L'application utilise une interface en ligne de commande (console). Les utilisateurs interagissent avec elle à travers différents menus.

Les données sont sauvegardées dans des fichiers CSV afin de pouvoir être conservées entre plusieurs exécutions de l'application.

## 2. Fonctionnalités principales

AutoCare propose plusieurs fonctionnalités organisées autour de la gestion du garage.

### 2.1 Gestion des clients (Customer Management)

Cette fonctionnalité permet de :
- Ajouter de nouveaux clients.
- Consulter les informations des clients.
- Enregistrer leurs coordonnées.
- Mettre à jour certaines informations, comme leur numéro de téléphone.

Chaque client possède un identifiant unique.

### 2.2 Gestion des véhicules (Vehicle Management)

Cette partie permet d'enregistrer et de gérer les véhicules appartenant aux clients.

Les informations enregistrées comprennent notamment :
- La marque et le modèle.
- L'année de fabrication.
- Le numéro d'immatriculation.
- Le kilométrage.
- Les caractéristiques propres au type de véhicule.

L'application prend en charge trois types de véhicules :
- **ElectricVehicle** : véhicule électrique.
- **CombustionVehicle** : véhicule à moteur thermique.
- **HybridVehicle** : véhicule hybride.

Ces trois classes héritent de la classe abstraite `Vehicule`. Chaque type de véhicule possède son propre comportement pour le calcul des frais de diagnostic.

### 2.3 Gestion des rendez-vous (Appointment Management)

Cette fonctionnalité permet de planifier et de suivre les rendez-vous du garage.

Un rendez-vous est associé à :
- Un client.
- Un véhicule.
- Un mécanicien.
- Une date et une heure.
- Une description du problème ou de l'intervention prévue.

Le statut du rendez-vous peut également être mis à jour.

### 2.4 Gestion des mécaniciens (Mechanic Management)

L'application permet de consulter les mécaniciens et leurs spécialisations.

Chaque mécanicien possède des informations telles que son nom, son prénom et son domaine de spécialisation.

Ces informations permettent d'associer un mécanicien à un rendez-vous ou à un ordre de réparation.

### 2.5 Gestion des réparations (Repair Management)

La gestion des réparations est l'une des fonctionnalités principales de l'application.

Un ordre de réparation (`RepairOrder`) représente une intervention effectuée sur un véhicule.

Il contient notamment :
- Le client concerné.
- Le véhicule à réparer.
- Le mécanicien responsable.
- Une description de la réparation.
- Le statut de la réparation.
- Les services nécessaires.
- Les pièces détachées utilisées.

L'application calcule automatiquement le coût total de la réparation en additionnant le prix des services et le coût des pièces utilisées.

**Exemple :**

- Remplacement des plaquettes de frein : 120 €.
- Deux jeux de plaquettes à 35 € chacun : 70 €.

Coût total de la réparation : **190 €**.

### 2.6 Gestion des services (Service Management)

Cette partie concerne les différentes prestations proposées par le garage.

Chaque service possède :
- Un identifiant.
- Un nom.
- Une description.
- Un prix.
- Une durée estimée.

Exemples de services :
- Changement d'huile.
- Remplacement des plaquettes de frein.
- Diagnostic moteur.
- Remplacement des pneus.

Les services peuvent être associés aux ordres de réparation afin de calculer le coût des interventions.

### 2.7 Gestion des stocks (Inventory Management)

Cette fonctionnalité permet de gérer les pièces détachées utilisées par le garage.

Pour chaque pièce, l'application conserve :
- Son identifiant.
- Son nom.
- Sa description.
- Son prix unitaire.
- Sa quantité disponible en stock.

L'application permet notamment d'ajouter des pièces, de consulter le stock et de gérer les quantités.

Une vérification est effectuée avant de retirer des pièces du stock afin d'éviter les quantités négatives ou les sorties supérieures au stock disponible.

### 2.8 Gestion des factures (Invoice Management)

Une facture (`Invoice`) est associée à un ordre de réparation.

Elle contient :
- Un identifiant unique.
- L'ordre de réparation correspondant.
- La date d'émission.
- Le montant total.
- Le statut du paiement.

Le montant de la facture est calculé à partir du coût total de l'ordre de réparation.

Une facture peut notamment avoir le statut `UNPAID` ou `PAID`.

### 2.9 Gestion des paiements (Payment Management)

L'application permet de simuler le paiement des factures avec plusieurs moyens de paiement :
- Carte bancaire.
- Espèces.
- Virement bancaire.

Chaque moyen de paiement possède sa propre logique de traitement.

Lorsqu'un paiement est effectué avec succès, le statut de la facture est automatiquement mis à jour.

Les paiements sont simulés : aucune transaction bancaire réelle n'est effectuée.

## 3. Technologies utilisées

Le projet utilise les technologies suivantes :

- **Java 21** : langage de programmation utilisé pour développer l'application.
- **Maven** : outil de gestion du projet et de ses dépendances.
- **JUnit 5** : framework utilisé pour les tests unitaires.
- **CSV** : format utilisé pour sauvegarder les données.
- **IntelliJ IDEA** : environnement de développement utilisé pour réaliser le projet.

## 4. Architecture du projet

Le projet est organisé en plusieurs packages afin de séparer les responsabilités et de rendre le code plus clair, structuré et facile à maintenir.

### 4.1 Package `model`

Ce package contient les classes qui représentent les différentes entités de l'application.

Exemples :
- `Customer`
- `Vehicule`
- `ElectricVehicle`
- `CombustionVehicle`
- `HybridVehicle`
- `Mechanic`
- `Appointment`
- `RepairOrder`
- `ServiceOperation`
- `Part`
- `PartUsage`
- `Invoice`

Ces classes définissent les attributs, les constructeurs et les méthodes nécessaires à la représentation des objets.

### 4.2 Package `repository`

Ce package est responsable de la communication avec les fichiers CSV.

Les repositories permettent notamment de :
- Lire les données enregistrées.
- Ajouter de nouvelles données.
- Retrouver les informations à partir d'un identifiant.
- Reconstruire les objets Java à partir des données sauvegardées.

Chaque repository est associé à un type de données.

Par exemple :
- `CustomerRepository` gère les clients.
- `VehicleRepository` gère les véhicules.
- `AppointmentRepository` gère les rendez-vous.
- `RepairOrderRepository` gère les ordres de réparation.
- `InvoiceRepository` gère les factures.

Cette séparation permet de ne pas mélanger la gestion des fichiers avec la logique métier de l'application.

### 4.3 Package `service`

Ce package contient la logique métier de l'application.

Les services utilisent les repositories pour accéder aux données et effectuer les opérations demandées.

Ils permettent notamment de gérer les clients, les véhicules, les rendez-vous, les réparations, les stocks et les factures.

Par exemple, le service de réparation peut créer un ordre de réparation et calculer son coût total.

### 4.4 Package `payment`

Ce package regroupe les classes liées aux moyens de paiement.

Il contient notamment :
- `PaymentMethod` : interface définissant le comportement commun des moyens de paiement.
- `CardPayment` : traitement d'un paiement par carte bancaire.
- `CashPayment` : traitement d'un paiement en espèces.
- `BankTransferPayment` : traitement d'un paiement par virement bancaire.
- `PaymentService` : service responsable du traitement des paiements et de la mise à jour des factures.

### 4.5 Package `exception`

Ce package regroupe les exceptions personnalisées utilisées pour gérer certaines erreurs spécifiques à l'application.

Ces exceptions permettent de signaler des situations particulières liées aux règles métier et de distinguer ces erreurs des exceptions générales de Java.

### 4.6 Classe `Main.java`

La classe `Main` constitue le point d'entrée de l'application.

Elle contient la méthode `main()` et les menus permettant à l'utilisateur de naviguer entre les différentes fonctionnalités.

Elle permet de lancer les opérations demandées et de guider l'utilisateur à travers l'application.

## 5. Concepts de programmation orientée objet utilisés

Le projet AutoCare met en pratique plusieurs concepts importants de la programmation orientée objet.

### 5.1 Encapsulation

L'encapsulation consiste à protéger les données d'un objet en limitant leur accès direct.

Dans le projet, les attributs des classes sont généralement déclarés `private`.

L'accès et la modification des données se font à travers des méthodes publiques, comme les getters et les méthodes de mise à jour.

Cela permet de mieux contrôler les modifications et de protéger la cohérence des objets.

### 5.2 Héritage

L'héritage permet à une classe de récupérer les attributs et les méthodes d'une autre classe.

Dans AutoCare, les classes `ElectricVehicle`, `CombustionVehicle` et `HybridVehicle` héritent de la classe `Vehicule`.

Elles partagent donc les caractéristiques communes d'un véhicule tout en possédant leurs propres attributs et comportements.

### 5.3 Classe abstraite

La classe `Vehicule` est une classe abstraite.

Elle représente les caractéristiques communes à tous les véhicules, mais ne peut pas être instanciée directement.

Elle définit également une méthode abstraite `calculateDiagnosticFee()` que les classes filles doivent implémenter.

Cela permet à chaque type de véhicule de définir ses propres frais de diagnostic.

### 5.4 Polymorphisme

Le polymorphisme permet d'utiliser des objets de différentes classes à travers un même type parent ou une même interface.

Par exemple, les différents types de véhicules peuvent être manipulés à travers une référence de type `Vehicule`.

Lorsqu'on appelle `calculateDiagnosticFee()`, Java exécute automatiquement la version correspondant au type réel de l'objet.

Le polymorphisme est également utilisé dans le système de paiement avec l'interface `PaymentMethod`.

### 5.5 Interfaces

Une interface définit un contrat que les classes qui l'implémentent doivent respecter.

Dans le projet, l'interface `PaymentMethod` définit les méthodes communes aux différents moyens de paiement.

Les classes `CardPayment`, `CashPayment` et `BankTransferPayment` implémentent cette interface.

Cela permet d'utiliser différents moyens de paiement sans dépendre directement d'une seule classe.

### 5.6 Collections

Le projet utilise des collections Java, notamment des `List` et des `ArrayList`, pour stocker et manipuler plusieurs objets.

Par exemple, un ordre de réparation peut contenir plusieurs services et plusieurs utilisations de pièces détachées.

### 5.7 Gestion des exceptions

Le projet utilise la gestion des exceptions pour éviter que certaines erreurs interrompent brutalement l'exécution de l'application.

Les validations permettent notamment de contrôler les valeurs saisies, les quantités de stock et les opérations de paiement.

Les exceptions personnalisées permettent de représenter certaines erreurs propres aux règles métier de l'application.

## 6. Gestion des données

Les données de l'application sont sauvegardées dans des fichiers CSV situés dans le dossier suivant :

`src/main/resources/data/`

Les fichiers utilisés sont :

- `customers.csv`
- `vehicles.csv`
- `appointments.csv`
- `mechanics.csv`
- `services.csv`
- `parts.csv`
- `repair_orders.csv`
- `invoices.csv`

Chaque fichier contient les informations correspondant à une catégorie de données.

Les repositories sont responsables de la lecture et de l'écriture de ces fichiers.

Lors du chargement des données, les lignes des fichiers CSV sont lues et utilisées pour reconstruire les objets Java correspondants.

Par exemple, le `CustomerRepository` lit les informations du fichier `customers.csv` et reconstruit les objets `Customer`.

Lorsqu'une nouvelle donnée est ajoutée, le repository correspondant l'enregistre dans le fichier CSV approprié.

Cette méthode permet de conserver les données après la fermeture de l'application, sans avoir besoin d'utiliser une base de données externe.

## 7. Décisions techniques

Plusieurs décisions techniques ont été prises pendant le développement du projet.

### 7.1 Utilisation de fichiers CSV

Nous avons choisi les fichiers CSV pour sauvegarder les données sans avoir besoin de mettre en place une base de données externe.

Cette solution est simple à utiliser, facile à consulter et adaptée à la taille du projet.

### 7.2 Architecture en plusieurs packages

Nous avons séparé les modèles, les repositories et les services afin de mieux organiser le code et de séparer les responsabilités.

Cette organisation facilite la maintenance et permet de modifier une partie de l'application sans devoir modifier toutes les autres.

### 7.3 Utilisation d'une classe abstraite pour les véhicules

Nous avons créé une classe abstraite `Vehicule` afin de regrouper les caractéristiques communes aux différents types de véhicules.

Chaque classe fille peut ensuite définir ses propres caractéristiques et son propre comportement, notamment pour le calcul des frais de diagnostic.

### 7.4 Utilisation du Strategy Pattern pour les paiements

Nous avons choisi le Strategy Pattern pour permettre à l'application de gérer plusieurs moyens de paiement à travers une interface commune.

Chaque moyen de paiement possède sa propre implémentation, ce qui permet de changer de stratégie de paiement sans modifier la logique principale du traitement des factures.

## 8. Principes SOLID et conception

Le projet suit plusieurs principes de conception visant à améliorer la lisibilité, la modularité et la maintenance du code.

### 8.1 Single Responsibility Principle (SRP)

Le principe de responsabilité unique consiste à donner une responsabilité principale à chaque classe.

Dans AutoCare :
- Les classes du package `model` représentent les données.
- Les classes du package `repository` gèrent la lecture et l'écriture des fichiers.
- Les classes du package `service` contiennent la logique métier.
- Les classes du package `payment` gèrent les moyens de paiement.

Cette séparation évite de regrouper toutes les responsabilités dans une seule classe.

### 8.2 Open/Closed Principle (OCP)

Le principe Open/Closed consiste à permettre l'ajout de nouveaux comportements sans devoir modifier constamment le code existant.

L'interface `PaymentMethod` facilite cette approche : un nouveau moyen de paiement peut être ajouté en créant une nouvelle classe qui implémente cette interface.

La logique générale du paiement peut ainsi rester indépendante des différentes méthodes de paiement.

### 8.3 Liskov Substitution Principle (LSP)

Le principe de substitution de Liskov signifie qu'une classe fille doit pouvoir remplacer sa classe parent sans provoquer de comportement incohérent.

Dans AutoCare, les classes `ElectricVehicle`, `CombustionVehicle` et `HybridVehicle` peuvent être utilisées à travers une référence de type `Vehicule`.

Elles respectent ainsi le comportement attendu d'un véhicule.

### 8.4 Interface Segregation Principle (ISP)

Le principe de ségrégation des interfaces recommande de créer des interfaces adaptées aux besoins des classes plutôt que des interfaces trop générales.

Dans AutoCare, l'interface `PaymentMethod` définit uniquement les comportements nécessaires au traitement d'un paiement.

Chaque moyen de paiement implémente ce contrat commun.

### 8.5 Dependency Inversion Principle (DIP)

Le principe d'inversion des dépendances recommande de privilégier les abstractions plutôt que les implémentations concrètes.

Dans le système de paiement, `PaymentService` peut travailler avec le type abstrait `PaymentMethod` plutôt qu'avec une classe de paiement spécifique.

Cela réduit le couplage entre le service de paiement et les différentes méthodes de paiement.

## 9. Gestion des exceptions personnalisées

Le projet utilise des exceptions pour gérer les erreurs et protéger les règles métier.

Les exceptions personnalisées permettent de représenter des situations spécifiques à l'application.

**Exceptions personnalisées créées :**
- [Ajouter ici le nom exact de la première exception et son rôle.]
- [Ajouter ici le nom exact de la deuxième exception, si elle existe.]

Ces exceptions permettent de rendre la gestion des erreurs plus claire et adaptée aux besoins de l'application.

## 10. Prérequis

Avant de lancer l'application, il faut disposer des éléments suivants :

- Java Development Kit (JDK) 21 ou version ultérieure.
- Maven.
- IntelliJ IDEA (recommandé).

## 11. Lancer l'application

Pour exécuter le projet :

1. Ouvrir le projet dans IntelliJ IDEA.
2. Attendre que Maven télécharge et configure les dépendances.
3. Vérifier que le JDK 21 est sélectionné dans la configuration du projet.
4. Ouvrir `Main.java` dans le package `com.autocare`.
5. Exécuter la méthode `main()`.
6. Utiliser les menus affichés dans la console pour naviguer dans l'application.

## 12. Exécuter les tests

Le projet utilise JUnit 5 pour réaliser des tests unitaires.

Les tests permettent de vérifier le comportement de certaines fonctionnalités et de détecter les erreurs éventuelles.

Pour lancer les tests avec Maven, exécuter la commande suivante à la racine du projet :

```bash
mvn test
```

Les tests couvrent notamment les opérations liées à la gestion du stock, comme :
- La validation des quantités.
- La gestion d'un stock insuffisant.
- La consommation des pièces disponibles.
