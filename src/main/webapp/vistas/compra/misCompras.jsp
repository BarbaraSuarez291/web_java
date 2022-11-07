<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
        
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Mis compras</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">

</head>
<body>
<table border="3">
	<thead>
			<th>id</th>
			<th>Total</th>
			<th>ver</th>
	</thead>
			<c:forEach var="f" items="${facturas}">
			<tr>
				<td><c:out value="${f.getId() }" /></td>
				<td><c:out value="${f.getTotal() }" /></td>
			<td>
				<form action="CompraController" method="post">
				<input type="hidden" name="accion" value="ver">
				<input type="hidden" value="${f.getId()}" name="id">
				<p>
				<input type="submit" value="Ver factura">
				</p>
				</form> 
			</td>
			</tr>
			</c:forEach>
			
	
		</tbody>

	</table>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js" integrity="sha384-oBqDVmMz9ATKxIep9tiCxS/Z9fNfEXiDAYTujMAeBAsjFuCZSmKbSSUnQlmh/jp3" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.min.js" integrity="sha384-IDwe1+LCz02ROU9k972gdyvl+AESN10+x7tBKgc9I5HFtuNz0wWnPclzo6p9vxnk" crossorigin="anonymous"></script>
</body>
</html>