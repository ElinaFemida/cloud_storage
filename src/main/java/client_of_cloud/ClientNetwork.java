package client_of_cloud;

import common.JsonDecoder;
import common.JsonEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.io.IOException;

public class ClientNetwork {
    private SocketChannel channel;
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 3606;


    public ClientNetwork() {
        Thread t = new Thread(() -> {
            NioEventLoopGroup workerGroup = new NioEventLoopGroup();
            try {
                Bootstrap b = new Bootstrap()
                        .group(workerGroup)
                        .channel(NioSocketChannel.class)
                        .handler(new ChannelInitializer<SocketChannel>() {
                            @Override
                            protected void initChannel(SocketChannel ch) {
                                channel = ch;
                                ch.pipeline().addLast(
                                        new LengthFieldBasedFrameDecoder(1024*1024,0,3,0,3),
                                        new LengthFieldPrepender(3),
                                        new StringEncoder(),
                                        new JsonDecoder(),
                                        new JsonEncoder());
                            }
                        });
                ChannelFuture future = b.connect(SERVER_HOST, SERVER_PORT).sync();
                future.channel().closeFuture().sync();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                workerGroup.shutdownGracefully();
            }
        });
        t.setDaemon(true);
        t.start();
    }

    public void close() {
        channel.close();
    }

    public void sendMessage(String str) {
        channel.writeAndFlush(str);
    }
}








