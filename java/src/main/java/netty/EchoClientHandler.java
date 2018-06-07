package netty;


import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import util.OperatorUtil;


/**
 * @author jianghong
 * @Description
 * @create 2018-06-06 10:39
 **/
public class EchoClientHandler extends SimpleChannelInboundHandler<Message> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Message message) {
        byte[] temp = message.getFile_byte();
        float[] features = new float[512];
        for (int i=0; i<512; i++){
            features[i] = OperatorUtil.byte2float(temp, i * 4);
        }
    }



}