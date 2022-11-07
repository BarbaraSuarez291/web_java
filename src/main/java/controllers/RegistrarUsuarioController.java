package controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import daos.UsuarioDao;
import models.Usuario;

/**
 * Servlet implementation class RegistrarUsuarioController
 */
@WebServlet("/RegistrarUsuarioController")
public class RegistrarUsuarioController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 private Usuario usuario1 = new Usuario();
    private UsuarioDao usuarioDAO = new UsuarioDao(); 
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegistrarUsuarioController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("RegistroUsuario.jsp").include(request, response);
		
		String nombre=request.getParameter("nombre");
		String password=request.getParameter("pass");
		
		System.out.println("Nombre:"+nombre+"--Password:"+password);
		usuario1.setNombre(nombre);
		usuario1.setClave(password);
		System.out.println(usuarioDAO.agregarCliente(usuario1));
		RequestDispatcher rd;
		rd=request.getRequestDispatcher("index.jsp");
		rd.forward(request, response);	
				 
	}

}
