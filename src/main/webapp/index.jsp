<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>dynamic-AccessInfo</title>
</head>

<body>
	<h1>${accessInfo[0].soName}(${accessInfo[0].soCode})<h1>
	<h1>${accessInfo[0].slName}(${accessInfo[0].slCode})</h1>
	<h1>최근 접속 일시 : ${accessInfo[0].date}</h1>
	
		<form action="AccessOut" method="post">
			<input type="submit" value="로그아웃" />
			<input type="hidden" name = "soCode" value="${accessInfo[0].soCode}"  />
			<input type="hidden" name = "slCode" value="${accessInfo[0].slCode}"  />
		</form>
</body>
</html>