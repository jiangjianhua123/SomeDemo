package netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author jianghong
 * @Description
 * @create 2018-06-06 19:45
 **/
public class MessageDecoder extends ByteToMessageDecoder {

    private final int HEAD_LENGTH = 47;


    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> list) throws Exception {
        /**这个HEAD_LENGTH是我们用于表示头长度的字节数**/
        if (in.readableBytes() < HEAD_LENGTH) {
            return;
        }
        /**读取传送过来的消息的长度。ByteBuf 的readInt()方法会让他的readIndex增加4**/
        int dataLength = in.readInt();
        byte protocol_flag = in.readByte();
        byte[] version = new byte[5];
        in.readBytes(version);
        byte message_flag = in.readByte();
        int command = in.readInt();
        byte[] message_id = new byte[32];
        in.readBytes(message_id);
        int jsonStrLeg = in.readInt();
        byte[] json_bytes = new byte[jsonStrLeg];
        in.readBytes(json_bytes);
        String json_str = new String(json_bytes,"utf-8");
        /** 我们读到的消息体长度为0，这是不应该出现的情况，这里出现这情况，关闭连接。**/
        if (dataLength < 0) {
            ctx.close();
        }
        /**读到的消息体长度如果小于我们传送过来的消息长度，则resetReaderIndex. 这个配合markReaderIndex使用的。把readIndex重置到mark的地方**/
        if (in.readableBytes() < 2048) {
            in.resetReaderIndex();
            return;
        }
        byte[] body = new byte[2048];
        in.readBytes(body);
        Message message = new Message();
        message.setFile_byte(body);
        message.setJsonStr(json_str);
        list.add(message);
    }
}
