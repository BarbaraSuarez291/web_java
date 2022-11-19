package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.startup.ClassLoaderFactory.Repository;
import org.apache.tomcat.util.http.fileupload.util.Streams;

import com.mysql.cj.Session;

import daos.BancoDaoBD;
import daos.UsuarioDao;
import models.Usuario;
import services.Service;

/**
 * Servlet implementation class BancoController
 */
@WebServlet("/Banco")
public class BancoController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//Estos son los DAOs que se utilizan el controlador.
	private BancoDaoBD dao;
	private UsuarioDao daousu;

	public BancoController() {
		dao = new BancoDaoBD();
		daousu = new UsuarioDao();
    }   

	/**
	 * En esta seccion recibe tres acciones diferentes(banco,transferir,reinicio) por GET 
	 * despachando a las vistas que corresponda cada accion. Si el accion es null recibida por parametros se le asigna
	 * el valor "banco" el cual retorna el index de controlador atraves del metodo getBanco(request,response).
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*Recibe la "accion" por GET, va despachando los diferentes metodos dependiendo de la accion, 
		  si es null se le asigna el valor "Banco" para despacharlo al index del controlador.*/
		var accion = Optional.ofNullable(request.getParameter("accion")).orElse("banco");
        
        switch (accion.toLowerCase()) {
		case "banco" -> getBanco(request,response);
		case "transferir" -> getTansferir(request,response);
		case "reinicio" -> getVolver(request, response);
		default ->
		throw new IllegalArgumentException("Unexpected value: " + accion);
		}
	}
    /**
     * 
     * @see HttpServlet#getVolver(HttpServletRequest request, HttpServletResponse response)
     * */
	private void getVolver(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Levanta los datos
		int id_usu = (int) request.getSession().getAttribute("id_usuario");
		String  nombre = (String) request.getSession().getAttribute("nombre");
		//Procesa los datos, crea el atributo nombre y la lista  del usuario.
		request.setAttribute("nombre", nombre.trim());
		
		var lista = dao.lista_usuario(id_usu);
		var lista_clientes = Stream.of(lista).toArray();//Recibe un objeto y lo transforma en array
		request.setAttribute("clientes", lista_clientes);
		//Aca despacha al jsp con los datos cargados
		var rd = request.getRequestDispatcher("Banco.jsp");
		rd.forward(request, response);
	}

	private void getTansferir(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Despacha al jsp para vizualizar la vista.
		var rd = request.getRequestDispatcher("Transferir.jsp");
		rd.forward(request, response);
	}

	private void getBanco(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Levanta los datos 
		int id_usu = (int) request.getSession().getAttribute("id_usuario");
		String  nombre = (String) request.getSession().getAttribute("nombre");
	
		//Procesa los datos para armar la lista del usuario
		request.setAttribute("nombre", nombre.trim());
		var lista = dao.lista_usuario(id_usu);
		var lista_clientes = Stream.of(lista).toArray();//Recibe un objeto y lo transforma en array
		request.setAttribute("clientes", lista_clientes);
		
		//Despacha al jsp con los datos cargados
		var rd = request.getRequestDispatcher("Banco.jsp");	
		rd.forward(request, response);
	}

	/**
	 * En esta seccion se gestionan las operaciones que realiza el cliente en su cuenta bancaria 
	 * por el metodo POST.
	 * @param request = "accion";
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*Recibe la "accion" por POST, va despachando los diferentes metodos dependiendo de la accion, 
		  si es null se le asigna el valor "Banco" para despacharlo al index del controlador.*/
		var accion = Optional.ofNullable(request.getParameter("accion")).orElse("banco");
        
        switch (accion.toLowerCase()) {
        case "banco" -> getBanco(request, response);
		case "agregar" -> postAgregar(request,response);
		case "transfiriendo" -> postTransferencia(request, response);
		case "remover" -> postRemover(request,response);	
		default ->
		throw new IllegalArgumentException("Unexpected value: " + accion);
		}
	}
   
	/**
	 * Este metodo recibe la id y la cantidad que desea extraer el usuario de su cuenta, y 
	 * redirecciona al usuario al Banco, contiene validaciones..
	 * @see HttpServlet#postRemover(HttpServletRequest request, HttpServletResponse response)
	 */
	private void postRemover(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		//Levanta los datos 
		int id_usuario = (int) request.getSession().getAttribute("id_usuario");
		var recibido = request.getParameter("remover");
		
		Service s3 = new Service();
		if(!s3.isDouble(recibido)) { //Valida que no se ingresen datos que no sean de tipo Double
			request.setAttribute("Error", "El tipo de dato no es numerico");
			var rd = request.getRequestDispatcher("errores.jsp");
			rd.forward(request, response);
			return;
		}
		
		if(!s3.isVacio(recibido)) { //Valida que no se ingresen datos que no esten vacios
			request.setAttribute("Error", "Ingrese un valor por favor");
			var rd = request.getRequestDispatcher("errores.jsp");
			rd.forward(request, response);
			return;
		}
		
		//Procesa los datos
		double cant_remover = Double.parseDouble(recibido);//Parsea la cantidad recibida a double
		double saldo1 = 0;
	
		var saldo = dao.Traer_Saldo(id_usuario, saldo1);//Trae el saldo del usuario
		 
		//A continuacion valida que el que la cantidad igresada no se mayor que saldo que tiene el usuario
	    if(cant_remover > saldo) {
			request.setAttribute("Error", "Saldo insuficiente para realizar esta operacion");
			var rd2 = request.getRequestDispatcher("errores.jsp");
			rd2.forward(request, response);
			return;
		}
	  //A continuacion valida que el que la cantidad igresada no sea un numero negativo.
	    if (cant_remover < 0) {
			request.setAttribute("Error", "Ingrese un valor valido");
			var rd1 = request.getRequestDispatcher("errores.jsp");
			rd1.forward(request, response);
			return;
		}
	  //Resta la cantidad a remover del saldo.
		double resto = saldo - cant_remover;
	  //Actualiza el saldo del usuario
		dao.agregar(id_usuario, resto);
		
	  //Alerta que se realizo la operacion
	  request.setAttribute("Error", "Extraccion realizada correctamente");
	  var rd3 = request.getRequestDispatcher("errores.jsp");
	  rd3.forward(request, response);
	  return;
	}	    
	/**
	 * Recibe el nombre del usuario que recibe la transferencia de saldo, la cantidad por parametros. La ID del usario que envia
	 * el saldo lo recibe por sesion, contiene validaciones.
	 * @param request = String nombre_usuario, double cantidad;
	 * @param response = response.sendRedirect("Banco");
	 * @see HttpServlet#postTransferencia(HttpServletRequest request, HttpServletResponse response)
	 */
	private void postTransferencia(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		//Levanta los datos 
		String  nombre_destino = request.getParameter("nombre_destinatario");
		var cantidad = request.getParameter("cantidad");
		
		int id_emisor = (int) request.getSession().getAttribute("id_usuario");
        
		Service s5 = new Service();
		 if(!s5.isString(nombre_destino)) { //Valida que no se ingresen datos sean string
				request.setAttribute("Error", "El tipo de dato no es una cadena de texto");
				RequestDispatcher rd;
				rd=request.getRequestDispatcher("errores.jsp");
				rd.forward(request, response);
				return;
	        }
		 
		 if(!s5.isVacio(nombre_destino)) { //Valida que no se ingresen datos vacios
				request.setAttribute("Error", "Ingrese un valor por favor");
				var rd = request.getRequestDispatcher("errores.jsp");
				rd.forward(request, response);
				return;
			}
		 if(!s5.isDouble(cantidad)) { //Valida que no se ingresen datos vacios
				request.setAttribute("Error", "El tipo de dato no es numerico");
				var rd = request.getRequestDispatcher("errores.jsp");
				rd.forward(request, response);
				return;
			}
		 if(!s5.isVacio(cantidad)) { //Valida que no se ingresen datos vacios
				request.setAttribute("Error", "Ingrese un valor por favor");
				var rd = request.getRequestDispatcher("errores.jsp");
				rd.forward(request, response);
				return;
			}
		//Aca valida que el nombre del destinatario exista
		try {
			var nombre_existe = daousu.existeUsuario(nombre_destino.trim());
			if (nombre_existe == false) {
				request.setAttribute("Error", "El nombre ingresado no existe");
				var rd1 = request.getRequestDispatcher("errores.jsp");
				rd1.forward(request, response);
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
        var recibo = request.getParameter("cantidad");
		Service s1 = new Service();
        if(!s1.isDouble(recibo)) { //Valida que no se ingresen datos que no sean de tipo Double
			request.setAttribute("Error", "El tipo de dato no es numerico");
			RequestDispatcher rd;
			rd=request.getRequestDispatcher("errores.jsp");
			rd.forward(request, response);
			return;
        }
		double cantidad1 = Double.parseDouble(recibo);//Parseamos a double la cantidad recibida
		double saldo = 0;
		
		var saldo1 = dao.Traer_Saldo(id_emisor, saldo);//Trae el saldo del usuario emisor del saldo
		//Aca valida que el usuario tenga saldo para realizar el envio
		if (cantidad1 > saldo1) {
			request.setAttribute("Error", "Saldo insuficiente");
			RequestDispatcher rd1;
			rd1=request.getRequestDispatcher("errores.jsp");
			try {
				rd1.forward(request, response);
			} catch (ServletException | IOException e) {
				e.printStackTrace();
			}
			return;
		}
		//Aca valida que la cantidad a enviar no sea negativa
		if( cantidad1 < 0 ){
			request.setAttribute("Error", "No se pueden agregar saldos negativos");
			RequestDispatcher rd;
			rd = request.getRequestDispatcher("errores.jsp");
			rd.forward(request, response);
			return;
		}
	    
		//Aca se descuenta la cantidad del saldo del emisor
		var emisor_saldo = saldo1 - cantidad1;
		
		dao.agregar(id_emisor,emisor_saldo);//Actualiza el saldo del usuario emisor
		
		var saldo_destino = dao.traer_saldo_pornombre(nombre_destino.trim(), saldo); //Trae el saldo del usuario destinatario
		//Suma el saldo del destinatorio con la cantidad, se guarda en una variable
		var saldo_final_destino = saldo_destino + cantidad1;
	    
		//Realiza la transferencia con el nombre y el saldo final al usuario destino.
		dao.Tranferencia(nombre_destino.trim(), saldo_final_destino);
		
		//Alerta que se realizo la operacion
		request.setAttribute("Error", "Su transferencia fue realizada correctamente");
		var rd = request.getRequestDispatcher("errores.jsp");
		rd.forward(request, response);
		return;
	}
	/**
	 * Recibe la cantidad que el usuario quiera agregar, realiza la operacion y redirecciona al Banco, la ID la recibe por
	 * sesion, contiene validaciones.
	 * @param request = double cantidad;
	 * @param response = response.sendRedirect("Banco");
	 * @see HttpServlet#postAgregar(HttpServletRequest request, HttpServletResponse response)
	 */
	private void postAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Levanta los datos
		int id_usu = (int) request.getSession().getAttribute("id_usuario");
		
		var recibo = request.getParameter("cantidad");
		Service s = new Service();
		
		if(!s.isDouble(recibo)) { //Valida que no se ingresen datos que no sean de tipo Double
			request.setAttribute("Error", "El tipo de dato no es numerico");
			RequestDispatcher rd;
			rd=request.getRequestDispatcher("errores.jsp");
			rd.forward(request, response);
			return;
		}else if (!s.isVacio(recibo)) {
			   //Valida que no se ingresen datos vacios
				request.setAttribute("Error", "Ingrese un valor por favor");
				var rd = request.getRequestDispatcher("errores.jsp");
				rd.forward(request, response);
				return;
			
		}else {
			double cantidad = Double.parseDouble(recibo);//Parseamos a double la cantidad recibida
			//Procesamos los datos
			var saldo = dao.Traer_Saldo(id_usu, cantidad);//Trae el saldo del usuario
		   //Valida que no se ingresen numero negativos 
			if( cantidad < 0 ){
				request.setAttribute("Error", "No se pueden agregar saldos negativos");
				RequestDispatcher rd;
				rd=request.getRequestDispatcher("errores.jsp");
				rd.forward(request, response);
				return;
			}else {
		
			//Suma el saldo actual con el que agregamos 
			var total =  cantidad  +  saldo;
			
			//y lo actualiza
			dao.agregar(id_usu,total);
			
			//Alerta que se realizo la operacion
			request.setAttribute("Error", "Su saldo se agrego correctamente");
			var rd = request.getRequestDispatcher("errores.jsp");
			rd.forward(request, response);
			return;
		    
			}
		}

	}

}