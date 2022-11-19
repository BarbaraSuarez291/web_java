package controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import daos.BancoDaoBD;
import daos.CompraDao;
import daos.ProductoDAO;
import daos.UsuarioDao;

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
    private UsuarioDao daoU;
    private ProductoDAO daoP;
    private BancoDaoBD dao_banco;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CompraController() {
        super();
        dao = new CompraDao();
        daoU = new UsuarioDao();
        daoP = new ProductoDAO();
        dao_banco = new BancoDaoBD();
    }
   

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		var optAccion = Optional.ofNullable(request.getParameter("accion"));

		var accion = optAccion.orElse("carrito");

		switch (accion) {
		case "carrito" -> getCarrito(request, response);
		case "compraExitosa" -> getCompraExitosa(request, response);
		case "deleteItem" -> getEliminarProdCarrito(request, response);
		case "ventas" -> getVentas(request, response);
		default -> response.getWriter().print("Not found (GET)");
		}
	}
	
	/** Redirige a la pagina de ventas. Donde se ve un listado de las mismas.
	 * y se puede ingresar al detalle de cada una.
	 * @param (HttpServletRequest request, HttpServletResponse response)**/
	private void getVentas(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		var facturas = dao.all();
		request.setAttribute("facturas", facturas );
		var rd = request.getRequestDispatcher("vistas/compra/ventas.jsp");
		rd.forward(request, response);
		
	}

	/**
	 *Recibe el id del producto a eliminar del carrito.
	 *Se actualiza la lista de los productos del carrito.
	 * @param (HttpServletRequest request, HttpServletResponse response)**/
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

	/** Redirecciona a una vista de notificacion si la compra fue realizada con exito **/
	private void getCompraExitosa(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		var sesion = request.getSession(); 
		var idUser_ = sesion.getAttribute("id_usu");
		request.setAttribute("idUser", idUser_);
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
	/** Permite segun el id de factura vizualisar su detalle.
	 * @param  (HttpServletRequest request, HttpServletResponse response)**/
	private void postVerFactura(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		var sesion = request.getSession();
		String rol = (String) sesion.getAttribute("rol");
		var s_Id = request.getParameter("id");
		var id = Integer.parseInt(s_Id);
		var idUser = Integer.parseInt(request.getParameter("idUser"));
		try {
			var usuario = daoU.buscarUsuario(idUser);
			Factura factura = dao.getFacturabyId(id);
			var detalle = dao.getDetalleById(id);
			request.setAttribute("rol", rol);
			request.setAttribute("usuario", usuario );
			request.setAttribute("factura", factura );
			request.setAttribute("detalle", detalle );
			var rd = request.getRequestDispatcher("vistas/compra/factura.jsp");
			rd.forward(request, response);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/** Recibe el id del usuario y busca las compras(facturas) que coiciden con el mismo. **/
	private void postVerCompras(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		var s_Id = request.getParameter("id");
		var id = Integer.parseInt(s_Id);
		
		var facturas = dao.comprasPorUsuario(id);
		request.setAttribute("facturas", facturas );
		var rd = request.getRequestDispatcher("vistas/compra/misCompras.jsp");
		rd.forward(request, response);
	}


	/** Obtenemos el carrito desde la session y se lo pasa a la vista. **/
	private void getCarrito(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession miCarrito = request.getSession(); 
		ArrayList<ProdCarrito> productosCarrito = new ArrayList<ProdCarrito>();
		if(productosCarrito!=null){
			productosCarrito = (ArrayList<ProdCarrito>) miCarrito.getAttribute("productosCarrito");
		}
		
		request.setAttribute("carrito", productosCarrito );
		var rd = request.getRequestDispatcher("vistas/compra/verCarrito.jsp");
		rd.forward(request, response);
		
	}

	/**  Eliminar el carrito. Guardando en la sesion "productosCarrito" el valor null
	 * **/
	private void postVaciarCarrito(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession miCarrito = request.getSession();
		miCarrito.setAttribute("productosCarrito", null);
		response.sendRedirect("ProductoController");
		
	}

	private void postFinCompra(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
var total_de_compra = request.getParameter("total_compra");
		
		int id_usuario_compra = (int) request.getSession().getAttribute("id_usuario");
	
		double saldo2 = 0;
		double saldo_usuario = dao_banco.Traer_Saldo(id_usuario_compra, saldo2);

		if(  Double.parseDouble(total_de_compra) > saldo_usuario ){
			request.setAttribute("Error", "No tiene saldo suficiente en tu cuenta para realizar esta compra");
			RequestDispatcher rd;
			rd=request.getRequestDispatcher("errores.jsp");
			rd.forward(request, response);
			return;
		}
	    
		
		var errorSession = request.getSession();
		String mensaje = null;
		HttpSession miCarrito = request.getSession(); 
		ArrayList<ProdCarrito> productosCarrito = new ArrayList<ProdCarrito>();
		if(productosCarrito!=null){
			productosCarrito = (ArrayList<ProdCarrito>) miCarrito.getAttribute("productosCarrito");
		}		
		boolean hayStock = comprobarStock(productosCarrito);
		if(!hayStock) {
			 mensaje = "No hay cantidades sufientes de stock. Vuelva a verificar";
			 errorSession.setAttribute("mensaje", mensaje);
			 response.sendRedirect("ProductoController");
		} else {
			
	
		
		if(!productosCarrito.isEmpty()) {
		//Datos factura
		Double total = calcularTotal(miCarrito);
		long miliseconds = System.currentTimeMillis();
	    //Date fecha = new Date(miliseconds);
		var sesion = request.getSession(); 
		int idUser = (int) sesion.getAttribute("id_usu");
		
	
		//instanciamos factura	
		Factura fac = new Factura(idUser,  total);
		
		try {
			dao.insertF(fac);
			dao.insertDetalle(idUser, productosCarrito);
			daoP.actualizarStock(productosCarrito);
			response.sendRedirect("CompraController?accion=compraExitosa");
			
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		miCarrito.setAttribute("productosCarrito", null);
		
		
		}else {
			var mensaje1= "El carrito se encuentra vacio!.";
			request.setAttribute("mensaje", mensaje1 );
			request.setAttribute("tipoAlerta", "warning");
			var rd = request.getRequestDispatcher("vistas/compra/notificacion.jsp");
			rd.forward(request, response);
			
		}
		
		double resultado = saldo_usuario - Double.parseDouble(total_de_compra);
		
		dao_banco.agregar(id_usuario_compra, resultado);
		
		}
	}
/**
 * Comprueba el stock de todos los productos del carrito
 * @param ArrayList<ProdCarrito>
 * @return boolean**/
	private boolean comprobarStock(ArrayList<ProdCarrito> productosCarrito) {
		boolean hayStock = true;
		for(ProdCarrito prod : productosCarrito) {
			if(daoP.obtenerStockDeProd(prod.getId())< prod.getCant()) {
				hayStock= false;
			}
		}
		return hayStock;
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

	private void postAgregarCarrito(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		// TODO Auto-generated method stub
		var errorSessionCarrito = request.getSession();
		String mensaje = null;
		HttpSession miCarrito = request.getSession(); 
		List<ProdCarrito> productosCarrito = new ArrayList<ProdCarrito>();
		if(productosCarrito!=null){
			productosCarrito = (ArrayList<ProdCarrito>) miCarrito.getAttribute("productosCarrito");
		}
		
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
				existe=true;
				if (daoP.obtenerStockDeProd(idProd)<(p.getCant()+cantProd)) {
					//response.sendError(405, "No hay cantidades sufiente de stock.");
					//request.setAttribute("Error", "Error de Usuario o Password");
					 mensaje = "No hay cantidades sufiente de stock.";
					
				}else {
					p.setCant(p.getCant()+cantProd);
					
				}
				
			}
		}
		if(!existe) {
			
			if (daoP.obtenerStockDeProd(idProd)<(cantProd)) {
				//response.sendError(405, "No hay cantidades sufiente de stock.");
				//request.setAttribute("Error", "Error de Usuario o Password");
				 mensaje = "No hay cantidades sufiente de stock.";
				
			}else {
				ProdCarrito pc = new ProdCarrito(idProd, cantProd, precio, nombre);
				productosCarrito.add(pc);
			}
		}
		errorSessionCarrito.setAttribute("mensajeC", mensaje);
		errorSessionCarrito.setAttribute("tipoMensajeC", "danger");
		miCarrito.setAttribute("productosCarrito", productosCarrito);
		response.sendRedirect("ProductoController");
	
		
	}

}
