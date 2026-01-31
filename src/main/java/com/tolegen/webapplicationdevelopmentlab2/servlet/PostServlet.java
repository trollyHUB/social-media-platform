package com.tolegen.webapplicationdevelopmentlab2.servlet;

import com.tolegen.webapplicationdevelopmentlab2.model.Post;
import com.tolegen.webapplicationdevelopmentlab2.model.SocialMedia;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Сервлет для работы с постами
 * doGet - получение списка постов
 * doPost - создание нового поста
 */
@WebServlet(name = "postServlet", value = "/posts")
public class PostServlet extends HttpServlet {

    private SocialMedia socialMedia;

    @Override
    public void init() {
        socialMedia = SocialMedia.getInstance();
    }

    /**
     * GET запрос - получение всех постов
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        // Обработка лайка
        String likePostId = request.getParameter("like");
        if (likePostId != null) {
            try {
                int postId = Integer.parseInt(likePostId);
                socialMedia.likePost(postId);
            } catch (NumberFormatException e) {
                // Игнорируем некорректный ID
            }
        }

        // Обработка удаления поста
        String deletePostId = request.getParameter("delete");
        if (deletePostId != null) {
            try {
                int postId = Integer.parseInt(deletePostId);
                socialMedia.deletePost(postId);
            } catch (NumberFormatException e) {
                // Игнорируем некорректный ID
            }
        }

        // Поиск по автору
        String searchAuthor = request.getParameter("author");
        List<Post> posts;
        if (searchAuthor != null && !searchAuthor.trim().isEmpty()) {
            posts = socialMedia.getPostsByAuthor(searchAuthor.trim());
        } else {
            posts = socialMedia.getAllPosts();
        }

        out.println("<!DOCTYPE html>");
        out.println("<html lang='ru'>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("<title>📱 Social Media - Лента новостей</title>");
        out.println("<style>");
        out.println("* { margin: 0; padding: 0; box-sizing: border-box; }");
        out.println("body { font-family: 'Segoe UI', Arial, sans-serif; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); min-height: 100vh; padding: 20px; }");
        out.println(".container { max-width: 650px; margin: 0 auto; }");
        out.println("h1 { color: white; text-align: center; margin-bottom: 20px; text-shadow: 2px 2px 4px rgba(0,0,0,0.3); font-size: 36px; }");
        out.println(".stats { background: rgba(255,255,255,0.2); backdrop-filter: blur(10px); padding: 15px; border-radius: 10px; color: white; text-align: center; margin-bottom: 20px; }");
        out.println(".nav-links { display: flex; gap: 10px; margin-bottom: 20px; flex-wrap: wrap; }");
        out.println(".nav-link { display: inline-block; background: rgba(255,255,255,0.2); color: white; padding: 10px 20px; border-radius: 8px; text-decoration: none; transition: all 0.3s; }");
        out.println(".nav-link:hover { background: rgba(255,255,255,0.3); transform: translateY(-2px); }");
        out.println(".search-form { background: white; padding: 15px; border-radius: 15px; margin-bottom: 20px; box-shadow: 0 5px 15px rgba(0,0,0,0.1); }");
        out.println(".search-form input { width: calc(100% - 120px); padding: 10px; border: 2px solid #e0e0e0; border-radius: 8px; font-size: 14px; }");
        out.println(".search-form button { padding: 10px 20px; background: #667eea; color: white; border: none; border-radius: 8px; cursor: pointer; margin-left: 10px; }");
        out.println(".search-form button:hover { background: #5568d3; }");
        out.println(".post-form { background: white; padding: 25px; border-radius: 15px; margin-bottom: 20px; box-shadow: 0 10px 30px rgba(0,0,0,0.2); }");
        out.println(".post-form h3 { margin-bottom: 15px; color: #333; }");
        out.println(".post-form input, .post-form textarea { width: 100%; padding: 12px; margin-bottom: 10px; border: 2px solid #e0e0e0; border-radius: 8px; font-size: 14px; font-family: inherit; }");
        out.println(".post-form textarea { resize: vertical; min-height: 100px; }");
        out.println(".char-counter { text-align: right; font-size: 12px; color: #888; margin-top: -8px; margin-bottom: 10px; }");
        out.println(".post-form button { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: white; border: none; padding: 12px 25px; border-radius: 8px; cursor: pointer; font-size: 16px; width: 100%; transition: all 0.3s; }");
        out.println(".post-form button:hover { opacity: 0.9; transform: translateY(-2px); }");
        out.println(".post { background: white; padding: 20px; border-radius: 15px; margin-bottom: 15px; box-shadow: 0 5px 15px rgba(0,0,0,0.1); transition: transform 0.3s; }");
        out.println(".post:hover { transform: translateY(-3px); box-shadow: 0 8px 20px rgba(0,0,0,0.15); }");
        out.println(".post-header { display: flex; align-items: center; margin-bottom: 12px; }");
        out.println(".avatar { width: 50px; height: 50px; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); border-radius: 50%; display: flex; align-items: center; justify-content: center; color: white; font-weight: bold; font-size: 20px; margin-right: 12px; }");
        out.println(".post-author { font-weight: bold; color: #333; font-size: 16px; }");
        out.println(".post-date { font-size: 12px; color: #888; margin-top: 2px; }");
        out.println(".post-content { color: #444; line-height: 1.6; margin-bottom: 15px; font-size: 15px; white-space: pre-wrap; word-wrap: break-word; }");
        out.println(".post-actions { display: flex; gap: 20px; padding-top: 10px; border-top: 1px solid #f0f0f0; }");
        out.println(".like-btn { background: none; border: none; color: #e74c3c; cursor: pointer; font-size: 15px; display: flex; align-items: center; gap: 5px; padding: 5px 10px; border-radius: 5px; transition: all 0.3s; text-decoration: none; }");
        out.println(".like-btn:hover { background: #ffe5e5; color: #c0392b; transform: scale(1.1); }");
        out.println(".delete-btn { background: none; border: none; color: #95a5a6; cursor: pointer; font-size: 14px; display: flex; align-items: center; gap: 5px; padding: 5px 10px; border-radius: 5px; transition: all 0.3s; text-decoration: none; }");
        out.println(".delete-btn:hover { background: #fff3cd; color: #e67e22; transform: scale(1.05); }");
        out.println(".empty-state { background: white; padding: 40px; border-radius: 15px; text-align: center; color: #888; }");
        out.println("@keyframes fadeIn { from { opacity: 0; transform: translateY(20px); } to { opacity: 1; transform: translateY(0); } }");
        out.println(".post { animation: fadeIn 0.5s ease; }");
        out.println("@media (max-width: 600px) { .container { padding: 10px; } h1 { font-size: 28px; } }");
        out.println("</style>");
        out.println("<script>");
        out.println("function updateCharCount() {");
        out.println("  const textarea = document.getElementById('content');");
        out.println("  const counter = document.getElementById('charCounter');");
        out.println("  const count = textarea.value.length;");
        out.println("  counter.textContent = count + '/500';");
        out.println("  counter.style.color = count > 450 ? '#e74c3c' : '#888';");
        out.println("}");
        out.println("</script>");
        out.println("</head>");
        out.println("<body>");
        out.println("<div class='container'>");
        out.println("<h1>📱 Social Media Platform</h1>");

        // Статистика
        out.println("<div class='stats'>");
        out.println("<strong>👥 Пользователей: " + socialMedia.getTotalUsersCount() + "</strong> | ");
        out.println("<strong>📝 Постов: " + socialMedia.getTotalPostsCount() + "</strong>");
        if (searchAuthor != null && !searchAuthor.isEmpty()) {
            out.println(" | 🔍 Найдено: " + posts.size());
        }
        out.println("</div>");

        // Навигация
        out.println("<div class='nav-links'>");
        out.println("<a href='/' class='nav-link'>🏠 Главная</a>");
        out.println("<a href='users' class='nav-link'>👥 Пользователи</a>");
        out.println("<a href='stats' class='nav-link'>📊 Статистика</a>");
        out.println("<a href='posts' class='nav-link'>🔄 Обновить</a>");
        out.println("</div>");

        // Форма поиска
        out.println("<div class='search-form'>");
        out.println("<form method='GET' action='posts' style='display:flex;align-items:center;'>");
        out.println("<input type='text' name='author' placeholder='🔍 Поиск по имени автора...' value='" + (searchAuthor != null ? escapeHtml(searchAuthor) : "") + "'>");
        out.println("<button type='submit'>Найти</button>");
        if (searchAuthor != null && !searchAuthor.isEmpty()) {
            out.println("<a href='posts' style='margin-left:10px;padding:10px;background:#e74c3c;color:white;border-radius:8px;text-decoration:none;'>✕</a>");
        }
        out.println("</form>");
        out.println("</div>");

        // Форма для нового поста
        out.println("<div class='post-form'>");
        out.println("<h3>✍️ Создать новый пост</h3>");
        out.println("<form method='POST' action='posts'>");
        out.println("<input type='text' name='author' placeholder='Ваше имя' required maxlength='50'>");
        out.println("<textarea id='content' name='content' placeholder='О чём вы думаете? Поделитесь своими мыслями...' required maxlength='500' oninput='updateCharCount()'></textarea>");
        out.println("<div class='char-counter' id='charCounter'>0/500</div>");
        out.println("<button type='submit'>📤 Опубликовать</button>");
        out.println("</form>");
        out.println("</div>");

        // Список постов
        if (searchAuthor != null && !searchAuthor.isEmpty()) {
            out.println("<h2 style='color:white;margin-bottom:15px;'>🔍 Результаты поиска</h2>");
        } else {
            out.println("<h2 style='color:white;margin-bottom:15px;'>📰 Лента новостей</h2>");
        }

        if (posts.isEmpty()) {
            out.println("<div class='empty-state'>");
            if (searchAuthor != null && !searchAuthor.isEmpty()) {
                out.println("<p style='font-size:18px;margin-bottom:10px;'>🔍 Ничего не найдено</p>");
                out.println("<p>Попробуйте изменить критерии поиска</p>");
            } else {
                out.println("<p style='font-size:18px;margin-bottom:10px;'>📝 Пока нет постов</p>");
                out.println("<p>Будьте первым, кто создаст пост!</p>");
            }
            out.println("</div>");
        } else {
            for (Post post : posts) {
                String initial = post.getAuthor().substring(0, 1).toUpperCase();
                int views = post.getLikes() * 5 + 42; // Симулируем просмотры

                out.println("<div class='post'>");
                out.println("<div class='post-header'>");
                out.println("<div class='avatar'>" + initial + "</div>");
                out.println("<div style='flex:1;'>");
                out.println("<div class='post-author'>@" + escapeHtml(post.getAuthor()) + "</div>");
                out.println("<div class='post-date' title='" + post.getDetailedDate() + "'>⏰ " + post.getRelativeTime() + " • 📅 " + post.getFormattedDate() + "</div>");
                out.println("</div>");
                out.println("</div>");
                out.println("<div class='post-content'>" + escapeHtml(post.getContent()) + "</div>");
                out.println("<div class='post-actions'>");
                out.println("<a href='posts?like=" + post.getId() + "' class='like-btn' title='Поставить лайк'>❤️ " + post.getLikes() + "</a>");
                out.println("<a href='post?id=" + post.getId() + "' class='like-btn' style='color:#667eea;' title='Открыть пост'>💬 " + post.getCommentsCount() + "</a>");
                out.println("<span class='like-btn' style='color:#95a5a6;cursor:default;' title='Просмотры'>👁️ " + views + "</span>");
                out.println("<a href='posts?delete=" + post.getId() + "' class='delete-btn' onclick='return confirm(\"Вы уверены, что хотите удалить этот пост?\")' title='Удалить пост'>🗑️ Удалить</a>");
                out.println("</div>");
                out.println("</div>");
            }
        }

        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    }

    /**
     * POST запрос - создание нового поста
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");

        String author = request.getParameter("author");
        String content = request.getParameter("content");

        if (author != null && !author.trim().isEmpty() &&
            content != null && !content.trim().isEmpty()) {
            socialMedia.addPost(author.trim(), content.trim());
        }

        // Перенаправление на GET для отображения списка
        response.sendRedirect("posts");
    }

    // Защита от XSS
    private String escapeHtml(String text) {
        if (text == null) return "";
        return text
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }
}
