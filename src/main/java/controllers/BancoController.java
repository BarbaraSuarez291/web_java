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
import models.Usuario;

/**
 * Servlet implementation class BancoController
 */
@WebServlet("/Banco")
public class BancoController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BancoDaoBD dao;

	public BancoController() {
		dao = new BancoDaoBD();
	}   

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		var accion = Optional.ofNullable(request.getParameter("accion")).orElse("banco");
        
        switch (accion) {
		case "banco" -> getBanco(request,response);
		case "transferir" -> getTansferir(request,response);
		case "reinicio" -> getVolver(request, response);
		default ->
		throw new IllegalArgumentException("Unexpected value: " + accion);
		}
	}

	private void getVolver(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int id_usu = (int) request.getSession().getAttribute("id_usuario");
		String  nombre = (String) request.getSession().getAttribute("nombre");
		request.setAttribute("nombre", nombre);
		var lista = dao.lista_usuario(id_usu);
		
		var lista_clientes = Stream.of(lista).toArray();
		request.setAttribute("clientes", lista_clientes);
		
		var rd = request.getRequestDispatcher("Banco.jsp");
		rd.forward(request, response);
	}

	private void getTansferir(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		var rd = request.getRequestDispatcher("Transferir.jsp");
		rd.forward(request, response);
	}

	private void getBanco(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id_usu = (int) request.getSession().getAttribute("id_usuario");
		String  nombre = (String) request.getSession().getAttribute("nombre");
		request.setAttribute("nombre", nombre);
		var lista = dao.lista_usuario(id_usu);
		
		var lista_clientes = Stream.of(lista).toArray();
		request.setAttribute("clientes", lista_clientes);
		var rd = request.getRequestDispatcher("Banco.jsp");	
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    var accion = Optional.ofNullable(request.getParameter("accion")).orElse("banco");
        
        switch (accion) {
        case "banco" -> getBanco(request, response);
		case "agregar" -> postAgregar(request,response);
		case "transfiriendo" -> postTransferencia(request,response);
		case "remover" -> postRemover(request,response);
		default ->
		throw new IllegalArgumentException("Unexpected value: " + accion);
		}
	}

	private void postRemover(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		int id_usuario = (int) request.getSession().getAttribute("id_usuario");
		
		var recibido = request.getParameter("remover");
		
		double cant_remover = Double.parseDouble(recibido);
		double saldo1 = 0;
	
		var saldo = dao.Traer_Saldo(id_usuario, saldo1);
		
	    if(cant_remover > saldo) {
			request.setAttribute("Error", "Saldo insuficiente para realizar esta operacion");
			double saldo_reformado = 0;
			dao.agregar(id_usuario, saldo_reformado);
			RequestDispatcher rd;
			rd=request.getRequestDispatcher("errores.jsp");
			rd.forward(request, response);
			return;
		}
	    
	    if (cant_remover < 0) {
			request.setAttribute("Error", "Ingrese un valor valido");
			double saldo_reformado2 = 0;
			dao.agregar(id_usuario, saldo_reformado2);
			RequestDispatcher rd1;
			rd1=request.getRequestDispatcher("errores.jsp");
			rd1.forward(request, response);
			return;
		}
		
		double resto = saldo - cant_remover;
	
		dao.agregar(id_usuario, resto);

		response.sendRedirect("Banco");
	}

	private void postTransferencia(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		String  nombre_destino = request.getParameter("nombre_destinatario");
		var cantidad = request.getParameter("cantidad");
		
		int id_emisor = (int) request.getSession().getAttribute("id_usuario");
		
        var recibo = request.getParameter("cantidad");
		
		double cantidad1 = Double.parseDouble(recibo);
		double saldo = 0;
		var saldo1 = dao.Traer_Saldo(id_emisor, saldo);
		
		if (cantidad1 > saldo1) {
			request.setAttribute("Error", "Saldo insuficiente");
			RequestDispatcher rd1;
			rd1=request.getRequestDispatcher("errores.jsp");
			rd1.forward(request, response);
			return;
		}
		
		if( cantidad1 < 0 ){
			request.setAttribute("Error", "No se pueden agregar saldos negativos");
			RequestDispatcher rd;
			rd=request.getRequestDispatcher("errores.jsp");
			rd.forward(request, response);
			return;
		}
	
		var emisor_saldo = saldo1 - cantidad1;
		
		dao.agregar(id_emisor,emisor_saldo);
		
		var saldo_destino = dao.traer_saldo_pornombre(nombre_destino, saldo);
		
		var saldo_final_destino = saldo_destino + cantidad1;
	
		dao.Tranferencia(nombre_destino, saldo_final_destino);
		
		response.sendRedirect("Banco");
	}

	private void postAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Levanta los datos
		int id_usu = (int) request.getSession().getAttribute("id_usuario");
		
		var recibo = request.getParameter("cantidad");
		
		double cantidad = Double.parseDouble(recibo);

		var saldo = dao.Traer_Saldo(id_usu, cantidad);
	  
		if( cantidad < 0 ){
			request.setAttribute("Error", "No se pueden agregar saldos negativos");
			RequestDispatcher rd;
			rd=request.getRequestDispatcher("errores.jsp");
			rd.forward(request, response);
			return;
		}
		
		//Suma el saldo actual con el que agregamos 
		var total =  cantidad  +  saldo;
		
		if(total < 0) {
			request.setAttribute("Error", "Error, saldo incorrecto");
			RequestDispatcher rd;
			rd=request.getRequestDispatcher("errores.jsp");
			rd.forward(request, response);
			return;
		}else {
		//y lo actualiza
		dao.agregar(id_usu,total);
		
	    response.sendRedirect("Banco");
	    
		}
	}

}