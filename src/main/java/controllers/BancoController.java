package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		default ->
		throw new IllegalArgumentException("Unexpected value: " + accion);
		}
	}

	private void getTansferir(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		var rd = request.getRequestDispatcher("Transferir.jsp");
		rd.forward(request, response);
	}

	private void getBanco(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id_usu = (int) request.getSession().getAttribute("id");
		String  nombre = (String) request.getSession().getAttribute("nombre");
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

	private void postRemover(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		int id_usuario = (int) request.getSession().getAttribute("id");
		
		var recibido = request.getParameter("remover");
		
		double cant_remover = Double.parseDouble(recibido);
		double saldo1 = 0;
		var saldo = dao.Traer_Saldo(id_usuario, saldo1);
		
		double resto = saldo - cant_remover;
		
		dao.agregar(id_usuario, resto);

		response.sendRedirect("Banco");
	}

	private void postTransferencia(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		String  nombre_destino = request.getParameter("nombre_destinatario");
		var cantidad = request.getParameter("cantidad");
		
		int id_emisor = (int) request.getSession().getAttribute("id");
		
        var recibo = request.getParameter("cantidad");
		
		double cantidad1 = Double.parseDouble(recibo);
		double saldo = 0;
		var saldo1 = dao.Traer_Saldo(id_emisor, saldo);
		
		var emisor_saldo = saldo1 - cantidad1;
		
		dao.agregar(id_emisor,emisor_saldo);
		
		var saldo_destino = dao.traer_saldo_pornombre(nombre_destino, saldo);
		
		var saldo_final_destino = saldo_destino + cantidad1;
	
		dao.Tranferencia(nombre_destino, saldo_final_destino);
		
		response.sendRedirect("Banco");
	}

	private void postAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Levanta los datos
		int id_usu = (int) request.getSession().getAttribute("id");
		
		var recibo = request.getParameter("cantidad");
		
		double cantidad = Double.parseDouble(recibo);

		var saldo = dao.Traer_Saldo(id_usu, cantidad);
		//Suma el saldo actual con el que agregamos 
		var total =  cantidad  +  saldo;
		//y lo actualiza
		dao.agregar(id_usu,total);
		
	    response.sendRedirect("Banco");
	}

}
