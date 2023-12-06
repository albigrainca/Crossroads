# Simulation de Carrefour

## Description
Ce projet est une simulation de carrefour, illustrant le contrôle des feux de circulation et le mouvement des véhicules dans un environnement urbain. L'objectif est de créer une simulation réaliste qui gère les feux de circulation et le flux de trafic pour minimiser les risques de collision et optimiser le flux de circulation.

## Contexte
Dans les environnements urbains, la gestion efficace des carrefours est cruciale pour assurer la fluidité du trafic et la sécurité des usagers. Ce projet simule ce scénario en utilisant des techniques de programmation multithread pour contrôler simultanément plusieurs feux de circulation et le mouvement des voitures.

## Technologies Utilisées
- Java 11 : Pour le développement du back-end et la logique de la simulation.
- Swing : Pour l'interface graphique, permettant une représentation visuelle de la simulation.
- Système de threads : Pour la gestion asynchrone des feux de circulation et des voitures.

## Fonctionnalités
- **Feux de Circulation :** Simulation de feux de circulation qui alternent entre rouge et vert, avec un délai de sécurité lors des changements d'état.
- **Mouvement des Voitures :** Les voitures se déplacent selon l'état des feux et s'arrêtent lorsque les feux sont rouges.
- **Simulation Temps Réel :** La simulation se déroule en temps réel, avec des voitures et des feux de circulation se mettant à jour de manière dynamique.
- **Interface Graphique :** Une représentation visuelle du carrefour, des voitures, et des feux de circulation.

## Installation et Exécution
1. Cloner le dépôt Git : `git clone [https://github.com/albigrainca/Crossroads]`.
2. Ouvrir le projet avec l'IDE de votre choix.
3. Lancer la classe `Main` se trouvant dans le package ui pour démarrer la simulation.

## Structure du Projet
- `src/fr/uha/ensisa/crossroad/app` : Classes modélisant les voitures (`Car`) et les feux de circulation (`TrafficLight`).
- `src/fr/uha/ensisa/crossroad/threads` : Classes de threads pour le contrôle des feux (`TrafficLightController`) et la gestion des voitures (`CarController`).
- `src/fr/uha/ensisa/crossroad/ui` : Interface utilisateur avec la classe principale de la fenêtre (`CrossroadsFrame`) et la gestion des panneaux (`TilePanel`).

## Comment ça marche ?
- **Feux de Circulation :** Les threads contrôlent les feux de circulation, en alternant leur état et en introduisant un délai de sécurité.
- **Mouvement des Voitures :** Les voitures réagissent à l'état des feux, se déplaçant ou s'arrêtant en conséquence.
- **Synchronisation :** Les threads sont synchronisés pour assurer une transition fluide et sécurisée entre les différents états des feux.

## Auteur
GRAINCA Albi, GOKER Batuhan
