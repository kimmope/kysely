<html>
	<head>
		<title>User info</title>
	</head>
	<body>
		<table>
			<thead>
				<tr><th>Username</th><th>Firstname</th><th>Lastname</th></tr>
			</thead>
			<tbody>
				
					<tr><td>${user.username}</td><td>${user.firstname}</td><td>${user.lastname}</td></tr>
					
			</tbody>
			
		</table>
		<form action="/del/${user.username}" method="POST">
		  		<input type="submit" value="Delete">
			</form>
	</body>
</html>