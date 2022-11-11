<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Bienvenido al Banco</title>
</head>
<body>
   <h2>Cuenta</h2>
   <h3> Bienvenido </h3>
    <p> Agregar dinero a su cuenta: </p>
    <form action="Banco" method="POST">
      <input value="agregar" name="accion" hidden="true">
      <input type="text" name="cantidad" id="cantidad"/>
      <input type="submit" value="Agregar">
    </form>
   
   
    <table border="1">
		<thead>
			<th>Nº Cuenta</th>
			<th>Nombre</th>
			<th>Clave</th>
			<th>Rol</th>
			<th>Saldo</th>
		</thead>
		<tbody>
			<c:forEach var="cliente" items="${clientes}">
				<tr>
					<td><c:out value="${cliente.id}" /></td>
					<td><c:out value="${cliente.nombre}" /></td>
					<td><c:out value="${cliente.clave}" /></td>
					<td><c:out value="${cliente.rol}" /></td>
					<td><c:out value="${cliente.saldo}" /></td> 
					<td><a href="Banco?accion=transferir">Tranferir saldo a otra cuenta</a></td>
					<td><form action="Banco" method="POST">
					    <input value="remover" name="accion" hidden="true">
					    <input type="text" name="remover" id="remover"/>
					     <input type="submit" value="Extraer dinero">
					     </form>
				     </td>
				</tr>
			</c:forEach>
		</tbody>

	</table>
	
</body>
</html>