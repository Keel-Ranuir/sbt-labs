package edu.phystech.lab;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        PrintWriter write = resp.getWriter();

        ServletContext servletContext = getServletConfig().getServletContext();

        String user = req.getParameter("userName");
        String pass = req.getParameter("userPass");

        if (user == null || user.equals("")) {
            write.println("<h1>Please, enter a username</h1>");
            RequestDispatcher rd = req.getRequestDispatcher("/register.html");
            rd.include(req, resp);
            return;
        }
        if (pass == null || pass.equals("")) {
            write.println("<h1>Please, enter a password</h1>");
            RequestDispatcher rd = req.getRequestDispatcher("/register.html");
            rd.include(req, resp);
            return;
        }

        req.setAttribute("userName", user);
        if (!UserAuthorization.userExists(user)) {
            write.println(String.format("<h1>User %s doesn't exist. Please, sign up to proceed</h1>", user));
            RequestDispatcher rd = req.getRequestDispatcher("/login.html");
            rd.include(req, resp);
        } else {
            if (UserAuthorization.isPasswordCorrect(user, pass)) {
                RequestDispatcher rd = req.getRequestDispatcher("/homepage.jsp");
                rd.include(req, resp);
            } else {
                write.println("<h1>Password is incorrect</h1>");
                RequestDispatcher rd = req.getRequestDispatcher("/login.html");
                rd.include(req, resp);
            }
        }
    }
}
