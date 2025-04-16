# Student Information System

This is a full-stack Student Information System project built with:

- 🎯 **Frontend**: React + TypeScript + Vite
- 🚀 **Backend**: Spring Boot (Java) + PostgreSQL (e.g. Supabase)

---

## 📁 Project Structure

```
StudentInformationSystem/
├── react-ts-app/       # Frontend source code
├── spring-boot-app/    # Backend source code
```

---

## 🧩 Tech Stack

### Frontend (`react-ts-app`)

- React 18
- TypeScript
- Vite
- Tailwind CSS (if added)

### Backend (`spring-boot-app`)

- Spring Boot
- Gradle (Kotlin DSL)
- PostgreSQL (e.g. via Supabase)
- RESTful API

---

## 🚀 Getting Started

### 🔧 Backend (Spring Boot)

1. Go to the backend directory:
   
   ```bash
   cd spring-boot-app
   ```

2. Add your database credentials in:
   
   ```
   src/main/resources/application.properties
   ```

3. Build and run the Spring Boot app:
   
   ```bash
   ./gradlew bootRun
   ```

By default, it runs at `http://localhost:8080`

---

### 🌐 Frontend (React)

1. Go to the frontend directory:
   
   ```bash
   cd react-ts-app
   ```

2. Install dependencies:
   
   ```bash
   npm install
   ```

3. Run the development server:
   
   ```bash
   npm run dev
   ```

By default, it runs at `http://localhost:5173`

> You may set up a proxy to forward `/api` calls to the Spring Boot backend in `vite.config.ts`.

---

## 🌍 Environment Configuration

- Don't commit `application.properties` or `.env` files with real credentials.
- Create a `.env` or `application-sample.properties` to share config templates.

---

## 📄 License

This project is for educational and internship purposes.
