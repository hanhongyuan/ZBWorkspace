<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/tags/mx-framework" prefix="mx" %>    

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>

<head>
<mx:Framework debugMode="true"/>
<title>输电监测装置统计页面</title>
<style>
	.sdzztjLink {
		text-decoration : underline;
		color:blue;
		cursor:pointer;
	}
</style>
</head>
<body>
<mx:WebletContainer id="sdzztjContainer" webletID="sdzztj"  bundleName="com.sgcc.pms.zbztjc.jczhgl"></mx:WebletContainer>
</body>
</html>