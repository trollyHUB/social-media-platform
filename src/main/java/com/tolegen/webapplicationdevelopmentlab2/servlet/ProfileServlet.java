package com.tolegen.webapplicationdevelopmentlab2.servlet;

import com.tolegen.webapplicationdevelopmentlab2.model.Post;
import com.tolegen.webapplicationdevelopmentlab2.model.SocialMedia;
import com.tolegen.webapplicationdevelopmentlab2.model.User;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Сервлет для просмотра профиля пользователя
 */
@WebServlet(name = "profileServlet", value = "/profile")
public class ProfileServlet extends HttpServlet {

    private SocialMedia socialMedia;

    @Override
    public void init() {
        socialMedia = SocialMedia.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String username = request.getParameter("user");
        if (username == null) {
            response.sendRedirect("users");
            return;
        }

        User user = socialMedia.findUserByUsername(username).orElse(null);
        if (user == null) {
            response.sendRedirect("users");
            return;
        }

        List<Post> userPosts = socialMedia.getPostsByAuthor(username);
        int totalLikes = userPosts.stream().mapToInt(Post::getLikes).sum();

        out.println("<!DOCTYPE html>");
        out.println("<html lang='ru'>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("<title>👤 @" + escapeHtml(username) + " - Профиль</title>");
        out.println("<style>");
        out.println("* { margin: 0; padding: 0; box-sizing: border-box; }");
        out.println("body { font-family: 'Segoe UI', Arial, sans-serif; background: linear-gradient(135deg, #11998e 0%, #38ef7d 100%); min-height: 100vh; padding: 20px; }");
        out.println(".container { max-width: 900px; margin: 0 auto; }");
        out.println("h1 { color: white; text-align: center; margin-bottom: 20px; text-shadow: 2px 2px 4px rgba(0,0,0,0.3); }");
        out.println(".nav-links { display: flex; gap: 10px; margin-bottom: 20px; flex-wrap: wrap; justify-content: center; }");
        out.println(".nav-link { display: inline-block; background: rgba(255,255,255,0.2); color: white; padding: 10px 20px; border-radius: 8px; text-decoration: none; transition: all 0.3s; }");
        out.println(".nav-link:hover { background: rgba(255,255,255,0.3); transform: translateY(-2px); }");
        out.println(".profile-card { background: white; padding: 30px; border-radius: 20px; margin-bottom: 20px; box-shadow: 0 10px 30px rgba(0,0,0,0.2); text-align: center; }");
        out.println(".profile-avatar { width: 120px; height: 120px; background: linear-gradient(135deg, " + user.getAvatarColor() + "); border-radius: 50%; display: flex; align-items: center; justify-content: center; color: white; font-weight: bold; font-size: 48px; margin: 0 auto 20px; box-shadow: 0 5px 15px rgba(0,0,0,0.2); }");
        out.println(".profile-username { font-size: 32px; font-weight: bold; color: #333; margin-bottom: 10px; }");
        out.println(".profile-email { color: #666; margin-bottom: 15px; }");
        out.println(".profile-bio { color: #888; font-style: italic; margin-bottom: 20px; padding: 15px; background: #f8f9fa; border-radius: 10px; }");
        out.println(".profile-stats { display: grid; grid-template-columns: repeat(3, 1fr); gap: 15px; margin-top: 20px; }");
        out.println(".stat-box { padding: 15px; background: #f8f9fa; border-radius: 10px; }");
        out.println(".stat-number { font-size: 28px; font-weight: bold; color: #11998e; }");
        out.println(".stat-label { color: #666; font-size: 13px; margin-top: 5px; }");
        out.println(".posts-section { background: white; padding: 25px; border-radius: 20px; box-shadow: 0 10px 30px rgba(0,0,0,0.2); }");
        out.println(".posts-section h2 { color: #333; margin-bottom: 20px; }");
        out.println(".post { background: #f8f9fa; padding: 20px; border-radius: 15px; margin-bottom: 15px; border-left: 4px solid #11998e; }");
        out.println(".post-content { color: #444; line-height: 1.6; margin-bottom: 10px; }");
        out.println(".post-meta { display: flex; gap: 20px; font-size: 13px; color: #888; }");
        out.println(".empty-state { text-align: center; padding: 40px; color: #888; }");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");
        out.println("<div class='container'>");
        out.println("<h1>👤 Профиль пользователя</h1>");

        out.println("<div class='nav-links'>");
        out.println("<a href='/' class='nav-link'>🏠 Главная</a>");
        out.println("<a href='posts' class='nav-link'>📰 Лента</a>");
        out.println("<a href='users' class='nav-link'>👥 Пользователи</a>");
        out.println("<a href='stats' class='nav-link'>📊 Статистика</a>");
        out.println("</div>");

        String initial = username.substring(0, 1).toUpperCase();

        out.println("<div class='profile-card'>");
        out.println("<div class='profile-avatar'>" + initial + "</div>");
        out.println("<div class='profile-username'>@" + escapeHtml(username) + "</div>");
        out.println("<div class='profile-email'>📧 " + escapeHtml(user.getEmail()) + "</div>");
        if (user.getBio() != null && !user.getBio().isEmpty()) {
            out.println("<div class='profile-bio'>\"" + escapeHtml(user.getBio()) + "\"</div>");
        }
        out.println("<div class='profile-stats'>");
        out.println("<div class='stat-box'>");
        out.println("<div class='stat-number'>" + userPosts.size() + "</div>");
        out.println("<div class='stat-label'>📝 Постов</div>");
        out.println("</div>");
        out.println("<div class='stat-box'>");
        out.println("<div class='stat-number'>" + totalLikes + "</div>");
        out.println("<div class='stat-label'>❤️ Лайков</div>");
        out.println("</div>");
        out.println("<div class='stat-box'>");
        out.println("<div class='stat-number'>" + (userPosts.size() > 0 ? totalLikes / userPosts.size() : 0) + "</div>");
        out.println("<div class='stat-label'>📊 Средний рейтинг</div>");
        out.println("</div>");
        out.println("</div>");
        out.println("</div>");

        out.println("<div class='posts-section'>");
        out.println("<h2>📝 Все посты пользователя (" + userPosts.size() + ")</h2>");

        if (userPosts.isEmpty()) {
            out.println("<div class='empty-state'>");
            out.println("<p>Пользователь пока не создал ни одного поста</p>");
            out.println("</div>");
        } else {
            for (Post post : userPosts) {
                out.println("<div class='post'>");
                out.println("<div class='post-content'>" + escapeHtml(post.getContent()) + "</div>");
                out.println("<div class='post-meta'>");
                out.println("<span>❤️ " + post.getLikes() + " лайков</span>");
                out.println("<span>💬 " + post.getCommentsCount() + " комментариев</span>");
                out.println("<span>📅 " + post.getFormattedDate() + "</span>");
                out.println("<a href='post?id=" + post.getId() + "' style='color:#11998e;text-decoration:none;'>Открыть →</a>");
                out.println("</div>");
                out.println("</div>");
            }
        }
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
