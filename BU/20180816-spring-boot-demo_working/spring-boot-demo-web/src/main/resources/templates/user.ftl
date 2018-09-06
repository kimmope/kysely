<html>
	<head>
		<title>User information</title>
	</head>
	<body>
		<table>
			<thead>
				<tr><th>Username</th><th>Firstname</th><th>Lastname</th></tr>
			</thead>
			<tbody>
				<tr>
					<td>${user.username}</td>
					<td>${user.firstname}</td>
					<td>${user.lastname}</td>
				</tr>
			</tbody>
		</table>
		<a href="/userspage">Back</a>
		<!--
		<a href="/users">Back</a>
		-->
	</body>
</html>