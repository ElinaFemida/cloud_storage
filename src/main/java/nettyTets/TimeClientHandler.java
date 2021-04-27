package nettyTets;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.internal.StringUtil;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

/*public class TimeClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf m = (ByteBuf) msg; // (1)
        try {
            long currentTimeMillis = (m.readUnsignedInt() - 2208988800L) * 1000L;
            System.out.println(new Date(currentTimeMillis));
            ctx.close();
        } finally {
            m.release();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
*/

class TimeClientHandler extends SimpleChannelInboundHandler<Object> {

    private ChannelHandlerContext ctx;

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println("connected");
        if (Objects.isNull(this.ctx)) {
            this.ctx = ctx;
        }
    }
    public boolean isConnected (){
        return Objects.nonNull(ctx);
    }


    @Override
    public void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 读取服务端发来的回应消息
        ByteBuf buf = (ByteBuf) msg;
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);
        String body = new String(req, StandardCharsets.UTF_8);

        System.out.printf("client receive msg: {}", body);

//    ctx.close();
    }
        @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            System.out.printf("Unexpected exception from downstream: {}", cause.getMessage());
        ctx.close();
    }

    public void sendMsg(String msg) {
        if (StringUtil.isNullOrEmpty(msg)) {
            return;
        }

        if (Objects.isNull(ctx)) {
            System.out.printf("not connect");
            return;
        }

        System.out.printf("msg={}", msg);
        byte[] req = msg.getBytes();
        ByteBuf firstMessage = Unpooled.buffer(req.length);
        firstMessage.writeBytes(req);

        ctx.writeAndFlush(firstMessage);
    }

    public ChannelHandlerContext getCtx() {
        return ctx;
    }

    public void close() {
        this.ctx.close();
    }
}