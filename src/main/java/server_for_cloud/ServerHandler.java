package server_for_cloud;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ServerHandler extends SimpleChannelInboundHandler <Object> {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("New client connection" + ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Client disconnect");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object data) throws Exception {
        ByteBuf buf = (ByteBuf)data;
        while (buf.readableBytes() > 0) {
            System.out.println("Пользователь отправляет данные");
        }
        buf.release();
    }


}