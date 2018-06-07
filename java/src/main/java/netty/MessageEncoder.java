package netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.apache.commons.lang3.ArrayUtils;

/**
 * @author jianghong
 * @Description
 * @create 2018-06-06 16:34
 **/
public class MessageEncoder extends MessageToByteEncoder<Message> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Message message, ByteBuf out) throws Exception {
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
        out.writeBytes(temp);
//        out.writeChar(message.getProtocol_flag());
//        out.writeBytes(message.getVersion());
//        out.writeChar(message.getMessage_flag());
//        out.writeInt(message.getCommand());
//        out.writeBytes(message.getMessage_id());
//        out.writeInt(message.getJsonStrLeg());
//        out.writeBytes(message.getJsonStr().getBytes("utf-8"));
//        out.writeBytes(message.getFile_byte());
    }

    static byte[] intToBytesBig(int value) {
        byte[] src = new byte[4];
        src[0] = (byte) ((value >> 24) & 0xFF);
        src[1] = (byte) ((value >> 16) & 0xFF);
        src[2] = (byte) ((value >> 8) & 0xFF);
        src[3] = (byte) (value & 0xFF);
        return src;
    }


}