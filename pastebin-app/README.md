# Pastebin Frontend (Angular)

A modern, responsive user interface for the Pastebin API.

## Requirements
- Node.js 18+
- Angular CLI

## Setup
1. Install dependencies:
   ```bash
   npm install
   ```

2. Run the development server:
   ```bash
   ng serve
   ```
   Open your browser at `http://localhost:4200`.

## Configuration
The application uses Angular environment files to manage API connections.

### Local Development
- File: `src/environments/environment.ts`
- API URL: `http://localhost:8080/api/pastes`
- Usage: Runs automatically with `ng serve`.

### Production
- File: `src/environments/environment.prod.ts`
- API URL: `https://pastebin-backend-kwjl.onrender.com/api/pastes`
- Usage: Runs automatically when building for production.
   ```bash
   ng build --configuration production
   ```
   The build process swaps the environment files, ensuring the deployed app connects to the production backend.

## Features
- **Create Paste**: Simple interface to create pastes with Content, TTL (seconds), and Max Views.
- **Input Validation**: Prevents invalid inputs (negative numbers) and double-submissions.
- **View Paste**: Displays paste content along with remaining views and expiration time.
- **Error Handling**: Clearly displays specific errors from the backend (e.g., "Paste not found" vs "Paste expired").
