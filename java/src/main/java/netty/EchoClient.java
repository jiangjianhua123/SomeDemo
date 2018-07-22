package netty;


import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.apache.commons.io.FileUtils;

import java.net.InetSocketAddress;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author jianghong
 * @Description
 * @create 2018-06-06 10:39
 **/
public class EchoClient {
    private final String host;
    private final int port;

    private volatile byte[] resultBytes;

    private volatile int i = 0;

    private volatile int datalength;

    public EchoClient() {
        this(0);
    }

    public byte[] getResultBytes() {
        return resultBytes;
    }

    public void setResultBytes(byte[] resultBytes) {
        this.resultBytes = resultBytes;
    }

    public EchoClient(int port) {
        this("localhost", port);
    }

    public EchoClient(String host, int port) {
        this.host = host;
        this.port = port;
    }


    public void start() throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            ChannelFuture cf = null;
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(new InetSocketAddress(this.host, this.port))
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch){
                            ch.pipeline().addLast("encoder", new MessageDecoder());
                            ch.pipeline().addLast("decoder", new MessageEncoder());
                            ch.pipeline().addLast(new CapClientHandler());
                        }
                    });
            cf = b.connect().sync();
            System.out.println("服务端连接成功...");
            sendNodeMessage(cf.channel());
            cf.channel().closeFuture().sync();
            System.out.println("连接已关闭..");

        } finally {
            group.shutdownGracefully().sync(); // 释放线程池资源
        }


    }

    private static void sendNodeMessage(Channel channel) throws Exception{
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
        message.setFile_byte(FileUtils.readFileToByteArray(path.toFile()));
        channel.writeAndFlush(message);
    }

    public static void main(String[] args) throws Exception {
        EchoClient client = new EchoClient("192.168.3.241", 9004);
        client.start();

    }
}