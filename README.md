# 🚀 SpaQuarkus (HTMX Edition)

> Application full-stack moderne utilisant Quarkus Qute (SSR) et htmx pour une expérience SPA sans JavaScript complexe.

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![Quarkus](https://img.shields.io/badge/Quarkus-3.31.3-blue.svg)](https://quarkus.io/)
[![htmx](https://img.shields.io/badge/htmx-2.0.2-blue.svg)](https://htmx.org/)
[![Pico CSS](https://img.shields.io/badge/Pico_CSS-2.0.6-pink.svg)](https://picocss.com/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

## 📋 Table des matières

- [À propos](#-à-propos)
- [Technologies](#-technologies)
- [Prérequis](#-prérequis)
- [Installation](#-installation)
- [Démarrage rapide](#-démarrage-rapide)
- [API & UI Endpoints](#-api--ui-endpoints)
- [Build & Déploiement](#-build--déploiement)
- [Tests](#-tests)
- [Architecture](#-architecture)
- [Contribution](#-contribution)

## 🎯 À propos

**SpaQuarkus** est une application de démonstration illustrant une approche moderne du **Server-Side Rendering (SSR)**. Contrairement aux architectures SPA traditionnelles (React/Vue), ce projet utilise la puissance du serveur pour générer du HTML et **htmx** pour les interactions dynamiques.

### Fonctionnalités

✅ Rendu côté serveur ultra-rapide avec **Quarkus Qute**  
✅ Interactions dynamiques sans framework JS lourd grâce à **htmx**  
✅ Design moderne et responsive avec **Pico CSS**  
✅ Gestion locale des assets via **Quarkus Web Bundler** & **mvnpm**  
✅ Stockage en mémoire thread-safe avec injection par constructeur  
✅ Build natif (GraalVM) supporté  
✅ Tests unitaires et d'intégration  

## 🛠 Technologies

### Backend & Frontend (SSR)
- **[Quarkus 3.31.3](https://quarkus.io/)** - Framework Java cloud-native
- **[Qute Templates](https://quarkus.io/guides/qute)** - Moteur de templates type-safe pour Quarkus
- **[htmx](https://htmx.org/)** - Permet d'accéder aux AJAX, CSS Transitions, WebSockets et Server Sent Events directement dans le HTML
- **[Pico CSS](https://picocss.com/)** - Framework CSS minimaliste pour un web élégant

### Asset Management
- **[Quarkus Web Bundler](https://github.com/quarkiverse/quarkus-web-bundler)** - Bundle automatiquement vos assets (JS, CSS)
- **[mvnpm](https://mvnpm.org/)** - Dépendances NPM directement dans Maven

## 📦 Prérequis

| Outil | Version minimale | Recommandé |
|-------|------------------|------------|
| JDK | 21 | 21+ |
| Maven | 3.8+ | Wrapper inclus |

> 💡 **Note** : Node.js n'est **plus requis** pour builder ou développer ce projet !

## 💻 Installation

```bash
# Cloner le projet
git clone https://github.com/votre-compte/spaquarkus.git
cd spaquarkus
```

## 🚀 Démarrage rapide

### Mode développement

```bash
./mvnw quarkus:dev
```

🌐 Application accessible sur : http://localhost:8080  
🔧 Dev UI Quarkus : http://localhost:8080/q/dev/

### Mode production (JAR unique)

```bash
# Build
./mvnw clean package -DskipTests

# Lancer l'application
java -jar target/quarkus-app/quarkus-run.jar
```

## 📡 API & UI Endpoints

L'application expose à la fois des pages HTML complètes et des fragments pour htmx.

### UI Routes (HTML)

| Route | Description |
|-------|-------------|
| `GET /` | Page d'accueil |
| `GET /customers` | Gestion des clients |
| `GET /bills` | Gestion des factures |

### Endpoints Fragments (htmx)

| Méthode | Endpoint | Description |
|---------|----------|-------------|
| `POST` | `/customers` | Ajoute un client et retourne le fragment de liste mis à jour |
| `DELETE` | `/customers/{id}` | Supprime un client et retourne le fragment mis à jour |
| `POST` | `/bills` | Ajoute une facture et retourne le fragment de liste mis à jour |
| `DELETE` | `/bills/{id}` | Supprime une facture et retourne le fragment mis à jour |

### API JSON

Des endpoints JSON restent disponibles pour l'interopérabilité :
- `GET /customers/api`
- `GET /bills/api`

## 🏗 Build & Déploiement

### Build standard (fast-jar)

```bash
./mvnw clean package
```

### Build natif (GraalVM)

```bash
./mvnw package -Dnative
# Exécuter le binaire natif
./target/*-runner
```

## 🧪 Tests

```bash
# Tous les tests (JUnit 5 + RestAssured)
./mvnw test
```

## 🏛 Architecture

```
spaquarkus/
├── src/
│   ├── main/
│   │   ├── java/ch/tranchida/sample/quarkus/
│   │   │   ├── bill/                        # Logique métier Bills
│   │   │   ├── customer/                    # Logique métier Customers
│   │   │   ├── HomeResource.java            # Contrôleur page d'accueil
│   │   │   └── JacksonConfig.java           # Configuration JSON
│   │   ├── resources/
│   │   │   ├── templates/                   # Templates Qute (.html)
│   │   │   │   ├── base.html                # Layout principal
│   │   │   │   ├── index.html               # Page d'accueil
│   │   │   │   └── ...-list.html            # Fragments htmx
│   │   │   ├── web/                         # Assets (JS/CSS) pour Web Bundler
│   │   │   │   ├── app.js
│   │   │   │   └── app.css
│   │   │   └── application.properties       # Config Quarkus
│   └── test/java/                           # Tests unitaires et intégration
├── pom.xml                                  # Dépendances (incluant mvnpm)
└── README.md
```

## 🤝 Contribution

1. Fork le projet
2. Créez une branche (`git checkout -b feature/AmazingFeature`)
3. Committez vos changements (`git commit -m 'Add AmazingFeature'`)
4. Push vers la branche (`git push origin feature/AmazingFeature`)
5. Ouvrez une Pull Request

## 📄 Licence

Ce projet est sous licence MIT. Voir le fichier [LICENSE](LICENSE) pour plus de détails.

---

**Développé avec ❤️ en utilisant Quarkus, Qute et htmx**
