package controllers;

import java.io.IOException;
import java.util.Optional;

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

		//Ingreso
		var sId = request.getParameter("id");
		var id = Integer.parseInt(sId);
		
		//Proceso
		Producto prod = dao.getById(id);
		
		//Preparacion
		request.setAttribute("producto", prod);
		
		//Salida		
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
		var errorSesion =request.getSession();
		String mensaje= null;
		if(errorSesion!=null){
			 mensaje = (String) errorSesion.getAttribute("mensaje");
		}
		HttpSession sesion = request.getSession(); 
		var id_usuario = sesion.getAttribute("id_usu");
		var rol = sesion.getAttribute("rol");
		if(rol==null) {
			response.sendRedirect("VerificarUsuarioController");
		}
		var listado=  dao.all();
		System.out.println(listado);
		request.setAttribute("productos", listado);
		request.setAttribute("rol", rol);
		request.setAttribute("id_usuario", id_usuario);
		request.setAttribute("mensaje", mensaje);
		var rd = request.getRequestDispatcher("vistas/productos/index.jsp");
		rd.forward(request, response);
	}


	private void postDelete(HttpServletRequest request, HttpServletResponse response)  throws IOException {

		var sId = request.getParameter("id");
		var id = Integer.parseInt(sId);
		dao.delete(id);
		// Salida
		response.sendRedirect("ProductoController");
		
	}

	private void postUpdate(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		var sId = request.getParameter("id");
		var id = Integer.parseInt(sId);

		if(request.getParameter("nombre").isEmpty() || request.getParameter("precio").isEmpty() || request.getParameter("cant").isEmpty()) {
			response.sendError(404, "No puede quedar ningun campo vacio.");
		}else {
			String nombre = request.getParameter("nombre");
			String precio = request.getParameter("precio");
			String cant = request.getParameter("cant");
		
			if(!isDouble(precio) || !isNumeric(cant)){
				//response.sendError(504, "El valor ingresado debe ser numerico.");
				String ruta = "ProductoController?accion=editar&id="+id;
				generarAlerta("danger", "El valor ingresado debe ser numerico.",ruta ,  request, response);
				
			}else {
				var precio_prod = Double.parseDouble(precio);
				var cant_prod = Integer.parseInt(cant);
				
				if(cant_prod < 0 || precio_prod < 0.0) {
					//response.sendError(404, "No puede ingresar numero negativos.");
					String ruta = "ProductoController?accion=editar&id="+id;
					generarAlerta("danger", "No puede ingresar numero negativos",ruta ,  request, response);
					
					
				}else{
				
				var prod = dao.getById(id);
				prod.setNombre(nombre);
				prod.setPrecio(precio_prod);
				prod.setCant(cant_prod);
				
				
				dao.update(prod);
				
				
				//response.sendRedirect("ProductoController");
				String ruta = "ProductoController";
				generarAlerta("success", "Producto actualizado con exito!",ruta ,  request, response);
				
				}
			}
			
		}
		
	}

	private void postInsert(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		// Levantar datos
		
		if(request.getParameter("nombre").isEmpty() || request.getParameter("precio").isEmpty() || request.getParameter("cant").isEmpty()) {
			response.sendError(404, "No puede quedar ningun campo vacio.");
			//generarAlerta("danger", "No puede quedar ningun campo vacio.", "vistas/productos/crear.jsp",  request, response);
			
		}else {
			String nombre = request.getParameter("nombre");
			String precio = request.getParameter("precio");
			String cant = request.getParameter("cant");
			if(!isDouble(precio) || !isNumeric(cant)){
				response.sendError(504, "El valor ingresado debe ser numerico.");
				//generarAlerta("danger", "El valor ingresado debe ser numerico.", "vistas/productos/crear.jsp",  request, response);
				
			}else {
				Double precio_prod = Double.parseDouble(precio);
				Integer cant_prod = Integer.parseInt(cant);
			
				if(cant_prod < 0 || precio_prod < 0.0) {
					response.sendError(404, "No puede ingresar numero negativos.");
					//generarAlerta("danger ", "No puede ingresar numero negativos.", "vistas/productos/crear.jsp",  request, response);
					
				}else{
					// Procesar
				var prod = new Producto(nombre, precio_prod, cant_prod);
					try {
						dao.insert(prod);
						
						generarAlerta("success", "Producto ingresado!", "ProductoController",  request, response);
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
	
	private static void generarAlerta (String tipoAlerta, String mensaje, String ruta, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("mensaje", mensaje);
		request.setAttribute("ruta", ruta);
		request.setAttribute("tipoAlerta", tipoAlerta);
		var rd = request.getRequestDispatcher("vistas/compra/notificacion.jsp");
		rd.forward(request, response);
	}

}
