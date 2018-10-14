package com.sgcc.pms.zbztjc.util.myUtils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.sgcc.uap.rest.support.DicItems;

public class Util {

	private static ImgCompress imgCompress = new ImgCompress();

//	数组
	/**
	 * 将数组(按逗号)拼接为字符串
	 * @param array 数组
	 * @return 数组按逗号拼接后的字符串
	 */
	public static String arrayToString(Object[] array) {
		return arrayToString(array, ",");
	}

	/**
	 * 将数组(按字符)拼接为字符串
	 * @param array 数组
	 * @param separator 拼接字符
	 * @return 数组按拼接字符拼接后的字符串
	 * 数组中为空的元素会被删掉 不会出现在拼接后的字符串里(至于空的元素删掉是否合理 正在考虑)
	 */
	public static String arrayToString(Object[] array, String separator) {
		return StringUtils.join(arrayRemoveEmpty(array), separator);
	}
	
	/**
	 * 删除数组中为空的元素
	 * @param array
	 * @return
	 */
	public static Object[] arrayRemoveEmpty(Object[] array) {
		return ArrayUtils.removeElement(array, null);
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
	public static void resizeFix(InputStream inputStream, int w, int h, OutputStream out) {
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
	public static void resizeTo(InputStream inputStream, int w, int h, OutputStream out) {
		imgCompress.resizeTo(inputStream, w, h, out);
	}
	
	/**
	 * 按照原始宽高写到输出流
	 * @param inputStream
	 * @param out
	 * @throws IOException
	 */
	public static void writeImg(InputStream inputStream, OutputStream out) {
		imgCompress.writeImg(inputStream, out);
	}

//	字典
	/**
	 * 从属性文件中查询字典
	 * @param fieldName 用来前台取值的名字
	 * @param dicId 用来取数据字典值的名字
	 * @return
	 */
	public static DicItems translateFromFile(String fieldName, String dicId) {
		return Translate.translateFromFile(fieldName, dicId);
	}
	
	/**
	 * 从数据库中查询字典
	 * @param fieldName 字典名称
	 * @param tableName	要查的表
	 * @param keyField 字典键
	 * @param valueField 字典值
	 * @return 字典对象
	 */
	public static DicItems translateFromDB(String fieldName, String tableName, String keyField, String valueField) {
		return translateFromDB(fieldName, tableName, keyField, valueField, null);
	}
	
	/**
	 * 从数据库中查询字典
	 * @param fieldName 字典名称
	 * @param tableName	要查的表
	 * @param keyField 字典键
	 * @param valueField 字典值
	 * @param where 条件
	 * @return 字典对象
	 */
	public static DicItems translateFromDB(String fieldName, String tableName, String keyField, String valueField, String where) {
		return Translate.translateFromDB(fieldName, tableName, keyField, valueField, where);
	}

//	数字
	/**
	 * 正数（针对indexOf）
	 * @param number
	 * @return
	 */
	public static boolean positive(int number) {
		return number >= 0;
	}
	
	/**
	 * 非零(针对数组、集合)
	 * @param number
	 * @return
	 */
	public static boolean notZero(int number) {
		return number != 0;
	}
	
	public static void free(InputStream inSt,OutputStream outSt,BufferedInputStream bufSt){
		try {
			if (inSt!=null){
				inSt.close();
			}
			if (outSt!=null){
				outSt.close();
			}
			if (bufSt!=null){
				bufSt.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
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