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

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/register.html").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        PrintWriter write = resp.getWriter();

        ServletContext servletContext = getServletConfig().getServletContext();

        String user = req.getParameter("userName");
        String pass = req.getParameter("userPass");

        if (user == null || user.equals("")) {
            write.println("<h1>Please, choose a username</h1>");
            RequestDispatcher rd = req.getRequestDispatcher("/register.html");
            rd.include(req, resp);
            return;
        }
        if (pass == null || pass.equals("")) {
            write.println("<h1>Please, choose a password</h1>");
            RequestDispatcher rd = req.getRequestDispatcher("/register.html");
            rd.include(req, resp);
            return;
        }

        req.setAttribute("userName", user);
        if (user.equals("admin")) {
            write.println("<h1>Can't create username \"admin\". Please, choose another username</h1>");
            RequestDispatcher rd = req.getRequestDispatcher("/register.html");
            rd.include(req, resp);
        } else {
            if (UserAuthorization.userExists(user)) {
                write.println(String.format("<h1>User %s already exists</h1>", user));
                RequestDispatcher rd = req.getRequestDispatcher("/register.html");
                rd.include(req, resp);
            } else {
                UserAuthorization.addUser(user, pass);
                write.println(String.format("<h1>Successfully signed up as %s. Please, log in to proceed</h1>", user));
                RequestDispatcher rd = req.getRequestDispatcher("/login.html");
                rd.include(req, resp);
            }
        }
    }
}
