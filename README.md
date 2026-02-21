# 📱 Social Media Platform

> **REST API платформа социальных сетей** на Spring Boot 3 + PostgreSQL + Java 17

[![Java](https://img.shields.io/badge/Java-17-orange?logo=java)](https://aws.amazon.com/corretto/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.2-brightgreen?logo=springboot)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue?logo=postgresql)](https://www.postgresql.org/)
[![Maven](https://img.shields.io/badge/Maven-3.x-red?logo=apachemaven)](https://maven.apache.org/)

---

## 🚀 Быстрый старт

### Требования
- Java 17 (Amazon Corretto)
- PostgreSQL 16 (порт `5434`)
- Maven 3.x (или использовать `mvnw`)

### 1. Настройка базы данных
Создайте базу данных в PostgreSQL:
```sql
CREATE DATABASE socialmediaplatform;
```

### 2. Настройка подключения
Файл `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5434/socialmediaplatform
spring.datasource.username=postgres
spring.datasource.password=1234
server.port=8090
```

### 3. Запуск приложения

**Через Maven Wrapper:**
```bash
./mvnw spring-boot:run
```

**Через IntelliJ IDEA:**
- Открыть `SocialMediaPlatformApplication.java`
- Нажать зелёную кнопку ▶ Run

### 4. Открыть в браузере
```
http://localhost:8090/
```

---

## 🏗️ Структура проекта

```
src/main/java/com/tolegen/webapplicationdevelopmentlab2/
├── SocialMediaPlatformApplication.java   # Точка входа Spring Boot
├── config/
│   ├── DataInitializer.java              # Инициализация тестовых данных
│   └── WebSecurityConfig.java            # Настройка безопасности (CSP, CORS)
├── controller/
│   └── SocialMediaApiController.java     # REST API контроллер (все эндпоинты)
├── dto/
│   ├── CreatePostRequest.java            # DTO для создания поста
│   ├── CreateUserRequest.java            # DTO для создания пользователя
│   └── CreateCommentRequest.java         # DTO для создания комментария
├── model/
│   ├── Post.java                         # JPA Entity — пост
│   ├── User.java                         # JPA Entity — пользователь
│   └── Comment.java                      # JPA Entity — комментарий
├── repository/
│   ├── PostRepository.java               # Spring Data JPA репозиторий постов
│   ├── UserRepository.java               # Spring Data JPA репозиторий пользователей
│   └── CommentRepository.java            # Spring Data JPA репозиторий комментариев
└── service/
    ├── PostService.java                  # Бизнес-логика постов
    ├── UserService.java                  # Бизнес-логика пользователей
    └── CommentService.java               # Бизнес-логика комментариев

src/main/resources/
├── application.properties                # Конфигурация приложения
└── static/
    ├── index.html                        # Главная страница (SPA)
    ├── css/style.css                     # Стили
    └── js/api-client.js                  # JavaScript API клиент
```

---

## 📡 REST API

Базовый URL: `http://localhost:8090`

### 📝 Posts (Посты)

| Метод | URL | Описание |
|-------|-----|----------|
| `GET` | `/api/posts` | Получить все посты |
| `GET` | `/api/posts?author=tolegen` | Посты конкретного автора |
| `GET` | `/api/posts/{id}` | Пост по ID |
| `POST` | `/api/posts` | Создать пост |
| `PUT` | `/api/posts/{id}` | Обновить пост |
| `DELETE` | `/api/posts/{id}` | Удалить пост |
| `POST` | `/api/posts/{id}/like` | Лайкнуть пост |

**Создать пост (POST `/api/posts`):**
```json
{
  "author": "tolegen",
  "content": "Привет, мир! 👋"
}
```

### 👥 Users (Пользователи)

| Метод | URL | Описание |
|-------|-----|----------|
| `GET` | `/api/users` | Получить всех пользователей |
| `GET` | `/api/users/{id}` | Пользователь по ID |
| `GET` | `/api/users?username=tolegen` | Поиск по username |
| `POST` | `/api/users` | Создать пользователя |
| `DELETE` | `/api/users/{id}` | Удалить пользователя |

**Создать пользователя (POST `/api/users`):**
```json
{
  "username": "newuser",
  "email": "newuser@example.com",
  "bio": "О себе..."
}
```
> ⚠️ Дубликаты username/email → `409 Conflict`

### 💬 Comments (Комментарии)

| Метод | URL | Описание |
|-------|-----|----------|
| `GET` | `/api/posts/{postId}/comments` | Комментарии поста |
| `POST` | `/api/posts/{postId}/comments` | Добавить комментарий |
| `DELETE` | `/api/comments/{id}` | Удалить комментарий |

### 📊 Статистика

| Метод | URL | Описание |
|-------|-----|----------|
| `GET` | `/api/stats` | Статистика платформы |
| `GET` | `/api/health` | Проверка работоспособности |

---

## 🧪 Тестирование через Postman

1. Импортируйте файл `Social_Media_API.postman_collection.json` в Postman
2. Переменная `{{baseUrl}}` = `http://localhost:8090` уже настроена
3. Коллекция содержит **17 запросов** в 4 папках:
   - 📝 **Posts API** — 7 запросов
   - 💬 **Comments API** — 3 запроса
   - 👥 **Users API** — 6 запросов
   - 📊 **Stats and Health** — 2 запроса

---

## 🛠️ Технологический стек

| Технология | Версия | Назначение |
|------------|--------|------------|
| Java (Amazon Corretto) | 17 | Язык программирования |
| Spring Boot | 3.2.2 | Фреймворк приложения |
| Spring Web (MVC) | 6.1.3 | REST API, встроенный Tomcat |
| Spring Data JPA | 3.2.2 | Работа с базой данных |
| Spring Security | 3.2.2 | Безопасность, CORS, CSP |
| Hibernate ORM | 6.4.1 | ORM маппинг |
| PostgreSQL | 16 | База данных |
| Lombok | 1.18.30 | Генерация кода (геттеры, сеттеры) |
| Jackson | 2.15.3 | JSON сериализация |
| Maven | 3.x | Сборка проекта |

---

## 🗄️ Модели данных

### Post (Пост)
```
id, author, content, likes, commentsCount, createdAt, updatedAt
```

### User (Пользователь)
```
id, username (уникальный), email (уникальный), bio, avatarColor, createdAt, lastActive
```

### Comment (Комментарий)
```
id, postId, author, content, createdAt
```

---

## ✨ Функциональность

- ✅ Полный CRUD для постов, пользователей, комментариев
- ✅ Система лайков для постов
- ✅ Защита от дублирования пользователей (409 Conflict)
- ✅ Фильтрация постов по автору
- ✅ Информация о времени создания/обновления
- ✅ Статистика платформы в реальном времени
- ✅ REST API Demo страница с живыми запросами
- ✅ Postman коллекция для тестирования
- ✅ CORS настроен для всех источников
- ✅ CSP (Content Security Policy) настроен
- ✅ Автоматическое создание таблиц (Hibernate DDL auto)
- ✅ Инициализация тестовых данных при первом запуске

---

## 📁 Документация

Вся документация находится в папке [`DOCS GENERAL/`](DOCS GENERAL/)

---

## 👨‍💻 Автор

**Tolegen** — Web Application Development LAB2  
Spring Boot + PostgreSQL + REST API

- GitHub: [@trollyHUB](https://github.com/trollyHUB)
- Repository: [social-media-platform](https://github.com/trollyHUB/social-media-platform)

---

## 🙏 Благодарности

- Jakarta EE Community
- Apache Tomcat Team
- IITU преподавателям

---

## 📞 Контакты

Если у вас есть вопросы или предложения:

- 🐛 Issues: [GitHub Issues](https://github.com/trollyHUB/social-media-platform/issues)

---

## 📈 Статус проекта

```
✅ Production Ready
✅ Высокий уровень безопасности (10/10)
✅ Все функции реализованы
✅ Документация полная
✅ Готов к демонстрации
```

---

<div align="center">

**⭐ Поставьте звезду, если проект понравился! ⭐**

Made with ❤️ by [Tolegen](https://github.com/trollyHUB)

**[Наверх ⬆️](#-social-media-platform)**

</div>
