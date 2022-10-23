
<% HttpSession miCarrito = request.getSession(); %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Productos</title>
</head>
<body>
	<h1>Productos</h1>

	<a href="ProductoController?accion=crear">Agregar producto</a>
	<br />
	<br />

	<table border="1">
		<thead>
			<th>id</th>
			<th>Nombre</th>
			<th>Precio</th>
			<th>Cantidad</th>
		
		</thead>
		<tbody>
			<c:forEach var="producto" items="${productos}">
				<tr>
				
					<td><c:out value="${producto.id }" /></td>
					<td><c:out value="${producto.nombre }" /></td>
					<td><c:out value="${producto.precio }" /></td>
					<td><c:out value='${producto.cant }' /></td>
				
					<td>
						<a href="ProductoController?accion=editar&id=${producto.id }">
						editar
					</a>
				</td>
	<td>
	<form action="ProductoController" method="post">

		<input type="hidden" name="accion" value="delete">
				<input type="hidden" value="${producto.id }" name="id">
		<p>
			<input type="submit" value="Eliminar">
		</p>

		</form>
				</td>
		<td>
		
		
		<form action="CompraController" method="post">
		<input type="hidden" name="accion" value="agregar">
			<p>
			Cantidad: <select name="cantidad">
				<option value=1>1</option>
				<option value=2>2</option>
				<option value=3>3</option>
			</select>
		</p>
		<input type="hidden" value="${producto}" name="prod">
		<p>
		<input type="submit" value="Agregar al carrito">
		</p>

	</form>
		</td>
				</tr>
			</c:forEach>
		</tbody>

	</table>

</body>
</html>