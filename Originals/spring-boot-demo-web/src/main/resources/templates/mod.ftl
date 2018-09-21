<html>
	<head>
		<title>modify</title>
	</head>
	<body>
		<form action="/mod" method="POST">
		username:<br>
  		<input type="text" name="username" value=${user.username}><br>
  		First name:<br>
  		<input type="text" name="firstname" value=${user.firstname}><br>
  		Last name:<br>
  		<input type="text" name="lastname" value=${user.lastname}>
  		<input type="submit" value="Submit">
		</form>
	</body>
</html>