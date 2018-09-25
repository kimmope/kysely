<html>
	<head>
		<title>Add user</title>
	</head>
	<body>
		<form action="/adduserform" method="POST">
			Username:<br>
			<input type="text" name="username"><br>
			First name:<br>
			<input type="text" name="firstname"><br>			
			Last name:<br>
			<input type="text" name="lastname"><br>			
			<br>
			<input type="submit" value="Add">
		</form>
		<br>
		<br>
		<a href="/userspage">Back</a>
	</body>
</html>