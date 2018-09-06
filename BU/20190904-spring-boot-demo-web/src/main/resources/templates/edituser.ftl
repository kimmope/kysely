<html>
	<head>
		<title>Edit user</title>
	</head>
	<body>
		<form action="/edituserform" method="POST">
			Username:<br>
			<input type="text" name="username" value="${user.username}" readonly="readonly"><br>
			First name:<br>
			<input type="text" name="firstname" value="${user.firstname}"><br>			
			Last name:<br>
			<input type="text" name="lastname" value="${user.lastname}"><br>			
			<br>
			<input type="submit" value="Ok">
		</form>
		<br>
		<a href="/userspage">Back</a>
	</body>
</html>