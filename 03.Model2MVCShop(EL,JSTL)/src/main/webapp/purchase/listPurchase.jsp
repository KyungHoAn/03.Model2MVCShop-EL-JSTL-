
<%@page import="java.util.ArrayList"%>
<%@page import="com.model2.mvc.service.domain.Purchase"%>
<%@page import="com.model2.mvc.service.domain.User"%>
<%@page import="com.model2.mvc.common.Search"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>

<%-- <%
	Map<String, Object> map = (Map<String,Object>)request.getAttribute("map");
	Search search = (Search)request.getAttribute("search");
	
	User user = (User)session.getAttribute("user");
	
	int total = 0;
	List<Purchase> list = null;
	if(map != null){
		total = ((Integer)map.get("totalCount")).intValue();
		list = (List<Purchase>)map.get("list");
	}
	
	int currentPage = search.getCurrentPage();
	
	int totalPage=0;
	if(total>0){
		totalPage=total/search.getPageSize();
		if(total%search.getPageSize()>0){
			totalPage +=1;
		}
	}
%> --%>

<html>
<head>
<title>구매 목록조회</title>

<link rel="stylesheet" href="/css/admin.css" type="text/css">

<script type="text/javascript">
	function fncGetUserList() {
		document.detailForm.submit();
	}
</script>
</head>

<body bgcolor="#ffffff" text="#000000">

<div style="width: 98%; margin-left: 10px;">

<form name="detailForm" action="/listUser.do" method="post">

<table width="100%" height="37" border="0" cellpadding="0"	cellspacing="0">
	<tr>
		<td width="15" height="37"><img src="/images/ct_ttl_img01.gif"width="15" height="37"></td>
		<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left: 10px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="93%" class="ct_ttl01">구매 목록조회</td>
				</tr>
			</table>
		</td>
		<td width="12" height="37"><img src="/images/ct_ttl_img03.gif"	width="12" height="37"></td>
	</tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0"	style="margin-top: 10px;">
	<tr>
		<td colspan="11">전체 2 건수, 현재 1 페이지</td>
	</tr>
	<tr>
		<td class="ct_list_b" width="100">No</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">회원ID</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">회원명</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">전화번호</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">배송현황</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">정보수정</td>
	</tr>
	<tr>
		<td colspan="11" bgcolor="808285" height="1"></td>
	</tr>
<%-- 	<%
		int no = list.size();
		for(int i=0; i<list.size(); i++){
			Purchase purchase = (Purchase)list.get(i);
	%> --%>
	<c:if test="${resultPage.totalCount > 0 }">
			<c:set var="i" value="0"/>
			<c:forEach var="purchase" items="${ list }">
			<tr class="ct_list_pop"> 
				<c:set var="i" value="${ i+1 }"/>
				<td align="center">
				<a href="/getPurchase.do?tranNo=${purchase.tranNo }">${i }</a>
			</td>
			<td></td>
			<td align="left">
				<a href="/getUser.do?userId=${purchase.buyer.userId}">${purchase.buyer.userId}</a>
			</td>
			<td></td>
			<td align="left">${purchase.receiverName}</td>
			<td></td>
			<td align="left">${purchase.receiverPhone}</td>
			<td></td>
			<td align="left">
			<c:choose>
				<c:when test="${purchase.tranCode eq '1' }">
					현재구매완료상태입니다.
				</c:when>
				<c:when test="${purchase.tranCode eq '2' }">
					현재배송중상태입니다.
				</c:when>
				<c:when test="${purchase.tranCode eq '3' }">
					현재배송완료상태입니다.
				</c:when>
			</c:choose>
			<%-- <%
				if(purchase.getTranCode().equals("1")){
			%>
				현재구매완료상태입니다
			<%
				}else if(purchase.getTranCode().equals("2")){
			%>
				현재배송중상태입니다.
			<%
				}else if(purchase.getTranCode().equals("3")) {
			%>
				현재배송완료상태입니다.
			<%} %></td> --%>
			<td></td>
			<td align="left">
			<c:choose>
				<c:when test="${purchase.tranCode eq '2' }">
					<a href="/updateTranCode.do?prodNo=${purchase.purchaseProd.prodNo}&tranCode=3">물건도착</a>
				</c:when>
			</c:choose>
			</td>
			</tr>
			<tr>
				<td colspan="11" bgcolor="D6D7D6" height="1"></td>
			</tr>
	</c:forEach>
	</c:if>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top: 10px;">
	<tr>
		<td align="center">
		<input type="hidden" id="currentPage" name="currentPage" value=""/>
			 <jsp:include page="../common/pageNavigator.jsp"/>
		</td>
	</tr>
</table>

<!--  페이지 Navigator 끝 -->
</form>

</div>

</body>
</html>