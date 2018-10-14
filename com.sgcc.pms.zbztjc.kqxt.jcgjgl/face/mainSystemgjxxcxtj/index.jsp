<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/tags/mx-framework" prefix="mx" %>    

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<mx:Framework debugMode="true"/>
<title>主要系统告警信息查询统计</title>
<style>
	.ywjxLinkBd {
		text-decoration : underline;
		color:blue;
		cursor:pointer;
	}
	
	.ssxtLink{
		text-decoration : underline;
		color:blue;
		cursor:pointer;
	}
	.ssxtLinkDetail{
		text-decoration : underline;
		color:blue;
		cursor:pointer;
	}
</style>

</head>
<body>

<mx:WebletContainer id="mainSystemgjxxcxtjContainer" webletID="mainSystemgjxxcxtj"  bundleName="com.sgcc.pms.zbztjc.kqxt.jcgjgl"></mx:WebletContainer>
</body>
</html>