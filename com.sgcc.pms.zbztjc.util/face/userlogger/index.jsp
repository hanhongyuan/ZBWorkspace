<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/tags/mx-framework" prefix="mx" %>    

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<mx:Framework debugMode="true"/>
<script type="text/javascript" src="resources/js/echarts.min.js"></script>
<title>用户审计</title>
</head>
<body>

<mx:WebletContainer id="userloggerContainer" webletID="userlogger"  bundleName="com.sgcc.pms.zbztjc.util"></mx:WebletContainer>
</body>
</html>