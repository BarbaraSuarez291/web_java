<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@page import="daos.ProductoDAO"%>
<%@page import="models.Producto" %>
<%@page import="models.ProdCarrito" %>
<%@page import="java.util.ArrayList" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <% 
    HttpSession miCarrito = request.getSession(); 
    ArrayList<ProdCarrito> productosCarrito ;
    productosCarrito = (ArrayList<ProdCarrito>) miCarrito.getAttribute("productosCarrito");
    Double total = 0.0;
    for(ProdCarrito p : productosCarrito){
    	total += (p.getPrecio()*p.getCant());
    }
    %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Carrito</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
	<link rel="stylesheet" href="css/bootstrap.min.css">   
	<link rel="stylesheet" href="css/styles.css">
</head>
<body>
<div class="container">
	<h1>Carrito</h1>
	

	<% if(productosCarrito!=null){ %>
	<table border="3" class="table table-hover table-warning">
	<thead>
			<th>id</th>
			<th>Cantidad</th>
			<th>Precio Unitario</th>
			<th>Descripcion</th>
			<th></th>
	</thead>
			<c:forEach var="prod" items="${carrito}">
				<tr>
					<td><c:out value="${prod.getId() }" /></td>
					<td><c:out value="${prod.getCant() }" /></td>
					<td><c:out value="${prod.getPrecio()}" /></td>
					<td><c:out value="${prod.getNombre()}" /></td> 
					
	<td><a href="CompraController?accion=deleteItem&id=${prod.getId()}">
	<img alt="imagen cancelar" width="25px"  src="img/cancelar.png" />
		</a> </td>
				</tr>
			</c:forEach>
			<tr>
			<td></td>
			<td></td>
			<td><Strong>Total carrito:</Strong></td> 
			<td><Strong> <%=total %> </Strong></td>
			<td></td>
			</tr>
			
			
			<tr> 
			
			<td></td>
			<td><a class="btn btn-success" href="ProductoController"> Seguir comprando </a> </td>
			<td> 
		<form action="CompraController" method="post">
		<input type="hidden" name="accion" value="vaciarCarrito">
		<input type="hidden" value="${prod}" name="id">
		<p>
			<input  class="btn btn-warning text-white" type="submit" value="Vaciar carrito">
		</p>
		</form>
			
			</td>
			<td>
			
			<form action="CompraController" method="post">
			<input type="hidden" name="accion" value="finCompra">
				<input type="hidden" value="${prod}" name="id">
				<input type="hidden" value="<%=total %>" name="total_compra">
			<p>
			<input class="btn btn-success" type="submit" value="Finalizar compra">
			</p>
			</form>  
			</td>
			<td></td>
			</tr>
		</tbody>

	</table>
	<% }%>
	</div>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js" integrity="sha384-oBqDVmMz9ATKxIep9tiCxS/Z9fNfEXiDAYTujMAeBAsjFuCZSmKbSSUnQlmh/jp3" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.min.js" integrity="sha384-IDwe1+LCz02ROU9k972gdyvl+AESN10+x7tBKgc9I5HFtuNz0wWnPclzo6p9vxnk" crossorigin="anonymous"></script>
</body>

</html>