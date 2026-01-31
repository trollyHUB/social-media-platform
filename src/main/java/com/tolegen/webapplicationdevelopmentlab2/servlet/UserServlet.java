package com.tolegen.webapplicationdevelopmentlab2.servlet;

import com.tolegen.webapplicationdevelopmentlab2.model.SocialMedia;
import com.tolegen.webapplicationdevelopmentlab2.model.User;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "userServlet", value = "/users")
public class UserServlet extends HttpServlet {

    private SocialMedia socialMedia;

    @Override
    public void init() {
        socialMedia = SocialMedia.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String error = request.getParameter("error");
        String success = request.getParameter("success");
        List<User> users = socialMedia.getAllUsers();

        out.println("<!DOCTYPE html>");
        out.println("<html lang='ru'>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("<title>👥 Social Media - Пользователи</title>");
        out.println("<style>");
        out.println("* { margin: 0; padding: 0; box-sizing: border-box; }");
        out.println("body { font-family: 'Segoe UI', Arial, sans-serif; background: linear-gradient(135deg, #11998e 0%, #38ef7d 100%); min-height: 100vh; padding: 20px; }");
        out.println(".container { max-width: 650px; margin: 0 auto; }");
        out.println("h1 { color: white; text-align: center; margin-bottom: 20px; text-shadow: 2px 2px 4px rgba(0,0,0,0.3); font-size: 36px; }");
        out.println(".nav-links { display: flex; gap: 10px; margin-bottom: 20px; flex-wrap: wrap; }");
        out.println(".nav-link { display: inline-block; background: rgba(255,255,255,0.2); color: white; padding: 10px 20px; border-radius: 8px; text-decoration: none; transition: all 0.3s; }");
        out.println(".nav-link:hover { background: rgba(255,255,255,0.3); transform: translateY(-2px); }");
        out.println(".alert { padding: 15px 20px; border-radius: 10px; margin-bottom: 20px; font-size: 15px; animation: slideDown 0.5s; }");
        out.println(".alert-error { background: #ffe5e5; border: 2px solid #e74c3c; color: #c0392b; }");
        out.println(".alert-success { background: #e5ffe5; border: 2px solid #27ae60; color: #1e8449; }");
        out.println("@keyframes slideDown { from { opacity: 0; transform: translateY(-20px); } to { opacity: 1; transform: translateY(0); } }");
        out.println(".register-form { background: white; padding: 25px; border-radius: 15px; margin-bottom: 20px; box-shadow: 0 10px 30px rgba(0,0,0,0.2); }");
        out.println(".register-form h3 { margin-bottom: 15px; color: #333; }");
        out.println(".register-form input { width: 100%; padding: 12px; margin-bottom: 10px; border: 2px solid #e0e0e0; border-radius: 8px; font-size: 14px; transition: border-color 0.3s; }");
        out.println(".register-form input:focus { border-color: #11998e; outline: none; }");
        out.println(".register-form button { background: linear-gradient(135deg, #11998e 0%, #38ef7d 100%); color: white; border: none; padding: 12px 25px; border-radius: 8px; cursor: pointer; font-size: 16px; width: 100%; transition: all 0.3s; }");
        out.println(".register-form button:hover { opacity: 0.9; transform: translateY(-2px); }");
        out.println(".user-card { background: white; padding: 20px; border-radius: 15px; margin-bottom: 15px; box-shadow: 0 5px 15px rgba(0,0,0,0.1); display: flex; align-items: center; transition: transform 0.3s, box-shadow 0.3s; }");
        out.println(".user-card:hover { transform: translateY(-3px); box-shadow: 0 8px 20px rgba(0,0,0,0.15); }");
        out.println(".user-avatar { width: 70px; height: 70px; background: linear-gradient(135deg, #11998e 0%, #38ef7d 100%); border-radius: 50%; display: flex; align-items: center; justify-content: center; color: white; font-weight: bold; font-size: 28px; margin-right: 20px; flex-shrink: 0; }");
        out.println(".user-info { flex: 1; }");
        out.println(".user-info h3 { color: #333; margin-bottom: 5px; font-size: 20px; }");
        out.println(".user-info p { color: #666; font-size: 14px; margin-bottom: 5px; }");
        out.println(".user-info .bio { color: #888; font-style: italic; margin-top: 8px; }");
        out.println(".stats-badge { display: inline-block; background: #f0f0f0; padding: 5px 12px; border-radius: 15px; font-size: 12px; color: #666; margin-top: 8px; }");
        out.println("@keyframes fadeIn { from { opacity: 0; transform: translateY(20px); } to { opacity: 1; transform: translateY(0); } }");
        out.println(".user-card { animation: fadeIn 0.5s ease; }");
        out.println("@media (max-width: 600px) { .container { padding: 10px; } h1 { font-size: 28px; } .user-avatar { width: 60px; height: 60px; font-size: 24px; } }");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");
        out.println("<div class='container'>");
        out.println("<h1>👥 Пользователи</h1>");

        out.println("<div class='nav-links'>");
        out.println("<a href='/' class='nav-link'>🏠 Главная</a>");
        out.println("<a href='posts' class='nav-link'>📰 Лента постов</a>");
        out.println("<a href='stats' class='nav-link'>📊 Статистика</a>");
        out.println("<a href='users' class='nav-link'>🔄 Обновить</a>");
        out.println("</div>");

        if (error != null) {
            out.println("<div class='alert alert-error'>");
            if ("username".equals(error)) {
                out.println("❌ <strong>Ошибка!</strong> Пользователь с таким именем уже существует. Выберите другое имя.");
            } else if ("email".equals(error)) {
                out.println("❌ <strong>Ошибка!</strong> Пользователь с таким email уже зарегистрирован.");
            }
            out.println("</div>");
        }

        if ("true".equals(success)) {
            out.println("<div class='alert alert-success'>");
            out.println("✅ <strong>Успешно!</strong> Пользователь успешно зарегистрирован!");
            out.println("</div>");
        }

        out.println("<div class='register-form'>");
        out.println("<h3>🆕 Регистрация нового пользователя</h3>");
        out.println("<form method='POST' action='users' onsubmit='return validateForm()'>");
        out.println("<input type='text' name='username' id='username' placeholder='Имя пользователя' required minlength='3' maxlength='30' pattern='[a-zA-Zа-яА-ЯёЁ0-9_]+' title='Только буквы, цифры и подчеркивание'>");
        out.println("<input type='email' name='email' id='email' placeholder='Email' required>");
        out.println("<input type='text' name='bio' placeholder='О себе (необязательно)' maxlength='200'>");
        out.println("<button type='submit'>✅ Зарегистрироваться</button>");
        out.println("</form>");
        out.println("</div>");
        out.println("<script>");
        out.println("function validateForm() {");
        out.println("  const username = document.getElementById('username').value;");
        out.println("  const email = document.getElementById('email').value;");
        out.println("  if (username.length < 3) { alert('Имя должно быть не менее 3 символов'); return false; }");
        out.println("  if (!email.includes('@')) { alert('Некорректный email'); return false; }");
        out.println("  return true;");
        out.println("}");
        out.println("</script>");

        out.println("<h2 style='color:white;margin-bottom:15px;text-align:center;'>📋 Все пользователи (" + users.size() + ")</h2>");

        for (User user : users) {
            String initial = user.getUsername().substring(0, 1).toUpperCase();
            int postCount = socialMedia.getPostsByAuthor(user.getUsername()).size();

            out.println("<div class='user-card'>");
            out.println("<a href='profile?user=" + escapeHtml(user.getUsername()) + "' style='text-decoration:none;display:contents;'>");
            out.println("<div class='user-avatar'>" + initial + "</div>");
            out.println("<div class='user-info'>");
            out.println("<h3>@" + escapeHtml(user.getUsername()) + "</h3>");
            out.println("<p>📧 " + escapeHtml(user.getEmail()) + "</p>");
            if (user.getBio() != null && !user.getBio().isEmpty()) {
                out.println("<p class='bio'>💬 \"" + escapeHtml(user.getBio()) + "\"</p>");
            }
            out.println("<span class='stats-badge'>📝 Постов: " + postCount + "</span>");
            out.println("</div>");
            out.println("</a>");
            out.println("</div>");
        }

        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");

        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String bio = request.getParameter("bio");

        String errorMessage = null;

        if (username != null && !username.trim().isEmpty() &&
            email != null && !email.trim().isEmpty()) {

            User newUser = socialMedia.addUser(username.trim(), email.trim(), bio != null ? bio.trim() : "");

            if (newUser == null) {
                if (socialMedia.findUserByUsername(username.trim()).isPresent()) {
                    errorMessage = "username";
                } else {
                    errorMessage = "email";
                }
            }
        }

        if (errorMessage != null) {
            response.sendRedirect("users?error=" + errorMessage);
        } else {
            response.sendRedirect("users?success=true");
        }
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
