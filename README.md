<div align="center">

# 🌐 NEXIS Connect

### Modern Social Media Platform

[![Java](https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://aws.amazon.com/corretto/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.2-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![React](https://img.shields.io/badge/React-19-61DAFB?style=for-the-badge&logo=react&logoColor=black)](https://react.dev/)
[![TypeScript](https://img.shields.io/badge/TypeScript-5.7-3178C6?style=for-the-badge&logo=typescript&logoColor=white)](https://www.typescriptlang.org/)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-4169E1?style=for-the-badge&logo=postgresql&logoColor=white)](https://www.postgresql.org/)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow?style=for-the-badge)](LICENSE)

**Полнофункциональная социальная сеть с авторизацией, лентой, лайками и админ-панелью.**

[Функции](#-функции) · [Быстрый старт](#-быстрый-старт) · [API](#-api) · [Документация](#-документация) · [Лицензия](#-лицензия)

</div>

---

## 📸 Скриншоты

| Landing Page | Лента | Admin Panel |
|:---:|:---:|:---:|
| Современная посадочная страница | Персональная лента постов | Управление пользователями |

---

## ✨ Функции

### 🔐 Аутентификация
- JWT авторизация (stateless)
- BCrypt хеширование паролей
- Три роли: `USER` · `MODERATOR` · `ADMIN`
- Защищённые маршруты

### 📝 Социальные функции
- Создание и просмотр постов
- Система лайков
- Комментарии
- Профили пользователей с аватарами

### 🛡️ Администрирование
- Панель управления (`/admin`)
- Управление пользователями и ролями
- Бан/разбан аккаунтов
- Удаление контента

### 🎨 Интерфейс
- Адаптивный дизайн (Desktop + Mobile)
- Тёмная / Светлая тема
- Плавные анимации (Framer Motion)
- Компоненты Shadcn/UI

---

## 🛠️ Технологический стек

<table>
<tr>
<td align="center" width="50%">

### Backend

| Технология | Версия |
|:---|:---|
| Java (Corretto) | 17 |
| Spring Boot | 3.2.2 |
| Spring Security | 6.2 |
| Spring Data JPA | 3.2 |
| Hibernate ORM | 6.4 |
| PostgreSQL | 16 |
| JWT (jjwt) | 0.12.6 |
| Maven | 3.9 |

</td>
<td align="center" width="50%">

### Frontend

| Технология | Версия |
|:---|:---|
| React | 19 |
| TypeScript | 5.7 |
| Vite | 6.4 |
| Tailwind CSS | 3.4 |
| Shadcn/UI | latest |
| Framer Motion | 12 |
| Zustand | 5 |
| TanStack Query | 5 |

</td>
</tr>
</table>

---

## 🚀 Быстрый старт

### Требования

- **Java 17+** — [Amazon Corretto](https://aws.amazon.com/corretto/)
- **PostgreSQL 16** — [Download](https://www.postgresql.org/download/)
- **Node.js 20+** — [Download](https://nodejs.org/)

### 1. Клонировать

```bash
git clone https://github.com/trollyHUB/social-media-platform.git
cd social-media-platform
```

### 2. Настроить БД

```sql
CREATE DATABASE socialmediaplatform;
```

### 3. Настроить конфиг

```bash
# Скопировать шаблон и заполнить свои данные:
cp src/main/resources/application.properties.example src/main/resources/application.properties
```

Отредактировать `application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/socialmediaplatform
spring.datasource.username=postgres
spring.datasource.password=YOUR_PASSWORD
app.jwt.secret=YOUR_SECRET_KEY_MINIMUM_32_CHARS
```

### 4. Запустить

```powershell
.\run.ps1
```

Или вручную:
```bash
./mvnw spring-boot:run
```

### 5. Открыть

```
http://localhost:8090
```

### 🔑 Тестовые аккаунты

| Email | Пароль | Роль |
|:---|:---|:---|
| `admin@nexis.com` | `admin123` | 🔴 ADMIN |
| `moder@nexis.com` | `moder123` | 🟡 MODERATOR |
| `tolegen@nexis.com` | `test123` | 🟢 USER |

> При первом запуске автоматически создаётся 12 тестовых пользователей.

---

## 📡 API

**Base URL:** `http://localhost:8090/api`

### Публичные эндпоинты

| Метод | URL | Описание |
|:---|:---|:---|
| `POST` | `/api/auth/register` | Регистрация |
| `POST` | `/api/auth/login` | Вход → JWT токен |
| `GET` | `/api/health` | Health check |
| `GET` | `/api/stats` | Статистика платформы |

### Авторизованные (Bearer JWT)

| Метод | URL | Описание |
|:---|:---|:---|
| `GET` | `/api/auth/me` | Текущий пользователь |
| `GET` | `/api/posts` | Лента постов |
| `POST` | `/api/posts` | Создать пост |
| `POST` | `/api/posts/{id}/like` | Лайкнуть пост |
| `GET` | `/api/users/{id}` | Профиль пользователя |

### Только ADMIN

| Метод | URL | Описание |
|:---|:---|:---|
| `GET` | `/api/admin/users` | Все пользователи |
| `PUT` | `/api/admin/users/{id}/role` | Изменить роль |
| `PUT` | `/api/admin/users/{id}/toggle-ban` | Бан/разбан |
| `DELETE` | `/api/admin/posts/{id}` | Удалить пост |

> 📮 Postman коллекция: [`Social_Media_API.postman_collection.json`](Social_Media_API.postman_collection.json)

---

## 🗄️ База данных

**8 таблиц** в PostgreSQL:

```
users · posts · comments · post_likes · follows · notifications · messages · bookmarks
```

SQL инициализация описана в [`database_init.sql`](database_init.sql) (локально, не публикуется в целях безопасности).

---

## 📁 Структура проекта

```
├── frontend/                  React 19 приложение
│   └── src/
│       ├── api/               API клиенты (Axios)
│       ├── components/        UI компоненты
│       ├── pages/             Страницы
│       ├── store/             Zustand state
│       └── types/             TypeScript типы
│
├── src/main/java/             Spring Boot API
│   └── com/tolegen/.../
│       ├── config/            Security, JWT, SPA
│       ├── controller/        REST контроллеры
│       ├── model/             JPA Entity
│       ├── repository/        Spring Data JPA
│       ├── service/           Бизнес-логика
│       └── dto/               Data Transfer Objects
│
├── docs/                      📚 Документация
│   ├── AI-GUIDE.md            Инструкция для разработки
│   ├── 01-project/            Roadmap, идеи, changelog
│   ├── 02-architecture/       Стек, API
│   ├── 05-university/         УМКД
│   └── database/              Схема БД, миграции
│
├── run.ps1                    Скрипт запуска
├── pom.xml                    Maven конфиг
└── КАК-ЗАПУСКАТЬ.md          Краткая инструкция
```

---

## 📚 Документация

> 📁 Документация хранится локально в папке `docs/` (не публикуется в репозитории).

| Документ | Описание |
|:---|:---|
| `docs/AI-GUIDE.md` | Главный гайд проекта |
| `docs/01-project/ROADMAP.md` | Дорожная карта |
| `docs/01-project/IDEAS.md` | Идеи (100+) |
| `docs/02-architecture/STACK.md` | Технологический стек |
| `docs/database/DATABASE.md` | Схема БД |
| `docs/05-university/COMPLIANCE.md` | Соответствие УМКД |

---

## 📊 Статус УМКД

Проект выполняет **13 из 15** лабораторных работ дисциплины *"Разработка веб-ориентированных приложений: Java EE"* (WBKA 3309).

| Требование | Реализация | Статус |
|:---|:---|:---:|
| Spring Boot | 3.2.2 | ✅ |
| Spring MVC | @RestController | ✅ |
| Spring Security | JWT + Roles | ✅ |
| Hibernate/JPA | Spring Data JPA | ✅ |
| PostgreSQL | 16 | ✅ |
| REST API | 14+ endpoints | ✅ |
| Git | GitHub | ✅ |
| Maven | pom.xml | ✅ |
| JUnit тесты | — | 🔜 |
| Деплой | — | 🔜 |

---

## 🤝 Contributing

Мы приветствуем вклад! Прочитайте [`CONTRIBUTING.md`](CONTRIBUTING.md) перед отправкой PR.

---

## 🔒 Безопасность

Обнаружили уязвимость? Прочитайте [`SECURITY.md`](SECURITY.md).

---

## 📄 Лицензия

Этот проект лицензирован под **MIT License** — подробности в файле [`LICENSE`](LICENSE).

```
MIT License — Copyright (c) 2026 Tolegen
Разрешено: использование, копирование, модификация, публикация, продажа.
Условие: указание авторских прав.
```

---

## 👨‍💻 Автор

**Толеген** — студент 3 курса, ЕНУ им. Л.Н. Гумилёва

- GitHub: [@trollyHUB](https://github.com/trollyHUB)
- Email: tolegen.manasov05@gmail.com

---

<div align="center">

**⭐ Star this repo if you like it! ⭐**

Made with ❤️ by [Tolegen](https://github.com/trollyHUB)


</div>
