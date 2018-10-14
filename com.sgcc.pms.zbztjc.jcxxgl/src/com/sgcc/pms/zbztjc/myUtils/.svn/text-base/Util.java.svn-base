package com.sgcc.pms.zbztjc.myUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.sgcc.uap.bizc.sysbizc.datadictionary.IDataDictionaryBizC;
import com.sgcc.uap.rest.support.DicItems;

public class Util {

	private static ImgCompress imgCompress = new ImgCompress();
	
//	三元表达式
	/**
	 * 三元表达式(是否为空或0 只兼容字符串和数字)
	 * @param variable
	 * @param tTrue
	 * @return
	 */
	public static Object ternary(Object variable, Object tTrue) {
		return Ternary.getNotNull(variable, tTrue);
	}

//	数组
	/**
	 * 将数组(逗号)拼接为字符串
	 * @param array 数组
	 * @return 数组按逗号拼接后的字符串
	 */
	public static String arrayToString(Object[] array) {
		return arrayToString(array, ",");
	}

	/**
	 * 将数组(字符)拼接为字符串
	 * @param array
	 * @param separator 拼接字符
	 * @return 数组按拼接字符拼接后的字符串
	 */
	public static String arrayToString(Object[] array, String separator) {
		return StringUtils.join(arrayRemoveEmpty(array), separator);
	}
	
	/**
	 * 替换数组中的元素
	 * @param element
	 * @param reElement
	 * @param array
	 */
	public static void arrayReplace(Object element, Object reElement, Object[] array) {
		array[arrayIndexOf(element, array)] = reElement;
	}
	
	/**
	 * 获取元素在数组中的索引
	 * @param element
	 * @param array
	 * @return
	 */
	public static int arrayIndexOf(Object objectToFind, Object[] array) {
		return ArrayUtils.indexOf(array, objectToFind);
	}
	
	/**
	 * 删除数组中为空的元素
	 * @param array
	 * @return
	 */
	public static Object[] arrayRemoveEmpty(Object[] array) {
		return arrayRemove(null, array);
	}
	
	/**
	 * 删除数组中的元素
	 * @param element
	 * @param array
	 * @return
	 */
	public static Object[] arrayRemove(Object element, Object[] array) {
		return ArrayUtils.removeElement(array, element);
	}
	
	/**
	 * 删除数组中指定索引的元素
	 * @param index
	 * @param array
	 * @return
	 */
	public static Object[] arrayRemove(int index, Object[] array) {
		return ArrayUtils.remove(array, index);
	}
	
	/**
	 * 数组中是否包含某一个值
	 * @param array
	 * @param objectToFind
	 * @return
	 */
	public static <T> boolean container(T[] array, T objectToFind) {
		return ArrayUtils.contains(array, objectToFind);
	}

//	字符串
	/**
	 * 非空
	 * @param str
	 * @return
	 */
	public static boolean notEmpty(String str) {
		return StringUtils.isNotEmpty(str);
	}
	
	/**
	 * 为空
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		return StringUtils.isEmpty(str);
	}
	
	/**
	 * 字符串中是否为数字
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		return StringUtils.isNumeric(str);
	}

//	图片
	/**
	 * 改变图片的大小(参照图片本身的宽高)
	 * @param inputStream
	 * @param w
	 * @param h
	 * @param out
	 * @throws IOException
	 * @throws SQLException
	 */
	public static void resizeFix(InputStream inputStream, int w, int h, OutputStream out) throws IOException, SQLException {
		imgCompress.resizeFix(inputStream, w, h, out);
	}
	
	/**
	 * 改变图片的大小
	 * @param inputStream
	 * @param w
	 * @param h
	 * @param out
	 * @throws IOException
	 * @throws SQLException
	 */
	public static void resizeTo(InputStream inputStream, int w, int h, OutputStream out) throws IOException, SQLException {
		imgCompress.resizeTo(inputStream, w, h, out);
	}

//	数据字典
	/**
	 * 根据电压等级范围获取多个dm的值
	 * @param dydjfw
	 * @return
	 */
	public static String getDms(String dydjfw) {
		return Translate.getDms(dydjfw);
	}

	/**
	 * 根据dm获取电压等级的值
	 * @param dm
	 * @return
	 */
	public static String getDydj(String dm) {
		return Translate.getDydj(dm);
	}

	/**
	 * 从属性文件中查询字典
	 * @param fieldName
	 * @param dicId
	 * @return
	 */
	public static DicItems translateFromFile(String fieldName, String dicId) {
		return Translate.translateFromFile(fieldName, dicId);
	}
	
	/**
	 * 从数据库中查询字典
	 * @param fieldName
	 * @param poName
	 * @param keyField
	 * @param valueField
	 * @return
	 */
	public static DicItems translateFromDB(String fieldName, String poName,String keyField, String valueField) {
		return Translate.translateFromDB(fieldName, poName, keyField, valueField);
	}
	
	/**
	 * 自定义数据字典对象
	 * @param dataDictionaryBizC
	 */
	public static void setDataDictionaryBizC(IDataDictionaryBizC dataDictionaryBizC) {
		Translate.setDataDictionaryBizC(dataDictionaryBizC);
	}

//	数字
	/**
	 * 有效数字(不包含零)
	 * @param number
	 * @return
	 */
	public static boolean validNumber(int number) {
		return number > 0;
	}
	
	/**
	 * 正数
	 * @param number
	 * @return
	 */
	public static boolean positive(int number) {
		return !minus(number);
	}
	
	/**
	 * 负数
	 * @param number
	 * @return
	 */
	public static boolean minus(int number) {
		return number < 0;
	}
	
	/**
	 * 非零
	 * @param number
	 * @return
	 */
	public static boolean notZero(int number) {
		return !isZero(number);
	}

	/**
	 * 为零
	 * @param number
	 * @return
	 */
	public static boolean isZero(int number) {
		// TODO Auto-generated method stub
		return number == 0;
	}
	public static String getCurrentTime(String format){
		SimpleDateFormat df = new SimpleDateFormat(format);	//定义时间格式为 2016-10-24 12:02:15
		String currentTime = null;
		Calendar curr = Calendar.getInstance();
		Date date =curr.getTime();
		currentTime = df.format(date);
		return currentTime;
	}
}