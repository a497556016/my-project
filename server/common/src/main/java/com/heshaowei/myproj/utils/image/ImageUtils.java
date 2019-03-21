package com.heshaowei.myproj.utils.image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Base64;
  

/** 
 * @author zxn 
 * @version 创建时间：2014-7-2 上午11:40:40 
 *  
 */  
public class ImageUtils {  
    /** 
     * 将网络图片进行Base64位编码 
     *  
     * @param imageUrl
     *            图片的url路径，如http://.....xx.jpg 
     * @return 
     */  
    public static String encodeImgageToBase64(URL imageUrl) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理  
        ByteArrayOutputStream outputStream = null;  
        try {  
            BufferedImage bufferedImage = ImageIO.read(imageUrl);  
            outputStream = new ByteArrayOutputStream();  
            ImageIO.write(bufferedImage, "jpg", outputStream);  
        } catch (MalformedURLException e1) {  
            e1.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        // 对字节数组Base64编码  
        Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(outputStream.toByteArray());// 返回Base64编码过的字节数组字符串
    }  
  
    /** 
     * 将本地图片进行Base64位编码 
     *  
     * @param imageFile
     *            图片的url路径，如http://.....xx.jpg 
     * @return 
     */  
    public static String encodeImgageToBase64(File imageFile) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理  
        ByteArrayOutputStream outputStream = null;  
        try {  
            BufferedImage bufferedImage = ImageIO.read(imageFile);  
            outputStream = new ByteArrayOutputStream();  
            ImageIO.write(bufferedImage, "jpg", outputStream);  
        } catch (MalformedURLException e1) {  
            e1.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        // 对字节数组Base64编码  
        Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(outputStream.toByteArray());// 返回Base64编码过的字节数组字符串
    }
  
    /** 
     * 将Base64位编码的图片进行解码，并保存到指定目录 
     *  
     * @param base64 
     *            base64编码的图片信息 
     * @return 
     */  
    public static void decodeBase64ToImage(String base64, String path,  
            String imgName) {  
        Base64.Decoder decoder = Base64.getDecoder();
        try {  
            FileOutputStream write = new FileOutputStream(new File(path  
                    + imgName));  
            byte[] decoderBytes = decoder.decode(base64);
            write.write(decoderBytes);  
            write.close();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }

    public static String zoom(String file, int w, int h) throws Exception {
        return new ImageHandler(file).zoom(w, h).writeToFile();
    }

    public static String scaleW(String file, int w) throws Exception {
        return new ImageHandler(file).scaleW(w).writeToFile();
    }

    public static String scaleH(String file, int h) throws Exception {
        return new ImageHandler(file).scaleH(h).writeToFile();
    }
}  