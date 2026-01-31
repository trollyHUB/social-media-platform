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
 * Сервлет для статистики и трендов
 */
@WebServlet(name = "statsServlet", value = "/stats")
public class StatsServlet extends HttpServlet {

    private SocialMedia socialMedia;

    @Override
    public void init() {
        socialMedia = SocialMedia.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        List<Post> trendingPosts = socialMedia.getTrendingPosts(5);
        List<User> topUsers = socialMedia.getTopUsers(5);

        out.println("<!DOCTYPE html>");
        out.println("<html lang='ru'>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("<title>📊 Social Media - Статистика</title>");
        out.println("<style>");
        out.println("* { margin: 0; padding: 0; box-sizing: border-box; }");
        out.println("body { font-family: 'Segoe UI', Arial, sans-serif; background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%); min-height: 100vh; padding: 20px; }");
        out.println(".container { max-width: 900px; margin: 0 auto; }");
        out.println("h1 { color: white; text-align: center; margin-bottom: 20px; text-shadow: 2px 2px 4px rgba(0,0,0,0.3); font-size: 36px; }");
        out.println(".nav-links { display: flex; gap: 10px; margin-bottom: 20px; flex-wrap: wrap; justify-content: center; }");
        out.println(".nav-link { display: inline-block; background: rgba(255,255,255,0.2); color: white; padding: 10px 20px; border-radius: 8px; text-decoration: none; transition: all 0.3s; }");
        out.println(".nav-link:hover { background: rgba(255,255,255,0.3); transform: translateY(-2px); }");
        out.println(".stats-grid { display: grid; grid-template-columns: repeat(auto-fit, minmax(200px, 1fr)); gap: 15px; margin-bottom: 30px; }");
        out.println(".stat-card { background: white; padding: 25px; border-radius: 15px; text-align: center; box-shadow: 0 5px 15px rgba(0,0,0,0.1); }");
        out.println(".stat-number { font-size: 36px; font-weight: bold; color: #f5576c; margin-bottom: 5px; }");
        out.println(".stat-label { color: #666; font-size: 14px; }");
        out.println(".section { background: white; padding: 25px; border-radius: 15px; margin-bottom: 20px; box-shadow: 0 5px 15px rgba(0,0,0,0.1); }");
        out.println(".section h2 { color: #333; margin-bottom: 20px; font-size: 24px; }");
        out.println(".trending-post { padding: 15px; border-left: 4px solid #f5576c; background: #f8f9fa; margin-bottom: 10px; border-radius: 5px; }");
        out.println(".trending-post .author { font-weight: bold; color: #f5576c; margin-bottom: 5px; }");
        out.println(".trending-post .content { color: #555; margin-bottom: 8px; }");
        out.println(".trending-post .stats { display: flex; gap: 20px; font-size: 13px; color: #888; }");
        out.println(".top-user { display: flex; align-items: center; padding: 15px; background: #f8f9fa; margin-bottom: 10px; border-radius: 10px; }");
        out.println(".top-user .rank { font-size: 24px; font-weight: bold; color: #f5576c; margin-right: 15px; min-width: 40px; }");
        out.println(".top-user .avatar { width: 50px; height: 50px; background: linear-gradient(135deg, #f093fb, #f5576c); border-radius: 50%; display: flex; align-items: center; justify-content: center; color: white; font-weight: bold; font-size: 20px; margin-right: 15px; }");
        out.println(".top-user .info { flex: 1; }");
        out.println(".top-user .username { font-weight: bold; color: #333; margin-bottom: 3px; }");
        out.println(".top-user .posts { color: #888; font-size: 13px; }");
        out.println("@media (max-width: 600px) { .container { padding: 10px; } h1 { font-size: 28px; } }");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");
        out.println("<div class='container'>");
        out.println("<h1>📊 Статистика платформы</h1>");

        out.println("<div class='nav-links'>");
        out.println("<a href='/' class='nav-link'>🏠 Главная</a>");
        out.println("<a href='posts' class='nav-link'>📰 Лента</a>");
        out.println("<a href='users' class='nav-link'>👥 Пользователи</a>");
        out.println("<a href='stats' class='nav-link'>🔄 Обновить</a>");
        out.println("</div>");

        // Общая статистика
        out.println("<div class='stats-grid'>");
        out.println("<div class='stat-card'>");
        out.println("<div class='stat-number'>" + socialMedia.getTotalUsersCount() + "</div>");
        out.println("<div class='stat-label'>👥 Пользователей</div>");
        out.println("</div>");
        out.println("<div class='stat-card'>");
        out.println("<div class='stat-number'>" + socialMedia.getTotalPostsCount() + "</div>");
        out.println("<div class='stat-label'>📝 Постов</div>");
        out.println("</div>");
        out.println("<div class='stat-card'>");
        out.println("<div class='stat-number'>" + socialMedia.getTotalLikes() + "</div>");
        out.println("<div class='stat-label'>❤️ Лайков</div>");
        out.println("</div>");
        out.println("<div class='stat-card'>");
        out.println("<div class='stat-number'>" + socialMedia.getTotalComments() + "</div>");
        out.println("<div class='stat-label'>💬 Комментариев</div>");
        out.println("</div>");
        out.println("</div>");

        // Трендовые посты
        out.println("<div class='section'>");
        out.println("<h2>🔥 Трендовые посты</h2>");
        if (trendingPosts.isEmpty()) {
            out.println("<p style='color:#888;text-align:center;'>Пока нет постов</p>");
        } else {
            for (Post post : trendingPosts) {
                out.println("<div class='trending-post'>");
                out.println("<div class='author'>@" + escapeHtml(post.getAuthor()) + "</div>");
                out.println("<div class='content'>" + escapeHtml(post.getContent()) + "</div>");
                out.println("<div class='stats'>");
                out.println("<span>❤️ " + post.getLikes() + " лайков</span>");
                out.println("<span>💬 " + post.getCommentsCount() + " комментариев</span>");
                out.println("<span>⏰ " + post.getRelativeTime() + "</span>");
                out.println("</div>");
                out.println("</div>");
            }
        }
        out.println("</div>");

        // Топ пользователи
        out.println("<div class='section'>");
        out.println("<h2>🏆 Топ пользователи</h2>");
        int rank = 1;
        for (User user : topUsers) {
            String initial = user.getUsername().substring(0, 1).toUpperCase();
            int postCount = socialMedia.getPostsByAuthor(user.getUsername()).size();
            String rankEmoji = rank == 1 ? "🥇" : rank == 2 ? "🥈" : rank == 3 ? "🥉" : String.valueOf(rank);

            out.println("<div class='top-user'>");
            out.println("<div class='rank'>" + rankEmoji + "</div>");
            out.println("<div class='avatar'>" + initial + "</div>");
            out.println("<div class='info'>");
            out.println("<div class='username'>@" + escapeHtml(user.getUsername()) + "</div>");
            out.println("<div class='posts'>📝 " + postCount + " постов</div>");
            out.println("</div>");
            out.println("</div>");
            rank++;
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
