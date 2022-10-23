package controllers;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


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
		// TODO Auto-generated method stub
		var rd = request.getRequestDispatcher("vistas/productos/crear.jsp");
		rd.forward(request, response);
	}

	private void getIndex(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		var listado=  dao.all();
		
		System.out.println(listado);
		request.setAttribute("productos", listado);
		
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

	private void postUpdate(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// Levantar datos
		String nombre = request.getParameter("nombre");
		String precio = request.getParameter("precio");
		String cant = request.getParameter("cant");
		
		var precio_prod = Double.parseDouble(precio);
		var cant_prod = Integer.parseInt(cant);
		var sId = request.getParameter("id");
		var id = Integer.parseInt(sId);
		
		//Proceso
		var prod = dao.getById(id);
		prod.setNombre(nombre);
		prod.setPrecio(precio_prod);
		prod.setCant(cant_prod);
		
		
		dao.update(prod);
		

		// Salida
		response.sendRedirect("ProductoController");
		
		
	}

	private void postInsert(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		// Levantar datos
		String nombre = request.getParameter("nombre");
		String precio = request.getParameter("precio");
		String cant = request.getParameter("cant");

		double precio_prod = Double.parseDouble(precio);
		int cant_prod = Integer.parseInt(cant);
		// Procesar
		var prod = new Producto(nombre, precio_prod, cant_prod);
		try {
			dao.insert(prod);
			// Salida
			response.sendRedirect("ProductoController");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		

	}
	
	
	
	

}