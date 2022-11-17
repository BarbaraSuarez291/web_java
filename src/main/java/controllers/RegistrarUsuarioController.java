package controllers;

import java.io.IOException;
import java.sql.SQLException;

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
	 * Valida los datos ingresados por el formulario de registro. En caso de pasar las validaciones
	 * ingresa un usuario nuevo y lo redirecciona al Login.
	 * El saldo unicial de cada usuario nuevo es 0.
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("RegistroUsuario.jsp").include(request, response);
		
		String nombre=request.getParameter("nombre");
		String password=request.getParameter("pass");
		try {
			if(!usuarioDAO.existeUsuario(nombre)) {
				if(usuarioDAO.validacionRegistro(nombre, password)== null) {
					System.out.println("Nombre:"+nombre+"--Password:"+password);
					usuario1.setNombre(nombre);
					usuario1.setClave(password);
					System.out.println(usuarioDAO.agregarCliente(usuario1));
					RequestDispatcher rd;
					rd=request.getRequestDispatcher("login.jsp");
					rd.forward(request, response);	
				}else {
				request.setAttribute("Error", usuarioDAO.validacionRegistro(nombre, password));
				RequestDispatcher rd;
				rd=request.getRequestDispatcher("RegistroUsuario.jsp");
				rd.forward(request, response);	
				}
			}else {
				request.setAttribute("Error", "El nombre de usuario ya existe en base de datos.");
				RequestDispatcher rd;
				rd=request.getRequestDispatcher("RegistroUsuario.jsp");
				rd.forward(request, response);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
	}
	

}
