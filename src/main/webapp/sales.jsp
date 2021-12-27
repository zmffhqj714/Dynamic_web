<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>세일즈페이지</title>
</head>



<style>
body{
dispaly:flex;

}
span {
   margin: 5px;
   color: #000000;
   font-size: 12pt;
}

.header {
   text-align: right;
   background-color: #BDBDBD;
}
#footer{
 background-color: #BDBDBD;
}

#btn {
   color: #ffffff;
   height: 30px;
   border: 1px solid #000000;
   font-weight: 800;
   background-color: #000000;
}

#menuWrap {
   position: absolute;
   width: 60%;
   height: 150px;
   top: 50%;
   left: 50%;
   transform: translate(-50%, -50%);
}

.divButton, .managementOver, .salesOver {
   float: left;
   height: 140px;
   line-height: 140px;
   font-size: 15pt;
   text-align: center;
   font-weight: 800;
   cursor:pointer;
   border-radius: 5% 30% 5% 30%;
}

#management {
   width: 48%;
   color: #000000;
   border: 1px solid #000000;
   margin-right: 20px;
}

.managementOver {
   background-color: #747474;
   border: 1px solid #000000;
}

#sales {
   width: 48%;
   color: #000000;
   border: 1px solid #000000;
}

.salesOver {
   background-color: #ffffff;
   color: #000000;
   border: 1px solid #000000;
}
</style>
</head>
<body>
   <div class="header">
		<span>${accessInfo[0].soName}(${accessInfo[0].soCode})</span> <span>${accessInfo[0].slName}(${accessInfo[0].slCode})</span>
		<span>최근 접속 일시 : ${accessInfo[0].date}</span> <input type="button"
			id=btn value="로그아웃"
			onClick="accessOut('${accessInfo[0].soCode}', '${accessInfo[0].slCode}')" />
	</div>
  <div class= footer>조본이만듬</div>
<script src="Resource/resource.js"></script>
   <script>
    
      function mouseOver(obj) {   
         obj.className = "managementOver";
      }
      function mouseLeave(obj) {
         obj.className = "divButton";
      }
      function moveServices(action, pSoCode, pSlCode){
         const form = makeForm("",action, "post");
         const soCode = makeInputElement("hidden", "soCode", pSoCode, "");
         const slCode = makeInputElement("hidden", "slCode", pSlCode, "");
         form.appendChild(soCode);
         form.appendChild(slCode);
         
         document.body.appendChild(form);
         form.submit();
      }
   </script>
</body>

</html>