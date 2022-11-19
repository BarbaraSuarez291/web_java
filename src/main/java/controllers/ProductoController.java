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
	/**
	 * En esta seccion se gestionan las operaciones que realiza el empleado en el crud de productos 
	 * por el metodo POST.
	 * @param request = "accion";
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
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
	/** Retorna la vista principal,  en caso de que el cliente quiera añadir al carrito un
	 * y no la cantidad de stock se retorna un mensaje de error.  **/
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

	/** Recibe el id del producto y lo elimina de base de datos.
	 * @param (HttpServletRequest request, HttpServletResponse response) **/
	private void postDelete(HttpServletRequest request, HttpServletResponse response)  throws IOException {
		var sId = request.getParameter("id");
		var id = Integer.parseInt(sId);
		dao.delete(id);
		response.sendRedirect("ProductoController");
		
	}
	/**Recibe el id del producto, realiza validaciones, en caso de error muestra el mensaje.
	 *  En caso contrario actualiza el producto.
	 *  @param  (HttpServletRequest request, HttpServletResponse response) **/
	private void postUpdate(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		var sId = request.getParameter("id");
		var id = Integer.parseInt(sId);

		var errorSession = request.getSession();
		errorSession.setAttribute("mensaje", null);
		errorSession.setAttribute("tipoMensaje", null);
		
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
					
				}
			}
			
		}
		
	}
	/** Recibe los datos del producto(nombre, precio y cantidad), realiza validaciones
	 *  y finalmente crea el producto.
	 *  @param (HttpServletRequest request, HttpServletResponse response)
	 * **/
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
					
				}else{
					
				var prod = new Producto(nombre, precio_prod, cant_prod);
					try {
						dao.insert(prod);
						request.setAttribute("mensaje", "Producto ingresado correctamente!");
						request.setAttribute("tipoMensaje", "success");
						RequestDispatcher rd;
						rd=request.getRequestDispatcher("vistas/productos/crear.jsp");
						rd.forward(request, response);
					} catch (ClassNotFoundException e) {
						
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	/** Recibe un String y retorna true si se pueda pasar a tipo Integer de lo contrario retorna false
	 * @param String cadena
	 * @return boolean **/
	private static boolean isNumeric(String cadena){
		try {
			Integer.parseInt(cadena);
			return true;
		} catch (NumberFormatException nfe){
			return false;
		}
	}
	/** Recibe un String y retorna true si se pueda pasar a tipo Double de lo contrario retorna false
	 * @param String cadena
	 * @return boolean **/
	private static boolean isDouble(String cadena){
		try {
			Double.parseDouble(cadena);
			return true;
		} catch (NumberFormatException nfe){
			return false;
		}
	}
	
	

}
