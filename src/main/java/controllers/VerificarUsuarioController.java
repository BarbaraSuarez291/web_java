package controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import daos.UsuarioDao;
import models.Usuario;

/**
 * Servlet implementation class VerificarUsuarioController
 */
@WebServlet("/Login")
public class VerificarUsuarioController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Usuario usuario1 = new Usuario();
     private UsuarioDao usuarioDAO = new UsuarioDao();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VerificarUsuarioController() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
           var accion = Optional.ofNullable(request.getParameter("accion")).orElse("login");
           
           switch (accion) {
		case "login" -> getLogin(request,response);
		
		default ->
		throw new IllegalArgumentException("Unexpected value: " + accion);
		}
		
	}

	private void getLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		var fd = request.getRequestDispatcher("index.jsp");
		fd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.getRequestDispatcher("index.jsp").include(request, response);
		
		String nombre=request.getParameter("usuario");
		String password=request.getParameter("pass");
		 try {
			usuario1 = usuarioDAO.validarUsuario(nombre, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		 
		 if(usuario1.getNombre()!=null || usuario1.getClave()!=null) {
			//redirecionar dependiendo del tipo de usuario e iniciar Session;
			//FALTA SESSION
			
			 var sesion = request.getSession();
			String tipo=usuario1.getRol();
			switch (tipo) {
				case "empleado" ->empleado(usuario1) ; //ir a la pagina dar de alta usuarios y productos
				case "cliente" ->cliente(usuario1) ; //ir a la pagina de compra
			}
			
		    sesion.setAttribute("id_usu", usuario1.getId());
		    
		    var id_usuario = sesion.getAttribute("id_usu");
			
		}else {
			//volver al index,con mensaje de error de usuario y clave!!!
				request.setAttribute("Error", "Error de Usuario o Password");
				RequestDispatcher rd;
				rd=request.getRequestDispatcher("index.jsp");
				rd.forward(request, response);		
		}	
	}
	private void cliente(Usuario usu) {
		System.out.println(usu+"SOY CLIENTE");
	}
	
	private void empleado(Usuario usu) {
		System.out.println(usu+"SOY EMPLEADO");
	}
	
	

}
