<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/tags/mx-framework" prefix="mx" %>    

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<mx:Framework debugMode="true"/>
<title>输电告警信息查询统计-统计页面</title>
<style>
	.ywjxLink {
		text-decoration : underline;
		color:blue;
		cursor:pointer;
	}
</style>
</head>
<body>

<mx:WebletContainer id="sdgjxxcxtj_statContainer" webletID="sdgjxxcxtj_stat"  bundleName="com.sgcc.pms.zbztjc.kqxt.jcgjgl"></mx:WebletContainer>
</body>
</html>