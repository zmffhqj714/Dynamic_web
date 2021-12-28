<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>매니지먼트 창</title>
</head>

<style>
#sidemenu{

display: flex;
flex-flow: row nowrap;
height: 95%;
width: 18%;
float: left;
}

body{
background:beige;
}
h2{
font-size: 15pt; margin: 20px 0 20px 10px;
}
.menuContainer{

width: 90%; margin:0 auto;}
.managements{
margin-bottom:13pt; background:#fff;}

.menuTitle{
background:pink;
padding: 5px; color: #fff;
background:#333; cursor: pointer;
text-align:center;}
items{
padding:0px 20px 3px 10px;
font-size:10px; text-align:right;}
#footer{
display: flex;
align-items: flex-end;
}


</style>
<body>
	<div id="wrap">
		<div id="top">
      <span>${accessInfo[0].soName}(${accessInfo[0].soCode})</span>
       <span>${accessInfo[0].slName}(${accessInfo[0].slCode})</span>
      <span>최근 접속 일시 : ${accessInfo[0].date}</span>
       <input type="button"
         id=btn value="로그아웃"
         onClick="accessOut('${accessInfo[0].soCode}', '${accessInfo[0].slCode}')" />

   </div>
		<div id="sidemenu">
			<div class="menuContainer">
				<h2>${accessInfo[0].soName}</h2>
				<section class="menuContainer">
					<article class="managements Active"> <!-- .high class ,Open&Close, DashBoard -->
						<p class="menuTitle">Daily Report</p>					
					</article>
					<article class="managements">
						<p class="menuTitle">영업관리</p>
						<div class="items">
							<p>금월매출정보</p> <!-- 금월기본조회, 월별, 일별 매출 정보 검색 -->
							<p>상품매출정보</p> <!-- 금월기본조회, 월별 매출 정보 검색 -->
							<p>요일매출정보</p> <!-- 금년기본조회, 년도별 정보 검색 -->
							<p>회원매출정보</p> <!-- 회원기본조회, 회원단위 월별 정보 검색 -->
						</div>
					</article>
					<article class="managements">
						<p class="menuTitle">직원관리</p>
						<div class="items">
							<p><span onClick="getEmpList('${accessInfo[0].soCode}')">직원리스트</span></p> <!-- 전체 직원 리스트, 직원별 검색 -->
							<p>직원정보등록</p> <!-- 개별 직원 등록 -->
							<p>직원정보수정</p> <!-- 개별 직원 정보 수정(상태 정보 수정) -->
						</div>
					</article>
					<article class="managements">
						<p class="menuTitle">회원관리</p>
						<div class="items">
							<p>회원리스트</p>	<!-- 전체 회원 리스트, 회원별 검색 -->
							<p>회원정보등록</p> <!--  개별 회원 등록 -->
							<p>회원정보수정</p> <!--  개별 회원 정보 수정(상태 정보 수정) -->
						</div>
					</article>
					<article class="managements">
						<p class="menuTitle">상품관리</p>
						<div class="items">
							<p>상품리스트</p>  <!-- 전체 상품 리스트, 상품별 검색 -->
							<p>상품정보등록</p> <!-- 개별 상품 등록 -->
							<p>상품정보수정</p> <!--  개별 상품 정보 수정(상태 정보 수정) -->
						</div>
					</article>
				</section>
			</div>
		</div> 
		<div id="contents">
		<br>
		<br>
		<br>
			<div>${list}</div>
		</div> 
		<div id="footer"><span>Designed By 본본</span></div> 
	</div>

</body>
<script src="Resource/resource.js"></script>
<script>
function modEmp(soCode, slCode){
	alert(soCode + " : " + slCode);
}


function getEmpList(soCode){
	const form = makeForm("","EmpList","post");
	const input = makeInputElement("hidden", "soCode", soCode, "");
	form.appendChild(input);
	
	document.body.appendChild(form);
	form.submit();
}


let menuZone = document.getElementsByClassName("managements");
let menuTitle = document.getElementsByClassName("menuTitle");
let menuItems = document.getElementsByClassName("items");

for(let titleIdx=0; titleIdx < menuTitle.length; titleIdx++){ <!--이벤트 만들어주는 법-->
	menuTitle[titleIdx].addEventListener("click",function(e){
		for(let zoneIdx=0; zoneIdx < menuZone.length; zoneIdx++){
			menuZone[zoneIdx].classList.remove("Active");
		}
		e.target.parentNode.classList.add("Active");
		activateItems();
	});
}


function activateItems(){
	for(let itemsIdx=0; itemsIdx<menuItems.length; itemsIdx++){
		menuItems[itemsIdx].style.display = "none";
	}
	const activeItems = document.querySelector(".managements.Active .items");
	
	if(activeItems != null){
	activeItems.style.display = "block";
	}
}
	activateItems();
</script>
</html>