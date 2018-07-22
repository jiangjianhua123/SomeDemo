package netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class CapClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //super.channelRead(ctx, msg);
        Message message = (Message) msg;
        byte[] temp = message.getFile_byte();
        System.err.println(temp);
        ctx.channel().closeFuture().sync();

    }
}



