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
<form action="ProductoController" method="post">

		<input type="hidden" value="update" name="accion">
		<input type="hidden" value="${producto.id }" name="id">
		<p>
			Nombre: <input name="nombre" value="${producto.nombre }">
		</p>
		<p>
			Precio: <input name="precio" value="${producto.precio}">
		</p>
		<p>
			Cantidad: <input name="cant" value="${producto.cant}">
		</p>
		
		<p>
			<input type="submit" value="Editar">
		</p>

	</form>

</body>
</html>