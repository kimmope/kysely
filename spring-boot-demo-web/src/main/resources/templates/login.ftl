<html>
<#include "head.ftl">	
<body>
<div class="centerer">	
	<form action="/firstQuestionForm" method="POST" id="loginForm">
		<div class="textLeft">
			<h4>Käyttäjätunnus</h4>
		</div>	
		<input type="text" name="username" class="text-input">
		<div class="textLeft">
			<h4>Mistä läänistä olet kotoisin</h4>
			<div class="custom-select">
			  <select name="pid" form="loginForm" required>
			    <option value="" disabled selected>Valitse</option>
			    <option value="979">Itä-Suomen lääni</option>
			    <option value="980">Lapin lääni</option>
			    <option value="981">Oulun lääni</option>
			    <option value="982">Etelä-Suomen lääni</option>
			    <option value="983">Länsi-Suomen lääni</option>
			  </select>
			</div>
		</div>			
		<div class="toolRow">
			<input type="submit" class="answer-button" value="Kirjaudu sisään">
		</div>
	</form>
</div>
<script src="/js/javascript.js"></script>
</body>
</html>