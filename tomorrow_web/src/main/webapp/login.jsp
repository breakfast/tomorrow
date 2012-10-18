<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Serviço de Autentificação - Breakfasti</title>
</head>
<body>
	<form method="post" action="/login">
		<table>
			<tr>
				<td>Login</td>
				<td><input type="text" name="login" /></td>
			</tr>
			<tr>
				<td>Senha</td>
				<td><input type="password" name="senha" /></td>
			</tr>
			<tr>
				<td></td>
				<td><input type="submit" value="Login" /></td>
			</tr>
		</table>
	</form>


</body>
</html>