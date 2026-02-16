# 🚀 SpaQuarkus

> Application full-stack moderne combinant Quarkus et Vue 3 avec packaging unifié

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![Quarkus](https://img.shields.io/badge/Quarkus-3.31.3-blue.svg)](https://quarkus.io/)
[![Vue](https://img.shields.io/badge/Vue-3.5-green.svg)](https://vuejs.org/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

## 📋 Table des matières

- [À propos](#-à-propos)
- [Technologies](#-technologies)
- [Prérequis](#-prérequis)
- [Installation](#-installation)
- [Démarrage rapide](#-démarrage-rapide)
- [API REST](#-api-rest)
- [Build & Déploiement](#-build--déploiement)
- [Tests](#-tests)
- [Architecture](#-architecture)
- [Contribution](#-contribution)

## 🎯 À propos

**SpaQuarkus** est une application de démonstration full-stack illustrant les meilleures pratiques pour combiner :

- **Backend** : API REST performante avec Quarkus
- **Frontend** : Interface moderne avec Vue 3 et Vite
- **Build unifié** : Quinoa pour un packaging transparent en un seul JAR exécutable

### Fonctionnalités

✅ API REST CRUD complète (Customers & Bills)  
✅ Stockage en mémoire thread-safe avec injection par constructeur  
✅ Frontend Vue 3 avec TypeScript et Composition API  
✅ Hot-reload en développement (backend + frontend)  
✅ Build optimisé produisant un uber-jar exécutable  
✅ Tests unitaires et d'intégration  
✅ JSON pretty-print activé  

## 🛠 Technologies

### Backend
- **[Quarkus 3.31.3](https://quarkus.io/)** - Framework Java cloud-native
- **[Jakarta REST](https://jakarta.ee/specifications/restful-ws/)** - API REST standard
- **[Jackson](https://github.com/FasterXML/jackson)** - Sérialisation JSON
- **Java Records** - DTOs immuables et concis

### Frontend
- **[Vue 3](https://vuejs.org/)** - Framework JavaScript progressif
- **[Vite](https://vitejs.dev/)** - Build tool ultra-rapide
- **[TypeScript](https://www.typescriptlang.org/)** - Typage statique
- **[Vue Router](https://router.vuejs.org/)** - Routing SPA
- **[Pinia](https://pinia.vuejs.org/)** - State management

### DevOps
- **[Quinoa](https://quarkiverse.github.io/quarkiverse-docs/quarkus-quinoa/dev/)** - Intégration frontend dans Maven
- **Maven** - Gestion de dépendances et build
- **JUnit 5** - Tests unitaires
- **RestAssured** - Tests d'API

## 📦 Prérequis

| Outil | Version minimale | Recommandé |
|-------|------------------|------------|
| JDK | 21 | 21+ |
| Node.js | 20.19.0 | 22.12.0+ |
| Maven | 3.8+ | Wrapper inclus |
| npm | 10+ | Dernière version |

## 💻 Installation

```bash
# Cloner le projet
git clone https://github.com/votre-compte/spaquarkus.git
cd spaquarkus

# Installer les dépendances frontend
cd src/main/webui
npm install
cd ../../..
```

## 🚀 Démarrage rapide

### Mode développement full-stack

**Un seul terminal suffit !**

```bash
./mvnw quarkus:dev
```

🌐 Application complète sur : http://localhost:8080  
🔧 Dev UI Quarkus : http://localhost:8080/q/dev/

> 💡 **Comment ça marche ?**  
> Quinoa démarre automatiquement le serveur Vite (npm run dev) en arrière-plan sur le port 5173 et configure un proxy inverse : toutes les requêtes vers http://localhost:8080 sont automatiquement routées vers le frontend Vite, sauf les endpoints API (`/customers`, `/bills`) qui sont traités par Quarkus.
> 
> Résultat : **Hot Module Replacement (HMR) du frontend + live reload du backend**, le tout sur un seul port !

### Mode production (JAR unique)

```bash
# Build avec frontend intégré
./mvnw clean package -DskipTests

# Lancer l'application
java -jar target/*-runner.jar
```

L'application complète (backend + frontend) est maintenant accessible sur http://localhost:8080

## 📡 API REST

### Customers

| Méthode | Endpoint | Description | Exemple |
|---------|----------|-------------|---------|
| `GET` | `/customers` | Liste tous les clients | `curl http://localhost:8080/customers` |
| `GET` | `/customers/{id}` | Récupère un client | `curl http://localhost:8080/customers/1` |
| `POST` | `/customers` | Crée un client | `curl -X POST -H "Content-Type: application/json" -d '{"name":"Alice"}' http://localhost:8080/customers` |
| `PUT` | `/customers/{id}` | Met à jour un client | `curl -X PUT -H "Content-Type: application/json" -d '{"name":"Bob"}' http://localhost:8080/customers/1` |
| `DELETE` | `/customers/{id}` | Supprime un client | `curl -X DELETE http://localhost:8080/customers/1` |

### Bills

| Méthode | Endpoint | Description | Exemple |
|---------|----------|-------------|---------|
| `GET` | `/bills` | Liste toutes les factures | `curl http://localhost:8080/bills` |
| `GET` | `/bills/{id}` | Récupère une facture | `curl http://localhost:8080/bills/1` |
| `POST` | `/bills` | Crée une facture | `curl -X POST -H "Content-Type: application/json" -d '{"description":"Consulting","amount":123.45}' http://localhost:8080/bills` |
| `PUT` | `/bills/{id}` | Met à jour une facture | - |
| `DELETE` | `/bills/{id}` | Supprime une facture | - |

### Exemples avec jq

```bash
# Récupérer et formater les clients
curl -s http://localhost:8080/customers | jq .

# Créer un client et capturer la réponse
curl -s -X POST -H "Content-Type: application/json" \
  -d '{"name":"Charlie"}' \
  http://localhost:8080/customers | jq .

# Récupérer un client spécifique
curl -s http://localhost:8080/customers/1 | jq '.name'
```

## 🏗 Build & Déploiement

### Build standard (fast-jar)

```bash
./mvnw clean package
java -jar target/quarkus-app/quarkus-run.jar
```

### Build uber-jar (recommandé pour production)

```bash
./mvnw clean package -DskipTests
java -jar target/*-runner.jar
```

> Le uber-jar contient toutes les dépendances + le frontend buildé

### Build natif (GraalVM)

```bash
# Avec GraalVM installé
./mvnw package -Dnative

# Avec Docker (sans GraalVM local)
./mvnw package -Dnative -Dquarkus.native.container-build=true

# Exécuter le binaire natif
./target/*-runner
```

## 🧪 Tests

### Tests backend

```bash
# Tous les tests
./mvnw test

# Tests unitaires uniquement
./mvnw test -Dtest=*Test

# Tests d'intégration
./mvnw verify
```

### Tests frontend

```bash
cd src/main/webui

# Vérification TypeScript
npm run type-check

# Build de validation
npm run build
```

### Coverage

```bash
./mvnw verify jacoco:report
# Rapport disponible dans target/site/jacoco/index.html
```

## 🏛 Architecture

```
spaquarkus/
├── src/
│   ├── main/
│   │   ├── java/ch/tranchida/sample/quarkus/
│   │   │   ├── Bill.java                    # DTO (record)
│   │   │   ├── BillResource.java            # REST endpoint
│   │   │   ├── BillStorage.java             # Interface
│   │   │   ├── InMemoryBillStorage.java     # Implémentation
│   │   │   ├── Customer.java                # DTO (record)
│   │   │   ├── CustomerResource.java        # REST endpoint
│   │   │   ├── CustomerStorage.java         # Interface
│   │   │   ├── InMemoryCustomerStorage.java # Implémentation
│   │   │   ├── JacksonConfig.java           # Config JSON
│   │   │   ├── StartupBillDataLoader.java   # Données initiales
│   │   │   └── StartupDataLoader.java       # Données initiales
│   │   ├── resources/
│   │   │   └── application.properties       # Config Quarkus
│   │   └── webui/                           # Application Vue 3
│   │       ├── src/
│   │       │   ├── components/
│   │       │   │   └── customers.vue        # Composant liste
│   │       │   ├── views/
│   │       │   ├── App.vue
│   │       │   └── main.ts
│   │       ├── package.json
│   │       └── vite.config.ts               # Config proxy
│   └── test/java/                           # Tests JUnit
├── pom.xml                                  # Config Maven
└── README.md
```

### Principes de conception

- **Injection par constructeur** : favorise la testabilité et l'immutabilité
- **Records Java** : DTOs concis et thread-safe
- **Storage en mémoire** : ConcurrentHashMap + AtomicLong pour la génération d'IDs
- **Séparation des préoccupations** : interfaces + implémentations
- **Configuration centralisée** : `application.properties` et `vite.config.ts`

## 🤝 Contribution

Les contributions sont les bienvenues ! Pour contribuer :

1. Fork le projet
2. Créez une branche (`git checkout -b feature/AmazingFeature`)
3. Committez vos changements (`git commit -m 'Add AmazingFeature'`)
4. Push vers la branche (`git push origin feature/AmazingFeature`)
5. Ouvrez une Pull Request

### Guidelines

- Suivez les conventions de code existantes
- Ajoutez des tests pour les nouvelles fonctionnalités
- Mettez à jour la documentation si nécessaire
- Assurez-vous que `./mvnw verify` passe avant de soumettre

## 📝 Notes importantes

### Pour la production

⚠️ **Storages en mémoire** : Les implémentations actuelles (`InMemoryCustomerStorage`, `InMemoryBillStorage`) sont des exemples. Pour la production :
- Migrez vers une base de données (PostgreSQL, MySQL)
- Utilisez Hibernate Panache ou Spring Data
- Ajoutez la persistance avec JPA

### Configuration avancée

```properties
# application.properties

# Port personnalisé
quarkus.http.port=9090

# Configuration Quinoa (optionnel)
quarkus.quinoa.dev-server.port=5173
quarkus.quinoa.package-manager-install=true
quarkus.quinoa.package-manager-command.install=install

# Logs
quarkus.log.level=INFO
quarkus.log.category."ch.tranchida".level=DEBUG
```

### Comment fonctionne l'intégration Quinoa

En **mode dev** (`./mvnw quarkus:dev`) :
1. Quinoa détecte automatiquement le projet frontend dans `src/main/webui`
2. Lance `npm install` si nécessaire
3. Démarre le dev server Vite sur le port 5173
4. Configure un **proxy inverse** : `localhost:8080` → Vite (`localhost:5173`)
5. Les endpoints API (`/customers`, `/bills`) sont interceptés par Quarkus
6. Tous les autres chemins sont proxyfiés vers Vite

En **mode build** (`./mvnw package`) :
1. Quinoa exécute `npm run build` automatiquement
2. Copie le dossier `dist/` dans `META-INF/resources` du JAR
3. Quarkus sert les fichiers statiques directement

> ⚡ **Résultat** : Développement simplifié avec un seul port et packaging transparent !

## 📚 Ressources

- [Documentation Quarkus](https://quarkus.io/guides/)
- [Guide Vue 3](https://vuejs.org/guide/)
- [Quarkus Quinoa](https://quarkiverse.github.io/quarkiverse-docs/quarkus-quinoa/dev/)
- [Vite Guide](https://vitejs.dev/guide/)

## 📄 Licence

Ce projet est sous licence MIT. Voir le fichier [LICENSE](LICENSE) pour plus de détails.

---

**Développé avec ❤️ en utilisant Quarkus et Vue 3**

