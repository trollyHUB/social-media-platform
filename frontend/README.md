# ⚛️ NEXIS CONNECT — Frontend

> **React 19 + TypeScript + Vite 6 + Tailwind CSS + Framer Motion**

## 🚀 Быстрый старт

```bash
# 1. Перейди в папку
cd frontend

# 2. Установи зависимости
npm install

# 3. Запусти
npm run dev
# → http://localhost:8080
```

> ⚠️ Бэкенд (Spring Boot) должен работать на **http://localhost:8090**  
> Vite автоматически проксирует `/api` → `:8090`

---

## 🛠 Стек

| Библиотека | Версия | Назначение |
|-----------|--------|-----------|
| React | **19** | UI фреймворк |
| TypeScript | 5.8 | Типизация |
| Vite | **6** | Сборщик |
| Tailwind CSS | 3.4 | Стили |
| Framer Motion | 12 | Анимации |
| TanStack Query | 5 | Server state + кэш |
| Zustand | 5 | Client state |
| Axios | 1.13 | HTTP клиент |
| Shadcn/ui | latest | UI компоненты |
| React Router | 6 | Навигация |

## 📁 Структура

```
src/
├── api/           → axios клиент + API функции
├── components/
│   ├── ui/        → Shadcn/ui компоненты
│   ├── layout/    → Layout, LeftSidebar, RightSidebar, BottomNav
│   └── posts/     → PostCard, PostSkeleton, CreatePostForm
├── hooks/         → usePosts, useUsers, useDebounce, useIntersection...
├── pages/         → Home, Login, Register, Profile, Search, Chat...
├── store/         → authStore (Zustand + persist)
└── types/         → User, Post, Comment, Notification, Message...
```

## 📜 Команды

```bash
npm run dev      # Разработка (порт 8080)
npm run build    # Продакшн сборка
npm run preview  # Предпросмотр сборки
npm run lint     # ESLint
npm run test     # Vitest
```
