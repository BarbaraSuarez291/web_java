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

</head>
<body>
<h1>Factura</h1>

	
	<br />


	
	<table border="3">
	<thead>
			<th>id producto</th>
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
			<tr><td></td><td>Total compra:</td> <td> 
<c:out value="${factura.total}"
 default="desconocida"/>

 </td> </tr>
			
			<tr> 
			<td>   </td>
			<td> 
		<form action="CompraController" method="post">

		<input type="hidden" name="accion" value="misCompras">
		<input type="hidden" name="id" value=1> <!-- estos seria el id del usuario -->
		<p>
			<input type="submit" value="Mis compras">
		</p>

		</form>
			</td>
			<td><a href="ProductoController"> volver al inicio </a> </td> 
		
			  </tr>
		</tbody>

	</table>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js" integrity="sha384-oBqDVmMz9ATKxIep9tiCxS/Z9fNfEXiDAYTujMAeBAsjFuCZSmKbSSUnQlmh/jp3" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.min.js" integrity="sha384-IDwe1+LCz02ROU9k972gdyvl+AESN10+x7tBKgc9I5HFtuNz0wWnPclzo6p9vxnk" crossorigin="anonymous"></script>
</body>
</html>