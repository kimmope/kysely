<html>
	<head>
		<link rel="stylesheet" href="/bootstrap-3.3.7/css/bootstrap.min.css" />
		<title>User list</title>
	</head>
	<body>
		<table>
			<thead>
				<tr><th>Username</th><th>Firstname</th><th>Lastname</th></tr>
			</thead>
			<tbody>
				<#list listOfUsers as user>
					<tr>
						<td><a href="/user/${user.username}">${user.username}</a></td>
						<td>${user.firstname}</td>
						<td>${user.lastname}</td>
						<td><a href="edituser/${user.username}">Edit</a></td>
						<td><a href="deleteuser?Id=${user.username}">Delete</a></td>
					</tr>
				</#list>						
			</tbody>
		</table>
		<br>
		<a href="adduser">Add record</a>
		<p>Test section:</p>
		<a href="/question/1">Next question</a>
		<br>
	</body>
</html>