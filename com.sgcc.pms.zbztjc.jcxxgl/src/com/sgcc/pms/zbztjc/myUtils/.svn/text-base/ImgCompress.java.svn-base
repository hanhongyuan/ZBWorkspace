package com.sgcc.pms.zbztjc.myUtils;

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
    
	/** 
     * 按照宽度还是高度进行压缩 
     * @param w int 最大宽度 
     * @param h int 最大高度 
     * @throws IOException 
     */  
    public void resizeFix(InputStream inputStream, int w, int h, OutputStream out) throws IOException { 
    	setImg(inputStream);
    	
        if (width / height > w / h) {  
            resizeByWidth(w, out);  
        } else {  
            resizeByHeight(h, out);  
        }  
    }
    
    public void resizeTo(InputStream inputStream, int w, int h, OutputStream out) throws IOException {
    	setImg(inputStream);
    	
    	resize(w, h, out);
    }
    
    private void setImg(InputStream inputStream) throws IOException {
		this.img = ImageIO.read(inputStream);// 构造Image对象
		this.width = img.getWidth(null);     // 得到源图宽    
		this.height = img.getHeight(null);    // 得到源图长    
	}
    
    /** 
     * 以宽度为基准，等比例放缩图片 
     * @param w int 新宽度 
     */  
    public void resizeByWidth(int w, OutputStream out) throws IOException {  
        int h = (int) (height * w / width);  
        resize(w, h, out);  
    }
    
    /** 
     * 以高度为基准，等比例缩放图片 
     * @param h int 新高度 
     */
    
    public void resizeByHeight(int h, OutputStream out) throws IOException {  
        int w = (int) (width * h / height);  
        resize(w, h, out);  
    }
    
    /** 
     * 强制压缩/放大图片到固定的大小 
     * @param w int 新宽度 
     * @param h int 新高度 
     */  
    public void resize(int w, int h, OutputStream out) throws IOException {  
        // SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的 优先级比速度高 生成的图片质量比较好 但速度慢  
        BufferedImage image = new BufferedImage(w, h,BufferedImage.TYPE_INT_RGB );   
        image.getGraphics().drawImage(img, 0, 0, w, h, null); // 绘制缩小后的图  
        // 可以正常实现bmp、png、gif转jpg  
        
        ImageIO.write(image, "png", out);
        out.close();  
    }
}
