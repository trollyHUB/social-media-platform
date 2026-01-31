package com.tolegen.webapplicationdevelopmentlab2.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Главная страница - редирект на index.jsp
 */
@WebServlet(name = "homeServlet", urlPatterns = {"", "/"})
public class HomeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // Перенаправляем на index.jsp
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }
}
