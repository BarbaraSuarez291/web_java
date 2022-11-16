package controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class UserController
 */
@WebServlet("/UserController")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		var accion = request.getParameter("accion");

		switch (accion) {
		case "logout" -> postLogout(request, response);
		//case "login" -> postLogin(request, response);
		
		}
		
	}

	private void postLogout(HttpServletRequest request, HttpServletResponse response) {
		
		var sesion = request.getSession();
	    sesion.setAttribute("id_usu", null);
	    sesion.setAttribute("rol", null);
	    sesion.setAttribute("nombre", null);
	    
		//response.sendRedirect("VerificarUsuarioController?accion=login");
	}

}
