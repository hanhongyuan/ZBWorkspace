<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/tags/mx-framework" prefix="mx" %>    

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<mx:Framework debugMode="true"/>
<title>输电装置考核指标计算</title>
<style>
	.sdkhzbLink {
		text-decoration : underline;
		color:blue;
		cursor:pointer;
	}
</style>
</head>
<body>

<mx:WebletContainer id="sdkhzbContainer" webletID="sdkhzb"  bundleName="com.sgcc.pms.zbztjc.jczhgl"></mx:WebletContainer>
</body>
</html>