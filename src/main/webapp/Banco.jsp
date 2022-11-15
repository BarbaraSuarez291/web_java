<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Bienvenido al Banco</title>
 
 <link rel="shortcut icon" href="assets/museo.png"> 
 
 <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-OERcA2EqjJCMA+/3y+gxIOqMEjwtxJY7qPCqsdltbNJuaOe923+mo//f6V8Qbsw3" crossorigin="anonymous"></script>

</head>
<body>
<script>
 .abs-center {
  display: flex;
  align-items: center;
  justify-content: center;
  min-height: 100vh;
}

.form {
  width: 450px;
}
</script>
<div class="container" align="center">
   <div class="abs-center">
   <h2>Cuenta bancaria</h2>
   <c:forEach var="cliente" items="${clientes}">
 <h5> Bienvenido <c:out value="${cliente.nombre}" /> </h5>
   </c:forEach>
    <p> Agregar dinero a su cuenta: </p>
    <form action="Banco" method="POST">
      <input value="agregar" name="accion" hidden="true">
      <input type="text" name="cantidad" id="cantidad"/>
      <input type="submit" value="Agregar" class="btn btn-primary">
    </form>
   
    <table class="table table-striped table-hover">
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
					<td><a class="btn btn-primary" href="Banco?accion=transferir">Tranferir saldo a otra cuenta</a></td>
					<td><form action="Banco" method="POST">
					    <input value="remover" name="accion" hidden="true">
					    <input type="text" name="remover" id="remover"/>
					     <input type="submit" class="btn btn-outline-success" value="Extraer dinero">
					     </form>
				     </td>
				</tr>
			</c:forEach>
		</tbody>

	</table>
	<div align="center">
		 <p class="alert alert-primary">${Error}</p>
	</div>
  </div>
 </div>
 
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js" integrity="sha384-oBqDVmMz9ATKxIep9tiCxS/Z9fNfEXiDAYTujMAeBAsjFuCZSmKbSSUnQlmh/jp3" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.min.js" integrity="sha384-IDwe1+LCz02ROU9k972gdyvl+AESN10+x7tBKgc9I5HFtuNz0wWnPclzo6p9vxnk" crossorigin="anonymous"></script>
</body>
</html>