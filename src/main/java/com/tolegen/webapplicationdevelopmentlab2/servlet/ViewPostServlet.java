package com.tolegen.webapplicationdevelopmentlab2.servlet;

import com.tolegen.webapplicationdevelopmentlab2.model.Post;
import com.tolegen.webapplicationdevelopmentlab2.model.SocialMedia;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Сервлет для просмотра отдельного поста
 */
@WebServlet(name = "viewPostServlet", value = "/post")
public class ViewPostServlet extends HttpServlet {

    private SocialMedia socialMedia;

    @Override
    public void init() {
        socialMedia = SocialMedia.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String postIdStr = request.getParameter("id");
        if (postIdStr == null) {
            response.sendRedirect("posts");
            return;
        }

        int postId = Integer.parseInt(postIdStr);
        Post post = socialMedia.findPostById(postId).orElse(null);

        if (post == null) {
            response.sendRedirect("posts");
            return;
        }

        out.println("<!DOCTYPE html>");
        out.println("<html lang='ru'>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("<title>📝 " + escapeHtml(post.getAuthor()) + " - Пост</title>");
        out.println("<style>");
        out.println("* { margin: 0; padding: 0; box-sizing: border-box; }");
        out.println("body { font-family: 'Segoe UI', Arial, sans-serif; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); min-height: 100vh; padding: 20px; }");
        out.println(".container { max-width: 800px; margin: 0 auto; }");
        out.println("h1 { color: white; text-align: center; margin-bottom: 20px; text-shadow: 2px 2px 4px rgba(0,0,0,0.3); }");
        out.println(".nav-links { display: flex; gap: 10px; margin-bottom: 20px; flex-wrap: wrap; }");
        out.println(".nav-link { display: inline-block; background: rgba(255,255,255,0.2); color: white; padding: 10px 20px; border-radius: 8px; text-decoration: none; transition: all 0.3s; }");
        out.println(".nav-link:hover { background: rgba(255,255,255,0.3); transform: translateY(-2px); }");
        out.println(".post-detail { background: white; padding: 30px; border-radius: 20px; box-shadow: 0 10px 30px rgba(0,0,0,0.2); }");
        out.println(".post-header { display: flex; align-items: center; margin-bottom: 20px; padding-bottom: 20px; border-bottom: 2px solid #f0f0f0; }");
        out.println(".avatar { width: 80px; height: 80px; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); border-radius: 50%; display: flex; align-items: center; justify-content: center; color: white; font-weight: bold; font-size: 32px; margin-right: 20px; }");
        out.println(".author-info { flex: 1; }");
        out.println(".author-name { font-size: 24px; font-weight: bold; color: #333; margin-bottom: 5px; }");
        out.println(".post-date { color: #888; font-size: 14px; }");
        out.println(".post-content { font-size: 18px; line-height: 1.8; color: #444; margin-bottom: 25px; white-space: pre-wrap; word-wrap: break-word; }");
        out.println(".post-stats { display: flex; gap: 30px; padding: 20px 0; border-top: 2px solid #f0f0f0; border-bottom: 2px solid #f0f0f0; margin-bottom: 20px; }");
        out.println(".stat { display: flex; align-items: center; gap: 8px; font-size: 16px; color: #666; }");
        out.println(".post-actions { display: flex; gap: 15px; }");
        out.println(".action-btn { flex: 1; padding: 15px; border: none; border-radius: 10px; font-size: 16px; cursor: pointer; transition: all 0.3s; text-decoration: none; display: flex; align-items: center; justify-content: center; gap: 8px; }");
        out.println(".like-btn { background: linear-gradient(135deg, #ff6b6b 0%, #ee5a6f 100%); color: white; }");
        out.println(".like-btn:hover { transform: translateY(-2px); box-shadow: 0 5px 15px rgba(238, 90, 111, 0.4); }");
        out.println(".delete-btn { background: linear-gradient(135deg, #95a5a6 0%, #7f8c8d 100%); color: white; }");
        out.println(".delete-btn:hover { transform: translateY(-2px); box-shadow: 0 5px 15px rgba(127, 140, 141, 0.4); }");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");
        out.println("<div class='container'>");
        out.println("<h1>📝 Просмотр поста</h1>");

        out.println("<div class='nav-links'>");
        out.println("<a href='/' class='nav-link'>🏠 Главная</a>");
        out.println("<a href='posts' class='nav-link'>📰 Вернуться к ленте</a>");
        out.println("<a href='users' class='nav-link'>👥 Пользователи</a>");
        out.println("</div>");

        String initial = post.getAuthor().substring(0, 1).toUpperCase();

        out.println("<div class='post-detail'>");
        out.println("<div class='post-header'>");
        out.println("<div class='avatar'>" + initial + "</div>");
        out.println("<div class='author-info'>");
        out.println("<div class='author-name'>@" + escapeHtml(post.getAuthor()) + "</div>");
        out.println("<div class='post-date'>📅 " + post.getDetailedDate() + " • ⏰ " + post.getRelativeTime() + "</div>");
        out.println("</div>");
        out.println("</div>");

        out.println("<div class='post-content'>" + escapeHtml(post.getContent()) + "</div>");

        out.println("<div class='post-stats'>");
        out.println("<div class='stat'><span style='font-size:20px;'>❤️</span> <strong>" + post.getLikes() + "</strong> лайков</div>");
        out.println("<div class='stat'><span style='font-size:20px;'>💬</span> <strong>" + post.getCommentsCount() + "</strong> комментариев</div>");
        out.println("<div class='stat'><span style='font-size:20px;'>👁️</span> <strong>" + (post.getLikes() * 5 + 42) + "</strong> просмотров</div>");
        out.println("</div>");

        out.println("<div class='post-actions'>");
        out.println("<a href='posts?like=" + post.getId() + "' class='action-btn like-btn' onclick='return confirm(\"Поставить лайк этому посту?\")'>❤️ Лайкнуть</a>");
        out.println("<a href='posts?delete=" + post.getId() + "' class='action-btn delete-btn' onclick='return confirm(\"Вы уверены, что хотите удалить этот пост?\")'>🗑️ Удалить</a>");
        out.println("</div>");

        out.println("</div>");
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    }

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
