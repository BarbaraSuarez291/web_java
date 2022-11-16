<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
       
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Productos</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
	<link rel="stylesheet" href="css/bootstrap.min.css">   
	<link rel="stylesheet" href="css/styles.css">
</head>
<body>
<div class="container" style="width:500px; margin-top:30px;">
	<c:choose>
    <c:when test="${not empty mensaje}">
<div class="alert alert-dismissible alert-${tipoMensaje }">
  <button type="submit" class="btn-close" data-bs-dismiss="alert"></button>
  <strong>${ mensaje }</strong> 
</div>
    </c:when>
    
	</c:choose>

	
	
	<div class="card mb-3">

  <div class="card-body">
    <h4 class="card-title title">Editar producto</h4>
   
   
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
			<input class="btn btn-success" type="submit" value="Editar">
		</p>

	</form>
		<a href="ProductoController"> <button class="btn btn-success"> volver</button> </a>
   
   
  </div>
</div>
	
	</div>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js" integrity="sha384-oBqDVmMz9ATKxIep9tiCxS/Z9fNfEXiDAYTujMAeBAsjFuCZSmKbSSUnQlmh/jp3" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.min.js" integrity="sha384-IDwe1+LCz02ROU9k972gdyvl+AESN10+x7tBKgc9I5HFtuNz0wWnPclzo6p9vxnk" crossorigin="anonymous"></script>
</body>
</html>