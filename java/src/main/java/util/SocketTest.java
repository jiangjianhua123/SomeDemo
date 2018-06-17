package util;

import netty.Message;
import org.apache.commons.lang3.ArrayUtils;

import java.io.*;
import java.net.Socket;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author jianghong
 * @Description
 * @create 2018-06-07 10:11
 **/
public class SocketTest {

    public static void main(String[] args) throws Exception{
        //模拟发送对象
        Message message = new Message();
        Path path = Paths.get("D:\\成都照片\\廖伟.jpg");
        int file_context_len = (int) path.toFile().length();
        String jsonStr = String.format("{\"MAX_RESULT\": %d, \"MIN_SCORE\": %f, \"LIBRARIES\": [], \"FILE_SOURCE_TYPE\": \"BINARY\",\"FILE_TYPE\": \"JPG\", \"FILE_LENGTH\": %d}", 10, 0.2, file_context_len);
        int json_len = jsonStr.length();
        int content_length = 47 + json_len + file_context_len;
        message.setJsonStr(jsonStr);
        message.setJsonStrLeg(json_len);
        message.setContent_length(content_length);
        message.setFile_byte(file2Byte(path.toFile()));

        byte[] temp = new byte[0];
        temp = ArrayUtils.addAll(temp,intToBytesBig(message.getContent_length()));
        temp = ArrayUtils.addAll(temp,(byte) message.getProtocol_flag());
        temp = ArrayUtils.addAll(temp,message.getVersion());
        temp = ArrayUtils.addAll(temp,(byte) message.getMessage_flag());
        temp = ArrayUtils.addAll(temp, intToBytesBig(message.getCommand()));
        temp = ArrayUtils.addAll(temp,message.getMessage_id());
        temp = ArrayUtils.addAll(temp,intToBytesBig(message.getJsonStrLeg()));
        temp = ArrayUtils.addAll(temp,message.getJsonStr().getBytes("utf-8"));
        temp = ArrayUtils.addAll(temp,message.getFile_byte());

        //客户端
        //1、创建客户端Socket，指定服务器地址和端口
        Socket socket =new Socket("192.168.3.241",9004);
        //2、获取输出流，向服务器端发送信息
        OutputStream os = socket.getOutputStream();
        os.write(temp);
        os.flush();
        socket.shutdownOutput();
        //3、获取输入流，并读取服务器端的响应信息
        InputStream is = socket.getInputStream();
//        ByteArrayInputStream
//        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        while(is.available()>0){
            System.out.print((byte)is.read()+",");
        }
        //4、关闭资源
//        br.close();
        is.close();
        os.close();
        socket.close();

//        byte[] temp_byte_4 = new byte[4];
//        is.read(temp_byte_4);
//
//        int contentleng = byteArrayToInt(temp_byte_4);
//
//        byte protocol_flag = (byte)is.read();
//        byte[] version = new byte[5];
//        is.read(version);
//        byte message_flag = (byte)is.read();
//
//        is.read(temp_byte_4);
//        int command = byteArrayToInt(temp_byte_4);
//
//        byte[] message_id = new byte[32];
//        is.read(message_id);
//
//        is.read(temp_byte_4);
//        int jsonStrLeg = byteArrayToInt(temp_byte_4);
//
//        byte[] json_bytes = new byte[jsonStrLeg];
//        is.read(json_bytes);
//        String json_str = new String(json_bytes,"utf-8");
//
//        is.available();
//        byte[] file_byte = new byte[2048];
//        is.read(file_byte);



        //4、关闭资源
        is.close();
        os.close();
        socket.close();

    }


    static byte[] intToBytesBig(int value) {
        byte[] src = new byte[4];
        src[0] = (byte) ((value >> 24) & 0xFF);
        src[1] = (byte) ((value >> 16) & 0xFF);
        src[2] = (byte) ((value >> 8) & 0xFF);
        src[3] = (byte) (value & 0xFF);
        return src;
    }

    public static int byteArrayToInt(byte[] b) {
        return   b[3] & 0xFF |
                (b[2] & 0xFF) << 8 |
                (b[1] & 0xFF) << 16 |
                (b[0] & 0xFF) << 24;
    }


    public static byte[] file2Byte(File file) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        int i;
        byte[] b = new byte[1024];
        while ((i = fis.read(b)) != -1) {
            bos.write(b, 0, i);
        }
        fis.close();
        bos.close();
        return bos.toByteArray();
    }

}
