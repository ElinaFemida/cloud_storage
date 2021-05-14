package server_for_cloud;

import common.*;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class ServerHandler extends SimpleChannelInboundHandler<Object> {
    private static String root;

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
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object message) throws Exception {
        if (message == null) {
            return;
        }

        if (message instanceof FileRequest) {
            FileRequest fr = (FileRequest) message;
            if (Files.exists(Paths.get(root + fr.getFileName()))) {
                FileMessage fm = new FileMessage(Paths.get(root + fr.getFileName()));
                channelHandlerContext.writeAndFlush(fm);
            }
            return;
        }
        if (message instanceof FileMessage) {
            Files.write(Paths.get(root + ((FileMessage) message)
                    .getFileName()), ((FileMessage) message).getData(), StandardOpenOption.CREATE);
            System.out.println("File has been received");
            return;
        }
        if (message instanceof DeleteRequest) {
            DeleteRequest deleteRequest = (DeleteRequest) message;
            Files.delete(Paths.get(root + deleteRequest.getFileName()));

            return;
        }
    }


}