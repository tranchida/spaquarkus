# GEMINI.md - SpaQuarkus Project Context (HTMX Edition)

This document provides essential context for development on the SpaQuarkus project.

## 🚀 Project Overview
SpaQuarkus has been migrated from a Quinoa/Vue 3 stack to a **modern Server-Side Rendering (SSR)** stack using **Quarkus Qute** and **htmx**.

- **Purpose**: A demonstration of high-performance, low-complexity web application development.
- **Backend**: Quarkus 3.31.3, Java 21, Jakarta REST.
- **Frontend**: Quarkus Qute Templates, [htmx](https://htmx.org/), [Pico CSS](https://picocss.com/).
- **Architecture**: Logic is server-side; htmx handles partial DOM updates for a SPA-like experience without a heavy JS framework.

## 🏗 Architecture & Design
- **Template Structure**:
  - `src/main/resources/templates`: Contains Qute templates.
  - `base.html`: Common layout with HTMX and CSS.
  - `index.html`: Home page.
  - `customers.html` / `customers-list.html`: Customer management (Full page vs Fragment).
  - `bills.html` / `bills-list.html`: Bill management (Full page vs Fragment).
- **API & UI Endpoints**:
  - `/customers`: Main UI for customers.
  - `/bills`: Main UI for bills.
  - REST resources return `TemplateInstance` (HTML) for standard requests and fragments for HTMX requests.
- **Data Persistence**: In-memory storage (`InMemoryCustomerStorage`, `InMemoryBillStorage`).

## 🛠 Building and Running

### Development Mode
```bash
mvn quarkus:dev
```
- **App**: [http://localhost:8080](http://localhost:8080)

### Production Build
```bash
mvn clean package -DskipTests
```

## 📝 Development Conventions

- **Backend**:
  - Use `TemplateInstance` for UI responses.
  - Use `@RestForm` to handle form submissions from HTMX.
  - Prefer returning partial HTML fragments (e.g., `customers-list.html`) for `POST`, `PUT`, `DELETE` requests triggered by HTMX.
- **Frontend (htmx)**:
  - Use `hx-target` to specify where to swap content.
  - Use `hx-confirm` for destructive actions.
  - Keep templates modular using Qute fragments or separate files for lists/rows.

## 📡 UI Routes

- `GET /`: Home page.
- `GET /customers`: Customer management page.
- `POST /customers`: Add customer (returns updated list fragment).
- `DELETE /customers/{id}`: Delete customer (returns updated list fragment).
- `GET /bills`: Bill management page.
- `POST /bills`: Add bill (returns updated list fragment).
- `DELETE /bills/{id}`: Delete bill (returns updated list fragment).
