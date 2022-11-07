<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Sistema Ventas| Iniciar Session</title>
 <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">



</head>
<body 	class="text-black-50 text-bg-info" >

<div class="row">
  	<div class="col-4" aling="center">
  		
  	</div>
  	
	
	
  	<div class="col-3 border border-5 rounded" style="margin:120px;">
  		<!-- formulario -->
	<div class="container">
	<br>
	<br>
	<br>
	<div align="center">
  		<img alt="login-icon" src="assets/login-icon.svg" style="height:8rem;">
  	</div>
  	<div class="display-5" align="center">Login Sistema Ventas</div>

<form action="Login" method="POST">
	<div class="mb-2">
		<label class="form-label" for="usuario"><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-person-fill" viewBox="0 0 16 16">
  			<path d="M3 14s-1 0-1-1 1-4 6-4 6 3 6 4-1 1-1 1H3zm5-6a3 3 0 1 0 0-6 3 3 0 0 0 0 6z"/>
			</svg>Usuario</label>
		<input class="form-control" type="text" id="usuario" name="usuario" placeholder="Por favor ingrese su Usuario">
	</div>
	<div class="mb-2">
		<label class="form-label" for="pass"><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-key-fill" viewBox="0 0 16 16">
  			<path d="M3.5 11.5a3.5 3.5 0 1 1 3.163-5H14L15.5 8 14 9.5l-1-1-1 1-1-1-1 1-1-1-1 1H6.663a3.5 3.5 0 0 1-3.163 2zM2.5 9a1 1 0 1 0 0-2 1 1 0 0 0 0 2z"/>
			</svg>Password</label>
		<input class="form-control" type="password" id="pass" name="pass" placeholder="Por favor ingrese su Password">
	</div>
	<br>
	<div align="center"  >
		<input type="submit" class="btn btn-secondary btn-lg" value="&nbsp &nbsp &nbsp  Enviar &nbsp &nbsp &nbsp  ">
	</div>
	<br>


	</form>
	<div align="center">
		 <p class="alert alert-primary">${Error}</p>
	</div>
	<br>
	<div>
		<p>Si no estas registrado.Registrate <a class="link-danger" href="RegistroUsuario.jsp" target="">AQUI</a></p>
	</div>
	
	
	
	</div>

	<div class="col-5"></div>
 </div> 	
	
 </div> 	
  	
  	
  
  
 



   
</body>
</html>