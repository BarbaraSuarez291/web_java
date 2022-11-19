<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Sistema Ventas|Registro Usuario</title>
	<link rel="stylesheet" href="css/bootstrap.min.css">   
	<link rel="stylesheet" href="css/styles.css">   
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
</head>
<body class="text-black-50 text-bg-info">
    <div class="container">
    	<div class="row">
    		<div class="col-4"></div>
    		<div class="col-4">
    			<br>
    			<br>
    			   	<c:choose>
    <c:when test="${not empty Error}">
	<div class="alert alert-dismissible alert-danger">
	  <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
	  <strong>${ Error }</strong> 
	</div>
    </c:when>
	</c:choose>
    			<br>
    			
    			<P class="display-6">Registro Usuario</P>
    			  			
    			<form action="RegistrarUsuarioController" method="POST">
  					<div class="mb-3">
					    <label for="exampleInputEmail1" class="form-label">Nombre Usuario:</label>
					    <input type="text" class="form-control" id="exampleInputEmail1" name="nombre" placeholder="Ingrese su Nuevo Nombre">
				    </div>
  					<div class="mb-3">
					    <label for="exampleInputPassword1" class="form-label">Password Usuario:</label>
					    <input type="text" class="form-control" id="exampleInputPassword1" name="pass" placeholder="Ingrese su Nueva Clave">
  					</div>
  					<br>
					<div align="center"  >
						<input type="submit" class="btn btn-secondary btn-lg" value="&nbsp &nbsp &nbsp  Enviar &nbsp &nbsp &nbsp  ">
					</div>
  					
				</form>
    				<div >
    				<br>
		<p>Si ya tenes cuenta <a class="link-danger" href="login.jsp" target="">AQUI</a></p>
	</div>
    		</div>
    				
  
    		<div class="col-4"></div>
 
    	
    	
    	</div>
    
    
    
    
    </div> 
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js" integrity="sha384-oBqDVmMz9ATKxIep9tiCxS/Z9fNfEXiDAYTujMAeBAsjFuCZSmKbSSUnQlmh/jp3" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.min.js" integrity="sha384-IDwe1+LCz02ROU9k972gdyvl+AESN10+x7tBKgc9I5HFtuNz0wWnPclzo6p9vxnk" crossorigin="anonymous"></script>
    
</body>
</html>