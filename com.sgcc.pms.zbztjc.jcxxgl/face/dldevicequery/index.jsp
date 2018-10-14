<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/tags/mx-framework" prefix="mx" %>    

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<style>
	.ywjxLink {
		text-decoration : underline;
		color:blue;
		cursor:pointer;
	}
	</style>
<mx:Framework debugMode="true"/>
<title>电缆监测信息查询</title>
</head>
<body>

<mx:WebletContainer id="dldevicequeryContainer" webletID="dldevicequery"  bundleName="com.sgcc.pms.zbztjc.jcxxgl"></mx:WebletContainer>
</body>
</html>