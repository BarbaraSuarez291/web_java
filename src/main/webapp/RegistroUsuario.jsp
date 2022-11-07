<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Sistema Ventas|Registro Usuario</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
</head>
<body class="text-black-50 text-bg-info">
    <div class="container">
    	<div class="row">
    		<div class="col-4"></div>
    		<div class="col-4">
    			<br>
    			<br>
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
    		
    		</div>
    		<div class="col-4"></div>
    	
    	
    	
    	</div>
    
    
    
    
    </div> 
</body>
</html>