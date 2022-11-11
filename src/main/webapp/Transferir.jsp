<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Bienvenido al Banco</title>
</head>
<body>

    <form action="Banco" method="POST">
      <input value="transfiriendo" name="accion" hidden="true">
      Nombre del destinatario: <input type="text" name="nombre_destinatario" id="nombre_destinatario"/>
      Cantidad: <input type="text" name="cantidad" id="cantidad"/>
      <input type="submit" value="Enviar">
    </form>
   
</body>
</html>