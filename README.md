# Pastebin Clone

A robust, full-stack Pastebin application featuring a **Spring Boot** backend and **Angular** frontend.

## Features
- **Create Pastes**: Set content, TTL (time-to-live), and max view limits.
- **Strict Constraints**: Server-side enforcement of expiration and view counts.
- **Concurrency Safety**: Pessimistic locking prevents race conditions on view counts.
- **Deterministic Testing**: Supports time-travel testing via headers.
- **Secure viewing**: XSS-safe text-only view mode.

## Project Structure
- `pastebin/` - Spring Boot Backend API
- `pastebin-app/` - Angular Frontend UI

---

## Configuration

The application uses environment variables for configuration, with sensible defaults for local development.

| Variable | Description | Backend Default | Frontend Usage |
|----------|-------------|-----------------|----------------|
| `DB_HOST` | Database Host | `localhost` | N/A |
| `DB_NAME` | Database Name | `pastebin` | N/A |
| `DB_USER` | Database User | `postgres` | N/A |
| `DB_PASSWORD` | Database Password | `yourpassword` | N/A |
| `FRONTEND_URL`| Frontend Origin | `http://localhost:4200` | N/A |
| `PORT` | Backend Port | `8080` | N/A |

---

## Getting Started (Local Development)

### 1. Database Setup
Ensure you have PostgreSQL running locally with a database named `pastebin`.

### 2. Backend (Spring Boot)
Navigate to the `pastebin` directory:
```bash
cd pastebin
./mvnw spring-boot:run
```
The API will start at `http://localhost:8080`.

### 3. Frontend (Angular)
Navigate to the `pastebin-app` directory:
```bash
cd pastebin-app
npm install
ng serve
```
Open current browser at `http://localhost:4200`.

---

## Deployment Guide

### Backend (Render / Cloud)
Set the environment variables listed in the configuration table.
- **Build Command**: `./mvnw clean package`
- **Run Command**: `java -jar target/pastebin-0.0.1-SNAPSHOT.jar`
- **Env Vars**: Set `FRONTEND_URL` to your production frontend domain (e.g., `https://your-app.vercel.app`).

### Frontend (Vercel / Netlify)
The frontend automatically detects the production environment during build.
- **Build Command**: `ng build --configuration production`
- **Output Directory**: `dist/pastebin-app/browser`
- It is pre-configured to connect to: `https://pastebin-backend-kwjl.onrender.com` (Update `src/environments/environment.prod.ts` if your backend URL changes).

---

## API Reference

### `POST /api/pastes`
Create a new paste.
```json
{
  "content": "Secret content",
  "ttl_seconds": 3600,
  "max_views": 5
}
```

### `GET /api/pastes/{id}`
Retrieve paste metadata (JSON). Returns `404` if expired/not found.

### `GET /p/{id}`
View paste content (Raw Text/HTML).
