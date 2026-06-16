# Classification de Signaux ECG par Apprentissage Profond

Ce projet a été développé dans le cadre du module Deep Learning au sein de la spécialité Informatique et Réseaux (2ème année) à l'ENSISA. Il présente une étude comparative de trois architectures de réseaux de neurones pour la classification binaire de signaux ECG à partir du jeu de données ECG200.

L'application intègre également une architecture conteneurisée permettant le déploiement et l'interconnexion des modèles via une API d'inférence et une interface utilisateur web.

## Auteur
* Elève : Ozkosar Enes
* Encadrant : M. Maxime Devanne

---

## Modèles d'Apprentissage Comparés
L'évaluation de la robustesse des modèles a été mesurée sur la moyenne de 5 runs distincts sur les 96 points temporels du dataset ECG200 :

1. MLP (Multi-Layer Perceptron) : Réseau pleinement connecté intégrant des couches de Batch Normalization et une régularisation L2. 
2. CNN 1D (Convolutional Network) : Conçu pour l'extraction automatique de motifs locaux invariants dans le temps. 
3. BiLSTM (Bidirectional LSTM) : Architecture récurrente analysant le signal dans les deux directions chronologiques afin de capturer l'ensemble du contexte de l'onde ECG.

---

## Architecture du Système
Le déploiement logiciel repose sur une dissociation en deux microservices distincts :
* ia_service (Flask / Python 3.11) : Service d'inférence chargé d'exécuter les prédictions à partir des modèles sérialisés (.keras) et du pipeline de normalisation (scaler.pkl).
* api_frontal (Spring Boot 4.0 / Java 21) : Interface applicative web permettant le téléversement de fichiers de signaux ECG au format texte (.txt) et la restitution des diagnostics médicaux.

---

## Instructions de Lancement

### Prérequis
* Docker Desktop installé et fonctionnel.

### Procédure d'exécution
Pour orchestrer et démarrer l'ensemble des services, ouvrez un terminal dans le sous-dossier contenant l'application conteneurisée et exécutez le script d'automatisation :

```bash
cd application_docker
chmod +x go
./go
