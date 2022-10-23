<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
crear productos
<form action="ProductoController" method="post">

		<input type="hidden" name="accion" value="insert">
		<p>
			Nombre producto: <input name="nombre">
		</p>
		<p>
			Precio: <input name="precio">
		</p>
		<p>
			Cantidad: <input name="cant">
		</p>
		<p>
			<input type="submit" value="Crear">
		</p>

	</form>

</body>
</html>