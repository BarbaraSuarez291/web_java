package controllers;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import daos.CompraDao;

import models.DetalleProducto;
import models.Factura;
import models.ProdCarrito;


/**
 * Servlet implementation class CompraController
 */
@WebServlet("/CompraController")
public class CompraController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private CompraDao dao;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CompraController() {
        super();
        dao = new CompraDao();
    }
   

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		var optAccion = Optional.ofNullable(request.getParameter("accion"));

		var accion = optAccion.orElse("carrito");

		switch (accion) {
		case "carrito" -> getCarrito(request, response);
		case "compraExitosa" -> getCompraExitosa(request, response);
		case "deleteItem" -> getEliminarProdCarrito(request, response);
		default -> response.getWriter().print("Not found (GET)");
		}
	}
	
	
	private void getEliminarProdCarrito(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession miCarrito = request.getSession(); 
		ArrayList<ProdCarrito> productosCarrito = new ArrayList<ProdCarrito>();
		if(productosCarrito!=null){
			productosCarrito = (ArrayList<ProdCarrito>) miCarrito.getAttribute("productosCarrito");
		}
		miCarrito.setAttribute("productosCarrito", null);
		int idProd = Integer.parseInt(request.getParameter("id"));
		productosCarrito =(ArrayList<ProdCarrito>) productosCarrito.stream()
				.filter(p -> p.getId()!=idProd)
				.collect(Collectors.toList());
		miCarrito.setAttribute("productosCarrito", productosCarrito);
		response.sendRedirect("CompraController");
		
	}


	private void getCompraExitosa(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		var rd = request.getRequestDispatcher("vistas/compra/compraExitosa.jsp");
		rd.forward(request, response);
	}


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		var accion = request.getParameter("accion");

		switch (accion) {
		case "agregar" -> postAgregarCarrito(request, response);
		case "finCompra" -> postFinCompra(request, response);
		case "vaciarCarrito" -> postVaciarCarrito(request, response);
		case "misCompras" -> postVerCompras(request, response);
		case "ver" -> postVerFactura(request, response);
		}
	}

	private void postVerFactura(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		var s_Id = request.getParameter("id");
		var id = Integer.parseInt(s_Id);
		
		Factura factura = dao.getFacturabyId(id);
		var detalle = dao.getDetalleById(id);
		request.setAttribute("factura", factura );
		request.setAttribute("detalle", detalle );
		var rd = request.getRequestDispatcher("vistas/compra/factura.jsp");
		rd.forward(request, response);
		
		
		
	}


	private void postVerCompras(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		var s_Id = request.getParameter("id");
		var id = Integer.parseInt(s_Id);
		var facturas = dao.comprasPorUsuario(id);
		request.setAttribute("facturas", facturas );
		var rd = request.getRequestDispatcher("vistas/compra/misCompras.jsp");
		rd.forward(request, response);
	}



	private void getCarrito(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//HttpSession miCarrito = request.getSession(); 
		//List<ProdCarrito> productosCarrito ;
		//productosCarrito = (List<ProdCarrito>) miCarrito.getAttribute("productosCarrito");
		//var carrito = productosCarrito.stream().toList();
		HttpSession miCarrito = request.getSession(); 
		ArrayList<ProdCarrito> productosCarrito = new ArrayList<ProdCarrito>();
		if(productosCarrito!=null){
			productosCarrito = (ArrayList<ProdCarrito>) miCarrito.getAttribute("productosCarrito");
		}
		
		request.setAttribute("carrito", productosCarrito );
		var rd = request.getRequestDispatcher("vistas/compra/verCarrito.jsp");
		rd.forward(request, response);
		
	}

	
	private void postVaciarCarrito(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession miCarrito = request.getSession();
		miCarrito.setAttribute("productosCarrito", null);
		response.sendRedirect("ProductoController");
		
	}

	private void postFinCompra(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession miCarrito = request.getSession(); 
		ArrayList<ProdCarrito> productosCarrito = new ArrayList<ProdCarrito>();
		if(productosCarrito!=null){
			productosCarrito = (ArrayList<ProdCarrito>) miCarrito.getAttribute("productosCarrito");
		}	if(!productosCarrito.isEmpty()) {
		//Datos factura
		Double total = calcularTotal(miCarrito);
		long miliseconds = System.currentTimeMillis();
	    //Date fecha = new Date(miliseconds);
		int idUser = 1; //hasta q este la parte de usuarios
		
		//instanciamos factura
		Factura fac = new Factura(idUser,  total);
		
		try {
			dao.insertF(fac);
			int id_factura = dao.obtenerIDFactura();
			for(ProdCarrito prod : productosCarrito) {
				DetalleProducto detalle = new DetalleProducto(id_factura,prod.getId(),prod.getCant(), prod.getPrecio());
				dao.insertD(detalle);
				
			}
			response.sendRedirect("CompraController?accion=compraExitosa");
			
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		miCarrito.setAttribute("productosCarrito", null);
		
		
		}else {
			var mensaje= "El carrito se encuentra vacio!.";
			request.setAttribute("mensaje", mensaje );
			var rd = request.getRequestDispatcher("vistas/compra/notificacion.jsp");
			rd.forward(request, response);
			
		}
		
	}

	private Double calcularTotal(HttpSession miCarrito) {
		ArrayList<ProdCarrito> productosCarrito;
		  productosCarrito = (ArrayList<ProdCarrito>) miCarrito.getAttribute("productosCarrito");
		    Double total = 0.0;
		    for(ProdCarrito p : productosCarrito){
		    	total += (p.getPrecio()*p.getCant());
		    }
		    return total;
	}

	private void postAgregarCarrito(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		HttpSession miCarrito = request.getSession(); 
		List<ProdCarrito> productosCarrito = new ArrayList<ProdCarrito>();
		if(productosCarrito!=null){
			productosCarrito = (ArrayList<ProdCarrito>) miCarrito.getAttribute("productosCarrito");
		}//Gson gson = new Gson();
		//Producto prod = gson.fromJson(request.getParameter("prod"),Producto.class);
		int idProd = Integer.parseInt(request.getParameter("prod"));
		int cantProd = Integer.parseInt(request.getParameter("cantidad"));
		String nombre = request.getParameter("nombre");
		Double precio = Double.parseDouble(request.getParameter("precio"));
		if(productosCarrito==null) {
		productosCarrito = new ArrayList<>();
		}
		boolean existe = false;
	
		for(ProdCarrito p : productosCarrito) {
			if(p.getId()== idProd) {
				p.setCant(p.getCant()+cantProd);
				existe=true;
			}
		}
		if(!existe) {
			ProdCarrito pc = new ProdCarrito(idProd, cantProd, precio, nombre);
			productosCarrito.add(pc);
		}
	
		miCarrito.setAttribute("productosCarrito", productosCarrito);
		response.sendRedirect("ProductoController");
		
	}

}
