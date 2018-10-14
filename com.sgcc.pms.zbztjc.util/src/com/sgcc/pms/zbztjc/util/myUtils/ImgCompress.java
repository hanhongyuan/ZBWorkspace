package com.sgcc.pms.zbztjc.util.myUtils;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;

import javax.imageio.ImageIO;

public class ImgCompress {
    private Image img;
    private int width;
    private int height;
    
    private void setImg(InputStream inputStream) {
		try {
			this.img = ImageIO.read(inputStream); // 构造Image对象
			this.width = img.getWidth(null);     // 得到源图宽 
			this.height = img.getHeight(null);    // 得到源图长  
		} catch (IOException e) {
			System.out.println("Util: ImgCompress: setImg(): 图像构造失败，IO异常: line 18-20");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Util: ImgCompress: setImg(): 图片构造失败，未知异常: line 18-20");
			e.printStackTrace();
		}
	}
    
	/** 
     * 按照宽度或高度进行压缩
     * @param w int 最大宽度
     * @param h int 最大高度
     * @throws IOException
     */  
    public void resizeFix(InputStream inputStream, int w, int h, OutputStream out) { 
    	setImg(inputStream);
    	
        if (width / height < w / h) {
            resizeByWidth(w, out);  
        } else {  
            resizeByHeight(h, out);  
        }  
    }
    
    /**
     * 按照给定的宽度和高度压缩
     * @param inputStream
     * @param w
     * @param h
     * @param out
     * @throws IOException
     */
    public void resizeTo(InputStream inputStream, int w, int h, OutputStream out) {
    	setImg(inputStream);
    	resize(w, h, out);
    }
    
    /**
     * 按照原始宽高写到输出流
     * @param inputStream
     * @param out
     * @throws IOException
     */
    public void writeImg(InputStream inputStream, OutputStream out) {
    	setImg(inputStream);
    	resize(width, height, out);
    }
    
    /** 
     * 以宽度为基准，等比例放缩图片
     * @param w int 新宽度
     */  
    public void resizeByWidth(int w, OutputStream out) {  
        int h = (int) (height * w / width);
        resize(w, h, out);  
    }
    
    /** 
     * 以高度为基准，等比例缩放图片
     * @param h int 新高度
     */
    
    public void resizeByHeight(int h, OutputStream out) {  
        int w = (int) (width * h / height);
        resize(w, h, out);  
    }
    
    /** 
     * 强制压缩/放大图片到固定的大小
     * @param w int 新宽度
     * @param h int 新高度
     */  
    public void resize(int w, int h, OutputStream out) {
        try {
			// SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的 优先级比速度高 生成的图片质量比较好 但速度慢 
			BufferedImage image = new BufferedImage(w, h,BufferedImage.TYPE_INT_RGB ); 
			image.getGraphics().drawImage(img, 0, 0, w, h, null); // 绘制缩小后的图 
			// 可以正常实现bmp、png、gif转jpg
			ImageIO.write(image, "png", out);
			out.close();
		} catch (IOException e) {
			System.out.println("Util: ImgCompress: resiza(): 图片写出失败，IO异常: line 89-93");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Util: ImgCompress: resiza(): 图片写出失败，未知异常: line 89-93");
			e.printStackTrace();
		}
    }
}
