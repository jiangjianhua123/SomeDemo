package com.jjh.springboot.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;

public class Base64Util {

    /**
     * Base64字符串转输入流
     * @param imgStr 图片base64编码
     * @return
     */
    public static InputStream stringToInputStream(String imgStr) {
        ByteArrayInputStream inputStream = null;
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] bytes = decoder.decodeBuffer(imgStr);
            inputStream = new ByteArrayInputStream(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  inputStream;
    }

    /**
     * 图片转Base64字符串
     * @param file 图片File
     * @return
     */
    public static String imageToString(File file) {
        byte[] bytes = null;
        try {
            InputStream in = new FileInputStream(file);
            bytes = new byte[in.available()];
            in.read(bytes);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(bytes).replaceAll("\r\n", "");
    }


    public static String getImageStr(String imgFile) {
        InputStream inputStream = null;
        byte[] data = null;
        try {
            inputStream = new FileInputStream(imgFile);
            data = new byte[inputStream.available()];
            inputStream.read(data);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 加密
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);
    }


    /**
     * base64字符串转储为图片文件
     * @param imgStr 图片base64编码
     * @param imgFilePath 图片转储路径
     * @return
     */
    public static boolean stringToImage(String imgStr, String imgFilePath) {
        if (imgStr == null) {
            return false;
        }
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            byte[] bytes = decoder.decodeBuffer(imgStr);
            for (int i = 0; i < bytes.length; ++i) {
                if (bytes[i] < 0) {
                    bytes[i] += 256;
                }
            }
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(bytes);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
