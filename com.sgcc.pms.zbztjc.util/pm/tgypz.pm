<?xml version="1.0" encoding="UTF-8"?>
<app-data>
<database name="db" package="null" defaultIdMethod="null" baseClass="BaseObject" basePeer="BasePeer">
<table name="CMST_EQUIPMENT_DISPOSE" id="2c905e725b1e5965015b1e6353080001" javaName="CmstEquipmentDispose" idMethod="uuid.hex" isLinkTable="false" pkg="CMST_EQUIPMENT_DISPOSE" skipSql="false" abstract="false" chineseDescription="CMST_EQUIPMENT_DISPOSE" generatePO="true" lazy="true" generatePK="true" generateFK="true">
    <meta key="height" value="241" />
    <meta key="width" value="196" />
    <meta key="xpos" value="538" />
    <meta key="ypos" value="18" />
    <column name="LINKEDSTATION" id="2c905e725b1e5965015b1e6355dc0002" javaName="linkedstation" description="LINKEDSTATION" required="false" type="VARCHAR" size="17" editorType="text" default="" inputSize="80" order="false" />
    <column name="SBLX" id="2c905e725b1e5965015b1e63609c0003" javaName="sblx" description="设备类型" required="false" type="VARCHAR" size="10" editorType="dropdown" default="" inputSize="80" order="false" >
        <dictionary-db displayColName="设备类型" tableId="2c905e725b21f71a015b2243ed15001c" tableName="CMSMV_PMSAPP_UD_GY_SBLX" pmFilePath="/com.sgcc.pms.zbztjc.util/pm/tgypz.pm" keyColName="sblxbm" valueColName="sblx" filterStr="1=1" orderStr="sblxbm" />
    </column>
    <column name="MONITORINGTYPE" id="2c905e725b1e5965015b1e63609d0004" javaName="monitoringtype" description="监测类型" required="false" type="VARCHAR" size="10" editorType="dropdown" default="" inputSize="80" order="false" >
        <dictionary-db displayColName="监测类型" tableId="2c905e725b21f71a015b2244c6c5008a" tableName="CMST_MONITORINGTYPE" pmFilePath="/com.sgcc.pms.zbztjc.util/pm/tgypz.pm" keyColName="typecode" valueColName="typename" filterStr="1=1" orderStr="typecode" />
    </column>
    <column name="DEVICECODE" id="2c905e725b1e5965015b1e63609d0005" javaName="devicecode" description="DEVICECODE" required="false" type="VARCHAR" size="17" editorType="text" default="" inputSize="80" order="false" />
    <column name="DYDJ" id="2c905e725b1e5965015b1e63609d0006" javaName="dydj" description="DYDJ" required="false" type="VARCHAR" size="10" editorType="text" default="" inputSize="80" order="false" />
    <column name="XH" id="2c905e725b1e5965015b1e63609e0007" javaName="xh" description="XH" primaryKey="true" required="true" type="VARCHAR" size="10" editorType="text" default="" inputSize="80" order="false" />
    <column name="SCMC" id="2c905e725b1e5965015b1e63609e0008" javaName="scmc" description="设备名称" required="false" type="VARCHAR" size="200" editorType="dropdown" default="" inputSize="80" order="false" >
        <dictionary-db displayColName="设备名称" tableId="2c905e725b21f71a015b224472ea003a" tableName="CMSMV_PMSAPP_UD_SBD_SB" pmFilePath="/com.sgcc.pms.zbztjc.util/pm/tgypz.pm" keyColName="yxbh" valueColName="sbmc" filterStr="1=1" orderStr="1=1" />
    </column>
    <column name="YXBH" id="2c905e725b1e5965015b1e63609f0009" javaName="yxbh" description="YXBH" required="false" type="VARCHAR" size="42" editorType="text" default="" inputSize="80" order="false" />
    <column name="SBLXMC" id="2c905e725b1e5965015b1e63609f000a" javaName="sblxmc" description="SBLXMC" required="false" type="VARCHAR" size="50" editorType="text" default="" inputSize="80" order="false" />
    <column name="TYPENAME" id="2c905e725b1e5965015b1e63609f000b" javaName="typename" description="TYPENAME" required="false" type="VARCHAR" size="50" editorType="text" default="" inputSize="80" order="false" />
    <column name="DEVICECODENAME" id="2c905e725b1e5965015b1e6360a0000c" javaName="devicecodename" description="DEVICECODENAME" required="false" type="VARCHAR" size="100" editorType="text" default="" inputSize="80" order="false" />
    <column name="DYDJMC" id="2c905e725b1e5965015b1e6360a0000d" javaName="dydjmc" description="DYDJMC" required="false" type="VARCHAR" size="50" editorType="text" default="" inputSize="80" order="false" />
</table>
<table name="CMSMV_PMSAPP_UD_GY_SBLX" id="2c905e725b21f71a015b2243ed15001c" javaName="CmsmvPmsappUdGySblx" idMethod="uuid.hex" isLinkTable="false" skipSql="false" abstract="false" chineseDescription="CMSMV_PMSAPP_UD_GY_SBLX" generatePO="true" lazy="true" generatePK="true" generateFK="true">
    <meta key="height" value="272" />
    <meta key="width" value="160" />
    <meta key="xpos" value="835" />
    <meta key="ypos" value="323" />
    <column name="OBJ_ID" id="2c905e725b21f71a015b2243efc4001d" javaName="objId" primaryKey="true" required="true" type="VARCHAR" size="42" inputSize="80" order="false" />
    <column name="OBJ_DISPIDX" id="2c905e725b21f71a015b2243f89f001e" javaName="objDispidx" required="true" type="NUMERIC" size="22" inputSize="80" order="false" />
    <column name="SBLXBM" id="2c905e725b21f71a015b2243f89f001f" javaName="sblxbm" required="false" type="VARCHAR" size="50" inputSize="80" order="false" />
    <column name="SBLX" id="2c905e725b21f71a015b2243f89f0020" javaName="sblx" required="false" type="VARCHAR" size="50" inputSize="80" order="false" />
    <column name="FSBLX" id="2c905e725b21f71a015b2243f89f0021" javaName="fsblx" required="false" type="VARCHAR" size="50" inputSize="80" order="false" />
    <column name="SFGDBJ" id="2c905e725b21f71a015b2243f89f0022" javaName="sfgdbj" required="false" type="VARCHAR" size="2" inputSize="80" order="false" />
    <column name="ZYXZ" id="2c905e725b21f71a015b2243f89f0023" javaName="zyxz" required="false" type="VARCHAR" size="20" inputSize="80" order="false" />
    <column name="JLDW" id="2c905e725b21f71a015b2243f89f0024" javaName="jldw" required="false" type="VARCHAR" size="20" inputSize="80" order="false" />
    <column name="SFXJY" id="2c905e725b21f71a015b2243f89f0025" javaName="sfxjy" required="false" type="VARCHAR" size="2" inputSize="80" order="false" />
    <column name="JYZQ" id="2c905e725b21f71a015b2243f89f0026" javaName="jyzq" required="false" type="NUMERIC" size="2" inputSize="80" order="false" />
    <column name="BPBJXZ" id="2c905e725b21f71a015b2243f89f0027" javaName="bpbjxz" required="false" type="VARCHAR" size="5" inputSize="80" order="false" />
    <column name="SFGWBZ" id="2c905e725b21f71a015b2243f89f0028" javaName="sfgwbz" required="false" type="VARCHAR" size="2" inputSize="80" order="false" />
    <column name="SFYXSBBM" id="2c905e725b21f71a015b2243f89f0029" javaName="sfyxsbbm" required="false" type="VARCHAR" size="2" inputSize="80" order="false" />
    <column name="YQYBLX" id="2c905e725b21f71a015b2243f89f002a" javaName="yqyblx" required="false" type="VARCHAR" size="42" inputSize="80" order="false" />
</table>
<table name="CMSMV_PMSAPP_UD_SBD_SB" id="2c905e725b21f71a015b224472ea003a" javaName="CmsmvPmsappUdSbdSb" idMethod="uuid.hex" isLinkTable="false" skipSql="false" abstract="false" chineseDescription="CMSMV_PMSAPP_UD_SBD_SB" generatePO="true" lazy="true" generatePK="true" generateFK="true">
    <meta key="height" value="393" />
    <meta key="width" value="219" />
    <meta key="xpos" value="176" />
    <meta key="ypos" value="136" />
    <column name="OBJ_ID" id="2c905e725b21f71a015b224474bf003b" javaName="objId" primaryKey="true" required="true" type="VARCHAR" size="42" inputSize="80" order="false" />
    <column name="OBJ_DISPIDX" id="2c905e725b21f71a015b224474cf003c" javaName="objDispidx" required="true" type="NUMERIC" size="22" inputSize="80" order="false" />
    <column name="DYDJ" id="2c905e725b21f71a015b224474cf003d" javaName="dydj" required="false" type="VARCHAR" size="20" inputSize="80" order="false" />
    <column name="JGDY" id="2c905e725b21f71a015b224474cf003e" javaName="jgdy" required="false" type="VARCHAR" size="42" inputSize="80" order="false" />
    <column name="SBXH" id="2c905e725b21f71a015b224474cf003f" javaName="sbxh" required="false" type="VARCHAR" size="50" inputSize="80" order="false" />
    <column name="TYRQ" id="2c905e725b21f71a015b224474cf0040" javaName="tyrq" required="false" type="DATE" editorType="datetime" inputSize="80" order="false" />
    <column name="CCBH" id="2c905e725b21f71a015b224474cf0041" javaName="ccbh" required="false" type="VARCHAR" size="50" inputSize="80" order="false" />
    <column name="SXHDX" id="2c905e725b21f71a015b224474cf0042" javaName="sxhdx" required="false" type="VARCHAR" size="8" inputSize="80" order="false" />
    <column name="XB" id="2c905e725b21f71a015b224474cf0043" javaName="xb" required="false" type="VARCHAR" size="20" inputSize="80" order="false" />
    <column name="SBMC" id="2c905e725b21f71a015b224474cf0044" javaName="sbmc" required="false" type="VARCHAR" size="60" inputSize="80" order="false" />
    <column name="BDZ" id="2c905e725b21f71a015b224474cf0045" javaName="bdz" required="false" type="VARCHAR" size="42" inputSize="80" order="false" />
    <column name="SCCJ" id="2c905e725b21f71a015b224474cf0046" javaName="sccj" required="false" type="VARCHAR" size="50" inputSize="80" order="false" />
    <column name="SSSJ" id="2c905e725b21f71a015b224474cf0047" javaName="sssj" required="false" type="VARCHAR" size="42" inputSize="80" order="false" />
    <column name="ZCSSJB" id="2c905e725b21f71a015b224474cf0048" javaName="zcssjb" required="false" type="VARCHAR" size="40" inputSize="80" order="false" />
    <column name="ZCSSDW" id="2c905e725b21f71a015b224474cf0049" javaName="zcssdw" required="false" type="VARCHAR" size="42" inputSize="80" order="false" />
    <column name="YXBH" id="2c905e725b21f71a015b224474cf004a" javaName="yxbh" required="false" type="VARCHAR" size="42" inputSize="80" order="false" />
    <column name="EDDL" id="2c905e725b21f71a015b224474cf004b" javaName="eddl" required="false" type="VARCHAR" size="30" inputSize="80" order="false" />
    <column name="EDPL" id="2c905e725b21f71a015b224474cf004c" javaName="edpl" required="false" type="NUMERIC" size="12" inputSize="80" order="false" />
    <column name="SYHJ" id="2c905e725b21f71a015b224474cf004d" javaName="syhj" required="false" type="VARCHAR" size="30" inputSize="80" order="false" />
    <column name="JYNRDJ" id="2c905e725b21f71a015b224474cf004e" javaName="jynrdj" required="false" type="VARCHAR" size="30" inputSize="80" order="false" />
    <column name="FWDJ" id="2c905e725b21f71a015b224474cf004f" javaName="fwdj" required="false" type="VARCHAR" size="30" inputSize="80" order="false" />
    <column name="CCRQ" id="2c905e725b21f71a015b224474cf0050" javaName="ccrq" required="false" type="DATE" editorType="datetime" inputSize="80" order="false" />
    <column name="ZCBM" id="2c905e725b21f71a015b224474cf0051" javaName="zcbm" required="false" type="VARCHAR" size="42" inputSize="80" order="false" />
    <column name="SBLX" id="2c905e725b21f71a015b224474cf0052" javaName="sblx" required="false" type="VARCHAR" size="42" inputSize="80" order="false" />
    <column name="YXZT" id="2c905e725b21f71a015b224474cf0053" javaName="yxzt" required="false" type="VARCHAR" size="42" inputSize="80" order="false" />
    <column name="SHR" id="2c905e725b21f71a015b224474cf0054" javaName="shr" required="false" type="VARCHAR" size="42" inputSize="80" order="false" />
    <column name="SHSJ" id="2c905e725b21f71a015b224474cf0055" javaName="shsj" required="false" type="DATE" editorType="datetime" inputSize="80" order="false" />
    <column name="BZ" id="2c905e725b21f71a015b224474cf0056" javaName="bz" required="false" type="VARCHAR" size="1000" inputSize="80" order="false" />
    <column name="SFSH" id="2c905e725b21f71a015b224474cf0057" javaName="sfsh" required="false" type="VARCHAR" size="10" inputSize="80" order="false" />
    <column name="GLOBAL_ID" id="2c905e725b21f71a015b224474cf0058" javaName="globalId" required="false" type="VARCHAR" size="17" inputSize="80" order="false" />
    <column name="CPDH" id="2c905e725b21f71a015b224474cf0059" javaName="cpdh" required="false" type="VARCHAR" size="42" inputSize="80" order="false" />
    <column name="ZZGJ" id="2c905e725b21f71a015b224474cf005a" javaName="zzgj" required="false" type="VARCHAR" size="50" inputSize="80" order="false" />
    <column name="EDDY" id="2c905e725b21f71a015b224474cf005b" javaName="eddy" required="false" type="VARCHAR" size="50" inputSize="80" order="false" />
    <column name="ZJTYRQ" id="2c905e725b21f71a015b224474cf005c" javaName="zjtyrq" required="false" type="DATE" editorType="datetime" inputSize="80" order="false" />
    <column name="ZCSSDWID" id="2c905e725b21f71a015b224474cf005d" javaName="zcssdwid" required="false" type="VARCHAR" size="42" inputSize="80" order="false" />
    <column name="JB" id="2c905e725b21f71a015b224474cf005e" javaName="jb" required="false" type="VARCHAR" size="20" inputSize="80" order="false" />
    <column name="ZJWTZLWS" id="2c905e725b21f71a015b224474cf005f" javaName="zjwtzlws" required="false" type="VARCHAR" size="42" inputSize="80" order="false" />
    <column name="EJWTZLWS" id="2c905e725b21f71a015b224474cf0060" javaName="ejwtzlws" required="false" type="VARCHAR" size="42" inputSize="80" order="false" />
    <column name="JYFS" id="2c905e725b21f71a015b224474cf0061" javaName="jyfs" required="false" type="VARCHAR" size="42" inputSize="80" order="false" />
</table>
<table name="CMST_MONITORINGTYPE" id="2c905e725b21f71a015b2244c6c5008a" javaName="CmstMonitoringtype" idMethod="uuid.hex" isLinkTable="false" skipSql="false" abstract="false" chineseDescription="CMST_MONITORINGTYPE" generatePO="true" lazy="true" generatePK="true" generateFK="true">
    <meta key="height" value="229" />
    <meta key="width" value="160" />
    <meta key="xpos" value="559" />
    <meta key="ypos" value="324" />
    <column name="OBJ_ID" id="2c905e725b21f71a015b2244c89a008b" javaName="objId" description="唯一标识" primaryKey="true" required="true" type="VARCHAR" size="42" inputSize="80" order="false" />
    <column name="OBJ_DISPIDX" id="2c905e725b21f71a015b2244c8a9008c" javaName="objDispidx" description="（系统内部字段）" required="true" type="NUMERIC" size="22" inputSize="80" order="false" />
    <column name="OBJ_CAPTION" id="2c905e725b21f71a015b2244c8a9008d" javaName="objCaption" required="false" type="VARCHAR" size="256" inputSize="80" order="false" />
    <column name="TYPENAME" id="2c905e725b21f71a015b2244c8a9008e" javaName="typename" description="类型名称" required="false" type="VARCHAR" size="50" inputSize="80" order="false" />
    <column name="TYPECODE" id="2c905e725b21f71a015b2244c8a9008f" javaName="typecode" description="类型编码" required="false" type="VARCHAR" size="10" inputSize="80" order="false" />
    <column name="MONITORINGSPECIALTY" id="2c905e725b21f71a015b2244c8a90090" javaName="monitoringspecialty" description="专业性质" required="false" type="VARCHAR" size="10" inputSize="80" order="false" />
    <column name="MONITORINGDATATABLE" id="2c905e725b21f71a015b2244c8a90091" javaName="monitoringdatatable" description="监测数据表" required="false" type="VARCHAR" size="100" inputSize="80" order="false" />
    <column name="ISALARM" id="2c905e725b21f71a015b2244c8a90092" javaName="isalarm" description="是否告警" required="false" type="CHAR" inputSize="80" order="false" />
    <column name="REMARKS" id="2c905e725b21f71a015b2244c8a90093" javaName="remarks" description="备注" required="false" type="VARCHAR" size="200" inputSize="80" order="false" />
    <column name="TYPENAMEJC" id="2c905e725b21f71a015b2244c8a90094" javaName="typenamejc" description="监测类型名称" required="false" type="VARCHAR" size="40" inputSize="80" order="false" />
    <column name="EQUIPMENTTYPE" id="2c905e725b21f71a015b2244c8a90095" javaName="equipmenttype" description="被监测设备类型" required="false" type="VARCHAR" size="10" inputSize="80" order="false" />
</table>
</database>
</app-data>