<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>📱 Social Media Platform - Главная</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            padding: 20px;
            overflow-x: hidden;
        }

        /* Анимированные круги на фоне */
        .bg-circles {
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            z-index: 0;
            overflow: hidden;
        }

        .circle {
            position: absolute;
            border-radius: 50%;
            background: rgba(255,255,255,0.1);
            animation: float 20s infinite ease-in-out;
        }

        .circle:nth-child(1) { width: 80px; height: 80px; top: 10%; left: 10%; animation-delay: 0s; }
        .circle:nth-child(2) { width: 120px; height: 120px; top: 20%; right: 10%; animation-delay: 2s; }
        .circle:nth-child(3) { width: 60px; height: 60px; bottom: 15%; left: 20%; animation-delay: 4s; }
        .circle:nth-child(4) { width: 100px; height: 100px; bottom: 20%; right: 15%; animation-delay: 6s; }

        @keyframes float {
            0%, 100% { transform: translateY(0) rotate(0deg); }
            50% { transform: translateY(-30px) rotate(180deg); }
        }

        .container {
            position: relative;
            z-index: 1;
            max-width: 1200px;
            margin: 0 auto;
        }

        /* Header с анимацией */
        .header {
            text-align: center;
            margin-bottom: 50px;
            animation: fadeInDown 0.8s ease;
        }

        @keyframes fadeInDown {
            from { opacity: 0; transform: translateY(-30px); }
            to { opacity: 1; transform: translateY(0); }
        }

        .logo {
            font-size: 100px;
            margin-bottom: 20px;
            animation: bounce 2s infinite;
            display: inline-block;
        }

        @keyframes bounce {
            0%, 100% { transform: translateY(0); }
            50% { transform: translateY(-20px); }
        }

        h1 {
            color: white;
            font-size: 48px;
            text-shadow: 2px 2px 8px rgba(0,0,0,0.3);
            margin-bottom: 10px;
        }

        .subtitle {
            color: rgba(255,255,255,0.9);
            font-size: 20px;
            text-shadow: 1px 1px 3px rgba(0,0,0,0.2);
        }

        /* Карточки с возможностями */
        .cards-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
            gap: 25px;
            margin-bottom: 40px;
        }

        .card {
            background: white;
            padding: 30px;
            border-radius: 20px;
            box-shadow: 0 10px 30px rgba(0,0,0,0.2);
            transition: transform 0.3s, box-shadow 0.3s;
            animation: fadeInUp 0.8s ease;
            animation-fill-mode: both;
        }

        .card:nth-child(1) { animation-delay: 0.1s; }
        .card:nth-child(2) { animation-delay: 0.2s; }
        .card:nth-child(3) { animation-delay: 0.3s; }

        @keyframes fadeInUp {
            from { opacity: 0; transform: translateY(30px); }
            to { opacity: 1; transform: translateY(0); }
        }

        .card:hover {
            transform: translateY(-10px);
            box-shadow: 0 20px 40px rgba(0,0,0,0.3);
        }

        .card-icon {
            font-size: 60px;
            margin-bottom: 15px;
        }

        .card h3 {
            color: #333;
            font-size: 24px;
            margin-bottom: 15px;
        }

        .card-features {
            list-style: none;
            text-align: left;
        }

        .card-features li {
            padding: 8px 0;
            color: #666;
            position: relative;
            padding-left: 25px;
        }

        .card-features li::before {
            content: "✓";
            position: absolute;
            left: 0;
            color: #667eea;
            font-weight: bold;
            font-size: 18px;
        }

        /* Кнопки действий */
        .action-buttons {
            display: flex;
            gap: 20px;
            justify-content: center;
            flex-wrap: wrap;
            margin-bottom: 40px;
        }

        .btn {
            display: inline-flex;
            align-items: center;
            gap: 10px;
            padding: 18px 40px;
            text-decoration: none;
            border-radius: 15px;
            font-size: 20px;
            font-weight: bold;
            transition: all 0.3s ease;
            box-shadow: 0 5px 15px rgba(0,0,0,0.2);
        }

        .btn-primary {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
        }

        .btn-secondary {
            background: linear-gradient(135deg, #11998e 0%, #38ef7d 100%);
            color: white;
        }

        .btn:hover {
            transform: translateY(-5px) scale(1.05);
            box-shadow: 0 15px 30px rgba(0,0,0,0.3);
        }

        .btn:active {
            transform: translateY(-2px) scale(1.02);
        }

        /* Статистика */
        .stats {
            display: flex;
            gap: 20px;
            justify-content: center;
            flex-wrap: wrap;
            margin-bottom: 40px;
        }

        .stat-card {
            background: rgba(255,255,255,0.2);
            backdrop-filter: blur(10px);
            padding: 25px 40px;
            border-radius: 15px;
            color: white;
            text-align: center;
            min-width: 150px;
            animation: fadeInUp 0.8s ease;
            animation-delay: 0.4s;
            animation-fill-mode: both;
        }

        .stat-number {
            font-size: 48px;
            font-weight: bold;
            display: block;
            text-shadow: 2px 2px 4px rgba(0,0,0,0.3);
        }

        .stat-label {
            font-size: 16px;
            opacity: 0.9;
        }

        /* Технологии */
        .tech-section {
            background: rgba(255,255,255,0.15);
            backdrop-filter: blur(10px);
            padding: 30px;
            border-radius: 20px;
            text-align: center;
            color: white;
            animation: fadeInUp 0.8s ease;
            animation-delay: 0.5s;
            animation-fill-mode: both;
        }

        .tech-section h3 {
            font-size: 24px;
            margin-bottom: 20px;
            text-shadow: 1px 1px 3px rgba(0,0,0,0.2);
        }

        .tech-badges {
            display: flex;
            gap: 15px;
            justify-content: center;
            flex-wrap: wrap;
        }

        .tech-badge {
            background: white;
            color: #667eea;
            padding: 10px 20px;
            border-radius: 20px;
            font-weight: bold;
            box-shadow: 0 5px 15px rgba(0,0,0,0.2);
            transition: transform 0.3s;
        }

        .tech-badge:hover {
            transform: scale(1.1);
        }

        /* Footer */
        .footer {
            text-align: center;
            color: rgba(255,255,255,0.8);
            margin-top: 40px;
            font-size: 14px;
        }

        /* Адаптивность */
        @media (max-width: 768px) {
            h1 { font-size: 36px; }
            .logo { font-size: 70px; }
            .cards-grid { grid-template-columns: 1fr; }
            .btn { padding: 15px 30px; font-size: 18px; }
        }
    </style>
</head>
<body>
    <!-- Анимированный фон -->
    <div class="bg-circles">
        <div class="circle"></div>
        <div class="circle"></div>
        <div class="circle"></div>
        <div class="circle"></div>
    </div>

    <div class="container">
        <!-- Header -->
        <div class="header">
            <div class="logo">📱</div>
            <h1>Social Media Platform</h1>
            <p class="subtitle">Современная социальная сеть | Лабораторная работа №3-4</p>
        </div>

        <!-- Статистика -->
        <div class="stats">
            <div class="stat-card">
                <span class="stat-number">3</span>
                <span class="stat-label">👥 Пользователей</span>
            </div>
            <div class="stat-card">
                <span class="stat-number">3</span>
                <span class="stat-label">📝 Постов</span>
            </div>
            <div class="stat-card">
                <span class="stat-number">100%</span>
                <span class="stat-label">✨ Готовности</span>
            </div>
        </div>

        <!-- Карточки возможностей -->
        <div class="cards-grid">
            <div class="card">
                <div class="card-icon">📰</div>
                <h3>Лента постов</h3>
                <ul class="card-features">
                    <li>Просмотр постов (doGet)</li>
                    <li>Создание постов (doPost)</li>
                    <li>Система лайков ❤️</li>
                    <li>Удаление постов 🗑️</li>
                    <li>Поиск по автору 🔍</li>
                    <li>Относительное время</li>
                </ul>
            </div>

            <div class="card">
                <div class="card-icon">👥</div>
                <h3>Пользователи</h3>
                <ul class="card-features">
                    <li>Регистрация (doPost)</li>
                    <li>Уникальность username</li>
                    <li>Уникальность email</li>
                    <li>Цветные аватары</li>
                    <li>Счетчик постов</li>
                    <li>Биография</li>
                </ul>
            </div>

            <div class="card">
                <div class="card-icon">📊</div>
                <h3>Статистика</h3>
                <ul class="card-features">
                    <li>Общая статистика</li>
                    <li>Трендовые посты 🔥</li>
                    <li>Топ пользователи 🏆</li>
                    <li>Счетчик лайков</li>
                    <li>Количество комментариев</li>
                    <li>Рейтинги</li>
                </ul>
            </div>
        </div>

        <!-- Кнопки действий -->
        <div class="action-buttons">
            <a href="posts" class="btn btn-primary">
                <span>📰</span>
                <span>Открыть ленту</span>
            </a>
            <a href="users" class="btn btn-secondary">
                <span>👥</span>
                <span>Все пользователи</span>
            </a>
        </div>

        <!-- Технологии -->
        <div class="tech-section">
            <h3>🔧 Используемые технологии</h3>
            <div class="tech-badges">
                <div class="tech-badge">☕ Java 17</div>
                <div class="tech-badge">🌐 Jakarta Servlet 6.1</div>
                <div class="tech-badge">🐱 Apache Tomcat 10.x</div>
                <div class="tech-badge">📦 Maven</div>
                <div class="tech-badge">🎨 CSS3</div>
                <div class="tech-badge">🔐 Security</div>
            </div>
        </div>

        <!-- Footer -->
        <div class="footer">
            <p>💡 Проект демонстрирует работу методов doGet и doPost в Jakarta Servlets</p>
            <p>⚙️ Порт: 8090 | 🎓 Web Application Development | 2026</p>
        </div>
    </div>
</body>
</html>