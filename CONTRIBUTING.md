# 🤝 Contributing to NEXIS Connect

Спасибо за интерес к проекту! Мы приветствуем вклад от всех.

## 📋 Как внести вклад

### 1. Fork и Clone

```bash
# Fork репозитория через GitHub UI, затем:
git clone https://github.com/YOUR_USERNAME/nexis-social-pulse.git
cd nexis-social-pulse
```

### 2. Создай ветку

```bash
git checkout -b feature/my-awesome-feature
```

### 3. Внеси изменения

- Следуй существующему стилю кода
- Пиши понятные комментарии
- Обновляй документацию при необходимости

### 4. Commit и Push

```bash
git add .
git commit -m "feat: добавлена крутая функция"
git push origin feature/my-awesome-feature
```

### 5. Открой Pull Request

Перейди на GitHub и создай Pull Request с описанием изменений.

---

## 📝 Правила коммитов

Используем [Conventional Commits](https://www.conventionalcommits.org/):

| Префикс | Описание |
|----------|----------|
| `feat:` | Новая функция |
| `fix:` | Исправление бага |
| `docs:` | Изменения в документации |
| `style:` | Форматирование, пробелы |
| `refactor:` | Рефакторинг кода |
| `test:` | Добавление тестов |
| `chore:` | Обновление зависимостей |

**Примеры:**
```
feat: добавлена система подписок
fix: исправлен лайк при повторном нажатии
docs: обновлён README
```

---

## 🏗️ Структура проекта

```
├── src/main/java/    ← Backend (Spring Boot)
├── frontend/src/     ← Frontend (React)
├── docs/             ← Документация
└── pom.xml           ← Maven конфиг
```

---

## 🔧 Настройка окружения

### Требования:
- Java 17 (Amazon Corretto)
- PostgreSQL 16
- Node.js 20+

### Запуск:
```powershell
.\run.ps1
# → http://localhost:8090
```

---

## ✅ Чеклист перед PR

- [ ] Код компилируется без ошибок
- [ ] Нет console.log / System.out.println в продакшн коде
- [ ] Документация обновлена
- [ ] `.gitignore` не нарушен
- [ ] Нет секретов (паролей, токенов) в коде

---

## 🐛 Нашли баг?

Создайте [Issue](https://github.com/trollyHUB/nexis-social-pulse/issues) с описанием:
1. Что произошло
2. Что ожидалось
3. Шаги для воспроизведения
4. Скриншоты (если есть)

---

## 📞 Контакты

- GitHub: [@trollyHUB](https://github.com/trollyHUB)

---

*Спасибо за вклад! ❤️*
