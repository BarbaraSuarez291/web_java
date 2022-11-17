package controllers;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import daos.ProductoDAO;
import models.Producto;

/**
 * Servlet implementation class ProductoController
 */
@WebServlet("/ProductoController")
public class ProductoController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ProductoDAO dao;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProductoController() {
        super();
        dao = new ProductoDAO();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		var optAccion = Optional.ofNullable(request.getParameter("accion"));

		var accion = optAccion.orElse("index");

		switch (accion) {
		case "index" -> getIndex(request, response);
		case "crear" -> getCrear(request, response);
		case "editar" -> getEditar(request, response);

		default -> response.getWriter().print("Not found (GET)");
	}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		var accion = request.getParameter("accion");

		switch (accion) {
		case "insert" -> postInsert(request, response);
		case "update" -> postUpdate(request, response);
		case "delete" -> postDelete(request, response);
		}
	}

	private void getEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		var errorSession = request.getSession();
		
	
		String mensaje = null;
		String tipo = null;
		if(errorSession!= null) {
			mensaje = (String) errorSession.getAttribute("mensaje");
			tipo = (String) errorSession.getAttribute("tipoMensaje");
			errorSession.setAttribute("mensaje", null);
		}
		
		var sId = request.getParameter("id");
		var id = Integer.parseInt(sId);
		
		
		Producto prod = dao.getById(id);
		
		
		request.setAttribute("producto", prod);
		request.setAttribute("mensaje", mensaje);
		request.setAttribute("tipoMensaje", tipo);
		errorSession.setAttribute("mensaje", null);

		var rd = request.getRequestDispatcher("vistas/productos/editar.jsp");
		rd.forward(request, response);
	}

	private void getCrear(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		var rd = request.getRequestDispatcher("vistas/productos/crear.jsp");
		rd.forward(request, response);
	}

	private void getIndex(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		var errorSession = request.getSession();
		errorSession.setAttribute("mensaje", null);
		var errorSessionCarrito =request.getSession();
		
		String mensaje = null;
		String tipo = null;
		if(errorSessionCarrito.getAttribute("mensajeC")!=null){
			 mensaje = (String) errorSessionCarrito.getAttribute("mensajeC");
			 tipo = (String) errorSessionCarrito.getAttribute("tipoMensajeC");
			 errorSessionCarrito.setAttribute("mensajeC", null);
		}
	
		HttpSession sesion = request.getSession(); 
		var id_usuario = sesion.getAttribute("id_usu");
		var nombre_usu = sesion.getAttribute("nombre");
		var rol = sesion.getAttribute("rol");
		var nombre = sesion.getAttribute("nombre");
		if(rol==null) {
			response.sendRedirect("VerificarUsuarioController");
		}
		var listado=  dao.all();
		System.out.println(listado);
		sesion.setAttribute("id_usuario", id_usuario);
		sesion.setAttribute("nombre", nombre_usu);
		request.setAttribute("productos", listado);
		request.setAttribute("rol", rol);
		request.setAttribute("id_usuario", id_usuario);
		request.setAttribute("nombre", nombre);
		request.setAttribute("mensaje", mensaje);
		request.setAttribute("tipoMensaje", tipo);
		
		errorSessionCarrito.setAttribute("mensajeC", null);
		
		var rd = request.getRequestDispatcher("vistas/productos/index.jsp");
		rd.forward(request, response);
	}


	private void postDelete(HttpServletRequest request, HttpServletResponse response)  throws IOException {

		var sId = request.getParameter("id");
		var id = Integer.parseInt(sId);
		dao.delete(id);
		response.sendRedirect("ProductoController");
		
	}

	private void postUpdate(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		var sId = request.getParameter("id");
		var id = Integer.parseInt(sId);

		var errorSession = request.getSession();
		errorSession.setAttribute("mensaje", null);
		errorSession.setAttribute("tipoMensaje", null);
		//var tipoM = request.getSession();
		String mensaje = null;
		String tipo = null;
		if(request.getParameter("nombre").isEmpty() || request.getParameter("precio").isEmpty() || request.getParameter("cant").isEmpty()) {
			mensaje = "No puede quedar ningun campo vacio.";
			tipo = "danger";
			errorSession.setAttribute("mensaje", mensaje);
			errorSession.setAttribute("tipoMensaje", tipo);
			
			response.sendRedirect("ProductoController?accion=editar&id="+id);
			
			//response.sendError(404, "No puede quedar ningun campo vacio.");
		}else {
			String nombre = request.getParameter("nombre");
			String precio = request.getParameter("precio");
			String cant = request.getParameter("cant");
		
			if(!isDouble(precio) || !isNumeric(cant)){
				//response.sendError(504, "El valor ingresado debe ser numerico.");
				mensaje = "El valor ingresado debe ser numerico.";
				tipo = "danger";
				errorSession.setAttribute("mensaje", mensaje);
				errorSession.setAttribute("tipoMensaje", tipo);
				
				response.sendRedirect("ProductoController?accion=editar&id="+id);
				//String ruta = "ProductoController?accion=editar&id="+id;
				//generarAlerta("danger", "El valor ingresado debe ser numerico.",ruta ,  request, response);
				
			}else {
				var precio_prod = Double.parseDouble(precio);
				var cant_prod = Integer.parseInt(cant);
				
				if(cant_prod < 0 || precio_prod < 0.0) {
					//response.sendError(404, "No puede ingresar numero negativos.");
					mensaje = "No puede ingresar numero negativos.";
					tipo = "danger";
					errorSession.setAttribute("mensaje", mensaje);
					errorSession.setAttribute("tipoMensaje", tipo);
					
					response.sendRedirect("ProductoController?accion=editar&id="+id);
					//String ruta = "ProductoController?accion=editar&id="+id;
					//generarAlerta("danger", "No puede ingresar numero negativos",ruta ,  request, response);
					
					
				}else{
				
				var prod = dao.getById(id);
				prod.setNombre(nombre);
				prod.setPrecio(precio_prod);
				prod.setCant(cant_prod);
				
				
				dao.update(prod);
				
				
				mensaje = "Producto actualizado con exito!.";
				tipo = "success";
				errorSession.setAttribute("mensaje", mensaje);
				errorSession.setAttribute("tipoMensaje", tipo);
				
				response.sendRedirect("ProductoController?accion=editar&id="+id);
				//response.sendRedirect("ProductoController?accion=editar&id="+id);
				//String ruta = "ProductoController";
				//generarAlerta("success", "Producto actualizado con exito!",ruta ,  request, response);
				
				}
			}
			
		}
		
	}

	private void postInsert(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		request.setAttribute("mensaje", null);
		
		if(request.getParameter("nombre").isEmpty() || request.getParameter("precio").isEmpty() || request.getParameter("cant").isEmpty()) {
			request.setAttribute("mensaje", "No puede quedar ningun campo vacio.");
			request.setAttribute("tipoMensaje", "danger");
			RequestDispatcher rd;
			rd=request.getRequestDispatcher("vistas/productos/crear.jsp");
			rd.forward(request, response);			
		}else {
			String nombre = request.getParameter("nombre");
			String precio = request.getParameter("precio");
			String cant = request.getParameter("cant");
			if(!isDouble(precio) || !isNumeric(cant)){
				request.setAttribute("mensaje", "El valor ingresado debe ser numerico.");
				request.setAttribute("tipoMensaje", "danger");
				RequestDispatcher rd;
				rd=request.getRequestDispatcher("vistas/productos/crear.jsp");
				rd.forward(request, response);	
					
			}else {
				Double precio_prod = Double.parseDouble(precio);
				Integer cant_prod = Integer.parseInt(cant);
			
				if(cant_prod < 0 || precio_prod < 0.0) {
					response.sendError(404, "No puede ingresar numero negativos.");
					request.setAttribute("tipoMensaje", "danger");
					//generarAlerta("danger ", "No puede ingresar numero negativos.", "vistas/productos/crear.jsp",  request, response);
					
				}else{
					// Procesar
				var prod = new Producto(nombre, precio_prod, cant_prod);
					try {
						dao.insert(prod);
						request.setAttribute("mensaje", "Producto ingresado correctamente!");
						request.setAttribute("tipoMensaje", "success");
						RequestDispatcher rd;
						rd=request.getRequestDispatcher("vistas/productos/crear.jsp");
						rd.forward(request, response);
						//generarAlerta("success", "Producto ingresado!", "ProductoController",  request, response);
						//response.sendRedirect("ProductoController");
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	
	private static boolean isNumeric(String cadena){
		try {
			Integer.parseInt(cadena);
			return true;
		} catch (NumberFormatException nfe){
			return false;
		}
	}
	private static boolean isDouble(String cadena){
		try {
			Double.parseDouble(cadena);
			return true;
		} catch (NumberFormatException nfe){
			return false;
		}
	}
	
	

}
