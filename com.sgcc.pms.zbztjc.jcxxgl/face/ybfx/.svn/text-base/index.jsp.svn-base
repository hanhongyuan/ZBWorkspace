<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
		String uri = request.getContextPath();
		
	
%>

<html xmlns="http://www.w3.org/1999/xhtml">

	<head>
		<title>总部月报信息</title>
	<link rel="stylesheet" href="../ybfx/resources/css/ybfx.css">
	<script type="text/javascript" src="../ybfx/scripts/ybfx.js"></script>
	<script src="../ybfx/scripts/jquery.js"></script>
	</head>
	<script type="text/javascript">
	var uri = "<%=uri%>";
</script>
	<body>
		<div id="div_report_bg">
			<table border="0" cellspacing="0" cellpadding="0" align="right">
				<tr>
					<td class="conLabekText">&nbsp;&nbsp;年度：
					</td>
					<td>
						<select id="yearStrCheck" onchange="changeYear();"></select>
					</td>
					<td class="conLabekText">&nbsp;&nbsp;月份：
					</td>
					<td>
						<select id="monthCheck" onchange="changeMonth();"></select>
					</td>
					<td>
						&nbsp;&nbsp;
						<img src="../ybfx/resources/images/print-preview.png" title="打印月度分析报告" onclick="window.print();" />
					</td>

				</tr>
			</table>
		</div>
<br/>
		<div id="mainDiv">
			<div id="header">
				<h1>
					<span id="prv1"></span>国网公司输电线路状态监测系统
				</h1>

				<h1>
					运行状态考评月报
				</h1>

				</h1>
				<h3>
					（<span id="yr1"></span>年<span id="mt1"></span>月）
				</h3>
			</div>
			<div id="content">
				<div id="capOne">
				<h2>
				一、考核装置概况
				</h2>

				<p>
					本报告仅对66千伏及以上架空输电线路状态监测运行情况进行考评，考评的监测装置类型包括图像监控、气象监测、导线温度监测、等值覆冰厚度监测、杆塔倾斜监测、微风振动监测、导线舞动监测、风偏监测、导线弧垂监测、现场污秽度监测，由于大部分视频监控装置没有接入状态监测系统中，暂不纳入考评。</br>
				</p>
				<p>
					截至<span id="yr2"></span>年<span id="mt2"></span>月，国家电网公司输电线路状态监测装置服役数量共计<span id="ZYZZ"></span>套，涉及66千伏及以上线路<span id="XLZS"></span>条。
					各单位状态监测装置数量见表1，各单位不同电压等级线路的监测装置分布情况见表2，公司系统不同类型监测装置服役数量情况见表3。
				</p>

				<div id="tab1">
					<table id="table1" class="gridtable">
							<caption>
								表1&nbsp;&nbsp;各单位服役的状态监测装置数量（套）
							</caption>

					<tr>
						<th>
							单位
						</th>
						<th>
							在运数量
						</th>
						<th>
							检修/调试数量
						</th>
						<th>
							服役数量
						</th>
					</tr>
					</table>

				注：服役数量=在运数量+检修/调试数量。
				</div>

			<div id="tab2">
			<table id="table2" class="gridtable">
				<caption>
					表2&nbsp;&nbsp;各单位不同电压等级线路的服役状态监测装置数量
				</caption>

				<tr>
					<th>
						单位
					</th>
					<th>
						1000kV
					</th>
					<th>750kV
					</th>
					<th>500kV
					</th>
					<th>330kV
					</th>
					<th>220kV
					</th>
					<th>110kV
					</th>
					<th>66kV
					</th>
					<th>±800kV
					</th>
					<th>±660kV
					</th>
					<th>±500kV
					</th>
					<th>±400kV
					</th>
				</tr>
			</table>

			</div>

			<div id="tab3">
			<table id="table3" class="gridtable">
				<caption>
					表3&nbsp;&nbsp;公司系统不同类型监测装置服役数量（套）
				</caption>

				<tr>
					<th>
						装置类型
					</th>
					<th>
						在运数量
					</th>
					<th>
						检修/调试数量
					</th>
					<th>
						服役数量
					</th>
				</tr>
	
				</table>
				</div>
			</div>
			<div id="capTwo">
				<h2>
					二、考核装置运行情况
				</h2>
				<p>本月各单位监测装置运行考核情况见表，不同类型监测装置运行考核情况见表5、表6。
				</p>

				<div id="tab4">
					<table id="table4" class="gridtable">
						<caption>
							表4&nbsp;&nbsp;各单位监测装置运行考核指标
						</caption>
					<tr>
						<th>
							单位
						</th>
						<th>
							考核装置数
						</th>
						<th>
							平均装置接入率（%）
						</th>
						<th>
							数据接入率（%）
						</th>
						<th>
							数据准确/有效率（%）
						</th>
						<th>
							平均得分
						</th>
						
					</tr>
					
				</table>
				注：</br>
				1.平均装置接入率为一个自然月每天实时在线率的平均值；</br>
				2.数据接入率＝实际上传数据次数/应上传数据总次数×100％</br>
				3.装置数据准确率适用于数据类监测装置，是指考评期内数据类装置上传合理数据次数占装置数据上传总次数的百分比；数据有效率适用于图像监控装置，为图像有效数据上传次数占全部图像上传次数的百分比。</br>
				4.平均得分计算方法见运检二〔2015〕48号文中规定的国家电网公司输电线路在线监测系统运行状态考评方案。

				</div>
				<div id="tab5">
					<table id="table5" class="gridtable">
						<caption>
							表5&nbsp;&nbsp;数据类监测装置考核指标
						</caption>
					<tr>
						<th style="width: 20%">
							监测类型
						</th>
						<th>
							考核装置数（套）
						</th>
						<th>
							装置接入率（%） 
						</th>
						<th>
							数据接入率（%）
						</th>
						<th>
							数据准确率（%）
						</th>
						<th>
							装置错报率（%）
						</th>
						<th>
							装置故障时间（分）
						</th>
						<th>
							平均得分
						</th>
					</tr>
						
					</table>
				</div>
				<div id="tab6">
						<table id="table6" class="gridtable">
							<caption>
								表6&nbsp;&nbsp;图像监控装置考核指标
							</caption>
						<tr>
							<th>
								装置类型
							</th>
							<th>
								考核装置数（套）
							</th>
							<th>
								装置接入率（%） 
							</th>
							<th>
								数据接入率（%）
							</th>
							<th>
								数据有效率（%）
							</th>
							<th>
								装置故障时间（分）
							</th>
							<th>
								平均得分
							</th>
						</tr>

						</table>
				</div>
			</div>
			<div id="capThree">
				<h2>
					三、本月装置存在的主要问题
				</h2>
				<div>
					<h3 class="hindex">
						（一）本月监测装置故障及处理情况
					</h3>
					<p>
						本月各单位服役的监测装置故障及处理情况见表7。
					</p>
					<div id="tab7">
							<table id="table7" class="gridtable">
								<caption>
									表7&nbsp;&nbsp;各单位监测装置故障及处理情况</caption>
								<tr>
									<th rowspan=2 style="width: 20%">
										单位
									</th>
									<th rowspan=2>
										故障装置数（套）
									</th>
									<th rowspan=2>
										故障总次数
									</th>
									<th rowspan=2>
										故障总时间
									</th>
									<th rowspan=2>
										消缺装置数（套）
									</th>
									<th width="46%" colspan=6>
										不同故障原因数（套.次）
									</th>
								</tr>
								<tr>
									<th>
										传感器
									</th>
									<th>
										装置硬件
									</th>
									<th>
										装置软件
									</th>
									<th>
										通讯链路
									</th>
									<th>
										电源
									</th>
									<th>
										其它
									</th>
								</tr>
					
						</table>

					</div>
					<h3 class="hindex">
						（二）本月各类装置误报、漏报情况
					</h3>
					<p>
						本月各单位在运监测装置误报、漏报情况见表8。
					</p>
					<div id="tab8">
						<table id="table8" class="gridtable">
							<caption>
								表8&nbsp;&nbsp;各单位监测装置误报、漏报情况（套）
							</caption>
						<tr>
							<th>
								单位
							</th>
							<th>
								告警装置数
							</th>
							<th>
								告警次数
							</th>
							<th>
								误报次数
							</th>
							<th>
								漏报装置数
							</th>
							<th>
								漏报次数
							</th>
						</tr>
						</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</body>

</html>

