package Server;

import Common.*;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.ReferenceCountUtil;

import java.io.File;
import java.io.IOException;
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
        try {
            if (message == null) {
                return;
            }
            if (message instanceof Auth) {
                String userFolder = ((Auth) message).getLogin();
                new File("SERVER_FILES/ServerStorage/" + userFolder).mkdirs();
                new File("CLIENT_FILES/ClientStorage/" + userFolder).mkdirs();
                System.out.println("File created: " + userFolder);

                root = new StringBuilder("SERVER_FILES").append(File.separator)
                        .append("ServerStorage").append(File.separator)
                        .append(userFolder).append(File.separator).toString();
            }
            if (message instanceof DeleteRequest) {
                DeleteRequest deleteRequest = (DeleteRequest) message;
                Files.delete(Paths.get(root + deleteRequest.getFileName()));
                return;
            }
            if (message instanceof FileMessage) {
                Files.write(Paths.get(root + ((FileMessage) message)
                        .getFileName()), ((FileMessage) message).getData(), StandardOpenOption.CREATE);
                System.out.println("File has been received");
                sendFilesList(channelHandlerContext);
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
            if (message instanceof FilesListRequest) {
                sendFilesList(channelHandlerContext);
                return;
            }
        } finally {
            ReferenceCountUtil.release(message);
        }
    }

    private void sendFilesList(ChannelHandlerContext ctx) throws IOException {
        FilesListRequest lr = new FilesListRequest();
        Files.list(Paths.get(root)).map(p -> p.getFileName().toString()).forEach(o -> lr.addFile(o));
        ctx.writeAndFlush(lr);
        System.out.println(lr.getRemoteFiles());
    }

}