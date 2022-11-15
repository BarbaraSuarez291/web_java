<%@page import="models.ProdCarrito"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%
HttpSession miCarrito = request.getSession(); 
List<ProdCarrito> productosCarrito = new ArrayList<ProdCarrito>();
if(productosCarrito!=null){
	productosCarrito = (List<ProdCarrito>) miCarrito.getAttribute("productosCarrito");
}

int cantidadCarrito;
if(productosCarrito==null){
	cantidadCarrito=0;
}else{
	cantidadCarrito=productosCarrito.size();
}
boolean notificacion = false;
HttpSession errorSession = request.getSession(); 

//HttpSession sesion1 = request.getSession(); 
//var id_usuario = sesion1.getAttribute("id_usu");
//var rol = request.getAttribute("rol");
%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
<title>Productos</title>
	<link rel="stylesheet" href="css/bootstrap.min.css">   
	<link rel="stylesheet" href="css/styles.css">   
	
</head>
<script type="text/javascript">
function ConfirmDelete()
{
var respuesta = confirm("Estas seguro de eleiminar?");
if (respuesta==true){
	return true;
}else{
	return false;
}
}


</script>
<body>
<div class="container ">
	<c:choose>
    <c:when test="${rol.equals('cliente')}">
	<form action="CompraController" method="post">

		<input type="hidden" name="accion" value="misCompras">
		<input type="hidden" name="id" value=${ id_usuario }> <!-- estos seria el id del usuario -->
		<p>
			<input type="submit" class="btn btn-success" value="Mis compras">
		</p>

		</form>
			<form action="Banco" method="post">

		<input type="hidden" name="accion" value="banco">
		<input type="hidden" name="id" value="${ id_usuario }"> <!-- estos seria el id del usuario -->
		<p>
			<input type="submit" class="btn btn-success" value="Banco">
		</p>

		</form>
	</c:when>
	<c:when test="${rol.equals('empleado')}">
	<form action="CompraController" method="get">
		<input type="hidden" name="accion" value="ventas">
		<p>
		<input type="submit" value="Ventas">
		</p>
		</form>
	</c:when>
    
	</c:choose>
	<h1 class="title container text-center" >Productos</h1>

	<c:choose>
    <c:when test="${mensaje!=null}">
<div class="alert alert-dismissible alert-danger">
  <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
  <strong>${ mensaje }</strong> 
</div>
    </c:when>
    
	</c:choose>



	<c:choose>
    <c:when test="${rol.equals('empleado')}">
	<a href="ProductoController?accion=crear">Agregar producto</a>
    </c:when>
	</c:choose>

	<br />
	<br />
<% if(cantidadCarrito>0){
	%> <a href="CompraController" class="icono-carrito"style=""> 
	<img alt="imagen carrito" width="30px"  src="img/carrito-de-compras.png" />(<%=cantidadCarrito %>)</a>   
	<%} %>
<c:out value="${rol }" />

	<table border="1" style="margin-top:8px;" class="table table-hover table-dark">
		<thead>
			<th>id</th>
			<th>Nombre</th>
			<th>Precio</th>
			<th>Cantidad</th>
			<th></th>
		</thead>
		<tbody>
			<c:forEach var="producto" items="${productos}">
				<tr>
					<td><c:out value="${producto.id }" /></td>
					<td><c:out value="${producto.nombre }" /></td>
					<td><c:out value="${producto.precio }" /></td>
					<td><c:out value='${producto.cant }' /></td>
					
					<c:choose>
    <c:when test="${rol.equals('empleado')}">
	<td> <a href = "<c:url value = "ProductoController?accion=editar&id=${producto.id }"/>">Editar</a>
    
    </td>
    <td>
	<form action="ProductoController" method="post">

		<input type="hidden" name="accion" value="delete">
				<input type="hidden" value="${producto.id }" name="id">
		<p>
			<input class="btn btn-danger" type="submit" value="Eliminar" onclick="return ConfirmDelete()">
		</p>

		</form>
				</td>
    
    </c:when>
    
</c:choose>
					
		
			
	<c:choose>
    <c:when test="${rol.equals('cliente')}">
	<td>
		
		
		<form action="CompraController" method="post">
		<input type="hidden" name="accion" value="agregar">
			<p>
			Cantidad: <select name="cantidad">
				<option value=1>1</option>
				<option value=2>2</option>
				<option value=3>3</option>
				<option value=4>4</option>
				<option value=5>5</option>
				<option value=6>6</option>
			</select>
		</p>
		<input type="hidden" value="${producto.id}" name="prod">
		<input type="hidden" value="${producto.nombre}" name="nombre">
		<input type="hidden" value="${producto.precio}" name="precio">
		<p>
		<input class="btn btn-primary" type="submit" value="Agregar al carrito">
		</p>

	</form>
		</td>
    
    </c:when>
    
	</c:choose>
				</tr>
			</c:forEach>
		</tbody>

	</table>



</div>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js" integrity="sha384-oBqDVmMz9ATKxIep9tiCxS/Z9fNfEXiDAYTujMAeBAsjFuCZSmKbSSUnQlmh/jp3" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.min.js" integrity="sha384-IDwe1+LCz02ROU9k972gdyvl+AESN10+x7tBKgc9I5HFtuNz0wWnPclzo6p9vxnk" crossorigin="anonymous"></script>
</body>
</html>