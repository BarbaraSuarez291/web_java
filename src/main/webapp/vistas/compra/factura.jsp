<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Factura</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
	<link rel="stylesheet" href="css/bootstrap.min.css">   
	<link rel="stylesheet" href="css/styles.css">
</head>
<body>
<div class="container">
<h1 class="title">Factura</h1>

	
	<br />


	<h3><c:out value="Usuario : ${usuario.nombre }" /></h3>
	
	<table border="3"  class="table table-hover table-info">
	<thead>
			<th>id</th>
			<th>Cantidad</th>
			<th>Precio Unitario</th>
	</thead>
			<c:forEach var="prod" items="${detalle}">
				<tr>
					<td><c:out value="${prod.id_prod }" /></td>
					<td><c:out value="${prod.cant }" /></td>
					<td><c:out value="${prod.precio}" /></td>
				</tr>
			</c:forEach>
			<tr><td></td><td><strong>Total compra:</strong></td> <td> 
<strong><c:out value="${factura.total}"
 default="desconocida"/></strong>

 </td> </tr>
			
			<tr> 
			<td>   </td>
			<td> 
		
		
			<c:choose>
			
			
    <c:when test="${rol.equals('cliente')}">
    <form action="CompraController" method="post">

		<input type="hidden" name="accion" value="misCompras">
		<input type="hidden" name="id" value=${ usuario.id }> <!-- estos seria el id del usuario -->
    <p>
			<input class="btn btn-info" type="submit" value="Mis compras">
		</p>
				</form>
    </c:when>
    <c:when test="${rol.equals('empleado')}">
    	<form action="CompraController" method="get">
		<input type="hidden" name="accion" value="ventas">
		<p>
		<input type="submit"  class="btn btn-info" value="Ventas">
		</p>
		</form>
    </c:when>
    </c:choose>
		
		


			</td>
			<td><a class="btn btn-success" href="ProductoController"> volver al inicio </a> </td> 
		
			  </tr>
		</tbody>

	</table>
	</div>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js" integrity="sha384-oBqDVmMz9ATKxIep9tiCxS/Z9fNfEXiDAYTujMAeBAsjFuCZSmKbSSUnQlmh/jp3" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.min.js" integrity="sha384-IDwe1+LCz02ROU9k972gdyvl+AESN10+x7tBKgc9I5HFtuNz0wWnPclzo6p9vxnk" crossorigin="anonymous"></script>
</body>
</html>