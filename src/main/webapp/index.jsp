<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>dynamic-AccessInfo</title>
<script src="Resource/resource.js"></script>
</head>

<body>
	<h1>${accessInfo[0].soName}(${accessInfo[0].soCode})</h1>
	<h1>${accessInfo[0].slName}(${accessInfo[0].slCode})</h1>
	<h1>최근 접속 일시 : ${accessInfo[0].date}</h1>

		<input type="button" value="로그아웃" onClick= "accessOut2()"/>
		<input type="hidden"name="soCode" value="${accessInfo[0].soCode}" /> 
		<input type="hidden" name="slCode" value="${accessInfo[0].slCode}" />
<script>

function accessOut(soCode,slCode){
	const form = makeForm("","AccessOut","get");
	const inputSoCode = makeInputElement("hidden","soCode",soCode,"");
	const inputSlCode = makeInputElement("hidden","slCode",slCode,"");
	
	
	form.appendChild(inputSoCode);
	form.appendChild(inputSlCode);
	
	document.body.appendChild(form);
	form.submit();
}

function accessOut2(soCode,slCode){
	location.href = "AccessOut?soCode=" + soCode + "&slCode=" + slCode;
}


</script>
</body>


</html>